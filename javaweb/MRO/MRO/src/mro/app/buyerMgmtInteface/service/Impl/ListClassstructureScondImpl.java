package mro.app.buyerMgmtInteface.service.Impl;

import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.buyerMgmtInteface.form.ClassstructureSecondSourceForm;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.bo.ClassstructureBO;
import mro.base.bo.ClassstructureSecondSourceBO;
import mro.base.entity.ClassstructureSecondSource;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ListClassstructureScondImpl implements ListUpLoadInterface,UploadInterfaces {
	
	private ClassstructureSecondSourceBO csBO;
	private ClassstructureBO classstructureBO;
	private ClassstructureSecondSourceForm form;
	public ListClassstructureScondImpl(){
		
	}
	public ListClassstructureScondImpl(Object form){
		csBO=SpringContextUtil.getBean(ClassstructureSecondSourceBO.class);
		classstructureBO=SpringContextUtil.getBean(ClassstructureBO.class);
		this.form =(ClassstructureSecondSourceForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		form.setList(csBO.getList(form.getsClassstructureid()));
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		csBO.delete(form.getDeleteList());
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((ClassstructureSecondSource[]) 
				ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getList()));
	}
	@Override
	public void setParameter() {
		form.setOption(Utility.swapMap(classstructureBO.getClassstructureMap(null,null)));
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
