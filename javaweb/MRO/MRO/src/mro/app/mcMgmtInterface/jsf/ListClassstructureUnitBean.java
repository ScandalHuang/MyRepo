package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.ClassstructureUnitForm;
import mro.app.mcMgmtInterface.service.ListClassstructureUnitInterface;
import mro.app.mcMgmtInterface.service.Impl.ListClassstructureUnitImpl;
import mro.base.entity.ClassstructureUnit;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureUnitBean")
@ViewScoped
public class ListClassstructureUnitBean implements Serializable {
	
	private static final long serialVersionUID = -8400966000664569682L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ClassstructureUnitForm classstructureUnitForm;

	public ListClassstructureUnitBean() {

	}

	@PostConstruct
	public void init() {
		classstructureUnitForm = new ClassstructureUnitForm();
		this.onSearch();
	}

	public void onSearch() {
		ListClassstructureUnitInterface listClassstructureUnitInterface = new ListClassstructureUnitImpl();
		classstructureUnitForm = listClassstructureUnitInterface
				.onSearch(classstructureUnitForm);
	}

	public void onDelete() {
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureUnitInterface listClassstructureUnitInterface = new ListClassstructureUnitImpl();
		classstructureUnitForm = listClassstructureUnitInterface
				.onDelete(classstructureUnitForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}

	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureUnit classstructureUnit=new ClassstructureUnit();
		classstructureUnit.setUpdateBy(loginInfoBean.getEmpNo());
		classstructureUnit.setUpdateDate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureUnit,file,
				Arrays.asList("CLASSSTRUCTUREID"),true,true) == true) {
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

	public ClassstructureUnitForm getClassstructureUnitForm() {
		return classstructureUnitForm;
	}

	public void setClassstructureUnitForm(
			ClassstructureUnitForm classstructureUnitForm) {
		this.classstructureUnitForm = classstructureUnitForm;
	}

}
