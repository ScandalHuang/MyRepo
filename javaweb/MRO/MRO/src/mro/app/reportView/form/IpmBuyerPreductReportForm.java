package mro.app.reportView.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inx.commons.util.SpringContextUtil;

import lombok.Data;
import mro.base.bo.LocationSiteMapBO;


public class IpmBuyerPreductReportForm implements Serializable {
	private static final long serialVersionUID = 2570523590374533130L;
	private List list;
	private String[] organizationCode;
	private String[] locationSite;
	private String classstructureid;
	private String itemnum;
	private Date stDate;
	private Date edDate;
	
	private Map siteMap;
	
	public IpmBuyerPreductReportForm(){
		intial();
		list=new ArrayList();
	}
	
	public void intial(){
	}

	public void setParameter(){
		LocationSiteMapBO locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
		siteMap=locationSiteMapBO.getOption();
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String[] getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String[] organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String[] getLocationSite() {
		return locationSite;
	}

	public void setLocationSite(String[] locationSite) {
		this.locationSite = locationSite;
	}

	public String getClassstructureid() {
		return classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getEdDate() {
		return edDate;
	}

	public void setEdDate(Date edDate) {
		this.edDate = edDate;
	}

	public Map getSiteMap() {
		return siteMap;
	}

	public void setSiteMap(Map siteMap) {
		this.siteMap = siteMap;
	}
	
	
}
