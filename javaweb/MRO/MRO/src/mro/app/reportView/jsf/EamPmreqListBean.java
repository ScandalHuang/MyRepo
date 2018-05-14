package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.EamPmreqListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "EamPmreqListBean")
@ViewScoped
public class EamPmreqListBean {
	private transient EamPmreqListBo bo;
	private String itemnum;
	private String prnum;
	private String deptNo;
	private Date strDate;
	private Date endDate;
	private List list;
	private List<ColumnModel> columns;

	public EamPmreqListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(EamPmreqListBo.class);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(itemnum,prnum,deptNo ,strDate,endDate);
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

	public String getPrnum() {
		return prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public Date getStrDate() {
		return strDate;
	}

	public void setStrDate(Date strDate) {
		this.strDate = strDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
