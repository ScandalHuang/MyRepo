package mro.app.mcMgmtInterface.service.Impl;

import java.util.List;

import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.bo.ListStoreCategoryChangeBO;

import com.inx.commons.util.SpringContextUtil;

public class ListStoreCategoryChangeImpl implements UploadInterfaces{

	@Override
	public boolean validate(List objects) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void excute(List objects) {
		
	}

	@Override
	public void destroyed(List objects) {
		
	}

	@Override
	public boolean assignFunctionFlag() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void assignFunction(List objects) {
		ListStoreCategoryChangeBO listStoreCategoryChangeBO = SpringContextUtil
				.getBean(ListStoreCategoryChangeBO.class);
		listStoreCategoryChangeBO.update(objects);
		
	}

	@Override
	public void preFunction(List objects) {
		// TODO Auto-generated method stub
		
	}
}
