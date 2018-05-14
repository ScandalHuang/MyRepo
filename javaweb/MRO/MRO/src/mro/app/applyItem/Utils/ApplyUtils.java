package mro.app.applyItem.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.bo.ApplyItemChangeBo;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.bo.ListAlndomainBO;
import mro.app.commonview.bo.ListClassspecBO;
import mro.app.commonview.bo.ListClassstructureBO;
import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.app.commonview.vo.ListClassspecVO;
import mro.app.sign.bo.ApplyItemSignBo;
import mro.app.util.StringUtilsConvert;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.Eaudittype;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AItemSimpleBO;
import mro.base.bo.AItemspecBO;
import mro.base.bo.AlndomainBO;
import mro.base.bo.AlndomainCommonBO;
import mro.base.bo.ClassstructureBO;
import mro.base.bo.FunctionBO;
import mro.base.bo.ItemAttributeBO;
import mro.base.bo.ItemMappingBO;
import mro.base.bo.ItemSiteBO;
import mro.base.bo.ItemspecBO;
import mro.base.bo.MeasureunitDomainBO;
import mro.base.entity.AItem;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.Alndomain;
import mro.base.entity.Classstructure;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemMapping;
import mro.base.entity.ItemSite;
import mro.base.entity.Itemspec;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.ItemForm;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.ActiveFlag;

public class ApplyUtils {
	// ===========================新增分類屬性==============================
	public static Map<String, Map<String,String>> onDomainSet(AItem aItem) {
		AlndomainBO alndomainBO = SpringContextUtil.getBean(AlndomainBO.class);
		AlndomainCommonBO alndomainCommonBO=SpringContextUtil.getBean(AlndomainCommonBO.class);
		Map<String, Map<String,String>> domainMap = alndomainBO.getMap(aItem.getClassstructureid(), true);
		Iterator<Entry<String, Map<String, String>>> iterator=alndomainCommonBO.getMap(
				aItem.getCommoditygroup(),aItem.getClassstructureid()).entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Map<String,String>> entry=iterator.next();
			if(domainMap.get(entry.getKey())==null){
				domainMap.put(entry.getKey(), new HashMap<String, String>());
			}
			domainMap.get(entry.getKey()).putAll(entry.getValue());
		}

