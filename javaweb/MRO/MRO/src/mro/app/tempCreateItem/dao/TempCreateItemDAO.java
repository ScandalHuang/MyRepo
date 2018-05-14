package mro.app.tempCreateItem.dao;

import java.util.List;

import mro.base.entity.AItem;
import mro.base.entity.Item;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

/**
 * @author hongjie.wu
 *
 */
public class TempCreateItemDAO extends FactoryBaseDAO{
	
	public TempCreateItemDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	public List getItemList(String condition){
		String sql="select * from Item  " +
				"where  1=1 " +condition ;
		return queryBySQLWithEntity(sql, Item.class);
	}
	
	public List getAItemList(String condition){
		String sql="select * from A_Item  " +
				"where  1=1 " +condition +" order by EAUDITTRANSID";
		return queryBySQLWithEntity(sql, AItem.class);
	}
}
