package mro.app.applyQuery.jsf;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AItemBO;
import mro.base.entity.AItem;
import mro.base.entity.Person;
import mro.form.ItemForm;
import mro.utility.ValidationUtils;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemQueryBean")
@ViewScoped
public class ApplyItemQueryBean implements Serializable {
	
	private static final long serialVersionUID = 4046267631458346192L;
	
	private ItemForm itemForm;
	private transient AItemBO aItemBO;
	
	public ApplyItemQueryBean() {

	}

	@PostConstruct
	public void init() {
		aItemBO=SpringContextUtil.getBean(AItemBO.class);
		ItemInterface itemInterface=new ItemImpl();
		itemForm=new ItemForm();
		itemForm.setSelectStatus(SignStatus.getStatusOpetion(ItemStatusType.TYPE_PROCESS_AISR.getValue()));
		itemForm=itemInterface.setParameter(itemForm);
	}

	// ==========================================action====================================================================
	public void selectApply(BigDecimal  eaudittransid) { // 選取申請單
		ItemInterface itemInterface=new ItemImpl();
		itemForm.itemIntial();
		itemForm=itemInterface.selectApply(itemForm,aItemBO.getAItem(eaudittransid,true));
	}
	public void selectApply(SelectEvent event) { // 選取申請單
		ItemInterface itemInterface=new ItemImpl();
		itemForm.itemIntial();
		itemForm=itemInterface.selectApply(itemForm,(AItem) event.getObject());
	}
	public void query() {
		if (ValidationUtils.validationCondition(itemForm.getsLocationSite(),itemForm.getsOrganizationCode(),
				itemForm.getItemnum(),itemForm.getDescription(),
				itemForm.getBeginDate(),itemForm.getEndDate(), itemForm.getStatus(),
				itemForm.getDeptCode(),itemForm.getChangeby(),itemForm.getSelectItemCategory())){// 驗證通過  added by r.c.	
			itemForm.itemIntial();
			itemForm.setListAItem(aItemBO.getAItemList(itemForm.getsLocationSite(),itemForm.getsOrganizationCode(),
				itemForm.getItemnum(),itemForm.getDescription(),
				itemForm.getStatus(), itemForm.getChangeby(), itemForm.getSelectItemCategory(),
				itemForm.getDeptCode(), itemForm.getBeginDate(),itemForm.getEndDate(),
				ItemStatusType.TYPE_N, false));	
		}
	}
	public void setEmployee(Person person) {
		itemForm.setChangeby(person.getPersonId());
	}	
	// ===================================================================================================================

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

}
