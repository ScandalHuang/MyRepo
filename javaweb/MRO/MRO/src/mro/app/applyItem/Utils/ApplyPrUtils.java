package mro.app.applyItem.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.entity.PrMcParameter;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.LocationSiteMapBO;
import mro.base.bo.PrlineBO;
import mro.base.bo.PrlineDisableSumcounterBO;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.form.PrForm;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.ActiveFlag;

public class ApplyPrUtils {
	
	public static Object[] onSelectAll(Object[] prs,List prList) { // 選取全部申請單
		if(Utility.isNotEmpty(prList)){
			if(Utility.isNotEmpty(prs)){//取消全部
				prs=null;
			}else{ //選取全部
				prs=prList.toArray(prs);
			}
		}
		return prs;
	}
	// ============================刪除檔案=============================================
	public static void deletePrFile(String prid, FileCategory category) { // PR檔案
		FileUploadInterfaces fileUploadInterfaces = new FileUploadImpl();
		fileUploadInterfaces.fileDelete(prid, category, "");
	}

	public static void deleteFile(BigDecimal prid, FileCategory category) {// prline檔案
		FileUploadInterfaces fileUploadInterfaces = new FileUploadImpl();

		PrlineBO prlineBO = SpringContextUtil.getBean(PrlineBO.class);
		List<Prline> listPrline = prlineBO.getPrlineList(prid, null);

		for (Prline p : listPrline) {
			if (p.getPrlineid() != null) {
				fileUploadInterfaces.fileDelete(p.getPrlineid().toString(),
						category, "");
			}
		}
	}
	
	public static void setErrorMap(PrForm prForm,String warnMessage){
		prForm.getLineErrorMap().clear();		
		for (Prline lp : prForm.getListPrline()) {
			StringBuffer lpMessage=new StringBuffer();
			int i1=warnMessage.indexOf(lp.getItemnum());
			int i2=warnMessage.indexOf("項次" + lp.getPrlinenum());
			while(i1!=-1 || i2 !=-1){
				if(i1!=-1){
					lpMessage.append(warnMessage.substring(i1, warnMessage.indexOf("<br />",i1)+6));
					i1=warnMessage.indexOf(lp.getItemnum(),warnMessage.indexOf("<br />",i1)+6);
				}
				if(i2!=-1){
					lpMessage.append(warnMessage.substring(i2, warnMessage.indexOf("<br />",i2)+6));
					i2=warnMessage.indexOf("項次" + lp.getPrlinenum(),warnMessage.indexOf("<br />",i2)+6);
				}
			}
			if(lpMessage.length()>0){
				prForm.getLineErrorMap().put(lp.getItemnum(), lpMessage.toString());
			}
		}
		
	}
	// =========================================ListPrline驗證====================================
	public static StringBuffer ListPrlineValidate(Prline prline,StringBuffer warnMessage) {
		if (StringUtils.isBlank(prline.getItemnum())) {
			warnMessage.append("[料號]必須填寫!<br />");
		}
		return warnMessage;
	}
	
	public static boolean validate(PrForm prForm, Person person,GlobalGrowl message) {
		Pr pr = prForm.getPr();
		List<Prline> prlines=prForm.getListPrline();
		Map<String,Prline> prlineMap=new HashedMap();
		LocationSiteMapBO locationSiteMapBO = SpringContextUtil.getBean(LocationSiteMapBO.class);
		PrlineDisableSumcounterBO prlineDisableSumcounterBO=SpringContextUtil.getBean(
				PrlineDisableSumcounterBO.class);
		// ============================================================
		LocationSiteMap locationSiteMap = Utility.nvlEntity(
				locationSiteMapBO.getLocationSiteMapBySiteId(pr.getSiteid()),LocationSiteMap.class);

		//========================主+替不驗證清單========================
		String disableSumcounter=prlineDisableSumcounterBO.getValidatePrline(
				prForm.getListPrline(), pr.getSiteid(), pr.getMDept()).toString();
		//=========SSTOCK是否可以多選=================
		String sstockFlag=prForm.getPrControlConfig()!=null?
				prForm.getPrControlConfig().getSstockFlag():ActiveFlag.N.name();
		//=====================================header驗證=====================================
		StringBuffer warnMessage = new StringBuffer();

		warnMessage = ApplyPrValidationUtils.PrValidate(prForm, person, warnMessage); 
		
		if (prlines.size() == 0) {
			warnMessage.append("請填寫[申請項目]再送出!<br />");
		}

		List<String> itemList = new ArrayList<String>();

		for (Prline lp : prlines) {
			itemList.add(lp.getItemnum());
			prlineMap.put(lp.getItemnum(), lp);
			
			if (StringUtils.isBlank(lp.getItemnum())) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ "： [料號] 必須選取!<br />");
				break;
			}
			
