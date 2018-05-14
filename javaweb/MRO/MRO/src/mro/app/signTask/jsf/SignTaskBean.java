package mro.app.signTask.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.signTask.bo.SignTaskAppBo;
import mro.app.signTask.utils.SignTaskUtils;
import mro.app.signView.bo.SignPreViewBo;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SignTaskStatus;
import mro.base.bo.HrEmpBO;
import mro.base.bo.SignTaskBO;
import mro.base.bo.SignTaskListBO;
import mro.base.entity.HrEmp;
import mro.base.entity.SignTask;
import mro.base.entity.SignTaskList;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "SignTaskBean")
@ViewScoped
public class SignTaskBean implements Serializable {
	private static final long serialVersionUID = 4910169820418346129L;
	public SignTaskBean() {

	}

	@PostConstruct
	public void init() {
	}

	// =======================簽核送出================================
	public BigDecimal onSubmit(int taskId, String applyNum, // SIGN_PROCESS_ID
			String createBy,String param) {   // empno 送審人 ,createBy 申請人,map  其他參數
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		Map map=SignTaskUtils.getSignParameter(param);
		
		int processId = Integer.valueOf(ObjectUtils.toString(map.get("processId"),"0"));
		String empno = ObjectUtils.toString(map.get("empno")); 
		int price =  (int) Double.parseDouble(ObjectUtils.toString(map.get("price"),"0"));
		String comment=ObjectUtils.toString(map.get("comment")); //送審者簽核意見
		String[] counterEmp = ObjectUtils.toString(map.get("counterEmp")).split(","); 
		
		// ================================驗證系統來源人數是否正確=======================================
		if (SignTaskUtils.vaildateSubmit(empno,processId).length()>0) {
			return null;
		}
		
		// ===============================驗證通過=======================================================
			//=====================簽核清單========================================
			SignPreViewBo signPreViewBo=SpringContextUtil.getBean(SignPreViewBo.class);
			List<MroSignTaskListV> signHistoryVOList=signPreViewBo.onSignPreView(
					processId, empno,comment, price, counterEmp, map);
			if(SignTaskUtils.vaildateSignList(signHistoryVOList).length()>0){
				return null;
			}
			
			SignTask signTask = signTaskBo.onSubmit(processId, taskId, applyNum,
					empno,createBy,signHistoryVOList);
			BigDecimal r=signTask!=null?signTask.getTaskId():null;
		//================================mail==========================================================
		if(r!=null){
			signTaskBo.sendmail(r, empno, SignTaskStatus.INPRG,"","",null);
		}
		return r;
	}

