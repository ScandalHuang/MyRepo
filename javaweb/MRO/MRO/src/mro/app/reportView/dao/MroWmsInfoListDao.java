package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class MroWmsInfoListDao extends FactoryBaseDAO {

	public MroWmsInfoListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT   a.SYNC_LASTUPDATE \"資料更新日\",NVL(SC.LOCATION_SITE,LM.LOCATION_SITE) \"區域\","
				+ "ORGANIZATION_CODE 廠別,a.ITEM_NUM \"料號\","
				+ "(select CLASSSTRUCTUREID from item where itemnum=a.item_num) \"類別結構\","
				+ "(select Description from item where itemnum=a.ITEM_NUM) \"品名敘述\","
				+ "a.SUBINVENTORY \"倉別\",a.CREATED_DATE \"收料日\",a.FIFO_NO,"
				+ "a.STOCK_TYPE \"庫存類別\",A.REAL_QTY \"分配數量\",a.wms_owned_dept wms提供歸屬部門,A.BINNUM \"權責部門\","
				+ "A.INSPECTOR_NUM  \"申請人工號\","
				+ "(select name from hr_emp where emp_no=A.INSPECTOR_NUM) \"申請人姓名\","
				+ "A.ERP_PO_NO,A.PR_NUM,A.PR_LINE_NO,A.PR_REQUESTOR \"PR請購人\","
				+ "A.NOTE_TO_AGENT \"採購員說明\",A.LINE_MEMO \"其他說明\","
				+ "A.TRANS_TYPE \"入料類別\",A.ORI_MINLEVEL \"控管量\","
				+ "(select idledays from INVENTORY where itemnum=a.ITEM_NUM "
				+ "and LOCATION=a.SUBINVENTORY and siteid=a.org_id) \"呆滯天數\","
				+ "A.STOCK_DAYS \"庫齡天數\",STOCK_DAYS_TYPE 庫齡區間"
				+ "  from WMS_STOCK_DETAIL_ALL a "
				+ "LEFT JOIN  location_map lm ON a.ORG_ID = lm.site_id "
				+ "LEFT JOIN (select * from SUBINVENTORY_CONFIG where ORI_ORGANIZATION_CODE like 'B%' ) SC  "
				+ "ON SC.ORI_ORGANIZATION_CODE=LM.ORGANIZATION_CODE "
				+ "and sc.ORI_LOCATION=a.subinventory  "
				+ "where a.SUBINVENTORY not like '%WS' and a.SUBINVENTORY!='E1MT' "
				+ "and a.SUBINVENTORY not like '%PG' and (a.SUBINVENTORY NOT like '347%' OR (a.SUBINVENTORY  like '347%' "
				+ "AND a.ORG_ID NOT IN ('B002','B009','B010')))"+ condition;
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
	public List getStockType() {
		String sql = "select distinct stock_type from WMS_STOCK_DETAIL_ALL ";
		return queryBySQL(sql);
	}
}
