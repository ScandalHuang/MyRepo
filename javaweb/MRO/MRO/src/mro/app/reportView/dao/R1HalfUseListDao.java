package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R1HalfUseListDao extends FactoryBaseDAO {

	public R1HalfUseListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select sysdate 資料下載日,  lm.LOCATION_SITE 區域,"
				+ "lm.ORGANIZATION_CODE  FAB,a.STORELOC 倉別, "
				+ "a.itemnum 料號,a.M_BINNUM 部門, "
				+ "(select fab from MRO_ORG_FACILITY mf where mf.dept_no=inv.DEPTCODE "
				+ "and exists(select 1 from location_map lm "
				+ "where lm.location_site=mf.location_site "
				+ "and lm.SITE_ID=a.SITEID)) \"SHOP\",b.DESCRIPTION, "
				+ "c.DELIVERYTIME \"交貨前置期(日)\",inv.MINLEVEL 月用量,a.C0 \"N 月領用數量\", "
				+ "a.C1 \"N-1 月領用數量\",a.C2 \"N-2 月領用數量\",a.C3 \"N-3 領用數量\", "
				+ "a.C4 \"N-4 領用數量\",a.C5 \"N-5 領用數量\",a.C6 \"N-6 領用數量\", "
				+ "ROUND((a.C6+a.C5+a.C4+a.C3+a.C2+a.C1)/6,0) \"近半年平均月領用數量\", "
				+ "a.HALFYEAR_ISSUE_COUNTER \"近半年領用月次數\",C.MC_ORDER_QUANTITY MOQ,"
				+ "C.MC_MIN_PACKAGE_QUANTITY MPQ from MATUSETRANS_HALF a "
				+ "left join item b on a.itemnum=b.itemnum "
				+ "left join item_attribute c on b.itemid=c.itemid "
				+ "left join INVBALANCES inv on a.itemnum=inv.itemnum "
				//+ "and a.M_BINNUM=GET_DEPT_UP(inv.DEPTCODE,40) and a.siteid=inv.siteid "//201708 revise
				+ "and a.M_BINNUM=GET_COSTCENTER(inv.DEPTCODE) and a.siteid=inv.siteid "
				+ "LEFT JOIN location_map lm   ON TO_CHAR (a.SITEID) = lm.site_id "
				+ "WHERE A.ITEMNUM LIKE 'R1%'  "
				+ "AND  exists(select 1 FROM CLASSSTRUCTURE "
				+ "WHERE STORE_CATEGORY='ZERS' and CLASSSTRUCTUREID=b.CLASSSTRUCTUREID) "
				+ "and (a.c0>0 or a.c1>0 or a.c2>0 or a.c3>0 or a.c4>0 or a.c5>0 "
				+ "or a.c6>0) " + condition
				+ " ORDER BY A.ITEMNUM,A.M_BINNUM,a.STORELOC ";
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
