package mro.app.sign.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.item.jsf.AItemChangeBean;
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

@ManagedBean(name = "ApplyItemChangeSignBean")
@ViewScoped
public class ApplyItemChangeSignBean implements Serializable {
	private static final long serialVersionUID = -8758350348694854836L;

	@ManagedProperty(value = "#{AItemChangeBean}")
	private AItemChangeBean aItemChangeBean;
	
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private ItemForm itemForm;
	private transient AItemBO aItemBO;

	public ApplyItemChangeSignBean() {

	}

	@PostConstruct
	public void init() {
		aItemBO=SpringContextUtil.getBean(AItemBO.class);
		itemForm=new ItemForm();
		this.inital();
		ItemInterface itemInterface=new ItemImpl();
		itemForm=itemInterface.setParameter(itemForm);
	}

	// =========================================inital===============================================================
	public void inital() {
		itemForm.itemIntial();
		itemForm.setSignMap(SourceCategory.CHANGE_ITEM.getInprgMap(loginInfoBean.getEmpNo()));
		itemForm.setListAItem(aItemBO.getList(itemForm.getSignMap().keySet()));
		aItemChangeBean.init();
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

	public void selectApply(SelectEvent event) throws Exception { // 選取異動申請單
		ItemInterface itemInterface = new ItemImpl();
		itemForm = itemInterface.selectApply(itemForm, (AItem) event.getObject());

		// ================================異動前申請單====================================
		aItemChangeBean.setPreItemForm(itemInterface.setPreItemForm(itemForm));
		// ========================異動欄位判斷===================================
		itemForm = itemInterface.setApplyChangeMap(aItemChangeBean.getPreItemForm(),
				itemForm);
		// ================================異動後申請單====================================
		aItemChangeBean.setItemForm(itemForm);
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

	public AItemChangeBean getaItemChangeBean() {
		return aItemChangeBean;
	}

	public void setaItemChangeBean(AItemChangeBean aItemChangeBean) {
		this.aItemChangeBean = aItemChangeBean;
	}
	
}