		return domainMap;
	}

	// ===========================取得classpec==============================
	public static List<ListAItemspecVO> getAItemSpec(AItem aItem) {
		ListClassspecBO listClassspecBO = SpringContextUtil.getBean(ListClassspecBO.class);
		AItemspecBO aItemspecBO = SpringContextUtil.getBean(AItemspecBO.class);
		ItemspecBO itemspecBO = SpringContextUtil.getBean(ItemspecBO.class);
		
		List<ListClassspecVO> listClassspecVO=listClassspecBO.getListClassspec(
				aItem.getClassstructureid(), null, true);
		Map specMap=null;
		List<ListAItemspecVO> listAItemspecVO = new LinkedList<ListAItemspecVO>();
		for(ListClassspecVO list:listClassspecVO){
			ListAItemspecVO aItemspecVO=new ListAItemspecVO();
			BeanUtils.copyProperties(list, aItemspecVO);
			aItemspecVO.setDisplaysequence(list.getItemsequence());
			
			//================================================================
			if(aItem.getEaudittransid()!=null){//A_item,重新設定資料 避免attribute有被更動(料號新增、規格異動)
				if(specMap==null){
					specMap=aItemspecBO.getMap(aItem.getEaudittransid(), aItem.getClassstructureid());
				}
				AItemspec aItemspec=Utility.nvlEntity(specMap.get(list.getAssetattrid()), AItemspec.class);
				aItemspecVO.setAlnvalue(aItemspec.getAlnvalue());
				aItemspecVO.setNumvalue(aItemspec.getNumvalue());
				aItemspecVO.setMeasureunitid(aItemspec.getMeasureunitid());
			}else if(aItem.getItem()!=null){//item,重新設定資料 避免attribute有被更動(規格異動異動申請)
				if(specMap==null){
					specMap=itemspecBO.getMap(aItem.getItem().getItemid(), aItem.getClassstructureid());
				}
				Itemspec itemspec=Utility.nvlEntity(specMap.get(list.getAssetattrid()), Itemspec.class);
				aItemspecVO.setAlnvalue(itemspec.getAlnvalue());
				aItemspecVO.setNumvalue(itemspec.getNumvalue());
				aItemspecVO.setMeasureunitid(itemspec.getMeasureunitid());
			}
			//=================================aln 與num  更新==========================
			if(StringUtils.isNotBlank(aItemspecVO.getDatatype())){
				if(aItemspecVO.getDatatype().equals(SystemConfig.aln) && StringUtils.isBlank(aItemspecVO.getAlnvalue())){
					aItemspecVO.setAlnvalue(aItemspecVO.getNumvalue()!=null?aItemspecVO.getNumvalue().toString():"");
				}
				if(aItemspecVO.getDatatype().equals(SystemConfig.numeric) && aItemspecVO.getNumvalue()==null){
					aItemspecVO.setNumvalue(NumberUtils.isNumber(aItemspecVO.getAlnvalue())?
								new BigDecimal(NumberUtils.createNumber(
										aItemspecVO.getAlnvalue()).toString()):null);
				}
			}
			listAItemspecVO.add(aItemspecVO);
		}
		return listAItemspecVO;
	}
	// ================================取IR料號=========================================================
	public static Object[] getIRItemNum(AItem aItem) {
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		Object[] s = new Object[2];
		if (aItem.getEaudittransid() == null) {
			s[0] = functionBO.getSquence("EAUDITSEQ"); // 取的新的Eaudittransid
			s[1] = "IR-" + functionBO.getSquence("A_ITEM_SEQ"); // 取的新的ItemNum
		} else {
			s[0] = aItem.getEaudittransid();
			s[1] = aItem.getItemnum();
		}
		return s;
	}

	// ================================取料號=========================================================
	public static String getItemNum(ItemForm itemForm) {
		String itemNum = itemForm.getaItem().getClassstructureid(); // 料號
		ApplyItemSignBo applyItemSignBo = SpringContextUtil
				.getBean(ApplyItemSignBo.class);
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		ListAlndomainBO ListAlndomainBO = SpringContextUtil
				.getBean(ListAlndomainBO.class);
		ListClassstructureBO listClassstructureBO = SpringContextUtil
				.getBean(ListClassstructureBO.class);
		Classstructure classstructure = listClassstructureBO
				.getClassstructure(itemForm.getaItem().getClassstructureid());
		if (classstructure.getType().equals("1")) {
			AItemspec aItemspec = applyItemSignBo.getAItemspec(
					itemForm.getaItem().getClassstructureid(), SystemConfig.meterialName,
					itemForm.getaItem().getEaudittransid());
			Alndomain alndomain = ListAlndomainBO.getAlndomain(
					itemForm.getaItem().getClassstructureid() + SystemConfig.meterialName,
					aItemspec.getAlnvalue());
			
			if(alndomain.getValue().length()>2){  //material name最多為2碼
				return null;
			}
			AItemAttribute aItemAttribute=itemForm.getaItemAttribute();
			
			if(aItemAttribute.getSecondItemFlag().equals(ActiveFlag.Y.name()) &&
				SecondItemType.valueOf(aItemAttribute.getSecondItemType()).secondSource()){ //2nd
				ItemAttributeBO bo=SpringContextUtil.getBean(ItemAttributeBO.class);
				long count=bo.getCountBy2nd(aItemAttribute.getSecondSourceItemnum());
				itemNum =aItemAttribute.getSecondSourceItemnum().substring(0,11)+
						Integer.toString((int)(count+1),36);
			}else{	
				itemNum = itemNum
					+ StringUtilsConvert.addZeroForNum(alndomain.getValue(), 2)
					+ StringUtilsConvert.addZeroForNum(Integer.toString(
							functionBO.getSquence("R1_TYP1_SEQ").intValue(),
							36), 4) + "0";
			}
		} else if (classstructure.getType().equals("2")) {
			itemNum = itemNum
					+ StringUtilsConvert.addZeroForNum(Integer.toString(
							functionBO.getSquence("R1_TYP1_SEQ").intValue(),
							36), 6) + "0";
		}
		if(StringUtils.isBlank(itemNum) || itemNum.length()>12){
			throw new RuntimeException("取料號失敗!,"+ "料號："+ itemNum );
		}
		return itemNum.toUpperCase();
	}
	//==2014/04/22 94取號
	public static String getItemNum94(ItemForm itemForm) {  //94 10碼料號
		FunctionBO functionBO = SpringContextUtil.getBean(FunctionBO.class);
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		String itemNum = itemForm.getaItem().getClassstructureid(); // 料號
		itemNum = itemNum+ StringUtilsConvert.addZeroForNum(Integer.toString(
				functionBO.getSquence("ITEM_94_SEQ").intValue(),
						36), 4);
		while(listItemCommonBO.getItemList(ItemType.R94.toString(), new String[]{"itemnum"}, itemNum).size()!=0){
			itemNum = itemNum+ StringUtilsConvert.addZeroForNum(Integer.toString(
					functionBO.getSquence("ITEM_94_SEQ").intValue(),
					36), 4);
		}
		return itemNum;
	}
	public static String validateFinallyAppr(AItem aItem) {
		ApplyItemSignBo applyItemSignBo = SpringContextUtil
				.getBean(ApplyItemSignBo.class);
		ListAlndomainBO ListAlndomainBO = SpringContextUtil
				.getBean(ListAlndomainBO.class);
		StringBuffer warnMessage=new StringBuffer();
		AItemspec aItemspec = applyItemSignBo.getAItemspec(
				aItem.getClassstructureid(), SystemConfig.meterialName,
				aItem.getEaudittransid());
		Alndomain alndomain = ListAlndomainBO.getAlndomain(
				aItem.getClassstructureid() + SystemConfig.meterialName,
				aItemspec.getAlnvalue());
		//======================meterialName已失效============================
		if(alndomain==null){
			warnMessage.append("該類別的MATER_NAME已不存在!");
		}
		//2015/06/15 送出的申請單不驗證失效類別
//		warnMessage.append(validateClassstructure(aItem.getClassstructureid()));
		return warnMessage.toString();
	}

	// ===========================產生品名(20150210取消)=================================================
