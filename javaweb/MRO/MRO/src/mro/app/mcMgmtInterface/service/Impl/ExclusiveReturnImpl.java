package mro.app.mcMgmtInterface.service.Impl;

import java.util.Arrays;
import java.util.List;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.form.ExclusiveReturnForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.bo.MroExclusiveReturnBO;
import mro.base.entity.MroExclusiveReturn;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ExclusiveReturnImpl implements ListUpLoadInterface,UploadInterfaces {

	private MroExclusiveReturnBO mroExclusiveReturnBO;
	private ExclusiveReturnForm form;
	public ExclusiveReturnImpl(){
		
	}
	public ExclusiveReturnImpl(Object form){
		mroExclusiveReturnBO=SpringContextUtil.getBean(MroExclusiveReturnBO.class);
		this.form =(ExclusiveReturnForm) form;
	}
	@Override
	public void onSearch() {
		form.intial();
		if(form.getsLocationSiteMap()!=null){
			if(form.getType().equals("ITEM")){
				if(StringUtils.isNotBlank(form.getSelectPlantCode())){
					if(form.getSelectPlantCode().equals("ALL")){
						form.setList(mroExclusiveReturnBO.getListByPlantCode(
								form.getsLocationSiteMap(),null));
					}else{
						form.setList(mroExclusiveReturnBO.getListByPlantCode(
								form.getsLocationSiteMap(),form.getSelectPlantCode()));
					}
				}
			}else if(form.getType().equals("CLASSSTRUCTUREID")){
				form.setList(mroExclusiveReturnBO.getListByclassstructureid(
						form.getsLocationSiteMap(),null));
			}
		}
		form.copyList();
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		mroExclusiveReturnBO.delete(Arrays.asList(form.getDeleteList()));
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((MroExclusiveReturn[]) 
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
