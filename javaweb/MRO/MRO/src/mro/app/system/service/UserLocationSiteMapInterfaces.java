package mro.app.system.service;

import com.inx.commons.jsf.GlobalGrowl;

import mro.app.system.form.UserLocationSiteMapForm;
import mro.base.entity.Person;

public interface UserLocationSiteMapInterfaces {
	/** /載入時預載參數*/
	public void setParameter(UserLocationSiteMapForm form); 
	
	/** /載入選取user*/
	public void setEmployee(UserLocationSiteMapForm form,Person person);
	
	/** /載入選取user*/
	public void setUserLocationMenu(UserLocationSiteMapForm form);
	
	/** /載入選取user*/
	public void update(UserLocationSiteMapForm form,Person login,GlobalGrowl message);
}
