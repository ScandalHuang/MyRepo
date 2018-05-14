package mro.app.signProcess.Utils;

import java.math.BigDecimal;
import java.util.List;

import mro.app.signProcess.form.SignProcessForm;
import mro.base.entity.SignProcess;
import mro.base.entity.SignProcessList;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;

public class SignProcessUtils {
	public static boolean validate(SignProcessForm signProcessForm, GlobalGrowl message) {
		boolean vaildate = true;
		SignProcess signProcess=signProcessForm.getSignProcess();
		List<SignProcessList> signProcessList=signProcessForm.getSignProcessList();
		StringBuffer warnMessage=new StringBuffer();
		if (StringUtils.isBlank(signProcess.getProcessName())) // 必須填寫簽核流程名稱
		{
			warnMessage.append("必須填寫簽核流程名稱!<br />");
		}
		if (StringUtils.isBlank(signProcess.getSourceSystem())) // 必須填寫簽核流程名稱
		{
			warnMessage.append("必須選取系統來源!<br />");
		}
		//==============================簽核清單檢查===========================================
		if(signProcess.getProcessId()!=null){
			BigDecimal preSignLevel = null;
			for(SignProcessList s:signProcessList){
				if (s.getSignSequence()==null){ // 簽核順序
					if(warnMessage.indexOf("簽核順序 不得為空值")==-1){
						warnMessage.append("簽核順序 不得為空值 !<br />");
					}
				}else{				
					if (StringUtils.isBlank(s.getSignCategory()) && s.getSignSequence().intValue()!=1){ // 簽核類別
						warnMessage.append("簽核順序 ："+s.getSignSequence()+" 必須選取簽核類別 !<br />");
					}
					
					if(StringUtils.isNotBlank(s.getSignCategory())){
						if ( s.getSignCategory().equals("APPOINT") && 
								StringUtils.isBlank(s.getSignEmpNo())){ // 簽核人員
							warnMessage.append("簽核順序 ："+s.getSignSequence()+" 必須填寫簽核人員 !<br />");
						}
						if ( s.getSignCategory().equals("CONTINUE") && 
								s.getSignLevel()==null){ // 簽核層級
							warnMessage.append("簽核順序 ："+s.getSignSequence()+" 必須選取簽核層級  !<br />");
						}
						if ( s.getSignCategory().equals("CONTINUE") && 
								(preSignLevel!=null &&
								s.getSignLevel().intValue()>=preSignLevel.intValue())){ // 簽核層級
							warnMessage.append("簽核順序 ："+s.getSignSequence()+" 延續上層的簽核層級有誤!<br />");
						}
						if ( s.getSignCategory().equals("VALIDATE") && 
								s.getSignValidateTrue()!=null &&
								s.getSignValidateTrue().compareTo(s.getSignSequence())!=1){ // 簽核層級
							warnMessage.append("簽核順序 ："+s.getSignSequence()+" 驗證TRUE必須大於簽核順序  !<br />");
						}
						if ( s.getSignCategory().equals("VALIDATE") && 
								s.getSignValidateFalse()!=null &&
								s.getSignValidateFalse().compareTo(s.getSignSequence())!=1){ // 簽核層級
							warnMessage.append("簽核順序 ："+s.getSignSequence()+" 驗證FALSE必須大於簽核順序  !<br />");
						}
					}
				}
				
				preSignLevel=s.getSignLevel();
			}
		}
		if(warnMessage.length()>0){
			message.addWarnMessage("Warn", warnMessage.toString());
			vaildate=false;
		}
		return vaildate;
	}

}
