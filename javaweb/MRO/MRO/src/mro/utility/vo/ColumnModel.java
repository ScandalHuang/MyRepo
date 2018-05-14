package mro.utility.vo;

import java.io.Serializable;

import javax.persistence.Entity;

import mro.app.reportView.viewType.InventoryStockReportType;



@Entity
public class ColumnModel implements Serializable {
	private static final long serialVersionUID = -7278616011779039851L;
	private String header;
	private String property;
	private InventoryStockReportType methodType;

	public ColumnModel(String property,String header) {
		this.property = property;
		this.header = header;
	}
	public ColumnModel(String property,String header,InventoryStockReportType methodType) {
		this.property = property;
		this.header = header;
		this.methodType = methodType;
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	public InventoryStockReportType getMethodType() {
		return methodType;
	}
	public void setMethodType(InventoryStockReportType methodType) {
		this.methodType = methodType;
	}
	
}