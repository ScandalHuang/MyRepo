package mro.app.mcMgmtInterface.utils;

import mro.app.mcMgmtInterface.form.LampControlForm;
import mro.base.entity.Item;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;

public class ListLampControlUtils {
	
	// =========================================Header驗證====================================
	public static boolean addHeaderValidate(LampControlForm form,Item item) {
		GlobalGrowl message=new GlobalGrowl();
		StringBuffer warnMessage=new StringBuffer();
		if (item==null) {
			warnMessage.append("[料號]必須填寫!<br />");
		}
		if (StringUtils.isNotBlank(form.getOrganizationCode())) {
			warnMessage.append("[廠區]必須選取!<br />");
		}
		if (StringUtils.isNotBlank(form.getDeptCode())) {
			warnMessage.append("[部門]必須選取!<br />");
		}
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error!", warnMessage.toString());
			return false;
		} else {
			message.addInfoMessage("Add", "Add successful.");
			return true;
		}
	}
	
}
