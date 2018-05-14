package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.BuyerItemSupplierListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "BuyerItemSupplierListBean")
@ViewScoped
public class BuyerItemSupplierListBean {
	private transient BuyerItemSupplierListBo bo;
	private String itemtype;
	private String itemnum;
	private String description;
	private String classstructureid;
	private String vendor;
	private Date cStrDate;
	private Date cEndDate;
	private Date uStrDate;
	private Date uEndDate;
	private List list;
	private List<ColumnModel> columns;
	private Map cOption;
	private Map iOption;

	public BuyerItemSupplierListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(BuyerItemSupplierListBo.class);
		cOption=bo.getOption();
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		iOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList( itemtype, itemnum, description,classstructureid, 
				vendor, cStrDate, cEndDate, uStrDate, uEndDate);
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

	public String getClassstructureid() {
		return classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
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

	public Date getuStrDate() {
		return uStrDate;
	}

	public void setuStrDate(Date uStrDate) {
		this.uStrDate = uStrDate;
	}

	public Date getuEndDate() {
		return uEndDate;
	}

	public void setuEndDate(Date uEndDate) {
		this.uEndDate = uEndDate;
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

	public Map getcOption() {
		return cOption;
	}

	public void setcOption(Map cOption) {
		this.cOption = cOption;
	}

	public Map getiOption() {
		return iOption;
	}

	public void setiOption(Map iOption) {
		this.iOption = iOption;
	}

}
