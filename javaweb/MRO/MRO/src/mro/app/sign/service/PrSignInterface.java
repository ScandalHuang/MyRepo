package mro.app.sign.service;

import java.util.Map;

import mro.base.entity.Person;
import mro.form.ItemForm;
import mro.form.PrForm;

public interface PrSignInterface {
	
	public PrForm setlistUnSignPr(PrForm prForm,String loginEmpNo);
	
	public PrForm setTransferEmployee(PrForm prForm,Person person);
	
	public boolean onTransfer(PrForm prForm,String loginEmpNo);
	
	public boolean onRejectToNew(PrForm prForm,String loginEmpNo);

	
	public boolean onCanSign(PrForm prForm,String loginEmpNo); //簽核通過後,在轉單時取消該申請單
	
	public boolean onCan(PrForm prForm,String loginEmpNo);
	
	public boolean onAccept(PrForm prForm,String loginEmpNo);
	
	public boolean onMultiAppr(PrForm prForm,String loginEmpNo);
	
	public void onFinalApprNoSign(PrForm prForm);  //直接finally appr 不需簽核

	//===================================開單人員姓名============================================
	public Map<String, String> requestedby2NameMap(PrForm prForm);
	
	//拋轉時取消LINE
	public boolean onClosePrLine(PrForm prForm);
	public boolean onCombinePrLine(PrForm prForm);
}
