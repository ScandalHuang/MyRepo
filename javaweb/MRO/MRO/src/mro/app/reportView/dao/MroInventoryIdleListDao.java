package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class MroInventoryIdleListDao extends FactoryBaseDAO {

	public MroInventoryIdleListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT ITEMNUM \"ITEMNUM(料號)\",ORGANIZATION_CODE \"ORGANIZATION_CODE(廠區)\","
				+ "LOCATION \"LOCATION(倉別)\","
				+ "(SELECT stock from inventory where itemnum=idle.itemnum and location=idle.location "
				+ "and siteid=(select site_id from location_map "
				+ "where ORGANIZATION_CODE=idle.ORGANIZATION_CODE)) \"庫存\",REMARK \"Remark(備註)\","
				+ "LOCATION_SITE \"區域\",CREATE_BY \"建立人工號\","
				+ "(SELECT NAME FROM HR_EMP WHERE EMP_NO=CREATE_BY) \"建立人姓名\","
				+ "CREATE_DATE \"建立時間\"  FROM MRO_INVENTORY_IDLE_LIST idle "
				+ "where 1=1 " + condition;
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOrgs(Map param) {

		String sql = "SELECT * FROM (select ORGANIZATION_NAME,ORGANIZATION_CODE from location_map "
				+ "where organization_code is not null and location_site is not null "
				+ "and LOCATION_SITE=:locationSite "
				+ "UNION "
				+ "select NONVALUE_PLANT,NONVALUE_PLANT from location_map "
				+ "where NONVALUE_PLANT is not null and location_site is not null and LOCATION_SITE=:locationSite) "
				+ "order by ORGANIZATION_CODE";
		return queryBySQLToLinkMap(sql, param);
	}
}
