package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemSiteListDao extends FactoryBaseDAO {

	public ItemSiteListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = " SELECT   A.ITEMNUM \"料號\",A.LOCATION_SITE ,A.ENABLE_FLAG \"是否啟用\","
				+ "C.ORGANIZATION_CODE Plant,A.LAST_UPDATED_BY \"最後更新人員\","
				+ "(SELECT   NAME || ' ' || EMP_NO || ' ' || EXT_NO FROM   HR_EMP "
				+ " WHERE   EMP_NO = A.LAST_UPDATED_BY) \"最後更新人員資訊\", "
				+ "A.LAST_UPDATE_DATE \"最後更新日期\"  "
				+ "FROM ITEM_SITE A  LEFT JOIN "
				+ "LOCATION_MAP C ON A.LOCATION_SITE = C.LOCATION_SITE "
				+ "where 1=1 " + condition
				+ "ORDER BY  A.ITEMNUM, A.LOCATION_SITE, C.ORGANIZATION_CODE, A.LAST_UPDATE_DATE ";
		return queryBySQLToLinkMap(sql, param);
	}
}