//	public static String getItemDescription(AItem aItem,List<ListAItemspecVO> lAItemspecVO) {
//		String  itemDescription = "";
//		List<ListAItemspecVO> temp_lAItemspecVO=new LinkedList<ListAItemspecVO>();
//		if(aItem.getCommodity().indexOf(SystemConfig.makeDescriptionRule)!=-1){
//			//==============================(R18的品名全取)============================
//			temp_lAItemspecVO=lAItemspecVO;
//		}else{
//			//==============================取得必填欄位================================
//			for(ListAItemspecVO l:lAItemspecVO){
//				if ( l.getItemrequirevalue().intValue() == 1 ||
//					SystemConfig.spec_size.indexOf(l.getAssetattrid())!=-1) {
//					temp_lAItemspecVO.add(l);
//				}
//			}
//		}
//		itemDescription=getDescription(temp_lAItemspecVO);
//		
//		if (itemDescription.length() > 40) {
//			return itemDescription.substring(0, 40);
//		} else {
//			return itemDescription;
//		}
//
//	}
	// ===========================產生驗證品名=================================================
	public static String getValidateDescription(List<ListAItemspecVO> lAItemspecVO) {
		String  itemDescription = "";
		List<ListAItemspecVO> temp_lAItemspecVO=new LinkedList<ListAItemspecVO>();
		//==============================取得品名欄位========================================
		for(ListAItemspecVO l:lAItemspecVO){
			if (SystemConfig.validateDescription.indexOf(l.getClassstructureid())==-1
					&& l.getAssetattrid().equals("A999")) {
				continue;
			}
			temp_lAItemspecVO.add(l);
		}
		itemDescription=getDescription(temp_lAItemspecVO);
		return itemDescription.replaceAll(" ","");
	}

	public static String spec_description(ListAItemspecVO lAItemspecVO,ListAItemspecVO nextlAItemspecVO){
		if(!lAItemspecVO.getMeasureunitid().equals(nextlAItemspecVO.getMeasureunitid())){
			return lAItemspecVO.getMeasureunitid().toUpperCase();
		}else{
			return "";
		}
	}
	public static ListAItemspecVO getNextListAItemspecVO(int x,List<ListAItemspecVO> lAItemspecVO){
		ListAItemspecVO nextListAItemspecVO=new ListAItemspecVO();
		for(int i=x+1;i<lAItemspecVO.size();i++){
			nextListAItemspecVO=lAItemspecVO.get(i);
			if(StringUtils.isNotBlank(nextListAItemspecVO.getAlnvalue()) || 
				nextListAItemspecVO.getNumvalue()!=null){
				return nextListAItemspecVO;
			}
		}
		return null;
		
	}
	//===================================取得品名=======================================
	public static String getDescription(List<ListAItemspecVO> lAItemspecVO){
		StringBuffer itemDescription = new StringBuffer();
		for (int x=0;x<lAItemspecVO.size();x++){
			ListAItemspecVO l=lAItemspecVO.get(x);
			String temp = "";
			ListAItemspecVO nextListAItemspecVO=null;
			// ==================規格====================
			if (l.getDatatype().equals(SystemConfig.numeric) && l.getNumvalue()!=null) {
				temp = l.getNumvalue().toString();
			} else if (l.getDatatype().equals(SystemConfig.aln)
					&& StringUtils.isNotBlank(l.getAlnvalue())) {
				l.setAlnvalue(StringUtilsConvert.convertToHalfWidth(l
						.getAlnvalue().toUpperCase()));
				temp = l.getAlnvalue();
			}
			//========================Size===============================
			if(SystemConfig.spec_size.indexOf(l.getAssetattrid())!=-1){
				nextListAItemspecVO=getNextListAItemspecVO(x, lAItemspecVO);
			}
			// ==================規格====================
			 if (StringUtils.isNotBlank(l.getMeasureunitid()) && StringUtils.isNotBlank(temp))
			  {
				 if(nextListAItemspecVO!=null)
					 temp+=spec_description(l,nextListAItemspecVO); 
				 else
					 temp+=l.getMeasureunitid().toUpperCase(); 
			  }
			 
			if (StringUtils.isNotBlank(temp)) {// =分隔號===
					if(nextListAItemspecVO!=null
					   && SystemConfig.spec_size.indexOf(nextListAItemspecVO.getAssetattrid())!=-1 )
						temp+="*";
					else
						temp+=",";
				itemDescription.append(temp);
			}
		}
		if(itemDescription.length()>0){
			return itemDescription.subSequence(0, itemDescription.length()-1).toString().toUpperCase();
		}else{
			return "";
		}
	}

	// ==========================================替代料號==============================================
	public static List<AItemSecondItemnum> AddAItemSecondItemnum(Item i,
			List<AItemSecondItemnum> listAItemSecondItemnum) { // 新增替代料號
		if (i != null) {
			boolean b = true;
			for (AItemSecondItemnum l : listAItemSecondItemnum) {
				if (l.getSecondItemnum().equals(i.getItemnum())) {
					b = false;
					break;
				}
			}
			if (b) {
				AItemSecondItemnum a = new AItemSecondItemnum();
				a.setSecondItemnum(i.getItemnum());
				listAItemSecondItemnum.add(a);
			}
		}
		return listAItemSecondItemnum;
	}

	public static List<AItemSecondItemnum> DeleteAItemSecondItemnum(
			AItemSecondItemnum a,
			List<AItemSecondItemnum> listAItemSecondItemnum) { // 刪除替代料號
		listAItemSecondItemnum.remove(a);
		return listAItemSecondItemnum;
	}

	// ==========================================驗證===================================================================
	public static boolean validate(ItemForm itemForm,GlobalGrowl message)
			throws Exception {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		ApplyItemChangeBo applyItemChangeBo = SpringContextUtil
				.getBean(ApplyItemChangeBo.class);
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		ListItemCommonBO listItemCommonBO = SpringContextUtil
				.getBean(ListItemCommonBO.class);
		ItemSiteBO itemSiteBO=SpringContextUtil.getBean(ItemSiteBO.class);
//		String decription = getItemDescription(itemForm.getaItem(),itemForm.getListAItemspecVO());
		String longDescription = getDescription(itemForm.getListAItemspecVO());
		String validateDescription = getValidateDescription(itemForm.getListAItemspecVO());
		StringBuffer warnMessage = new StringBuffer();

		
		if (StringUtils.isBlank(itemForm.getaItem().getClassstructureid())) // 必須先選擇類別結構
		{
			warnMessage.append("請先選擇類別結構!<br />");
		}else{
			//規格異動不驗證
			if(!Utility.equals(itemForm.getaItem().getEaudittype(),Eaudittype.U)){
				warnMessage.append(validateClassstructure(itemForm.getaItem().getClassstructureid()));
			}
		}
		if (StringUtils.isBlank(itemForm.getaItem().getOrganizationCode())) // 必須先選擇廠區
		{
			warnMessage.append("[廠區]必須選取!<br />");
		}

		if(StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum()) && 
			!itemForm.getaItem().getCommoditygroup().equals(ItemType.R94.toString())){ //規格異動
			ItemSite itemSite=itemSiteBO.getItemSiteByOrgCode(
					itemForm.getaItem().getOriitemnum(), itemForm.getaItem().getOrganizationCode());
			if(itemSite==null){
				warnMessage.append("此料號在該廠區("+itemForm.getaItem().getOrganizationCode()+")不存在! <br/>");
			}else{
				warnMessage=ApplyItemTransferSiteUtils.validateProcessHeaderApply(
					Arrays.asList(itemForm.getaItem().getOriitemnum()), 
					itemSite.getLocationSite(), LocationSiteActionType.S.name(), warnMessage);
			}
			
		}
		if (StringUtils.isBlank(itemForm.getaItem().getRemark())) {
			warnMessage.append("用途說明為必填欄位!<br />");
		}
		if (StringUtils.isBlank(itemForm.getaItemAttribute().getMinBasicUnit())) {
			warnMessage.append("請先選擇最小計量單位!<br />");
		}
		if (StringUtils.isNotBlank(itemForm.getaItemAttribute().getPackageUnit())
				&& (itemForm.getaItemAttribute().getTransferQuantity() == null || itemForm.getaItemAttribute()
						.getTransferQuantity().doubleValue() <= 0)) {
			warnMessage.append("當選取包裝單位時，轉換數量需大於0!<br />");
		}
		if (StringUtils.isNotBlank(itemForm.getaItemAttribute().getPackageUnit())
				&& (itemForm.getaItemAttribute().getTransferQuantity() != null && itemForm.getaItemAttribute()
						.getTransferQuantity().doubleValue() >= 99999)) {
			warnMessage.append("當選取包裝單位時，轉換數量不可大於99999!<br />");
		}
		//=============================驗證替代料號==============================
		warnMessage=ApplyValidationUtils.validateSecondItemList(itemForm, warnMessage);
		
		if(!itemForm.isSimpleSignFlag()){  //簡易板異動修改不驗證
			if (itemForm.getListListAInvvendorVO().size() < 1) {
				warnMessage.append("供應商不能為0!<br />");
			}
			//====20150212  r2,94料號新增且有舊料號不卡報價單===================
			if(!(StringUtils.isBlank(itemForm.getaItem().getOriitemnum()) &&
					itemForm.getItemMapping()!=null)){
				if (fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
						FileCategory.ITEM_ATTACHMENT, "").size() == 0) {
					warnMessage.append("報價單 必須上傳!<br />");
				}
			}
		}
		
		if(itemForm.getaItemAttribute().getOriUnitcost()==null){
			warnMessage.append("報價單價格 不能為空值!<br />");
		}
		if(StringUtils.isBlank(itemForm.getaItemAttribute().getOriCurrency())){
			warnMessage.append("報價單幣別不能為空值!<br />");
		}
		
		if(itemForm.getaItemAttribute().getDeliverytime()==null || itemForm.getaItemAttribute().getDeliverytime().doubleValue()==0){
			warnMessage.append("交貨前置期(天)需大於0!<br />");
		}
		if(itemForm.getaItemAttribute().getDeliverytime()!=null &&
			itemForm.getaItemAttribute().getDeliverytime().compareTo(new BigDecimal("999"))==1){
			warnMessage.append("交貨前置期(天)最大為999天!<br />");
		}
		
		if (itemForm.getaItemAttribute().getMcOrderQuantity() == null
				|| itemForm.getaItemAttribute().getMcOrderQuantity().compareTo(new BigDecimal("0"))!=1) {
			warnMessage.append("經濟訂購量必須大於0!<br />");
		}
		if (itemForm.getaItemAttribute().getMcMinPackageQuantity() != null && 
			itemForm.getaItemAttribute().getMcOrderQuantity() != null && 
			itemForm.getaItemAttribute().getMcOrderQuantity().compareTo(
						itemForm.getaItemAttribute().getMcMinPackageQuantity())==-1
				) {
			warnMessage.append("經濟訂購量必須大於或等於最小包裝數!<br />");
		}
		if (itemForm.getaItemAttribute().getMcMinPackageQuantity() == null
				|| itemForm.getaItemAttribute().getMcMinPackageQuantity().compareTo(new BigDecimal("0"))!=1) {
			warnMessage.append("最小包裝數必須大於0!<br />");
		}
		if (itemForm.getListAItemspecVO().size() < 1) {
			warnMessage.append("料號規格不能為0!<br />");
		}
		if(StringUtils.isNotBlank(itemForm.getaItemAttribute().getFacilityNum())){
			String[] fn=itemForm.getaItemAttribute().getFacilityNum().trim().split(",");
			if (fn.length >50) {
				warnMessage.append("使用機台不能超過50組!<br />");
			}
			for(String s:fn){
				if (s.getBytes("UTF-8").length >30) {
					warnMessage.append("使用機台單組長度不能超過30!<br />");
				}
			}
		}

		byte[] s = longDescription.getBytes("UTF-8");
		if (s.length > 240) {
			warnMessage.append("品名敘述最多為240 Bytes!<br />");
		}
		if (s.length ==0) {
			warnMessage.append("品名敘述必須填寫<br />");
		}else{
			//2015.08.03 對應的舊料號不檢查該驗證品名
			List<Item> validateItemList=applyItemBo.getValidItemList(validateDescription, 
					itemForm.getaItem().getItem(),itemForm.getItemMapping());
			if (validateItemList.size()>0) {
				for(Item i:validateItemList){
					warnMessage.append("驗證品名與料號"+i.getItemnum()+"相同!<br />");
				}
			}
			
			List<AItem> vaildateAItemList=applyItemBo.getVaildateAItemList(validateDescription);
			if (vaildateAItemList.size() > 0) {
				for(AItem i:vaildateAItemList){
					warnMessage.append("驗證品名與送審中料號"+i.getItemnum()+"相同!<br />");
				}
			}
		}
		// =====退回修改可皆狀態不是active、新增料號皆收無料號id，其他只接受active
		if (itemForm.getaItem().getItem() != null 
				&& !SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.REJECT)
				&& applyItemBo.getItemCount(itemForm.getaItem().getItem().getItemid()) > 0) {
			warnMessage.append("此料號目前無法使用，請查詢料號是否生效中!<br />");
		}
		if (itemForm.getaItem().getItem() != null  &&
				 applyItemChangeBo.getChangeItemCount(itemForm.getaItem().getItem().getItemid(),
						itemForm.getaItem().getCreateDate()) > 0) {
			warnMessage.append("此料號已被更新，請刪除此異動後再重新申請異動!<br />");
		}
		warnMessage = validateAItemspec(itemForm.getaItem(), itemForm.getListAItemspecVO(), message,
				warnMessage);
		
		// ==============================料號新增驗證========================================================
		if (StringUtils.isBlank(itemForm.getaItem().getOriitemnum())) {
			// ==============================MC控管==========================================================
			if (StringUtils.isBlank(itemForm.getaItemAttribute().getMcContinuityUse())) {
				warnMessage.append("連續性用料必須選取!<br />");
			} else {
				if (itemForm.getaItemAttribute().getMcContinuityUse().equals("Y")) {
					if (itemForm.getaItemAttribute().getMcOriMinLevel() == null
							|| itemForm.getaItemAttribute().getMcOriMinLevel().intValue() <= 0) {
						warnMessage.append("連續性用料時，月需求量必須大於0!<br />");
					}
				} else if (itemForm.getaItemAttribute().getMcContinuityUse().equals("N")) {
					if (itemForm.getaItemAttribute().getMcUseFrequencyLevel() == null
							|| itemForm.getaItemAttribute().getMcUseFrequencyLevel()
									.intValue() <= 0) {
						warnMessage.append("非連續性用料時，耗用頻率必須大於0!<br />");
					}
					if (itemForm.getaItemAttribute().getMcTotalMinLevel() == null
							|| itemForm.getaItemAttribute().getMcTotalMinLevel().intValue() <= 0) {
						warnMessage.append("非連續性用料時，總數量必須大於0!<br />");
					}
				}
			}
		}
		// ==============================料號異動驗證=====================================
		if (StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())) {
			if(!itemChangeValidate(itemForm)){
				warnMessage.append("規格異動申請單所有欄位皆未修改卡不可送審!<br />");
			}
			if(StringUtils.isBlank(itemForm.getaItem().getChangeRemark())){
				warnMessage.append("規格異動時，異動說明必須填寫<br />");
			}
		}
		
		//===============================價格判斷=========================================
		if(itemForm.getaItemAttribute().getUnitcost()==null){
			warnMessage.append("參考單價不能為空值!<br />");
		}else{
			if(itemForm.getItemMapping()==null &&
			   itemForm.getaItemAttribute().getUnitcost().compareTo(new BigDecimal("0"))!=1){
				warnMessage.append("參考單價需大於0!<br />");
			}
			//TN20161200101拿掉卡關 解除MRO的金額卡關(>NTD 300萬無法新增料號)機制，但針對料號新增/規格異動/廠區啟用流程，
			//只要一碰到金額超過台幣300萬，就需會簽前/後段管理員。
			//if(itemForm.getaItemAttribute().getUnitcost().compareTo(new BigDecimal("3000000"))>=0){
			//	warnMessage.append("料號參考單價超過NT 300萬(含)之請購，請以固資類開立!<br />");
			//}
		}
		
		//====================================R1料號判斷==================================
		if (itemForm.getaItem().getCommoditygroup().equals(ItemType.R1.toString())){
			BigDecimal totalShelfLife=itemForm.getaItem().getTotalShelfLife()==null ?
					new BigDecimal(0):itemForm.getaItem().getTotalShelfLife();
					
			BigDecimal minShelfLife=itemForm.getaItem().getMinShelfLife()==null ?
					new BigDecimal(0):itemForm.getaItem().getMinShelfLife();
					
			if(minShelfLife.compareTo(totalShelfLife)==1){
				warnMessage.append("Min Shelf Life必須小於等於Total Shelf Life!<br />");
			}
			if(totalShelfLife.compareTo(new BigDecimal(0))==1 && minShelfLife.compareTo(new BigDecimal(0))!=1){
				warnMessage.append("當Total Shelf Life>0時，Min Shelf Life也必須大於0!<br />");
			}
		}
		// ==============================GP控管==========================================================
			if (StringUtils.isBlank(itemForm.getaItemAttribute().getGpProductCategory())) {
				warnMessage.append("產品/製程類別必須選取!<br />");
			} else {
				if (GpType.valueOf(itemForm.getaItemAttribute().getGpProductCategory()).vGpType2()) {
					if (StringUtils.isBlank(itemForm.getaItemAttribute().getGpDeliveryType())) {
						warnMessage.append("是否隨產品出貨必須選取!<br />");
					}
					if (StringUtils.isBlank(itemForm.getaItemAttribute().getGpRemainType())) {
						warnMessage.append("是否殘留在產品內部必須選取!<br />");
					}
					if (GpType.getGpFlag(itemForm.getaItemAttribute()).equals("Y")) {
						if (itemForm.getaItemAttribute().getGpReportDate() == null) {
							warnMessage.append("高 / 中風險時，檢測報告日期為 必填欄位!<br />");
						}
						if (fileUploadBO
								.getAttachmentList(
										itemForm.getaItem().getItemnum(),
										FileCategory.ITEM_GP_REPORT,
										"").size() == 0) {
							warnMessage.append("高 / 中風險時，檢測報告必須上傳!<br />");
						}
						if (fileUploadBO
								.getAttachmentList(
										itemForm.getaItem().getItemnum(),
										FileCategory.ITEM_GP_MSDS,
										"").size() == 0) {
							warnMessage.append("高 / 中風險時，MSDS必須上傳!<br />");
						}
					}
				}
			}
			if(!itemForm.isSimpleSignFlag()){  //簡易版異動修改不驗證
				Map map = SystemConfig.msdsReportMap();
				if (map.get(itemForm.getaItem().getClassstructureid()) != null) {
					if (fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
							FileCategory.ITEM_GP_MSDS, "").size() == 0) {
						warnMessage.append("化學品類物料，MSDS必須上傳!<br />");
					}
				}
			}
		//====================================R2料號判斷===============================================
		if (Utility.equalsOR(itemForm.getaItem().getCommoditygroup(), ItemType.R2,ItemType.R94)){
			ItemAttribute itemattribute=null;
			if(itemForm.getaItem().getItem()!=null){
				itemattribute=listItemCommonBO.getItemAttribute(itemForm.getaItem().getItem().getItemid().intValue());
			}
//			if (StringUtils.isBlank(itemForm.getaItemAttribute().getFacilityNum())	) {
//				warnMessage.append("使用機台為必填!<br />");
//			}
			if (StringUtils.isBlank(itemForm.getaItemAttribute().getStrategyMgmtFlag())	) {
				warnMessage.append("戰略管制品為必選欄位!<br />");
			}
			//TN20170300016  戰略性高科技管制貨品於MRO可進行雙向修改
//			else if(itemattribute!=null && itemattribute.getStrategyMgmtFlag().equals("Y") && 
//					itemForm.getaItemAttribute().getStrategyMgmtFlag().equals("N")){
//				warnMessage.append("戰略管制品僅可由No修改為Yes ; Yes不可修改為No!<br />");
//				
//			}
			if (StringUtils.isBlank(itemForm.getaItemAttribute().getFacilitySupplierName())	) {
				warnMessage.append("機台設備商名稱為必填!<br />");
			}
			
			//舊料號不卡兩家報價單&指廠說明
			if (itemForm.isVendorRemarkFlag() &&
					fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
							FileCategory.ITEM_VENDOR_REMARK, "").size()==0	) {
				warnMessage.append("無替代料號且供應商或報價單若少於2個時，指廠說明必須上傳!<br />");
			}
			
