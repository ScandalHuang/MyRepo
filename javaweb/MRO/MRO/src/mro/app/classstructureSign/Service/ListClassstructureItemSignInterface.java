package mro.app.classstructureSign.Service;

import mro.app.classstructureSign.form.ClassstructureItemSignForm;

public interface ListClassstructureItemSignInterface {

	public void onSearch(ClassstructureItemSignForm classstructureItemSignForm);
	
	public void onDelete(ClassstructureItemSignForm classstructureItemSignForm);

	public void setParameter(ClassstructureItemSignForm classstructureItemSignForm);
}
