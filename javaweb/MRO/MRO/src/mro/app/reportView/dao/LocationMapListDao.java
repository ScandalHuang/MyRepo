package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class LocationMapListDao extends FactoryBaseDAO {

	public LocationMapListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {
		String sql = "SELECT  * FROM (SELECT DISTINCT a.site_id, a.organization_code \"廠區代碼\", a.org, "
				+ "a.currencycode \"幣別\", a.organization_name \"廠區名稱\", "
				+ "(SELECT LOCATION_SITE FROM LOCATION_MAP "
				+ "WHERE ORGANIZATION_CODE= NVL(SC.ASSIGN_ORGANIZATION_CODE,a.organization_code)) \"SITE名稱\" "
				+ "FROM location_map a LEFT JOIN subinventory_config sc "
				+ "ON a.organization_code = sc.ori_organization_code WHERE a.organization_code IS NOT NULL )  "
				+ "WHERE 1=1 "+ condition
				+ "ORDER BY \"SITE名稱\",\"廠區代碼\" ";
		return queryBySQLToLinkMap(sql, param);
	}
}
