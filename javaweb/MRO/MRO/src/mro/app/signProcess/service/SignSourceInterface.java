package mro.app.signProcess.service;

import mro.app.signProcess.form.SignSourceForm;
import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;

public interface SignSourceInterface {

	public void mainQuery(SignSourceForm signSourceForm,int Index);
	
	public void onSelect(SignSourceForm signSourceForm,SignSourceSystem signSourceSystem);
	
	public void onSave(SignSourceForm signSourceForm);
	
	public void addLine(SignSourceForm signSourceForm);
	
	public void deleteLine(SignSourceForm signSourceForm,SignSourceCategory signSourceCategory);
	
}
