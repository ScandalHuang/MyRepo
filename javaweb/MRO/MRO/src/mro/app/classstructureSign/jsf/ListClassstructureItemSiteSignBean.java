package mro.app.classstructureSign.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.classstructureSign.Service.ListClassstructureItemSiteSignInterface;
import mro.app.classstructureSign.Service.Impl.ListClassstructureItemSiteSignImpl;
import mro.app.classstructureSign.form.ClassstructureItemSiteSignForm;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureItemSiteSign;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureItemSiteSignBean")
@ViewScoped
public class ListClassstructureItemSiteSignBean implements Serializable {	
	private static final long serialVersionUID = -8399139575657663139L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ClassstructureItemSiteSignForm classstructureItemSiteSignForm;
	

	public ListClassstructureItemSiteSignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureItemSiteSignForm = new ClassstructureItemSiteSignForm();
		ListClassstructureItemSiteSignInterface listClassstructureItemSiteSignInterface = new ListClassstructureItemSiteSignImpl();
		listClassstructureItemSiteSignInterface.setParameter(classstructureItemSiteSignForm);
	}

	public void changeSelect() {
		ListClassstructureItemSiteSignInterface listClassstructureItemSiteSignInterface = new ListClassstructureItemSiteSignImpl();
		listClassstructureItemSiteSignInterface.onSearch(classstructureItemSiteSignForm);
	}

	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureItemSiteSignInterface listClassstructureItemSiteSignInterface = new ListClassstructureItemSiteSignImpl();
		listClassstructureItemSiteSignInterface.onDelete(classstructureItemSiteSignForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureItemSiteSign classstructureItemSiteSign=new ClassstructureItemSiteSign();
		classstructureItemSiteSign.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureItemSiteSign.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureItemSiteSign,file,
				Arrays.asList("CLASSSTRUCTUREID","LOCATION_SITE"),true,true) == true) {
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

	public ClassstructureItemSiteSignForm getClassstructureItemSiteSignForm() {
		return classstructureItemSiteSignForm;
	}

	public void setClassstructureItemSiteSignForm(
			ClassstructureItemSiteSignForm classstructureItemSiteSignForm) {
		this.classstructureItemSiteSignForm = classstructureItemSiteSignForm;
	}

}

