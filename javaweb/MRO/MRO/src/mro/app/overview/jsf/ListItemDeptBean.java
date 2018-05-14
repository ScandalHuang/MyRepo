package mro.app.overview.jsf;

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
import mro.app.overview.form.ItemDeptForm;
import mro.app.overview.service.ListItemDeptInterface;
import mro.app.overview.service.Impl.ListItemDeptImpl;
import mro.base.entity.ItemDept;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListItemDeptBean")
@ViewScoped
public class ListItemDeptBean implements Serializable {

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private ItemDeptForm itemDeptForm;

	public ListItemDeptBean() {

	}

	@PostConstruct
	public void init() {
		itemDeptForm=new ItemDeptForm();
		itemDeptForm.setDeptCode(loginInfoBean.getPerson().getMDeptCode());
	}
	
	public void onSearch(){
		ListItemDeptInterface listItemDeptInterface=new ListItemDeptImpl();
		itemDeptForm=listItemDeptInterface.onSearch(itemDeptForm);
	}
	public void onInsert(){
		GlobalGrowl message = new GlobalGrowl();
		ListItemDeptInterface listItemDeptInterface=new ListItemDeptImpl();
		String warnMessage=listItemDeptInterface.validate(itemDeptForm);
		if(warnMessage.length()==0){
			itemDeptForm=listItemDeptInterface.onInsert(
					loginInfoBean.getEmpNo(),loginInfoBean.getPerson().getMDeptCode(), itemDeptForm);
			message.addInfoMessage("Upload", "Upload successful!");
		}else{
			message.addWarnMessage("Warn", warnMessage);
		}
	}
	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		ListItemDeptInterface listItemDeptInterface=new ListItemDeptImpl();
		itemDeptForm=listItemDeptInterface.onDelete(itemDeptForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		
		//======================excel upload 額外參數====================================
		ItemDept itemDept=new ItemDept();
		itemDept.setDeptcode(loginInfoBean.getPerson().getMDeptCode());
		itemDept.setLastupdateBy(loginInfoBean.getEmpNo());
		itemDept.setLastupdateDate(new Date(System.currentTimeMillis()));
		//===========================upload====================================================
		
		if (fileUploadExcelInterfaces.uploadExcel(itemDept,file,
				Arrays.asList("ITEMNUM","DEPTCODE"),true,true) == true) {
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

	public ItemDeptForm getItemDeptForm() {
		return itemDeptForm;
	}

	public void setItemDeptForm(ItemDeptForm itemDeptForm) {
		this.itemDeptForm = itemDeptForm;
	}

}
