package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacilityEq;

public class MroOrgFacilityEqForm implements Serializable{
	private static final long serialVersionUID = -8941732813198607040L;
	private List<MroOrgFacilityEq> listMroOrgFacilityEq;
	private List<MroOrgFacilityEq> filterListMroOrgFacilityEq;
	private MroOrgFacilityEq[] deleteMroOrgFacilityEq;
	private Map reasonOption;
	
	private String selectReason;
	private String selectDeptNo;
	private LocationSiteMap sLocationSiteMap;
	
	public MroOrgFacilityEqForm(){
		intial();
	}
	
	public void intial(){
		listMroOrgFacilityEq=new ArrayList<>();
		filterListMroOrgFacilityEq=new ArrayList<>();
		deleteMroOrgFacilityEq=null;
	}

	public void copyList(){
		filterListMroOrgFacilityEq=listMroOrgFacilityEq;
	}

	public List<MroOrgFacilityEq> getListMroOrgFacilityEq() {
		return listMroOrgFacilityEq;
	}

	public void setListMroOrgFacilityEq(List<MroOrgFacilityEq> listMroOrgFacilityEq) {
		this.listMroOrgFacilityEq = listMroOrgFacilityEq;
	}

	public List<MroOrgFacilityEq> getFilterListMroOrgFacilityEq() {
		return filterListMroOrgFacilityEq;
	}

	public void setFilterListMroOrgFacilityEq(
			List<MroOrgFacilityEq> filterListMroOrgFacilityEq) {
		this.filterListMroOrgFacilityEq = filterListMroOrgFacilityEq;
	}

	public MroOrgFacilityEq[] getDeleteMroOrgFacilityEq() {
		return deleteMroOrgFacilityEq;
	}

	public void setDeleteMroOrgFacilityEq(MroOrgFacilityEq[] deleteMroOrgFacilityEq) {
		this.deleteMroOrgFacilityEq = deleteMroOrgFacilityEq;
	}

	public Map getReasonOption() {
		return reasonOption;
	}

	public void setReasonOption(Map reasonOption) {
		this.reasonOption = reasonOption;
	}

	public String getSelectReason() {
		return selectReason;
	}

	public void setSelectReason(String selectReason) {
		this.selectReason = selectReason;
	}
	
	public String getSelectDeptNo() {
		return selectDeptNo;
	}

	public void setSelectDeptNo(String selectDeptNo) {
		this.selectDeptNo = selectDeptNo;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}
	
}
