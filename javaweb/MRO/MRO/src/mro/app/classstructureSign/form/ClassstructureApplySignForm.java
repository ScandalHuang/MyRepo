package mro.app.classstructureSign.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ClassstructureApplySign;

public class ClassstructureApplySignForm implements Serializable{
	
	private static final long serialVersionUID = -1933111867672310749L;
	
	private List<ClassstructureApplySign> listClassstructureApplySign;
	private List<ClassstructureApplySign> filterListClassstructureApplySign;
	private ClassstructureApplySign[] deleteClassstructureApplySign;
	
	private String selectclassstructureid;
	
	public ClassstructureApplySignForm(){
		intial();
	}
	
	
	public void intial(){
		listClassstructureApplySign=new ArrayList<>();
		filterListClassstructureApplySign=new ArrayList<>();
		deleteClassstructureApplySign=null;
	}

	public void copyList(){
		filterListClassstructureApplySign=listClassstructureApplySign;
	}

	public List<ClassstructureApplySign> getListClassstructureApplySign() {
		return listClassstructureApplySign;
	}

	public void setListClassstructureApplySign(
			List<ClassstructureApplySign> listClassstructureApplySign) {
		this.listClassstructureApplySign = listClassstructureApplySign;
	}

	public List<ClassstructureApplySign> getFilterListClassstructureApplySign() {
		return filterListClassstructureApplySign;
	}

	public void setFilterListClassstructureApplySign(
			List<ClassstructureApplySign> filterListClassstructureApplySign) {
		this.filterListClassstructureApplySign = filterListClassstructureApplySign;
	}

	public ClassstructureApplySign[] getDeleteClassstructureApplySign() {
		return deleteClassstructureApplySign;
	}

	public void setDeleteClassstructureApplySign(
			ClassstructureApplySign[] deleteClassstructureApplySign) {
		this.deleteClassstructureApplySign = deleteClassstructureApplySign;
	}

	public String getSelectclassstructureid() {
		return selectclassstructureid;
	}

	public void setSelectclassstructureid(String selectclassstructureid) {
		this.selectclassstructureid = selectclassstructureid;
	}
	
	
}
