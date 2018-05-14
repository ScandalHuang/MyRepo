package mro.app.mcMgmtInterface.service;

import mro.app.mcMgmtInterface.form.PrControlConfigForm;
import mro.base.entity.Person;
import mro.base.entity.PrControlConfig;

import com.inx.commons.jsf.GlobalGrowl;


public interface PrControlConfigInterface {
	
	public void setParameter(PrControlConfigForm form);
	/** delete  */
	public void onDelete(PrControlConfigForm form,PrControlConfig prControlConfig,GlobalGrowl msg);
	
	/** save  */
	public void onSave(PrControlConfigForm form,Person person,GlobalGrowl msg);

	/** select */
	public void onSelect(PrControlConfigForm form);
	
	/** Query */
	public void query(PrControlConfigForm form);
	
	/** 驗證 */
	public boolean validation(PrControlConfig prControlConfig,GlobalGrowl msg);
}
