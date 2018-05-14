package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.PrtypeBudgetListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "PrtypeBudgetListBean")
@ViewScoped
public class PrtypeBudgetListBean {
	private transient PrtypeBudgetListBo bo;
	private String deptNo;
	private String budgetType;
	private String budgetMonth;
	private List list;
	private List<ColumnModel> columns;
	private Map bOption;

	public PrtypeBudgetListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(PrtypeBudgetListBo.class);
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		bOption=bean.getParameterOption().get(ParameterType.BUDGET_TYPE);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(deptNo,budgetType,budgetMonth);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}


	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	public String getBudgetMonth() {
		return budgetMonth;
	}

	public void setBudgetMonth(String budgetMonth) {
		this.budgetMonth = budgetMonth;
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
