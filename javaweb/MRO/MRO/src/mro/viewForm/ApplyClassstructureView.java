package mro.viewForm;

import java.io.Serializable;

import mro.base.System.config.basicType.ClassstructureApplyType;
import mro.base.entity.ClassstructureHeaderApply;
import mro.form.ClassstructureForm;

import com.inx.commons.util.Utility;

public class ApplyClassstructureView implements Serializable {
	private static final long serialVersionUID = -8902888072155109773L;
	private boolean iDescription;   //敘述為input
	private boolean sDescription;  //敘述為select
	private boolean inactiveDate;  //失效日期 (簽核&查詢)
	
	public ApplyClassstructureView(){
		this.inital();
	}
	public void inital(){
		iDescription=false;
		sDescription=false;
	}
	
	public void setView(ClassstructureForm classstructureForm){
		this.inital();
		ClassstructureHeaderApply header=classstructureForm.getClassstructureHeaderApply();
		
		iDescription=Utility.equals(header.getAction(), 
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ADD);
		sDescription=Utility.equalsOR(header.getAction(), 
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE,
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ACTIVE);
		inactiveDate=Utility.equals(header.getAction(),
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE);
	}
	
	
	public boolean isiDescription() {
		return iDescription;
	}
	public void setiDescription(boolean iDescription) {
		this.iDescription = iDescription;
	}
	public boolean issDescription() {
		return sDescription;
	}
	public void setsDescription(boolean sDescription) {
		this.sDescription = sDescription;
	}
	public boolean isInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(boolean inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	
}
