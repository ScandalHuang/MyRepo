package mro.app.applyItem.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyItemTransferSiteUtils;
import mro.app.applyItem.bo.ApplyItemTransferSiteBo;
import mro.app.applyItem.service.ItemTransferSiteInterface;
import mro.app.applyItem.service.impl.ItemTransferSiteImpl;
import mro.app.commonview.jsf.ListClassstructureBean;
import mro.app.commonview.jsf.ListItemCommonBean;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.entity.Item;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ItemTransferSiteForm;

import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemTransferSiteBean")
@ViewScoped
public class ApplyItemTransferSiteBean implements Serializable {
	
	private static final long serialVersionUID = 3730686031763011965L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;
	
	@ManagedProperty(value = "#{ListClassstructureBean}")
	private ListClassstructureBean listClassstructureBean;

	private ItemTransferSiteForm itemTransferSiteForm;

	private ItemTransferSiteInterface impl;
	
	public ApplyItemTransferSiteBean() {

	}

	@PostConstruct
	public void init() {
		impl = new ItemTransferSiteImpl();
		itemTransferSiteForm=new ItemTransferSiteForm();
		impl.setParameter(itemTransferSiteForm);
		listClassstructureBean.setAddButton(true); // 開啟分類選取功能
		listClassstructureBean.onSearchView(false,ItemType.R94.toString());// 類別結構清單	
		this.getApplyList(0);
	}

// =========================================inital===============================================================
	public void inital() {
		itemTransferSiteForm.itemTransferSiteIntial();
	}
// ================================申請單list============================================================
	public void getApplyList(int activeI) {
		this.inital();
		ApplyItemTransferSiteBo applyItemTransferSiteBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteBo.class);
		itemTransferSiteForm.setListItemTransferHeaderApply(
				applyItemTransferSiteBo.getItemTransferHeaderApplyList(loginInfoBean.getEmpNo()));
		itemTransferSiteForm.setActiveIndex(activeI);
		
		itemTransferSiteForm.setSignMap(SourceCategory.getInprgMap(
				itemTransferSiteForm.getListItemTransferHeaderApply()));
	}
	public void SelectItemTransferHeaderApply(SelectEvent event) { // 選取異動申請單
		this.SelectItemTransferHeaderApply((ItemTransferHeaderApply) event.getObject());
	}
	public void SelectItemTransferHeaderApply(ItemTransferHeaderApply itemTransferHeaderApply) { // 選取異動申請單	
		itemTransferSiteForm=impl.selectApply(
				itemTransferSiteForm,itemTransferHeaderApply);
		
		// 非審核中  可以進行編輯
		if (SignStatus.valueOf(itemTransferSiteForm.getItemTransferHeaderApply().getStatus()).editStatus()){
			itemTransferSiteForm.setEditButton(true);
			listItemCommonBean.setClassstructure(itemTransferHeaderApply.getClassstructureid(),
					null,ItemStatusType.TYPE_A);
			if(itemTransferHeaderApply.getAction().equals(LocationSiteActionType.S.toString())){
				listItemCommonBean.setLocationSiteInfo(
						itemTransferHeaderApply.getLocationSite(),LocationSiteActionType.I);
			}else if(itemTransferHeaderApply.getAction().equals(LocationSiteActionType.I.toString())){
				listItemCommonBean.setLocationSiteInfo(
						itemTransferHeaderApply.getLocationSite(),LocationSiteActionType.S);
			}
			
		}
	}

	// =========================================Action===============================================================
	//批次上傳開單
	public void onBatchApply() { 
		GlobalGrowl message = new GlobalGrowl();
		impl.onBatchApply(itemTransferSiteForm, loginInfoBean.getPerson());
		this.getApplyList(0);
		message.addInfoMessage("批次開單", "批次開單 成功!");
	}
	
	//新增料號
	public void setSelectItem(Item item){
		impl.addItem(itemTransferSiteForm, item,null);
	}
	//刪除料號
	public void deleteItemTransferLineApply(ItemTransferLineApply itemTransferLineApply){
		impl.deleteItemTransferLineApply(itemTransferSiteForm, itemTransferLineApply);
	}
	//簽核預覽
	public void onSignPreView(){ 
		itemTransferSiteForm=impl.onSignPreView(itemTransferSiteForm);
	}
	
	// 刪除申請單
	public void onDelete() { 
		impl.onDelete(itemTransferSiteForm);
		this.getApplyList(0);
	}
	
	 // 新建申請單
	public void newItemTransferHeaderApply() {
		this.inital();
		itemTransferSiteForm.onNewApply(loginInfoBean.getPerson(), 
				loginInfoBean.getLocationMap().getLocationSiteMap());
	}
	
	// 送審申請單
	public void Submit(String type) {
		GlobalGrowl message = new GlobalGrowl();
		String warnMessage=ApplyItemTransferSiteUtils.vaildate(itemTransferSiteForm);
		if (warnMessage.length()==0) { //無錯誤訊息
			this.onApplySave(type);
		}else{
			message.addErrorMessage("Error",warnMessage.toString());
		}
	}
	
	 // 儲存申請單
	public void onApplySave(String type) {
		boolean applyb=impl.onApplySave(itemTransferSiteForm, ActionType.valueOf(type));
		if(applyb){
			if (type.equals("save")) {
				ItemTransferHeaderApply itemTransferHeaderApply=itemTransferSiteForm.getItemTransferHeaderApply();
				this.getApplyList(1);
				this.SelectItemTransferHeaderApply(itemTransferHeaderApply);
			} else if (type.equals("submit")) {
				this.getApplyList(0);
			}
		}else{
			itemTransferSiteForm.setActiveIndex(1);
		}
	}
	
	//選取類別
	public void setClassstructureuid(String classstructureid){
		itemTransferSiteForm.getItemTransferHeaderApply().setClassstructureid(classstructureid);
	}

	public void getDownLoadFile(){
		impl.setDownLoadFile(itemTransferSiteForm);
	}
	
	public void uploadExcel(File file) { //上傳excel
		FileUploadExcelInterfaces fileImpl = new FileUploadExceImpl();
		itemTransferSiteForm.setBatchList(fileImpl.converList(null,file,
				Arrays.asList("ITEMNUM","LOCATION_SITE","ACTION"),new GlobalGrowl()));
		fileImpl.message(itemTransferSiteForm.getBatchList());
	}
	
	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ListItemCommonBean getListItemCommonBean() {
		return listItemCommonBean;
	}

	public void setListItemCommonBean(ListItemCommonBean listItemCommonBean) {
		this.listItemCommonBean = listItemCommonBean;
	}

	public ListClassstructureBean getListClassstructureBean() {
		return listClassstructureBean;
	}

	public void setListClassstructureBean(
			ListClassstructureBean listClassstructureBean) {
		this.listClassstructureBean = listClassstructureBean;
	}

	public ItemTransferSiteForm getItemTransferSiteForm() {
		return itemTransferSiteForm;
	}

	public void setItemTransferSiteForm(ItemTransferSiteForm itemTransferSiteForm) {
		this.itemTransferSiteForm = itemTransferSiteForm;
	}

}
