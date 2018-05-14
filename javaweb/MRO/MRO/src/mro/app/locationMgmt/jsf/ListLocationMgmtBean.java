package mro.app.locationMgmt.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.locationMgmt.bo.ListLocationMgmtBO;
import mro.app.locationMgmt.utils.ListLocationMgmtUtils;
import mro.base.bo.LocationMapBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.primefaces.event.RowEditEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

/*
 * 
 * 
 * Create Date: 2013/12/27
 * Author     : hongjie.wu
 * Purpose    : Location Info Manage Interface
 * 
 */

@ManagedBean(name = "ListLocationMgmtBean")
@ViewScoped
public class ListLocationMgmtBean implements Serializable{
	private static final long serialVersionUID = 4350005869791759513L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private List<LocationMap> listLocationMap;
	private List<LocationMap> filterListLocationMap;
	private String addOrganizationCode;
	
	private List<LocationSiteMap> listLocationSiteMap;
	
	private transient LocationMapBO locationMapBO;
	private transient LocationSiteMapBO locationSiteMapBO;
	private transient ListLocationMgmtBO listLocationMgmtBO;
	
	public ListLocationMgmtBean(){
		
	}
	
	@PostConstruct
	public void init() {
		locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
		listLocationMgmtBO=SpringContextUtil.getBean(ListLocationMgmtBO.class);
		queryLocationMap();
		getLocationSiteMapList();
		addOrganizationCode="";
	}
	
	
	/*
	 * Purpose    : 取得全部的LocationMap
	 */
	public void queryLocationMap(){	
		listLocationMap=locationMapBO.getLocationAllList();		
		filterListLocationMap=listLocationMap;
	}
	/*
	 * Purpose    : 取得全部的LOCATION_SITE_MAP
	 */
	public void getLocationSiteMapList(){	
		listLocationSiteMap=locationSiteMapBO.getLocationSiteMapList();
	}
	/*
	 * Purpose    : 新增Organization Name
	 */
    public void setOrganizationCode() {  
    	GlobalGrowl message = new GlobalGrowl();	
    	String warnMessage=ListLocationMgmtUtils.validate(addOrganizationCode);
    	if(warnMessage.length()==0){
	    	LocationMap locationMap=new LocationMap();
	    	locationMap.setOrganizationCode(addOrganizationCode);
	    	locationMap.setSiteId(addOrganizationCode);
	    	listLocationMgmtBO.updateLocationMap(locationMap,loginInfoBean.getEmpNo());
	    	queryLocationMap();
	    	addOrganizationCode="";
	    	message.addInfoMessage("Insert", "Insert successful.");
    	}else{
    		message.addErrorMessage("Error", warnMessage);
    	}
    }
    	
	/*
	 * Purpose    : 更新Row Select
	 */
    public void onEdit(RowEditEvent event) {  
    	GlobalGrowl message = new GlobalGrowl();	
    	LocationMap locationMap=(LocationMap) event.getObject();
    	listLocationMgmtBO.updateLocationMap(locationMap,loginInfoBean.getEmpNo());
//    	queryLocationMap();
    	message.addInfoMessage("Update", "Update successful.");
    }
	/*
	 * Purpose    : 更新Location_site_map Select
	 */
	public void updateLocationSiteMap(RowEditEvent event) { 
		GlobalGrowl message = new GlobalGrowl();
		listLocationMgmtBO.updateLocationSiteMap((LocationSiteMap)event.getObject(),loginInfoBean.getEmpNo());
		getLocationSiteMapList();
		message.addInfoMessage("Upload", "Upload successful!");
	}
	//=================================================================================
	public List<LocationMap> getListLocationMap() {
		return listLocationMap;
	}

	public void setListLocationMap(List<LocationMap> listLocationMap) {
		this.listLocationMap = listLocationMap;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public String getAddOrganizationCode() {
		return addOrganizationCode;
	}

	public void setAddOrganizationCode(String addOrganizationCode) {
		this.addOrganizationCode = addOrganizationCode;
	}

	public List<LocationMap> getFilterListLocationMap() {
		return filterListLocationMap;
	}

	public void setFilterListLocationMap(List<LocationMap> filterListLocationMap) {
		this.filterListLocationMap = filterListLocationMap;
	}

	public List<LocationSiteMap> getListLocationSiteMap() {
		return listLocationSiteMap;
	}

	public void setListLocationSiteMap(List<LocationSiteMap> listLocationSiteMap) {
		this.listLocationSiteMap = listLocationSiteMap;
	}	
}
