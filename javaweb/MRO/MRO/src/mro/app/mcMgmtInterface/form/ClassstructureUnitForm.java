package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ClassstructureUnit;

public class ClassstructureUnitForm implements Serializable{
	private static final long serialVersionUID = 2019994515228894288L;
	private List<ClassstructureUnit> listClassstructureUnit;
	private List<ClassstructureUnit> filterListClassstructureUnit;
	private ClassstructureUnit[] deleteClassstructureUnit;
	
	private String selectclassstructureid;
	
	public ClassstructureUnitForm(){
		intial();
	}
	
	public void intial(){
		listClassstructureUnit=new ArrayList<>();
		filterListClassstructureUnit=new ArrayList<>();
		deleteClassstructureUnit=null;
	}
	public void copyList(){
		filterListClassstructureUnit=listClassstructureUnit;
	}

	public List<ClassstructureUnit> getListClassstructureUnit() {
		return listClassstructureUnit;
	}

	public void setListClassstructureUnit(
			List<ClassstructureUnit> listClassstructureUnit) {
		this.listClassstructureUnit = listClassstructureUnit;
	}

	public List<ClassstructureUnit> getFilterListClassstructureUnit() {
		return filterListClassstructureUnit;
	}

	public void setFilterListClassstructureUnit(
			List<ClassstructureUnit> filterListClassstructureUnit) {
		this.filterListClassstructureUnit = filterListClassstructureUnit;
	}

	public ClassstructureUnit[] getDeleteClassstructureUnit() {
		return deleteClassstructureUnit;
	}

	public void setDeleteClassstructureUnit(
			ClassstructureUnit[] deleteClassstructureUnit) {
		this.deleteClassstructureUnit = deleteClassstructureUnit;
	}

	public String getSelectclassstructureid() {
		return selectclassstructureid;
	}

	public void setSelectclassstructureid(String selectclassstructureid) {
		this.selectclassstructureid = selectclassstructureid;
	}
	
}
