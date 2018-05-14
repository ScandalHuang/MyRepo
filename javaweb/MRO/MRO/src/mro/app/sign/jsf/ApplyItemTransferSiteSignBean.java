package mro.app.sign.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemTransferSiteInterface;
import mro.app.applyItem.service.impl.ItemTransferSiteImpl;
import mro.app.sign.service.ItemTransferSiteSignInterface;
import mro.app.sign.service.impl.ItemTransferSiteSignImpl;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.bo.ItemTransferHeaderApplyBO;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ItemTransferSiteForm;

import org.primefaces.event.SelectEvent;
//import java.util.HashMap;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemTransferSiteSignBean")
@ViewScoped
public class ApplyItemTransferSiteSignBean implements Serializable {
	private static final long serialVersionUID = -987841997542610807L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private ItemTransferSiteForm itemTransferSiteForm;
	private transient ItemTransferHeaderApplyBO itemTransferHeaderApplyBO;
	
	public ApplyItemTransferSiteSignBean() {

	}

	@PostConstruct
	public void init() {
		itemTransferHeaderApplyBO = SpringContextUtil.getBean(ItemTransferHeaderApplyBO.class);
		itemTransferSiteForm=new ItemTransferSiteForm();
		ItemTransferSiteInterface itemTransferSiteInterface = new ItemTransferSiteImpl();
		itemTransferSiteInterface.setParameter(itemTransferSiteForm);
		this.inital();
	}

	// =========================================inital===============================================================
	public void inital() { 
		itemTransferSiteForm.itemTransferSiteIntial();
		itemTransferSiteForm.setListItemTransferHeaderApply(
			itemTransferHeaderApplyBO.getList(
			SourceCategory.ITEM_SITE_TRANSFER.getInprgMap(loginInfoBean.getEmpNo()).keySet()));
	}

	// ==========================================action====================================================================
	public void onTransfer() { // 轉呈
		ItemTransferSiteSignInterface itemTransferSiteSignInterface = new ItemTransferSiteSignImpl();
		if (itemTransferSiteSignInterface.onTransfer(itemTransferSiteForm, loginInfoBean.getEmpNo())) {
			this.inital();
		}
	}

	public void onRejectToNew() { // 退回修改
		ItemTransferSiteSignInterface itemTransferSiteSignInterface = new ItemTransferSiteSignImpl();
		if (itemTransferSiteSignInterface.onRejectToNew(itemTransferSiteForm, loginInfoBean.getEmpNo())) {
			this.inital();
		}
	}

	public void onAccept() throws IOException { // 確定同意
		ItemTransferSiteSignInterface itemTransferSiteSignInterface = new ItemTransferSiteSignImpl();
		if (itemTransferSiteSignInterface.onAccept(itemTransferSiteForm, loginInfoBean.getEmpNo())) {
			this.inital();
		}
	}

	// ============================================Listen=================================================================
	public void setEmployee(Person person) {
		ItemTransferSiteSignInterface itemTransferSiteSignInterface = new ItemTransferSiteSignImpl();
		itemTransferSiteForm=itemTransferSiteSignInterface.setTransferEmployee(itemTransferSiteForm, person);
	}
	
	public void selectApply(SelectEvent event) { // 選取異動申請單
		this.selectApply((ItemTransferHeaderApply)event.getObject());
	}
	public void selectApply(ItemTransferHeaderApply itemTransferHeaderApply) { // 選取異動申請單
		ItemTransferSiteInterface itemTransferSiteInterface = new ItemTransferSiteImpl();
		itemTransferSiteForm=itemTransferSiteInterface.selectApply(
				itemTransferSiteForm,itemTransferHeaderApply);
	}
	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ItemTransferSiteForm getItemTransferSiteForm() {
		return itemTransferSiteForm;
	}

	public void setItemTransferSiteForm(ItemTransferSiteForm itemTransferSiteForm) {
		this.itemTransferSiteForm = itemTransferSiteForm;
	}
	
}
