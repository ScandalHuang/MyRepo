package mro.app.applyItem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.applyItem.Utils.ApplyPrValidationUtils;
import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.app.applyItem.service.PrInterface;
import mro.app.sign.service.PrSignInterface;
import mro.app.sign.service.impl.PrSignImpl;
import mro.app.util.HrEmpUtils;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.entity.PrMcParameter;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AttachmentBO;
import mro.base.bo.MroOrgFacilityEqBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.PrForm;
import mro.viewForm.PrView;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class PrImpl implements PrInterface {
	
	@Override
	public PrForm selectApply(PrForm prForm, Pr pr) {
		prForm.prInital();
		prForm.setPr(pr);
		prForm.setLocationMap();//pr對應的locationMap
		prForm.setPrControlConfig();//pr對應的控管設定
		// =============================prLine list=============================================
		this.setPrlineList(prForm);
		// =============================pr 相關人員姓名=============================================
		this.setPrNameMap(prForm);
		// =============================簽核歷程====================================================
		prForm.setSignHistoryUrl(WorkflowActionUtils.onSignHistory(prForm
				.getPr().getTaskId()));
		// =============================檔案下載====================================================
		this.setDownLoadFile(prForm);
		//=============申請目的 ============================
		this.setReasonFlag(prForm);
		//=============控管參數 ============================
		prForm.setPrMcParameter(new PrMcParameter(pr.getPrtype()));
		return prForm;
	}

	@Override
	public void setDownLoadFile(PrForm prForm) {
		prForm.setActiveIndex(1);
		AttachmentBO attachmentBO=SpringContextUtil.getBean(AttachmentBO.class);
		
		prForm.setFileHeaderType(FileCategory.PR_HEADER_ATTACHMENT);
		prForm.getDowloadFileMap().putAll(attachmentBO.getMap(
				prForm.getPr().getPrid().toString(), prForm.getFileHeaderType(), true));
		
		prForm.setFileLineType(FileCategory.PR_LINE_ATTACHMENT);
		prForm.getDowloadFileMap().putAll(attachmentBO.getMap(
				prForm.getLineIds(),prForm.getFileLineType(),false));
	}
	@Override
	public void reclosePrLine(PrForm prForm,PrView prView,Prline prline) {
		if(prline!=null){
			prline.setChangeBy("");
			prForm.getListPrline().add(prline);
			prForm.getListDeletePrline().remove(prline);
			prForm.getListClosePrline().remove(prline);
			prView.removeCloseRemark(prline);
			Collections.sort(prForm.getListPrline());  //排序
		}
	}
	@Override
	public void recombinePrLine(PrForm prForm,PrView prView,Prline prline) {
		if(prline!=null){
			prline.setChangeBy("");
			prForm.getListPrline().add(prline);
			//prForm.getListDeletePrline().remove(prline);
			prForm.getListCombinePrline().remove(prline);
			prView.removeCombineRemark(prline);
			Collections.sort(prForm.getListPrline());  //排序
		}
	}
	@Override
	public void closePrLine(PrForm prForm,PrView prView,Prline prline, String loginEmpNO) {
		if(prline!=null){
			prline.setChangeBy(loginEmpNO);
			prForm.getListPrline().remove(prline);
			prForm.getListDeletePrline().add(prline);
			prForm.getListClosePrline().add(prline);
			prView.setCloseRemark(prline);
		}
	}
	@Override
	public void combinePrLine(PrForm prForm,PrView prView,Prline prline, String loginEmpNO) {
		if(prline!=null){
			prline.setChangeBy(loginEmpNO);
			prForm.getListPrline().remove(prline);
			//prForm.getListDeletePrline().add(prline);
			prForm.getListCombinePrline().add(prline);
			prView.setCombineRemark(prline);
		}
	}
	@Override
	public PrForm closePrLine(PrForm prForm,PrView prView, String loginEmpNO) {
		for (Prline p : prForm.getDeleteListPrline()) {
			this.closePrLine(prForm,prView, p, loginEmpNO);
		}
		prForm.setDeleteListPrline(null);
		return prForm;
	}
	@Override
	public PrForm combinePrLine(PrForm prForm,PrView prView, String loginEmpNO) {
		for (Prline p : prForm.getDeleteListPrline()) {
			this.combinePrLine(prForm,prView, p, loginEmpNO);
		}
		prForm.setDeleteListPrline(null);
		return prForm;
	}

	@Override
	public PrForm onEmployeeClear(PrForm prForm, Long type) {
		if (type == 1) { // 會簽1
			prForm.getPr().setCountersign1("");
		} else if (type == 2) { // 會簽2
			prForm.getPr().setCountersign2("");
		}
		return prForm;
	}

	@Override
	public PrForm setEmployee(PrForm prForm, Person person) {
		if (person != null && person.getPersonId() != null) {
			if (prForm.getEmployeeType() == 0) { // 選取申請人
				prForm.setRequestedby2(person);
			} else if (prForm.getEmployeeType() == 1) { // 會簽1
				prForm.getPr().setCountersign1(person.getPersonId());
			} else if (prForm.getEmployeeType() == 2) {// 會簽2
				prForm.getPr().setCountersign2(person.getPersonId());
			}
			prForm.getPrNameMap().put(person.getPersonId(), person.getDisplayName());
		} else {
			this.onEmployeeClear(prForm, prForm.getEmployeeType());
		}
		return prForm;

	}

	@Override
	public PrForm setSelectPrline(PrForm prForm, Prline prline,ActionType actionType) {
		if (prline != null && prForm.isEditButton()) {
			if (actionType.compareTo(ActionType.add)==0) {
				prForm.getListPrline().add(prline);
				prForm.setMaxPrlineNum(prForm.getMaxPrlineNum()+1);
			} else if (actionType.compareTo(ActionType.update)==0) {
				prForm.getListPrline().set(prForm.getListPrline().indexOf(prline), prline);
			}
			prForm.getPr().setTotalbasecost2(this.gettotalcost(prForm));
		}
		return prForm;
	}

	@Override
	public BigDecimal gettotalcost(PrForm prForm) {
		BigDecimal totalcost=new BigDecimal(0);
		for(Prline p:prForm.getListPrline()){
			totalcost=totalcost.add(p.getLinecost());
		}
		return totalcost;
	}

	@Override
	public PrForm deletePrLine(PrForm prForm) {
		for (Prline p : prForm.getDeleteListPrline()) {
			prForm.getListPrline().remove(p);
			prForm.getListDeletePrline().add(p);
		}
		//============================================================
		if(SignStatus.valueOf(prForm.getPr().getStatus()).equals(SignStatus.DRAFT)){ //新申請單 prline編號再進行重編
			prForm.setMaxPrlineNum(1);
			for (Prline p : prForm.getListPrline()) {
				p.setPrlinenum(prForm.getMaxPrlineNum());
				prForm.setMaxPrlineNum(prForm.getMaxPrlineNum()+1);
			}
		}
		prForm.setDeleteListPrline(null);
		prForm.getPr().setTotalbasecost2(this.gettotalcost(prForm));
		return prForm;
	}

	@Override
	public void onCan(PrForm prForm) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		applyItemPrBo.updatePrOther(prForm.getPr(), SignStatus.CAN);
		message.addInfoMessage("取消申請單", "取消申請單 成功!");
	}

	@Override
	public void onDelete(PrForm prForm) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		applyItemPrBo.deletePr(prForm);
		message.addInfoMessage("刪除申請單", "刪除申請單 成功!");
	}

	@Override
	public boolean onApplySave(PrForm prForm, ActionType actionType,Person person,GlobalGrowl message) {
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		prForm.getPr().setTotalbasecost2(this.gettotalcost(prForm));
		this.updaetMroOrgFacilityEq(prForm);
		if (actionType.compareTo(ActionType.save)==0) {
			applyItemPrBo.onApplySave(actionType,prForm); // 儲存
			if(message!=null) {message.addInfoMessage("Save", "Save successful.");}
			return true;
		} else if (actionType.compareTo(ActionType.submit)==0) {
			prForm.getPr().setTaskId(WorkflowActionUtils.onPrSubmit(prForm,getSignParameter(prForm)));
			if (prForm.getPr().getTaskId() != null) {
				applyItemPrBo.onApplySave(actionType,prForm); // 儲存
				if(message!=null) {message.addInfoMessage("Submit", "Submit successful.");}
				return true;
			} else {
				if(message!=null) {message.addErrorMessage("送審失敗", "請重新送審或與MIS聯繫!");}
			}
		}
		return false;
	}
	

	@Override
	public PrForm onSignPreView(PrForm prForm) {
		prForm.setSignPreViewUrl(WorkflowActionUtils.onSignPreView(this
				.getSignParameter(
						prForm,
						ApplyPrValidationUtils.validateMcSigner(
								prForm.getListPrline(), prForm.getPr(),
								new StringBuffer()).toString())));

		return prForm;
	}

	@Override
	public int getPrSignProcessId(Pr pr) {
		if (pr.getPrtype().equals(PrType.R1CONTROL.name())) {
			return WorkflowUtils.getR1ControlSignProcessId(pr);
		} else if (pr.getPrtype().equals(PrType.R2CONTROL.name())) {
			return WorkflowUtils.getR2ControlSignProcessId();
		} else if (pr.getPrtype().equals(PrType.R1PMREQ.name())) {
			return WorkflowUtils.getR1PmreqSignProcessId(pr);
		} else if (pr.getPrtype().equals(PrType.R2PMREQ.name())) {
			return WorkflowUtils.getR2PmreqSignProcessId();
		} else {
			return WorkflowUtils.getPrSignProcessId(pr);
		}
	}

	@Override
	public boolean getMroOrgFacilityEqSize(Pr pr,LocationSiteMap locationSiteMap) {
		MroOrgFacilityEqBO bo = SpringContextUtil.getBean(MroOrgFacilityEqBO.class);
		List list=bo.getList(locationSiteMap,pr.getReasoncode(),null);
		if(StringUtils.isNotBlank(pr.getReasoncode()) && Utility.isNotEmpty(list)){
			return true;
		}
		return false;
	}

	@Override
	public void updaetMroOrgFacilityEq(PrForm prForm) {
		if(!prForm.isEqFlag()) prForm.getListPrline().forEach(l->l.setEqId(""));
	}
	@Override
	public String getSignParameter(PrForm prForm) {
		return this.getSignParameter(prForm,"");
	}
	@Override
	public String getSignParameter(PrForm prForm,String warnMessage) {
		JSONObject json = new JSONObject();
		try {
			json.put("SITEID", prForm.getPr().getSiteid());
			if(prForm.getListPrline()!=null && prForm.getListPrline().size()>0){
				json.put("CLASSSTRUCTUREID", prForm.getListPrline().get(0).getClassstructureid());
			}
			json.put("PRTYPE", prForm.getPr().getPrtype());
			json.put("DEPTCODE", prForm.getPr().getMDept());
			json.put("reasoncode", prForm.getPr().getReasoncode());
			
			json.put("processId", getPrSignProcessId(prForm.getPr()));
			json.put("empno", prForm.getPr().getRequestedby2());
			json.put("price", prForm.getPr().getTotalbasecost2());
			json.put("counterEmp", WorkflowUtils.getCounterArray(prForm.getPr()));
			json.put("comment", prForm.getSignComment());
			json.put("warnMessage", warnMessage);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.toString());
		}
		

		return json.toString();
	}

	@Override
	public void setPrNameMap(PrForm prForm) {
		List<String> actors = new ArrayList<String>();
		if(Utility.isNotEmpty(prForm.getListClosePrline())){
			for (Prline prline : prForm.getListClosePrline()) {
				actors.add(prline.getChangeBy());
			}
		}
		if(Utility.isNotEmpty(prForm.getListPrline())){
			for (Prline prline : prForm.getListPrline()) {
				actors.add(prline.getChangeBy());
			}
		}
		if(Utility.isNotEmpty(prForm.getListCombinePrline())){
			for (Prline prline : prForm.getListCombinePrline()) {
				actors.add(prline.getChangeBy());
			}
		}
		actors.addAll(Arrays.asList(prForm.getPr().getRequestedby(),
				prForm.getPr().getRequestedby2(),prForm.getPr().getDeptsupervisor(),
				prForm.getPr().getCountersign1(),prForm.getPr().getCountersign2()));
		prForm.setPrNameMap(HrEmpUtils.getMapList(actors));
	}

	@Override
	public void setParameter(PrForm prForm) {
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		// 2015/05/06  測試報告中決議取消
//		prForm.setPriorityOption(bean.getParameterOption().get(ParameterType.PRPRIORITY));		
		prForm.setApplyPurpose(bean.getParameterOption().get(ParameterType.R2_PMREQ_REASONCODE));
	}

	@Override
	public void setPrlineList(PrForm prForm) {
		PrlineBO prlineBO = SpringContextUtil.getBean(PrlineBO.class);
		prForm.setMaxPrlineNum(1);
		List<Prline> list=prlineBO.getPrlineList(prForm.getPr().getPrid(), null);
		for(Prline prline:list){
			if(Utility.equals(prline.getLineClosedCode(), SignStatus.CLOSE)){
				prForm.getListClosePrline().add(prline);
			}else if(Utility.equals(prline.getLineClosedCode(), SignStatus.COMB)){
				prForm.getListCombinePrline().add(prline);
			}else{
				prForm.getListPrline().add(prline);
			}
			prForm.setMaxPrlineNum(prline.getPrlinenum()+1);
		}
		
	}

	@Override
	public void setReasonFlag(PrForm prForm) {
		if(StringUtils.isNotBlank(prForm.getPr().getPrtype()) && 
			prForm.getPr().getPrtype().equals(PrType.R2PMREQ.toString())){
			prForm.setReasonFlag(true);
		}else{
			prForm.setReasonFlag(false);
		}
		
	}

	/*
	 *使用finallyapprove 一定要用TANSACTION包起來，避免資料不一致
	 */
	@Override
	public boolean onApplyProcess(PrForm prForm, ActionType actionType, Person person,GlobalGrowl message) {
		if(actionType.compareTo(ActionType.save)==0){
			StringBuffer warnMessage=ApplyPrValidationUtils.PrValidate(prForm,person,new StringBuffer());
			if(warnMessage.length()>0){
				if(message!=null) {message.addErrorMessage("Error",warnMessage.toString());}
				return false;
			}
		}else{
			if(!ApplyPrUtils.validate(prForm,person, message))return false;
		}
		
		boolean save=true;
		if(actionType.compareTo(ActionType.noSign)==0){
			PrSignInterface signImp=new PrSignImpl();
			save=onApplySave(prForm, ActionType.save,person,message); //先儲存在跑最後審核的程序
			if(save) signImp.onFinalApprNoSign(prForm);
		}else{
			if(actionType.compareTo(ActionType.submit)==0 && prForm.getPr().getPrid()==null){
				save=onApplySave(prForm, ActionType.save,person,null); //先儲存在送審
			}
			if(save) save=onApplySave(prForm, actionType,person,message);
		}
		return save;
	}

}
