package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemCostListDao extends FactoryBaseDAO {

	public ItemCostListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT ITEMNUM,DESCRIPTION,GET_UNITCOST(ITEMNUM) UNITCOST "
				+ "FROM ITEM  "
				+ "where 1=1 " + condition;
		return queryBySQLToLinkMap(sql, param);
	}
}
