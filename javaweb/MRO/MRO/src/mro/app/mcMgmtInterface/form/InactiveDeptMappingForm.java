package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.InactiveDeptMapping;

public class InactiveDeptMappingForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<InactiveDeptMapping> list;
	private List<InactiveDeptMapping> filterList;
	private InactiveDeptMapping[] deleteList;
	
	public InactiveDeptMappingForm(){
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

	public List<InactiveDeptMapping> getList() {
		return list;
	}

	public void setList(List<InactiveDeptMapping> list) {
		this.list = list;
	}

	public List<InactiveDeptMapping> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<InactiveDeptMapping> filterList) {
		this.filterList = filterList;
	}

	public InactiveDeptMapping[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(InactiveDeptMapping[] deleteList) {
		this.deleteList = deleteList;
	}

}
