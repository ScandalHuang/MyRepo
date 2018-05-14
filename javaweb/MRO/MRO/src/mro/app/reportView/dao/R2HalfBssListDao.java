package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2HalfBssListDao extends FactoryBaseDAO {

	public R2HalfBssListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select sysdate 資料下載日, "
				+ "(select lm.LOCATION_SITE from location_map lm "
				+ "where a.SITEID=lm.site_id) 區域, (select lm.ORGANIZATION_CODE "
				+ "from location_map lm where a.SITEID=lm.site_id) FAB, "
				+ "a.itemnum 料號,a.M_DEPT 部門, "
				+ "(select fab from MRO_ORG_FACILITY mf where mf.dept_no=inv.DEPTCODE "
				+ "and exists(select 1 from location_map lm "
				+ "where lm.location_site=mf.location_site "
				+ "and lm.SITE_ID=a.SITEID)) \"SHOP\",b.DESCRIPTION, "
				+ "c.DELIVERYTIME \"交貨前置期(日)\", "
				+ "inv.ORIAVGUSEQTY 平均月耗用量,inv.sstock 最低安全存量,inv.MINLEVEL 重訂購量, "
				+ "a.M5DQTY \"N-5 月耗用數量\",a.M4DQTY \"N-4 月耗用數量\","
				+ "a.M3DQTY \"N-3 月耗用數量\", "
				+ "a.M2DQTY \"N-2 月耗用數量\",a.M1DQTY \"N-1 月耗用數量\",a.mDqty \"N 月耗用數量\", "
				+ "(a.mDqty+a.M1DQTY+a.M2DQTY+a.M3DQTY+a.M4DQTY+a.m5dqty) 近半年總耗用數量, "
				+ "ROUND((a.mDqty+a.M1DQTY+a.M2DQTY+a.M3DQTY+a.M4DQTY+a.M5DQTY)/6,0) "
				+ "\"近半年平均月耗用數量\", a.USECOUNTER \"近半年耗用月次數\" "
				+ "from BSSTRKHIST a "
				+ "left join item b on a.itemnum=b.itemnum "
				+ "left join item_attribute c on b.itemid=c.itemid "
				+ "left join INVBALANCES inv on a.itemnum=inv.itemnum "
				//+ "and a.M_DEPT=GET_DEPT_UP(inv.DEPTCODE,40) AND A.SITEID=INV.SITEID "//201708 revise
				+ "and a.M_DEPT=GET_COSTCENTER(inv.DEPTCODE) AND A.SITEID=INV.SITEID "
				+ "WHERE B.COMMODITYGROUP IN ('R2','94') "+ condition
				+ "ORDER BY A.ITEMNUM,A.M_DEPT ";
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
