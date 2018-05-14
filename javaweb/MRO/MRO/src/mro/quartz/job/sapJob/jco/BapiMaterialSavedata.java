package mro.quartz.job.sapJob.jco;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.quartz.job.sapJob.vo.MroToSapIo;

import org.apache.commons.lang3.StringUtils;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Structure;

public class BapiMaterialSavedata extends SapRfcFunction {

	private JCO.Function jcoFunction = null;
	private JCO.Client jcoClient = null;
	HashMap hmPart;

	protected static final int PANT_LENGTH = 4;
	protected static final int PANT_START_POS = 0;

	protected static final int INSPECTIONTYPE_LENGTH = 1;
	protected static final int INSPECTIONTYPE_START_POS = 5;

	protected static final int APPLY_EMP_LENGTH = 30;
	protected static final int APPLY_EMP_START_POS = 6;

	protected static final int CONSUMABLE_LENGTH = 1;
	protected static final int CONSUMABLE_START_POS = 63;

	protected static final int PERIOD_LENGTH = 2;
	protected static final int PERIOD_START_POS = 64;

	protected static final int USAGEQUANTITY_LENGTH = 13;
	protected static final int USAGEQUANTITY_START_POS = 66;

	public BapiMaterialSavedata() {
		super.setFunctionName("BAPI_MATERIAL_SAVEDATA");
	}

	/*
	 * public Map<String, String> execute(MroToSapIo obj) throws Exception{
	 * Map<String, String> jcoReturn = new HashMap<String, String>(); // connect
	 * jcoClient = super.connect(); jcoFunction =
	 * super.getJcoFunction(jcoClient);
	 * 
	 * setImportData(obj);
	 * 
	 * setTableData(obj);
	 * 
	 * // execute function jcoClient.execute(jcoFunction);
	 * 
	 * jcoReturn = getExportData();
	 * 
	 * // disconnect super.disconnect();
	 * 
	 * return jcoReturn; }
	 */

	// public Map execute(MroToSapIo obj) throws Exception{
	public Map execute(MroToSapIo itemNum) {
		Map jcoReturn = new HashMap<String, String>();
		// connect
		try {
			jcoClient = super.connect();
			jcoFunction = super.getJcoFunction(jcoClient);

			setImportData(itemNum);

			BigInteger d = null;
			if (itemNum.getTransferQuantity() != null) {
				BigDecimal b = new BigDecimal(itemNum.getTransferQuantity());
				d = b.unscaledValue();
			}

			
				setTableData(itemNum);
				// execute function
				jcoClient.execute(jcoFunction);

				jcoReturn = getExportData();
		} catch (Exception e) {
			e.printStackTrace();
			jcoReturn = new HashMap<>();
			jcoReturn.put("TYPE", "E");
			jcoReturn.put("MESSAGE", e.toString());
		}

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
				this.tableExtensionIn = this.tableParameterList
						.getTable("EXTENSIONIN");
				this.tableExtensionInX = this.tableParameterList
						.getTable("EXTENSIONINX");
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
				field.replace(attribute.index, attribute.index
						+ attribute.value.length(), attribute.value);
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
		ParameterList exportParameterList = jcoFunction
				.getExportParameterList();
		Structure exportReturn = exportParameterList.getStructure("RETURN");

		if (exportReturn != null) {
			// TYPE
			// Message type: S Success, E Error, W Warning, I Info, A Abort
			String type = exportReturn.getString("TYPE");
			String message = exportReturn.getString("MESSAGE");
			result.put("TYPE", type);
			result.put("MESSAGE", message);
		}

		return result;
	}

	private Map<String, String> getExportDataError(String type, String message) {
		Map<String, String> result = new HashMap<String, String>();
		// TYPE
		// Message type: S Success, E Error, W Warning, I Info, A Abort
		result.put("TYPE", "E");
		result.put("MESSAGE", message);
		return result;
	}

