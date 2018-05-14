package mro.app.aItemSimple.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.aItemSimple.bo.ListAItemSimpleBO;
import mro.app.aItemSimple.form.AItemSimpleForm;
import mro.app.applyItem.jsf.ApplyItemChangeBean;
import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.bo.PersonBO;
import mro.base.entity.AItemSimple;
import mro.base.entity.Item;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListAItemSimpleBean")
@ViewScoped
public class ListAItemSimpleBean implements Serializable {
	
	private static final long serialVersionUID = 1381774376015019858L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	@ManagedProperty(value = "#{ApplyItemChangeBean}")
	private ApplyItemChangeBean applyItemChangeBean;

	private AItemSimpleForm aItemSimpleForm;

	public ListAItemSimpleBean() {

	}

	@PostConstruct
	public void init() {
		aItemSimpleForm=new AItemSimpleForm();
	}
	
	public void onSearch(){
		ListAItemSimpleBO listAItemSimpleBO = SpringContextUtil.getBean(ListAItemSimpleBO.class);
		aItemSimpleForm.setListAItemSimple(listAItemSimpleBO.getAItemSimpleList(aItemSimpleForm));
	}
	
	public void onAItemChangeBySpec() throws Exception {
		ListAItemSimpleBO listAItemSimpleBO = SpringContextUtil.getBean(ListAItemSimpleBO.class);
		ListItemCommonBO listItemCommonBO = SpringContextUtil.getBean(ListItemCommonBO.class);
		PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
		int i=0;
		for (AItemSimple aItemSimple : aItemSimpleForm.getListAItemSimple()) {
			if(onValidate(aItemSimple)){
				Person person=personBO.getPerson(aItemSimple.getChangeby());
				Item item=listItemCommonBO.getItem(aItemSimple.getOriitemnum());
				applyItemChangeBean.setSelectItem(item);
				applyItemChangeBean.getItemForm().setaItemSimple(aItemSimple);
				applyItemChangeBean.onItemSelectEdit("simpleSave",
						person.getEmailAddress().substring(
								0, person.getEmailAddress().indexOf("@")),person);
				listAItemSimpleBO.update(aItemSimple, applyItemChangeBean.getItemForm().getaItem());
				applyItemChangeBean.inital();
				i++;
			}
		}
		GlobalGrowl message = new GlobalGrowl();
		message.addInfoMessage("Info","共" + i + "顆料號<br />");
		this.onSearch();
	}
	
	public void uploadExcel(File file) {
		GlobalGrowl message = new GlobalGrowl();
		
		AItemSimple aItemSimple=new AItemSimple();
		aItemSimple.setUpdateBy(loginInfoBean.getEmpNo());
		aItemSimple.setUploadDate(new Date(System.currentTimeMillis()));
		
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(aItemSimple,file,
				Arrays.asList("ORIITEMNUM","CHANGEBY"),false,true) == true) {
			this.onSearch();
		}

	}
	
	public boolean onValidate(AItemSimple aItemSimple){
		ListAItemSimpleBO listAItemSimpleBO = SpringContextUtil.getBean(ListAItemSimpleBO.class);
		aItemSimple=listAItemSimpleBO.getAItemSimple(
				new String[]{"aItemSimpleId"}, aItemSimple.getAItemSimpleId());
		if(aItemSimple==null || StringUtils.isNotBlank(aItemSimple.getApplyNum()) ||
			StringUtils.isBlank(aItemSimple.getOriitemnum())||
			StringUtils.isBlank(aItemSimple.getChangeby())){
			return false;
		}
		return true;
	}

	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public AItemSimpleForm getaItemSimpleForm() {
		return aItemSimpleForm;
	}

	public void setaItemSimpleForm(AItemSimpleForm aItemSimpleForm) {
		this.aItemSimpleForm = aItemSimpleForm;
	}

	public ApplyItemChangeBean getApplyItemChangeBean() {
		return applyItemChangeBean;
	}

	public void setApplyItemChangeBean(ApplyItemChangeBean applyItemChangeBean) {
		this.applyItemChangeBean = applyItemChangeBean;
	}

}
