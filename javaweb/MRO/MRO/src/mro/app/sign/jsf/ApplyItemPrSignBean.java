package mro.app.sign.jsf;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.applyItem.service.PrInterface;
import mro.app.applyItem.service.impl.PrImpl;
import mro.app.sign.service.PrSignInterface;
import mro.app.sign.service.impl.PrSignImpl;
import mro.base.System.config.basicType.PrType;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.PrForm;
import mro.viewForm.PrView;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ApplyItemPrSignBean")
@ViewScoped
public class ApplyItemPrSignBean implements Serializable {
	private static final long serialVersionUID = -3779215138445510762L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private PrForm prForm;
	private PrView prView;
	private PrSignImpl prSignImpl;
	private PrImpl prImpl;

	public ApplyItemPrSignBean() {

	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		prSignImpl = new PrSignImpl();
		prImpl=new PrImpl();
		prForm = new PrForm();
		prView=new PrView();
		prForm.setPrtypeOption(PrType.getApplyPrTypeOption());
		prForm.setSelectPrtype(paramMap.get("prtype"));
		this.inital();
	}

	// =========================================inital===============================================================
	public void inital() { 
		prForm.prInital();
		prView.inital();
		prForm.setListPr(null);
		if (StringUtils.isNotBlank(prForm.getSelectPrtype())) {
			prForm = prSignImpl.setlistUnSignPr(prForm,loginInfoBean.getEmpNo());

		}
	}

	public void search() {
		this.inital();
		if (StringUtils.isBlank(prForm.getSelectPrtype())) {
			GlobalGrowl message = new GlobalGrowl();
			message.addWarnMessage("Warning", "申請單類型必須選取!");
		}
	}

	// ==========================================action====================================================================
	public void onTransfer() {
		if (prSignImpl.onTransfer(prForm, loginInfoBean.getEmpNo())) {
			this.inital();
		}
	}

	public void onRejectToNew() {
		if(prSignImpl.onRejectToNew(prForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}

	public void onCan() {
		if(prSignImpl.onCan(prForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}

	public void onMultiAppr() {
		if(prSignImpl.onMultiAppr(prForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}

	public void onAccept() {
		if(prSignImpl.onAccept(prForm, loginInfoBean.getEmpNo())){
			this.inital();
		}
	}

	// ============================================Listen=================================================================
	public void setEmployee(Person person) {
		prForm=prSignImpl.setTransferEmployee(prForm, person);
	}

	public void selectApply(SelectEvent event) { // 選取申請單
		this.selectApply((Pr) event.getObject());
	}

	public void selectApply(Pr pr) { // 選取申請單
		prForm=prImpl.selectApply(prForm, pr);
		prView.setView(prForm);
	}

	public void closePrLine() { // close prline
		prForm=prImpl.closePrLine(prForm,prView, loginInfoBean.getEmpNo());
	}
	public void reclosePrLine(Prline prline) { // close prline
		prImpl.reclosePrLine(prForm,prView, prline);
	}
	public void combinePrLine() { // combine prline
		prForm=prImpl.combinePrLine(prForm,prView, loginInfoBean.getEmpNo());
	}
	public void recombinePrLine(Prline prline) { // combine prline
		prImpl.recombinePrLine(prForm,prView, prline);
	}
	public void onSelectAllPr() { // 選取全部申請單
		prForm.setPrs((Pr[])ApplyPrUtils.onSelectAll(prForm.getPrs(), prForm.getListPr()));
	}
	// ===================================================================================================================

	
	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

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
