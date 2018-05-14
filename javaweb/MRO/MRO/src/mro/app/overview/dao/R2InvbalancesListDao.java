package mro.app.overview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2InvbalancesListDao extends FactoryBaseDAO {

	public R2InvbalancesListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT  (select lm.LOCATION_SITE from location_map lm where A1.siteid=lm.site_id) 區域,"
				+ "(SELECT   ORGANIZATION_CODE FROM   LOCATION_MAP WHERE   site_id = a1.siteid) \"廠區\","
				+ "a1.LOCATION \"倉庫\","
				+ "(SELECT PARAMETER_VALUE FROM PARAMETER WHERE CATEGORY='CONTROL_TYPE' AND PARAMETER_KEY=a2.cmommcontrol) \"控管模式\","
				+ "NVL ((SELECT usecounter FROM bsstrkhist a3 "
				//+ "WHERE    a3.itemnum = a1.itemnum AND a3.m_dept = get_dept_up(a1.DEPTCODE,40) "//201708
				+ "WHERE    a3.itemnum = a1.itemnum AND a3.m_dept = GET_COSTCENTER(a1.DEPTCODE) "
				+ "AND a3.siteid = a1.siteid), 0) \"近半年耗用月次\", a1.itemnum \"料號\", "
				+ "a5.description \"品名敘述\", A6.MIN_BASIC_UNIT \"最小計量單位\", a1.lastrequestedby2 \"申請人\", "
				+ "a4.display_name \"姓名\", a4.phone_num \"電話\", a1.DEPTCODE \"部門\", "
				+ "a1.oriavguseqty \"平均月耗用量\", a1.SSTOCK \"最低安全存量\", a1.minlevel \"重訂購量\", "
				+ "a2.oriavguseqty \"總平均月耗用量\", a2.sstock \"總最低安全存量\", a2.CMOMINLEVEL \"總重訂購量\", "
				+ "a2.iqc \"待驗區\", a2.stock \"目前存量(倉庫)\", a2.idledays \"呆滯天數\", "
				+ "A2.MTSTOCK \"該Site 原物料倉總數\", a2.sestock \"該Site費用倉總數\", "
				+ "a2.poqty \"PO_QTY\", a2.prqty \"PR_QTY\", NVL(R2_LT.LT1, NVL(R2_LT.LT2, R2_LT.DELIVERYTIME)) \"交貨前置期(天)\", " //a6.deliverytime \"交貨前置期(天)\", "
				+ "(SELECT   ldtext FROM   longdescription WHERE   ldkey = a1.invbalancesid "
				+ "AND ldownertable = 'INVBALANCES') \"備註\", a2.mccommand \"庫存分類變更說明\", "
				+ "a5.CLASSSTRUCTUREID \"類別結構\", (SELECT   DESCRIPTION FROM   CLASSSTRUCTURE "
				+ "WHERE   CLASSSTRUCTUREID = a5.CLASSSTRUCTUREID) \"類別說明\" "
				+ "FROM invbalances a1 LEFT JOIN inventory a2 ON a2.itemnum = a1.itemnum "
				+ "AND a2.LOCATION = a1.LOCATION aND a2.siteid = a1.siteid "
				+ "LEFT JOIN person a4 ON a4.person_id = a1.lastrequestedby2 "
				+ "LEFT JOIN item a5 ON a1.itemnum = a5.itemnum "
				+ "LEFT JOIN item_attribute a6 ON a5.itemid = a6.itemid "
				+ "LEFT JOIN MRO_EP_R2_LT_V R2_LT ON a5.ITEMNUM = R2_LT.ITEMNUM "//20171123 LT
				+ "WHERE   a1.binnum != 'DXSE' AND a5.status != 'STOPUSE' AND A1.ITEMNUM LIKE 'R2%' "
				+ condition
				+ "ORDER BY   a1.LOCATION ";
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
