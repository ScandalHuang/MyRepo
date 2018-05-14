package mro.app.util;

import org.apache.commons.lang3.StringUtils;



public class SystemUtils {
	
	private String action;
	private String[] columnName;
	
	public SystemUtils(String action,String[] columnName){
		this.action=action;
		this.columnName=columnName;
	}
	public SystemUtils(String[] columnName){
		this.columnName=columnName;
	}
	public String[] getColumnName(Object... object){
		String[] s=new String[object.length];
		int i=0;
		for(Object o:object){ 
			if(o!=null){
				s[i]=(String)o;
			}
			i++;
		}
		return s;
	}
	//組合sql condition 
	public StringBuffer getConditions(String[] action,Object... object){
		StringBuffer condition=new StringBuffer();
		int i=0;
		for(Object o:object){ 
			if(o!=null){
				if(o instanceof String ){
					if(StringUtils.isNotBlank(o.toString())){
						condition.append(getCondition(columnName[i],action[i],o));
					}
				}else{
					condition.append(getCondition(columnName[i],action[i],o));
				}
			}
			i++;
		}
		return condition;
	}
	
	//組合sql condition 
	public StringBuffer getConditions(Object... object){
		StringBuffer condition=new StringBuffer();
		int i=0;
		for(Object o:object){ 
			if(o!=null){
				if(o instanceof String ){
					if(StringUtils.isNotBlank(o.toString())){
						condition.append(getCondition(columnName[i],action,o));
					}
				}else{
					condition.append(getCondition(columnName[i],"=",o));
				}
			}
			i++;
		}
		return condition;
	}
	
	//============================================================================================
	private String getCondition(String columnName,String action,Object o){ 
		StringBuffer condition=new StringBuffer();
		if(StringUtils.isBlank(action)){
			action="=";
		}
		if(o.toString().equals("null")){
			condition.append(" and "+getColumnName(columnName)+" is null ");
		}else{
			condition.append(" and "+getColumnName(columnName)+" "+action+" ");
			if(action.equals("like")){
				condition.append("'"+o.toString()+"%' ");
			}else if(action.equals("in")){
				condition.append("("+o.toString()+") ");
			}else {
				condition.append("'"+o.toString()+"' ");
			}
		}
		return condition.toString();
	}
	
	private String getColumnName(String name){
		String[] sName = name.split("(?=[A-Z])");
		StringBuffer newName=new StringBuffer();
		for(String s:sName){
			newName.append(s+"_");
		}
		if(newName.length()>0){
			return newName.toString().substring(0, newName.length()-1);
		}
		return "";
	}

	//============================================================================================
	public void setAction(String action) {
		this.action = action;
	}

	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}
	
	
}
