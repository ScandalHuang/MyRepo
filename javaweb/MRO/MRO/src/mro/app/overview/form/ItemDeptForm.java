package mro.app.overview.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ItemDept;

public class ItemDeptForm implements Serializable {
	private static final long serialVersionUID = -2732030583503305209L;
	private List<ItemDept> listItemDept;
	private ItemDept[] deleteItemDept;
	private String deptCode;
	private String itemnum;
	
	public ItemDeptForm(){
		intial();
	}
	
	public void intial(){
		listItemDept=new ArrayList<>();
		deleteItemDept=null;
		itemnum="";
	}

	public List<ItemDept> getListItemDept() {
		return listItemDept;
	}

	public void setListItemDept(List<ItemDept> listItemDept) {
		this.listItemDept = listItemDept;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public ItemDept[] getDeleteItemDept() {
		return deleteItemDept;
	}

	public void setDeleteItemDept(ItemDept[] deleteItemDept) {
		this.deleteItemDept = deleteItemDept;
	}

	
}
