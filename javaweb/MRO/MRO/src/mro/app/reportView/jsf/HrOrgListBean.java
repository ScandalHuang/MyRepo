package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.HrOrgListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "HrOrgListBean")
@ViewScoped
public class HrOrgListBean {
	private transient HrOrgListBo bo;
	private String dept;
	private String manager;
	private String employee;
	private String ext;
	private List list;
	private List<ColumnModel> columns;

	public HrOrgListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(HrOrgListBo.class);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(dept,manager,employee,ext);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
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
