package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.MroPmreqListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "MroPmreqListBean")
@ViewScoped
public class MroPmreqListBean {
	private transient MroPmreqListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String itemnum;
	private String prtype;
	private String prnum;
	private String status;
	private String requestby2;
	private Date cStrDate;
	private Date cEndDate;
	private String deptNo;
	private Date sStrDate;
	private Date sEndDate;
	String sName;
	private List list;
	private List<ColumnModel> columns;
	private Map sOption;

	public MroPmreqListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(MroPmreqListBo.class);
		sOption=bo.getStatus();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList( locationSite,organizationCode, itemnum,
	    		 prtype, prnum, status, requestby2,cStrDate, cEndDate, 
	    		 deptNo, sStrDate, sEndDate,sName);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
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

	public String getPrtype() {
		return prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	public String getPrnum() {
		return prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestby2() {
		return requestby2;
	}

	public void setRequestby2(String requestby2) {
		this.requestby2 = requestby2;
	}

	public Date getcStrDate() {
		return cStrDate;
	}

	public void setcStrDate(Date cStrDate) {
		this.cStrDate = cStrDate;
	}

	public Date getcEndDate() {
		return cEndDate;
	}

	public void setcEndDate(Date cEndDate) {
		this.cEndDate = cEndDate;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public Date getsStrDate() {
		return sStrDate;
	}

	public void setsStrDate(Date sStrDate) {
		this.sStrDate = sStrDate;
	}

	public Date getsEndDate() {
		return sEndDate;
	}

	public void setsEndDate(Date sEndDate) {
		this.sEndDate = sEndDate;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
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

	public Map getsOption() {
		return sOption;
	}

	public void setsOption(Map sOption) {
		this.sOption = sOption;
	}

}
