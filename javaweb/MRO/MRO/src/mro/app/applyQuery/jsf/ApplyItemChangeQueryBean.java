package mro.app.applyQuery.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.item.jsf.AItemChangeBean;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AItemBO;
import mro.base.entity.AItem;
import mro.base.entity.Person;
import mro.form.ItemForm;
import mro.utility.ValidationUtils;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemChangeQueryBean")
@ViewScoped
public class ApplyItemChangeQueryBean implements Serializable {
	
	private static final long serialVersionUID = -2276686863328047835L;

	@ManagedProperty(value = "#{AItemChangeBean}")
	private AItemChangeBean aItemChangeBean;

	private ItemForm itemForm;
	private transient AItemBO aItemBO;

	public ApplyItemChangeQueryBean() {

	}

	@PostConstruct
	public void init() {
		aItemBO=SpringContextUtil.getBean(AItemBO.class);
		ItemInterface itemInterface = new ItemImpl();
		itemForm = new ItemForm();
		itemForm.setSelectStatus(SignStatus.getStatusOpetion(ItemStatusType.TYPE_PROCESS_AISR.getValue()));
		itemForm = itemInterface.setParameter(itemForm);
	}

	public void initial() {
		itemForm.itemIntial();
		aItemChangeBean.init();
	}

	// ==========================================action==================================
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

	public void query() {
		if (ValidationUtils.validationCondition(itemForm.getsLocationSite(),itemForm.getsOrganizationCode(),
				itemForm.getItemnum(), itemForm.getDescription(),itemForm.getBeginDate(),
				itemForm.getEndDate(), itemForm.getStatus(),
				itemForm.getDeptCode(), itemForm.getChangeby(),
				itemForm.getSelectItemCategory())) {// 驗證通過 added by r.c.
			this.initial();

			itemForm.setListAItem(aItemBO.getAItemList(itemForm.getsLocationSite(),itemForm.getsOrganizationCode(),
				itemForm.getItemnum(),itemForm.getDescription(),
				itemForm.getStatus(), itemForm.getChangeby(), itemForm.getSelectItemCategory(),
				itemForm.getDeptCode(), itemForm.getBeginDate(),itemForm.getEndDate(),
				ItemStatusType.TYPE_N, true));	
		}
	}

	public void setEmployee(Person person) {
		itemForm.setChangeby(person.getPersonId());
	}

	// =================================================================================

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
