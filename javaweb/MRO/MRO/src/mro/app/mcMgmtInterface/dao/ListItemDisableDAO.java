package mro.app.mcMgmtInterface.dao;

import java.util.List;

import mro.base.entity.ItemDisableLog;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemDisableDAO extends FactoryBaseDAO{
	
	public ListItemDisableDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getItemDisableLogList(String condition){
		String sql="select * from ITEM_DISABLE_LOG where 1=1 "+condition+" order by CREATE_DATE desc";
		return queryBySQLWithEntity(sql, ItemDisableLog.class);
	}
	public int updateEpSystemItems(String itemnum,String disableFlag){
		String sql ="update  epadm.EP_SYSTEM_ITEMS@DBL_ERP_MROUSER "
				+ "set DISABLE_FLAG='"+disableFlag+"'"+
				"where item_code='"+itemnum+"'";
        return modifyBySQL(sql);
	}
	public int updateItem(String itemnum,String disableFlag,String disableFlagRemark){
		String sql ="update  item "
				+ "set DISABLE_FLAG='"+disableFlag+"', DISABLE_FLAG_REMAK='"+disableFlagRemark+"' "+
				"where itemnum='"+itemnum+"'";
        return modifyBySQL(sql);
	}
	
	public int updateInventory(String itemnum,String mccommand){
		String sql ="update  Inventory "
				+ "set MCCOMMAND="+mccommand+" "+
				"where itemnum='"+itemnum+"'";
        return modifyBySQL(sql);
	}
}
