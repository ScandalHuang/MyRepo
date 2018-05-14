package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemVendorListDao extends FactoryBaseDAO {

	public ItemVendorListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select itemnum \"料號\", "
				+ "(select  REGISTRATIONNUM from VW_NEWVENDORCODE_EPMALL where nvcid=vendor) \"供應商\","
				+ "(select  NEWVENDORNAME from VW_NEWVENDORCODE_EPMALL where nvcid=vendor) \"供應商名稱\" "
				+ "from invvendor "
				+ "where 1=1 " + condition
				+ "order by itemnum ";
		return queryBySQLToLinkMap(sql, param);
	}
}
