package mro.app.applyQuery.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.applyItem.Utils.ApplyPrValidationUtils;
import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.app.applyItem.service.PrInterface;
import mro.app.applyItem.service.impl.PrImpl;
import mro.app.applyQuery.bo.ApplyItemPrQueryBo;
import mro.app.sign.service.PrSignInterface;
import mro.app.sign.service.impl.PrSignImpl;
import mro.app.util.DownLoadExcel;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.PrBO;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.PrForm;
import mro.viewForm.PrQueryAppView;
import mro.viewForm.PrView;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ApplyItemPrQueryApprBean")
@ViewScoped 
public class ApplyItemPrQueryApprBean implements Serializable {
	
	private static final long serialVersionUID = -557552865035693385L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private PrForm prForm;
	private PrView prView;
	private PrQueryAppView prQueryAppView;
	private transient ApplyItemPrQueryBo applyItemPrQueryBo;
	
	public ApplyItemPrQueryApprBean() {

	}

	@PostConstruct
	public void init() {
		applyItemPrQueryBo=SpringContextUtil.getBean(ApplyItemPrQueryBo.class);
		prForm=new PrForm();
		prView=new PrView();
		prQueryAppView=new PrQueryAppView();
		prForm.setRequestby2(loginInfoBean.getEmpNo());
		prForm.setPrtypeOption(PrType.getTransPrTypeOption());
	}

	public void queryAppr() {
		GlobalGrowl message = new GlobalGrowl();
		PrBO prBO = SpringContextUtil.getBean(PrBO.class);
		prForm.prInital();
		prForm.setListPr(null);
		if(StringUtils.isBlank(prForm.getSelectPrtype())){
			message.addInfoMessage("Info", "請選擇申請單類型!!");
		}else{
			prQueryAppView.setView(prForm, loginInfoBean.getEmpNo());  //set page view
			prForm.setListPr(prBO.getTransPrList(prForm.getPrnum(), SignStatus.APPR,
					prForm.getRequestby2(), prForm.getSelectPrtype(),
					prForm.getBeginDate(), prForm.getEndDate(), prForm.getTransferFlag()));
		}
		
	}
	public void pmreqprtx() {
		GlobalGrowl message = new GlobalGrowl();
		String  str="";
		if(Utility.isNotEmpty(prForm.getPrs())){
			if(ApplyPrUtils.apprValidate(prForm.getPrs(),loginInfoBean.getPerson(),message)){ //驗證通過
				str=applyItemPrQueryBo.mroPrToErp(loginInfoBean.getPerson(), prForm.getPrs());
				message.addInfoMessage("Info", str);
				queryAppr();
			}
		}else{
			message.addWarnMessage("Warm", "請選擇Pr!!.");
		}
	}
	/*
	 * 更新TRANSFER_FLAG
	 */
	public void updatePr(boolean transferFlag){
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer error=ApplyPrValidationUtils.validatePrPoTransfer(
				prForm.getPr(), transferFlag, new StringBuffer());
		if(error.length()>0){
			message.addErrorMessage("Error",error.toString());
		}else{
			applyItemPrQueryBo.updatePr(prForm.getPr(),prForm.getListPrline(),transferFlag);
			message.addInfoMessage("Upload", "PRNUM:"+prForm.getPr().getPrnum()+" Upload successful!");
			this.queryAppr();
		}
		
	}
	public void selectApply(SelectEvent event){ // 選取申請單
		this.selectApply((Pr)event.getObject());
	}
	public void selectApply(Pr pr) { // 選取申請單
		PrInterface prInterface=new PrImpl();
		prForm=prInterface.selectApply(prForm, pr);
		prQueryAppView.setView(prForm, loginInfoBean.getEmpNo());  //set page view
	}

	public void onApplySave(){ // 儲存申請單
		PrSignInterface prSignInterface=new PrSignImpl();
		if(prSignInterface.onClosePrLine(prForm)){
			Pr pr=prForm.getPr();
			this.selectApply(pr);
		}
		if(prSignInterface.onCombinePrLine(prForm)){
			Pr pr=prForm.getPr();
			this.selectApply(pr);
		}
	}
	
	public void closePrLine(Prline prline) { // close prline
		PrInterface prInterface=new PrImpl();
		prInterface.closePrLine(prForm,prView,prline, loginInfoBean.getEmpNo());
	}
	public void combinePrLine(Prline prline) { // combine prline
		PrInterface prInterface=new PrImpl();
		prInterface.combinePrLine(prForm,prView,prline, loginInfoBean.getEmpNo());
	}

	public void onCan() { // 取消申請單
		PrSignInterface prSignInterface=new PrSignImpl();
		if(prSignInterface.onCanSign(prForm, loginInfoBean.getEmpNo())){
			this.queryAppr();
		}
	}

	public void onDeletePR() { // 刪除申請單
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		if (prForm.getPrs().length > 0) {
			applyItemPrBo.deletePr(prForm.getPrs());
			this.queryAppr();
			message.addInfoMessage("刪除申請單", "刪除申請單 成功!");
		} else {
			message.addWarnMessage("Warm", "請選擇Pr!!.");
		}
	}
	public void onEditPrline(RowEditEvent event) {
		GlobalGrowl message = new GlobalGrowl();
		Prline prline = (Prline) event.getObject();
		applyItemPrQueryBo.updatePrline(prForm.getPr(), prline);
		message.addInfoMessage("更新", "更新料號" + prline.getItemnum() + " 成功!");

	}	
	
	public void onSelectAllPr() { // 選取全部申請單
		prForm.setPrs((Pr[])ApplyPrUtils.onSelectAll(prForm.getPrs(), prForm.getListPr()));
	}
	
	public void setEmployee(Person person) {
		prForm.setRequestby2(person.getPersonId());
	}
	
	public void downloadExcel() throws Exception{
		if(Utility.isNotEmpty(prForm.getPrs())){
			DownLoadExcel downLoadExcel=new DownLoadExcel();
			downLoadExcel.postProcessXLS(applyItemPrQueryBo.getApplyPrlineList(
					prForm.getPrs(), SignStatus.OPEN));
			applyItemPrQueryBo.updateEPFlag(prForm.getPrs());
			queryAppr();
		}else{
			GlobalGrowl message = new GlobalGrowl();
			message.addWarnMessage("Warm", "請選擇Pr!!.");
		}
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

	public PrQueryAppView getPrQueryAppView() {
		return prQueryAppView;
	}

	public void setPrQueryAppView(PrQueryAppView prQueryAppView) {
		this.prQueryAppView = prQueryAppView;
	}

	public PrView getPrView() {
		return prView;
	}

	public void setPrView(PrView prView) {
		this.prView = prView;
	}
	
}
