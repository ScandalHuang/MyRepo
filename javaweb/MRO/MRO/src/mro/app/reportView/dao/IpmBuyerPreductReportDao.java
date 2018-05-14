package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class IpmBuyerPreductReportDao extends FactoryBaseDAO{
	
	public IpmBuyerPreductReportDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	public List getList(String prC,String poC,Map param){
		
		String sql ="WITH p AS (  SELECT ORG_CODE,siteid,PM_TYPE,LEADTIME,LT2,PART_NO ITEMNUM,SUM (QUANTITY) QTY  "
				+ "FROM  IPM_EQ_PARTS_REQ_PLAN p "
				+ "WHERE 1=1 "+prC
				+ "GROUP BY ORG_CODE,siteid,PM_TYPE,PART_NO,LEADTIME,LT2), "
				+ "PO AS (  SELECT   part_no, ORGANIZATION_ID, SUM (QUANTITY) QUANTITY "
				+ "FROM   (SELECT   part_no, ORGANIZATION_ID, QUANTITY, PO_STATUS,PO_APPROVED_DATE "
				+ "FROM   Z_ERP_OPEN_PO "
				+ "UNION "
				+ "SELECT   part_no, ORGANIZATION_ID, QUANTITY, PO_STATUS,PO_APPROVED_DATE "
				+ "FROM   Z_ZPO_MRO_NON_OPEN_PO) "
				+ "WHERE   PO_STATUS = 'APPROVED' "+poC+" GROUP BY   part_no, ORGANIZATION_ID) "
				+ "SELECT   a.*, PRQTY + poqty all_buyer, "
				+ "NVL (ROUND ( (PRQTY + poqty) / all_qty, 4) * 100, 0) || '%' rate, "
				+ "all_qty - poqty - STOCK net_qty, "
				+ "(SELECT   CLASSSTRUCTUREID FROM ITEM WHERE ITEMNUM = A.ITEMNUM) CLASSSTRUCTUREID,"
				+ "(SELECT   DESCRIPTION FROM ITEM WHERE ITEMNUM = A.ITEMNUM) DESCRIPTION,"
				+ "LM.ORGANIZATION_CODE,LM.LOCATION_SITE "
				+ "FROM ("
				+ "SELECT   p.itemnum, p.siteid,p.PM_TYPE,p.LEADTIME,P.LT2, NVL (p.qty, 0) qty, NVL ( (SELECT   SUM (qty) FROM   p tp1 "
				+ "WHERE   tp1.itemnum = p.itemnum), 0) all_qty,  NVL ( "
				+ "(SELECT   SUM (ABS(QUANTITY)) FROM   MATUSETRANS "
				+ "WHERE issuetype = 'ISSUE' AND ITEMNUM = P.ITEMNUM AND SITEID = P.SITEID), 0 ) USE_QTY,"
				+ " NVL ((SELECT   SUM (ABS(QUANTITY)) FROM MATUSETRANS M "
				+ "WHERE   issuetype = 'ISSUE' AND M.ITEMNUM = P.ITEMNUM "
				+ "AND EXISTS ( SELECT 1 FROM P WHERE  ITEMNUM = M.ITEMNUM AND SITEID = M.SITEID)), 0 ) ALL_USE_QTY, "
				+ "NVL ((SELECT   SUM (STOCK + mcbssonhand)FROM INVENTORY "
				+ "WHERE   ITEMNUM = P.ITEMNUM AND (LOCATION LIKE '%MT' OR LOCATION LIKE '%SE')),0)STOCK, "
				+ "NVL ( (SELECT   SUM (PRQTY) FROM   INVENTORY WHERE   ITEMNUM = P.ITEMNUM), 0) PRQTY, "
				+ "NVL ( (SELECT   SUM (QUANTITY) FROM   PO WHERE   PART_NO = P.ITEMNUM), 0) POQTY FROM p) a "
				+ "LEFT JOIN LOCATION_MAP LM ON A.SITEID = LM.SITE_ID "
				+ "ORDER BY   A.ITEMNUM, A.SITEID";
		return queryBySQLToLinkMap(sql, param);
	}
	
}
