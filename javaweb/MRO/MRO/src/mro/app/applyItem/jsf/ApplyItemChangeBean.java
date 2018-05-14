package mro.app.applyItem.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.bo.ApplyItemChangeBo;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.commonview.jsf.FileUploadBean;
import mro.app.commonview.jsf.ListItemCommonBean;
import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.Item;
import mro.base.entity.Person;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ItemForm;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ApplyItemChangeBean")
@ViewScoped
public class ApplyItemChangeBean implements Serializable {
	
	private static final long serialVersionUID = 7042254576843503326L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;

	@ManagedProperty(value = "#{FileUploadBean}")
	private FileUploadBean fileUploadBean;
	
	private ItemForm itemForm;
	private ItemImpl itemImpl;
	

	public ApplyItemChangeBean() {

	}

	@PostConstruct
	public void init() {
		itemForm = new ItemForm();
		itemImpl=new ItemImpl();
		this.getSepecEditApplyList(0);
		itemForm=itemImpl.setParameter(itemForm);
		itemForm=itemImpl.setChangeParameter(itemForm);
		//規格異動不能選取SECOND_TYPE2
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		itemForm.getSecondItemOption().remove(bean.getParameterMap().get(
				SecondItemType.SECOND_TYPE2.name()));
	}

	// =========================================inital===============================================================
	public void inital() { // 開立新單
		itemForm.itemIntial();
	}

	// ================================申請單list============================================================
	public void getSepecEditApplyList(int activeI) {
		this.inital();
		ApplyItemChangeBo applyItemChangBo = SpringContextUtil.getBean(ApplyItemChangeBo.class);
		itemForm.setListAItem(applyItemChangBo.getAItemList(loginInfoBean.getUserId()));
		itemForm.setActiveIndex(activeI);
		itemForm.setSignMap(SourceCategory.CHANGE_ITEM.getInprgMap(loginInfoBean.getEmpNo()));
	}

