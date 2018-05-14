package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemListDao extends FactoryBaseDAO {

	public ItemListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT i.ITEMNUM New_Part_Number,i.status 狀態, "
				+ "(select PARAMETER_VALUE from PARAMETER "
				+ "where PARAMETER_KEY=c.STORE_CATEGORY  "
				+ "and category='SAP_STORE_CATEGORY') 庫存分類,  "
				+ "i.CLASSSTRUCTUREID Material_Group,i.STORAGE_CONDITION, "
				+ "(select PARAMETER_VALUE from PARAMETER "
				+ "where PARAMETER_KEY=i.STORAGE_CONDITION  "
				+ "and category='STORAGE_CONIDTION') 儲存條件,I.DESCRIPTION 品名, "
				+ "(SELECT to_char(wm_concat(LOCATION_SITE)) FROM ITEM_SITE "
				+ "WHERE ENABLE_FLAG='Y' AND ITEMNUM=i.ITEMNUM ) \"生效區域\", "
				+ "(SELECT to_char(wm_concat(LOCATION_SITE)) FROM ITEM_SITE "
				+ "WHERE ENABLE_FLAG IS NULL AND ITEMNUM=i.ITEMNUM ) \"凍結區域\", "
				+ "ia.MIN_BASIC_UNIT Basic_unit, ia.PACKAGE_UNIT 包裝單位, "
				+ "ia.TRANSFER_QUANTITY 轉換數量, ia.MC_ORDER_QUANTITY 經濟訂購量, "
				+ "ia.MC_MIN_PACKAGE_QUANTITY 最小包裝數,ia.DELIVERYTIME  \"交貨前置期(天)\", "
				+ "i.CREATE_DATE  料號生效日,i.CHANGE_LAST_UPDATE 最後異動日, i.REMARK 用途說明, "
				+ "(select  e.name  from hr_emp e where e.emp_no=i.CREATE_BY) 料號申請人, "
				+ "i.CREATE_BY_DEPT_NO 料號申請部門, "
				+ "(select  e.name  from hr_emp e where e.emp_no=i.CHANGEBY) 料號最後更新人, "
				+ "I.CREATE_ORGANIZATION_CODE 申請廠區, "
				+ "case when (select count(1) from attachment "
				+ "where key_id=itemnum and file_category='ITEM_SPEC')>0 then 'Y' "
				+ "else 'N' end \"是否有規格書\", "
				+ "(select parameter_value from parameter "
				+ "where category='GP_PRODUCT_CATEGORY' "
				+ "and parameter_key=ia.GP_PRODUCT_CATEGORY) \"產品/製程類別\", "
				+ "(select parameter_value from parameter "
				+ "where category='GP_DELIVERY_TYPE' "
				+ "and parameter_key=ia.GP_DELIVERY_TYPE) \"是否隨產品出貨\", "
				+ "(select parameter_value from parameter "
				+ "where category='GP_REMAIN_TYPE' "
				+ "and parameter_key=ia.GP_remain_TYPE) \"是否殘留在產品內部\", "
				+ "ia.GP_RISK_LV \"風險等級\",ia.GP_EV_LV \"環保等級\","
				+ "ia.GP_REPORT_DATE \"檢測報告日期\", "
				+ "case when (select count(1) from attachment "
				+ "where key_id=itemnum and file_category='ITEM_GP_REPORT')>0 then 'Y' "
				+ "else 'N' end \"是否有檢測報告\", "
				+ "case when (select count(1) from attachment "
				+ "where key_id=itemnum and file_category='ITEM_GP_MSDS')>0 then 'Y' "
				+ "else 'N' end \"是否有MSDS\",  i.DISABLE_FLAG Oracle是否禁止請購"
				+ ",i.DISABLE_FLAG_REMAK Oracle禁止請購說明 "
				+ "FROM  ITEM I   LEFT JOIN ITEM_ATTRIBUTE IA ON I.ITEMID=IA.ITEMID "
				+ "left join CLASSSTRUCTURE  c on i.CLASSSTRUCTUREID=c.CLASSSTRUCTUREID "
				+ "where 1=1 " + condition
				+ "ORDER BY i.CLASSSTRUCTUREID,I.ITEMNUM ";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOption() {

		String sql = "SELECT classstructureid || '('||DESCRIPTION ||')' key ,"
				+ "classstructureid "
				+ "from classstructure  where HASCHILDREN=0 "
				+ "group by classstructureid,DESCRIPTION order by classstructureid";
		return queryBySQL(sql);
	}
}
