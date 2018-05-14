package mro.app.applyQuery.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.PrInterface;
import mro.app.applyItem.service.impl.PrImpl;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.PrBO;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.form.PrForm;
import mro.utility.ValidationUtils;
import mro.viewForm.PrView;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyItemPrQueryBean")
@ViewScoped
public class ApplyItemPrQueryBean implements Serializable {
	
	private static final long serialVersionUID = 6681109896260794207L;
	
	private PrForm prForm;
	private PrView prView;
	private transient PrBO prBO;
	
	public ApplyItemPrQueryBean() {

	}

	@PostConstruct
	public void init() {
		prBO=SpringContextUtil.getBean(PrBO.class);
		prForm=new PrForm();
		prView=new PrView();
		prForm.setSelectStatus(SignStatus.getStatusOpetion(ItemStatusType.TYPE_PROCESS_AIRC.getValue()));
		prForm.setPrtypeOption(PrType.getALLPrTypeOption());
	}

	// =========================================inital===============================================================
	public void inital() { //
		prForm.prInital();
		prView.inital();
	}
	// ==========================================action====================================================================
	public void query() {
		if (ValidationUtils.validationCondition(prForm.getsLocationSite(),prForm.getsSiteid(),
				prForm.getSelectPrtype(),prForm.getPrnum(),
				prForm.getBeginDate(),prForm.getEndDate(),prForm.getStatus(),
				prForm.getRequestby2(), prForm.getMDept())){// 驗證通過 
			this.inital();
			prForm.setListPr(prBO.getPrList(prForm.getsLocationSite(),prForm.getsSiteid(),
					prForm.getPrnum(), prForm.getStatus(),
					prForm.getRequestby2(), prForm.getMDept(), 
					prForm.getSelectPrtype(), prForm.getBeginDate(), prForm.getEndDate(), 
					ItemStatusType.TYPE_D));	
		}
		prForm.setActiveIndex(0);
	}
	public void selectApply(SelectEvent event){ // 選取申請單
		this.selectApply((Pr)event.getObject());
	}
	public void selectApply(Pr pr) { // 選取申請單
		PrInterface prInterface=new PrImpl();
		prForm=prInterface.selectApply(prForm, pr);
		prView.setView(prForm);
	}
	public void setEmployee(Person person) {
		prForm.setRequestby2(person.getPersonId());
	}

	// ===================================================================================================================


	public PrForm getPrForm() {
		return prForm;
	}

	public void setPrForm(PrForm prForm) {
		this.prForm = prForm;
	}

	public PrView getPrView() {
		return prView;
	}

	public void setPrView(PrView prView) {
		this.prView = prView;
	}
		
}
