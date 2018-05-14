package mro.quartz.job.sapJob.jco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.quartz.job.sapJob.vo.MroToSapMespn;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Structure;
public class MespnMroToTABdata extends SapRfcFunction {

	private JCO.Function jcoFunction = null;
	private JCO.Client jcoClient = null;
	 HashMap hmPart ;
	 
	 protected static final int PANT_LENGTH = 4;
     protected static final int PANT_START_POS = 0;
	
     protected static final int INSPECTIONTYPE_LENGTH = 1;
	 protected static final int INSPECTIONTYPE_START_POS = 5;

	 protected static final int CONSUMABLE_LENGTH = 1;
	 protected static final int CONSUMABLE_START_POS = 63;

	 protected static final int PERIOD_LENGTH = 2;
	 protected static final int PERIOD_START_POS = 64;
		
	 protected static final int USAGEQUANTITY_LENGTH = 13;
	 protected static final int USAGEQUANTITY_START_POS = 66;
	 
	public MespnMroToTABdata() {
		super.setFunctionName("ZMM_GENERATE_MESPN_MRO_TO_TAB");
	}
	 
 
 	/*
	public Map<String, String> execute(MroToSapIo obj) throws Exception{
		Map<String, String> jcoReturn = new HashMap<String, String>();
		// connect
		jcoClient = super.connect();
		jcoFunction = super.getJcoFunction(jcoClient);
	 
		setImportData(obj);

		setTableData(obj);

		// execute function
		jcoClient.execute(jcoFunction);

		jcoReturn = getExportData();

		// disconnect
		super.disconnect();
		
		return jcoReturn;
	} */
	
	//public Map execute(MroToSapIo obj) throws Exception{
	public Map execute(MroToSapMespn itemNum) throws Exception{
	Map jcoReturn = new HashMap<String, String>();
		// connect
		jcoClient = super.connect();
		jcoFunction = super.getJcoFunction(jcoClient);
	 
		setImportData(itemNum);

	//	setTableData(itemNum);  //目前用不到

		// execute function
		jcoClient.execute(jcoFunction);

	 	jcoReturn = getExportData();
		
		return jcoReturn;
	} 
	
	 

	
	// 
	
	protected static class InputParameterList {
		JCO.ParameterList importParameterList;

		JCO.ParameterList tableParameterList;
  	 
		 

		JCO.Table tableExtensionIn;

		JCO.Table tableExtensionInX;

		public InputParameterList(JCO.Function function) {
			this.importParameterList = function.getImportParameterList();
			this.tableParameterList = function.getTableParameterList();
		}

		public void appendExtensionInRow() {
			if (this.tableExtensionIn == null) {
				this.tableExtensionIn = this.tableParameterList.getTable("EXTENSIONIN");
				this.tableExtensionInX = this.tableParameterList.getTable("EXTENSIONINX");
			}
			tableExtensionIn.appendRow();
			tableExtensionInX.appendRow();

		}

	 
 
	 

		public void setExtensionIn(String value, String key) {
			String xValue = "X";
			setExtensionIn(value, xValue, key);
		}

		public void setExtensionIn(String value, String xValue, String key) {
			if (StringFilter(value).equals("")) {
				return;
			}
			tableExtensionIn.setValue(value, key);
			tableExtensionInX.setValue(xValue, key);
		}

		public String StringFilter(String str) {
			if (str == null) {
				return "";
			}
			return str.trim();
		}

	}
	
	
	
	protected InputParameterList input;

	
 
	/**
	 * 定義Bapi客制化的數據類型, 客制化時,許多屬性是放在同一個欄位中,是根據他們的位置來決定, <br>
	 * 所以在AttributeType 定義了atrribute 的起始位置, 長度,和value
	 * 
	 */
	class AttributeType {
		int index;

		int length;

		String value;

		public AttributeType(int index, int length, String value) {
			this.index = index;
			this.length = length;
			this.value = value;

			if (value == null)
				this.value = "";

		}
	}

	class CustomFieldComputer {
		private static final String BLANK_STRING = " ";

		private static final int CUSTOM_FIELD_LENGTH = 240;

		List attributeList;

		StringBuffer field;

		public CustomFieldComputer() {
			attributeList = new ArrayList();
			initStringBuffer();
		}

		public void addAttribute(AttributeType attribute) {
			attributeList.add(attribute);
		}

		public String getFieldValue() {
			for (int i = 0; i < attributeList.size(); i++) {
				AttributeType attribute = (AttributeType) attributeList.get(i);
				field.replace(attribute.index, attribute.index + attribute.value.length(), attribute.value);
			}

			return field.toString();
		}

		private void initStringBuffer() {
			field = new StringBuffer();

			for (int i = 0; i < CUSTOM_FIELD_LENGTH; i++) {
				field.append(BLANK_STRING);
			}
		}
		
		

	}



