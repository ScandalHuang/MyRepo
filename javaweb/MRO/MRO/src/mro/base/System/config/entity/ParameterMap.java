package mro.base.System.config.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ParameterMap {
	
	private Map<String,String> map;
	private Map<String,String> option;
	
	public  ParameterMap() {
		map=new LinkedHashMap<String,String>();
		option=new TreeMap<String,String>();
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getOption() {
		return option;
	}

	public void setOption(Map<String, String> option) {
		this.option = option;
	}

	
	
}
