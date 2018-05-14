package mro.quartz.job.sapJob.Job;

import java.util.List;
import java.util.Map;

import mro.quartz.job.sapJob.jco.BapiMaterialSavedata;
import mro.quartz.job.sapJob.jco.MesEqMroToTEBdata;
import mro.quartz.job.sapJob.jco.MespnMroToTABdata;
import mro.quartz.job.sapJob.jco.SapRfc;
import mro.quartz.job.sapJob.oracle.AccessLog;
import mro.quartz.job.sapJob.oracle.AccessMesPnLog;
import mro.quartz.job.sapJob.oracle.Ora;
import mro.quartz.job.sapJob.oracle.OraMroToMesPnIo;
import mro.quartz.job.sapJob.oracle.OraMroToSapIo;
import mro.quartz.job.sapJob.vo.MroToSapIo;
import mro.quartz.job.sapJob.vo.MroToSapMespn;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public class Mro2Sap{
	
	public static void main(String[] s) throws Exception{
		Mro2Sap job=new Mro2Sap();
		job.start();
	}
	
	public void start() throws Exception {
		/*
		 * hongjie.wu 順序不得調整，因為by site可以申請changed 所以必須以料號異動為優先
		 */
		System.out.println("Mro2SapJob開始");
		// 1:料號新增,2:規格異動,3:BY_SITE申請(順序不得改變，因為要避免異動覆蓋BYSITE的料號狀態)
		System.out.println("系統重拋開始");
		sapTransfer("0");// 系統重拋
		System.out.println("系統重拋結束");

		System.out.println("料號新增開始");
		sapTransfer("1");// 料號新增
		System.out.println("料號新增結束");

		System.out.println("規格異動開始");
		sapTransfer("2");// 規格異動
		System.out.println("規格異動結束");

		System.out.println("料號BySite拋轉開始");
		sapTransfer("3");// 料號BySite拋轉
		System.out.println("料號BySite拋轉結束");

		System.out.println("PN更新開始");
		// SAP PN碼更新 ，料號新增執行，只需拋一筆即可 'R1107', 'R1508', 'R1510', 'R1603',
		// 'R1606'
		sapTransferPN();
		System.out.println("PN更新結束");

		System.out.println("料號申請/異動狀態更新開始");
		updateAItemSite();
		System.out.println("料號申請/異動狀態更新結束");
		
		System.out.println("料號BySite拋轉更新ITEM_SITE開始");
		updateItemSite();
		System.out.println("料號BySite拋轉更新ITEM_SITE結束");
		Ora.disconnectOraDatabase();
		SapRfc.disconnect();
	}

	public static void sapTransfer(String applyCategory) throws Exception {
		BapiMaterialSavedata function = new BapiMaterialSavedata();
		OraMroToSapIo dao = new OraMroToSapIo();
		AccessLog lDAO = new AccessLog();
		int itemSiteTransferId=0;
		while(dao.getDataCount(applyCategory,itemSiteTransferId)>0){
			List<MroToSapIo> list = dao.getExperimentData(applyCategory,itemSiteTransferId); //一次10萬筆
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					MroToSapIo obj = list.get(i);
					//=============記下 最後一次的id，避免重覆計算=====================
					itemSiteTransferId=Integer.parseInt(obj.getItemSiteTransferId());
					//=======================================================
					int t = i + 1;
					System.out.println("共" + list.size() + "料號，目前執行第" + t + "，id="+itemSiteTransferId);
					Map result = function.execute(obj);
	
					if (result != null) {
						if (result.get("TYPE") != null) {
							System.out.println("TYPE:" + result.get("TYPE"));
						}
						if (result.get("MESSAGE") != null) {
							System.out.println("LogInfo:" + result.get("MESSAGE"));
						}
						System.out.println("MATERIAL:" + obj.getItemNum());
						System.out.println("PlantCode:" + obj.getPlantCode());
						System.out.println("TransferQuantity():"
								+ obj.getTransferQuantity());
						System.out.println("PackageUnit:" + obj.getPackageUnit());
	
						// sap_access_log
						lDAO.createLog(obj, result.get("TYPE").toString(), result
								.get("MESSAGE").toString());
	
						// =============拋轉結果更新ITEM_SITE_TRANSFER_LOG=======================
						if (result.get("TYPE") != null) {
							lDAO.updateItemSiteTranferLog(obj, result.get("TYPE")
									.toString());
						}
	
						System.out.println("TYPE to do:" + result.get("TYPE"));
						System.out.println("MATERIAL to do:" + obj.getItemNum());
					}
				}// list
			}
		}
		if (applyCategory.equals("3")) {// 料號BySite拋轉
			updateItemTransferLineApply(applyCategory);
		}
		// ======================更新IN20================================================
		if (!applyCategory.equals("0")) { // 系統重拋不更新IN20
			for (MroToSapIo m : lDAO.getUpdateAItemList(applyCategory)) {
				if (m.getItemNum() != null) { // 有單號
					boolean in20 = true;
					// 有拋轉sap且有料號
					if (m.getMaterial() != null && m.getTotalTransfer() > 0) {
						in20 = sapTransferEQ(m);
					}
					System.out.println("更新IN20:" + m.getItemNum()+",in20="+in20+",Material="+m.getMaterial());
					if (in20) {
						lDAO.updtateStatusToMro(applyCategory, m.getItemNum());
						System.out.println("更新IN20:" + m.getItemNum());
						lDAO.updtateItemStatusToMro(applyCategory, m.getMaterial());
						System.out.println("更新 item status:" + m.getMaterial());
						lDAO.updtateItemSiteStatusToMro(applyCategory, m.getMaterial());
						System.out.println("更新 item site:" + m.getMaterial());
					}
				}
			}
		}
	}

	public static void updateItemTransferLineApply(String applyCategory)
			throws Exception {
		AccessLog lDAO = new AccessLog();
		List<Map<String, String>> list = lDAO
				.getItemTransferLineApplyList(applyCategory);
		for (Map<String, String> m : list) {
			int total_transfer = Integer.parseInt(m.get("TOTAL_TRANSFER"));
			int total_success_transfer = Integer.parseInt(m
					.get("TOTAL_SUCCESS_TRANSFER"));
			String type = total_transfer != total_success_transfer ? "E" : "S";
			if (total_transfer > 0 && type.equals("S")) { // 拋轉sap且拋轉完成的料號，要拋轉eqid
				if (!sapTransferEQ(m.get("ITEMNUM"), m.get("CLASSSTRUCTUREID"),
						m.get("FACILITY_NUM"))) {
					type = "E";
				}
			}
			lDAO.updtateItemTransferLineApply(m.get("APPLY_LINE_ID"), type);
			System.out.println("更新APPLY_LINE_ID=" + m.get("APPLY_LINE_ID")
					+ ":" + type);
		}
	}

	public static void sapTransferPN() throws Exception {
		MespnMroToTABdata function = new MespnMroToTABdata();
		OraMroToMesPnIo dao = new OraMroToMesPnIo();
		List<MroToSapMespn> list = dao.getExperimentData();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {

				MroToSapMespn obj = list.get(i);
				Map result = function.execute(obj);
				AccessMesPnLog lDAO = new AccessMesPnLog();
				if (result != null) {
					if (result.get("ITEM") != null) {
						System.out.println("TYPE:" + result.get("ITEM"));
					}
					if (result.get("MESSAGE") != null) {
						System.out.println("LogInfo:" + result.get("MESSAGE"));
					}
					System.out.println("MATERIAL:" + obj.getItemNum());
					System.out.println("MATERIAL to do:" + obj.getItemNum());
					System.out.println("Material Group to do:"
							+ obj.getClassStructureid());

					lDAO.createLog(obj.getItemNum().toString(), obj
							.getClassStructureid().toString(),
							result.get("ITEM").toString(), result
									.get("MESSAGE").toString());
					System.out.println("TYPE to do:" + result.get("ITEM"));

				}
				if (result.get("ITEM").equals("S")
						|| result.get("MESSAGE").toString()
								.indexOf("The material had been exist") != -1) {
					lDAO.updtateStatusToMro(obj.getItemNum().toString());
					// lDAO.updtateStatusToMroItem(obj.getItemNum().toString());
					// 判斷若傳檔成功更新狀態in21='Y'
				} // for
			}// list
		}
	}

	public static boolean sapTransferEQ(MroToSapIo mroToSapIo) throws Exception {
		MesEqMroToTEBdata mesEqMroToTEBdata = new MesEqMroToTEBdata();
		MroToSapMespn obj = new MroToSapMespn();
		obj.setItemNum(mroToSapIo.getMaterial());
		obj.setEquipmentId(mroToSapIo.getFacilityNum());
		Map<String, String> map = mesEqMroToTEBdata.execute(obj);

		AccessLog lDAO = new AccessLog();
		lDAO.createEQLog(mroToSapIo.getMaterial(),
				mroToSapIo.getClassStructureid(), mroToSapIo.getFacilityNum(),
				map.get("TYPE"), map.get("MESSAGE"));
		System.out.println(mroToSapIo.getMaterial()
				+ " CLASSIFICATION_VIEW TYPE to do:" + map.get("TYPE"));
		if (map.get("TYPE").equals("S")) {
			return true;
		}
		return false;
	}

	public static boolean sapTransferEQ(String material,
			String classStructureid, String EquipmentId) throws Exception {
		MesEqMroToTEBdata mesEqMroToTEBdata = new MesEqMroToTEBdata();
		MroToSapMespn obj = new MroToSapMespn();
		obj.setItemNum(material);
		obj.setEquipmentId(EquipmentId);
		Map<String, String> map = mesEqMroToTEBdata.execute(obj);

		AccessLog lDAO = new AccessLog();
		lDAO.createEQLog(material, classStructureid, EquipmentId,
				map.get("TYPE"), map.get("MESSAGE"));
		System.out.println(material + " CLASSIFICATION_VIEW TYPE to do:"
				+ map.get("TYPE"));
		if (map.get("TYPE").equals("S")) {
			return true;
		}
		return false;
	}
	
	public static void updateAItemSite() throws Exception {
		AccessLog lDAO = new AccessLog();
		lDAO.updtateAItemApply();
	}
	
	public static void updateItemSite() throws Exception {
		AccessLog lDAO = new AccessLog();
		List<Map<String, String>> list = lDAO.getItemTransferheaderApplyList();
		for (Map<String, String> m : list) {
			if (m.get("ACTION").equals("I")) { // 啟用廠區
				lDAO.updtateItemTransferheaderApply(m, "APPR", "Y");
//				updtateEpMallCompanyId(m.get("APPLY_HEADER_ID"));// 更新erp
																	// epmallCompanyId
			} else if (m.get("ACTION").equals("S")) { // 失效廠區
				lDAO.updtateItemTransferheaderApply(m, "APPR", "");
//				updtateEpMallCompanyId(m.get("APPLY_HEADER_ID"));// 更新erp
																	// epmallCompanyId
			}
		}
	}

	// 更新erp epmallCompanyId
	public static void updtateEpMallCompanyId(String applyHeaderId)
			throws Exception {
		AccessLog lDAO = new AccessLog();
		List<Map<String, String>> list = lDAO
				.getItemTransferEpMallCompanyIdList(applyHeaderId);
		for (Map<String, String> m : list) {
			lDAO.updtateEpMallCompanyId(m);
		}
	}

	public boolean validateJob(JobExecutionContext jobExecutionContext)
			throws SchedulerException {
		List<JobExecutionContext> jobs = jobExecutionContext.getScheduler()
				.getCurrentlyExecutingJobs();
		for (JobExecutionContext job : jobs) {
			if (job.getTrigger().equals(jobExecutionContext.getTrigger())
					&& !job.getJobInstance().equals(this)) {
				System.out.println("job =" + job.getJobInstance() + "正在執行中!");
				return false;
			}
		}
		return true;
	}

}
