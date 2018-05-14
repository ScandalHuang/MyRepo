package mro.app.applyQuery.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ApplyClassstructureInterface;
import mro.app.applyItem.service.impl.ApplyClassstructureImpl;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.ClassstructureHeaderApplyBO;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.Person;
import mro.form.ClassstructureForm;
import mro.utility.ValidationUtils;
import mro.viewForm.ApplyClassstructureView;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyClassstructureQueryBean")
@ViewScoped
public class ApplyClassstructureQueryBean implements Serializable {
	
	private static final long serialVersionUID = -6942045471372500823L;

	private ClassstructureForm classstructureForm;
	
	private ApplyClassstructureView applyClassstructureView;

	private transient ClassstructureHeaderApplyBO classstructureHeaderApplyBO;

	public ApplyClassstructureQueryBean() {

	}

	@PostConstruct
	public void init() {
		classstructureHeaderApplyBO=SpringContextUtil.getBean(ClassstructureHeaderApplyBO.class);
		classstructureForm= new ClassstructureForm();
		applyClassstructureView= new ApplyClassstructureView();
		// ======================setParameter==============================
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm=applyClassstructureInterface.setParameter(classstructureForm);
		//=======================Status====================================
		classstructureForm.setSelectStatus(SignStatus.getStatusOpetion(ItemStatusType.TYPE_PROCESS_AISR.getValue()));
	}

	// ==========================================action====================================================================
	public void query() {
		if (ValidationUtils.validationCondition(
				classstructureForm.getsLocationSite(),classstructureForm.getsOrganizationCode(),
				classstructureForm.getApplyHeaderNum()
				, classstructureForm.getBeginDate(),classstructureForm.getEndDate(),
				classstructureForm.getStatus(), classstructureForm.getCreateBy(), classstructureForm.getAction())){
			classstructureForm.intial();
			classstructureForm.setListClassstructureHeaderApply(
					classstructureHeaderApplyBO.getApplyList(
						classstructureForm.getsLocationSite(),classstructureForm.getsOrganizationCode(),
						null, classstructureForm.getApplyHeaderNum(), 
						classstructureForm.getStatus(), classstructureForm.getAction(),
						classstructureForm.getCreateBy(), classstructureForm.getBeginDate(),
						classstructureForm.getEndDate(), ItemStatusType.TYPE_N));
		}
		classstructureForm.setActiveIndex(0);
	}

	public void selectClassstructureHeaderApply(SelectEvent event) { // 選取申請單
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm=applyClassstructureInterface.selectApply(
				classstructureForm,(ClassstructureHeaderApply) event.getObject());
		applyClassstructureView.setView(classstructureForm);
	}
	public void setEmployee(Person person) {
		classstructureForm.setCreateBy(person.getPersonId());
	}
	// ===================================================================================================================

	public ClassstructureForm getClassstructureForm() {
		return classstructureForm;
	}

	public void setClassstructureForm(ClassstructureForm classstructureForm) {
		this.classstructureForm = classstructureForm;
	}

	public ApplyClassstructureView getApplyClassstructureView() {
		return applyClassstructureView;
	}

	public void setApplyClassstructureView(
			ApplyClassstructureView applyClassstructureView) {
		this.applyClassstructureView = applyClassstructureView;
	}
}
