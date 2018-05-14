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
import mro.app.mcMgmtInterface.form.MroOrgFacilityForm;
import mro.app.mcMgmtInterface.service.ListMroOrgFacilityInterface;
import mro.app.mcMgmtInterface.service.Impl.ListMroOrgFacilityImpl;
import mro.base.entity.MroOrgFacility;
import mro.base.entity.SapPlantAttribute;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListMroOrgFacilityBean")
@ViewScoped
public class ListMroOrgFacilityBean implements Serializable {
	private static final long serialVersionUID = 7397800108344115760L;


	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private MroOrgFacilityForm mroOrgFacilityForm;
	private ListMroOrgFacilityImpl impl;
	
	public ListMroOrgFacilityBean() {

	}

	@PostConstruct
	public void init() {
		impl=new ListMroOrgFacilityImpl();
		mroOrgFacilityForm=new MroOrgFacilityForm();
		this.onSearch();
	}
	
	public void onSearch(){
		mroOrgFacilityForm=impl.onSearch(mroOrgFacilityForm);
	}

	public void onSelectAll(){
		mroOrgFacilityForm.setDeleteMroOrgFacility((MroOrgFacility[]) 
				ApplyPrUtils.onSelectAll(mroOrgFacilityForm.getDeleteMroOrgFacility(),
						mroOrgFacilityForm.getFilterListMroOrgFacility()));
	}
	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		mroOrgFacilityForm=impl.onDelete(mroOrgFacilityForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}

	public void uploadExcel(File file) {
		MroOrgFacility mroOrgFacility=new MroOrgFacility();
		mroOrgFacility.setCreateBy(loginInfoBean.getEmpNo());
		mroOrgFacility.setCreateDate(new Date(System.currentTimeMillis()));
		mroOrgFacility.setLocationSiteMap(mroOrgFacilityForm.getsLocationSiteMap());
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(mroOrgFacility,file,
				Arrays.asList("LOCATION_SITE","DEPT_NO"),true,true,ListMroOrgFacilityImpl.class) == true) {
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

	public MroOrgFacilityForm getMroOrgFacilityForm() {
		return mroOrgFacilityForm;
	}

	public void setMroOrgFacilityForm(MroOrgFacilityForm mroOrgFacilityForm) {
		this.mroOrgFacilityForm = mroOrgFacilityForm;
	}

}
