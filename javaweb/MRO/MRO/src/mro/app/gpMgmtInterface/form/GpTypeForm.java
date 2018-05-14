package mro.app.gpMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import mro.base.entity.ClassstructureGp;


public class GpTypeForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<ClassstructureGp> list;
	private List<ClassstructureGp> filterList;
	private ClassstructureGp[] deleteList;

	private Map gpDelivery; // 是否隨產品出貨選單
	private Map gpRemain; // 是否殘留在產品內部選單
	
	public GpTypeForm(){
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

	public List<ClassstructureGp> getList() {
		return list;
	}

	public void setList(List<ClassstructureGp> list) {
		this.list = list;
	}

	public List<ClassstructureGp> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<ClassstructureGp> filterList) {
		this.filterList = filterList;
	}

	public ClassstructureGp[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(ClassstructureGp[] deleteList) {
		this.deleteList = deleteList;
	}

	public Map getGpDelivery() {
		return gpDelivery;
	}

	public void setGpDelivery(Map gpDelivery) {
		this.gpDelivery = gpDelivery;
	}

	public Map getGpRemain() {
		return gpRemain;
	}

	public void setGpRemain(Map gpRemain) {
		this.gpRemain = gpRemain;
	}

	
}
