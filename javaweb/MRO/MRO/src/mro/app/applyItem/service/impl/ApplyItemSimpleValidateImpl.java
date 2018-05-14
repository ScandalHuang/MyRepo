package mro.app.applyItem.service.impl;

import java.util.Map;

import mro.app.signTask.service.ValidateInterface;

public class ApplyItemSimpleValidateImpl implements ValidateInterface {

	@Override
	public boolean onSignVaildate(Map map) {
		boolean simpleSignFlag=(boolean) map.get("SIMPLE_SIGN_FLAG");
		return simpleSignFlag;
	}

}
