package mro.app.overview.service;

import mro.app.overview.form.ItemDeptForm;

public interface ListItemDeptInterface {

	public ItemDeptForm onSearch(ItemDeptForm itemDeptForm);
	
	public ItemDeptForm onInsert(String empNO,String deptCode,ItemDeptForm itemDeptForm);
	
	public ItemDeptForm onDelete(ItemDeptForm itemDeptForm);
	
	public String validate(ItemDeptForm itemDeptForm);
}