			if (lp.getStoreCategory().equals(SystemConfig.ZERS)
					&& StringUtils.isBlank(lp.getStoreroom())) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ "： [倉庫] 必須選取!<br />");
			}
			
			if (StringUtils.isBlank(lp.getCmoremark())) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ "：[用途]必須填寫!<br />");

			}
			
			if (lp.getStoreCategory().equals(SystemConfig.ZHIB)) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ " ：費用類料號請至EPMALL請購 !<br />");
			}

			if (!pr.getStatus().equals(SignStatus.APPR.toString()) && 
					lp.getReqdeliverydate() != null
					&& lp.getReqdeliverydate().compareTo(new Date()) <= 0) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ " ：[需求日期] 必須大於今天!<br />");

			}
			if (lp.getUnitcost().compareTo(new BigDecimal("0")) != 0) {
				if (lp.getUnitcost().compareTo(lp.getOriunitcost()) != 0
						&& StringUtils.isBlank(lp.getChangeUnitcostRemark())) {
					warnMessage.append("項次" + lp.getPrlinenum()
							+ " ：[異動最新單價] 必須說明!<br />");

				}
			} else {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ " ：[異動最新單價] 不可為0!<br />");
			}

			if (lp.getDeliverytime() != null
					&& lp.getDeliverytime().compareTo(new BigDecimal("0")) != 0) {
				if (lp.getDeliverytime().compareTo(lp.getOrideliverytime()) != 0
						&& StringUtils
								.isBlank(lp.getChangeDeliverytimeRemark())) {
					warnMessage.append("項次" + lp.getPrlinenum()
							+ " ：[異動Lead Time] 必須說明!<br />");

				}
			} else {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ " ：[異動Lead Time] 不可為0!<br />");
			}
			if (prForm.isEqFlag() && StringUtils.isBlank(lp.getEqId())) {
				warnMessage.append("項次" + lp.getPrlinenum()
						+ " ：[使用機台] 必須選取!<br />");
			}
			
			if (pr.getPrtype().equals(PrType.R1CONTROL.name())) { // R1耗材控管
				warnMessage = ApplyPrValidationUtils.PrLineValidateR1CONTROL(lp,
						new PrMcParameter(pr.getPrtype()),disableSumcounter,warnMessage);
			} else if (pr.getPrtype().equals(PrType.R2CONTROL.name())) { // R2需求控管
				//=========SSTOCK是否可以多選=================
				if(sstockFlag.equals(ActiveFlag.Y.name())){
					warnMessage = ApplyPrValidationUtils.PrLineValidateR2CONTROL2(
							locationSiteMap,pr, lp,
							new PrMcParameter(pr.getPrtype()),disableSumcounter,warnMessage);
				}else{
					warnMessage = ApplyPrValidationUtils.PrLineValidateR2CONTROL(pr, lp,
						new PrMcParameter(pr.getPrtype()),disableSumcounter,warnMessage);
				}
			} else if (pr.getPrtype().equals(PrType.R1PMREQ.name())) { // 非周期
				warnMessage = ApplyPrValidationUtils.validatePmeqqty(lp, warnMessage);
			} else if (pr.getPrtype().equals(PrType.R2PMREQ.name())) { // 保養需求
				warnMessage = ApplyPrValidationUtils.PrLineValidateR2PMREQ(pr, lp,warnMessage);
				warnMessage = ApplyPrValidationUtils.validatePmeqqty(lp, warnMessage);
			} else {
				if(!Utility.equalsOR(pr.getPrtype(), PrType.R1REORDER,PrType.R2REORDER))
					warnMessage = ApplyPrValidationUtils.validatePmeqqty(lp, warnMessage);
			}
		}
		
		// ===============================倉別驗證=======================
		warnMessage = ApplyPrValidationUtils.validatePrlineByDefaultStore(prlines, warnMessage);

		// =============================庫存分類=========================
		warnMessage = ApplyPrValidationUtils.validateStroageCategory(prlines, warnMessage);
				
		// =============================區域判斷==========================
		// 2014/02/07 94 暫不做區域判斷
		// 2016/05/09 取消部門驗證FLAG=Y 不做區域判斷
		if(!ObjectUtils.toString(pr.getIgnoreDeptFlag()).equals("Y")){
			warnMessage = ApplyPrValidationUtils.validateItemSiteInactvie(itemList,
					locationSiteMap.getLocationSite(), warnMessage);
		}

		// =============================料號狀態判斷==========================
		warnMessage = ApplyPrValidationUtils.validateNeStautsItem(itemList,
				ItemStatusType.TYPE_AC.getValue(),warnMessage);
		// ==========驗證料號是否有凍結中單據====================
		warnMessage.append(ApplyItemTransferSiteUtils.validateInprgTransfer(
				itemList,  pr.getSiteid(), LocationSiteActionType.S.name()));
		

		// =============================合法供應商判斷==========================
