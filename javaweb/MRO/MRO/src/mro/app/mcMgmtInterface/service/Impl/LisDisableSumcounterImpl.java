package mro.app.mcMgmtInterface.service.Impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.form.DisableSumcounterForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PrlineDisableSumcounterBO;
import mro.base.entity.PrlineDisableSumcounter;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class LisDisableSumcounterImpl implements ListUpLoadInterface,UploadInterfaces {

	private PrlineDisableSumcounterBO prlineDisableSumcounterBO;
	private LocationMapBO locationMapBO;
	private DisableSumcounterForm form;
	public LisDisableSumcounterImpl(){
		
	}
	public LisDisableSumcounterImpl(Object form){
		prlineDisableSumcounterBO=SpringContextUtil.getBean(PrlineDisableSumcounterBO.class);
		locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		this.form =(DisableSumcounterForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		if(form.getsLocationSite()!=null){
			form.setList(prlineDisableSumcounterBO.getList(form.getsLocationSite()));
			form.copyList();
		}
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		prlineDisableSumcounterBO.delete(Arrays.asList(form.getDeleteList()));
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((PrlineDisableSumcounter[]) 
				ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getList()));
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
