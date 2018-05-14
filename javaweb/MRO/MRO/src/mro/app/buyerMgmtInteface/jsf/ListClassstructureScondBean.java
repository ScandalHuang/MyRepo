package mro.app.buyerMgmtInteface.jsf;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.buyerMgmtInteface.form.ClassstructureSecondSourceForm;
import mro.app.buyerMgmtInteface.service.Impl.ListClassstructureScondImpl;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureSecondSource;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureScondBean")
@ViewScoped
public class ListClassstructureScondBean{
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private ClassstructureSecondSourceForm form;
	private ListClassstructureScondImpl impl;


	public ListClassstructureScondBean() {

	}

	@PostConstruct
	public void init() {
		form=new ClassstructureSecondSourceForm();
		impl=new ListClassstructureScondImpl(form);
		impl.setParameter();
		this.onSearch();
	}

	public void onSearch() {
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
		ClassstructureSecondSource entity=new ClassstructureSecondSource();
		entity.setLastUpdateBy(loginInfoBean.getEmpNo());
		entity.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces uploadImpl = new FileUploadExceImpl();
		if (uploadImpl.uploadExcel(entity,file,
				Arrays.asList("CLASSSTRUCTUREID","ASSETATTRID"),true,true,impl.getClass()) == true) {
			this.onSearch();
		}

	}
	// ==============================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ClassstructureSecondSourceForm getForm() {
		return form;
	}

	public void setForm(ClassstructureSecondSourceForm form) {
		this.form = form;
	}

}
