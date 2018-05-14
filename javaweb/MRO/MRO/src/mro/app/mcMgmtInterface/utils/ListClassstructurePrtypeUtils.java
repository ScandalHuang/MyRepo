package mro.app.mcMgmtInterface.utils;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;

public class ListClassstructurePrtypeUtils {

	// ==========================================驗證=====================================================
	public static boolean validate(String selectPrtype,String selectClassstructureid
			,GlobalGrowl message) {
		StringBuffer warnMessage=new StringBuffer();
		
		if(StringUtils.isBlank(selectPrtype)){
			warnMessage.append("請選取Prtype!<br/>");
		}else if(StringUtils.isBlank(selectClassstructureid)){
			warnMessage.append("請選取類別結構!<br/>");
		}
		
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error",warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}
}
