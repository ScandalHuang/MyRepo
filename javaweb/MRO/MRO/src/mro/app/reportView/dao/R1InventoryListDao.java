package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R1InventoryListDao extends FactoryBaseDAO {

	public R1InventoryListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select INV.SYNC_LASTUPDATE 資料更新日,"
				+ "(select lm.LOCATION_SITE from location_map lm "
				+ "where inv.siteid=lm.site_id) 區域,"
				+ "(select lm.ORGANIZATION_CODE from location_map lm "
				+ "where inv.siteid=lm.site_id) FAB,"
				+ "INV.location 倉別,INV.ORGID,INV.itemnum item,I.DESCRIPTION,"
				+ "ia.MIN_BASIC_UNIT 料號單位, INV.stock 庫存,"
				+ "INV.IDLEDAYS,INV.IQC,INV.PRQTY PR,INV.POQTY PO,INV.CMOMINLEVEL 總月用量 "
				+ "from inventory INV  LEFT JOIN ITEM I ON INV.ITEMNUM=I.ITEMNUM "
				+ "left join item_attribute ia on i.itemid=ia.itemid "
				+ "where inv.itemnum like 'R1%' and inv.location not like '%WS%' "
				+ "and (inv.stock>0 or inv.IQC>0 or inv.prqty>0 or inv.poqty>0 "
				+ "or inv.CMOMINLEVEL>0) "+ condition
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
