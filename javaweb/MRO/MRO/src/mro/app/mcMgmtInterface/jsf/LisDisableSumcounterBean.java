package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.DisableSumcounterForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.app.mcMgmtInterface.service.Impl.LisDisableSumcounterImpl;
import mro.app.mcMgmtInterface.service.Impl.ListStoreCategoryChangeImpl;
import mro.base.entity.Inventory;
import mro.base.entity.PrlineDisableSumcounter;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "LisDisableSumcounterBean")
@ViewScoped
public class LisDisableSumcounterBean implements Serializable {
	private static final long serialVersionUID = 7087810290145443699L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private DisableSumcounterForm disableSumcounterForm;
	private LisDisableSumcounterImpl impl;


	@PostConstruct
	public void init() {
		disableSumcounterForm=new DisableSumcounterForm();
		impl=new LisDisableSumcounterImpl(disableSumcounterForm);
		impl.setParameter();
	}
	
	public void onSearch(){
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
		PrlineDisableSumcounter prlineDisableSumcounter=new PrlineDisableSumcounter();
		prlineDisableSumcounter.setLastUpdateBy(loginInfoBean.getEmpNo());
		prlineDisableSumcounter.setLastUpdate(new Date(System.currentTimeMillis()));

		List<String> keys=Arrays.asList("ORGANIZATION_CODE","ITEMNUM","DEPT_CODE");
		FileUploadExceImpl uploadImpl = new FileUploadExceImpl();
		List<PrlineDisableSumcounter> list=uploadImpl.converList(
				prlineDisableSumcounter, file, keys,new GlobalGrowl());
		
		if (list!=null && list.stream().filter(l -> !loginInfoBean.validateUserLSMap(l.getOrganizationCode()))
				.findAny().orElse(null) != null) {
			GlobalGrowl msg=new GlobalGrowl();
			msg.addErrorMessage("上傳錯誤!","EXCLE內有非權限的ORGANIZATION_CODE");
		}else{
			//DPET_CODE為KEY值，所以若為空值必須上傳為NULL才能加入判斷條件
			if (uploadImpl.uploadExcel(list,keys,true,true,
					LisDisableSumcounterImpl.class) == true) {
				this.onSearch();
			}
		}

	}
	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public DisableSumcounterForm getDisableSumcounterForm() {
		return disableSumcounterForm;
	}

	public void setDisableSumcounterForm(DisableSumcounterForm disableSumcounterForm) {
		this.disableSumcounterForm = disableSumcounterForm;
	}

	
}
