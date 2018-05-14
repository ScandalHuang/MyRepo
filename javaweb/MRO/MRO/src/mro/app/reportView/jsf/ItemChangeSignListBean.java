package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.ItemChangeSignListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ItemChangeSignListBean")
@ViewScoped
public class ItemChangeSignListBean {
	private transient ItemChangeSignListBo bo;
	private String classstructureid;
	private String organizationCode;
	private List list;
	private List<ColumnModel> columns;
	private Map cOption;

	public ItemChangeSignListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(ItemChangeSignListBo.class);
		cOption=bo.getOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(classstructureid,organizationCode);
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

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

}
