package mro.app.mcMgmtInterface.service.Impl;

import java.util.Arrays;
import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.form.PrtypeBudgetForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.bo.PrtypeBudgetBO;
import mro.base.bo.PrtypeBudgetReasoncodeBO;
import mro.base.entity.PrtypeBudget;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ListPrtypeBudgetImpl implements ListUpLoadInterface,UploadInterfaces {
	
	private PrtypeBudgetBO pdBO;
	private PrtypeBudgetReasoncodeBO pdrBO;
	private PrtypeBudgetForm form;
	public ListPrtypeBudgetImpl(){
		
	}
	public ListPrtypeBudgetImpl(Object form){
		pdBO=SpringContextUtil.getBean(PrtypeBudgetBO.class);
		pdrBO=SpringContextUtil.getBean(PrtypeBudgetReasoncodeBO.class);
		this.form =(PrtypeBudgetForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		if (StringUtils.isNotBlank(form.getSelectPrtype()) && 
			StringUtils.isNotBlank(form.getSelectBudgetType())&&
			form.getsLocationSiteMap()!=null) {
			form.setListPrtypeBudget(pdBO.getPrtypeBudgetList(
					form.getsLocationSiteMap(),
					form.getSelectPrtype(),
					form.getSelectBudgetType(),form.getYear()+"/"+form.getMonth()));
		}
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		pdBO.delete(Arrays.asList(form.getDeleteList()));
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((PrtypeBudget[]) 
				ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getListPrtypeBudget()));
	}
	@Override
	public void setParameter() {
		form.setTypeOption(pdrBO.getOption(form.getSelectPrtype()));
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
