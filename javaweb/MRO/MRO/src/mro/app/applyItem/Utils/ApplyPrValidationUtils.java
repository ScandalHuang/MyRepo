package mro.app.applyItem.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.entity.PrMcParameter;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.BsstrkhistBO;
import mro.base.bo.ClassstructureBO;
import mro.base.bo.ClassstructureSignBO;
import mro.base.bo.ClassstructureSubinventoryBO;
import mro.base.bo.FunctionBO;
import mro.base.bo.InvbalancesBO;
import mro.base.bo.InventoryBO;
import mro.base.bo.InvvendorBO;
import mro.base.bo.ItemAttributeBO;
import mro.base.bo.ItemBO;
import mro.base.bo.MatusetransHalfBO;
import mro.base.bo.PersonBO;
import mro.base.bo.PrBO;
import mro.base.bo.PrControlConfigBO;
import mro.base.bo.PrlineBO;
import mro.base.bo.PrtypeActiveMemberBO;
import mro.base.entity.Bsstrkhist;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureSign;
import mro.base.entity.ClassstructureSubinventory;
import mro.base.entity.Invbalances;
import mro.base.entity.Inventory;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MatusetransHalf;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;
import mro.form.PrForm;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ApplyPrValidationUtils {
	// ==========================================Pr驗證=====================================================
	public static StringBuffer PrReorderValidate(Pr pr,Person person, StringBuffer warnMessage) {
		if (StringUtils.isBlank(pr.getLastSigner())) {
			warnMessage.append("申請單號" + pr.getPrnum() + "沒有轉單者!<br />");
		} else if (!pr.getLastSigner().equals(person.getPersonId())) {
			warnMessage.append("申請單號" + pr.getPrnum() + "的轉單者應為" + pr.getLastSigner() + "!<br />");
		}
		if (StringUtils.isNotBlank(pr.getTransferFlag()) && pr.getTransferFlag().equals("Y")) {
			warnMessage.append("申請單號" + pr.getPrnum() + "已轉至epmall!<br />");
		}
		return warnMessage;
	}
	// ==========================================Pr驗證=====================================================
	public static StringBuffer PrValidate(PrForm prForm, Person person,
			StringBuffer warnMessage) {
		Pr pr = prForm.getPr();
		if (StringUtils.isBlank(pr.getRequestedby2())) {
			warnMessage.append("[申請人]必須填寫!<br />");

		}
		if (StringUtils.isBlank(pr.getSiteid())) {
			warnMessage.append("[廠區]必須選取!<br />");

		}
		if (StringUtils.isBlank(pr.getPrtype())) {
			warnMessage.append("[申請單類型]必須選取!<br />");

		}
		if (prForm.isReasonFlag() && StringUtils.isBlank(pr.getReasoncode())) {
			warnMessage.append("[申請目的]必須選取!<br />");

		}
		//簽核同意 跟非HIGHLIGHT下修
		if (!pr.getStatus().equals(SignStatus.APPR.toString())
				&& (StringUtils.isBlank(pr.getIgnoreDeptFlag())||
						pr.getIgnoreDeptFlag().equals("N"))) {
			if (!pr.getMDept().equals(person.getMDeptCode())) {
				warnMessage.append("此張申請單部門與登入人資料不符!<br />");
			}

			// ===================================================================================
			PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
			Person requestPerson = personBO.getPerson(pr.getRequestedby2());

			if (!pr.getMDept().equals(requestPerson.getMDeptCode())) {
				warnMessage.append("[申請人]與[填寫人]部門名稱不同!<br />");

			} else if (!pr.getDeptsupervisor().equals(
					requestPerson.getSupervisor())) {
				warnMessage.append("[申請人]與[填寫人主管]不同，請開立新單!<br />");
			}
		}

		// ===============================限制開單人員清單======================================
		if(StringUtils.isBlank(warnMessage.toString())){
			PrtypeActiveMemberBO bo = SpringContextUtil.getBean(PrtypeActiveMemberBO.class);
			prForm.setLocationMap();
			if (bo.getCount(prForm.getLocationSiteMap(),pr.getPrtype()) > 0) {
				if (bo.getPrtypeActiveMember(prForm.getLocationSiteMap(),
						pr.getPrtype(),pr.getRequestedby()) == null) {
					SystemConfigBean bean = JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
					warnMessage.append("您無"+ bean.getParameterMap().get(pr.getPrtype())
							+ "申請權限!<br />");
				}

			}
		}
		return warnMessage;
	}

	// ==========================================R2PMREQ PRLINE 其他驗證=====================================================
	public static StringBuffer PrLineValidateR2PMREQ(Pr pr,Prline prline, StringBuffer warnMessage) {		
		if(pr.getPrtype().equals(PrType.R2PMREQ.name())){
			if (prline.getReqdeliverydate()==null) {
				warnMessage.append("項次" + prline.getPrlinenum()
						+ " ：需求日必須選取!<br />");
			}
		}
		if (StringUtils.isNotBlank(prline.getVendor()) && 
				StringUtils.isBlank(prline.getVendorRemark()) ) {
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：指定供應商說明必須填寫!<br />");
				
		}
				
		return warnMessage;
	}
	
	// ==========================================R1CONTROL PRLINE 其他驗證=====================================================
	public static StringBuffer PrLineValidateR1CONTROL(Prline prline,
			PrMcParameter prMcParameter,String disableSumcounter,StringBuffer warnMessage) {
		if (prline.getNewminlevel().compareTo(new BigDecimal("0"))==-1 ) {
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新建議月用量] 不能小於 0!<br />");
		}
		if (prline.getMinlevel().compareTo(new BigDecimal("0"))==0) {
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新增/下修月用量] 不能為 0!<br />");
		}
		if (prline.getNewminlevel().compareTo(new BigDecimal("0"))==1 ) {
			if(prline.getNewminlevel().subtract(prline.getNewminlevel().divideToIntegralValue(
					prline.getMcMinPackageQuantity()).multiply(prline.getMcMinPackageQuantity())).doubleValue()!=0){
				warnMessage.append("項次" + prline.getPrlinenum()
						+ " ：開單數量應為最小包裝量之乘數!<br />");
			}
		}
		
		//============================月用量===================================================
		if(prline.getNewminlevel().intValue()>0 &&
			disableSumcounter.indexOf(prline.getItemnum())==-1 &&
			(prline.getNewminlevel().doubleValue()>
				prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()  ||
				prline.getNewminlevel().doubleValue()<
				prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue())
			&& StringUtils.isBlank(prline.getReqmark2())){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新建議月用量]>"+prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()+" 或 "
					+ "< "+prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue()+"，[說明 ] 欄位必須填寫!<br />");
		}
		if(prline.getMinlevel().doubleValue()>0 && 
			disableSumcounter.indexOf(prline.getItemnum())==-1 &&
			prline.getSumofissuecounter().compareTo(prMcParameter.getmFrequenceLimit())==-1){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：近半年領用月次數[主+替] >= "+prMcParameter.getmFrequenceLimit()+"，"
							+ "才能進行  [新增/下修月用量]!<br />");
		}
		return warnMessage;
	}
	
	 /*
	  * 驗證料號是否有重複
	  */
	public static StringBuffer validateItemList(List<String> itemList, StringBuffer warnMessage) {
		ArrayList<String> uniqItemList= new ArrayList<String>(new  LinkedHashSet<String>(itemList));
		for(String itemnum : uniqItemList){
	    	if(Collections.frequency(itemList, itemnum)>1)
	    		warnMessage.append("料號" + itemnum+ "重複申請!<br />");
		}
		return warnMessage;
	}
	// ==========================================非控管 PRLINE 其他驗證=====================================================
	public static StringBuffer validatePmeqqty(Prline prline, StringBuffer warnMessage) {
		return validatePmeqqty(prline.getItemnum(),prline.getPmreqqty(),
				prline.getMcMinPackageQuantity(),prline.getMcOrderQuantity(),warnMessage);
	}
	public static StringBuffer validatePmeqqty(String itemnum,BigDecimal pmreqqty,
			BigDecimal mcMinPackageQuantity,BigDecimal McOrderQuantity, StringBuffer warnMessage) {
		if (pmreqqty==null || pmreqqty.compareTo(new BigDecimal("0"))==0) {
			warnMessage.append("料號" + itemnum
					+ " ：[需求量]不能為0!<br />");
		}else{					
			if(pmreqqty.subtract(pmreqqty.divideToIntegralValue(
					mcMinPackageQuantity).multiply(mcMinPackageQuantity)).doubleValue()!=0){
				warnMessage.append("料號" + itemnum
						+ " ：需求量("+pmreqqty+")應為最小包裝量("+mcMinPackageQuantity+")之乘數!<br />");
			}
			
			if(pmreqqty.doubleValue()<McOrderQuantity.doubleValue()){
				warnMessage.append("料號" + itemnum
						+ " ：需求量("+pmreqqty+")需>=經濟訂購量("+McOrderQuantity+")!<br />");
			}
		}
		
		return warnMessage;
	}
	// ==========================================R2CONTROL PRLINE 其他驗證=====================================================
	public static StringBuffer PrLineValidateR2CONTROL(Pr pr,Prline prline,
			PrMcParameter prMcParameter,String disableSumcounter, StringBuffer warnMessage) {		
		if(prline.getDavguseqty().doubleValue()==0 && prline.getSstock().doubleValue()==0){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ： 新增/下修 平均月耗用量與最低安全存量不得同時為0!<br />");
		}
		if(StringUtils.isBlank(prline.getCmommcontrol())){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ： 控管模式不得為空值，請重新選取料號，若還是為空值請通知物管進行設定!<br />");
		}
		//============================月用量===================================================
		if(prline.getNewavguseqty().intValue()>0 &&
			disableSumcounter.indexOf(prline.getItemnum())==-1 &&
			(prline.getNewavguseqty().doubleValue()>
				prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()  ||
				prline.getNewavguseqty().doubleValue()<
				prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue())
			&& StringUtils.isBlank(prline.getReqmark2())){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新平均月耗用量]>"+prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()+" 或 "
					+ "< "+prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue()+"，[說明 ] 欄位必須填寫!<br />");
		}
		if((prline.getSstock().compareTo(new BigDecimal("0"))==1 ||
				prline.getDavguseqty().compareTo(new BigDecimal("0"))==1)	&&
				prline.getDavguseqty().doubleValue()>0 && 
				disableSumcounter.indexOf(prline.getItemnum())==-1 &&
				prline.getSumofusecounter().compareTo(prMcParameter.getmFrequenceLimit())==-1){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：近半年耗用月次數[主+替] >= "+prMcParameter.getmFrequenceLimit()+"，"
							+ "才能進行  新增/下修 平均月耗用量!<br />");
		}
		//===================================新 最低安全存量===============================================

		BigDecimal minValue=prline.getMaxhdqtyThree().subtract(prline.getAvghdqtyThree()).min(
				prline.getAvghdqtyThree());
		if(minValue.doubleValue()<1 && prline.getAvghdqtyThree().doubleValue()<1)
			minValue=new BigDecimal("1");
		if((prline.getSstock().compareTo(new BigDecimal("0"))==1 ||
				prline.getDavguseqty().compareTo(new BigDecimal("0"))==1)	&&
			prline.getNewsstock().doubleValue()>0 && 
			prline.getSumofusecounter().compareTo(prMcParameter.getmFrequenceLimit())==-1){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新 最低安全存量] >0 時，近半年耗用月次數[主+替]必須 >= "+ 
					prMcParameter.getmFrequenceLimit()+"!<br />");
		}else if(prline.getNewsstock().doubleValue()>0 && 
			prline.getNewsstock().doubleValue()>minValue.doubleValue()){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新 最低安全存量] >0 時，新最低安全存量須<="+minValue+"<br />");
		}
		
		if (prline.getNewavguseqty().compareTo(new BigDecimal("0"))==-1) {
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新平均月耗用量] 不能小於 0!<br />");
		}
		if (prline.getNewsstock().compareTo(new BigDecimal("0"))==-1) {
			warnMessage.append("項次" + prline.getPrlinenum()
						+ " ：[新最低安全存量] 不能小於 0!<br />");
		}
		if (prline.getNewminlevel().doubleValue()<0 ) {
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新 重訂購量 ] 不能小於 0!<br />");
		}
		//2016/1/22取消
