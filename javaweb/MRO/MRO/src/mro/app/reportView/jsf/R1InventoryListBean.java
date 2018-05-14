package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.R1InventoryListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R1InventoryListBean")
@ViewScoped
public class R1InventoryListBean{
	private transient R1InventoryListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String location;
	private String itemnum;
	private List list;
	private List<ColumnModel> columns;
	
	public R1InventoryListBean() {
		
	}

	@PostConstruct
	public void init() {
		bo=SpringContextUtil.getBean(R1InventoryListBo.class);
	}
	
	public void search() {
		if(list!=null)  list.clear();
		list=bo.getList(locationSite, organizationCode,location,itemnum);
		if(Utility.isNotEmpty(list) && columns==null){ 
			columns=new ArrayList<ColumnModel>();
			((Map)list.get(0)).keySet().forEach(l->columns.add(new ColumnModel(l.toString(), l.toString())));
		}
	}
	public Map getOrgs() {
		if(organizationCode!=null) organizationCode.clone();
		return bo.getOrgs(locationSite);
	}
	// ==========================================================================================

	public String getLocationSite() {
		return locationSite;
	}

	public void setLocationSite(String locationSite) {
		this.locationSite = locationSite;
	}

	public String[] getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String[] organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

}
