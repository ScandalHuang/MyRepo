package mro.app.classstructureSign.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.classstructureSign.Service.ListClassstructureItemChangeSignInterface;
import mro.app.classstructureSign.Service.Impl.ListClassstructureItemChangeSignImpl;
import mro.app.classstructureSign.form.ClassstructureItemChangeSignForm;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureItemchangeSign;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureItemChangeSignBean")
@ViewScoped
public class ListClassstructureItemChangeSignBean implements Serializable {
	private static final long serialVersionUID = 1174798273367795760L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ClassstructureItemChangeSignForm classstructureItemChangeSignForm;

	public ListClassstructureItemChangeSignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureItemChangeSignForm = new ClassstructureItemChangeSignForm();
		ListClassstructureItemChangeSignInterface listClassstructureItemChangeSignInterface = new ListClassstructureItemChangeSignImpl();
		listClassstructureItemChangeSignInterface
				.setParameter(classstructureItemChangeSignForm);
	}

	public void changeSelect() {
		ListClassstructureItemChangeSignInterface listClassstructureItemChangeSignInterface = new ListClassstructureItemChangeSignImpl();
		listClassstructureItemChangeSignInterface
				.onSearch(classstructureItemChangeSignForm);
	}

	public void onDelete() {
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureItemChangeSignInterface listClassstructureItemChangeSignInterface = new ListClassstructureItemChangeSignImpl();
		listClassstructureItemChangeSignInterface
				.onDelete(classstructureItemChangeSignForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}

	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureItemchangeSign classstructureItemChangeSign=new ClassstructureItemchangeSign();
		classstructureItemChangeSign.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureItemChangeSign.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureItemChangeSign,file,
				Arrays.asList("CLASSSTRUCTUREID","PLANT_CODE"),true,true) == true) {
			this.changeSelect();
		}
	}

	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ClassstructureItemChangeSignForm getClassstructureItemChangeSignForm() {
		return classstructureItemChangeSignForm;
	}

	public void setClassstructureItemChangeSignForm(
			ClassstructureItemChangeSignForm classstructureItemChangeSignForm) {
		this.classstructureItemChangeSignForm = classstructureItemChangeSignForm;
	}

}
