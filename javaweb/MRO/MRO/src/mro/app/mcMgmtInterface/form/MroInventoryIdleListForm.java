package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroInventoryIdleList;

public class MroInventoryIdleListForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<MroInventoryIdleList> list;
	private List<MroInventoryIdleList> filterList;
	private MroInventoryIdleList[] deleteList;
	private LocationSiteMap sLocationSiteMap;
	private String selectPlantCode;
	
	public MroInventoryIdleListForm(){
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

	public List<MroInventoryIdleList> getList() {
		return list;
	}

	public void setList(List<MroInventoryIdleList> list) {
		this.list = list;
	}

	public List<MroInventoryIdleList> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<MroInventoryIdleList> filterList) {
		this.filterList = filterList;
	}

	public MroInventoryIdleList[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(MroInventoryIdleList[] deleteList) {
		this.deleteList = deleteList;
	}

	public String getSelectPlantCode() {
		return selectPlantCode;
	}

	public void setSelectPlantCode(String selectPlantCode) {
		this.selectPlantCode = selectPlantCode;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}

}
