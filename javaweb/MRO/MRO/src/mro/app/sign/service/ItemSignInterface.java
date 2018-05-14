package mro.app.sign.service;

import mro.base.entity.Person;
import mro.form.ItemForm;

public interface ItemSignInterface {
	
	public ItemForm setTransferEmployee(ItemForm itemForm,Person person);
	
	public boolean onTransfer(ItemForm itemForm,String loginEmpNo);
	
	public boolean onRejectToNew(ItemForm itemForm,String loginEmpNo);
	
	public boolean onAccept(ItemForm itemForm,String loginEmpNo);
	
	public void onFinalApprNoSign(ItemForm itemForm);
}
