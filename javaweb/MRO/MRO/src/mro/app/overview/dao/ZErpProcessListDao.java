package mro.app.overview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ZErpProcessListDao extends FactoryBaseDAO {

	public ZErpProcessListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT (select lm.LOCATION_SITE from location_map lm "
				+ "where B.ORGANIZATION_ID=lm.site_id) \"區域\", "
				+ "(SELECT   ORGANIZATION_CODE FROM   LOCATION_MAP l "
				+ "WHERE TO_CHAR(B.ORGANIZATION_ID) = site_id) \"廠區\","
				+ "b.DESTINATION_SUBINVENTORY \"倉庫\",B.PART_NO \"料號\", "
				+ "(SELECT   p.PARAMETER_VALUE FROM item i "
				+ "LEFT JOIN CLASSSTRUCTURE c ON i.CLASSSTRUCTUREid = c.CLASSSTRUCTUREid "
				+ "LEFT JOIN parameter p ON c.store_category = p.PARAMETER_KEY "
				+ "WHERE   i.itemnum = b.part_no "
				+ "AND p.CATEGORY = 'SAP_STORE_CATEGORY') \"庫存分類\", "
				+ "PART_NO_DESC \"品名敘述\", "
				+ "(SELECT MIN_BASIC_UNIT FROM ITEM_ATTRIBUTE "
				+ "WHERE ITEMID=(SELECT ITEMID FROM ITEM WHERE ITEMNUM=B.part_no)) \"單位\", "
				+ "QUANTITY \"請購量\",PR_NO \"請購單編號\",PR_LINE_NO \"請購單項次\", "
				+ "PR_STATUS \"狀態\",PR_CREATION_DATE \"開單日\",REQUESTOR_EMP_NO \"請購人工號\", "
				+ "REQUESTOR_NAME \"請購人姓名\",REQUESTOR_DEPT_CODE \"請購人部門\", "
				+ "NEED_BY_DATE \"需求日\",INSPECTOR_EMP_NUM \"驗收人工號\", "
				+ "INSPECTOR_NAME \"驗收人姓名\",INSPECTOR_DEPT_CODE \"驗收人部門\", "
				+ "SUGGESTED_BUYER_NUM \"預設採購員工號\",SUGGESTED_BUYER_NAME \"預設採購員姓名\", "
				+ "DISPATCH_DATE \"分發採購員日期\",LINE_MEMO \"其他說明\","
				+ "NOTE_TO_AGENT \"採購員說明\" FROM   Z_ZPO_MRO_PROCESS_PR b "
				+ "where 1=1 " + condition;
		return queryBySQLToLinkMap(sql, param);
	}


	public List getOrgs(Map param) {

		String sql = "select ORGANIZATION_NAME,ORGANIZATION_CODE from location_map "
				+ "where organization_code is not null and location_site is not null "
				+ "and LOCATION_SITE=:locationSite  "
				+ "order by ORGANIZATION_CODE";
		return queryBySQLToLinkMap(sql, param);
	}
}
