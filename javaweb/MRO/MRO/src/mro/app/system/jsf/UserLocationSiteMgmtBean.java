package mro.app.system.jsf;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.EmployeeInterfaces;
import mro.app.system.form.UserLocationSiteMapForm;
import mro.app.system.service.Impl.UserLocationSiteMapImpl;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "UserLocationSiteMgmtBean")
@ViewScoped
public class UserLocationSiteMgmtBean implements EmployeeInterfaces {
	
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private UserLocationSiteMapForm form;
	private UserLocationSiteMapImpl impl;

	@PostConstruct
	public void init() {
		form=new UserLocationSiteMapForm();
		impl=new UserLocationSiteMapImpl();
		this.inital();
		impl.setParameter(form);
	}
	
	public void inital() {
		
	}

	@Override
	public void setEmployee(Person person) {
		impl.setEmployee(form, person);
		
	}
	public void update() {
		impl.update(form, loginInfoBean.getPerson(), new GlobalGrowl());
	}

	public UserLocationSiteMapForm getForm() {
		return form;
	}

	public void setForm(UserLocationSiteMapForm form) {
		this.form = form;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}
	
}
