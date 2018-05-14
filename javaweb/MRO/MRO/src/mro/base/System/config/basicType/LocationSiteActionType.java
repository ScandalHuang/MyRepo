package mro.base.System.config.basicType;

import java.util.LinkedHashMap;
import java.util.Map;


public enum LocationSiteActionType {

	/*
	 * hongjie.wu
	 * 2015.03.19
	 * 定義可Location site 執行type
	 */
	
	I("區域啟用"),
	S("區域凍結");

	private String value;
	
	private LocationSiteActionType(String value) {
		this.value = value;
	}
	
	public static Map getMap(){
		Map map=new LinkedHashMap<>();
		for(LocationSiteActionType o:LocationSiteActionType.values()){
			map.put(o.name(), o.getValue());
		}
		return map;
	}
	

	public ItemSiteTransferType getTransferType(){
		if(name().equals(LocationSiteActionType.I.name())){
			return ItemSiteTransferType.insert;
		}else if(name().equals(LocationSiteActionType.S.name())){
			return ItemSiteTransferType.stop;
		}
		return null;
	}
	
	public String getMsg(){
		if(name().equals(LocationSiteActionType.I.name())){
			return "區域啟用";
		}else if(name().equals(LocationSiteActionType.S.name())){
			return "區域未啟用";
		}
		return "";
	}
	public boolean validateS(){
		if(name().equals(LocationSiteActionType.S.name())) return true;
		return false;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
