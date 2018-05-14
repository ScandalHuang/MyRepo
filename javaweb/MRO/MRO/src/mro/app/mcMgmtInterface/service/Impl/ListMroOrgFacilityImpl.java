package mro.app.mcMgmtInterface.service.Impl;

import java.util.List;

import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.bo.ListMroOrgFacilityBO;
import mro.app.mcMgmtInterface.form.MroOrgFacilityForm;
import mro.app.mcMgmtInterface.service.ListMroOrgFacilityInterface;
import mro.base.bo.MroOrgFacilityBO;

import com.inx.commons.util.SpringContextUtil;

public class ListMroOrgFacilityImpl implements ListMroOrgFacilityInterface,UploadInterfaces {

	@Override
	public MroOrgFacilityForm onSearch(MroOrgFacilityForm mroOrgFacilityForm) {
		MroOrgFacilityBO mroOrgFacilityBO = SpringContextUtil.getBean(MroOrgFacilityBO.class);
		mroOrgFacilityForm.intial();
		if(mroOrgFacilityForm.getsLocationSiteMap()!=null){
			mroOrgFacilityForm.setListMroOrgFacility(mroOrgFacilityBO.getList(
					mroOrgFacilityForm.getsLocationSiteMap()));
			mroOrgFacilityForm.copyList();
		}
		return mroOrgFacilityForm;
	}


	@Override
	public MroOrgFacilityForm onDelete(MroOrgFacilityForm mroOrgFacilityForm) {
		ListMroOrgFacilityBO listMroOrgFacilityBO = SpringContextUtil.getBean(ListMroOrgFacilityBO.class);
		listMroOrgFacilityBO.delete(mroOrgFacilityForm.getDeleteMroOrgFacility());
		return onSearch(mroOrgFacilityForm);
	}


	@Override
	public boolean validate(List objects) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public void excute(List objects) {
		ListMroOrgFacilityBO listMroOrgFacilityBO = SpringContextUtil.getBean(ListMroOrgFacilityBO.class);
		listMroOrgFacilityBO.updateShortName();
		
	}


	@Override
	public void destroyed(List objects) {
		// TODO Auto-generated method stub
		
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
	public void preFunction(List objects) {
		// TODO Auto-generated method stub
		
	}

}
