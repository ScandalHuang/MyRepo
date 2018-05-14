package mro.app.sign.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ApplyClassstructureInterface;
import mro.app.applyItem.service.impl.ApplyClassstructureImpl;
import mro.app.sign.service.ApplyClassstructureSignInterface;
import mro.app.sign.service.impl.ApplyClassstructureSignImpl;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ClassstructureForm;
import mro.viewForm.ApplyClassstructureView;

import org.primefaces.event.SelectEvent;
//import java.util.HashMap;

@ManagedBean(name = "ApplyClassstructureSignBean")
@ViewScoped
public class ApplyClassstructureSignBean implements Serializable {
	private static final long serialVersionUID = 5484021568419584804L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private ClassstructureForm classstructureForm;
	
	private ApplyClassstructureView applyClassstructureView;
	
	public ApplyClassstructureSignBean() {

	}

	@PostConstruct
	public void init() {
		classstructureForm=new ClassstructureForm();
		applyClassstructureView = new ApplyClassstructureView();
		// ======================setParameter==============================
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm=applyClassstructureInterface.setParameter(classstructureForm);
		this.getApplyList(0);
	}
	// ================================申請單list============================================================
	public void getApplyList(int activeI) {
		ApplyClassstructureSignInterface applyClassstructureSignInterface = new ApplyClassstructureSignImpl();
		classstructureForm = applyClassstructureSignInterface.getApplySignList(
				classstructureForm, loginInfoBean.getEmpNo(), activeI);
	}

	// ==========================================action====================================================================
	public void onTransfer() { // 轉呈
		ApplyClassstructureSignInterface applyClassstructureSignInterface = new ApplyClassstructureSignImpl();
		if (applyClassstructureSignInterface.onTransfer(classstructureForm, loginInfoBean.getEmpNo())) {
			this.getApplyList(0);
		}
	}

	public void onRejectToNew() { // 退回修改
		ApplyClassstructureSignInterface applyClassstructureSignInterface = new ApplyClassstructureSignImpl();
		if (applyClassstructureSignInterface.onRejectToNew(classstructureForm, loginInfoBean.getEmpNo())) {
			this.getApplyList(0);
		}
	}

	public void onAccept() throws IOException { // 確定同意
		ApplyClassstructureSignInterface applyClassstructureSignInterface = new ApplyClassstructureSignImpl();
		if (applyClassstructureSignInterface.onAccept(classstructureForm, loginInfoBean.getEmpNo())) {
			this.getApplyList(0);
		}
	}

	// ============================================Listen=================================================================
	public void setEmployee(Person person) {
		ApplyClassstructureSignInterface applyClassstructureSignInterface = new ApplyClassstructureSignImpl();
		classstructureForm=applyClassstructureSignInterface.setTransferEmployee(classstructureForm, person);
	}
	
	public void selectClassstructureHeaderApply(SelectEvent event) { // 選取異動申請單
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm=applyClassstructureInterface.selectApply(
				classstructureForm,(ClassstructureHeaderApply) event.getObject());
		applyClassstructureView.setView(classstructureForm);
	}
	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ClassstructureForm getClassstructureForm() {
		return classstructureForm;
	}

	public void setClassstructureForm(ClassstructureForm classstructureForm) {
		this.classstructureForm = classstructureForm;
	}

	public ApplyClassstructureView getApplyClassstructureView() {
		return applyClassstructureView;
	}

	public void setApplyClassstructureView(
			ApplyClassstructureView applyClassstructureView) {
		this.applyClassstructureView = applyClassstructureView;
	}

}
