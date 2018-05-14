package mro.app.applyItem.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import mro.app.applyItem.Utils.ApplyClassstructureUtils;
import mro.app.applyItem.service.ApplyClassstructureInterface;
import mro.app.applyItem.service.impl.ApplyClassstructureImpl;
import mro.app.commonview.jsf.ListClassstructureBean;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.ClassstructureForm;
import mro.viewForm.ApplyClassstructureView;

import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ApplyClassstructureBean")
@ViewScoped
public class ApplyClassstructureBean implements Serializable {
	
	private static final long serialVersionUID = 2465746110265794460L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{ListClassstructureBean}")
	private ListClassstructureBean listClassstructureBean;

	private ClassstructureForm classstructureForm;
	private ApplyClassstructureView applyClassstructureView;
	public ApplyClassstructureBean() {

	}

	@PostConstruct
	public void init() {
		classstructureForm = new ClassstructureForm();
		applyClassstructureView = new ApplyClassstructureView();
		// ======================setParameter==============================
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm=applyClassstructureInterface.setParameter(classstructureForm);
		// ======================分類===========================================
		listClassstructureBean.setAddButton(true); // 開啟分類選取功能
		listClassstructureBean.onSearchView(true);// 類別結構清單
		this.getApplyList(0);
	}
	public void handleChange(ValueChangeEvent event){  
		System.out.println("here "+event.getNewValue());
		}
	// ================================申請單list============================================================
	public void getApplyList(int activeI) {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm = applyClassstructureInterface.getApplyList(
				classstructureForm, loginInfoBean.getPerson(), activeI);
	}

	public void selectClassstructureHeaderApply(SelectEvent event) { // 選取異動申請單
		this.selectClassstructureHeaderApply((ClassstructureHeaderApply) event
				.getObject());
	}

	public void selectClassstructureHeaderApply(
			ClassstructureHeaderApply classstructureHeaderApply) { // 選取異動申請單
		ApplyClassstructureInterface aInterface = new ApplyClassstructureImpl();
		classstructureForm = aInterface.selectApply(
				classstructureForm, classstructureHeaderApply);
		// ============================非審核中 可以進行編輯==============================
		if (SignStatus.valueOf(classstructureForm.getClassstructureHeaderApply().getStatus()).editStatus()){
			classstructureForm.setEditButton(true);
			classstructureForm.setListAlndomainMap(aInterface.getAlnDomainMap(classstructureForm));
			classstructureForm.setListClassspec(aInterface.getClassspecList(classstructureForm));
		}
		applyClassstructureView.setView(classstructureForm);
	}

	// =========================================Action===============================================================

	// 新增Line
	public void onAddLine() {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm = applyClassstructureInterface
				.onAddLine(classstructureForm);
	}

	// 刪除Line
	public void onDeleteLine(ClassstructureLineApply classstructureLineApply) {
		if (classstructureLineApply != null) {
			ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
			classstructureForm = applyClassstructureInterface.onDeleteLine(
					classstructureForm, classstructureLineApply);
		}
	}

	// 簽核預覽
	public void onSignPreView() {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		classstructureForm = applyClassstructureInterface
				.onSignPreView(classstructureForm);
	}

	// 刪除申請單
	public void onDelete() {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		applyClassstructureInterface.onDelete(classstructureForm);
		this.getApplyList(0);
	}

	// 新建申請單
	public void newClassstructureHeaderApply() {
		classstructureForm.newApply(loginInfoBean.getPerson(),
				loginInfoBean.getLocationMap());
	}

	// 送審申請單
	public void Submit(String type) {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		GlobalGrowl message = new GlobalGrowl();
		applyClassstructureInterface.setSubmit(classstructureForm);
		String warnMessage = ApplyClassstructureUtils.validate(classstructureForm);
		if (warnMessage.length() == 0) { // 無錯誤訊息
			this.onApplySave(type);
		} else {
			message.addErrorMessage("Error", warnMessage.toString());
		}
	}

	// 儲存申請單
	public void onApplySave(String type) {
		ApplyClassstructureInterface applyClassstructureInterface = new ApplyClassstructureImpl();
		boolean applyb = applyClassstructureInterface.onApplySave(
				classstructureForm, type);
		if (applyb) {
			if (type.equals("save")) {
				ClassstructureHeaderApply classstructureHeaderApply = classstructureForm
						.getClassstructureHeaderApply();
				this.getApplyList(1);
				this.selectClassstructureHeaderApply(classstructureHeaderApply);
			} else if (type.equals("submit")) {
				this.getApplyList(0);
			}
		} else {
			classstructureForm.setActiveIndex(1);
		}
	}

	// 選取類別
	public void setClassstructureuid(String classstructureid) {
		classstructureForm.getClassstructureHeaderApply().setClassstructureid(classstructureid);
	}
	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ListClassstructureBean getListClassstructureBean() {
		return listClassstructureBean;
	}

	public void setListClassstructureBean(
			ListClassstructureBean listClassstructureBean) {
		this.listClassstructureBean = listClassstructureBean;
	}

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
