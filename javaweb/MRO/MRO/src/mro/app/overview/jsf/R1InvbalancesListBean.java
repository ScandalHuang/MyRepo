package mro.app.overview.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.overview.bo.R1InvbalancesListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R1InvbalancesListBean")
@ViewScoped
public class R1InvbalancesListBean{
	private transient R1InvbalancesListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String location;
	private String itemnum;
	private String description;
	private String deptNo;
	private List list;
	private List<ColumnModel> columns;
	private boolean searchFlag=true;
	
	public R1InvbalancesListBean() {
		
	}

	@PostConstruct
	public void init() {
		bo=SpringContextUtil.getBean(R1InvbalancesListBo.class);
	}
	
	public void search() {
		if(list!=null)  list.clear();
		list=bo.getList(locationSite, organizationCode,  location,
				 itemnum, description, deptNo);
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
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

	public boolean isSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
	}

}
