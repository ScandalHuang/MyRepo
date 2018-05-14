package mro.app.overview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ZMroOpenPoListDao extends FactoryBaseDAO {

	public ZMroOpenPoListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT    (SELECT   location_site FROM   LOCATION_MAP l "
				+ "WHERE TO_CHAR(B.ORGANIZATION_ID) = site_id) \"區域\","
				+ "(SELECT   ORGANIZATION_CODE FROM   LOCATION_MAP l "
				+ "WHERE TO_CHAR(B.ORGANIZATION_ID) = site_id) \"廠區\", "
				+ "B.DESTINATION_SUBINVENTORY \"倉庫\",PART_NO \"料號\", "
				+ "(SELECT   p.PARAMETER_VALUE FROM item i LEFT JOIN "
				+ "CLASSSTRUCTURE c ON i.CLASSSTRUCTUREid = c.CLASSSTRUCTUREid "
				+ "LEFT JOIN parameter p ON c.store_category = p.PARAMETER_KEY "
				+ "WHERE   i.itemnum = b.part_no AND p.CATEGORY = 'SAP_STORE_CATEGORY') \"庫存分類\", "
				+ " B.PART_NO_DESC \"品名敘述\", "
				+ "(SELECT MIN_BASIC_UNIT FROM ITEM_ATTRIBUTE WHERE ITEMID="
				+ "(SELECT ITEMID FROM ITEM WHERE ITEMNUM=B.part_no)) \"單位\","
				+ "PR_NO \"請購單編號\",PR_LINE_NO \"請購單項次\", PO_NO \"訂單編號\","
				+ "PO_LINE_NO \"訂單項次\",QUANTITY \"訂單量\", "
				+ "(b.QUANTITY - b.RECEIVED_QTY) \"未交量\",UNDELIVER_QTY \"IQC\","
				+ "RECEIVED_QTY \"收料量\", BILL_QTY \"立帳量\",(RECEIVED_QTY - BILL_QTY) \"已收未立帳數量\", "
				+ "(SELECT newvendorname FROM VW_NEWVENDORCODE_EPMALL  "
				+ "WHERE b.VENDOR_ID=nvcid) \"供應商\",PROMISED_DATE \"PO交期\", "
				+ "CONFIRM_PROMISE_DATE \"供應商confirm交期\",PO_STATUS \"狀態\", "
				+ "(SELECT DISPLAY_NAME FROM PERSON  WHERE person_id = SUBSTR (b.BUYER_EMPLOYEE_NUMBER, -8, 8)) \"採購員\", "
				+ "REQUESTOR_EMP_NO \"請購人工號\",REQUESTOR_NAME \"請購人姓名\","
				+ "REQUESTOR_DEPT_CODE \"請購人部門\", "
				+ "INSPECTOR_EMP_NUM \"驗收人工號\",INSPECTOR_EMP_NAME \"驗收人姓名\", "
				+ "INSPECTOR_DEPT_CODE \"驗收人部門\",DISPATCH_DATE \"分發採購員日期\","
				+ "LINE_MEMO \"其他說明\", NOTE_TO_AGENT \"採購員說明\" "
				+ " FROM  Z_ERP_OPEN_PO b "
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
