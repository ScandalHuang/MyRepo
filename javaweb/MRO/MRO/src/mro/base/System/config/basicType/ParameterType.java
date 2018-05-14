package mro.base.System.config.basicType;

import java.util.Map;

import mro.base.System.config.entity.ParameterMap;
import mro.base.System.config.jsf.SystemConfigBean;

import org.apache.commons.collections.map.HashedMap;

import com.inx.commons.util.JsfContextUtil;

public enum ParameterType {
	ATTRIBUTE_DATA_TYPE,
	BSS_TYPE,
	CLASSSTRUCTURE_APPLY_ACTION,
	GP_DELIVERY_TYPE,
	GP_PRODUCT_CATEGORY,
	GP_REMAIN_TYPE,
	R1_HIGHLIGHT_REASON_TYPE,
	R2_HIGHLIGHT_REASON_TYPE,
	INSPECTION_TYPE,
	ITEM_CATEGORY,
	MC_USE_FREQUENCY_TYPE,
	PROCESS_CATEGORY,
	PRPRIORITY,
	PRTYPE,
	R2_PMREQ_REASONCODE,
	REASONCODE,
	SAP_STATUS,
	SAP_STORE_CATEGORY,
	STOCK_CATEGORY,
	STORAGE_CONIDTION,
	BUDGET_TYPE,
	SECOND_ITEM_TYPE,
	EPR_REQUESTEDBY2,
	CONTROL_TYPE;
	
	public static ParameterType getEnum(String value){
		return ParameterType.valueOf(value);
	}
	
	public  Map getOption(){
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		return bean.getParameterOption().get(ParameterType.valueOf(name()));
	}
	
	public static String getParameterValue(String parameterKey){
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		return bean.getParameterMap().get(parameterKey);
	}
	
	public static Map getInitialMap(){
		Map<ParameterType,ParameterMap> map=new HashedMap();
		for(ParameterType p:ParameterType.values()){
			map.put(p, new ParameterMap());
		}
		return map;
	}
	public static Map<ParameterType,Map> getInitialMap2(){
		Map<ParameterType,Map> map=new HashedMap();
		for(ParameterType p:ParameterType.values()){
			map.put(p, new HashedMap());
		}
		return map;
	}
}
