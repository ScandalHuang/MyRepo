package mro.app.classstructureSign.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.classstructureSign.Service.ListClassstructureApplySignInterface;
import mro.app.classstructureSign.Service.Impl.ListClassstructureApplySignImpl;
import mro.app.classstructureSign.form.ClassstructureApplySignForm;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.entity.ClassstructureApplySign;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListClassstructureApplySignBean")
@ViewScoped
public class ListClassstructureApplySignBean implements Serializable {
	private static final long serialVersionUID = 8055352672489022280L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ClassstructureApplySignForm classstructureApplySignForm;

	public ListClassstructureApplySignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureApplySignForm = new ClassstructureApplySignForm();
		this.onSearch();
	}

	public void onSearch() {
		ListClassstructureApplySignInterface listClassstructureApplySignInterface = new ListClassstructureApplySignImpl();
		classstructureApplySignForm = listClassstructureApplySignInterface
				.onSearch(classstructureApplySignForm);
	}

	public void onDelete() {
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructureApplySignInterface listClassstructureApplySignInterface = new ListClassstructureApplySignImpl();
		classstructureApplySignForm = listClassstructureApplySignInterface
				.onDelete(classstructureApplySignForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}

	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		ClassstructureApplySign classstructureApplySign=new ClassstructureApplySign();
		classstructureApplySign.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureApplySign.setLastUpdate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(classstructureApplySign,file,
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

	public ClassstructureApplySignForm getClassstructureApplySignForm() {
		return classstructureApplySignForm;
	}

	public void setClassstructureApplySignForm(
			ClassstructureApplySignForm classstructureApplySignForm) {
		this.classstructureApplySignForm = classstructureApplySignForm;
	}

}
