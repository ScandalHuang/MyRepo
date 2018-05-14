package mro.app.reportView.utils;

import java.util.Date;

import mro.app.reportView.form.ExclusiveReturnReportForm;
import mro.utility.DateUtils;

import com.inx.commons.jsf.GlobalGrowl;

public class ExclusiveReturnReportUtils {
	

	public static boolean validateSearch(ExclusiveReturnReportForm form) {
		GlobalGrowl message=new GlobalGrowl();
		StringBuffer warnMessage=new StringBuffer();
		warnMessage=ExclusiveReturnReportUtils.validateDate(warnMessage, 
				form.getStDate(), form.getIssueEdDate(), form.getReturnEdDate());
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error!", warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validateMDSearch(ExclusiveReturnReportForm form) {
		GlobalGrowl message=new GlobalGrowl();
		StringBuffer warnMessage=new StringBuffer();
		warnMessage=ExclusiveReturnReportUtils.validateDate(warnMessage, 
				form.getStDate2(), form.getIssueEdDate2(), form.getReturnEdDate2());
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error!", warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}
	
	public static StringBuffer validateDate(StringBuffer warnMessage,Date stDate,
			Date issueEdDate,Date returnEdDate){
		if (stDate==null) {
			warnMessage.append("[起始日]必須填寫!<br />");
		}
		if (issueEdDate==null) {
			warnMessage.append("[領料截止日]必須填寫!<br />");
		}
		if (returnEdDate==null) {
			warnMessage.append("[退料截止日]必須填寫!<br />");
		}
		if(warnMessage.length()==0){  //日期都有填寫
			if(stDate.compareTo(issueEdDate)==1 ||
				stDate.compareTo(returnEdDate)==1){
				warnMessage.append("[起始日]必須小於[領料截止日]與[退料截止日]<br />");
			}
			if(DateUtils.getAddDate(stDate,700).compareTo(issueEdDate)==-1 ||
			   DateUtils.getAddDate(stDate,700).compareTo(returnEdDate)==-1){
				warnMessage.append("搜索區間不得超過700天<br />");
			}
		}
		return warnMessage;
	}
	
}
