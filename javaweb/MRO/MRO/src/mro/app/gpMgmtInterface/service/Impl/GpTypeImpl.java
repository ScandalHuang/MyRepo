package mro.app.gpMgmtInterface.service.Impl;

import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.gpMgmtInterface.form.GpTypeForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.ClassstructureGpBO;
import mro.base.entity.ClassstructureGp;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

public class GpTypeImpl implements ListUpLoadInterface,UploadInterfaces {

	private ClassstructureGpBO classstructureGpBO;
	private GpTypeForm form;
	public GpTypeImpl(){
		
	}
	public GpTypeImpl(Object form){
		classstructureGpBO=SpringContextUtil.getBean(ClassstructureGpBO.class);
		this.form =(GpTypeForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		form.setList(classstructureGpBO.getList(null));
		form.copyList();
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		classstructureGpBO.delete(form.getDeleteList());
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((ClassstructureGp[]) 
				ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getList()));
	}
	@Override
	public void setParameter() {
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		// 是否隨產品出貨選單
		form.setGpDelivery(bean.getParameterOption().get(ParameterType.GP_DELIVERY_TYPE));
		 // 是否殘留在產品內部選單
		form.setGpRemain(bean.getParameterOption().get(ParameterType.GP_REMAIN_TYPE));
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