	private Map<String, String> getExportData() {
		Map<String, String> result = new HashMap<String, String>();
		// get export data
		ParameterList exportParameterList = jcoFunction.getExportParameterList();
		Structure exportReturn = exportParameterList.getStructure("RETURN");
		
		if (exportReturn != null) {
			// TYPE
			// Message type: S Success, E Error, W Warning, I Info, A Abort
			String type = exportReturn.getString("ITEM");
			String message = exportReturn.getString("MESSAGE");
			result.put("ITEM", type);
			result.put("MESSAGE", message);
		}
		
		return result;
	} 
	 

	private void setTableData(MroToSapMespn obj) {
		// set table data
		ParameterList tableParameterList = jcoFunction.getTableParameterList();
		// MATERIALDESCRIPTION	LIKE	BAPI_MAKT
		/*
		JCO.Table materialdescription = tableParameterList.getTable("MATERIALDESCRIPTION");
		// 第一筆資料
		materialdescription.appendRow();
		materialdescription.setRow(0);
	 	materialdescription.setValue("EN", "LANGU");//sap必傳
	 	materialdescription.setValue("EN", "LANGU_ISO");//sap必傳
	 
		// 第二筆資料
		materialdescription.appendRow();
		materialdescription.setRow(1);
	 	materialdescription.setValue("ZF", "LANGU");//sap必傳
		 materialdescription.setValue("ZF", "LANGU_ISO");//sap必傳
 
		tableParameterList.setValue(materialdescription, "MATERIALDESCRIPTION");
		*/
		
		// UNITSOFMEASURE	LIKE	BAPI_MARM
		 // 傳檔有問題暫時忽略
		 
		 
		// EXTENSIONINX	LIKE	BAPIPAREXX
	 
 	 
		
		// UNITSOFMEASUREX	LIKE	BAPI_MARMX
		
		// INTERNATIONALARTNOS	LIKE	BAPI_MEAN
		// MATERIALLONGTEXT	LIKE	BAPI_MLTX
	 
		// TAXCLASSIFICATIONS	LIKE	BAPI_MLAN
		// RETURNMESSAGES	LIKE	BAPI_MATRETURN2
		// PRTDATA	LIKE	BAPI_MFHM
		// PRTDATAX	LIKE	BAPI_MFHMX

		// EXTENSIONIN	LIKE	BAPIPAREX
		
	 	
 
	 
	}

	private void setImportData(MroToSapMespn obj) {
		// set import data
		ParameterList importParameterList = jcoFunction.getImportParameterList();
		// P_MATNR	LIKE	MARA-MATNR
 		//Structure matnr = importParameterList.getStructure("P_MATNR");
 
 		importParameterList.setValue(obj.getItemNum(), "P_MATNR");  //料號若都為數字前面要補0至18碼
 		importParameterList.setValue(obj.getClassStructureid(), "P_MATKL");  // 料號MG
	  
     //	importParameterList.setValue(matnr, "P_MATNR");

		// P_MATKL	LIKE	MARA-MATKL
		//Structure clientdata = getCLIENTDATA(obj);
 	//	Structure matkl = importParameterList.getStructure("P_MATKL");
 	//	matkl.setValue(obj.getItemNum(), "MATNR"); //SAP傳檔必要
 	//	matkl.setValue(obj.getClassStructureid(), "MATKL");
	 
	//	importParameterList.setValue(matkl, "P_MATKL");
	 
		// CLIENTDATAX	LIKE	BAPI_MARAX
		 

		// PLANTDATA	LIKE	BAPI_MARC
		 
  

		// PLANTDATAX	LIKE	BAPI_MARCX
		 
	 

		// FORECASTPARAMETERS	LIKE	BAPI_MPOP
		// FORECASTPARAMETERSX	LIKE	BAPI_MPOPX
		// PLANNINGDATA	LIKE	BAPI_MPGD
		// PLANNINGDATAX	LIKE	BAPI_MPGDX

		// STORAGELOCATIONDATA	LIKE	BAPI_MARD
	 
	 
		// VALUATIONDATA	LIKE	BAPI_MBEW
 
		
		// WAREHOUSENUMBERDATA	LIKE	BAPI_MLGN
		// WAREHOUSENUMBERDATAX	LIKE	BAPI_MLGNX
		// SALESDATA	LIKE	BAPI_MVKE
		// SALESDATAX	LIKE	BAPI_MVKEX
		// STORAGETYPEDATA	LIKE	BAPI_MLGT
		// STORAGETYPEDATAX	LIKE	BAPI_MLGTX
		// FLAG_ONLINE	LIKE	SY-DATAR
		// FLAG_CAD_CALL	LIKE	SY-DATAR
		 
		  
	}

	 
}
