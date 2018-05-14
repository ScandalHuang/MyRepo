package mro.app.classstructureSign.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureSign;

public class ClassstructureSignForm implements Serializable{
	
	private static final long serialVersionUID = -3301890723821889211L;
	
	private List<ClassstructureSign> listClassstructureSign;
	private ClassstructureSign[] deleteClassstructureSign;
	private Map classstructureMap;
	private Map organizationCodeMap;
	private String selectClassstructureid;
	private String selectOrganizationCode;
	
	public ClassstructureSignForm(){
		intial();
	}
	
	
	public void intial(){
		listClassstructureSign=new ArrayList<>();
		deleteClassstructureSign=null;
	}


	public List<ClassstructureSign> getListClassstructureSign() {
		return listClassstructureSign;
	}


	public void setListClassstructureSign(
			List<ClassstructureSign> listClassstructureSign) {
		this.listClassstructureSign = listClassstructureSign;
	}


	public ClassstructureSign[] getDeleteClassstructureSign() {
		return deleteClassstructureSign;
	}


	public void setDeleteClassstructureSign(
			ClassstructureSign[] deleteClassstructureSign) {
		this.deleteClassstructureSign = deleteClassstructureSign;
	}


	public Map getClassstructureMap() {
		return classstructureMap;
	}


	public void setClassstructureMap(Map classstructureMap) {
		this.classstructureMap = classstructureMap;
	}


	public Map getOrganizationCodeMap() {
		return organizationCodeMap;
	}


	public void setOrganizationCodeMap(Map organizationCodeMap) {
		this.organizationCodeMap = organizationCodeMap;
	}


	public String getSelectClassstructureid() {
		return selectClassstructureid;
	}


	public void setSelectClassstructureid(String selectClassstructureid) {
		this.selectClassstructureid = selectClassstructureid;
	}


	public String getSelectOrganizationCode() {
		return selectOrganizationCode;
	}


	public void setSelectOrganizationCode(String selectOrganizationCode) {
		this.selectOrganizationCode = selectOrganizationCode;
	}

}
