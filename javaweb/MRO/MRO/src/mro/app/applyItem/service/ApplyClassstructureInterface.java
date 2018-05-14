package mro.app.applyItem.service;

import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.entity.Person;
import mro.form.ClassstructureForm;

public interface ApplyClassstructureInterface {

	public ClassstructureForm getApplyList(
			ClassstructureForm classstructureForm, Person person, int activeI);

	public ClassstructureForm selectApply(
			ClassstructureForm classstructureForm,
			ClassstructureHeaderApply classstructureHeaderApply);

	public ClassstructureForm onSignPreView(
			ClassstructureForm classstructureForm);

	public void onDelete(ClassstructureForm classstructureForm);

	public ClassstructureForm onAddLine(ClassstructureForm classstructureForm);

	public ClassstructureForm onDeleteLine(
			ClassstructureForm classstructureForm,
			ClassstructureLineApply classstructureLineApply);

	public boolean onApplySave(ClassstructureForm classstructureForm,
			String type);

	public void setSubmit(ClassstructureForm classstructureForm);  //送審前參數設定
	
	public ClassstructureForm setParameter(ClassstructureForm classstructureForm);

	public Map getAlnDomainMap(ClassstructureForm classstructureForm);
	
	public List getClassspecList(ClassstructureForm classstructureForm);
	
	public void getDownLoadFile(ClassstructureForm classstructureForm); // 取得下載檔案
	//==================================簽核參數===============================
	public String getSignParameter(ClassstructureForm classstructureForm);
}