	private void setTableData(MroToSapIo obj) throws UnsupportedEncodingException {
		// set table data
		ParameterList tableParameterList = jcoFunction.getTableParameterList();
		// MATERIALDESCRIPTION LIKE BAPI_MAKT
		JCO.Table materialdescription = tableParameterList
				.getTable("MATERIALDESCRIPTION");
		// 第一筆資料
		materialdescription.appendRow();
		materialdescription.setRow(0);
		materialdescription.setValue("E", "LANGU");// sap必傳
		materialdescription.setValue("E", "LANGU_ISO");// sap必傳
		materialdescription.setValue(obj.getDescription(), "MATL_DESC");
		// 第二筆資料
		materialdescription.appendRow();
		materialdescription.setRow(1);
		materialdescription.setValue("M", "LANGU");// sap必傳
		materialdescription.setValue("M", "LANGU_ISO");// sap必傳
		materialdescription.setValue(obj.getDescription(), "MATL_DESC");
		tableParameterList.setValue(materialdescription, "MATERIALDESCRIPTION");
		if (obj.getPackageUnit() != null) { // 包裝單位不為空值才執行此段拋轉
			// UNITSOFMEASURE LIKE BAPI_MARM
			// 傳檔有問題暫時忽略

			JCO.Table extensionin = tableParameterList
					.getTable("UNITSOFMEASURE");
			// 第一筆資料
			extensionin.appendRow();
			extensionin.setRow(0);

			// extensionin.setValue("BAPI_TE_MARC", "STRUCTURE");
			extensionin.setValue(obj.getPackageUnit(), "ALT_UNIT");
			extensionin.setValue(obj.getPackageUnit(), "ALT_UNIT_ISO");

			// kaiyeu 201307 因sap數量無法接受小數,故對轉換數量大於0或小於0時,拋轉處理

			if (obj.getTransferQuantity() != null) {
				/*
				 * 如轉換數量為整數值,NUMERATOR放原轉換數量,DENOMINATR放預設單位1
				 * 如轉換數量為小數點後1位,NUMERATOR放原轉換數量乘10,DENOMINATR放 單位10
				 * 如轉換數量為小數點後2位,NUMERATOR放原轉換數量乘100,DENOMINATR放 單位100
				 */
				BigDecimal b = new BigDecimal(obj.getTransferQuantity());
				int c = b.scale();// 取出欄位有小數幾位
				double sum = Math.pow(10, c);// 取出小數幾位後算10的n次方當做將欄位變成整數的倍率
				System.out.println(c);
				BigInteger d = b.unscaledValue();
				System.out.println(d);
				extensionin.setValue(String.valueOf(d), "NUMERATOR"); // 算出轉換數量的整數
				extensionin.setValue(String.valueOf(sum), "DENOMINATR"); // 把倍率放入
																			// 包裝單位比率
			}
			// else if(obj.getTransferQuantity()!=null &&
			// Double.parseDouble(obj.getTransferQuantity())>=0){
			// extensionin.setValue(obj.getTransferQuantity(), "NUMERATOR");
			// extensionin.setValue("1", "DENOMINATR");
			// }
			else {
				extensionin.setValue(obj.getTransferQuantity(), "NUMERATOR");
				extensionin.setValue("", "DENOMINATR");
			}

			tableParameterList.setValue(extensionin, "UNITSOFMEASURE");

			// EXTENSIONINX LIKE BAPIPAREXX
			JCO.Table extensioninx = tableParameterList
					.getTable("UNITSOFMEASUREX");
			// 第一筆資料
			extensioninx.appendRow();
			extensioninx.setRow(0);
			// extensioninx.setValue("BAPI_TE_MARCX", "STRUCTURE");
			// extensioninx.setValue("T003XXXXXXXX", "VALUEPART1");
			extensioninx.setValue(obj.getPackageUnit(), "ALT_UNIT");
			extensioninx.setValue(obj.getPackageUnit(), "ALT_UNIT_ISO");
			extensioninx.setValue("X", "NUMERATOR");
			extensioninx.setValue("X", "DENOMINATR");

			// 第二筆資料
			/*
			 * extensioninx.appendRow(); extensioninx.setRow(1);
			 * //extensioninx.setValue("BAPI_TE_MARAX", "STRUCTURE");
			 * extensioninx.setValue("X", "ALT_UNIT");
			 * extensioninx.setValue("X", "ALT_UNIT_ISO");
			 * extensioninx.setValue("X", "DENOMINATR");
			 */

			tableParameterList.setValue(extensioninx, "UNITSOFMEASUREX");
		}

		// UNITSOFMEASUREX LIKE BAPI_MARMX

		// INTERNATIONALARTNOS LIKE BAPI_MEAN
		// MATERIALLONGTEXT LIKE BAPI_MLTX
		JCO.Table materiallongtext = tableParameterList
				.getTable("MATERIALLONGTEXT");
		// 第一筆資料 是Basic data text
		materiallongtext.appendRow();
		materiallongtext.setRow(0);
		materiallongtext.setValue("MATERIAL", "APPLOBJECT");
		materiallongtext.setValue(obj.getItemNum(), "TEXT_NAME");
		materiallongtext.setValue("GRUN", "TEXT_ID");
		materiallongtext.setValue("EN", "LANGU");
		materiallongtext.setValue("EN", "LANGU_ISO");
		materiallongtext.setValue("*", "FORMAT_COL");
		materiallongtext.setValue(obj.getDescription2(), "TEXT_LINE");
		// 第二筆資料 是Internal Comment
		materiallongtext.appendRow();
		materiallongtext.setRow(1);

		materiallongtext.setValue("MATERIAL", "APPLOBJECT");
		materiallongtext.setValue(obj.getItemNum(), "TEXT_NAME");
		materiallongtext.setValue("IVER", "TEXT_ID");
		materiallongtext.setValue("EN", "LANGU");
		materiallongtext.setValue("EN", "LANGU_ISO");
		materiallongtext.setValue("*", "FORMAT_COL");
		materiallongtext.setValue(obj.getOridescription(), "TEXT_LINE");

		tableParameterList.setValue(materiallongtext, "MATERIALLONGTEXT");

		// TAXCLASSIFICATIONS LIKE BAPI_MLAN 20170303 add start
		if(obj.getTaxclass1() != null) {

			JCO.Table taxClassification = tableParameterList.getTable("TAXCLASSIFICATIONS");
		
			taxClassification.appendRow();
			taxClassification.setRow(0);
			taxClassification.setValue("TW", "DEPCOUNTRY");//"TW"
			taxClassification.setValue(obj.getTaxType1(), "TAX_TYPE_1");//"MWST"
			//taxClassification.setValue("MWST", "TAX_TYPE_1");//"MWST"
			taxClassification.setValue(obj.getTaxclass1(), "TAXCLASS_1");//"0"
			//taxClassification.setValue("0", "TAXCLASS_1");//"0"
			tableParameterList.setValue(taxClassification, "TAXCLASSIFICATIONS");
			
		}
		//20170303 add end
		// RETURNMESSAGES LIKE BAPI_MATRETURN2
		// PRTDATA LIKE BAPI_MFHM
		// PRTDATAX LIKE BAPI_MFHMX

		// EXTENSIONIN LIKE BAPIPAREX

		JCO.Table extensionin1 = tableParameterList.getTable("EXTENSIONIN");

		CustomFieldComputer filedComputer = new CustomFieldComputer();
		CustomFieldComputer filedComputer2 = new CustomFieldComputer();

		/*
		 * 放置第六碼, plant_attribute入料檢驗設定, oracle設定免驗,sap有部份欄位需以user所填為準,
		 * 但在plant_attribute有哪些欄位是走這邏輯待北廠告知,故暫且以oracle值為準
		 */
		AttributeType PlantInfo = new AttributeType(PANT_START_POS,
				PANT_LENGTH, obj.getPlantCode()); // 前四碼為廠別
		AttributeType inspectionType = new AttributeType(
				INSPECTIONTYPE_START_POS, INSPECTIONTYPE_LENGTH,
				obj.getPurchasingInspectionType());

		/*
		 * 20141002 BAPI_TE_MARC 的 VALUEPART1 內容裡，第 1~4 是"T003"(廠區)，第5碼是空白(SA
		 * Status)， 第6碼是 "4" (inspection type) ，第7碼開始 30 碼長放的就是 檢驗人資料；
		 * 
		 * 另外 在 BAPI_TE_MARCX 的 VALUEPART1 內容裡，如需更新該欄位，則須放上"X"； 比如第 1~4
		 * 是"T003"(廠區)，第5碼不更新(SA Status)放空白，第6碼要更新(inspection type)放 "X"， 第7碼
		 * 要更新 "檢驗人資料" 放"X"。
		 */
		// ======因為sap中文會自動佔兩個byte 所以之後的每個參數都要往前調整====================
		String empInfo = obj.getName() + " " + obj.getEmpNo() + " "
				+ obj.getExtNo();
		int empLength = (empInfo.getBytes("UTF-8").length - empInfo.length())/2;
		AttributeType empInfoAttributeType = new AttributeType(
				APPLY_EMP_START_POS, APPLY_EMP_LENGTH - empLength, empInfo);

		// 20130708邏輯修改:申請單沒有,則以plant attribute裡的值為主)
		AttributeType consumable = new AttributeType(CONSUMABLE_START_POS
				- empLength, CONSUMABLE_LENGTH, obj.getMcContinuityUse());// 連續性用料放第64碼
		// 連續性用料
		AttributeType period = new AttributeType(PERIOD_START_POS - empLength,
				PERIOD_LENGTH, obj.getMcUseFrequencyLevel());// 耗用頻率放第65-66碼
		// 耗用頻率
		AttributeType usageQuantity = new AttributeType(USAGEQUANTITY_START_POS
				- empLength, USAGEQUANTITY_LENGTH, obj.getMcTotalMinLevel());// 總數量放第67-79碼
		// 總數量
		filedComputer.addAttribute(PlantInfo);// 放入廠別
		filedComputer.addAttribute(inspectionType);
		filedComputer.addAttribute(empInfoAttributeType);
		filedComputer.addAttribute(consumable);
		filedComputer.addAttribute(period);
		filedComputer.addAttribute(usageQuantity);
		
		String filedAffix1 = filedComputer.getFieldValue().substring(
				PANT_START_POS, PANT_LENGTH)
				+ "XXXXXXXX";
		System.out.println(filedComputer.getFieldValue());

		System.out.println(filedAffix1);
		
		
		//=====================戰略管制品 料號：1~18 碼，戰略管制品：第19碼=======================
		filedComputer2.addAttribute(new AttributeType(0,18, obj.getItemNum()));
		filedComputer2.addAttribute(new AttributeType(18,1, obj.getStrategyMgmtFlag()));
		String filedAffix2 = filedComputer2.getFieldValue().substring(0,18)+ "X";
		System.out.println(filedComputer2.getFieldValue());

		System.out.println(filedComputer2);
		
		// 第一筆資料
		extensionin1.appendRow();
		extensionin1.setRow(0);

		extensionin1.setValue("BAPI_TE_MARC", "STRUCTURE");
		extensionin1.setValue(filedComputer.getFieldValue(), "VALUEPART1");
		System.out.println("filedComputer.getFieldValue()-->"
				+ filedComputer.getFieldValue());

		// extensionin1.setValue("T003 許凱宇 10055239 75176", "VALUEPART1");

		// 第二筆資料
		
		  extensionin1.appendRow(); 
		  extensionin1.setRow(1);
		  extensionin1.setValue("BAPI_TE_MARA", "STRUCTURE");
		  extensionin1.setValue(filedComputer2.getFieldValue(), "VALUEPART1");
			System.out.println("filedComputer.getFieldValue()-->"
					+ filedComputer2.getFieldValue());
		 
		
		tableParameterList.setValue(extensionin1, "EXTENSIONIN");
		// EXTENSIONINX LIKE BAPIPAREXX

		JCO.Table extensioninx1 = tableParameterList.getTable("EXTENSIONINX");

		// 用來放plant Attribute的VALUEPART1 組成廠區加1空格+此欄位內容值存入
		// 第一筆資料
		extensioninx1.appendRow();
		extensioninx1.setRow(0);
		extensioninx1.setValue("BAPI_TE_MARCX", "STRUCTURE");
		extensioninx1.setValue(filedAffix1, "VALUEPART1");
		System.out.println("BAPI_TE_MARCX:" + filedAffix1);

		// 第二筆資料
		
		extensioninx1.appendRow(); 
		extensioninx1.setRow(1);
		extensioninx1.setValue("BAPI_TE_MARAX", "STRUCTURE");
		extensioninx1.setValue(filedAffix2,"VALUEPART1");
		System.out.println("BAPI_TE_MARAX:" + filedAffix2);
		
		tableParameterList.setValue(extensioninx1, "EXTENSIONINX");

	}

