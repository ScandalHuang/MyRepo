package mro.app.overview.dao;

import java.util.List;

import mro.base.entity.ItemDept;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemDeptDAO extends FactoryBaseDAO{
	public ListItemDeptDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getItemDeptList(String condition){
		String sql="select * from ITEM_DEPT where 1=1 "+condition+" order by dump(itemnum) desc";
		return queryBySQLWithEntity(sql, ItemDept.class);
	}
}
