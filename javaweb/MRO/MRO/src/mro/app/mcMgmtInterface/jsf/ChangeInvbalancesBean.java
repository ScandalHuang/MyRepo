package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.jsf.ListEmployeeBean;
import mro.app.mcMgmtInterface.bo.ChangeInvbalancesBO;
import mro.app.mcMgmtInterface.form.ChangeInvbalancesForm;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.primefaces.context.RequestContext;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ChangeInvbalancesBean")
@ViewScoped
public class ChangeInvbalancesBean implements Serializable{
	
	private static final long serialVersionUID = 6617890838807246057L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	@ManagedProperty(value = "#{ListEmployeeBean}")
	private ListEmployeeBean listEmployeeBean;
	
	private ChangeInvbalancesForm changeInvbalancesForm;
	
	public ChangeInvbalancesBean() {

	}

	@PostConstruct
	public void init() {
		listEmployeeBean.setActiveStatus(true); //只顯示生效
		listEmployeeBean.onDepotSearch(loginInfoBean.getPerson().getMDeptCode());// 登入者部門資料
		changeInvbalancesForm = new ChangeInvbalancesForm(); 
		query();
	}
	
	public void inital(){
		changeInvbalancesForm.intial();
	}
	
	public void query(){ 
		inital();
		ChangeInvbalancesBO changeInvbalancesBO=SpringContextUtil.getBean(ChangeInvbalancesBO.class);
		changeInvbalancesForm.setInvbalancesList(changeInvbalancesBO.getInvbalancesList(
				loginInfoBean.getPerson().getMDeptCode()));
		changeInvbalancesForm.setFiltetInvbalancesList(changeInvbalancesForm.getInvbalancesList());
		
	}
	public void onupdateView(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("tabView:invbalancesList");
	}
	public void onUpdate(){
		ChangeInvbalancesBO changeInvbalancesBO=SpringContextUtil.getBean(ChangeInvbalancesBO.class);
		GlobalGrowl message = new GlobalGrowl();
		changeInvbalancesBO.onUpdate(changeInvbalancesForm.getSelectInvbalances());
		message.addInfoMessage("Update", "Update successful.");
		query();
		onupdateView();
	}
	
	public void setEmployee(Person person) {// 選取員工
		changeInvbalancesForm.getSelectInvbalances().put("NEW_NAME", person.getDisplayName());
		changeInvbalancesForm.getSelectInvbalances().put("NEW_LASTREQUESTEDBY2", person.getPersonId());
	}
	
	//============================================================================================


	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ChangeInvbalancesForm getChangeInvbalancesForm() {
		return changeInvbalancesForm;
	}

	public void setChangeInvbalancesForm(ChangeInvbalancesForm changeInvbalancesForm) {
		this.changeInvbalancesForm = changeInvbalancesForm;
	}

	public ListEmployeeBean getListEmployeeBean() {
		return listEmployeeBean;
	}

	public void setListEmployeeBean(ListEmployeeBean listEmployeeBean) {
		this.listEmployeeBean = listEmployeeBean;
	}
	
}
