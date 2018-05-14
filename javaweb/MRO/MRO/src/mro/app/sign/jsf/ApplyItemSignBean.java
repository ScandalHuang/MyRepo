package mro.app.sign.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.sign.service.ItemSignInterface;
import mro.app.sign.service.impl.ItemSignImpl;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.bo.AItemBO;
import mro.base.entity.AItem;
import mro.base.entity.Person;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ItemForm;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemSignBean")
@ViewScoped
public class ApplyItemSignBean implements Serializable {
	private static final long serialVersionUID = 4290578449767915195L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ItemForm itemForm;
	private ItemInterface impl;
	private transient AItemBO aItemBO;

	public ApplyItemSignBean() {

	}

	@PostConstruct
	public void init() {
		aItemBO = SpringContextUtil.getBean(AItemBO.class);
		itemForm=new ItemForm();
		impl=new ItemImpl();
		this.inital();
		itemForm=impl.setParameter(itemForm);
	}

	// =========================================inital===============================================================
	public void inital() {
		itemForm.itemIntial();
		itemForm.listIntial();
		this.getApplyList();
	}
	// ================================申請單list================================================
	/*
	 * 201511024 朱媽要求執行的需求(TN20151100112 )
	 * 料號審查畫面新增到站時間欄位
	 */
	public void getApplyList() { 
		itemForm.setSignMap(SourceCategory.NEW_ITEM.getInprgMap(loginInfoBean.getEmpNo()));
		itemForm.setListAItem(aItemBO.getList(itemForm.getSignMap().keySet()));
	}
	// ==========================================action====================================================================
	public void onTransfer() { // 轉呈
		ItemSignInterface itemSignInterface=new ItemSignImpl();
		if(itemSignInterface.onTransfer(itemForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}
	public void onRejectToNew() { // 退回修改
		ItemSignInterface itemSignInterface=new ItemSignImpl();
		if(itemSignInterface.onRejectToNew(itemForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}
	
	public void onAccept() throws IOException { // 確定同意
		ItemSignInterface itemSignInterface=new ItemSignImpl();
		if(itemSignInterface.onAccept(itemForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}
	// ============================================Listen=================================================================
	public void setEmployee(Person person) {
		ItemSignInterface itemSignInterface=new ItemSignImpl();
		itemForm=itemSignInterface.setTransferEmployee(itemForm, person);
	}
	public void selectApply(SelectEvent event) { // 選取異動申請單
		ItemInterface itemInterface=new ItemImpl();
		itemForm=itemInterface.selectApply(itemForm, (AItem) event.getObject());
	}
	// ===================================================================================================================
	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

}
