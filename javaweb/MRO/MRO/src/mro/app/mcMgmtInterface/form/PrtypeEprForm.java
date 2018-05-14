package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrtypeEpr;

public class PrtypeEprForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private PrtypeEpr prtypeEpr;
	private List<PrtypeEpr> list;
	private List<String> locationSites;

	private Map prtypeOption; // prtype選項
	private Map eprRequestedby2TypeO; // prtype選項

	public PrtypeEprForm() {
		this.inital();
	}

	public void inital() {
		prtypeEpr = new PrtypeEpr();
		list = null;
	}

	public void setLocationSites(Collection<LocationSiteMap> list){
		locationSites=list.stream().map(l->l.getLocationSite()).collect(Collectors.toList());
	}
	
	public PrtypeEpr getPrtypeEpr() {
		return prtypeEpr;
	}

	public void setPrtypeEpr(PrtypeEpr prtypeEpr) {
		this.prtypeEpr = prtypeEpr;
	}

	public List<PrtypeEpr> getList() {
		return list;
	}

	public void setList(List<PrtypeEpr> list) {
		this.list = list;
	}

	public Map getPrtypeOption() {
		return prtypeOption;
	}

	public void setPrtypeOption(Map prtypeOption) {
		this.prtypeOption = prtypeOption;
	}

	public Map getEprRequestedby2TypeO() {
		return eprRequestedby2TypeO;
	}

	public void setEprRequestedby2TypeO(Map eprRequestedby2TypeO) {
		this.eprRequestedby2TypeO = eprRequestedby2TypeO;
	}

	public List<String> getLocationSites() {
		return locationSites;
	}

	public void setLocationSites(List<String> locationSites) {
		this.locationSites = locationSites;
	}

}
