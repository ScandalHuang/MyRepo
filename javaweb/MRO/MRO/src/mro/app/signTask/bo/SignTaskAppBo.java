package mro.app.signTask.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mro.app.signTask.dao.SignTaskDao;
import mro.app.util.MessageSender;
import mro.app.util.vo.MailVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SignTaskStatus;
import mro.base.bo.HrEmpBO;
import mro.base.bo.MroSignTaskListVBO;
import mro.base.bo.SignProcessBO;
import mro.base.bo.SignSourceCategoryBO;
import mro.base.bo.SignTaskBO;
import mro.base.bo.SignTaskListBO;
import mro.base.entity.HrEmp;
import mro.base.entity.SignProcess;
import mro.base.entity.SignTask;
import mro.base.entity.SignTaskList;
import mro.base.entity.SignTaskListDeputy;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;


@Component
@Scope("prototype")
public class SignTaskAppBo {

	private SimpleDateFormat dateFormatissue = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	SignTaskDao signTaskDao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		signTaskDao = new SignTaskDao(sessionFactory);
	}
		
	@Transactional(readOnly = true)
	public SignTaskList setSignTaskList(SignTask signTask, String empno,
			int seq, Date date,String action, String signDescription) {
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		HrEmp hrEmp = hrEmpBO.getHrEmp(empno);
		if(hrEmp==null){return null;}
		SignTaskList signTaskList = new SignTaskList();
		signTaskList.setSignTask(signTask);
		signTaskList.setSignSeqId(new BigDecimal(seq));
		signTaskList.setSignDescription(signDescription);
		signTaskList.setActorId(empno);
		signTaskList.setActorDept(hrEmp.getDeptNo());
		signTaskList.setSignDate(date);
		signTaskList.setFromDate(date);
		signTaskList.setAction(action);
		return signTaskList;
	}
	@Transactional(readOnly = false)
	public SignTask onSubmit(int processId,int taskId,String applyNum,String empno,String createBy,
			List<MroSignTaskListV> listSignHistoryVO) {
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		Date date = new Date(System.currentTimeMillis());
		int seqId=0;   //目前簽核
		int preSeqId = 0; // 上一次簽核流程的最大順序
		// ============================SignTask========================================================
		SignProcessBO signProcessBO=SpringContextUtil.getBean(SignProcessBO.class);
		SignProcess signProcess = signProcessBO.getSignProcess(new BigDecimal(processId),false);
		SignTask signTask = new SignTask();
		if (taskId != 0) {// 已存在簽核流程
			signTaskDao.deleteSeq(new BigDecimal(taskId), new BigDecimal("0")); // 刪除未簽核人員
			signTask = signTaskBo.getSignTask(new BigDecimal(taskId));
			preSeqId = signTaskListBO.getMaxeSeqId(new BigDecimal(taskId)).intValue();
		}
		signTask.setApplyNum(applyNum);
		signTask.setCreateBy(createBy);
		signTask.setCreateDate(date);
		signTask.setSourceCategory(signProcess.getSourceCategory());
		signTask.setSourceSystem(signProcess.getSourceSystem());
		signTask.setProcessId(signProcess.getProcessId());
		signTaskDao.insertUpdate(signTask);
		// =============================SignProcessList================================================
		for(MroSignTaskListV s:listSignHistoryVO){
			SignTaskList signTaskList = new SignTaskList();
			signTaskList.setSignTask(signTask);
			signTaskList.setSignSeqId(new BigDecimal(seqId + preSeqId));
			signTaskList.setActorId(s.getActorId());
			signTaskList.setActorDept(s.getActorDept());
			signTaskList.setSignDescription(s.getSignDescription());
			signTaskList.setSignerComment(s.getSignerComment());
			if(seqId==0){
				signTaskList.setSignDate(date);
				signTaskList.setFromDate(date);
				signTaskList.setAction(SignTaskStatus.SUBMIT.toString());
			}
			if (seqId == 1) {
				signTaskList.setFromDate(date);
			}
			
			signTaskDao.insertUpdate(signTaskList);
			
			if (seqId == 1) {
				signTask.setActorId(signTaskList.getActorId());
				signTask.setStatus(SignStatus.INPRG.toString());
				signTask.setSignTaskList(signTaskList);
				signTaskDao.insertUpdate(signTask);
				
			}
			
			seqId++;
		}
		return signTask;
	}

	@Transactional(readOnly = false)
	public SignTaskList onAppr(BigDecimal taskId, String empNO, SignTaskStatus action,
			String comment, Date date) {
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		MroSignTaskListVBO mroSignTaskListVBO=SpringContextUtil.getBean(MroSignTaskListVBO.class);
		SignTaskList signTaskList = signTaskListBO.getNowSignTaskList(taskId);
		if(StringUtils.isBlank(empNO)){ //自動轉呈(下一個簽核者往後調整)
			comment="簽核人員離職，系統自動代轉直屬主管\r\n"+comment;
		}else if(!signTaskList.getActorId().equals(empNO)){  //非本人
			//==========================記錄代理資訊======================================
			SignTaskListDeputy signTaskListDeputy=new  SignTaskListDeputy();
			signTaskListDeputy.setActorId(signTaskList.getActorId());
			signTaskListDeputy.setDeputyActorId(empNO);
			signTaskListDeputy.setTaskListId(signTaskList.getTaskListId());
			signTaskListDeputy.setSignDate(date);
			signTaskDao.insertUpdate(signTaskListDeputy);
			//==========================修改簽核人員========================================
			HrEmp hrEmp = hrEmpBO.getHrEmp(empNO);
//			signTaskList.setActorId(empNO);
//			signTaskList.setActorDept(hrEmp.getDeptNo());
			if(mroSignTaskListVBO.getNowListCount(taskId, empNO)==1){ //代理人
				comment="代理人 "+hrEmp.getName()+"("+hrEmp.getEmpNo()+") 簽核\r\n"+comment;
			}else {						//系統管理員
				comment="系統管理員 "+hrEmp.getName()+"("+hrEmp.getEmpNo()+") 代轉\r\n"+comment;
			}
		}
		signTaskList.setSignDate(date);
		signTaskList.setAction(action.toString());
		signTaskList.setSignerComment(comment);
		signTaskDao.insertUpdate(signTaskList);

		return signTaskList;
	}
	@Transactional(readOnly = false)
	public boolean getNextSignTask(int taskId) { // 是否有下一個簽核者
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		SignTaskList signTaskList = signTaskListBO.getNowSignTaskList(new BigDecimal(taskId));
		SignTaskList nextSignTaskList = signTaskListBO.getNextSignTaskList(new BigDecimal(taskId),
				signTaskList.getSignSeqId().add(new BigDecimal("1")));
		if (nextSignTaskList == null) {
			return false;
		} 
		return true;
	}

	@Transactional(readOnly = false)
	public String getNextSignTaskList(BigDecimal taskId, BigDecimal seq, Date date) { // 下一個簽核者
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		SignTaskList signTaskList = signTaskListBO.getNextSignTaskList(taskId,
				seq.add(new BigDecimal("1")));

		SignTask signTask = signTaskBo.getSignTask(taskId);
		if (signTaskList == null) {
			signTask.setStatus(SignStatus.APPR.toString());
			signTask.setActorId(null);
		} else {
			signTaskList.setFromDate(date);
			signTaskDao.insertUpdate(signTaskList);
			signTask.setSignTaskList(signTaskList);
			signTask.setActorId(signTaskList.getActorId());
		}
		signTaskDao.insertUpdate(signTask);

		return signTask.getStatus();
	}
	@Transactional(readOnly = false)
	public void onTran(BigDecimal taskId, BigDecimal seq, String transferEmpNo, Date date,
			String signDescription) { // 轉呈 or 會簽
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		signTaskDao.updateSeq(taskId, seq); // 更新SEQ

		SignTask signTask = signTaskBo.getSignTask(taskId);

		HrEmp hrEmp = hrEmpBO.getHrEmp(transferEmpNo);
		SignTaskList signTaskList = new SignTaskList();
		signTaskList.setSignTask(signTask);
		signTaskList.setSignSeqId(seq.add(new BigDecimal("1")));
		signTaskList.setSignDescription(signDescription);
		signTaskList.setActorId(hrEmp.getEmpNo());
		signTaskList.setActorDept(hrEmp.getDeptNo());
		signTaskList.setFromDate(date);
		signTaskDao.insertUpdate(signTaskList);

		signTask.setSignTaskList(signTaskList);
		signTask.setActorId(signTaskList.getActorId());
		signTask.setStatus(SignStatus.INPRG.toString());
		signTaskDao.insertUpdate(signTask);
	}

	@Transactional(readOnly = false)
	public void onReject(BigDecimal taskId, BigDecimal seq) { // 拒絕
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		signTaskDao.deleteSeq(taskId, seq); // 刪除未簽核人員

		SignTask signTask = signTaskBo.getSignTask(taskId);
		signTask.setStatus(SignStatus.REJECT.toString());
		signTask.setActorId(signTask.getCreateBy());
		signTaskDao.insertUpdate(signTask);
	}
	@Transactional(readOnly = false)
	public void onCancel(BigDecimal taskId, BigDecimal seq) { // 取消
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		signTaskDao.deleteSeq(taskId, seq); // 刪除未簽核人員

		SignTask signTask = signTaskBo.getSignTask(taskId);
		signTask.setStatus(SignTaskStatus.CANCEL.toString());
		signTask.setActorId(null);
		signTaskDao.insertUpdate(signTask);
	}
	@Transactional(readOnly = true)
	public void sendmail(BigDecimal taskId,String empno,SignTaskStatus mailtype,String signComment,
			String mailContent,List<String> otherNotifyList){
		SignTaskBO signTaskBo=SpringContextUtil.getBean(SignTaskBO.class);
		SignTaskListBO signTaskListBO=SpringContextUtil.getBean(SignTaskListBO.class);
		SignSourceCategoryBO signSourceCategoryBO=SpringContextUtil.getBean(SignSourceCategoryBO.class);
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		MessageSender messageSender=new MessageSender();
		
		SignTask signTask = signTaskBo.getSignTask(taskId);
		SignTaskList signTaskList = signTaskListBO.getNowSignTaskList(signTask.getTaskId());//新的簽核者
		List<String> notifyList=new ArrayList<String>();
		MailVO mailVO = new MailVO();
		
		HrEmp hrEmpFrom = hrEmpBO.getHrEmp(empno);
		HrEmp hrEmpApply = hrEmpBO.getHrEmp(signTask.getCreateBy());
		HrEmp hrEmpSign=null;
		if(mailtype.compareTo(SignTaskStatus.INPRG)==0 ||mailtype.compareTo(SignTaskStatus.TRANSFER)==0){ 
			hrEmpSign = hrEmpBO.getActiveHrEmp(signTaskList.getActorId());
			if(hrEmpSign==null){
				hrEmpSign = hrEmpApply;
				mailVO.setSubject("(簽核人員已離職，請找該主管 "+
						Utility.nvlEntity(hrEmpBO.getMamgerHrEmp(signTaskList.getActorId()),
								HrEmp.class).getName()+" 確認)");
			}
			mailVO.setSendDate(dateFormatissue.format(signTaskList.getFromDate()));
		}else{
			hrEmpSign = hrEmpApply;
			mailVO.setSendDate(dateFormatissue.format(signTaskList.getSignDate()));
		}
		
		//=============================結案簽核者通知================================================
		/*
		 * 201511024 朱媽要求執行的需求(TN20151100112 )
		 * 文件核准時,若簽核說明欄位有資訊,系統自動發mail通知申請人
		 */
		if(mailtype.compareTo(SignTaskStatus.INPRG)==0 && StringUtils.isNotBlank(signComment)){
			notifyList.add(hrEmpApply.getEMail());
		}
		
		//=============================結案簽核者通知==================================================
		/*
		 * hongjie.wu	
		 * 2013.09.25
		 */
		if(mailtype.compareTo(SignTaskStatus.APPR)==0){
			SignProcessBO signProcessBO=SpringContextUtil.getBean(SignProcessBO.class);
			SignProcess signProcess= signProcessBO.getSignProcess(signTask.getProcessId(),false);
			if(StringUtils.isNotEmpty(signProcess.getNotifyFlag()) &&
					signProcess.getNotifyFlag().equals("Y") ){
				notifyList.addAll(hrEmpBO.getNotify(taskId));
			}
		}
		
		if(hrEmpSign!=null){
			mailVO.setSignmail(hrEmpSign.getEMail());
			mailVO.setSignempname(hrEmpSign.getName());
			mailVO.setSignempno(hrEmpSign.getEmpNo());
			mailVO.setSendempname(hrEmpFrom.getName());
			mailVO.setSendempno(empno);
			mailVO.setApplyempno(hrEmpApply.getEmpNo());
			mailVO.setApplyempname(hrEmpApply.getName());
			mailVO.setComment(StringUtils.defaultString(signComment));
			mailVO.setIssueid(signTask.getApplyNum());
			mailVO.setMailtype(mailtype);
			mailVO.setComment(mailVO.getComment());
			mailVO.setApplyDescription(signSourceCategoryBO.getSignSourceCategory(taskId).getDescription());
			//=================================測試用=======================================
//			hrEmpSign = signTaskDao.getHrEmp(signTask.getCreateBy());
//			mailVO.setSignmail(hrEmpSign.getEMail());
//			otherNotifyList=null;
//			notifyList=null;
			//========================================================================
			if(Utility.validateHostName(SystemConfig.PRODUCTION_MAP)){
				messageSender.runDeliver(mailVO,mailContent,notifyList,otherNotifyList); 
			}
		}
	}
	
	//===============================額外會簽========================================================
	@Transactional(readOnly = false)
    public void addExtraSigner(SignTask signTask,int seq,int preSeqId,String[] extraEmp,
    		String[] extraDescription){
		int total=1; //調整筆數
		// ======================會簽(目前簽核者往後調整)========================================
		if(extraEmp!=null && extraEmp.length>0){
			for(int i=0;i<extraEmp.length;i++){
				//==========================會簽節點==============================================
				String description="會簽";
				if(extraDescription!=null && extraDescription.length>i && 
						StringUtils.isNotBlank(extraDescription[i])){
					description=extraDescription[i];
				}
				
				SignTaskList signTaskList =this.setSignTaskList(
						signTask, extraEmp[i].trim(),seq+preSeqId+total, null, "", description);
				if(signTaskList!=null){
					signTaskDao.insertUpdate(signTaskList);
					total++;
				}
			}
		}
    }
}
