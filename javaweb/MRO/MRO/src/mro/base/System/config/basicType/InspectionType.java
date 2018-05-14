package mro.base.System.config.basicType;


public enum InspectionType {
	Type1("1","免驗"), 
	Type2("2","User自驗"); 
	
	private String key;
	private String value;
	
	private InspectionType(String key,String value) {
		this.key =key;
		this.value = value;
	}
	
	public static InspectionType findValue(String key){
		return InspectionType.valueOf(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
