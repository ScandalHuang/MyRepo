package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.R2ItemMoniterListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R2ItemMoniterListBean")
@ViewScoped
public class R2ItemMoniterListBean {
	private transient R2ItemMoniterListBo bo;
	private String itemnum;
	private String oldMatnr;
	private String name;
	private String status;
	private String supplierFlag;
	private String organizationCode;
	private List list;
	private List<ColumnModel> columns;
	private Map sOption;

	public R2ItemMoniterListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(R2ItemMoniterListBo.class);
		sOption=bo.getOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(itemnum,  oldMatnr, name, status,  supplierFlag, organizationCode);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getOldMatnr() {
		return oldMatnr;
	}

	public void setOldMatnr(String oldMatnr) {
		this.oldMatnr = oldMatnr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(String supplierFlag) {
		this.supplierFlag = supplierFlag;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
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