	// ======================簽核同意========================================
	public String onAppr(BigDecimal taskId, String empNO, String comment,String mailContent,List<String> notifyList) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		HrEmpBO hrEmpBO = SpringContextUtil.getBean(HrEmpBO.class);
		Date date = new Date(System.currentTimeMillis());
		if (!SignTaskUtils.vaildateApprove(taskId, empNO)) {
			return null;
		}
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, empNO, SignTaskStatus.APPROVE,
				comment, date);
		String r=signTaskBo.getNextSignTaskList(taskId, signTaskList
				.getSignSeqId(), date);
		
		signTaskBo.sendmail(taskId, empNO, SignTaskStatus.valueOf(r),comment,mailContent,notifyList);
		
		//===========================簽核人員離職自動轉呈============================================
		if(r.equals(SignStatus.INPRG.toString())){
			signTaskList=signTaskListBO.getNowSignTaskList(taskId);
			HrEmp activeHrEmp=hrEmpBO.getActiveHrEmp(signTaskList.getActorId());
			if(activeHrEmp==null){ 
				this.onAutoTransfer(taskId, signTaskList.getActorId(), comment);
			}
		}
		
		return r;

	}
	// ======================會簽========================================
		public boolean onCounterSign(BigDecimal taskId,String transferEmpNo) {
			SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
			SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
			Date date = new Date(System.currentTimeMillis());
			SignTaskList signTaskList = signTaskListBO.getNowSignTaskList(taskId);
			signTaskBo.onTran(taskId, signTaskList.getSignSeqId(),
					transferEmpNo, date, null);
			return true;

		}
	// ======================簽核轉呈(下一個簽核者往後調整)========================================
	public boolean onTransfer(BigDecimal taskId, String empNO, String comment,
			String transferEmpNo) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		Date date = new Date(System.currentTimeMillis());
		if (!SignTaskUtils.vaildateApprove(taskId, empNO)) {
			return false;
		}
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, empNO,
				SignTaskStatus.TRANSFER, comment, date);
		signTaskBo.onTran(taskId, signTaskList.getSignSeqId(),
				transferEmpNo, date, "轉呈");
		//================================mail==========================================================
		signTaskBo.sendmail(taskId, empNO, SignTaskStatus.TRANSFER,comment,"",null);
		
		return true;

	}
	// ======================管理員轉呈(下一個簽核者往後調整)========================================
	public boolean onTransferByAdmin(BigDecimal taskId, String empNO, String comment,
			String transferEmpNo) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		Date date = new Date(System.currentTimeMillis());
		if (taskId == null || StringUtils.isBlank(empNO)) {
			return false;
		}
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, empNO,
				SignTaskStatus.TRANSFER, comment, date);
		signTaskBo.onTran(taskId, signTaskList.getSignSeqId(),
				transferEmpNo, date, "轉呈");
		//================================mail==========================================================
		signTaskBo.sendmail(taskId, empNO, SignTaskStatus.TRANSFER,comment,"",null);
		
		return true;

	}
	// ======================自動轉呈(下一個簽核者往後調整)========================================
	public boolean onAutoTransfer(BigDecimal taskId, String actorID,String comment) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		Date date = new Date(System.currentTimeMillis());
		HrEmp hrEmp=hrEmpBO.getHrEmp(actorID);
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, "",
				SignTaskStatus.TRANSFER, comment, date);
		HrEmp manager=hrEmpBO.getHrEmp(hrEmp.getManagerId());
		if(manager!=null){
			signTaskBo.onTran(taskId, signTaskList.getSignSeqId(),
					hrEmp.getManagerId(), date, "轉呈");
			//================================mail==========================================================
			signTaskBo.sendmail(taskId, actorID, SignTaskStatus.TRANSFER,comment,"",null);
		}
		return true;

	}
	// ======================簽核拒絕========================================
	public boolean onReject(BigDecimal taskId, String empNO, String comment) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		Date date = new Date(System.currentTimeMillis());
		if (!SignTaskUtils.vaildateApprove(taskId, empNO)) {
			return false;
		}
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, empNO, SignTaskStatus.REJECT,
				comment, date);
		signTaskBo.onReject(taskId, signTaskList.getSignSeqId());
		//================================mail==========================================================
		signTaskBo.sendmail(taskId, empNO, SignTaskStatus.REJECT,comment,"",null);
		
		return true;
	}
	// ======================簽核取消========================================
	public boolean onCancel(BigDecimal taskId, String empNO, String comment) {
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		Date date = new Date(System.currentTimeMillis());
		if (!SignTaskUtils.vaildateApprove(taskId, empNO)) {
			return false;
		}
		SignTaskList signTaskList = signTaskBo.onAppr(taskId, empNO, SignTaskStatus.CANCEL,
				comment, date);
		signTaskBo.onCancel(taskId, signTaskList.getSignSeqId());
		//================================mail==========================================================
		signTaskBo.sendmail(taskId, empNO, SignTaskStatus.CANCEL,comment,"",null);
		
		return true;
	}

	// ======================待簽核清單========================================
	public List<BigDecimal> getWaitSignTask(String empno,String applyNum, SignStatus status,
			String sourceSystem, String sourceCategory,boolean deputy) {
		SignTaskBO signTaskBO = SpringContextUtil.getBean(SignTaskBO.class);
		return signTaskBO.getWaitSignTask(empno,applyNum, status, sourceSystem,
				sourceCategory,deputy);
	}
	// ======================待簽核清單========================================
	public List<BigDecimal> getWaitSignTask(String empno, SignStatus status,
			String sourceSystem, String sourceCategory,boolean deputy) {
		SignTaskBO signTaskBO = SpringContextUtil.getBean(SignTaskBO.class);
		return signTaskBO.getWaitSignTask(empno,"", status, sourceSystem,
				sourceCategory,deputy);
	}
	// ======================是否有下一個簽核者========================================
	public boolean getNextSignTask(int taskId) { 
		SignTaskAppBo signTaskBo = SpringContextUtil.getBean(SignTaskAppBo.class);
		return signTaskBo.getNextSignTask(taskId);
	}
}
