package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.PrtypeBudgetForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.app.mcMgmtInterface.service.Impl.ListPrtypeBudgetImpl;
import mro.base.entity.PrtypeBudget;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListPrtypeBudgetBean")
@ViewScoped
public class ListPrtypeBudgetBean implements Serializable {
	private static final long serialVersionUID = 10443660780502700L;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private PrtypeBudgetForm form;
	private ListUpLoadInterface impl;


	public ListPrtypeBudgetBean() {

	}

	@PostConstruct
	public void init() {
		form=new PrtypeBudgetForm();
		impl=new ListPrtypeBudgetImpl(form);
	}


	// ================================Prtype==============================================
	public void onSearch(String prtype) {
		form.setSelectPrtype(prtype);
		impl.setParameter();
		this.onSearch();
	}
	
	public void onSearch() {
		impl.onSearch();
	}

	
	public void onSelectAll(){
		impl.onSelectAll();
	}
	
	public void onDelete(){
		impl.onDelete(new GlobalGrowl());
		this.onSearch();
	}
	
	public void uploadExcel(File file) {
		PrtypeBudget prtypeBudget=new PrtypeBudget();
		prtypeBudget.setLastupdateBy(loginInfoBean.getEmpNo());
		prtypeBudget.setLastupdateDate(new Date(System.currentTimeMillis()));
		prtypeBudget.setBudgetType(form.getSelectBudgetType());
		prtypeBudget.setLocationSiteMap(form.getsLocationSiteMap());
		
		FileUploadExcelInterfaces impl = new FileUploadExceImpl();
		//DPET_CODE為KEY值，所以若為空值必須上傳為NULL才能加入判斷條件
		if (impl.uploadExcel(prtypeBudget,file,
				Arrays.asList("LOCATION_SITE","DEPT_NO","BUDGET_MONTH","BUDGET_TYPE"),
				true,true,ListPrtypeBudgetImpl.class) == true) {
			onSearch();
		}

	}
	// ==============================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public PrtypeBudgetForm getForm() {
		return form;
	}

	public void setForm(PrtypeBudgetForm form) {
		this.form = form;
	}
	

}