//			if (fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
//					SystemConfig.ApplyFileItemSpec, "").size()==0) {
//				warnMessage.append("規格書必須上傳!<br />");
//			}
			if (fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
					FileCategory.ITEM_PHOTO, "").size()==0 &&
					itemForm.getaItemAttribute().getInspectionType().equals("1")) {
				warnMessage.append("入料檢驗方式若為免驗，圖片必須上傳!<br />");
			}
		}else{
			if(!itemForm.getaItemAttribute().getInspectionType().equals("1")){
				warnMessage.append("入料檢驗方式必須為免驗!!");
			}			
		}
		
		//====================================R3料號判斷===============================================
		if (itemForm.getaItem().getCommoditygroup().equals(ItemType.R3.toString())){
			if (fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
					FileCategory.ITEM_SPEC, "").size()==0) {
				warnMessage.append("規格書必須上傳!<br />");
			}
		}
		//====================================供應商重複驗證=============================
		warnMessage=ApplyUtils.validateVendor(itemForm.getListListAInvvendorVO(), warnMessage);
		//=========================================================================
		if (warnMessage.length() > 0) {
			// =====2013/6/21料號產生使用=====
			// applyItemBo.updateErrorCode(aitem.getEaudittransid(),
			// warnMessage.toString());
			//======2015/07/13 儲存errorlog使用======
			if(itemForm.getaItemSimple()!=null && itemForm.getaItemSimple().getAItemSimpleId()!=null){
				AItemSimpleBO aItemSimpleBO=SpringContextUtil.getBean(AItemSimpleBO.class);
				aItemSimpleBO.updateErrorLog(itemForm.getaItemSimple(), warnMessage.toString());
			}
			
			message.addErrorMessage("Error", warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}

	public static StringBuffer validateAItemspec(AItem aitem,
			List<ListAItemspecVO> lAItemspecVO, GlobalGrowl message,
			StringBuffer warnMessage) {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		MeasureunitDomainBO measureunitDomainBO=SpringContextUtil.getBean(MeasureunitDomainBO.class);
		String[] R2ItemSpecNonDuplicate = SystemConfig.R94ItemSpecNonDuplicate.split(",");
		for (ListAItemspecVO l : lAItemspecVO) {
			// ===============================品名是否有重覆=============================================
			if (l.getDatatype().equals(SystemConfig.numeric)) {
				if (l.getItemrequirevalue().intValue() == 1
						&& l.getNumvalue() == null) {
					warnMessage
							.append("屬性" + l.getAssetattrid() + "為必填!<br />");
				}
			} else if (l.getDatatype().equals(SystemConfig.aln)) {
				if (l.getItemrequirevalue().intValue() == 1
						&& StringUtils.isBlank(l.getAlnvalue())) {
					warnMessage
							.append("屬性" + l.getAssetattrid() + "為必填!<br />");
				}
			}
			
			if (l.getItemrequirevalue().intValue() == 1 && 
					measureunitDomainBO.getCount(l.getClassstructureid()+l.getAssetattrid(),true)>0 &&
					StringUtils.isBlank(l.getMeasureunitid())){
				warnMessage
						.append("單位" + l.getAssetattrid() + "為必選!<br />");
			}
			// =========================R2/94指定===============================
			if (Utility.equalsOR(aitem.getCommoditygroup(), ItemType.R2,ItemType.R94)) {
				for (String s : R2ItemSpecNonDuplicate) {
					if (l.getAssetattrid().equals(s) && 
						(StringUtils.isNotBlank(l.getAlnvalue()) || l.getNumvalue()!=null)) {
						List list=applyItemBo.getR2AItemCount(aitem , 
								R2ItemSpecNonDuplicate, l.getAlnvalue(),
								getAlnvalue(Arrays.asList(SystemConfig.meterialName,SystemConfig.makerId),
								lAItemspecVO));
						if(list.size()>0){
							warnMessage.append("屬性" + l.getAssetattrid()
									+ "已經存在申請單"+WorkflowUtils.ListToString(list)+"中!<br />");
						}
						list=applyItemBo.getR2ItemCount(
								aitem ,R2ItemSpecNonDuplicate,l.getAlnvalue(),
								getAlnvalue(Arrays.asList(SystemConfig.meterialName,SystemConfig.makerId),
								lAItemspecVO));
						
						if(list.size()>0){
							warnMessage.append("屬性" + l.getAssetattrid()
									+ "已經存在料號"+WorkflowUtils.ListToString(list)+"中!<br />");
						}
					}

				}
			}
		}
		return warnMessage;
	}
	
	public static String validateClassstructure(String classstructureid){
		ClassstructureBO classstructureBO=SpringContextUtil.getBean(ClassstructureBO.class);
		StringBuffer warnMessage = new StringBuffer();
		long count=classstructureBO.getClassstructure(classstructureid, true);
		if(count==0){
			warnMessage.append("類別"+classstructureid+"已被失效，請取消申請單!<br />");
		}
		return warnMessage.toString();
		
	}
	public static boolean validateDelete(ItemForm itemForm,GlobalGrowl message){
		StringBuffer warnMessage = new StringBuffer();
		ItemMappingBO itemMappingBO=SpringContextUtil.getBean(ItemMappingBO.class);
		ItemMapping itemMapping=itemMappingBO.getItemMapping(
				itemForm.getaItem().getEaudittransid(),null);
		if(itemMapping!=null){
			warnMessage.append("此申請單為舊料號回補，禁止刪除!<br />");
		}	
		
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error", warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}

	// =================================產品/製程類別==============================
	public static AItemAttribute gpProductCategory(
			AItemAttribute aItemAttribute, String ItemNum) {
		// 產品專用 /航太製程專用
		if(StringUtils.isBlank(aItemAttribute.getGpProductCategory())){
			aItemAttribute.setGpControlFlag(null);
		}else{
			if(!GpType.valueOf(aItemAttribute.getGpProductCategory()).vGpType2()){
				aItemAttribute.setGpControlFlag("N");
				aItemAttribute.setGpDeliveryType(null);
				aItemAttribute.setGpRemainType(null);
				aItemAttribute.setGpReportDate(null);
				aItemAttribute.setGpRiskLv(null);
				aItemAttribute.setGpEvLv(GpType.getEvLv(null,null));
				FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
				fileUploadInterfaces.fileDelete(ItemNum,FileCategory.ITEM_GP_REPORT, "");
			}else{
				GpType.setGpValue(aItemAttribute);
			}
		}
		return aItemAttribute;
	}

	// =================================連續性用料==============================
	public static AItemAttribute mcContinuityUse(AItemAttribute aItemAttribute) {
		if(StringUtils.isNotBlank(aItemAttribute.getMcContinuityUse())){
			if (aItemAttribute.getMcContinuityUse().equals("Y")) {
				aItemAttribute.setMcUseFrequencyLevel(new BigDecimal(0));
				aItemAttribute.setMcTotalMinLevel(new BigDecimal(0));
			} else if (aItemAttribute.getMcContinuityUse().equals("N")) {
				aItemAttribute.setMcOriMinLevel(new BigDecimal(0));
			}
		}
		return aItemAttribute;
	}

	
	public static void deleteFile(String prid,FileCategory category) { //PR檔案
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.fileDelete(prid,category,"");
	}
	public static boolean itemChangeValidate(ItemForm itemForm) throws Exception{ //料號異動判斷
		ItemInterface itemInterface=new ItemImpl();
		itemForm=itemInterface.setApplyChangeMap(itemInterface.setPreItemForm(itemForm), itemForm);
		Map<String,String> applyChangeMap=itemForm.getApplyChangeMap();
		SystemConfig.itemChangeNonValidate.stream().forEach(v->applyChangeMap.remove(v));
		return StringUtils.isNotBlank(applyChangeMap.values().stream().
				filter(k->StringUtils.isNotBlank(k)).findAny().orElse(null));
	}
	public static List<ListAItemspecVO> getAlnvalue(List<String> assetattrids,List<ListAItemspecVO> lAItemspecVO){
		List<ListAItemspecVO> list=new ArrayList<ListAItemspecVO>();
		Map map=Utility.listToMap(assetattrids);
		for(ListAItemspecVO l :lAItemspecVO){
			if(map.get(l.getAssetattrid())!=null){
				list.add(l);
			}
		}
		return list;
		
	}
	public static boolean validateChangeVendor(List<ListAInvvendorVO> preVendor,
			List<ListAInvvendorVO> nowVendor){
		List preList=new ArrayList<>();
		List nowList=new ArrayList<>();
		List nowList2=new ArrayList<>();
		for(ListAInvvendorVO l :preVendor){
			preList.add(l.getVendor());
		}
		for(ListAInvvendorVO l :nowVendor){
			nowList.add(l.getVendor());
			nowList2.add(l.getVendor());
		}
		if(nowList.retainAll(preList) || preList.retainAll(nowList2)){
			return true;
		}
		return false;
		
	}
	 /*
	  * 驗證廠商是否有重複
	  */
	public static StringBuffer validateVendor(List<ListAInvvendorVO> list, StringBuffer warnMessage) {
		Map<String,String> map=new HashMap<String,String>();
		for(ListAInvvendorVO l : list){
	    	if(map.get(l.getVendor())!=null){
	    		warnMessage.append("供應商"+l.getNewvendorname() + "("+l.getVendor()+ ")重複申請!<br />");
	    	}
	    	map.put(l.getVendor(), l.getVendor());
		}
		return warnMessage;
	}
}