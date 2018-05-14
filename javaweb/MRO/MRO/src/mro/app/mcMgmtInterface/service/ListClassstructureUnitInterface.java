package mro.app.mcMgmtInterface.service;

import mro.app.mcMgmtInterface.form.ClassstructureUnitForm;

public interface ListClassstructureUnitInterface {

	public ClassstructureUnitForm onSearch(ClassstructureUnitForm classstructureUnitForm);
	
	public ClassstructureUnitForm onDelete(ClassstructureUnitForm classstructureUnitForm);
		
	public ClassstructureUnitForm setParameter(ClassstructureUnitForm classstructureUnitForm);
}
