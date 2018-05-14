package mro.app.mcMgmtInterface.service;

import mro.app.mcMgmtInterface.form.PrtypeEprForm;
import mro.base.entity.Person;
import mro.base.entity.PrtypeEpr;

import com.inx.commons.jsf.GlobalGrowl;


public interface PrtypeEprInterface {
	
	public void setParameter(PrtypeEprForm form);
	/** delete  */
	public void onDelete(PrtypeEprForm form,PrtypeEpr prtypeEpr,GlobalGrowl msg);
	
	/** save  */
	public void onSave(PrtypeEprForm form,Person person,GlobalGrowl msg);

	/** select */
	public void onSelect(PrtypeEprForm form);
	
	/** Query */
	public void query(PrtypeEprForm form);
	
	/** 驗證 */
	public boolean validation(PrtypeEpr prtypeEpr,GlobalGrowl msg);
}