//		else if (prline.getNewminlevel().doubleValue()>0 ) {
//			if(prline.getNewminlevel().subtract(prline.getNewminlevel().divideToIntegralValue(
//					prline.getMcMinPackageQuantity()).multiply(prline.getMcMinPackageQuantity())).doubleValue()!=0){
//				warnMessage.append("項次" + prline.getPrlinenum()
//						+ " ：新重訂購量應為最小包裝量之乘數!<br />");
//			}
//		}
		
		
		return warnMessage;
	}
	 //T0~T2 及N廠區 與南廠現行控管申請單差異
	public static StringBuffer PrLineValidateR2CONTROL2(LocationSiteMap locationSiteMap,Pr pr,Prline prline,
			PrMcParameter prMcParameter,String disableSumcounter, StringBuffer warnMessage) {
		if(prline.getDavguseqty().doubleValue()==0 && prline.getSstock().doubleValue()==0){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ： 新增/下修 平均月耗用量與最低安全存量不得同時為0!<br />");
		}
		if(StringUtils.isBlank(prline.getCmommcontrol())){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ： 控管模式不得為空值，請重新選取料號，若還是為空值請通知物管進行設定!<br />");
		}
		//============================月用量===================================================
		if(prline.getNewavguseqty().intValue()>0 &&
			disableSumcounter.indexOf(prline.getItemnum())==-1 &&
			(prline.getNewavguseqty().doubleValue()>
				prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()  ||
				prline.getNewavguseqty().doubleValue()<
				prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue())
			&& StringUtils.isBlank(prline.getReqmark2())){
			warnMessage.append("項次" + prline.getPrlinenum()
					+ " ：[新平均月耗用量]>"+prline.getAvghdqtyThree().multiply(prMcParameter.getMaxRemarkRange()).doubleValue()+" 或 "
					+ "< "+prline.getAvghdqtyThree().multiply(prMcParameter.getMinRemarkRange()).doubleValue()+"，[說明 ] 欄位必須填寫!<br />");
		}
		if(disableSumcounter.indexOf(prline.getItemnum())==-1){
			if(prline.getSumofusecounter().compareTo(new BigDecimal("0"))==0){
				if (prline.getNewminlevel().doubleValue()!=0) {
					warnMessage.append("項次" + prline.getPrlinenum()
							+ " ：近半年耗用月次數[主+替]為 0時，[新 重訂購量] 只能為0!<br />");
				}
				if (prline.getNewsstock().doubleValue()!=0) {
					warnMessage.append("項次" + prline.getPrlinenum()
							+ " ：近半年耗用月次數[主+替]為 0時，[新 最低安全存量] 只能為0!<br />");
				}
			}else if(prline.getSumofusecounter().intValue()<3){
				if (prline.getDavguseqty().doubleValue()>0) {
					warnMessage.append("項次" + prline.getPrlinenum()
							+ " ：近半年耗用月次數[主+替]為1-2次且物控模式非補滿半年單與補滿季單，"
							+ "[平均月耗用量] 僅可為0或負值!<br />");
				}
			}
		}
		return warnMessage;
	}
	
	
	public static StringBuffer validatePrline(Pr pr,List<String> itemList, StringBuffer warnMessage){
		PrlineBO prlineBO =SpringContextUtil.getBean(PrlineBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Prline> prlines=prlineBO.getPrlineList(itemList,pr.getMDept(),pr.getPrtype(),
					pr.getSiteid(),SignStatus.INPRG,SignStatus.OPEN);
			for(Prline prline:prlines){
				warnMessage.append("料號" + prline.getItemnum() + 
						" 已在申請("+prline.getPrnum()+")流程中!<br />");
			}
		}
		
		return warnMessage;
	}

	public static StringBuffer validateInvbalances(List<Prline> prlines,boolean controlFlag,StringBuffer warnMessage){
		InvbalancesBO invbalancesBO =SpringContextUtil.getBean(InvbalancesBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Invbalances> invbalancesList=invbalancesBO.validateInvbalancesByPrline(prlines,controlFlag);
			for(Invbalances invbalances:invbalancesList){
				warnMessage.append("料號" + invbalances.getItemnum()
						+"[控管資訊]錯誤，請重新選取料號或開單，以取得正確[控管資訊]!<br />");
			}
		}
		
		return warnMessage;
	}
	
	public static StringBuffer validateBsstrkhist(List<Prline> prlines,boolean costFlag, StringBuffer warnMessage){
		BsstrkhistBO bsstrkhistBO =SpringContextUtil.getBean(BsstrkhistBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Bsstrkhist> list=bsstrkhistBO.validateBsstrkhistByPrline(prlines, costFlag);
			for(Bsstrkhist bsstrkhist:list){
				warnMessage.append("料號" + bsstrkhist.getItemnum()
						+ "[耗用資訊]錯誤，請重新選取料號或開單，以取得正確[耗用資訊]!<br />");
			}
		}
		
		return warnMessage;
	}
	public static StringBuffer validateMatusetransHalf(List<Prline> prlines,boolean controlFlag,StringBuffer warnMessage){
		MatusetransHalfBO matusetransHalfBO=SpringContextUtil.getBean(MatusetransHalfBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<MatusetransHalf> list=matusetransHalfBO.validateByPrline(prlines,controlFlag);
			for(MatusetransHalf MatusetransHalf:list){
				warnMessage.append("料號" + MatusetransHalf.getItemnum()
						+ "[領用資訊]錯誤，請重新選取料號或開單，以取得正確[領用資訊]!<br />");
			}
		}
		
		return warnMessage;
	}
	public static StringBuffer validateInventory(List<Prline> prlines, StringBuffer warnMessage){
		InventoryBO inventoryBO =SpringContextUtil.getBean(InventoryBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Inventory> list=inventoryBO.validateInventoryByPrline(prlines);
			for(Inventory inventory:list){
				warnMessage.append("料號" + inventory.getItemnum()
						+ "[廠區設定值]錯誤，請重新選取料號或開單，以取得正確[廠區設定值]!<br />");
			}
		}
		
		return warnMessage;
	}
	
	public static StringBuffer validateControl(List<Prline> prlines, StringBuffer warnMessage){
		if(Utility.isNotEmpty(prlines)){
			PrControlConfigBO prControlConfigBO=SpringContextUtil.getBean(PrControlConfigBO.class);
			PrControlConfig prControlConfig=prControlConfigBO.getPrControlConfigBySiteId(prlines.get(0).getItemnum(), prlines.get(0).getSiteid());
			if(prControlConfig==null) {
				warnMessage.append("該廠區未設定控管模式，請通知物管設定!<br />");
			}else{
				ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
				List<Item> items=itemBO.notInventory(prlines);
				if(CollectionUtils.isNotEmpty(items)){
					Map map=prlines.stream().collect(Collectors.toMap(Prline::getItemnum, Prline::getCmommcontrol));
					items.stream().filter(l->!map.get(l.getItemnum()).equals(prControlConfig.getControlType()))
					.forEach(i->warnMessage.append("料號" + i.getItemnum()
							+ "[控管模式]錯誤，請重新選取料號或開單，以取得正確[控管模式設定值]!<br />"));
				}
			}
		}
		
		return warnMessage;
	}
	
	public static StringBuffer validateSumofUseCounter(List<Prline> prlines,String deptcode, StringBuffer warnMessage){
		FunctionBO functionBO =SpringContextUtil.getBean(FunctionBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Item> list=functionBO.validateSumofUseCounterByPrline(prlines, deptcode);
			for(Item item:list){
				warnMessage.append("料號" + item.getItemnum()
						+ "[近半年耗用月次數(主+替)]錯誤，請重新選取料號或開單，以取得正確次數!<br />");
			}
		}
		
		return warnMessage;
	}
	public static StringBuffer validateSumofIssueCounter(List<Prline> prlines,String deptcode, StringBuffer warnMessage){
		FunctionBO functionBO =SpringContextUtil.getBean(FunctionBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Item> list=functionBO.validateSumofIssueCounterByPrline(prlines, deptcode);
			for(Item item:list){
				warnMessage.append("料號" + item.getItemnum()
						+ "[近半年領用月次數(主+替)]錯誤，請重新選取料號或開單，以取得正確次數!<br />");
			}
		}
		
		return warnMessage;
	}
	
	public static StringBuffer validateStroageCategory(List<Prline> prlines, StringBuffer warnMessage){
		ClassstructureBO classstructureBO=SpringContextUtil.getBean(ClassstructureBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Classstructure> storeValidateList=classstructureBO.validatePrlineByStorage(prlines);
			if(Utility.isNotEmpty(storeValidateList)){
				for(Classstructure classstructure:storeValidateList){
					warnMessage.append("類別結構" + classstructure.getClassificationid()
							+ "： [庫存分類] 錯誤，請重新選取料號或開單，以取得正確[庫存分類]!<br />");
				}
			}
		}
		
		return warnMessage;
	}
	public static StringBuffer validatePrlineByDefaultStore(List<Prline> prlines, StringBuffer warnMessage){
		ClassstructureSubinventoryBO bo=SpringContextUtil.getBean(ClassstructureSubinventoryBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<ClassstructureSubinventory> list=bo.validatePrlineBySubinventory(prlines);
			if(Utility.isNotEmpty(list)){
				for(ClassstructureSubinventory c:list){
					warnMessage.append("類別結構" + c.getClassstructureid()
							+ " ：預設倉別有誤，請洽該廠物管負責人員處理 !<br />");
				}
			}
		}
		
		return warnMessage;
	}
	/*
	 * hongjie.wu
	 * 驗證PRLINE的經濟訂購量,最小包裝數,異動Lead Time是否與設定檔一致==
	 */
	public static StringBuffer validateIAByPrline(List<Prline> prlines, StringBuffer warnMessage){
		ItemAttributeBO itemAttributeBO=SpringContextUtil.getBean(ItemAttributeBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<ItemAttribute> validateList=itemAttributeBO.validateItemAttributeByPrline(prlines);
			if(Utility.isNotEmpty(validateList)){
				for(ItemAttribute itemAttribute:validateList){
					warnMessage.append("料號" + itemAttribute.getItem().getItemnum()
							+ " 屬性與料號主檔不同，請重新開立此料號!<br />");
				}
			}
		}
		
		return warnMessage;
	}
	
	/* hongjie.wu
	 * 2015/05/11
	 * 驗證數量是否符合料號主檔的moqmpq(只驗與主檔不相同的料號)
	 * 
	 */
	public static StringBuffer validateIAByItemMoqMpq(List<Prline> prlines,
			Map<String,Prline> prlineMap, StringBuffer warnMessage){
		ItemAttributeBO itemAttributeBO=SpringContextUtil.getBean(ItemAttributeBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<ItemAttribute> validateList=itemAttributeBO.validateItemAttributeByPrline(prlines);
			if(Utility.isNotEmpty(validateList)){
				for(ItemAttribute itemAttribute:validateList){
					warnMessage=validatePmeqqty(itemAttribute.getItem().getItemnum(), 
							prlineMap.get(itemAttribute.getItem().getItemnum()).getPmreqqty(),
							itemAttribute.getMcMinPackageQuantity(), 
							itemAttribute.getMcOrderQuantity(), warnMessage);
				}
			}
		}
		
		return warnMessage;
	}
	
	public static StringBuffer validateItemSiteInactvie(List<String> itemList,
			String locationSite, StringBuffer warnMessage){
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Item> inactiveItemList=itemBO.getInactiveItemSiteBySite(itemList, locationSite);
			if(Utility.isNotEmpty(inactiveItemList)){
				for(Item item:inactiveItemList){
						warnMessage.append("料號"+ item.getItemnum() + "未在"
								+ locationSite+ "區域生效 !<br />");
				}
			}
		}
		return warnMessage;
	}
	public static StringBuffer validateItemSiteActive(List<String> itemList,
			String locationSite, StringBuffer warnMessage){
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Item> inactiveItemList=itemBO.getActiveItemSiteBySite(itemList, locationSite);
			if(Utility.isNotEmpty(inactiveItemList)){
				for(Item item:inactiveItemList){
						warnMessage.append("料號"+ item.getItemnum() + "已在"
								+ locationSite+ "區域生效 !<br />");
				}
			}
		}
		return warnMessage;
	}
	public static StringBuffer validateNeStautsItem(List<String> itemList, 
			List<String> status,StringBuffer warnMessage){
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Item> neItemList=itemBO.getNeStautsItem(itemList, status);
			if(Utility.isNotEmpty(neItemList)){
				for(Item item:neItemList){
					warnMessage.append("料號"+item.getItemnum()+"狀態必須為"+status.toString()
							+ "，目前狀態為(" + item.getStatus() + ") !<br />");
				}
			}
		}
		return warnMessage;
	}
	
	public static StringBuffer validateVendor(List<String> itemList,StringBuffer warnMessage){
		InvvendorBO invvendorBO=SpringContextUtil.getBean(InvvendorBO.class);
		if(Utility.isNotEmpty(itemList)){
			Map vendorMap=invvendorBO.getActiveVendorCountByItem(itemList);
			for(String itemnum:itemList){
				if(vendorMap.get(itemnum)==null || (int)vendorMap.get(itemnum)==0){
					warnMessage.append("料號" + itemnum + "在 Oracle 無合格供應商!" +
							"請進行料號主檔維護，再申請此料號!<br />");
				}
			}
		}
		return warnMessage;
	}
	public static StringBuffer validatePrlineVendor(List<Prline> prlines,StringBuffer warnMessage){
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		if(Utility.isNotEmpty(prlines)){
			List<Item> list=itemBO.validateVmVendorByPrline(prlines);
			for(Item item:list){
				warnMessage.append("料號" + item.getItemnum() + "所選取的供應商非合格供應商" +
						"或供應商已被移除!請進行料號主檔維護，再申請此料號!<br />");
			}
		}
		return warnMessage;
	}
	//只要最新安全存量或異動的安全存量 !=0  就驗證是不是有送審中的申請單
	public static StringBuffer validateSstock(List<String> itemList,String siteid,StringBuffer warnMessage){
		PrlineBO prlineBO=SpringContextUtil.getBean(PrlineBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Prline> list=prlineBO.validateSstock(itemList,siteid);
			for(Prline prline:list){
				warnMessage.append("料號" + prline.getItemnum() + 
						"在該廠區的廠區設定值 已在申請流程中!<br />");
			}
		}
		return warnMessage;
	}
	//物管簽核人員驗證
	public static StringBuffer validateMcSigner(List<Prline> prlines,Pr pr,StringBuffer warnMessage){
		List<String> itemList=new ArrayList<String>();
		for(Prline prline:prlines){
			itemList.add(prline.getItemnum());
		}
		return validateMcSigner(itemList, pr.getSiteid(), pr.getPrtype(), warnMessage);
	}
	//物管簽核人員驗證
	public static StringBuffer validateMcSigner(List<String> itemList,String siteId,
			String prtype,StringBuffer warnMessage){
		ClassstructureSignBO classstructureSignBO=SpringContextUtil.getBean(ClassstructureSignBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<ClassstructureSign> list=classstructureSignBO.getClassstructureSignByItem(itemList, siteId);
			LinkedHashSet<String> signer=new  LinkedHashSet<String>();
			for(ClassstructureSign classstructureSign:list){
				if(prtype.equals(PrType.R1CONTROL.name()) || prtype.equals(PrType.R2CONTROL.name())){
					signer.add(classstructureSign.getControlMcEmpno());
				}else{
					signer.add(classstructureSign.getPmreqMcEmpno());
				}
			}
			if(signer.size()>1){
				warnMessage.append("此張申請單[物管]簽核有誤，無法送簽!<br />");
			}
		}
		return warnMessage;
	}
	//prline 不存在ClassstructurePrtype 清單
	public static StringBuffer validateClassstructurePrtype(List<String> itemList,String prtype,
			StringBuffer warnMessage){
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		if(Utility.isNotEmpty(itemList)){
			List<Item> list=itemBO.validateClassstructurePrtype(itemList, prtype);
			for(Item item:list){
				warnMessage.append("料號" + item.getItemnum() + 
						"在此申請單類型限制請購!<br />");
			}
		}
		return warnMessage;
	}
	//prline 不存在ClassstructurePrtype 清單
	public static StringBuffer validatePrtypeBudget(PrForm prForm,
			StringBuffer warnMessage){

		if(prForm.getUnUseBudget()!=null){
			BigDecimal unuseBudget=prForm.getUnUseBudget().subtract(prForm.getPr().getTotalbasecost2());
			if(unuseBudget.compareTo(new BigDecimal("0"))==-1){
				warnMessage.append("申請單金額超出預算("+prForm.getUnUseBudget()+
						")<br />");
			}
		}
		return warnMessage;
	}
	//prline 不存在ClassstructurePrtype 清單
	public static StringBuffer validateInvetoryIdle(List items,LocationSiteMap locationSiteMap,
			StringBuffer warnMessage){
		if(CollectionUtils.isNotEmpty(items) && locationSiteMap!=null){
			InventoryBO bo=SpringContextUtil.getBean(InventoryBO.class);
			List<Inventory> list=bo.getInventoryIdleList(items, locationSiteMap);
			list.forEach(l->warnMessage.append("料號" + l.getItemnum() + 
					"在廠區("+l.getSiteid()+")倉碼("+l.getLocation()+")有庫存("+l.getStock()+")可先使用!<br />"));
		}
		return warnMessage;
	}
	public static StringBuffer validatePrPoTransfer(Pr pr,boolean transferFlag,
			StringBuffer warnMessage){
		if(transferFlag){ //已經拋轉過，但要關閉
			PrlineBO prlineBO=SpringContextUtil.getBean(PrlineBO.class);
			if(prlineBO.getPrlineCount(pr.getPrid(), transferFlag)>0){
				warnMessage.append("申請單中有已Approve PR或已轉PO的PRLINE!!<br />");
			}
		}
		return warnMessage;
	}
}
