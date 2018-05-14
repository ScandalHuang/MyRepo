package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class BuyerItemSupplierListDao extends FactoryBaseDAO {

	public BuyerItemSupplierListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = " WITH vendor1 AS (SELECT * FROM (SELECT   ROW_NUMBER ()  "
				+ "OVER (PARTITION BY a.itemnum ORDER BY a.INVVENDORID) ROW_NUMBER, "
				+ "a.itemnum, (SELECT NEWVENDORNAME FROM VW_NEWVENDORCODE_EPMALL WHERE "
				+ "A.VENDOR = NVCID) NEWVENDORNAME "
				+ "FROM INVVENDOR A) WHERE ROW_NUMBER=1) , "
				+ "vendor2 AS (SELECT * FROM (SELECT   ROW_NUMBER ()  "
				+ "OVER (PARTITION BY a.itemnum ORDER BY a.INVVENDORID) ROW_NUMBER, "
				+ "a.itemnum, (SELECT NEWVENDORNAME FROM VW_NEWVENDORCODE_EPMALL WHERE "
				+ "A.VENDOR = NVCID) NEWVENDORNAME "
				+ "FROM      INVVENDOR A ) WHERE ROW_NUMBER=2) "
				+ ", vendor3 AS (SELECT * FROM (SELECT   ROW_NUMBER () "
				+ "OVER (PARTITION BY a.itemnum ORDER BY a.INVVENDORID) ROW_NUMBER, "
				+ "a.itemnum, (SELECT NEWVENDORNAME FROM VW_NEWVENDORCODE_EPMALL WHERE "
				+ "A.VENDOR = NVCID) NEWVENDORNAME "
				+ "FROM      INVVENDOR A ) WHERE ROW_NUMBER=3) "
				+ ", vendorAll as (select itemnum,to_char(wm_concat(NEWVENDORNAME)) NEWVENDORNAME "
				+ "from ( (select *  from invvendor a left join VW_NEWVENDORCODE_EPMALL b "
				+ "on A.VENDOR = b.NVCID order by a.INVVENDORID)) group by itemnum) "
				+ ", STATUS_PARAMETER AS ( "
				+ "SELECT * FROM PARAMETER WHERE  CATEGORY='PROCESS_CATEGORY') "
				+ "select  I.ITEMNUM \"料號\",I.DESCRIPTION \"品名\", "
				+ "IA.ORI_UNITCOST \"報價單價格\",IA.ORI_CURRENCY \"報價單幣別\", "
				+ "I.CLASSSTRUCTUREID \"類別結構\", "
				+ "(select DESCRIPTION from CLASSSTRUCTURE where CLASSSTRUCTUREID=I.CLASSSTRUCTUREID) \"類別結構敘述\","
				+ "I.CREATE_DATE \"建料日期\",I.CHANGE_LAST_UPDATE \"更新日期\","
				+ "(SELECT PARAMETER_VALUE FROM STATUS_PARAMETER WHERE PARAMETER_KEY=I.STATUS ) \"料號狀態\","
				+ "(select TO_CHAR(WM_CONCAT(LOCATION_SITE)) from item_site where itemnum=i.itemnum  and ENABLE_FLAG='Y') \"生效區域\","
				+ "(select NEWVENDORNAME from vendor1 where  i.itemnum=vendor1.itemnum) \"供應商1\","
				+ "(select NEWVENDORNAME from vendor2 where  i.itemnum=vendor2.itemnum) \"供應商2\","
				+ "(select NEWVENDORNAME from vendor3 where i.itemnum=vendor3.itemnum) \"供應商3\","
				+ "(select NEWVENDORNAME from vendorAll where i.itemnum=vendorAll.itemnum) \"全部供應商\" "
				+ "from ITEM I LEFT JOIN ITEM_ATTRIBUTE IA ON I.itemid=ia.itemid "
				+ "where 1=1 " + condition
				+ "ORDER BY I.ITEMNUM ";
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
