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

import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.MroInventoryIdleListForm;
import mro.app.mcMgmtInterface.service.Impl.ListMroInventoryIdleImpl;
import mro.base.entity.MroInventoryIdleList;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListMroInventoryIdleBean")
@ViewScoped
public class ListMroInventoryIdleBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private MroInventoryIdleListForm form;
	private ListMroInventoryIdleImpl impl;


	@PostConstruct
	public void init() {
		form=new MroInventoryIdleListForm();
		impl=new ListMroInventoryIdleImpl(form);
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
		MroInventoryIdleList entity=new MroInventoryIdleList();
		entity.setCreateBy(loginInfoBean.getEmpNo());
		entity.setCreateDate(new Date(System.currentTimeMillis()));
		
		List<String> keys=Arrays.asList("ITEMNUM","LOCATION","ORGANIZATION_CODE");
		FileUploadExceImpl uploadImpl = new FileUploadExceImpl();
		List<MroInventoryIdleList> list=uploadImpl.converList(entity, file, keys,new GlobalGrowl());
		if (list == null) {
			return;
		}
		List<MroInventoryIdleList> vList=impl.getVMroInventoryIdleList(list); //取得SubinventoryConfig mapping更新後的廠區
		if (vList!=null && vList.stream().filter(l -> !loginInfoBean.validateUserLSMap(l.getOrganizationCode()))
				.findAny().orElse(null) != null) {
			GlobalGrowl msg=new GlobalGrowl();
			msg.addErrorMessage("上傳錯誤!","EXCLE內有非權限的廠區");
		}else{		
			if (uploadImpl.uploadExcel(list,keys,true,true,impl.getClass()) == true) {
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

	public MroInventoryIdleListForm getForm() {
		return form;
	}

	public void setForm(MroInventoryIdleListForm form) {
		this.form = form;
	}
	
}