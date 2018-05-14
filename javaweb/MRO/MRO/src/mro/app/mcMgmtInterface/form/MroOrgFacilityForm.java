package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacility;

public class MroOrgFacilityForm implements Serializable {
	private static final long serialVersionUID = 1454625593658139910L;
	private List<MroOrgFacility> listMroOrgFacility;
	private List<MroOrgFacility> filterListMroOrgFacility;
	private MroOrgFacility[] deleteMroOrgFacility;
	private LocationSiteMap sLocationSiteMap;
	
	public MroOrgFacilityForm(){
		intial();
	}
	
	public void intial(){
		listMroOrgFacility=new ArrayList<>();
		filterListMroOrgFacility=new ArrayList<>();
		deleteMroOrgFacility=null;
	}

	public void copyList(){
		filterListMroOrgFacility=listMroOrgFacility;
	}
	
	public List<MroOrgFacility> getListMroOrgFacility() {
		return listMroOrgFacility;
	}

	public void setListMroOrgFacility(List<MroOrgFacility> listMroOrgFacility) {
		this.listMroOrgFacility = listMroOrgFacility;
	}

	public MroOrgFacility[] getDeleteMroOrgFacility() {
		return deleteMroOrgFacility;
	}

	public void setDeleteMroOrgFacility(MroOrgFacility[] deleteMroOrgFacility) {
		this.deleteMroOrgFacility = deleteMroOrgFacility;
	}

	public List<MroOrgFacility> getFilterListMroOrgFacility() {
		return filterListMroOrgFacility;
	}

	public void setFilterListMroOrgFacility(
			List<MroOrgFacility> filterListMroOrgFacility) {
		this.filterListMroOrgFacility = filterListMroOrgFacility;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}
	
}
