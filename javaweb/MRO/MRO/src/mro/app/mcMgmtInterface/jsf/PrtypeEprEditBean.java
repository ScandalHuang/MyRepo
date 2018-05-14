package mro.app.mcMgmtInterface.jsf;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.BeanUtils;

import mro.app.mcMgmtInterface.form.PrtypeEprForm;
import mro.app.mcMgmtInterface.service.Impl.PrtypeEprImpl;
import mro.base.entity.PrtypeEpr;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "PrtypeEprEditBean")
@ViewScoped
public class PrtypeEprEditBean{

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private PrtypeEprForm form;
	private PrtypeEprImpl prtypeEprImpl;

	@PostConstruct
	public void init() {
		form=new PrtypeEprForm();
		prtypeEprImpl=new PrtypeEprImpl();
		prtypeEprImpl.setParameter(form);
		this.quary();
	}

	public void inital() {
		form.inital();
	}
	// =========================================Action===================================================

	/** Quary */
	public void quary() {
		prtypeEprImpl.query(form);
	}
	/** select */
	public void onSelect() {
		prtypeEprImpl.onSelect(form);
	}
	/** select */
	public void onSelect(PrtypeEpr prtypeEpr) {
		BeanUtils.copyProperties(prtypeEpr, form.getPrtypeEpr());
	}
	/** save */
	public void onSave() {
		prtypeEprImpl.onSave(form,loginInfoBean.getPerson(),new GlobalGrowl());
	}
	/** Delete */
	public void onDelete(PrtypeEpr prtypeEpr) {
		prtypeEprImpl.onDelete(form,prtypeEpr,new GlobalGrowl());
	}
	// =====================================================================================================

	public PrtypeEprForm getForm() {
		return form;
	}

	public void setForm(PrtypeEprForm form) {
		this.form = form;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

}
