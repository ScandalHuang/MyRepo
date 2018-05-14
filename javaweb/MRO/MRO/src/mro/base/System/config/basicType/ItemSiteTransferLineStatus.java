package mro.base.System.config.basicType;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ItemSiteTransferLineStatus {
	S("更新成功"), 
	E("更新失敗"),  
	N("待更新"); 
	

	private String value;
	

	private ItemSiteTransferLineStatus(String value) {
		this.value = value;
	}
	
	public static Map getMap(){
		Map map=new LinkedHashMap<>();
		for(ItemSiteTransferLineStatus o:ItemSiteTransferLineStatus.values()){
			map.put(o.name(), o.value);
		}
		return map;
	}
	
	public String getValue() {
        return value;
    }
}
