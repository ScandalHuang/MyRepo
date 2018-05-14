package mro.app.alitem.jsf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.alitem.bo.Classstructure2ndListBo;
import mro.base.bo.ClassstructureSecondSourceBO;
import mro.base.entity.ClassstructureSecondSource;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "Classstructure2ndListBean")
@ViewScoped
public class Classstructure2ndListBean {
	private transient Classstructure2ndListBo bo;
	private transient ClassstructureSecondSourceBO csBo;
	private String classstructureid;
	private String assetattrid;
	private List list;
	private List<ColumnModel> columns;
	private Map cOption;

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(Classstructure2ndListBo.class);
		csBo = SpringContextUtil.getBean(ClassstructureSecondSourceBO.class);
		cOption=new LinkedHashMap();
		csBo.getList(null).forEach(l->cOption.put(l.getClassstructureid(), l.getClassstructureid()));
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(classstructureid, assetattrid);
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

	public String getAssetattrid() {
		return assetattrid;
	}

	public void setAssetattrid(String assetattrid) {
		this.assetattrid = assetattrid;
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
