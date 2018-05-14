package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class InventoryStockReportDao extends FactoryBaseDAO{
	
	public InventoryStockReportDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getStocks(String condition,Map param){
		String sql =" SELECT * FROM (SELECT   itemnum,"
				+ "(SELECT DESCRIPTION FROM ITEM WHERE ITEMNUM=INVENTORY.ITEMNUM) DESCRIPTION,"
				+ "location,"
				+ "SUM (stock + iqc + mcbssonhand + sestock + sdstock + poqty + prqty) total "
				+ "FROM   inventory "
				+ "where location not like '%WS' "+condition
				+ "GROUP BY   itemnum, location) WHERE TOTAL >0 ORDER BY ITEMNUM,LOCATION";
		return queryBySQLToLinkMap(sql, param);
	}
	
	public List getWmsStockDetailAlls(String condition,Map param){
		String sql ="select * from WMS_STOCK_DETAIL_ALL "
				+ "where 1=1 "+condition+" order by ITEM_NUM,fifo_no";
		return queryBySQLToLinkMap(sql, param);
	}
	public List getOpenPos(String condition,Map param){
		String sql ="select V.*,(NVL (quantity, 0) - NVL (received_qty, 0)) unreceived_qty "
				+ "from Z_ZPO_MRO_OPEN_PO_GROUP_V1 V "
				+ "where 1=1 "+condition+" order by im_itemnum,po_no,po_line_no";
		return queryBySQLToLinkMap(sql, param);
	}
	public List getUnProcessPrs(String condition,Map param){
		String sql ="select * "
				+ "from Z_ZPO_MRO_UNPROCESS_PR_GRP_V1 V "
				+ "where 1=1 "+condition+" order by im_itemnum,pr_no,pr_line_no";
		return queryBySQLToLinkMap(sql, param);
	}
	public List getBss(String condition,Map param){
		String sql ="select * "
				+ "from eam_snapshot_bss_o "
				+ "where 1=1 "+condition+" order by itemnum,transferdate";
		return queryBySQLToLinkMap(sql, param);
	}
}
