package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.ItemSiteSignListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ItemSiteSignListBean")
@ViewScoped
public class ItemSiteSignListBean {
	private transient ItemSiteSignListBo bo;
	private String classstructureid;
	private String locationSite;
	private List list;
	private List<ColumnModel> columns;
	private Map cOption;

	public ItemSiteSignListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(ItemSiteSignListBo.class);
		cOption=bo.getOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(classstructureid,locationSite);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getClassstructureid() {
		return classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
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

	public Map getcOption() {
		return cOption;
	}

	public void setcOption(Map cOption) {
		this.cOption = cOption;
	}

}
