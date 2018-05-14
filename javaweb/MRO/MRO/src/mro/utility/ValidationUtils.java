package mro.utility;

import java.math.BigDecimal;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.bo.MroApplyListBO;
import mro.base.entity.view.MroApplyList;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ValidationUtils {
	public static boolean validationCondition(Object... objects) {
		GlobalGrowl message = new GlobalGrowl();
		if(Utility.isValueNotEmpty(objects)){
			return true;
		}
		message.addWarnMessage("Warning", "搜尋條件不可空白 !<br />");
		return false;
	}

	
	public static void validateStatus(String applyNum,BigDecimal taskId,ItemStatusType itemStatusType){
		MroApplyListBO mroApplyListBO=SpringContextUtil.getBean(MroApplyListBO.class);
		MroApplyList mroApplyList=mroApplyListBO.getMroApplyList(applyNum,taskId,itemStatusType);
		if(mroApplyList!=null){
			ExceptionUtils.showFalilException(applyNum, taskId, 
					"申請單狀態("+mroApplyList.getStatus()+")錯誤!!");
		}
	}
}
