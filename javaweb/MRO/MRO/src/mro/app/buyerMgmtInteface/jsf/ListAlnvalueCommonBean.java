package mro.app.buyerMgmtInteface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.buyerMgmtInteface.form.AlnvalueCommonForm;
import mro.app.buyerMgmtInteface.service.ListAlnvalueCommonInterface;
import mro.app.buyerMgmtInteface.service.Impl.ListAlnvalueCommonImpl;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AssetattributeBO;
import mro.base.entity.AlndomainCommon;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListAlnvalueCommonBean")
@ViewScoped
public class ListAlnvalueCommonBean implements Serializable {
	
	private static final long serialVersionUID = -1965231714268194281L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private AlnvalueCommonForm alnvalueCommonForm;
	private transient AssetattributeBO assetattributeBO;

	public ListAlnvalueCommonBean() {

	}

	@PostConstruct
	public void init() {
		assetattributeBO=SpringContextUtil.getBean(AssetattributeBO.class);
		alnvalueCommonForm=new AlnvalueCommonForm();
		alnvalueCommonForm.setAssetattributeList(assetattributeBO.getListNonIn(
				Arrays.asList(SystemConfig.meterialName)));
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		alnvalueCommonForm.setItemCategoryOption(bean.getParameterOption().get(ParameterType.ITEM_CATEGORY));
	}
	
	public void onSearch(){
		ListAlnvalueCommonInterface listAlnvalueCommonInterface=new ListAlnvalueCommonImpl();
		alnvalueCommonForm=listAlnvalueCommonInterface.onSearch(alnvalueCommonForm);
	}

	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		ListAlnvalueCommonInterface listAlnvalueCommonInterface=new ListAlnvalueCommonImpl();
		alnvalueCommonForm=listAlnvalueCommonInterface.onDelete(alnvalueCommonForm);
		message.addInfoMessage("Delete", "Delete successful!");
	}
	
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();		
		AlndomainCommon alndomainCommon=new AlndomainCommon();
		alndomainCommon.setUpdateBy(loginInfoBean.getEmpNo());
		alndomainCommon.setUploadDate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(alndomainCommon,file,
				Arrays.asList("ITEMTYPE","ASSETATTRID","VALUE"),true,true) == true) {
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

	public AlnvalueCommonForm getAlnvalueCommonForm() {
		return alnvalueCommonForm;
	}

	public void setAlnvalueCommonForm(AlnvalueCommonForm alnvalueCommonForm) {
		this.alnvalueCommonForm = alnvalueCommonForm;
	}

}
