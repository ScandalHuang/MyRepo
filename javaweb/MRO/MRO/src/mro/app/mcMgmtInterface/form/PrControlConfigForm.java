package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrControlConfig;

public class PrControlConfigForm  implements Serializable {

	private static final long serialVersionUID = 1L;
	private PrControlConfig prControlConfig;
	private List<PrControlConfig> list;
	private List<String> locationSites;
	
	private Map groupOption; //料號大類別選項
	private Map controlOption;
	
	public PrControlConfigForm(){
		this.inital();
	}
	
	public void inital() {
		prControlConfig=new PrControlConfig();
		list=null;
	}

	public void setLocationSites(Collection<LocationSiteMap> list){
		locationSites=list.stream().map(l->l.getLocationSite()).collect(Collectors.toList());
	}
	public PrControlConfig getPrControlConfig() {
		return prControlConfig;
	}

	public void setPrControlConfig(PrControlConfig prControlConfig) {
		this.prControlConfig = prControlConfig;
	}

	public List<PrControlConfig> getList() {
		return list;
	}

	public void setList(List<PrControlConfig> list) {
		this.list = list;
	}

	public Map getGroupOption() {
		return groupOption;
	}

	public void setGroupOption(Map groupOption) {
		this.groupOption = groupOption;
	}

	public Map getControlOption() {
		return controlOption;
	}

	public void setControlOption(Map controlOption) {
		this.controlOption = controlOption;
	}

	public List<String> getLocationSites() {
		return locationSites;
	}

	public void setLocationSites(List<String> locationSites) {
		this.locationSites = locationSites;
	}
	
}


