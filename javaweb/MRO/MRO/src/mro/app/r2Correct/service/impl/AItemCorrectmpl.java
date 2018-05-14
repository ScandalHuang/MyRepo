package mro.app.r2Correct.service.impl;

import java.util.List;

import mro.app.commonview.services.UploadInterfaces;
import mro.app.r2Correct.bo.AItemCorrectBo;

import com.inx.commons.util.SpringContextUtil;

public class AItemCorrectmpl implements UploadInterfaces {

	@Override
	public boolean assignFunctionFlag() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void assignFunction(List objects) {
		AItemCorrectBo aItemCorrectBo=SpringContextUtil.getBean(AItemCorrectBo.class);
		aItemCorrectBo.update(objects);
	}

	@Override
	public boolean validate(List objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excute(List objects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyed(List objects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preFunction(List objects) {
		// TODO Auto-generated method stub
		
	}


}
