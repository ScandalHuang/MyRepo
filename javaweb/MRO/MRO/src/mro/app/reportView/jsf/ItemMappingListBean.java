package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.AlndomainListBo;
import mro.app.reportView.bo.ItemMappingListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ItemMappingListBean")
@ViewScoped
public class ItemMappingListBean {
	private transient ItemMappingListBo bo;
	private String newMatnr;
	private String oldMatnr;
	private String locationSite;
	private List list;
	private List<ColumnModel> columns;

	public ItemMappingListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(ItemMappingListBo.class);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(newMatnr,oldMatnr,locationSite);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getNewMatnr() {
		return newMatnr;
	}

	public void setNewMatnr(String newMatnr) {
		this.newMatnr = newMatnr;
	}

	public String getOldMatnr() {
		return oldMatnr;
	}

	public void setOldMatnr(String oldMatnr) {
		this.oldMatnr = oldMatnr;
	}

	public String getLocationSite() {
		return locationSite;
	}

	public void setLocationSite(String locationSite) {
		this.locationSite = locationSite;
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


}
