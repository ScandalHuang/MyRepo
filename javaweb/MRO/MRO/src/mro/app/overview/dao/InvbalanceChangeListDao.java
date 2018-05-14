package mro.app.overview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class InvbalanceChangeListDao extends FactoryBaseDAO {

	public InvbalanceChangeListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT  (select lm.LOCATION_SITE from location_map lm "
				+ "where A1.siteid=lm.site_id) 區域,"
				+ "(select lm.ORGANIZATION_CODE from location_map lm "
				+ "where A1.siteid=lm.site_id) \"廠區\",a1.storeroom \"倉別\","
				+ "a1.prnum \"申請單號\",a1.PRLINENUM \"請購單項次\",a1.itemnum \"料號\","
				+ "a1.description \"品名敘述\",a2.STATUS \"料號狀態\",a3.DISPLAY_NAME \"申請人\","
				+ "a1.m_dept\"部門代碼\",a3.PHONE_NUM \"電話\",a4.ISSUEDATE \"申請日期\","
				+ "a4.STATUSDATE \"狀態變更日期\",a1.unitcost \"最新單價\",a4.CURRENCYCODE \"幣別\","
				+ "a1.linecost \"預估總價\",a1.minlevel \"調整 重訂購量\",a1.sstock \"調整 最低安全存量\","
				+ "a1.davguseqty \"調整 平均月耗用量\",a1.cmoremark \"用途\","
				+ "a5.PARAMETER_DESCRIPTION \"狀態\""
				+ "FROM  prline a1 "
				+ "LEFT JOIN item a2 ON a1.itemnum = a2.itemnum "
				+ "LEFT JOIN PERSON a3  ON a3.PERSON_ID = a1.REQUESTEDBY2 "
				+ "LEFT JOIN PR a4 ON a4.PRNUM = a1.prnum AND a4.siteid = a1.siteid "
				+ "LEFT JOIN parameter a5 ON a5.PARAMETER_KEY = a4.status "
				+ "WHERE       a1.enterdate >= (SYSDATE - 365) "
				+ "AND a1.prtype IN ('R1CONTROL', 'R2CONTROL') "
				+ "AND a4.status = 'APPR' "+ condition
				+ "ORDER BY   a1.prnum DESC, a1.prlinenum ASC ";
		return queryBySQLToLinkMap(sql, param);
	}
}
