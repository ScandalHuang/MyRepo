package mro.app.classstructureSign.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureItemchangeSign;

public class ClassstructureItemChangeSignForm implements Serializable {
	
	private static final long serialVersionUID = 1559296755971315381L;
	
	private List<ClassstructureItemchangeSign> listClassstructureItemchangeSign;
	private ClassstructureItemchangeSign[] deleteClassstructureItemchangeSign;
	private Map classstructureMap;
	private Map organizationCodeMap;
	private String selectClassstructureid;
	private String selectOrganizationCode;
	
	public ClassstructureItemChangeSignForm(){
		intial();
	}
	
	
	public void intial(){
		listClassstructureItemchangeSign=new ArrayList<>();
		deleteClassstructureItemchangeSign=null;
	}


	public List<ClassstructureItemchangeSign> getListClassstructureItemchangeSign() {
		return listClassstructureItemchangeSign;
	}


	public void setListClassstructureItemchangeSign(
			List<ClassstructureItemchangeSign> listClassstructureItemchangeSign) {
		this.listClassstructureItemchangeSign = listClassstructureItemchangeSign;
	}


	public ClassstructureItemchangeSign[] getDeleteClassstructureItemchangeSign() {
		return deleteClassstructureItemchangeSign;
	}


	public void setDeleteClassstructureItemchangeSign(
			ClassstructureItemchangeSign[] deleteClassstructureItemchangeSign) {
		this.deleteClassstructureItemchangeSign = deleteClassstructureItemchangeSign;
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
