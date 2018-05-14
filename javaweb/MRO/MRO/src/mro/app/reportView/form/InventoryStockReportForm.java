package mro.app.reportView.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mro.app.reportView.viewType.InventoryStockReportType;
import mro.base.entity.Inventory;
import mro.utility.vo.ColumnModel;
import mro.utility.vo.LinkedModel;

public class InventoryStockReportForm implements Serializable {
	private static final long serialVersionUID = 2570523590374533130L;
	private List<Inventory> list;
	private LinkedList<LinkedModel> SLinked;
	private List<ColumnModel> columns;
	
	private String itemnum;
	
	public InventoryStockReportForm(){
		intial();
	}
	
	public void intial(){
		list=new ArrayList();
		SLinked=new LinkedList<LinkedModel>();
		columns=null;
	}

	public void setList(List<Inventory> list,InventoryStockReportType type,boolean addNode){
		this.list = list;
		columns=type.getValue();
		if(addNode) this.addSLinked(list, type);
	}
	public void addSLinked(List<Inventory> list,InventoryStockReportType type){
		SLinked.add(new LinkedModel(this.list, type));
	}
	
	public List<Inventory> getList() {
		return list;
	}

	public void setList(List<Inventory> list) {
		this.list = list;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public LinkedList<LinkedModel> getSLinked() {
		return SLinked;
	}

	public void setSLinked(LinkedList<LinkedModel> sLinked) {
		SLinked = sLinked;
	}

}
