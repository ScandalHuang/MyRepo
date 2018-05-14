package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroExclusiveReturn;

public class ExclusiveReturnForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<MroExclusiveReturn> list;
	private List<MroExclusiveReturn> filterList;
	private MroExclusiveReturn[] deleteList;
	private LocationSiteMap sLocationSiteMap;
	private String type;  //搜索type：ITEM,CLASSSTRUCTUREID
	
	private String selectPlantCode;
	
	public ExclusiveReturnForm(){
		type="ITEM";
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

	public List<MroExclusiveReturn> getList() {
		return list;
	}

	public void setList(List<MroExclusiveReturn> list) {
		this.list = list;
	}

	public List<MroExclusiveReturn> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<MroExclusiveReturn> filterList) {
		this.filterList = filterList;
	}

	public MroExclusiveReturn[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(MroExclusiveReturn[] deleteList) {
		this.deleteList = deleteList;
	}

	public String getSelectPlantCode() {
		return selectPlantCode;
	}

	public void setSelectPlantCode(String selectPlantCode) {
		this.selectPlantCode = selectPlantCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}

}
