package mro.base.System.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inx.commons.util.Utility;

public class SystemConfig {

	public static final Map<String,String> PRODUCTION_MAP=Utility.listToMap(
			Arrays.asList("EAM2","EAM3"));
	//========================================================================================
	public static final String uploadpath_stagging="D:\\MROFILE\\";
	public static final String uploadpath_prodution="Y:\\MROFILE\\";
	
	public static final int ApplyFilesizeLimit=1048576;//1MB
	
	public static final int prDeptLevel=40; //需求單位level(部)
	public static final String defaultCurrency="TWD";  //系統預設幣別
	public static final String R2ItemSpec="R2";  //規格型號檢查
	public static final String meterialName="A300"; //料號type
	public static final String makerId="A319"; //makerID
	public static final String R94ItemSpecNonDuplicate="A308,A105";//94規格型號檢查的屬性
    public static final Map reasonCode=Utility.listToMap(Arrays.asList( //申請目的為風險備庫跟例外管理最少簽到總廠長
        "PR_REASONCODE6","PR_REASONCODE7","PR_REASONCODE9","PR_REASONCODE10"));
    public static final Map<String, BigDecimal> highLightCode=new HashMap<String, BigDecimal>(){{//下修控管量
    	put("REASON_TYPE1",new BigDecimal("0"));
    	put("REASON_TYPE10",new BigDecimal("0"));
    	put("REASON_TYPE13",new BigDecimal("0"));
    }};

	public static final String SYSTEMNAME="MRO";
	
//	public static final String REORDER_SITE="TW_ORACLE";  //南科ERP
	 //T0~T2 及N廠區 與南廠現行控管申請單差異
//	public static final Map CONTROL_SITE=Utility.listToMap(Arrays.asList("TW_SAP_T0-T2","TW_SAP_TSG")); 
	
	public static final String LINETYPE_ITEM="ITEM";  //R1申請 LINETYPE

	public static final String ZERS="ZERS";  //存貨
	public static final String ZHIB="ZHIB";  //費用
	
	public static final String LANGCODE_ZH="ZH";  
	public static final String COSTTYPE_F="F"; 
	
	public static final String INVENTORY_CATEGORY_NS="NS"; 
	public static final String INVENTORY_CATEGORY_STK="STK"; 
	//=======================================spec data type=================================================
	public static final String aln="ALN"; 
	public static final String numeric="NUMERIC"; 
	//========================================================================================
	
	public static final String signPreViewUrl="../signView/signPreView.xhtml";
	public static final String signHistoryUrl="../signView/signHistoryMain.xhtml";
	
	public static final String spec_size="A331,A332,A333,A334,A335,A336";
	public static final String validateDescription="R1503,R1508";  //驗證品名
//	public static final String makeDescriptionRule="R18";  //品名不考慮必填非必填
	public static final List itemChangeNonValidate=Arrays.asList(
		        "deptNo","itemnum","AItemAttributeId","organizationCode"
		        ,"createDate" ,"in20","in19","changeby"
		        ,"eauditusername","eaudittransid","status"
		        ,"preEaudittransid","taskId","eaudittimestamp"
		        ,"changeRemark","eaudittype","oriitemnum","secondSourceRemark");
	
	
	public static Map msdsReportMap(){
		return Utility.listToMap(Arrays.asList(
				"R1301","R1302","R1303","R1304",
				"R1305","R1306","R1307","R1308","R1309","R1507"));
	}
	public static Map defaultNonEditSpec(){
		return Utility.listToMap(Arrays.asList("A300","A312"));
	}
}

