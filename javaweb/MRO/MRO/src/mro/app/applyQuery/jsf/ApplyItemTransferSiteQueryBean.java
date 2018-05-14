package mro.app.applyQuery.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemTransferSiteInterface;
import mro.app.applyItem.service.impl.ItemTransferSiteImpl;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.ItemTransferHeaderApplyBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.Person;
import mro.form.ItemTransferSiteForm;
import mro.utility.ValidationUtils;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemTransferSiteQueryBean")
@ViewScoped
public class ApplyItemTransferSiteQueryBean implements Serializable {
	
	private static final long serialVersionUID = 2660294046963506928L;
	
	private ItemTransferSiteForm itemTransferSiteForm;
	private transient ItemTransferHeaderApplyBO itemTransferHeaderApplyBO;
	

	public ApplyItemTransferSiteQueryBean() {

	}

	@PostConstruct
	public void init() {
		itemTransferHeaderApplyBO=SpringContextUtil.getBean(ItemTransferHeaderApplyBO.class);
		itemTransferSiteForm=new ItemTransferSiteForm();
		itemTransferSiteForm.setSelectStatus(SignStatus.getStatusOpetion(ItemStatusType.TYPE_PROCESS_AISR.getValue()));
		ItemTransferSiteInterface itemTransferSiteInterface = new ItemTransferSiteImpl();
		itemTransferSiteInterface.setParameter(itemTransferSiteForm);
	}

	// ==========================================action====================================================================
	public void query() {
		if (ValidationUtils.validationCondition(itemTransferSiteForm.getApplyHeaderNum()
				, itemTransferSiteForm.getBeginDate(),itemTransferSiteForm.getEndDate(),
				itemTransferSiteForm.getStatus(), itemTransferSiteForm.getCreateBy(), 
				itemTransferSiteForm.getAction(),itemTransferSiteForm.getLocationSite())){
			itemTransferSiteForm.itemTransferSiteIntial();
			itemTransferSiteForm.setListItemTransferHeaderApply(
				itemTransferHeaderApplyBO.getItemTransferHeaderApply(
						itemTransferSiteForm.getApplyHeaderNum(), itemTransferSiteForm.getStatus(),
						itemTransferSiteForm.getAction(), itemTransferSiteForm.getCreateBy(),
						itemTransferSiteForm.getBeginDate(), itemTransferSiteForm.getEndDate(),
						ItemStatusType.TYPE_N,itemTransferSiteForm.getLocationSite()));
		}
		itemTransferSiteForm.setActiveIndex(0);
	}

	public void selectApply(SelectEvent event) { 
		ItemTransferSiteInterface itemTransferSiteInterface = new ItemTransferSiteImpl();
		itemTransferSiteForm=itemTransferSiteInterface.selectApply(
				itemTransferSiteForm,(ItemTransferHeaderApply) event.getObject());
	}
	public void setEmployee(Person person) {
		itemTransferSiteForm.setCreateBy(person.getPersonId());
	}
	// ===================================================================================================================

	public mro.form.ItemTransferSiteForm getItemTransferSiteForm() {
		return itemTransferSiteForm;
	}

	public void setItemTransferSiteForm(
			mro.form.ItemTransferSiteForm itemTransferSiteForm) {
		this.itemTransferSiteForm = itemTransferSiteForm;
	}


}
