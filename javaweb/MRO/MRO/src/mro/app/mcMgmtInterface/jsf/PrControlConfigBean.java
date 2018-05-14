package mro.app.mcMgmtInterface.jsf;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.form.PrControlConfigForm;
import mro.app.mcMgmtInterface.service.Impl.PrControlConfigImpl;
import mro.base.entity.PrControlConfig;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "PrControlConfigBean")
@ViewScoped
public class PrControlConfigBean{

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private PrControlConfigForm form;
	private PrControlConfigImpl impl;

	@PostConstruct
	public void init() {
		form=new PrControlConfigForm();
		impl=new PrControlConfigImpl();
		impl.setParameter(form);
		this.quary();
	}

	public void inital() {
		form.inital();
	}
	// =========================================Action===================================================

	/** Quary */
	public void quary() {
		impl.query(form);
	}
	/** select */
	public void onSelect() {
		impl.onSelect(form);
	}
	/** select */
	public void onSelect(PrControlConfig prControlConfig) {
		BeanUtils.copyProperties(prControlConfig, form.getPrControlConfig());
	}
	/** save */
	public void onSave() {
		impl.onSave(form,loginInfoBean.getPerson(),new GlobalGrowl());
	}
	/** Delete */
	public void onDelete(PrControlConfig prControlConfig) {
		impl.onDelete(form,prControlConfig,new GlobalGrowl());
	}
	// =====================================================================================================

	public PrControlConfigForm getForm() {
		return form;
	}

	public void setForm(PrControlConfigForm form) {
		this.form = form;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

}
