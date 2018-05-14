package mro.app.classstructureSign.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.classstructureSign.Service.ListClassstructureItemSignInterface;
import mro.app.classstructureSign.Service.Impl.ListClassstructureItemSignImpl;
import mro.app.classstructureSign.form.ClassstructureItemSignForm;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureItemSign;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureItemSignBean")
@ViewScoped
public class ListClassstructureItemSignBean implements Serializable {
	private static final long serialVersionUID = 8441448909017003794L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private ClassstructureItemSignForm classstructureItemSignForm;
	
	public ListClassstructureItemSignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureItemSignForm = new ClassstructureItemSignForm();
		ListClassstructureItemSignInterface listClassstructureItemSignInterface = new ListClassstructureItemSignImpl();
		listClassstructureItemSignInterface.setParameter(classstructureItemSignForm);
	}

	public void changeSelect() {
		ListClassstructureItemSignInterface listClassstructureItemSignInterface = new ListClassstructureItemSignImpl();
		listClassstructureItemSignInterface.onSearch(classstructureItemSignForm);
	}

	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureItemSignInterface listClassstructureItemSignInterface = new ListClassstructureItemSignImpl();
		listClassstructureItemSignInterface.onDelete(classstructureItemSignForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureItemSign classstructureItemSign=new ClassstructureItemSign();
		classstructureItemSign.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureItemSign.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureItemSign,file,
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

	public ClassstructureItemSignForm getClassstructureItemSignForm() {
		return classstructureItemSignForm;
	}

	public void setClassstructureItemSignForm(
			ClassstructureItemSignForm classstructureItemSignForm) {
		this.classstructureItemSignForm = classstructureItemSignForm;
	}

}
