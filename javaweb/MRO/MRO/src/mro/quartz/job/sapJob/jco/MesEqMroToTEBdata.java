package mro.quartz.job.sapJob.jco;

import java.util.HashMap;
import java.util.Map;

import mro.quartz.job.sapJob.vo.MroToSapMespn;

import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

public class MesEqMroToTEBdata extends SapRfcFunction {

	
	private String NOT_EXIST_CLASSFICATION_CODE="000000000000000000"; //18碼都為0代表未新增
	
	 
	public MesEqMroToTEBdata() {
	}
	public void setFunctionName(String functionName){
		super.setFunctionName(functionName);
	}
	
	public Map execute(MroToSapMespn obj) {
		Client jcoClient = super.connect();
		this.setFunctionName("BAPI_OBJCL_GET_KEY_OF_OBJECT");   //判斷是否新增過
		String clobjectkeyout=(String) executeJCO(jcoClient,obj,null,true);
		if (this.validateExists(clobjectkeyout)) { //已存在update 
			this.setFunctionName("BAPI_OBJCL_CHANGE_KEY");
		}else{  //create
			this.setFunctionName("BAPI_OBJCL_CREATE");
		}
		Map map=(Map) this.executeJCO(jcoClient,obj,clobjectkeyout,false);
		map=updateMessage(map);
		this.commitFunction(jcoClient);
		return map;
	}
	
	public boolean validateExists(String clobjectkeyout){
		if ( clobjectkeyout != null && !clobjectkeyout.equals(NOT_EXIST_CLASSFICATION_CODE)) { //已存在update 
			return true;
		}
		return false;
	}
	public Map updateMessage(Map map){
		if (map!=null && map.get("MESSAGE").equals("The assignment was not changed")) { 
			map.put("TYPE","S");
		}
		return map;
	}
	
	
	public Object executeJCO(Client jcoClient,MroToSapMespn obj,String clobjectkeyout,boolean ckeckFlag) {
		Object returnValue = null;
		Function jcoFunction = super.getJcoFunction(jcoClient);
		
		if(ckeckFlag){  //BAPI_OBJCL_GET_KEY_OF_OBJECT
			setCheckImportData(jcoFunction,obj);		
			jcoClient.execute(jcoFunction);
			returnValue = getCheckExport(jcoFunction);
		}else{
			setCreatemportData(jcoFunction,obj,clobjectkeyout);
			jcoClient.execute(jcoFunction);
			returnValue = getExportTableData(jcoFunction);
		}
		
		return returnValue;
	} 
	


	private String  getCheckExport(Function jcoFunction) {
		ParameterList exportParameterList = jcoFunction.getExportParameterList();
		String existClassficationView = exportParameterList.getString("CLOBJECTKEYOUT");
		return existClassficationView;
	} 
	
	private Map<String, String> getExportTableData(Function jcoFunction) {
		Map<String, String> result = new HashMap<String, String>();
		ParameterList tableParameterList = jcoFunction.getTableParameterList();
		Table table = tableParameterList.getTable("RETURN");		
		if (table != null) {
			String type = table.getString("TYPE");
			String message = table.getString("MESSAGE");
			result.put("TYPE", type);
			result.put("MESSAGE", message);
		}
		
		return result;
	} 



	public void setCheckImportData(Function jcoFunction,MroToSapMespn obj) {
		// set import data
		ParameterList importParameterList = jcoFunction.getImportParameterList();
		importParameterList.setValue(obj.getItemNum(), "OBJECTNAME");
		importParameterList.setValue("MARA", "OBJECTTABLE");
		importParameterList.setValue("001", "CLASSTYPE");
	}

	
	public void setCreatemportData(Function jcoFunction,MroToSapMespn obj,String clobjectkeyout) {
		// set import data
		ParameterList importParameterList = jcoFunction.getImportParameterList();
		
		if(this.validateExists(clobjectkeyout)){
			importParameterList.setValue(clobjectkeyout,"CLOBJECTKEY");
			importParameterList.setValue("MATERIAL", "CLASSNUM");
			importParameterList.setValue("1", "STATUS_NEW");
		}else{
			importParameterList.setValue(obj.getItemNum(),"OBJECTKEYNEW");
			importParameterList.setValue("MARA", "OBJECTTABLENEW");
			importParameterList.setValue("MATERIAL", "CLASSNUMNEW");
			importParameterList.setValue("001", "CLASSTYPENEW");
			importParameterList.setValue("1", "STATUS");
		}
		this.setTableData(jcoFunction, obj, clobjectkeyout);
	}
	
	private void setTableData(Function jcoFunction,MroToSapMespn obj,String clobjectkeyout) {
		// set table data
		ParameterList tableParameterList = jcoFunction.getTableParameterList();
		Table createViewTable = null;
		if(this.validateExists(clobjectkeyout)){
			createViewTable = tableParameterList.getTable("ALLOCVALUESCHAR_NEW");	
		}else{
			createViewTable = tableParameterList.getTable("ALLOCVALUESCHAR");		
		}	
		if(obj.getEquipmentId()!=null){
			String[] equipmentid=obj.getEquipmentId().split(",");
			for(String eqId:equipmentid){
				createViewTable.appendRow();
				createViewTable.setValue("EQUIPMENTID", "CHARACT");
				createViewTable.setValue( eqId , "VALUE_CHAR");
			}
		}
	 
	}
	public void commitFunction(Client jcoClient){
		this.setFunctionName("BAPI_TRANSACTION_COMMIT");
		Function jcoFunction = super.getJcoFunction(jcoClient);		
		ParameterList importParameterList = jcoFunction.getImportParameterList();
		importParameterList.setValue("X","WAIT");
		jcoClient.execute(jcoFunction);
	}
	 
}
