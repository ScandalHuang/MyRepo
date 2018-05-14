package mro.app.system.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.UserLocationSiteMap;

import org.primefaces.model.DualListModel;


public class UserLocationSiteMapForm {
	
	private Person person;
	private LocationSiteMap locationSiteMap; //user  LocationSiteMap
	private List<UserLocationSiteMap> userLocationSiteMaps;  //user location_site
	private List<LocationSiteMap> locationSiteMapALL;  //defualt all site
	private DualListModel<LocationSiteMap> sites;
	
	public UserLocationSiteMapForm(){
		this.inital();
	}
	public void inital() {
		person=null;
		locationSiteMap=null;
		userLocationSiteMaps=new ArrayList<UserLocationSiteMap>();
		sites = new DualListModel<LocationSiteMap>();
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public LocationSiteMap getLocationSiteMap() {
		return locationSiteMap;
	}
	public void setLocationSiteMap(LocationSiteMap locationSiteMap) {
		this.locationSiteMap = locationSiteMap;
	}
	public List<UserLocationSiteMap> getUserLocationSiteMaps() {
		return userLocationSiteMaps;
	}
	public void setUserLocationSiteMaps(
			List<UserLocationSiteMap> userLocationSiteMaps) {
		this.userLocationSiteMaps = userLocationSiteMaps;
	}
	public List<LocationSiteMap> getLocationSiteMapALL() {
		return locationSiteMapALL;
	}
	public void setLocationSiteMapALL(List<LocationSiteMap> locationSiteMapALL) {
		this.locationSiteMapALL = locationSiteMapALL;
	}
	public DualListModel<LocationSiteMap> getSites() {
		return sites;
	}
	public void setSites(DualListModel<LocationSiteMap> sites) {
		this.sites = sites;
	}
	
	
}
