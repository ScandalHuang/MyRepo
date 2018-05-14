package mro.app.mcMgmtInterface.service;

import com.inx.commons.jsf.GlobalGrowl;

public interface ListUpLoadInterface {

	public void onSearch();
	
	public void onDelete(GlobalGrowl message);

	public void onSelectAll();
	
	public void setParameter();
}
