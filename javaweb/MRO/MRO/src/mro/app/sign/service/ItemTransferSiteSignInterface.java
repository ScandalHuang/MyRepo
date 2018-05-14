package mro.app.sign.service;

import mro.base.entity.Person;
import mro.form.ItemTransferSiteForm;

public interface ItemTransferSiteSignInterface {
	
	public ItemTransferSiteForm setTransferEmployee(ItemTransferSiteForm itemTransferSiteForm,Person person);
	
	public boolean onTransfer(ItemTransferSiteForm itemTransferSiteForm,String loginEmpNo);
	
	public boolean onRejectToNew(ItemTransferSiteForm itemTransferSiteForm,String loginEmpNo);
	
	public boolean onAccept(ItemTransferSiteForm itemTransferSiteForm,String loginEmpNo);
	
}
