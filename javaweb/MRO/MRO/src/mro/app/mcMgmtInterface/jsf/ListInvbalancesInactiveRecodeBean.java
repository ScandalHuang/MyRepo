package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.bo.ApplyInactiveDeptMinLevelBo;
import mro.utility.vo.ColumnModel;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListInvbalancesInactiveRecodeBean")
@ViewScoped
public class ListInvbalancesInactiveRecodeBean implements Serializable {
	private static final long serialVersionUID = 25867384787120379L;
	private List inactiveRecord;
	private List filterInactiveRecord;
	private List<ColumnModel> columns; //三年內失效清單column
	
	public ListInvbalancesInactiveRecodeBean() {

	}

	@PostConstruct
	public void init() {
		this.inital();
		this.getInactiveDeptList();
	}
	
	public void inital(){
		inactiveRecord=new ArrayList<>();
		filterInactiveRecord=new ArrayList<>();
		columns= new ArrayList<ColumnModel>();
	}
	
	public void getInactiveDeptList(){ //三年內失效清單
		ApplyInactiveDeptMinLevelBo applyInactiveDeptMinLevelBo=SpringContextUtil.getBean(ApplyInactiveDeptMinLevelBo.class);
		this.inital();
		inactiveRecord=applyInactiveDeptMinLevelBo.getInactiveDeptList();
		filterInactiveRecord=inactiveRecord;
		if(inactiveRecord.size()>0){
			columns.clear(); 
			Map m=(Map) inactiveRecord.get(0);
			for ( Object key : m.keySet() ) {
		           columns.add(new ColumnModel(key.toString(),key.toString()));  
			}
		}
	}
	
	//============================================================================================


	public List getInactiveRecord() {
		return inactiveRecord;
	}

	public void setInactiveRecord(List inactiveRecord) {
		this.inactiveRecord = inactiveRecord;
	}

	public List getFilterInactiveRecord() {
		return filterInactiveRecord;
	}

	public void setFilterInactiveRecord(List filterInactiveRecord) {
		this.filterInactiveRecord = filterInactiveRecord;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}
}
