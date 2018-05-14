package mro.app.applyItem.jsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.commonview.bo.ListClassstructureBO;
import mro.app.commonview.jsf.FileUploadBean;
import mro.app.commonview.jsf.ListClassstructureBean;
import mro.app.commonview.jsf.ListItemCommonBean;
import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.Classstructure;
import mro.base.entity.Item;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ItemForm;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ApplyItemBean")
@ViewScoped
public class ApplyItemBean implements Serializable {
	
	private static final long serialVersionUID = 2568163245643314976L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{ListClassstructureBean}")
	private ListClassstructureBean listClassstructureBean;

	@ManagedProperty(value = "#{FileUploadBean}")
	private FileUploadBean fileUploadBean;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;

	private ItemForm itemForm;
	private transient ApplyItemBo applyItemBo;
	private ItemImpl itemImpl=new ItemImpl();

	public ApplyItemBean() {

	}

	@PostConstruct
	public void init() {
		applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		itemForm = new ItemForm();
		listClassstructureBean.setAddButton(true); // 開啟分類選取功能
		listClassstructureBean.onSearchView(true);// 類別結構清單
		this.getApplyList(0);// 申請單list
		ItemInterface itemInterface=new ItemImpl();
		itemForm=itemInterface.setParameter(itemForm);
	}

	// =========================================inital===============================================================
	public void inital() { // 開立新單
		itemForm.itemIntial();
	}

	// ==========================================action====================================================================
	public void onSignPreView(){  //簽核預覽
		itemForm=itemImpl.setApplySubmit(itemForm);
		itemForm=itemImpl.onSignPreView(itemForm, loginInfoBean.getEmpNo());
	}
	public void newApply() {
		this.inital();
		itemForm.newItem(loginInfoBean.getLocationMap().getOrganizationCode());
	}
	public void selectApply(SelectEvent event) { 
		this.selectApply((AItem) event.getObject());
	}
	public void selectApply(AItem aItem) { // 申請單Select
		itemForm=itemImpl.selectApply(itemForm, aItem);
		
		if (SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.NEW) // 可以編輯 重新設定資料 避免attribute有被更動
				|| SignStatus.valueOf(itemForm.getaItem().getStatus()).equals(SignStatus.REJECT)) {
			this.setClassstructureuid(itemForm.getaItem().getClassstructureid());
		} 
	}


	public void onApplySubmit() throws Exception { // 送審申請單
		GlobalGrowl message = new GlobalGrowl();
		itemForm=itemImpl.setApplySubmit(itemForm);
		//=============================Submit===========================================
		if (ApplyUtils.validate(itemForm,message)) {
			this.onApplySave("submit");
		}
	}

	public void onApplyDelete() { // 刪除申請單
		GlobalGrowl message = new GlobalGrowl();
		if (ApplyUtils.validateDelete(itemForm,message)) {
			itemImpl.onApplyDelete(itemForm);
			this.getApplyList(0);// 申請單list
		}
	}

	public void onApplySave(String action){ // 儲存草稿
		boolean applyb=itemImpl.onApplySave(itemForm, action,loginInfoBean.getEmpNo());
		if(applyb){
			if (action.equals("save")) {
				AItem aItem=itemForm.getaItem();
				this.getApplyList(1);// 申請單list
				this.selectApply(aItem);
			} else if (action.equals("submit")) {
				this.getApplyList(0);
			}
		}else{
			itemForm.setActiveIndex(1);
		}
	}

	// ================================申請單list================================================
	public void getApplyList(int activeI) {
		this.inital();
		itemForm.setListAItem(applyItemBo.getAItemList(loginInfoBean.getUserId()));
		itemForm.copyListAItem();
		itemForm.setActiveIndex(activeI);
		
		itemForm.setSignMap(SourceCategory.getInprgMap(itemForm.getListAItem()));
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
	public void setSelectItem(Item item) {
		itemForm=itemImpl.setSelectItem(itemForm, item);
	}

	public void deleteAItemSecondItemnum(AItemSecondItemnum a) {
		itemForm=itemImpl.deleteAItemSecondItemnum(itemForm, a);
	}
	// ==========================================類別結構====================================================================
	public void setClassstructureuid(String classstructureid){ // 新增分類(草稿用)
		//==============================替代料號(清空)=========================================
		if(itemForm.getaItem().getClassstructureid()!=null &&
			!itemForm.getaItem().getClassstructureid().equals(classstructureid)){
			itemForm.setListAItemSecondItemnum(new ArrayList<AItemSecondItemnum>());
			itemForm.getaItemAttribute().setSecondSourceItemnum(null);
		}
		//==============================設定類別資訊====================================
		itemForm=itemImpl.setClassstructureuid(itemForm, classstructureid,
				loginInfoBean.getUserId(), loginInfoBean.getPerson());

	}

	public Classstructure onClassstructure(String classstructureId) { // 設定類別結構
		ListClassstructureBO listClassstructureBO = SpringContextUtil
				.getBean(ListClassstructureBO.class);
		Classstructure classstructure = listClassstructureBO
				.getClassstructure(classstructureId);
		return classstructure;
	}
	public void gpControlFlag(){  //設定是否為GP管控產品
		GpType.setGpValue(itemForm.getaItemAttribute());
	}
	//================================替代料號選取=============================================
	public void setItemCommonForSecond(){
		listItemCommonBean.setClassstructure(itemForm.getaItem().getClassstructureid(),
			ItemStatusType.TYPE_A,
			SecondItemType.valueOf(itemForm.getaItemAttribute().getSecondItemType()).secondSource());
	}

	
	// ========================================================================================
	public void checkSTRATEGY() {
		//if(itemForm.getaItemAttribute()!=null && itemForm.getaItemAttribute().getStrategyMgmtFlag().equals("Y"))
		if(Utility.equalsOR(itemForm.getaItem().getCommoditygroup(), ItemType.R2,ItemType.R94)){
		   RequestContext.getCurrentInstance().execute("PF('strategyConfirmDialog').show();");
		}
		else 
			RequestContext.getCurrentInstance().execute("PF('submitConfirmDialog').show();");
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ListClassstructureBean getListClassstructureBean() {
		return listClassstructureBean;
	}

	public void setListClassstructureBean(
			ListClassstructureBean listClassstructureBean) {
		this.listClassstructureBean = listClassstructureBean;
	}

	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
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
