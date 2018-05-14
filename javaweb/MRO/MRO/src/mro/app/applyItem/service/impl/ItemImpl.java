package mro.app.applyItem.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.aItemSimple.bo.ListAItemSimpleBO;
import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.Utils.ApplyValidationUtils;
import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.bo.ApplyItemChangeBo;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.bo.ListClassstructureBO;
import mro.app.sign.service.ItemSignInterface;
import mro.app.sign.service.impl.ItemSignImpl;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.Eaudittype;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AttachmentBO;
import mro.base.bo.ClassstructureGpBO;
import mro.base.bo.ClassstructureSecondSourceBO;
import mro.base.bo.ClassstructureUnitBO;
import mro.base.bo.ItemBO;
import mro.base.bo.ItemMappingBO;
import mro.base.bo.ItemspecBO;
import mro.base.bo.MeasureunitDomainBO;
import mro.base.bo.PersonBO;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureGp;
import mro.base.entity.ClassstructureUnit;
import mro.base.entity.Item;
import mro.base.entity.Itemspec;
import mro.base.entity.Person;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.ItemForm;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ItemImpl implements ItemInterface {

	@Override
	public ItemForm setChangeParameter(ItemForm itemForm) {
		ApplyItemChangeBo applyItemChangeBo =SpringContextUtil.getBean(ApplyItemChangeBo.class);
		itemForm.setBuyerSignMap(applyItemChangeBo.getBuyerSignMap());
		return itemForm;
	}

	@Override
	public ItemForm setParameter(ItemForm itemForm) {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		Map<ParameterType, Map> param=bean.getParameterOption();

		// 產品/製程類別選單
		itemForm.setGpProduction(param.get(ParameterType.GP_PRODUCT_CATEGORY));
		// 是否隨產品出貨選單
		itemForm.setGpDelivery(param.get(ParameterType.GP_DELIVERY_TYPE));
		 // 是否殘留在產品內部選單
		itemForm.setGpRemain(param.get(ParameterType.GP_REMAIN_TYPE));
		// 儲存條件
		itemForm.setStorageCondition(param.get(ParameterType.STORAGE_CONIDTION));
		// 驗收選單
		itemForm.setInspectionTypeOption(param.get(ParameterType.INSPECTION_TYPE));
		// 耗用分類選單
		itemForm.setMcUseFrequencyTypeOption(param.get(ParameterType.MC_USE_FREQUENCY_TYPE));
		// 耗用大分類
		itemForm.setItemCategoryOption(param.get(ParameterType.ITEM_CATEGORY));
		// 幣別分類選單
		itemForm.setCurrencyOption(applyItemBo.getRateCurrencyOption(SystemConfig.defaultCurrency));
		// 替代料號選單
		itemForm.setSecondItemOption(new LinkedHashMap(param.get(ParameterType.SECOND_ITEM_TYPE)));
		return itemForm;
	}

	@Override
	public ItemForm selectApply(ItemForm itemForm, AItem aItem) {
		itemForm.itemIntial();
		ApplyItemChangeBo applyItemChangBo = SpringContextUtil
				.getBean(ApplyItemChangeBo.class);
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		PersonBO personBO = SpringContextUtil
				.getBean(PersonBO.class);
		itemForm.setaItem(aItem);
		// ===========================異動人=========================================
		Person person = personBO.getPerson(aItem.getChangeby());
		if(person!=null){
			itemForm.setEmp(person.getDisplayName() + "("
					+ person.getPersonId() + ")");
		}
		// ==========================分類====================================
		itemForm = setClassstructurePhase(itemForm,itemForm.getaItem().getClassstructureid());

		if (aItem.getEaudittransid() != null) {
			// ============================其他細項========================================
			itemForm.setListListAInvvendorVO(applyItemBo
					.getListAInvvendorVO(aItem.getEaudittransid().intValue()));
			itemForm.setListAItemspecVO(applyItemBo
					.getListAItemspecVOList(aItem.getEaudittransid(),aItem.getPreEaudittransid()));
			itemForm.setListAItemSecondItemnum(applyItemBo
					.getListAItemSecondItemnum(aItem.getEaudittransid()
							.intValue()));
			// ============================申請單其他屬性==============================================
			itemForm.setaItemAttribute(applyItemBo.getAItemAttribute(aItem
					.getEaudittransid().intValue()));
			// =============================簽核歷程=========================================================
			itemForm.setSignHistoryUrl(WorkflowActionUtils.onSignHistory(aItem
					.getTaskId()));
		} else {
			itemForm.setListListAInvvendorVO(applyItemChangBo
					.getInvvendorToAInvvendorList(aItem.getItem().getItemid().toString()));
			itemForm.setListAItemspecVO(applyItemChangBo
					.getItemspecToItemspecList(aItem.getItem().getItemid().toString(),aItem.getClassstructureid()));
			itemForm.setListAItemSecondItemnum(applyItemChangBo
					.getItemSecondItemnumToAItemSecondItemnumList(aItem.getItem()
							.getItemid().toString()));
			// ============================申請單其他屬性==============================================
			itemForm.setaItemAttribute(applyItemChangBo
					.getItemAttributeToAItemAttribute(aItem.getItem().getItemid()
							.intValue()));
		}
		// ============================Page===================================================
		itemForm.setActiveIndex(1);
		// ============================下載檔案===============================================
		this.setDownLoadFile(itemForm);
		return itemForm;
	}

	public void setDownLoadFile(ItemForm itemForm) { // 取得下載檔案
		AttachmentBO attachmentBO = SpringContextUtil.getBean(AttachmentBO.class);
		itemForm.getDowloadFileMap().putAll(attachmentBO.getMap(
				itemForm.getaItem().getItemnum(), null, true));
	}

	@Override
	public void onApplyDelete(ItemForm itemForm) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		applyItemBo.onApplyDelete(itemForm.getaItem());
		itemForm.setEditButton(false);
		message.addInfoMessage("Delete", "Delete successful.");
	}

	@Override
	public ItemForm setCompany(ItemForm itemForm, VwNewvendorcodeEpmall vwNewvendorcodeEpmall) {
		ListAInvvendorVO i = new ListAInvvendorVO();
		i.setVendor(vwNewvendorcodeEpmall.getNvcid().toString());
		i.setNewvendorname(vwNewvendorcodeEpmall.getNewvendorname());
		i.setRegistrationnum(vwNewvendorcodeEpmall.getRegistrationnum());
		itemForm.getListListAInvvendorVO().add(i);
		return itemForm;
	}

	@Override
	public ItemForm deleteinvvendor(ItemForm itemForm, ListAInvvendorVO l) {
		itemForm.getListListAInvvendorVO().remove(l);
		return itemForm;
	}

	@Override
	public ItemForm setSelectItem(ItemForm itemForm, Item item) {
		GlobalGrowl message = new GlobalGrowl();
		if (item != null) {
			if (!item.getClassstructureid().equals(
					itemForm.getaItem().getClassstructureid())) {
				message.addWarnMessage("WARM!",
						"料號類別" + item.getClassstructureid() + "與申請單不同!");
			} else {
				// 2nd SecondSource
				if (SecondItemType.valueOf(itemForm.getaItemAttribute().getSecondItemType()).secondSource()) {
					itemForm.getaItemAttribute().setSecondSourceItemnum(item.getItemnum());
					this.setSecondSource(itemForm); //設定替代料號MAP
				} else {
					itemForm.setListAItemSecondItemnum(ApplyUtils
						.AddAItemSecondItemnum(item,itemForm.getListAItemSecondItemnum()));
				}
			}
		}
		return itemForm;
	}

	@Override
	public ItemForm deleteAItemSecondItemnum(ItemForm itemForm,
			AItemSecondItemnum a) {
		itemForm.setListAItemSecondItemnum(ApplyUtils.DeleteAItemSecondItemnum(
				a, itemForm.getListAItemSecondItemnum()));
		return itemForm;
	}

	@Override
	public ItemForm setUnitMap(ItemForm itemForm, String classstructureid) {
		// =============================最小計量單位&包裝單位===================================
		ClassstructureUnitBO classstructureUnitBO = SpringContextUtil.getBean(ClassstructureUnitBO.class);
		ClassstructureUnit classstructureUnit=Utility.nvlEntity(
				classstructureUnitBO.getClassstructureUnit(classstructureid),ClassstructureUnit.class);
		itemForm.setMinBasicUnit(Utility.splitToMap(classstructureUnit.getMinBasicUnit(), ","));
		itemForm.setPackageUnit(Utility.splitToMap(classstructureUnit.getPackageUnit(), ","));
		return itemForm;
	}

	@Override
	public ItemForm setClassstructureuid(ItemForm itemForm,
			String classstructureid, String loginUserId, Person person) {
		MeasureunitDomainBO measureunitDomainBO=SpringContextUtil.getBean(MeasureunitDomainBO.class);
		//=========classstructureid 異動判斷=============================
		boolean classstructureidNotEquals=StringUtils.isBlank(itemForm.getaItem().getClassstructureid()) ||
				!itemForm.getaItem().getClassstructureid().equals(classstructureid)?true:false;
		// ===========================GP選項(類別更新)=========================================
		if(classstructureidNotEquals){
			this.setGpDefault(itemForm,classstructureid);
		}
		// =============================異動申請不更新====================================
		if(StringUtils.isBlank(itemForm.getaItem().getOriitemnum())){  
			//============================驗收=======================================
			if(classstructureidNotEquals){
				if (Utility.equalsOR(classstructureid.substring(0, 2), ItemType.R94,ItemType.R2)){
					itemForm.getaItemAttribute().setInspectionType("2"); // user檢驗
				} else {
					itemForm.getaItemAttribute().setInspectionType(
						StringUtils.isBlank(classstructureid)?null:"1"); // 免驗
				}
			}
			// ===============second Source================================================
			this.setSecondSource(itemForm);
		}
		// =============================最小計量單位&包裝單位===================================
		itemForm = setUnitMap(itemForm, classstructureid);
		// ==========================類別結構====================================
		itemForm.getaItem().setClassstructureid(classstructureid);
		// ==========================分類====================================
		itemForm = setClassstructurePhase(itemForm,itemForm.getaItem().getClassstructureid());
		// ===========================大分類(Commoditygroup)===================================
		itemForm.getaItem().setCommoditygroup(
				itemForm.getsStructs1().substring(0, itemForm.getsStructs1().indexOf(" (")));		
		// ===========================零件類型(大分類)===============================
		itemForm.getaItem().setItemtype(itemForm.getaItem().getCommoditygroup());	
		// ==========================中分類(Commodityg)====================================
		itemForm.getaItem().setCommodity(
				itemForm.getsStructs2().substring(0, itemForm.getsStructs2().indexOf(" (")));	

		// ============================申請單狀態&TYPE======================================
		if (itemForm.getaItem().getEaudittransid() == null) {   //新申請單&異動申請
			itemForm.getaItem().setStatus(SignStatus.NEW.toString()); // 狀態
			if (StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())) {
				itemForm.getaItem().setEaudittype(Eaudittype.U.toString());
			} else {
				itemForm.getaItem().setEaudittype(Eaudittype.I.toString());
			}
		}
		// ===============類別(只要使用setClassstructureuid就必須重新載入)==============
			itemForm.setListAItemspecVO(ApplyUtils.getAItemSpec(itemForm.getaItem()));
			itemForm.setAlndomainMap(ApplyUtils.onDomainSet(itemForm.getaItem()));
			itemForm.setMeasureunitDomainMap(measureunitDomainBO.getMap(
					itemForm.getaItem().getClassstructureid(),true));  //只取有效的清單
		//=================================================================
		itemForm.getaItem().setEauditusername(loginUserId);
		itemForm.getaItem().setChangeby(person.getPersonId());
		itemForm.getaItem().setDeptNo(person.getDeptCode());
		itemForm.setEmp(person.getDisplayName() + "(" + person.getPersonId() + ")");
		itemForm.setActiveIndex(1);
		itemForm.setEditButton(true);
		
		return itemForm;
	}

	@Override
	public boolean onApplySave(ItemForm itemForm, String action,
			String loginEmpNo) {
		GlobalGrowl message = new GlobalGrowl();
		if (StringUtils.isNotBlank(itemForm.getaItem().getClassstructureid())) { // 必須先選擇類別結構
			Object[] sItem = ApplyUtils.getIRItemNum(itemForm.getaItem());
			BigDecimal eaudittransid = (BigDecimal) sItem[0];
			String itemNum = (String) sItem[1];
			// =============================產品/製程類別驗證=================================
			itemForm.setaItemAttribute(ApplyUtils.gpProductCategory(
					itemForm.getaItemAttribute(), itemNum));
			// =============================連續性用料驗證==================================
			itemForm.setaItemAttribute(ApplyUtils.mcContinuityUse(itemForm
					.getaItemAttribute()));
			// =============================替代料號===========================================
			itemForm.setSecondDefault();
			// =============================轉換數量===========================================
			if (StringUtils.isBlank(itemForm.getaItemAttribute()
					.getPackageUnit())) {
				itemForm.getaItemAttribute().setTransferQuantity(null);
			}
			// ==============================================================================

			itemForm.getaItem().setDescription(
					ApplyUtils.getDescription(itemForm.getListAItemspecVO()));// 品名
				
			itemForm.getaItem().setValidateDescription(
					ApplyUtils.getValidateDescription(itemForm
							.getListAItemspecVO()));// 驗證品名
			itemForm.getaItem().setEaudittimestamp(
					new Date(System.currentTimeMillis()));
			// =========================送審簽核流程================================================
			if (action.equals("submit")) {
				
				//====20150212  若料號新增且eaudittransid有對應舊料號，免簽核
				if(StringUtils.isBlank(itemForm.getaItem().getOriitemnum()) && 
						itemForm.getItemMapping()!=null){
					onApplySave(itemForm, eaudittransid, itemNum, action);
					ItemSignInterface itemSignInterface=new ItemSignImpl();
					itemForm=selectApply(itemForm, itemForm.getaItem());
					itemSignInterface.onFinalApprNoSign(itemForm);
					message.addInfoMessage("Submit", "Submit successful.");
					return true;
					
				}
				
				itemForm = setTaskId(itemForm, itemNum, loginEmpNo);
				if (itemForm.getaItem().getTaskId() != null) {
					onApplySave(itemForm, eaudittransid, itemNum, action);
					if (SignStatus.valueOf(itemForm.getaItem().getStatus())
							.equals(SignStatus.INPRG)) {
						message.addInfoMessage("Submit", "Submit successful.");
						if(StringUtils.isNotBlank(itemForm.getaItemAttribute().getSecondSourceRemark())){
							message.addInfoMessage("Submit", "Submit 2nd料號族群需一併修改："+
									itemForm.getaItemAttribute().getSecondSourceRemark());
						}
					}
					return true;
				} else {
					message.addErrorMessage("送審失敗", "請重新送審或與MIS聯繫!");
				}
			} else if (action.equals("save")) {
				onApplySave(itemForm, eaudittransid, itemNum, action);
				message.addInfoMessage("Save", "Save successful.");
				return true;
			} else if (action.equals("simpleSave")) {
				itemForm.getaItem().setMoqMpqFlag(
						itemForm.getaItemSimple().getMoqMpqFlag());
				onApplySave(itemForm, eaudittransid, itemNum, action);
				return true;
			}
		} else {
			message.addWarnMessage("Warn", "請先選擇類別結構!");
		}
		return false;
	}

	@Override
	public void onApplySave(ItemForm itemForm, BigDecimal Eaudittransid,
			String itemNum, String action) {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		ApplyItemChangeBo applyItemChangeBo = SpringContextUtil
				.getBean(ApplyItemChangeBo.class);
		if (Eaudittype.valueOf(itemForm.getaItem().getEaudittype())
				.equals(Eaudittype.U)) { // 規格異動
			applyItemChangeBo.onApplySave(itemForm, Eaudittransid, itemNum,
					action);
		} else {
			applyItemBo.onApplySave(itemForm, Eaudittransid, itemNum, action);
		}
	}

	@Override
	public ItemForm setTaskId(ItemForm itemForm, String ItemNum,
			String loginEmpNo) {
		if (Eaudittype.valueOf(itemForm.getaItem().getEaudittype())
				.equals(Eaudittype.U)) { // 規格異動
			itemForm.getaItem().setTaskId(
					WorkflowActionUtils.onItemChengeSumit(itemForm.getaItem(),
							ItemNum, loginEmpNo,getSignParameter(itemForm, loginEmpNo)));
		} else {
			itemForm.getaItem().setTaskId(
					WorkflowActionUtils.onItemApplySumit(itemForm.getaItem(),
							ItemNum, loginEmpNo,getSignParameter(itemForm, loginEmpNo)));
		}
		return itemForm;
	}

	@Override
	public int getSignProcessId(ItemForm itemForm) {
		if (Eaudittype.valueOf(itemForm.getaItem().getEaudittype())
				.equals(Eaudittype.U)) {
			return WorkflowUtils
					.getItemChengeSignProcessId(itemForm.getaItem());
		} else if (Eaudittype.valueOf(itemForm.getaItem().getEaudittype())
				.equals(Eaudittype.I)) {
			return WorkflowUtils.getItemApplySignProcessId(itemForm.getaItem());
		}
		return 0;
	}


	@Override
	public  ItemForm onSignPreView(ItemForm itemForm, String loginEmpNo) {
		itemForm.setSignPreViewUrl(WorkflowActionUtils.onSignPreView(
				getSignParameter(itemForm, loginEmpNo)));
		return itemForm;
	}

	@Override
	public ItemForm setClassstructurePhase(ItemForm itemForm,String classstructureid) {
		ListClassstructureBO listClassstructureBO = SpringContextUtil
				.getBean(ListClassstructureBO.class);
		// ==========================分類====================================
		List<Classstructure> list=listClassstructureBO.getClassstructurePhase(classstructureid);
		int i=1;
		for(Classstructure c:list){
			if(list.size()==3 && i==3){i++;}  //三階類別 小分類要跳開
			ReflectUtils.setFieldValue(itemForm, "sStructs"+i,
					c.getClassificationid() + " (" + c.getDescription() + ")");
			if(c.getClassstructureid().equals(classstructureid)){  //設定存貨/費用
				setStorageCategory(itemForm, c);
			}
			i++;
		}
		return itemForm;
	}

	@Override
	public void setUnitCost(ItemForm itemForm) {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		itemForm.getaItemAttribute().setOriRate(applyItemBo.getRate(itemForm));
		if(itemForm.getaItemAttribute().getOriUnitcost()!=null){
			itemForm.getaItemAttribute().setUnitcost(
					itemForm.getaItemAttribute().getOriUnitcost()
							.multiply(itemForm.getaItemAttribute().getOriRate())
							.setScale(3, BigDecimal.ROUND_HALF_UP));
		}else{
			itemForm.getaItemAttribute().setUnitcost(null);
		}
	}

	@Override
	public ItemForm setAItemSimple(ItemForm itemForm) {  //簡易版異動料號
		ListAItemSimpleBO listAItemSimpleBO = SpringContextUtil
				.getBean(ListAItemSimpleBO.class);
		itemForm.setaItemSimple(listAItemSimpleBO.getAItemSimple(
			new String[] { "applyNum" }, itemForm.getaItem().getItemnum()));
		itemForm.setSimpleSignFlag(simpleSignFlag(itemForm));
		if (itemForm.getaItemSimple() != null) {
			Map map = new HashMap<>();
			if(StringUtils.isNotBlank(itemForm.getaItemSimple().getAssetattrid())){
				String[] specEdit = itemForm.getaItemSimple().getAssetattrid()
						.split(",");
				for (String s : specEdit) {
					map.put(s, s);
				}
			}

			itemForm.setSpecEditMap(map);
		}
		if(itemForm.isSimpleSignFlag()){ //簡易版限定異動欄位
			itemForm.setEditButton(false);
		}
		return itemForm;
	}

	@Override
	public boolean simpleSignFlag(ItemForm itemForm) {
		if((itemForm.getaItem().getMoqMpqFlag()!=null && 
		   itemForm.getaItem().getMoqMpqFlag().equals("Y")) || 
		   itemForm.getaItemSimple()!=null){
			return true;
		}
		return false;
	}

	@Override
	public ItemForm setApplyChangeMap(ItemForm preItemForm,
			ItemForm afterItemForm) throws Exception {
		Map applyChangeMap =new HashMap<String,String>(); 
		Map preMap=new HashMap();
		Map afterMap=new HashMap();
		if(preItemForm.getaItem()!=null){
			preMap.putAll(org.apache.commons.beanutils.BeanUtils.describe(preItemForm.getaItemAttribute()));
			afterMap.putAll(org.apache.commons.beanutils.BeanUtils.describe(afterItemForm.getaItemAttribute()));
			
			preMap.putAll(org.apache.commons.beanutils.BeanUtils.describe(preItemForm.getaItem()));
			afterMap.putAll(org.apache.commons.beanutils.BeanUtils.describe(afterItemForm.getaItem()));		
			
			Iterator iter=afterMap.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry entry= (Map.Entry) iter.next();
			    Object key = entry.getKey();
			    String after=ObjectUtils.toString(afterMap.get(key));
			    String pre=ObjectUtils.toString(preMap.get(key));
			    String value="";
				if(!after.equals(pre)){
				    value="background-color: #98fb98;";
				    if(NumberUtils.isNumber(after)){
				    	if(Double.compare(NumberUtils.toDouble(after),NumberUtils.toDouble(pre))==0){
				    		value="";
				    	}
				    }
				}
			    applyChangeMap.put(key, value);
			}
			//==========================替代料號================================
			ApplyItemChangeBo applyItemChangeBo=SpringContextUtil.getBean(ApplyItemChangeBo.class);
			BigDecimal secondItemChangeCount=applyItemChangeBo.getSecondItemChangeCount(
					preItemForm.getaItem().getEaudittransid(),afterItemForm.getaItem().getEaudittransid());
			if(secondItemChangeCount!=null && secondItemChangeCount.compareTo(new BigDecimal("0"))!=0){
				applyChangeMap.put("listAItemSecondItemnum", "background-color: #98fb98;");
			}else{
				applyChangeMap.put("listAItemSecondItemnum", "");
			}
			//==========================供應商================================
			BigDecimal itemVendorChangeCount=applyItemChangeBo.getItemVendorChangeCount(
					preItemForm.getaItem().getEaudittransid(),afterItemForm.getaItem().getEaudittransid());
			if(itemVendorChangeCount!=null && itemVendorChangeCount.compareTo(new BigDecimal("0"))!=0){
				applyChangeMap.put("listAInvvendor", "background-color: #98fb98;");
			}else{
				applyChangeMap.put("listAInvvendor", "");
			}
			//==========================Spec================================
			List<Object[]> itemSpecChange=applyItemChangeBo.getItemSpecChangeCount(
					preItemForm.getaItem().getEaudittransid(),afterItemForm.getaItem().getEaudittransid());
			for(Object[] o:itemSpecChange){
				applyChangeMap.put(o[0].toString(), "background-color: #98fb98;");
			}
			//==========================附檔================================
			FileUploadBO fileUploadBO=SpringContextUtil.getBean(FileUploadBO.class);
			Map attachmentChange=fileUploadBO.getAttachmentChangeCount(
					preItemForm.getaItem().getItemnum(), afterItemForm.getaItem().getItemnum());
			Iterator attachmentIter=attachmentChange.entrySet().iterator();
			while(attachmentIter.hasNext()){
				Map.Entry entry= (Map.Entry) attachmentIter.next();
			    Object key = entry.getKey();
			    String value = entry.getValue().toString();
			    applyChangeMap.put(key, value.equals("0")?"":"background-color: #98fb98;");
			}
		}
		afterItemForm.setApplyChangeMap(applyChangeMap);
		return afterItemForm;
	}

	@Override
	public ItemForm setPreItemForm(ItemForm afterItemForm) {
		ItemForm preItemForm = new ItemForm();
		if(afterItemForm.getaItem().getPreEaudittransid()!=null){
		BeanUtils.copyProperties(afterItemForm, preItemForm);
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		return this.selectApply(preItemForm, applyItemBo.getAItem(
				afterItemForm.getaItem().getPreEaudittransid()));
		}
		return preItemForm;
	}

	@Override
	public boolean onVendorRemarkFlag(ItemForm itemForm) {
		ApplyItemBo applyItemBo=SpringContextUtil.getBean(ApplyItemBo.class);
		ItemMappingBO itemMappingBO=SpringContextUtil.getBean(ItemMappingBO.class);
		FileUploadBO fileUploadBO=SpringContextUtil.getBean(FileUploadBO.class);
		if (StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())) {  //規格異動
			itemForm.setItemMapping(itemMappingBO.getItemMapping(null,itemForm.getaItem().getOriitemnum()));
		}else{  //料號新增
			itemForm.setItemMapping(itemMappingBO.getItemMapping(itemForm.getaItem().getEaudittransid(),null));
		}
		//舊料號不卡兩家報價單&指廠說明
		if (itemForm.getItemMapping()==null){  //沒有舊料號
			// IF="Y"，指廠說明的特殊條件不檢查
			if(StringUtils.isBlank(itemForm.getaItemAttribute().getSecondItemFlag()) ||  
					!itemForm.getaItemAttribute().getSecondItemFlag().equals("Y")){
				// IF="Y"，指廠說明的特殊條件不檢查
				if(StringUtils.isBlank(itemForm.getaItemAttribute().getSecondSourceItemnum()) || 
					!itemForm.getaItemAttribute().getSecondSourceItemnum().equals("Y")){
					//供應商或報價單若少於2家
					if(itemForm.getListListAInvvendorVO().size()<2 ||    
							fileUploadBO.getAttachmentList(itemForm.getaItem().getItemnum(),
									FileCategory.ITEM_ATTACHMENT, "").size()<2 ){
						//規格異動時，若供應商及報價單的欄位皆未異動
						if (StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())) {  //規格異動
							//==============================供應商判斷================================
							List<ListAInvvendorVO> preVendor=applyItemBo.getListAInvvendorVO(itemForm.getaItem().getPreEaudittransid().intValue());
							boolean vendorValidate=ApplyUtils.validateChangeVendor(preVendor,
									itemForm.getListListAInvvendorVO());
							//==============================報價單判斷================================
							AItem aitem=applyItemBo.getAItem(itemForm.getaItem().getPreEaudittransid());
							Map attachmentChange=fileUploadBO.getAttachmentChangeCount(
									aitem.getItemnum(), itemForm.getaItem().getItemnum());
							//(報價單欄位異動與否同現行以檔案數及檔案名稱是否相同來判斷)
							if(vendorValidate ||
									(attachmentChange.get(FileCategory.ITEM_ATTACHMENT)!=null &&
									!attachmentChange.get(FileCategory.ITEM_ATTACHMENT).toString().equals("0"))){
								return true;
							}
						}else if (StringUtils.isBlank(itemForm.getaItem().getOriitemnum())) {  //料號新增
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemForm setApplySubmit(ItemForm itemForm) {
		//==============更新價格=========================
		setUnitCost(itemForm);
		// =============指廠說明FLAG===================================
		if(Utility.equalsOR(itemForm.getaItem().getCommoditygroup(), ItemType.R2,ItemType.R94)){
			itemForm.setVendorRemarkFlag(onVendorRemarkFlag(itemForm));
		}
		//===========second source 預設值==================
		itemForm.setSecondDefault();

		if(StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())){ //規格異動
			//===========second Remark======================
			setSecondSourceRemark(itemForm);
		}
		
		return itemForm;
	}

	@Override
	public String getSignParameter(ItemForm itemForm, String loginEmpNo) {
		JSONObject json = new JSONObject();
		try {
			json.put("ORGANIZATION_CODE", itemForm.getaItem().getOrganizationCode());
			json.put("CLASSSTRUCTUREID", itemForm.getaItem().getClassstructureid());
			json.put("ITEMNUM", itemForm.getaItem().getOriitemnum());
			json.put("SIMPLE_SIGN_FLAG",itemForm.isSimpleSignFlag());
			json.put("VENDOR_REMARK_FLAG", itemForm.isVendorRemarkFlag());
			json.put("GP_CONTROL_FLAG", itemForm.getaItemAttribute().getGpControlFlag());
			json.put("GP_PRODUCT_CATEGORY", itemForm.getaItemAttribute().getGpProductCategory());
			json.put("GP_DELIVERY_TYPE", itemForm.getaItemAttribute().getGpDeliveryType());
			json.put("GP_REMAIN_TYPE", itemForm.getaItemAttribute().getGpRemainType());
			
			json.put("processId", getSignProcessId(itemForm));
			json.put("empno", loginEmpNo);
			//json.put("price", new BigDecimal("0"));
			json.put("price", itemForm.getaItemAttribute().getUnitcost());
			json.put("EAUDITTYPE", itemForm.getaItem().getEaudittype());
			json.put("comment", itemForm.getSignComment());
		} catch (JSONException e) {
			throw new RuntimeException(e.toString());
		}
		return json.toString();
	}

	@Override
	public void setStorageCategory(ItemForm itemForm,Classstructure classstructure) {
		if(classstructure==null){
			itemForm.setStorageCategory("");
		}else{
			SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
			itemForm.setStorageCategory(bean.getParameterMap().get(classstructure.getStoreCategory()));
		}
	}

	@Override
	public void setGpDefault(ItemForm itemForm,String classstructureid) {
		ClassstructureGpBO ClassstructureGpBO = SpringContextUtil.getBean(ClassstructureGpBO.class);
		ClassstructureGp classstructureGp=ClassstructureGpBO.getClassstructureGp(classstructureid);
		if(classstructureGp!=null){
			itemForm.getaItemAttribute().setGpProductCategory(GpType.GP_TYPE2.name());
			itemForm.getaItemAttribute().setGpDeliveryType(classstructureGp.getDeliveryType());
			itemForm.getaItemAttribute().setGpRemainType(classstructureGp.getRemainType());
			GpType.setGpValue(itemForm.getaItemAttribute());
		}else{
			itemForm.getaItemAttribute().setGpProductCategory(GpType.GP_TYPE1.name());
		}
		
	}

	@Override
	public void setSecondSource(ItemForm itemForm) {
		if(StringUtils.isNotBlank(itemForm.getaItemAttribute().getSecondSourceItemnum())){
			ItemspecBO itemspecBO=SpringContextUtil.getBean(ItemspecBO.class);
			List list=itemspecBO.getListBySecondSource(itemForm.getaItemAttribute().getSecondSourceItemnum());
			itemForm.setItemSpecMap(itemspecBO.getMap(list));
			itemForm.getListAItemspecVO().stream()
			.filter(s->itemForm.getItemSpecMap().get(s.getAssetattrid())!=null)
			.forEach(s->{
				Itemspec itemspec=itemForm.getItemSpecMap().get(s.getAssetattrid());
				s.setNumvalue(itemspec.getNumvalue());
				s.setAlnvalue(itemspec.getAlnvalue());
				s.setMeasureunitid(itemspec.getMeasureunitid());
			});
		}else{
			itemForm.setSecondDefault();
		}
	}

	@Override
	public void setSecondSourceRemark(ItemForm itemForm) {
		itemForm.getaItemAttribute().setSecondSourceRemark(null);
		ItemBO itemBO =SpringContextUtil.getBean(ItemBO.class);
		ItemspecBO itemspecBO=SpringContextUtil.getBean(ItemspecBO.class);
		List<Item> items=itemBO.getItemList(itemForm.getaItem().getOriitemnum().substring(0,11));
		if(CollectionUtils.isNotEmpty(items)){
			Map<String, Itemspec> spec=itemspecBO.getMap(itemspecBO.getListBySecondSource(
					itemForm.getaItem().getOriitemnum()));
			if(!ApplyValidationUtils.validate2nd(spec, itemForm.getListAItemspecVO())){
				String itemnums=items.stream().map((l)->l.getItemnum())
                        .collect(Collectors.joining(","));
				itemForm.getaItemAttribute().setSecondSourceRemark(itemnums);
			}
		}
		
	}
}
