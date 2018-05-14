package mro.app.classstructureSign.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureItemSiteSign;

public class ClassstructureItemSiteSignForm implements Serializable{
	
	private static final long serialVersionUID = -4951165630803963559L;
	
	private List<ClassstructureItemSiteSign> listClassstructureItemSiteSign;
	private ClassstructureItemSiteSign[] deleteClassstructureItemSiteSign;
	
	private Map classstructureMap;
	private String selectClassstructureid;
	private String selectLocationSite;
	
	public ClassstructureItemSiteSignForm(){
		intial();
	}
	
	
	public void intial(){
		listClassstructureItemSiteSign=new ArrayList<>();
		deleteClassstructureItemSiteSign=null;
	}


	public List<ClassstructureItemSiteSign> getListClassstructureItemSiteSign() {
		return listClassstructureItemSiteSign;
	}


	public void setListClassstructureItemSiteSign(
			List<ClassstructureItemSiteSign> listClassstructureItemSiteSign) {
		this.listClassstructureItemSiteSign = listClassstructureItemSiteSign;
	}


	public ClassstructureItemSiteSign[] getDeleteClassstructureItemSiteSign() {
		return deleteClassstructureItemSiteSign;
	}


	public void setDeleteClassstructureItemSiteSign(
			ClassstructureItemSiteSign[] deleteClassstructureItemSiteSign) {
		this.deleteClassstructureItemSiteSign = deleteClassstructureItemSiteSign;
	}


	public Map getClassstructureMap() {
		return classstructureMap;
	}


	public void setClassstructureMap(Map classstructureMap) {
		this.classstructureMap = classstructureMap;
	}


	public String getSelectClassstructureid() {
		return selectClassstructureid;
	}


	public void setSelectClassstructureid(String selectClassstructureid) {
		this.selectClassstructureid = selectClassstructureid;
	}


	public String getSelectLocationSite() {
		return selectLocationSite;
	}


	public void setSelectLocationSite(String selectLocationSite) {
		this.selectLocationSite = selectLocationSite;
	}

	
}
