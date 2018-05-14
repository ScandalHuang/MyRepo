package mro.app.gpMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.gpMgmtInterface.form.GpTypeForm;
import mro.app.gpMgmtInterface.service.Impl.GpTypeImpl;
import mro.base.entity.ClassstructureGp;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListGpTypeBean")
@ViewScoped
public class ListGpTypeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private GpTypeForm gpTypeForm;
	private GpTypeImpl impl;

	@PostConstruct
	public void init() {
		gpTypeForm = new GpTypeForm();
		impl = new GpTypeImpl(gpTypeForm);
		impl.setParameter();
		this.onSearch();
	}

	public void onSearch() {
		impl.onSearch();
	}

	public void onSelectAll() {
		impl.onSelectAll();
	}

	public void onDelete() {
		impl.onDelete(new GlobalGrowl());
		this.onSearch();
	}

	public void uploadExcel(File file) {
		ClassstructureGp classstructureGp = new ClassstructureGp();
		classstructureGp.setLastUpdateBy(loginInfoBean.getEmpNo());
		classstructureGp.setLastUpdate(new Date(System.currentTimeMillis()));

		FileUploadExcelInterfaces impl = new FileUploadExceImpl();
		List<String> keyList = new ArrayList<String>();
		keyList = Arrays.asList("CLASSSTRUCTUREID");

		// KEY值若為空值必須上傳為NULL才能加入判斷條件
		if (impl.uploadExcel(classstructureGp, file, keyList, true, true,
				GpTypeImpl.class) == true) {
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

	public GpTypeForm getGpTypeForm() {
		return gpTypeForm;
	}

	public void setGpTypeForm(GpTypeForm gpTypeForm) {
		this.gpTypeForm = gpTypeForm;
	}

}