	private void setImportData(MroToSapIo obj) {
		// set import data
		ParameterList importParameterList = jcoFunction
				.getImportParameterList();
		// HEADDATA LIKE BAPIMATHEAD
		Structure headdata = importParameterList.getStructure("HEADDATA");
		// headdata.setValue("R1101010101", "MATERIAL"); //料號若都為數字前面要補0至18碼
		headdata.setValue(obj.getItemNum(), "MATERIAL"); // 料號若都為數字前面要補0至18碼
		// headdata.setValue(obj.getClassStructureid(), "MATL_TYPE"); //SAP傳檔必要
		headdata.setValue(obj.getMaterialType(), "MATL_TYPE"); // SAP傳檔必要
		headdata.setValue("L", "IND_SECTOR");// SAP傳檔必要
		//20160303 新增拋轉判斷
		if (StringUtils.isNotBlank(obj.getMaterialGroup())) {
			headdata.setValue("X", "BASIC_VIEW");// SAP傳檔必要
		}
		if (StringUtils.isNotBlank(obj.getSdSalesOrganization())) {
			headdata.setValue("X", "SALES_VIEW");// 20130619 SAP傳檔必要佳儒說要加拋
		}
		if (StringUtils.isNotBlank(obj.getPurchasingInspectionType())) {
			headdata.setValue("X", "PURCHASE_VIEW");// SAP傳檔必要
		}
		if (StringUtils.isNotBlank(obj.getMrpType()) &&
			StringUtils.isNotBlank(obj.getMrpProcurementType())) {
			headdata.setValue("X", "MRP_VIEW");// SAP傳檔必要
		}
		if (StringUtils.isNotBlank(obj.getStorageLocation())) {
			headdata.setValue("X", "STORAGE_VIEW");// 20130619 SAP傳檔必要佳儒說要加拋
		}
		if (StringUtils.isNotBlank(obj.getAccountingValuationClass())) {
			headdata.setValue("X", "ACCOUNT_VIEW");// SAP傳檔必要
		}
		if (StringUtils.isNotBlank(obj.getAccountionPriceUnit())) {
			headdata.setValue("X", "COST_VIEW");// SAP傳檔必要
		}

		importParameterList.setValue(headdata, "HEADDATA");

		// CLIENTDATA LIKE BAPI_MARA
		// Structure clientdata = getCLIENTDATA(obj);
		Structure clientdata = importParameterList.getStructure("CLIENTDATA");
		clientdata.setValue(obj.getClassStructureid(), "MATL_GROUP");
		clientdata.setValue(obj.getOrderUnit(), "BASE_UOM");
		clientdata.setValue(obj.getOrderUnit(), "BASE_UOM_ISO");
		clientdata.setValue(obj.getStorageCondition(), "STOR_CONDS");
		clientdata.setValue(obj.getTotalShelfLife(), "SHELF_LIFE");

		clientdata.setValue(obj.getSdDdivision(), "DIVISION");
		clientdata.setValue(obj.getSdMatlGrpPackMatls(), "MAT_GRP_SM");
		clientdata.setValue(obj.getGpSapCode(), "HAZMATPROF");

		// 此欄需大於或等於MINREMLIFE
		clientdata.setValue(obj.getMinShelfLife(), "MINREMLIFE");

		// clientdata.setValue("", "MANU_MAT"); //SAP傳檔必要Mfr part profile

		clientdata.setValue("0000", "MANUF_PROF"); // SAP傳檔必要
		if (!" ".equalsIgnoreCase(obj.getVendor()) || obj.getVendor() != null
				|| obj.getVendor() != " ") {
			clientdata.setValue(obj.getVendor(), "MFR_NO"); // 傳vendor id
			// clientdata.setValue("0009000000", "MFR_NO"); //SAP傳檔必要(測試機用)
		}

		clientdata.setValue(obj.getSecondItemnum(), "OLD_MAT_NO");
		//20170303 add CLIENTDATA-TRANS_GRP
		clientdata.setValue(obj.getTransGrp(), "TRANS_GRP");//"0001"
		//clientdata.setValue("0001", "TRANS_GRP");//"0001"
		
		importParameterList.setValue(clientdata, "CLIENTDATA");
		// CLIENTDATAX LIKE BAPI_MARAX
		// Structure clientdatax = getCLIENTDATAX(obj);
		Structure clientdatax = importParameterList.getStructure("CLIENTDATAX");
		clientdatax.setValue(obj.getClassStructureid(), "MATL_GROUP");
		clientdatax.setValue(obj.getOrderUnit(), "BASE_UOM");
		clientdatax.setValue(obj.getOrderUnit(), "BASE_UOM_ISO");
		clientdatax.setValue(obj.getStorageCondition(), "STOR_CONDS");
		clientdatax.setValue(obj.getTotalShelfLife(), "SHELF_LIFE");

		clientdatax.setValue(obj.getSdDdivision(), "DIVISION");
		clientdatax.setValue(obj.getSdMatlGrpPackMatls(), "MAT_GRP_SM");
		clientdatax.setValue(obj.getGpSapCode(), "HAZMATPROF");

		clientdatax.setValue(obj.getMinShelfLife(), "MINREMLIFE");
		// clientdatax.setValue("", "MANU_MAT"); //SAP傳檔必要Mfr part profile

		clientdatax.setValue("0000", "MANUF_PROF"); // SAP傳檔必要
		if (!" ".equalsIgnoreCase(obj.getVendor()) || obj.getVendor() != null
				|| obj.getVendor() != " ") {
			clientdatax.setValue(obj.getVendor(), "MFR_NO");//
			// clientdatax.setValue("0009000000", "MFR_NO"); //SAP傳檔必要(測試機用)
		}
		clientdatax.setValue(obj.getSecondItemnum(), "OLD_MAT_NO");
		//20170303 add CLIENTDATA-TRANS_GRP
		clientdatax.setValue(obj.getTransGrp(), "TRANS_GRP");//"0001"
		//clientdatax.setValue("0001", "TRANS_GRP");//"0001"        
		
		importParameterList.setValue(clientdatax, "CLIENTDATAX");

		// PLANTDATA LIKE BAPI_MARC

		Structure plantdata = importParameterList.getStructure("PLANTDATA");
		// plantdata.setValue("T003", "PLANT");
		plantdata.setValue(obj.getPlantCode(), "PLANT");
		plantdata.setValue(obj.getDeliverytime(), "PLND_DELRY");
		plantdata.setValue("02", "AVAILCHECK");  //20170303 add 預設值必拋 不改
		//plantdata.setValue(obj.getAvailcheck(), "AVAILCHECK"); //20170303 add
		plantdata.setValue(obj.getSdMaterialFreightGrp(), "MATFRGTGRP");
		plantdata.setValue(obj.getPurchasingGroup(), "PUR_GROUP");
		plantdata.setValue(obj.getMrpGroup(), "MRP_GROUP");
		plantdata.setValue(obj.getMrpType(), "MRP_TYPE");
		plantdata.setValue(obj.getMrpController(), "MRP_CTRLER");
		plantdata.setValue(obj.getMrpSelectionMethod(), "ALT_BOM_ID");
		plantdata.setValue(obj.getMrpLotSize(), "LOTSIZEKEY");
		plantdata.setValue(obj.getMrpProcurementType(), "PROC_TYPE");
		plantdata.setValue(obj.getMrpReorderPoint(), "REORDER_PT");
		plantdata.setValue(obj.getMrpGrprocessingDay(), "GR_PR_TIME");
		plantdata.setValue(obj.getMrpProdStorageLoc(), "ISS_ST_LOC");
		plantdata.setValue(obj.getMrpStorageLocForEp(), "SLOC_EXPRC");
		plantdata.setValue(obj.getMrpSpecialProcurementType(), "SPPROCTYPE");
		plantdata.setValue(obj.getMrpProdSchedProfile(), "PRODPROF");
		plantdata.setValue(obj.getMrpRepetitiveMfg(), "REP_MANUF");
		plantdata.setValue(obj.getMrpRemProfile(), "REPMANPROF");
		plantdata.setValue(obj.getMrpIndividualColl(), "DEP_REQ_ID");
		plantdata.setValue(obj.getMrpStrategyGroup(), "PLAN_STRGP");
		plantdata.setValue(obj.getMrpMixedMrp(), "MIXED_MRP");
		plantdata.setValue(obj.getMrpBackflush(), "BACKFLUSH");
		plantdata.setValue(obj.getMrpInhouseProduction(), "INHSEPRODT");
		plantdata.setValue(obj.getMrpProductionUnit(), "PROD_UNIT");
		plantdata.setValue(obj.getIssueUnit(), "ISSUE_UNIT");
		plantdata.setValue(obj.getStorageBatchManagement(), "BATCH_MGMT");
		plantdata.setValue(obj.getSpecprocuremCosting(), "SPECPROCTY");

		plantdata.setValue(obj.getProfitCtr(), "PROFIT_CTR"); // SAP必傳

		if (obj.getActionType().equals("S")) { // 失效廠區
			plantdata.setValue("06", "PUR_STATUS");
		} else if (obj.getActionType().equals("I")) { // 生效廠區
			plantdata.setValue("", "PUR_STATUS");
		}
        //20170303 add PLANTDATA-LOADINGGRP
		plantdata.setValue(obj.getLoadinggrp(), "LOADINGGRP");//"0001"
		//plantdata.setValue("0001", "LOADINGGRP");//"0001"
		
		importParameterList.setValue(plantdata, "PLANTDATA");

		// PLANTDATAX LIKE BAPI_MARCX

		Structure plantdatax = importParameterList.getStructure("PLANTDATAX");
		// plantdatax.setValue("T003", "PLANT");
		plantdatax.setValue(obj.getPlantCode(), "PLANT");
		plantdatax.setValue(obj.getDeliverytime(), "PLND_DELRY");
		plantdatax.setValue("02", "AVAILCHECK");  //20170303 add 預設值必拋 不改
		plantdatax.setValue(obj.getSdMaterialFreightGrp(), "MATFRGTGRP");
		plantdatax.setValue(obj.getPurchasingGroup(), "PUR_GROUP");
		plantdatax.setValue(obj.getMrpGroup(), "MRP_GROUP");
		plantdatax.setValue(obj.getMrpType(), "MRP_TYPE");
		plantdatax.setValue(obj.getMrpController(), "MRP_CTRLER");
		plantdatax.setValue(obj.getMrpSelectionMethod(), "ALT_BOM_ID");
		plantdatax.setValue(obj.getMrpLotSize(), "LOTSIZEKEY");
		plantdatax.setValue(obj.getMrpProcurementType(), "PROC_TYPE");
		plantdatax.setValue(obj.getMrpReorderPoint(), "REORDER_PT");
		plantdatax.setValue(obj.getMrpGrprocessingDay(), "GR_PR_TIME");
		plantdatax.setValue(obj.getMrpProdStorageLoc(), "ISS_ST_LOC");
		plantdatax.setValue(obj.getMrpStorageLocForEp(), "SLOC_EXPRC");
		plantdatax.setValue(obj.getMrpSpecialProcurementType(), "SPPROCTYPE");
		plantdatax.setValue(obj.getMrpProdSchedProfile(), "PRODPROF");
		plantdatax.setValue(obj.getMrpRepetitiveMfg(), "REP_MANUF");
		plantdatax.setValue(obj.getMrpRemProfile(), "REPMANPROF");
		plantdatax.setValue(obj.getMrpIndividualColl(), "DEP_REQ_ID");
		plantdatax.setValue(obj.getMrpStrategyGroup(), "PLAN_STRGP");
		plantdatax.setValue(obj.getMrpMixedMrp(), "MIXED_MRP");
		plantdatax.setValue(obj.getMrpBackflush(), "BACKFLUSH");
		plantdatax.setValue(obj.getMrpInhouseProduction(), "INHSEPRODT");
		plantdatax.setValue(obj.getMrpProductionUnit(), "PROD_UNIT");
		plantdatax.setValue(obj.getIssueUnit(), "ISSUE_UNIT");
		plantdatax.setValue(obj.getStorageBatchManagement(), "BATCH_MGMT");
		plantdatax.setValue(obj.getSpecprocuremCosting(), "SPECPROCTY");

		plantdatax.setValue(obj.getProfitCtr(), "PROFIT_CTR"); // SAP必傳

		plantdatax.setValue("X", "PUR_STATUS"); // X代表要更新
		
		//20170303 add PLANTDATA-LOADINGGRP
		plantdatax.setValue(obj.getLoadinggrp(), "LOADINGGRP");//"0001"
		//plantdatax.setValue("0001", "LOADINGGRP");//"0001"

		importParameterList.setValue(plantdatax, "PLANTDATAX");

		// FORECASTPARAMETERS LIKE BAPI_MPOP
		// FORECASTPARAMETERSX LIKE BAPI_MPOPX
		// PLANNINGDATA LIKE BAPI_MPGD
		// PLANNINGDATAX LIKE BAPI_MPGDX

		// STORAGELOCATIONDATA LIKE BAPI_MARD
		if (StringUtils.isNotBlank(obj.getStorageLocation())) {
			Structure storagelocationdata = importParameterList
					.getStructure("STORAGELOCATIONDATA");
			storagelocationdata.setValue(obj.getPlantCode(), "PLANT");
			storagelocationdata.setValue(obj.getStorageLocation(), "STGE_LOC");
			importParameterList.setValue(storagelocationdata,
					"STORAGELOCATIONDATA");

			// STORAGELOCATIONDATAX LIKE BAPI_MARDX
			Structure storagelocationdatax = importParameterList
					.getStructure("STORAGELOCATIONDATAX");
			storagelocationdatax.setValue(obj.getPlantCode(), "PLANT");
			storagelocationdatax.setValue(obj.getStorageLocation(), "STGE_LOC");
			importParameterList.setValue(storagelocationdatax,
					"STORAGELOCATIONDATAX");
		}
		// VALUATIONDATA LIKE BAPI_MBEW
		Structure valuationdata = importParameterList
				.getStructure("VALUATIONDATA");

		valuationdata.setValue(obj.getUnitCost(), "STD_PRICE"); // 20140904決議參考單價
																// 依申請單價格
		// valuationdata.setValue("1", "STD_PRICE"); //20130628決議參考單價 暫放1
		valuationdata.setValue(obj.getPlantCode(), "VAL_AREA"); // SAP傳檔必要
		valuationdata.setValue(obj.getAccountingValuationClass(), "VAL_CLASS");
		valuationdata.setValue(obj.getAccountingPriceControl(), "PRICE_CTRL");
		valuationdata.setValue(obj.getAccountionPriceUnit(), "PRICE_UNIT");

		valuationdata.setValue("X", "ORIG_MAT"); // SAP傳檔必要

		importParameterList.setValue(valuationdata, "VALUATIONDATA");
		// VALUATIONDATAX LIKE BAPI_MBEWX
		Structure valuationdatax = importParameterList
				.getStructure("VALUATIONDATAX");
		valuationdatax.setValue(obj.getPlantCode(), "VAL_AREA"); // SAP傳檔必要
		valuationdatax.setValue(obj.getUnitCost(), "STD_PRICE"); // 20140904決議參考單價
																	// 依申請單價格
		// valuationdatax.setValue("1", "STD_PRICE"); //20130628決議參考單價 暫放1
		valuationdatax.setValue(obj.getAccountingValuationClass(), "VAL_CLASS");
		valuationdatax.setValue(obj.getAccountingPriceControl(), "PRICE_CTRL");
		valuationdatax.setValue(obj.getAccountionPriceUnit(), "PRICE_UNIT");

		valuationdatax.setValue("X", "ORIG_MAT"); // SAP傳檔必要

		importParameterList.setValue(valuationdatax, "VALUATIONDATAX");
		// VALUATIONDATAX LIKE BAPI_MBEWX
		// WAREHOUSENUMBERDATA LIKE BAPI_MLGN
		// WAREHOUSENUMBERDATAX LIKE BAPI_MLGNX
		// SALESDATA LIKE BAPI_MVKE

		Structure salesdata = importParameterList.getStructure("SALESDATA");

		salesdata.setValue(obj.getSdSalesOrganization(), "SALES_ORG");
		salesdata.setValue(obj.getSdDistributionChannel(), "DISTR_CHAN");
		salesdata.setValue(obj.getSdAccountAssignmentGroup(), "ACCT_ASSGT");
        //20170303 add SALESDATA-ITEM_CAT
		salesdata.setValue(obj.getItemCat(), "ITEM_CAT");//"NORM"
		//salesdata.setValue("NORM", "ITEM_CAT");//"NORM"
		//20170303 add SALESDATA-MATL_STATS
		salesdata.setValue(obj.getMatlStats(), "MATL_STATS");//"1"
		//salesdata.setValue("1", "MATL_STATS");//"1"
		
		importParameterList.setValue(salesdata, "SALESDATA");
		// SALESDATAX LIKE BAPI_MVKEX

		Structure salesdatax = importParameterList.getStructure("SALESDATAX");
		salesdatax.setValue(obj.getSdSalesOrganization(), "SALES_ORG");
		salesdatax.setValue(obj.getSdDistributionChannel(), "DISTR_CHAN");
		salesdatax.setValue(obj.getSdAccountAssignmentGroup(), "ACCT_ASSGT");
		//20170303 add SALESDATA-ITEM_CAT
		salesdatax.setValue(obj.getItemCat(), "ITEM_CAT");//"NORM"
		//salesdatax.setValue("NORM", "ITEM_CAT");//"NORM"
		//20170303 add SALESDATA-MATL_STATS
		salesdatax.setValue(obj.getMatlStats(), "MATL_STATS");//"1"
		//salesdatax.setValue("1", "MATL_STATS");//"1"

		importParameterList.setValue(salesdatax, "SALESDATAX");

	}

}
