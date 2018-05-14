package mro.app.applyItem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mro.app.applyItem.Utils.ApplyItemTransferSiteUtils;
import mro.app.applyItem.bo.ApplyItemTransferSiteBo;
import mro.app.applyItem.service.ItemTransferSiteInterface;
import mro.app.applyQuery.bo.ApplyItemTransferSiteQueryBo;
import mro.app.item.bo.ListItemBO;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ItemSiteTransferLineStatus;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AttachmentBO;
import mro.base.bo.ItemAttributeBO;
import mro.base.bo.ItemBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.Person;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.ItemTransferSiteForm;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ItemTransferSiteImpl implements ItemTransferSiteInterface {

	@Override
	public ItemTransferSiteForm selectApply(ItemTransferSiteForm itemTransferSiteForm,
			ItemTransferHeaderApply itemTransferHeaderApply) {
		itemTransferSiteForm.itemTransferSiteIntial();
		itemTransferSiteForm.setItemTransferHeaderApply(itemTransferHeaderApply);
		ApplyItemTransferSiteQueryBo applyItemTransferSiteQueryBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteQueryBo.class);
		itemTransferSiteForm.setListItemTransferLineApply(
				applyItemTransferSiteQueryBo.getItemTransferLineApplyList(
						itemTransferSiteForm.getItemTransferHeaderApply().getApplyHeaderId()));
		// =============================檔案下載====================================================
		this.setDownLoadFile(itemTransferSiteForm);
		//=============================簽核歷程=========================================================
		itemTransferSiteForm.setSignHistoryUrl(WorkflowActionUtils.onSignHistory(
				itemTransferSiteForm.getItemTransferHeaderApply().getTaskId()));
		// ============================Page===================================================
		itemTransferSiteForm.setActiveIndex(1);
		
		return itemTransferSiteForm;
	}
	
	@Override
	public void setDownLoadFile(ItemTransferSiteForm form) {
		ItemTransferHeaderApply header=form.getItemTransferHeaderApply();
		
		form.setActiveIndex(1);
		AttachmentBO attachmentBO=SpringContextUtil.getBean(AttachmentBO.class);
		
		form.setFileHeaderType(FileCategory.SITE_HEADER_ATTACHMENT);
		form.getDowloadFileMap().putAll(attachmentBO.getMap(
				header.getApplyHeaderId().toString(), form.getFileHeaderType(), true));
		
		form.setFileLineType(FileCategory.SITE_LINE_ATTACHMENT);
		form.getDowloadFileMap().putAll(attachmentBO.getMap(
				form.getLineIds(),form.getFileLineType(),false));
	}
	
	@Override
	public ItemTransferSiteForm onSignPreView(
			ItemTransferSiteForm itemTransferSiteForm) {
		//取得簽核url
		itemTransferSiteForm.setSignPreViewUrl(
				WorkflowActionUtils.onSignPreView(getSignParameter(itemTransferSiteForm)));
		return itemTransferSiteForm;
	}

	@Override
	public void onDelete(ItemTransferSiteForm itemTransferSiteForm) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemTransferSiteBo applyItemTransferSiteBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteBo.class);
		applyItemTransferSiteBo.onDelteApply(itemTransferSiteForm.getItemTransferHeaderApply());
		message.addInfoMessage("刪除申請單", "刪除申請單 成功!");
	}

	@Override
	public boolean onApplySave(ItemTransferSiteForm itemTransferSiteForm,ActionType type) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemTransferSiteBo applyItemTransferSiteBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteBo.class);
		String warnMessage=ApplyItemTransferSiteUtils.vaildateHeader(itemTransferSiteForm.getItemTransferHeaderApply());
		if (warnMessage.length()==0) { //無錯誤訊息
			
			if (type.compareTo(ActionType.save)==0) { //儲存
				applyItemTransferSiteBo.onApplySave(itemTransferSiteForm,type);
				message.addInfoMessage("Save", "Save successful.");
				return true;
			}else if(type.compareTo(ActionType.submit)==0) {
				this.setExtraRemark(itemTransferSiteForm); //更新pr申請單錯誤訊息
				itemTransferSiteForm.getItemTransferHeaderApply().setTaskId(
						WorkflowActionUtils.onItemTransferApplySumit(
								itemTransferSiteForm.getItemTransferHeaderApply(),
								getSignParameter(itemTransferSiteForm)));
				if (itemTransferSiteForm.getItemTransferHeaderApply().getTaskId() != null) {  //簽核成功
					applyItemTransferSiteBo.onApplySave(itemTransferSiteForm,type);
					message.addInfoMessage("Submit", "Submit successful.");
					return true;
				} else {
					message.addErrorMessage("送審失敗", "請重新送審或與MIS聯繫!");
				}
			}
		} else {
			message.addErrorMessage("Error",warnMessage.toString());
		}
		return false;
	}

	@Override
	public String getSignParameter(ItemTransferSiteForm itemTransferSiteForm) {
		JSONObject json = new JSONObject();
		try {
			json.put("APPLY_HEADER_ID", itemTransferSiteForm.getItemTransferHeaderApply().getApplyHeaderId());
			json.put("ACTION", itemTransferSiteForm.getItemTransferHeaderApply().getAction());
			json.put("LOCATION_SITE", itemTransferSiteForm.getItemTransferHeaderApply().getLocationSite());
			json.put("CLASSSTRUCTUREID", itemTransferSiteForm.getItemTransferHeaderApply().getClassstructureid());
			json.put("itemList", getItemList(itemTransferSiteForm.getListItemTransferLineApply()));
			
			json.put("processId", WorkflowUtils.getItemTransferProcessId());
			json.put("empno", itemTransferSiteForm.getItemTransferHeaderApply().getCreateBy());
			//json.put("price", new BigDecimal("0"));
			json.put("price", getItemCost(itemTransferSiteForm.getListItemTransferLineApply()));
			json.put("comment", itemTransferSiteForm.getSignComment());
		} catch (JSONException e) {
			throw new RuntimeException(e.toString());
		}
		

		return json.toString();
	}

	@Override
	public List getItemList(
			List<ItemTransferLineApply> listItemTransferLineApply) {
		List<String> itemList=new ArrayList<String>();
		for(ItemTransferLineApply i:listItemTransferLineApply){
			itemList.add(i.getItemnum());
		}
		return itemList;
	}
	
	@Override
	public BigDecimal getItemCost(List<ItemTransferLineApply> listItemTransferLineApply) {
		ListItemBO itemBO = SpringContextUtil.getBean(ListItemBO.class);
		BigDecimal ItemCost = new BigDecimal("0");
		for(ItemTransferLineApply i:listItemTransferLineApply){
			ItemAttribute item = itemBO.getItemAttribute(i.getItemid());
			if(ItemCost.compareTo(item.getUnitcost())==-1) {//取UnitCost最大值
				ItemCost = item.getUnitcost();
			}
		}
		return ItemCost;
	}

	@Override
	public void setParameter(ItemTransferSiteForm itemTransferSiteForm) {
		itemTransferSiteForm.setActionTypeMapping(LocationSiteActionType.getMap());
		itemTransferSiteForm.setActionTypeOption(
				Utility.swapMap(itemTransferSiteForm.getActionTypeMapping()));
		itemTransferSiteForm.setLineStatusMapping(ItemSiteTransferLineStatus.getMap());
		

		LocationSiteMapBO locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
		itemTransferSiteForm.setSiteOption(locationSiteMapBO.getOption());
	}

	@Override
	public void deleteItemTransferLineApply(ItemTransferSiteForm itemTransferSiteForm,
			ItemTransferLineApply itemTransferLineApply) {
		if (itemTransferLineApply != null) {
			if(itemTransferLineApply.getApplyLineId()!=null){
				itemTransferSiteForm.getListDeleteItemTransferLineApply().add(itemTransferLineApply);
			}
			itemTransferSiteForm.getListItemTransferLineApply().remove(itemTransferLineApply);
			int lineNum = 1;
			for (ItemTransferLineApply i : itemTransferSiteForm.getListItemTransferLineApply()) {
				i.setLineNum(new BigDecimal(lineNum));
				lineNum++;
			}
		}
		
	}

	@Override
	public void addItem(ItemTransferSiteForm itemTransferSiteForm, Item item,String lineRemark) {
		if(item!=null){
			ItemTransferLineApply i = new ItemTransferLineApply();
			i.setItemnum(item.getItemnum());
			i.setItemid(item.getItemid());
			i.setLineNum(new BigDecimal(itemTransferSiteForm.getListItemTransferLineApply().size()+1));
			i.setDescription(item.getDescription());
			i.setLineRemark(lineRemark);
			itemTransferSiteForm.getListItemTransferLineApply().add(i);
		}
		
	}

	@Override
	public void setExtraRemark(ItemTransferSiteForm itemTransferSiteForm) {
		String key="<br />";
		ItemTransferHeaderApply header=itemTransferSiteForm.getItemTransferHeaderApply();
		if (LocationSiteActionType.valueOf(header.getAction()).validateS()) { // 凍結
			List<String> itemList = new ArrayList<String>();
			for (ItemTransferLineApply i : itemTransferSiteForm.getListItemTransferLineApply()) {
				itemList.add(i.getItemnum());
				i.setExtraRemark("");
			}
			
			StringBuffer warnMessage=new StringBuffer();
			if (itemTransferSiteForm.getItemTransferHeaderApply().getAction()
					.equals(LocationSiteActionType.S.name())) { // 凍結
				//============2016.05.06 修改 不卡控但存入extra_rema===================
				// =============================送審中mro pr==========================
				warnMessage.append(ApplyItemTransferSiteUtils.vaildateInprgPrline(itemList,
					itemTransferSiteForm.getItemTransferHeaderApply().getLocationSite()));
				// =============================送審中oracle pr=======================
				warnMessage.append(ApplyItemTransferSiteUtils.vaildateInprgOraclePR(itemList,
					itemTransferSiteForm.getItemTransferHeaderApply().getLocationSite()));
				// =============================有控管量==========================
				warnMessage.append(ApplyItemTransferSiteUtils.vaildateInvbalances(itemList,
					itemTransferSiteForm.getItemTransferHeaderApply().getLocationSite()));
			}
			String[] remarks=warnMessage.toString().split(key);
			for(String remark:remarks){
				int i=remark.indexOf("R");
				if(i!=-1){
					String itemnum=remark.substring(i, i+12);//12碼料號
					itemTransferSiteForm.getListItemTransferLineApply().stream()
					.filter(a -> Objects.equals(a.getItemnum(), itemnum))
					.forEach(a->a.setExtraRemark(a.getExtraRemark()+remark+key));
				}
			}
			
		}
	}

	@Override
	public void onBatchApply(ItemTransferSiteForm form,Person person) {
		SystemConfigBean cBean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		ItemBO itemBO=SpringContextUtil.getBean(ItemBO.class);
		ApplyItemTransferSiteBo siteBo=SpringContextUtil.getBean(ApplyItemTransferSiteBo.class);
		Map<String,ItemTransferSiteForm> map=new HashMap<String,ItemTransferSiteForm>();
		List<ItemTransferSiteForm> list=new ArrayList<ItemTransferSiteForm>();
		for(Map<String,String> l:form.getBatchList()){
			String itemnum = l.get("ITEMNUM");
			String classstructureid = itemnum.substring(0,5);
			String locationSite=cBean.getlSiteOption().get(l.get("LOCATION_SITE"));
			String key=classstructureid +locationSite+l.get("ACTION");
			if(map.get(key)==null){
				ItemTransferSiteForm from=new ItemTransferSiteForm();
				from.onNewApply(person,locationSite, l.get("ACTION"), classstructureid);
				list.add(from);
				map.put(key, from);
			}
			this.addItem(map.get(key), itemBO.getItem(itemnum),l.get("LINE_REMARK"));
		}
		list.stream().forEach(l->siteBo.onApplySave(l,ActionType.save));
	}
}