	// ========================================料號選取=======================================================================
	public void setSelectItem(Item item) { //料號選取
		if(item!=null){ 
			if(itemForm.getaItem()==null || 
				SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.ACTIVE)) // 生效料號選取
			{
				AItem aItem=new AItem();
				BeanUtils.copyProperties(item, aItem);
				aItem.setItem(item);
				aItem.setOriitemnum(item.getItemnum());
				itemForm=itemImpl.selectApply(itemForm, aItem);
			}else{  //替代料號
				itemForm=itemImpl.setSelectItem(itemForm, item);
			}
		}
	}
	public void selectApply(SelectEvent event) { 
		this.selectApply((AItem) event.getObject());
	}
	public void selectApply(AItem aItem) { // 編編清單料號選取
		itemForm=itemImpl.selectApply(itemForm, aItem);
		
		if (SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.NEW) // 可以編輯 重新設定資料 避免attribute有被更動
				|| SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.REJECT)) {
			itemForm=itemImpl.setClassstructureuid(itemForm, itemForm.getaItem().getClassstructureid(),
					loginInfoBean.getUserId(), loginInfoBean.getPerson());
			itemForm=itemImpl.setAItemSimple(itemForm); //簡易版料號設定
		} 
		ApplyItemChangeBo applyItemChangeBo = SpringContextUtil.getBean(ApplyItemChangeBo.class);
		//==============================異動更新提醒==========================================================
		if(applyItemChangeBo.getChangeItemCount(aItem.getItem().getItemid(), aItem.getCreateDate())>0
				&& !SignStatus.valueOf(aItem.getStatus()).equals(SignStatus.SYNC)){
			GlobalGrowl message = new GlobalGrowl();
			message.addErrorMessage("Error","此料號已被更新，請刪除此異動後再重新申請異動!<br />");
		}
	}
	// ===========================================Action================================================================
	public void onSignPreView(){  //簽核預覽
		itemForm=itemImpl.setApplySubmit(itemForm);
		itemForm=itemImpl.onSignPreView(itemForm, loginInfoBean.getEmpNo());
	}
	public void newApply(){
		this.inital();
		itemForm.setActiveIndex(1);
	}
	public void onApplyDelete() { // 取消異動
		itemImpl.onApplyDelete(itemForm);
		this.getSepecEditApplyList(0);// 申請單list
	}

	public void onItemSelectSubmit() throws Exception{ // 異動送審
		GlobalGrowl message = new GlobalGrowl();
		itemForm=itemImpl.setApplySubmit(itemForm);

		//=============================Submit===========================================
		if (ApplyUtils.validate(itemForm,message)) {
			this.onItemSave("submit");
		}
	}
	public void onItemSave(String action){
		boolean applyb=itemImpl.onApplySave(itemForm, action,loginInfoBean.getEmpNo());
		if(applyb){
			if (action.equals("save")) {
				AItem aItem=itemForm.getaItem();
				this.getSepecEditApplyList(1);// 申請單list
				this.selectApply(aItem);
			} else if (action.equals("submit")) {
				this.getSepecEditApplyList(0);// 申請單list
			}
		}else{
			itemForm.setActiveIndex(1);
		}
	}
	public void onItemSelectEdit(String action) throws Exception { // 異動申請
		onItemSelectEdit(action,loginInfoBean.getUserId(), loginInfoBean.getPerson());
	}
	public void onItemSelectEdit(String action,String userId,Person person) throws Exception { // 異動申請
		itemForm=itemImpl.setClassstructureuid(itemForm, itemForm.getaItem().getClassstructureid(),
				userId, person);
		
		itemForm.getaItem().setOrganizationCode(person.getOrganizationCode());
		//===============================前異動編號===============================================
		ApplyItemChangeBo applyItemChangeBo = SpringContextUtil
				.getBean(ApplyItemChangeBo.class);
		itemForm.getaItem().setPreEaudittransid(
				applyItemChangeBo.getpreEaudittransid(itemForm.getaItem().getItem().getItemid()));
		// ===============================儲存============================================
		onItemSave(action);
		//================================產生新檔案========================================================
		fileUploadBean.copyFile(itemForm.getaItem().getOriitemnum(),itemForm.getaItem().getItemnum(), null, false);
		itemImpl.setDownLoadFile(itemForm);
	}

	// =============================價格====================================================
	public void setUnitCost() {
		itemImpl.setUnitCost(itemForm);
	}
	// =============================供應商====================================================
	public void setCompany(VwNewvendorcodeEpmall vwNewvendorcodeEpmall) {
		itemForm=itemImpl.setCompany(itemForm, vwNewvendorcodeEpmall);
	}

	public void deleteinvvendor(ListAInvvendorVO l) {
		itemForm=itemImpl.deleteinvvendor(itemForm, l);
	}
	// =============================替代料號====================================================
	public void deleteAItemSecondItemnum(AItemSecondItemnum a) {
		itemForm=itemImpl.deleteAItemSecondItemnum(itemForm, a);
	}
	
	public void gpControlFlag(){  //設定是否為GP管控產品
		GpType.setGpValue(itemForm.getaItemAttribute());
	}
	//================================異動料號選取=============================================
	public void setItemCommonForChange(){
		listItemCommonBean.setClassstructure(ItemStatusType.TYPE_A);
	}
	//================================替代料號選取=============================================
	public void setItemCommonForSecond(){
		listItemCommonBean.setClassstructure(itemForm.getaItem().getClassstructureid(),
			ItemStatusType.TYPE_A,
			SecondItemType.valueOf(itemForm.getaItemAttribute().getSecondItemType()).secondSource());
	}
	// ===========================================================================================	
	public void checkSTRATEGY() {
		//if(itemForm.getaItemAttribute()!=null && itemForm.getaItemAttribute().getStrategyMgmtFlag().equals("Y"))
		if(Utility.equalsOR(itemForm.getaItem().getCommoditygroup(), ItemType.R2,ItemType.R94)){
		   RequestContext.getCurrentInstance().execute("PF('strategyConfirmDialog').show();");
		}
		else 
			RequestContext.getCurrentInstance().execute("PF('submitConfirmDialog').show();");
	}
	

	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

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

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

}
