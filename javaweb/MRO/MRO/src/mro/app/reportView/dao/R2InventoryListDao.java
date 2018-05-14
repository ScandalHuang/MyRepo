package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2InventoryListDao extends FactoryBaseDAO {

	public R2InventoryListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select INV.SYNC_LASTUPDATE 資料更新日,NVL(SC.LOCATION_SITE,LM.LOCATION_SITE) 區域,"
				+ "(select lm.ORGANIZATION_CODE from location_map lm "
				+ "where inv.siteid=lm.site_id) FAB,INV.location 倉別,INV.ORGID,"
				+ "INV.itemnum item,I.DESCRIPTION,"
				+ "ia.MIN_BASIC_UNIT 料號單位, INV.stock 庫存,"
				+ "INV.IDLEDAYS,INV.IQC,INV.SESTOCK 該Site費用倉總數 ,INV.PRQTY PR,"
				+ "INV.POQTY PO,INV.ORIAVGUSEQTY 總平均月耗用量,INV.SSTOCK 總最低安全存量,"
				+ "INV.CMOMINLEVEL 總重訂購量 "
				+ "from inventory INV  LEFT JOIN ITEM I ON INV.ITEMNUM=I.ITEMNUM "
				+ "left join item_attribute ia on i.itemid=ia.itemid "
				+ "LEFT JOIN LOCATION_MAP LM ON inv.siteid=lm.site_id "
				+ "LEFT JOIN (select * from SUBINVENTORY_CONFIG where ORI_ORGANIZATION_CODE like 'B%' ) SC  ON SC.ORI_ORGANIZATION_CODE=LM.ORGANIZATION_CODE "
				+ "AND SC.ORI_LOCATION=INV.LOCATION "
				+ "where I.COMMODITYGROUP IN ('94' ,'R2')  "
				+ "AND INV.LOCATION NOT LIKE '%WS%' "
				+ "and (inv.stock>0 or inv.IQC>0 or inv.prqty>0 or inv.poqty>0 "
				+ "or inv.CMOMINLEVEL>0  or inv.sstock>0 or inv.ORIAVGUSEQTY>0 "
				+ "or INV.SESTOCK>0) and (location NOT like '347%' OR (location  like '347%' "
				+ "AND siteid NOT IN ('B002','B009','B010')))  "+ condition
				+ "order by inv.itemnum,inv.location,INV.lastissuedate ";
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
