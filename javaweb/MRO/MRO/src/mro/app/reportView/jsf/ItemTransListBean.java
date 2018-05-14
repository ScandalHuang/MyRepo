package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.ItemTransListBo;
import mro.utility.DateUtils;
import mro.utility.vo.ColumnModel;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ItemTransListBean")
@ViewScoped
public class ItemTransListBean{
	private transient ItemTransListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String itemnum;
	private String deptNo;
	private String sourceType;
	private String refno;
	private Date strDate;
	private Date endDate;
	private List list;
	private List<ColumnModel> columns;
	
	public ItemTransListBean() {
		
	}

	@PostConstruct
	public void init() {
		bo=SpringContextUtil.getBean(ItemTransListBo.class);
	}
	
	public void search() {
		if(DateUtils.subtractDay(strDate, endDate)>365){
			GlobalGrowl msg=new GlobalGrowl();
			msg.addErrorMessage("Error!","查詢交易時間不得超過1年");
			return ;
		}
			
		if(list!=null)  list.clear();
		list=bo.getList(locationSite, organizationCode, itemnum,
	    		 deptNo, sourceType, refno,strDate, endDate);
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

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public Date getStrDate() {
		return strDate;
	}

	public void setStrDate(Date strDate) {
		this.strDate = strDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
