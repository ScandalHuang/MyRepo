package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.R2SpecMoniterListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R2SpecMoniterListBean")
@ViewScoped
public class R2SpecMoniterListBean {
	private transient R2SpecMoniterListBo bo;
	private String oldMatnr;
	private String newMatnr;
	private String assetattrid;
	private String aFlag;
	private String supplierFlag;
	private List list;
	private List<ColumnModel> columns;
	private Map bOption;

	public R2SpecMoniterListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(R2SpecMoniterListBo.class);
		bOption=bo.getOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList( oldMatnr, newMatnr, assetattrid,aFlag, supplierFlag);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getOldMatnr() {
		return oldMatnr;
	}

	public void setOldMatnr(String oldMatnr) {
		this.oldMatnr = oldMatnr;
	}

	public String getNewMatnr() {
		return newMatnr;
	}

	public void setNewMatnr(String newMatnr) {
		this.newMatnr = newMatnr;
	}

	public String getAssetattrid() {
		return assetattrid;
	}

	public void setAssetattrid(String assetattrid) {
		this.assetattrid = assetattrid;
	}

	public String getaFlag() {
		return aFlag;
	}

	public void setaFlag(String aFlag) {
		this.aFlag = aFlag;
	}

	public String getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(String supplierFlag) {
		this.supplierFlag = supplierFlag;
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

	public Map getbOption() {
		return bOption;
	}

	public void setbOption(Map bOption) {
		this.bOption = bOption;
	}

}
