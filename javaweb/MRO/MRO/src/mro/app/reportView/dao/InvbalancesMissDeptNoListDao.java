package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class InvbalancesMissDeptNoListDao extends FactoryBaseDAO {

	public InvbalancesMissDeptNoListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT    (SELECT   LOCATION_SITE "
				+ "FROM   LOCATION_MAP WHERE   SITE_ID = SITEID) 區域, "
				+ "(SELECT   ORGANIZATION_CODE FROM   LOCATION_MAP "
				+ "WHERE   SITE_ID = SITEID)  \"廠區\", IB.LOCATION \"倉庫\", "
				+ "IB.ITEMNUM \"料號\", (SELECT   DESCRIPTION FROM   ITEM "
				+ "WHERE   ITEMNUM = IB.ITEMNUM) \"品名敘述\", IB.DEPTCODE 部門, "
				+ "IB.ORIAVGUSEQTY \"平均月耗用量\", IB.SSTOCK \"最低安全存量\", "
				+ "IB.MINLEVEL \"重訂購量(R1月用量)\", IB.LASTREQUESTEDBY2 \"控管人工號\", "
				//+ "EMP.NAME \"控管人姓名\", GET_DEPT_UP(EMP.DEPT_NO,40)  \"控管人最新部門代號\", "//201708
				+ "EMP.NAME \"控管人姓名\", GET_COSTCENTER(EMP.DEPT_NO)  \"控管人最新部門代號\", "
				+ "DECODE(EMP.PER_STATE,'1','在職','2','離職','3','留職停薪','4','退休') \"員工狀態\" "
				+ "FROM INVBALANCES IB "
				+ "LEFT JOIN HR_EMP EMP ON IB.LASTREQUESTEDBY2 = EMP.EMP_NO "
				//+ "WHERE   (IB.DEPTCODE != GET_DEPT_UP(EMP.DEPT_NO,40)  "//201708
				+ "WHERE   (IB.DEPTCODE != GET_COSTCENTER(EMP.DEPT_NO)  "
				+ "OR EMP.PER_STATE != '1') AND NVL (IB.MINLEVEL, 0) > 0 "+ condition;
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
