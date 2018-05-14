package mro.app.classstructureSign.Service;

import mro.app.classstructureSign.form.ClassstructureSignForm;

public interface ListClassstructureSignInterface {

	public void onSearch(ClassstructureSignForm classstructureSignForm);
	
	public void onDelete(ClassstructureSignForm classstructureSignForm);

	public void setParameter(ClassstructureSignForm classstructureSignForm);
}
