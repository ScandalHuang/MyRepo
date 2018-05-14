package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.AssetAttributeListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "AssetAttributeListBean")
@ViewScoped
public class AssetAttributeListBean {
	private transient AssetAttributeListBo bo;
	private String assetattrid;
	private String description;
	private List list;
	private List<ColumnModel> columns;
	private Map aOption;
	private Map iOption;

	public AssetAttributeListBean() {

	}

	@PostConstruct
	public void init() {
		bo = SpringContextUtil.getBean(AssetAttributeListBo.class);
	}

	public void search() {
		if (list != null)
			list.clear();
		list = bo.getList(assetattrid,description);
		if (Utility.isNotEmpty(list) && columns == null) {
			columns = new ArrayList<ColumnModel>();
			((Map) list.get(0)).keySet()
					.forEach(
							l -> columns.add(new ColumnModel(l.toString(), l
									.toString())));
		}
	}
	// ==========================================================================================

	public String getAssetattrid() {
		return assetattrid;
	}

	public void setAssetattrid(String assetattrid) {
		this.assetattrid = assetattrid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Map getaOption() {
		return aOption;
	}

	public void setaOption(Map aOption) {
		this.aOption = aOption;
	}

	public Map getiOption() {
		return iOption;
	}

	public void setiOption(Map iOption) {
		this.iOption = iOption;
	}


}
