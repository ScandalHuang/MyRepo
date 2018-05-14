package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.MroOrgFacilityEqForm;
import mro.app.mcMgmtInterface.service.Impl.ListMroOrgFacilityEqImpl;
import mro.base.entity.MroOrgFacilityEq;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListMroOrgFacilityEqBean")
@ViewScoped
public class ListMroOrgFacilityEqBean implements Serializable {
	private static final long serialVersionUID = 4377546399194367155L;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private MroOrgFacilityEqForm mroOrgFacilityEqForm;
	private ListMroOrgFacilityEqImpl impl;

	public ListMroOrgFacilityEqBean() {

	}

	@PostConstruct
	public void init() {
		impl=new ListMroOrgFacilityEqImpl();
		mroOrgFacilityEqForm=new MroOrgFacilityEqForm();
		mroOrgFacilityEqForm=impl.setParameter(mroOrgFacilityEqForm);
	}
	
	public void onSearch(){
		mroOrgFacilityEqForm=impl.onSearch(mroOrgFacilityEqForm);
	}

	public void onSelectAll(){
		mroOrgFacilityEqForm.setDeleteMroOrgFacilityEq((MroOrgFacilityEq[]) 
				ApplyPrUtils.onSelectAll(mroOrgFacilityEqForm.getDeleteMroOrgFacilityEq(),
						mroOrgFacilityEqForm.getFilterListMroOrgFacilityEq()));
	}
	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		mroOrgFacilityEqForm=impl.onDelete(mroOrgFacilityEqForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	
	public void uploadExcel(File file) {
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		
		//======================excel upload 額外參數====================================
		MroOrgFacilityEq mroOrgFacilityEq=new MroOrgFacilityEq();
		mroOrgFacilityEq.setReasoncode(mroOrgFacilityEqForm.getSelectReason());
		mroOrgFacilityEq.setCreateBy(loginInfoBean.getEmpNo());
		mroOrgFacilityEq.setCreateDate(new Date(System.currentTimeMillis()));
		mroOrgFacilityEq.setLocationSiteMap(mroOrgFacilityEqForm.getsLocationSiteMap());
		//===========================upload====================================================
		
		if (fileUploadExcelInterfaces.uploadExcel(mroOrgFacilityEq,file,
				Arrays.asList("LOCATION_SITE","REASONCODE","FAB","EQ_ID"),true,true) == true) {
			onSearch();
		}
	}
	// ==========================================================================================
	

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public MroOrgFacilityEqForm getMroOrgFacilityEqForm() {
		return mroOrgFacilityEqForm;
	}

	public void setMroOrgFacilityEqForm(MroOrgFacilityEqForm mroOrgFacilityEqForm) {
		this.mroOrgFacilityEqForm = mroOrgFacilityEqForm;
	}

}
