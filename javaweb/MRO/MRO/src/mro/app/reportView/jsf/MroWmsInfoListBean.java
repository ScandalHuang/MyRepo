package mro.app.reportView.jsf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.MroWmsInfoListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "MroWmsInfoListBean")
@ViewScoped
public class MroWmsInfoListBean {
	private transient MroWmsInfoListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String location;
	private String itemtype;
	private String description;
	private String owerDept;
	private String stockType;
	private BigDecimal stockDay;
	private BigDecimal idleDay;
	private String itemnum;
	private List list;
	private List<ColumnModel> columns;
	private Map iOption;
	private Map sOption;

	public MroWmsInfoListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(MroWmsInfoListBo.class);
		sOption=bo.getStockType();
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		iOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(locationSite,organizationCode, location,
	    		 itemtype, description, owerDept, stockType, stockDay,idleDay, itemnum);
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwerDept() {
		return owerDept;
	}

	public void setOwerDept(String owerDept) {
		this.owerDept = owerDept;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public BigDecimal getStockDay() {
		return stockDay;
	}

	public void setStockDay(BigDecimal stockDay) {
		this.stockDay = stockDay;
	}

	public BigDecimal getIdleDay() {
		return idleDay;
	}

	public void setIdleDay(BigDecimal idleDay) {
		this.idleDay = idleDay;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
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

	public Map getiOption() {
		return iOption;
	}

	public void setiOption(Map iOption) {
		this.iOption = iOption;
	}

	public Map getsOption() {
		return sOption;
	}

	public void setsOption(Map sOption) {
		this.sOption = sOption;
	}

}
