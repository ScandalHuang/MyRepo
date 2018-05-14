package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.AlndomainListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "AlndomainListBean")
@ViewScoped
public class AlndomainListBean {
	private transient AlndomainListBo bo;
	private String classstructureid;
	private String atrid;
	private String description;
	private List list;
	private List<ColumnModel> columns;
	private Map cOption;
	private Map aOption;

	public AlndomainListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(AlndomainListBo.class);
		cOption=bo.getOption();
		aOption=bo.getAssetOption();
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(classstructureid,atrid,description);
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

	public String getAtrid() {
		return atrid;
	}

	public void setAtrid(String atrid) {
		this.atrid = atrid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map getaOption() {
		return aOption;
	}

	public void setaOption(Map aOption) {
		this.aOption = aOption;
	}

}
