package mro.app.signProcess.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.signHr.jsf.SignHrBean;
import mro.app.signProcess.form.SignProcessForm;
import mro.app.signProcess.service.SignProcessInterface;
import mro.app.signProcess.service.impl.SignProcessImpl;
import mro.base.entity.HrEmp;
import mro.base.entity.SignProcess;
import mro.base.entity.SignProcessList;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.primefaces.event.SelectEvent;
@ManagedBean(name = "SignProcessBean")
@ViewScoped
public class SignProcessBean implements Serializable {
	private static final long serialVersionUID = -4215893433042509034L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	@ManagedProperty(value = "#{SignHrBean}")
	private SignHrBean signHrBean;
	
	private SignProcessForm signProcessForm;

	
	@PostConstruct
	public void init() {
		signProcessForm= new SignProcessForm();
		this.mainQuery(0);
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.setParameter(signProcessForm);
	}
	
	public void mainQuery(int index){  //搜索簽核清單
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.mainQuery(signProcessForm,index);
	}
	
//=========================================Action===================================================
	public void onNew(){
		signProcessForm.onNew(loginInfoBean.getUserName());
	}
	public void onSave(){
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessInterface.onSave(signProcessForm,loginInfoBean.getEmpNo());
	}
	
	public void onChangeSequence(SignProcessList s,String type){
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.onChangeSequence(signProcessForm,s,type);
		
	}
//===================================actionListener==================================================
	public void selectSignProcess(SelectEvent event){  //選取簽核清單
		selectSignProcess((SignProcess) event.getObject());
	}
	
	public void selectSignProcess(SignProcess signProcess){  //選取簽核清單
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.selectSignProcess(signProcessForm, signProcess);
	}
	
	public void addSignProcessList(){ 
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.addSignProcessList(signProcessForm);
	}
	
	public void setEmployee(HrEmp hremp){
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.setEmployee(signProcessForm, hremp);
	}
	
	public void deleteSignProcessList(){
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.deleteSignProcessList(signProcessForm);
	}
	public void onSystemChange(){
		SignProcessInterface signProcessInterface=new SignProcessImpl();
		signProcessForm=signProcessInterface.onSystemChange(signProcessForm);
	}
//===================================================================================================
	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}
	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}
	public SignHrBean getSignHrBean() {
		return signHrBean;
	}
	public void setSignHrBean(SignHrBean signHrBean) {
		this.signHrBean = signHrBean;
	}
	public SignProcessForm getSignProcessForm() {
		return signProcessForm;
	}
	public void setSignProcessForm(SignProcessForm signProcessForm) {
		this.signProcessForm = signProcessForm;
	}
	
}
