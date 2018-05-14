package mro.app.mcMgmtInterface.service;

import java.util.List;
import java.util.Map;

import com.inx.commons.jsf.GlobalGrowl;

import mro.app.mcMgmtInterface.form.ReorderForm;
import mro.base.entity.Person;

public interface ReOrderInterface {

	public void setParamter(ReorderForm form); //預設參數
	public void search(ReorderForm form,Person person); //reorder query
	public void onReOrderToPR(ReorderForm form,Person person,String userId,GlobalGrowl message); //reorder to pr
	public List onReOrderProcess(Person person,List<Map> list); //reorder計算分配量
}
