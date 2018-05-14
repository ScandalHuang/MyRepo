package mro.viewForm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.form.PrForm;

import com.inx.commons.util.Utility;

public class PrView implements Serializable {
	
	private static final long serialVersionUID = -6221919750788205521L;
	
	private boolean cancelButton;
	//=============list prline=========================
	private boolean pmreqqty; 
	private boolean minlevel;
	private boolean newminlevel;
	
	//=============list close prline=========================
	private boolean cPmreqqty; 
	private boolean cMinlevel;
	private boolean cNewminlevel;
	private Map<BigDecimal,Boolean> closeRemarkMap;
	private Map<BigDecimal,Boolean> combineRemarkMap;
	
	public PrView(){
		this.inital();
	}
	public void inital(){
		cancelButton=false;
		pmreqqty=true;
		minlevel=false;
		newminlevel=false;

		cPmreqqty=pmreqqty;
		cMinlevel=minlevel;
		cNewminlevel=newminlevel;
		closeRemarkMap=new HashMap<BigDecimal, Boolean>();
		combineRemarkMap=new HashMap<BigDecimal, Boolean>();
	}
	
	public void setView(PrForm prForm){
		this.inital();
		Pr pr = prForm.getPr();
		cancelButton = Utility.equals(pr.getStatus(),SignStatus.REJECT);
		
		pmreqqty = !Utility.equalsOR(prForm.getPr().getPrtype(),PrType.R1CONTROL,PrType.R2CONTROL);
		cPmreqqty = pmreqqty;
		minlevel = Utility.equalsOR(prForm.getPr().getPrtype(),PrType.R1CONTROL,PrType.R2CONTROL);
		cMinlevel= minlevel;
		newminlevel = Utility.equals(prForm.getPr().getPrtype(),PrType.R1CONTROL);
		cNewminlevel=newminlevel;
	}
	public void setCloseRemark(Prline prline){
		closeRemarkMap.put(prline.getPrlineid(), true);
	}	
	public void removeCloseRemark(Prline prline){
		closeRemarkMap.remove(prline.getPrlineid());
	}
	public void setCombineRemark(Prline prline){
		combineRemarkMap.put(prline.getPrlineid(), true);
	}	
	public void removeCombineRemark(Prline prline){
		combineRemarkMap.remove(prline.getPrlineid());
	}
	public boolean isCancelButton() {
		return cancelButton;
	}
	public boolean isPmreqqty() {
		return pmreqqty;
	}
	public boolean isMinlevel() {
		return minlevel;
	}
	public boolean isNewminlevel() {
		return newminlevel;
	}
	public boolean iscPmreqqty() {
		return cPmreqqty;
	}
	public boolean iscMinlevel() {
		return cMinlevel;
	}
	public boolean iscNewminlevel() {
		return cNewminlevel;
	}
	public Map<BigDecimal, Boolean> getCloseRemarkMap() {
		return closeRemarkMap;
	}
	public Map<BigDecimal, Boolean> getCombineRemarkMap() {
		return combineRemarkMap;
	}
	
}
