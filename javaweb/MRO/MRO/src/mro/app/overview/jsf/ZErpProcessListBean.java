package mro.app.overview.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.overview.bo.ZErpProcessListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ZErpProcessListBean")
@ViewScoped
public class ZErpProcessListBean {
	private transient ZErpProcessListBo bo;
	private String locationSite;
	private String[] organizationCode;
	private String itemtype;
	private String itemnum;
	private String prnum;
	private String description;
	private String subinventory;
	private List list;
	private List<ColumnModel> columns;
	private Map iOption;

	public ZErpProcessListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(ZErpProcessListBo.class);
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		iOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(locationSite,organizationCode,  itemtype, itemnum,
				 prnum, description, subinventory);
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

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getPrnum() {
		return prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubinventory() {
		return subinventory;
	}

	public void setSubinventory(String subinventory) {
		this.subinventory = subinventory;
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

	public String[] getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String[] organizationCode) {
		this.organizationCode = organizationCode;
	}

}
