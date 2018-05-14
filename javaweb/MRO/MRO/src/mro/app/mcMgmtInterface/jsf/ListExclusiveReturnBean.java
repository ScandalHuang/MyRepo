package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.form.ExclusiveReturnForm;
import mro.app.mcMgmtInterface.service.Impl.ExclusiveReturnImpl;
import mro.base.entity.MroExclusiveReturn;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListExclusiveReturnBean")
@ViewScoped
public class ListExclusiveReturnBean implements Serializable {
	private static final long serialVersionUID = 7087810290145443699L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	

	private ExclusiveReturnForm exclusiveReturnForm;
	private ExclusiveReturnImpl impl;


	@PostConstruct
	public void init() {
		exclusiveReturnForm=new ExclusiveReturnForm();
		impl=new ExclusiveReturnImpl(exclusiveReturnForm);
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
		MroExclusiveReturn mroExclusiveReturn=new MroExclusiveReturn();
		mroExclusiveReturn.setCreateBy(loginInfoBean.getEmpNo());
		mroExclusiveReturn.setCreateDate(new Date(System.currentTimeMillis()));
		mroExclusiveReturn.setLocationSiteMap(exclusiveReturnForm.getsLocationSiteMap());
		
		FileUploadExcelInterfaces impl = new FileUploadExceImpl();
		List<String> keyList=new ArrayList<String>();
		
		if(exclusiveReturnForm.getType().equals("ITEM")){
			keyList=Arrays.asList("LOCATION_SITE","ORGANIZATION_CODE","ITEMNUM","DEPT_NO");
		}else if(exclusiveReturnForm.getType().equals("CLASSSTRUCTUREID")){
			keyList=Arrays.asList("LOCATION_SITE","CLASSSTRUCTUREID");
		}
		
		//KEY值若為空值必須上傳為NULL才能加入判斷條件
		if (impl.uploadExcel(mroExclusiveReturn,file,keyList,
				true,true,ExclusiveReturnImpl.class) == true) {
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

	public ExclusiveReturnForm getExclusiveReturnForm() {
		return exclusiveReturnForm;
	}

	public void setExclusiveReturnForm(ExclusiveReturnForm exclusiveReturnForm) {
		this.exclusiveReturnForm = exclusiveReturnForm;
	}

}
