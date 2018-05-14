package mro.app.reportView.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.R2LTListBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "R2LTListBean")
@ViewScoped
public class R2LTListBean{
	private transient R2LTListBo bo;
	
	private String description;
	private String itemnum;
	private List list;
	private List<ColumnModel> columns;
	
	public R2LTListBean() {
		
	}

	@PostConstruct
	public void init() {
		bo=SpringContextUtil.getBean(R2LTListBo.class);
	}
	
	public void search() {
		if(list!=null)  list.clear();
		list=bo.getList(itemnum, description);
		if(Utility.isNotEmpty(list) && columns==null){ 
			columns=new ArrayList<ColumnModel>();
			((Map)list.get(0)).keySet().forEach(l->columns.add(new ColumnModel(l.toString(), l.toString())));
		}
	}
	

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
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

}
