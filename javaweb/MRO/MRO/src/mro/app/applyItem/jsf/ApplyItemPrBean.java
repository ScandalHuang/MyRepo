package mro.app.applyItem.jsf;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.app.applyItem.service.PrInterface;
import mro.app.applyItem.service.impl.PrImpl;
import mro.app.commonview.jsf.ListEmployeeBean;
import mro.app.commonview.jsf.ListItemCommonBean;
import mro.app.commonview.jsf.ListOrgFacilityEqCommonBean;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.bo.LocationSiteMapBO;
import mro.base.bo.PrBO;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.PrForm;
import mro.viewForm.PrView;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemPrBean")
@ViewScoped
public class ApplyItemPrBean implements Serializable {
	
	private static final long serialVersionUID = 3448102488655886869L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{ListEmployeeBean}")
	private ListEmployeeBean listEmployeeBean;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;
	
	@ManagedProperty(value = "#{ListOrgFacilityEqCommonBean}")
	private ListOrgFacilityEqCommonBean listOrgFacilityEqCommonBean;

	private PrForm prForm;
	private PrView prView;
	private PrInterface prImpl;
	private transient LocationSiteMapBO locationSiteMapBO;
	
	public ApplyItemPrBean() {

	}

	@PostConstruct
	public void init() {
		prImpl=new PrImpl();
		locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();	
		prForm=new PrForm();
		prView=new PrView();
		prForm.setSelectPrtype(paramMap.get("prtype"));
		prForm.setPrtypeOption(PrType.getApplyPrTypeOption());
		prImpl.setParameter(prForm);
		this.getApplyList(0);
	}

	// =========================================inital===============================================================
	public void inital() { //
		prForm.prInital();
		prView.inital();
	}
	// ================================申請單list============================================================
	public void getApplyList(long activeI) {
		this.inital();
		prForm.setListPr(null);
		if(StringUtils.isNotBlank(prForm.getSelectPrtype())){
			PrBO prBO = SpringContextUtil.getBean(PrBO.class);
			prForm.setListPr(prBO.getPrList(loginInfoBean.getEmpNo(),
					prForm.getSelectPrtype(),ItemStatusType.TYPE_PROCESS_AC));
			prForm.setSignMap(SourceCategory.getInprgMap(prForm.getListPr()));
		}
		prForm.setActiveIndex(activeI);
	}

	// =========================================Select===============================================================
	public void SelectPrList(SelectEvent event) { // 從清單選取料號
		this.SelectPrList((Pr) event.getObject());
	}

	public void SelectPrList(Pr pr) { // 申請單讀取
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		prForm=prImpl.selectApply(prForm, pr);
		prView.setView(prForm);
		// 非審核中 且填寫人=登入者 可以進行編輯(appr不會在list中)
		if (SignStatus.valueOf(pr.getStatus()).editStatus() && 
				pr.getRequestedby().equals(loginInfoBean.getEmpNo())) {
			prForm.setEditButton(true);
			//==========================料號搜索條件=========================================
			listItemCommonBean.setClassstructure(null,pr.getPrtype(),ItemStatusType.TYPE_AC);
			listItemCommonBean.setLocationSiteInfo(prForm.getLocationSiteMap()!=null?
					prForm.getLocationSiteMap().getLocationSite():null,
					LocationSiteActionType.I);

			//==============================申請目的(設備機台代碼)============================
			if(prImpl.getMroOrgFacilityEqSize(prForm.getPr(),prForm.getLocationSiteMap())){
				prForm.setEqFlag(true);
				listOrgFacilityEqCommonBean.setProperty(prForm.getLocationSiteMap(),
						prForm.getPr().getReasoncode(),prForm.getPr().getMDept());
				listOrgFacilityEqCommonBean.onSearch();
			}
			//============預算(新申請或拒絕申請才顯示預算)==================================
			prForm.setUnUseBudget(applyItemPrBo.getUnUseBudget(
					pr.getSiteid(),pr.getMDept(),pr.getPrtype()));
		}
	}
	
	public void onEmployeeList(Long type) { // 人員新增
		listEmployeeBean.init();
		listEmployeeBean.setActiveStatus(true); //只顯示生效f
		if (type == 0) {
			// 登入者部級以上的部門
			listEmployeeBean.onDepotSearch(loginInfoBean.getPerson().getMDeptCode());
		} 
		prForm.setEmployeeType(type);
	}

	public void onEmployeeClear(Long type) { // 人員清空
		prImpl.onEmployeeClear(prForm, type);
	}

	public void setEmployee(Person person) { // 選取員工
		prImpl.setEmployee(prForm, person);
	}

	public void setSelectPrline(Prline prline,ActionType actionType) { // 選取prline
		prForm=prImpl.setSelectPrline(prForm, prline, actionType);
	}

	public void deletePrLine() { // 刪除prline
		prForm=prImpl.deletePrLine(prForm);
	}
	public void getDownLoadFile(){
		prImpl.setDownLoadFile(prForm);
	}
	// =========================================Action===============================================================
	public void onSignPreView(){  //簽核預覽
		prForm=prImpl.onSignPreView(prForm);
	}
	
	public void onCan() { // 取消申請單
		prImpl.onCan(prForm);
		this.getApplyList(0);
	}

	public void onDelete() { // 刪除申請單
		prImpl.onDelete(prForm);
		this.getApplyList(0);
	}

	public void NewPr() { // 新建PR
		this.inital();
		prForm.setEditButton(true);
		prForm.onNewPr(loginInfoBean.getPerson(), null);
		prForm.setActiveIndex(1);
	}

	public void Submit(String type) {
		this.onApplySave(type);
	}

	public void onApplySave(String type) { // 儲存申請單
		ActionType actionType=ActionType.valueOf(type);
		boolean applyb=prImpl.onApplyProcess(prForm, actionType, loginInfoBean.getPerson(),
				new GlobalGrowl());
		if(applyb){
			if (actionType.compareTo(ActionType.save)==0) {
				Pr pr=prForm.getPr();
				this.getApplyList(1);
				this.SelectPrList(pr);
			} else if (actionType.compareTo(ActionType.submit)==0) {
				this.getApplyList(0);
			}
		}else{
			prForm.setActiveIndex(1);
		}
	}
	public void onSelectPrtype(){
		PrInterface prInterface=new PrImpl();
		prInterface.setReasonFlag(prForm);
	}
	
	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ListEmployeeBean getListEmployeeBean() {
		return listEmployeeBean;
	}

	public void setListEmployeeBean(ListEmployeeBean listEmployeeBean) {
		this.listEmployeeBean = listEmployeeBean;
	}

	public ListItemCommonBean getListItemCommonBean() {
		return listItemCommonBean;
	}

	public void setListItemCommonBean(ListItemCommonBean listItemCommonBean) {
		this.listItemCommonBean = listItemCommonBean;
	}

	public PrForm getPrForm() {
		return prForm;
	}

	public void setPrForm(PrForm prForm) {
		this.prForm = prForm;
	}

	public PrView getPrView() {
		return prView;
	}

	public void setPrView(PrView prView) {
		this.prView = prView;
	}

	public ListOrgFacilityEqCommonBean getListOrgFacilityEqCommonBean() {
		return listOrgFacilityEqCommonBean;
	}

	public void setListOrgFacilityEqCommonBean(
			ListOrgFacilityEqCommonBean listOrgFacilityEqCommonBean) {
		this.listOrgFacilityEqCommonBean = listOrgFacilityEqCommonBean;
	}
	
}
