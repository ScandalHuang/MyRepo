package mro.app.applyItem.service.impl;

import java.util.Map;

import mro.app.signTask.service.ValidateInterface;
import mro.base.System.config.SystemConfig;

public class ApplyR2PmreqReasonCodeValidateImpl implements ValidateInterface {
	 /*
	  * 申請目的為風險備庫跟例外管理最少簽到總廠長
	  */
	@Override
	public boolean onSignVaildate(Map map) {

		String reasoncode=map.get("reasoncode")!=null?map.get("reasoncode").toString():"";
		if (SystemConfig.reasonCode.get(reasoncode) !=null) {
			return true;
		}
		return false;
	}

}
