package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.InactiveDeptMappingForm;
import mro.app.mcMgmtInterface.service.Impl.InactiveDeptMappingImpl;
import mro.base.entity.InactiveDeptMapping;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "InactiveDeptMappingBean")
@ViewScoped
public class InactiveDeptMappingBean implements Serializable {
	private static final long serialVersionUID = 1L;


	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private InactiveDeptMappingForm form;
	private InactiveDeptMappingImpl impl;


	@PostConstruct
	public void init() {
		form=new InactiveDeptMappingForm();
		impl=new InactiveDeptMappingImpl(form);
		impl.setParameter();
		this.onSearch();
	}
	
	public void onSearch(){
		impl.onSearch();
	}
	
	public void onSelectAll(){
		impl.onSelectAll();
	}
	
	public void onDelete(){
		impl.onDelete(new GlobalGrowl());
		this.onSearch();
	}
	
	public void uploadExcel(File file) {
		InactiveDeptMapping inactiveDeptMapping=new InactiveDeptMapping();
		inactiveDeptMapping.setLastUpdateBy(loginInfoBean.getEmpNo());
		inactiveDeptMapping.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces impl = new FileUploadExceImpl();
		List<String> keyList=Arrays.asList("OLD_DEPT_CODE");
		
		if (impl.uploadExcel(inactiveDeptMapping,file,keyList,true,true,InactiveDeptMappingImpl.class) == true) {
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

	public InactiveDeptMappingForm getForm() {
		return form;
	}

	public void setForm(InactiveDeptMappingForm form) {
		this.form = form;
	}
}
