package mro.quartz.job.sapJob.vo;

import java.util.HashMap;

public class MroToSapMespn {
	 
	private String itemNum;
	 
	private String  classStructureid;
	 
	protected HashMap hmPart;
	
	private String  equipmentId;
     
 
	public String getClassStructureid() {
		return classStructureid;
	}
	public void setClassStructureid(String classStructureid) {
		this.classStructureid = classStructureid;
	}
 
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	 
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	/*
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getItemNum());
		sb.append(", ");
		 
		return sb.toString();
	}*/
	protected void setApplicationPart(HashMap hm) {
		this.hmPart = hm;
	}
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	 
	
	
}
