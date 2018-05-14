package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemMappingListDao extends FactoryBaseDAO {

	public ItemMappingListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT * FROM ITEM_MAPPING WHERE OLD_MATNR IS not NULL "
				+ condition
				+ "order by new_matnr ";				
		return queryBySQLToLinkMap(sql, param);
	}
}
