package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.ItemListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ItemListBean")
@ViewScoped
public class ItemListBean {
	private transient ItemListBo bo;
	private String itemtype;
	private String classstructureid;
	private String itemnum;
	private String storageCategory;
	private String description;
	private String locationSite;
	private Date cStrDate;
	private Date cEndDate;
	private String sLocationSite;
	private String secondItem;
	private List list;
	private List<ColumnModel> columns;
	private Map iOption;
	private Map cOption;
	private Map sOption;

	public ItemListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(ItemListBo.class);
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		iOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
		sOption=bean.getParameterOption().get(ParameterType.SAP_STORE_CATEGORY);
		cOption=bo.getOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList( itemtype, classstructureid,
	    		 itemnum, storageCategory, 
	    		 description, locationSite, cStrDate,
	    		 cEndDate, sLocationSite, secondItem);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getClassstructureid() {
		return classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getStorageCategory() {
		return storageCategory;
	}

	public void setStorageCategory(String storageCategory) {
		this.storageCategory = storageCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocationSite() {
		return locationSite;
	}

	public void setLocationSite(String locationSite) {
		this.locationSite = locationSite;
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

	public String getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

	public String getSecondItem() {
		return secondItem;
	}

	public void setSecondItem(String secondItem) {
		this.secondItem = secondItem;
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

	public Map getcOption() {
		return cOption;
	}

	public void setcOption(Map cOption) {
		this.cOption = cOption;
	}

	public Map getsOption() {
		return sOption;
	}

	public void setsOption(Map sOption) {
		this.sOption = sOption;
	}
}
