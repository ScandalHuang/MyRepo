package mro.app.applyItem.service.impl;

import java.util.Map;

import mro.app.signTask.service.ValidateInterface;

public class ApplyItemVendorRemarkValidateImpl implements ValidateInterface {

	@Override
	public boolean onSignVaildate(Map map) {
		
		boolean vendorRemarkFlag=(boolean)map.get("VENDOR_REMARK_FLAG");
		return vendorRemarkFlag;
	}

}
