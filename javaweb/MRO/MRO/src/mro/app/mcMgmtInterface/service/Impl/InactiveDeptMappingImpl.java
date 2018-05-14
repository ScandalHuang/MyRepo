package mro.app.mcMgmtInterface.service.Impl;

import java.util.Arrays;
import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.form.InactiveDeptMappingForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.bo.InactiveDeptMappingBO;
import mro.base.entity.InactiveDeptMapping;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class InactiveDeptMappingImpl implements ListUpLoadInterface,UploadInterfaces {

	private InactiveDeptMappingBO inactiveDeptMappingBO;
	private InactiveDeptMappingForm form;
	public InactiveDeptMappingImpl(){
		
	}
	public InactiveDeptMappingImpl(Object form){
		inactiveDeptMappingBO=SpringContextUtil.getBean(InactiveDeptMappingBO.class);
		this.form =(InactiveDeptMappingForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		form.setList(inactiveDeptMappingBO.getList());
		form.copyList();
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		inactiveDeptMappingBO.delete(Arrays.asList(form.getDeleteList()));
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((InactiveDeptMapping[]) ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getList()));
	}
	@Override
	public void setParameter() {
	}
	@Override
	public boolean assignFunctionFlag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void assignFunction(List objects) {
		// TODO Auto-generated method stub
		
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
