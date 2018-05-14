package mro.app.classstructureSign.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureItemSign;

public class ClassstructureItemSignForm implements Serializable{
	
	private static final long serialVersionUID = 5912037195994177785L;
	
	private List<ClassstructureItemSign> listClassstructureItemSign;
	private ClassstructureItemSign[] deleteClassstructureItemSign;
	private Map classstructureMap;
	private Map organizationCodeMap;
	private String selectClassstructureid;
	private String selectOrganizationCode;
	
	public ClassstructureItemSignForm(){
		intial();
	}
	
	
	public void intial(){
		listClassstructureItemSign=new ArrayList<>();
		deleteClassstructureItemSign=null;
	}


	public List<ClassstructureItemSign> getListClassstructureItemSign() {
		return listClassstructureItemSign;
	}


	public void setListClassstructureItemSign(
			List<ClassstructureItemSign> listClassstructureItemSign) {
		this.listClassstructureItemSign = listClassstructureItemSign;
	}


	public ClassstructureItemSign[] getDeleteClassstructureItemSign() {
		return deleteClassstructureItemSign;
	}


	public void setDeleteClassstructureItemSign(
			ClassstructureItemSign[] deleteClassstructureItemSign) {
		this.deleteClassstructureItemSign = deleteClassstructureItemSign;
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
