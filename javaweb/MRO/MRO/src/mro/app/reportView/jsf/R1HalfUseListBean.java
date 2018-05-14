package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.R1HalfUseListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R1HalfUseListBean")
@ViewScoped
public class R1HalfUseListBean{
	private transient R1HalfUseListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String deptNo;
	private String itemnum;
	private String location;
	private List list;
	private List<ColumnModel> columns;
	private boolean searchFlag=true;
	
	public R1HalfUseListBean() {
		
	}

	@PostConstruct
	public void init() {
		bo=SpringContextUtil.getBean(R1HalfUseListBo.class);
	}
	
	public void search() {
		if(list!=null)  list.clear();
		list=bo.getList(locationSite, organizationCode,deptNo,itemnum,  location);
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