//		if(!pr.getPrtype().equals(PrType.R1REORDER.getKey()) &&
//			!pr.getPrtype().equals(PrType.R2REORDER.getKey()) ){
//			
//			warnMessage = ApplyPrValidationUtils.validateVendor(itemList,warnMessage);
//		}
		//==============================分類驗證==============================	
		if (pr.getStatus().equals(SignStatus.APPR.toString())){  //申請單為同意時進行拋轉驗證
			warnMessage = ApplyPrValidationUtils.PrReorderValidate(pr, person, warnMessage);
			//驗證數量是否符合料號主檔的moqmpq(只驗與主檔不相同的料號)
			if(!Utility.equalsOR(pr.getPrtype(), PrType.R1REORDER,PrType.R2REORDER))
				warnMessage = ApplyPrValidationUtils.validateIAByItemMoqMpq(prlines, prlineMap, warnMessage);
		}else{
			// ===============================驗證料號是否有重複=======================
			warnMessage = ApplyPrValidationUtils.validateItemList(itemList, warnMessage);
			// =============================控管簽核人員驗證=========================
			warnMessage=ApplyPrValidationUtils.validateMcSigner(itemList, 
					pr.getSiteid(), pr.getPrtype(), warnMessage);
			// =====驗證PRLINE的經濟訂購量,最小包裝數,異動Lead Time是否與設定檔一致==
			warnMessage = ApplyPrValidationUtils.validateIAByPrline(prlines, warnMessage);
			// =============================ClassstructurePrtype清單驗證==========================
			warnMessage = ApplyPrValidationUtils.validateClassstructurePrtype(itemList,pr.getPrtype(),
					warnMessage);
			
			if (pr.getPrtype().equals(PrType.R1CONTROL.name())) {
				warnMessage = ApplyPrValidationUtils.validatePrline(pr,itemList, warnMessage);// 申請流程
				warnMessage = ApplyPrValidationUtils.validateInvbalances(prlines,false, warnMessage); // 月用量
				warnMessage = ApplyPrValidationUtils.validateMatusetransHalf(prlines,true,warnMessage);// 領用資訊
				warnMessage = ApplyPrValidationUtils.validateSumofIssueCounter(prlines,pr.getMDept(),warnMessage); // 領用主+替料號驗證	
			} else if (pr.getPrtype().equals(PrType.R2CONTROL.name())) { // R2需求控管
				// 原重訂購量,原平均月耗用量,原最低安全存量
				warnMessage = ApplyPrValidationUtils.validateInvbalances(prlines,true, warnMessage); 
				warnMessage = ApplyPrValidationUtils.validatePrline(pr,itemList, warnMessage);// 申請流程
				warnMessage = ApplyPrValidationUtils.validateBsstrkhist(prlines, true,warnMessage);// 耗材資訊
				warnMessage = ApplyPrValidationUtils.validateInventory(prlines, warnMessage); // 廠區設定值
				warnMessage = ApplyPrValidationUtils.validateControl(prlines, warnMessage); // 控管模式設定值
				warnMessage = ApplyPrValidationUtils.validateSumofUseCounter(prlines,pr.getMDept(),warnMessage); // 耗用主+替料號驗證
				if(sstockFlag.equals(ActiveFlag.N.name())) //sstock 是否可以多選
					warnMessage = ApplyPrValidationUtils.validateSstock(itemList, pr.getSiteid(), warnMessage); //流程中SSTOCK
			} else if (pr.getPrtype().equals(PrType.R2PMREQ.name())) { // 保養需求
				warnMessage = ApplyPrValidationUtils.validateBsstrkhist(prlines, false,warnMessage); // 耗材資訊
				warnMessage = ApplyPrValidationUtils.validatePrlineVendor(prlines,warnMessage);// 選取供應商驗證
				warnMessage = ApplyPrValidationUtils.validatePrtypeBudget(prForm, warnMessage);// 預算驗證
				warnMessage = ApplyPrValidationUtils.validateInvetoryIdle(itemList,prForm.getLocationMap().getLocationSiteMap(), warnMessage);// 呆滯庫存
			}else if (pr.getPrtype().equals(PrType.R1PMREQ.name())) { // 保養需求
				warnMessage = ApplyPrValidationUtils.validateMatusetransHalf(prlines,false,warnMessage);// 領用資訊
			}
		}
		
		if (warnMessage.length() > 0) {
			String title=StringUtils.isNotBlank(pr.getPrnum())?"單號:"+pr.getPrnum()+" ":"";
			if(message!=null){
				message.addErrorMessage("<font color=red>"+title+"錯誤項目</font>", warnMessage.toString());
			}
			if(!pr.getStatus().equals(SignStatus.APPR.toString())){
				setErrorMap(prForm, warnMessage.toString());
			}
			return false;
		} else {
			return true;
		}
	}

	// =========================================PR簽核驗證=============================
	/*
	 * lineSizeV=true 代表要驗證申請項目數量
	 */
	public static StringBuffer prSignValidation(PrForm prForm,SignStatus signStatus,StringBuffer warnMessage) {
		if (signStatus.compareTo(SignStatus.TRANSFER)==0){
			if(prForm.getPersonForward() == null) {
				warnMessage.append("請填寫轉呈人員!<br />");
			}
		}
		if (signStatus.compareTo(SignStatus.CAN)==0||signStatus.compareTo(SignStatus.REJECT)==0){
			SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
			String status=bean.getParameterMap().get(signStatus.toString());
			if(StringUtils.isBlank(prForm.getSignComment())) {
				warnMessage.append("必須填寫"+status+"說明!<br />");
			}
			if(Utility.isNotEmpty(prForm.getListDeletePrline())) {
				warnMessage.append(status+"時，退回項目必須為空值!<br />");
			}			
		}
		
		if (signStatus.compareTo(SignStatus.APPR)==0 || signStatus.compareTo(SignStatus.TRANSFER)==0){
			if(prForm.getListPrline().size() == 0&&prForm.getListCombinePrline().size() == 0) {  
				warnMessage.append("簽核錯誤!! 申請項目不能為空值!<br />");
			}
			if(Utility.equalsOR(prForm.getPr().getPrtype(), PrType.R2CONTROL.name(),
					PrType.R1CONTROL.name())){
				warnMessage = ApplyPrValidationUtils.validateInvbalances(
						prForm.getListPrline(),
						prForm.getPr().getPrtype().equals(PrType.R2CONTROL.name())?true:false, 
						warnMessage); 
			}
		}
		
		for (Prline p : prForm.getListDeletePrline()) {
			if (StringUtils.isBlank(p.getCloseRemark())) {
				warnMessage.append("項次" + p.getPrlinenum()
						+ " ：刪除LINE必須填寫說明!<br />");

			}
		}
		for (Prline p : prForm.getListCombinePrline()) {
			if (StringUtils.isBlank(p.getCombineInplant())||p.getCombineIndate()==null) {
				warnMessage.append("項次" + p.getPrlinenum()
						+ " ：合併出貨LINE必須填寫入貨廠區及入貨日期!<br />");

			}
		}		
		return warnMessage;
	}
	// =========================================非週期pr轉單驗證=========================
	public static boolean apprValidate(Pr[] prs, Person person,
			GlobalGrowl message) {
		PrlineBO prlineBO=SpringContextUtil.getBean(PrlineBO.class);
		for(Pr p:prs){
			// ==================申請單驗證================================
			PrForm prForm=new PrForm();
			prForm.setPrs(prs);
			prForm.setPr(p);
			prForm.setListPrline(prlineBO.getPrlineList(p.getPrid(), SignStatus.OPEN));
			if(!validate(prForm, person, message)){
				return false;
			}
		}
		return true;
	}
}
