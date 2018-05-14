package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.InprgPmreqListBo;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "InprgPmreqListBean")
@ViewScoped
public class InprgPmreqListBean {
	private transient InprgPmreqListBo bo;
	private String prtype;
	private String itemnum;
	private String sName;
	private String resoncode;
	private List list;
	private List<ColumnModel> columns;
	private Map sOption;

	public InprgPmreqListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(InprgPmreqListBo.class);
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		sOption=bean.getParameterOption().get(ParameterType.R2_PMREQ_REASONCODE);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList( prtype, itemnum, sName, resoncode);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getPrtype() {
		return prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getResoncode() {
		return resoncode;
	}

	public void setResoncode(String resoncode) {
		this.resoncode = resoncode;
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
