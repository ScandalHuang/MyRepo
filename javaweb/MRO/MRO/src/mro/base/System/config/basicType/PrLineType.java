package mro.base.System.config.basicType;

import java.util.LinkedHashMap;
import java.util.Map;


public enum PrLineType {
	ALL("全部通過"),  
	PART("部份通過");  
	
private String value;
	
	private PrLineType(String value) {
		this.value = value;
	}
	
	public static Map getMap(){
		Map map=new LinkedHashMap<>();
		for(PrLineType o:PrLineType.values()){
			map.put(o.name(), o.value);
		}
		return map;
	}

	public String getValue() {
		return value;
	}
	
}
