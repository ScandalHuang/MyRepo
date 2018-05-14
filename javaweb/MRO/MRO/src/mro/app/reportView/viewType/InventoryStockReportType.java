package mro.app.reportView.viewType;

import java.util.Arrays;
import java.util.List;

import mro.app.reportView.bo.InventoryStockReportBO;
import mro.base.bo.InventoryBO;
import mro.utility.JsonUtils;
import mro.utility.ListUtility;
import mro.utility.vo.ColumnModel;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;


public enum InventoryStockReportType {
	PERSON(null),
	WMS(ListUtility.getColumnModels(Arrays.asList(
			("ITEM_NUM,SUBINVENTORY,STOCK_TYPE,ERP_PO_NO,BINNUM,REAL_QTY").split(",")))),
	IQC(Arrays.asList(new ColumnModel("IM_ITEMNUM","IM_ITEMNUM"),
			new ColumnModel("DESTINATION_SUBINVENTORY","DESTINATION_SUBINVENTORY"),
			new ColumnModel("PO_NO","PO_NO"),new ColumnModel("LINE_CLOSED_CODE","LINE_CLOSED_CODE"),
			new ColumnModel("UNDELIVER_QTY","UNDELIVER_QTY"),
			new ColumnModel("REQUESTOR_EMP_NO","REQUESTOR_EMP_NO",PERSON))),
	PO(Arrays.asList(new ColumnModel("IM_ITEMNUM","IM_ITEMNUM"),
			new ColumnModel("DESTINATION_SUBINVENTORY","DESTINATION_SUBINVENTORY"),
			new ColumnModel("PO_NO","PO_NO"),new ColumnModel("LINE_CLOSED_CODE","LINE_CLOSED_CODE"),
			new ColumnModel("UNRECEIVED_QTY","UNRECEIVED_QTY"),
			new ColumnModel("REQUESTOR_EMP_NO","REQUESTOR_EMP_NO",PERSON))),
	PR(Arrays.asList(new ColumnModel("IM_ITEMNUM","IM_ITEMNUM"),
			new ColumnModel("DESTINATION_SUBINVENTORY","DESTINATION_SUBINVENTORY"),
			new ColumnModel("PR_NO","PR_NO"),new ColumnModel("QUANTITY","QUANTITY"),
			new ColumnModel("REQUESTOR_EMP_NO","REQUESTOR_EMP_NO",PERSON))),
	BSS(ListUtility.getColumnModels(Arrays.asList(
					("ITEMNUM,QTY,SHOP").split(",")))),
	SESTOCK(ListUtility.getColumnModels(Arrays.asList(
			("itemnum,location,stock").split(",")))),
	SDSTOCK(ListUtility.getColumnModels(Arrays.asList(
			("itemnum,location,stock").split(",")))),
	INVENTORY(Arrays.asList(new ColumnModel("itemnum","itemnum"),
			new ColumnModel("location","location"),
			new ColumnModel("stock","stock",WMS),new ColumnModel("iqc","iqc",IQC),
			new ColumnModel("mcbssonhand","mcbssonhand",BSS),
			new ColumnModel("sestock","sestock",SESTOCK),new ColumnModel("sdstock","sdstock",SDSTOCK),
			new ColumnModel("poqty","poqty",PO),new ColumnModel("prqty","prqty",PR))),
	STOCKS(Arrays.asList(new ColumnModel("ITEMNUM","ITEMNUM"),
			new ColumnModel("DESCRIPTION","DESCRIPTION"),
			new ColumnModel("LOCATION","LOCATION"),
			new ColumnModel("TOTAL","TOTAL",INVENTORY)));

	private List<ColumnModel> value;

	private InventoryStockReportType(List<ColumnModel> value) {
		this.value = value;
	}
	
	public String getType(){
		if(name().equals(PERSON.name()))
			return "TYPE1";
		else
			return "TYPE0";
	}

	@Override
	public String toString() {
		return name() != null ? name() : null;
	}

	public static InventoryStockReportType findValue(String key) {
		return InventoryStockReportType.valueOf(key);
	}
	
	public List getList(Object object){
		InventoryStockReportBO ivsBO=SpringContextUtil.getBean(InventoryStockReportBO.class);
		InventoryBO inventoryBO=SpringContextUtil.getBean(InventoryBO.class);
		
		List variables=mro.utility.ReflectUtils.getObjectMap(object.getClass());
		String itemnum=this.getFieldValue(variables, object, "itemnum");
		String location=this.getFieldValue(variables, object,"location");
		String siteid=this.getFieldValue(variables, object, "siteid");
		
		if(name().equals(WMS.name())){
			return ivsBO.getWmsStockDetailAlls(itemnum,location);
		}else if(name().equals(IQC.name())){
			return ivsBO.getOpenPos(itemnum,location,false,true);
		}else if(name().equals(PO.name())){
			return ivsBO.getOpenPos(itemnum,location,true,false);
		}else if(name().equals(PR.name())){
			return ivsBO.getUnProcessPrs(itemnum,location);
		}else if(name().equals(BSS.name())){
			return ivsBO.getBss(itemnum,siteid);
		}else if(name().equals(SDSTOCK.name())){
			return inventoryBO.getInventorys(itemnum,"%SD","INX");
		}else if(name().equals(SESTOCK.name())){
			return inventoryBO.getInventorys(itemnum,"%SE","INX");
		}else if(name().equals(INVENTORY.name())){
			return inventoryBO.getInventoryStock(itemnum,location);
		}else if(name().equals(STOCKS.name())){
			return ivsBO.getStocks(itemnum,location);
		}
		return null;
	}
	private String getFieldValue(List variables,Object object,String column){
		String value=ObjectUtils.toString(JsonUtils.getValue(object,column));
		if(StringUtils.isBlank(value)){
			value=ObjectUtils.toString(JsonUtils.getValue(object,column.toUpperCase()));
		}
		return value;
	}
	
	public List<ColumnModel> getValue() {
		return value;
	}
}
