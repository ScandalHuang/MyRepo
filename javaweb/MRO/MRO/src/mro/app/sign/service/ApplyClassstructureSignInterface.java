package mro.app.sign.service;

import mro.base.entity.Person;
import mro.form.ClassstructureForm;

public interface ApplyClassstructureSignInterface {
	
	public ClassstructureForm getApplySignList(
			ClassstructureForm classstructureForm, String empNo, int activeI);

	public ClassstructureForm setTransferEmployee(
			ClassstructureForm classstructureForm,
			Person person);

	public boolean onTransfer(ClassstructureForm classstructureForm,
			String loginEmpNo);

	public boolean onRejectToNew(ClassstructureForm classstructureForm,
			String loginEmpNo);

	public boolean onAccept(ClassstructureForm classstructureForm,
			String loginEmpNo);

}
