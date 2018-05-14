package mro.app.classstructureSign.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.classstructureSign.Service.ListClassstructureSignInterface;
import mro.app.classstructureSign.Service.Impl.ListClassstructureSignImpl;
import mro.app.classstructureSign.form.ClassstructureSignForm;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureSign;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureSignBean")
@ViewScoped
public class ListClassstructureSignBean implements Serializable {
	private static final long serialVersionUID = 6321350182028510377L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	
	private ClassstructureSignForm classstructureSignForm;

	public ListClassstructureSignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureSignForm = new ClassstructureSignForm();
		ListClassstructureSignInterface listClassstructureSignInterface = new ListClassstructureSignImpl();
		listClassstructureSignInterface.setParameter(classstructureSignForm);
	}

	public void changeSelect() {
		ListClassstructureSignInterface listClassstructureSignInterface = new ListClassstructureSignImpl();
		listClassstructureSignInterface.onSearch(classstructureSignForm);
		
	}
	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureSignInterface listClassstructureSignInterface = new ListClassstructureSignImpl();
		listClassstructureSignInterface.onDelete(classstructureSignForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureSign classstructureSign=new ClassstructureSign();
		classstructureSign.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureSign.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureSign,file,
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

	public ClassstructureSignForm getClassstructureSignForm() {
		return classstructureSignForm;
	}

	public void setClassstructureSignForm(
			ClassstructureSignForm classstructureSignForm) {
		this.classstructureSignForm = classstructureSignForm;
	}


}
