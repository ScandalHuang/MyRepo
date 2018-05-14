package mro.app.signProcess.service;

import java.util.Map;

import mro.app.signProcess.form.SignProcessForm;
import mro.base.entity.HrEmp;
import mro.base.entity.SignProcess;
import mro.base.entity.SignProcessList;

public interface SignProcessInterface {
	

	public void onSave(SignProcessForm signProcessForm,String empno);

	public SignProcessForm setParameter(SignProcessForm signProcessForm);
	
	public SignProcessForm mainQuery(SignProcessForm signProcessForm,int Index);
	
	public SignProcessForm setMethodMap(SignProcessForm signProcessForm);
	
	public SignProcessForm onSystemChange(SignProcessForm signProcessForm);

	public SignProcessForm setEmployee(SignProcessForm signProcessForm,HrEmp hremp);

	public SignProcessForm selectSignProcess(SignProcessForm signProcessForm,SignProcess signProcess);
	
	public SignProcessForm deleteSignProcessList(SignProcessForm signProcessForm);
	
	public SignProcessForm addSignProcessList(SignProcessForm signProcessForm);
	
	public SignProcessForm onChangeSequence(SignProcessForm signProcessForm,SignProcessList s,String type);
	
	public void setSignSequenceMap(SignProcessForm signProcessForm);
	
	public <T> Map getMethodMap(Class clazz);
}
