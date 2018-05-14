package mro.base.workflow.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import mro.app.signTask.jsf.SignTaskBean;
import mro.base.System.config.SystemConfig;
import mro.base.entity.AItem;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ItemTransferHeaderApply;
import mro.form.PrForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;

public class WorkflowActionUtils {

	// 料號清單自動化申請送審
	public static BigDecimal onClassstructureApplySumit(
			ClassstructureHeaderApply classstructureHeaderApply,String param) {
		SignTaskBean signTaskBean = new SignTaskBean();
		int taskId = classstructureHeaderApply.getTaskId()== null ? 0 : classstructureHeaderApply.getTaskId().intValue();
		
		BigDecimal b = signTaskBean.onSubmit(
				taskId,classstructureHeaderApply.getApplyHeaderNum(), 
				classstructureHeaderApply.getCreateBy(), param);
		return b;
	}
	
	// 料號新BySite拋轉申請送審
	public static BigDecimal onItemTransferApplySumit(ItemTransferHeaderApply itemTransferHeaderApply,String param) {
		
		SignTaskBean signTaskBean = new SignTaskBean();
		int taskId = itemTransferHeaderApply.getTaskId()== null ? 0 : itemTransferHeaderApply.getTaskId().intValue();
		
		BigDecimal b = signTaskBean.onSubmit(
				taskId,itemTransferHeaderApply.getApplyHeaderNum(), 
				itemTransferHeaderApply.getCreateBy(), param);
		return b;
	}
	

	public static BigDecimal onItemApplySumit(AItem aItem, String ItemNum,
			String empNo,String param) { // 料號新增申請送審
		SignTaskBean signTaskBean = new SignTaskBean();
		int taskId = aItem.getTaskId()== null ? 0 : aItem.getTaskId().intValue();
		
		BigDecimal b = signTaskBean.onSubmit(
				taskId,ItemNum, empNo, param);
		return b;
	}

	public static BigDecimal onItemChengeSumit(AItem aItem, String ItemNum,
			String empNo,String param) { // 料號異動申請送審

		SignTaskBean signTaskBean = new SignTaskBean();
		int taskId = aItem.getTaskId()== null ? 0 : aItem.getTaskId().intValue();
		
		BigDecimal b = signTaskBean.onSubmit(
				taskId,ItemNum, empNo, param);
		return b;
	}


	
	public static BigDecimal onPrSubmit(PrForm prForm,String param)  { // 需求申請單
		SignTaskBean signTaskBean = new SignTaskBean();
		int taskId = prForm.getPr().getTaskId()== null ? 0 : prForm.getPr().getTaskId().intValue();
		
		BigDecimal b = signTaskBean.onSubmit(
				taskId,prForm.getPr().getPrnum(), prForm.getPr().getRequestedby(), param);
		return b;
	}
	// ======================================簽核歷程================================================
	public static String onSignHistory(BigDecimal taskID) { 
		StringBuffer str=new StringBuffer();
		str.append(SystemConfig.signHistoryUrl+"?");
		if(taskID!=null) {str.append("ID="+taskID);}
		return str.toString();
	}
	// ======================================簽核預覽================================================
	public static String onSignPreView(String param) { 
		StringBuffer str=new StringBuffer();
		str.append(SystemConfig.signPreViewUrl+"?");
		if(StringUtils.isNotBlank(param)){
			try {
				str.append("param="+java.net.URLEncoder.encode(param, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return str.toString();
	}
	// ======================================轉呈================================================
	public static boolean onTransfer(BigDecimal taskID, String empNo,
			String signComment, String transferEmpno, GlobalGrowl message) {
		SignTaskBean signTaskBean = new SignTaskBean();
		boolean b = signTaskBean.onTransfer(taskID, empNo, signComment,
				transferEmpno);
		if (b == false) {
			message.addWarnMessage("轉呈失敗", "請重新轉呈或與MIS聯繫!");
		}
		return b;
	}

	public static boolean onTransfer(BigDecimal taskID, String empNo,
			String signComment, String transferEmpno) {
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.onTransfer(taskID, empNo, signComment,
				transferEmpno);
	}
	// ======================================管理員轉呈================================================
	public static boolean onTransferByAdmin(BigDecimal taskID,String num, String empNo,
			String signComment, String transferEmpno, GlobalGrowl message) {
		SignTaskBean signTaskBean = new SignTaskBean();
		boolean b = signTaskBean.onTransferByAdmin(taskID, empNo, signComment,
				transferEmpno);
		if (b == false) {
			message.addWarnMessage("轉呈失敗", "申請單號："+num+" 轉呈失敗請，重新轉呈或與MIS聯繫!");
		}
		return b;
	}

	// ======================================退回================================================
	public static boolean onReject(BigDecimal taskID, String empNo,
			String signComment) {
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.onReject(taskID, empNo, signComment);
	}
	public static boolean onReject(BigDecimal taskID, String empNo,
			String signComment, GlobalGrowl message) {
		SignTaskBean signTaskBean = new SignTaskBean();
		boolean b = signTaskBean.onReject(taskID, empNo, signComment);
		if (b == false) {
			message.addWarnMessage("退回申請單失敗", "請重新退回申請單或與MIS聯繫!");
		}
		return b;
	}
	// ======================================取消================================================
	public static boolean onCancel(BigDecimal taskID, String empNo,
			String signComment, GlobalGrowl message) {
		SignTaskBean signTaskBean = new SignTaskBean();
		boolean b = signTaskBean.onCancel(taskID, empNo, signComment);
		if (b == false) {
			message.addWarnMessage("取消申請單失敗", "請重新取消申請單或與MIS聯繫!");
		}
		return b;
	}
	public static boolean onCancel(BigDecimal taskID, String empNo,
			String signComment) {
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.onCancel(taskID, empNo, signComment);
	}
	// ======================================同意================================================
	public static String onAppr(BigDecimal taskID, String empNo, String signComment,
			GlobalGrowl message,String mailContent,List<String> notifyList) {
		SignTaskBean signTaskBean = new SignTaskBean();
		String s = signTaskBean.onAppr(taskID, empNo, signComment,mailContent,notifyList);
		if (s == null) {
			message.addWarnMessage("簽核申請單失敗", "請重新簽核申請單或與MIS聯繫!");
		}
		return s;
	}
	public static String onAppr(BigDecimal taskID, String empNo, String signComment,
			String mailContent,List<String> notifyList) {
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.onAppr(taskID, empNo, signComment,mailContent,notifyList);
	}
	// ======================是否有下一個簽核者========================================
	public static boolean getNextSignTask(int taskId) { 
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.getNextSignTask(taskId);
	}

	// ======================================會簽================================================
	public static boolean onCounterSign(BigDecimal taskId,String transferEmpNo) {
		SignTaskBean signTaskBean = new SignTaskBean();
		return signTaskBean.onCounterSign(taskId,transferEmpNo);
	}
}
