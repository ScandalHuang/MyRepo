package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeInvbalancesForm implements Serializable {
	private static final long serialVersionUID = -9018183247974600987L;
	private List invbalancesList;
	private List filtetInvbalancesList;
	
	private Map selectInvbalances;
	
	public ChangeInvbalancesForm(){
		intial();
	}
	
	public void intial(){
		invbalancesList=new ArrayList<>();
		filtetInvbalancesList=new ArrayList<>();
		selectInvbalances=null;
	}

	public List getInvbalancesList() {
		return invbalancesList;
	}

	public void setInvbalancesList(List invbalancesList) {
		this.invbalancesList = invbalancesList;
	}

	public List getFiltetInvbalancesList() {
		return filtetInvbalancesList;
	}

	public void setFiltetInvbalancesList(List filtetInvbalancesList) {
		this.filtetInvbalancesList = filtetInvbalancesList;
	}

	public Map getSelectInvbalances() {
		return selectInvbalances;
	}

	public void setSelectInvbalances(Map selectInvbalances) {
		this.selectInvbalances = selectInvbalances;
	}
	
	
}
