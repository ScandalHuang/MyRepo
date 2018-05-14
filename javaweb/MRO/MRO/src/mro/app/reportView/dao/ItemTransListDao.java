package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemTransListDao extends FactoryBaseDAO {

	public ItemTransListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT a.sd 資料下載日, "
				+ "nvl(sc.location_site,lm.location_site) 區域, "
				+ "lm.organization_code FAB,a.storeloc 倉別,a.ITEMnum 料號, "
				+ "b.DESCRIPTION, c.MIN_BASIC_UNIT 料號單位,a.QUANTITY,a.ACTUALDATE 交易時間, "
				+ "a.SOURCE_TYPE,a.trans_type 交易類別,a.BINNUM 部門,a.REFWO \"雜收發單/過帳編號\" "
				+ "FROM ( select sysdate sd, SITEID, storeloc ,ORGID,ITEMNUM , "
				+ "QUANTITY,ACTUALDATE ,decode(ISSUETYPE,'ISSUE','領料','RETURN','退料') "
				+ "SOURCE_TYPE, trans_type ,BINNUM ,REFWO  from matusetrans UNION ALL "
				+ "select sysdate , SITEID , TOstoreloc ,ORGID,ITEMNUM , "
				+ "QUANTITY,ACTUALDATE ,decode(ISSUETYPE,'RECEIPT','PO入料',"
				+ "'TRANSFER','廠調','REJECTION','報廢品繳庫') SOURCE_TYPE, "
				+ "trans_type ,BINNUM,REFWO  FROM MATRECTRANS) a "
				+ "left join item b on a.itemnum=b.itemnum "
				+ "left join item_attribute c on b.itemid=c.itemid "
				+ "LEFT JOIN LOCATION_MAP LM ON A.SITEID=lm.SITE_id "
				+ "LEFT JOIN (SELECT * FROM SUBINVENTORY_CONFIG WHERE ORI_ORGANIZATION_CODE LIKE 'B%') SC  "
				+ "ON SC.ORI_ORGANIZATION_CODE=LM.ORGANIZATION_CODE "
				+ "AND SC.ORI_LOCATION=a.storeloc "
				+ "where 1=1 " + condition
				+ "ORDER BY a.ACTUALDATE ";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOrgs(Map param,String condition) {

		String sql = "SELECT * FROM (select ORGANIZATION_NAME,ORGANIZATION_CODE from location_map "
				+ "where organization_code is not null "+ condition
				+ " UNION "
				+ "select (select ORGANIZATION_NAME from location_map where ORGANIZATION_CODE=tlm.NONVALUE_PLANT),"
				+ "NONVALUE_PLANT from location_map tlm "
				+ "where NONVALUE_PLANT is not null "+condition+") "
				+ "order by ORGANIZATION_CODE";
		return queryBySQLToLinkMap(sql, param);
	}
}
