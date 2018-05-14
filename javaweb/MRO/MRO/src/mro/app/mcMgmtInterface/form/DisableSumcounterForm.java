package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrlineDisableSumcounter;

public class DisableSumcounterForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<PrlineDisableSumcounter> list;
	private List<PrlineDisableSumcounter> filterList;
	private PrlineDisableSumcounter[] deleteList;
	
	private LocationSiteMap sLocationSite;
	
	public DisableSumcounterForm(){
		intial();
	}
	
	public void intial(){
		list=new ArrayList<>();
		filterList=new ArrayList<>();
		deleteList=null;
	}

	public void copyList(){
		filterList=list;
	}

	public List<PrlineDisableSumcounter> getList() {
		return list;
	}

	public void setList(List<PrlineDisableSumcounter> list) {
		this.list = list;
	}

	public List<PrlineDisableSumcounter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<PrlineDisableSumcounter> filterList) {
		this.filterList = filterList;
	}

	public PrlineDisableSumcounter[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(PrlineDisableSumcounter[] deleteList) {
		this.deleteList = deleteList;
	}

	public LocationSiteMap getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(LocationSiteMap sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

}
