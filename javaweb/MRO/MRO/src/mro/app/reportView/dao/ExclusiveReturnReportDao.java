package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ExclusiveReturnReportDao extends FactoryBaseDAO{
	
	public ExclusiveReturnReportDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	public List getExclusiveReturnList(String condition,Map param){
		
//		String sql ="SELECT   A.ORGANIZATION_CODE,A.MANAGE_BINNUM,A.FAB,"
//				+ "A.im_itemnum,A.ECLUSIVE_REMARK,A.DESCRIPTION,"
//				+ "SUM(DECODE(TYPE,'ISSUE',QUANTITY,0)) ISSUE_QTY,"
//				+ "SUM(DECODE(TYPE,'RETURN',QUANTITY,0)) RETURN_QTY "
//				+ "FROM   (select * from MRO_RETURN_DETAIL_V where 1=1 "+condition+") A "
//				+ "GROUP BY   A.ORGANIZATION_CODE,A.MANAGE_BINNUM,"
//				+ "A.im_itemnum,A.ECLUSIVE_REMARK,A.FAB,A.DESCRIPTION "
//				+ "ORDER BY   A.ORGANIZATION_CODE, A.MANAGE_BINNUM, A.im_itemnum";
		String sql ="SELECT  A.MANAGE_BINNUM,A.FAB," //GET_SITE(A.ORGANIZATION_CODE,A.STORELOC) LOCATION_SITE, 
				+ "A.im_itemnum,A.ECLUSIVE_REMARK,A.DESCRIPTION,"
				+ "SUM(DECODE(TYPE,'ISSUE',QUANTITY,0)) ISSUE_QTY,"
				+ "SUM(DECODE(TYPE,'RETURN',QUANTITY,0)) RETURN_QTY "
				+ "FROM   (select * from MRO_RETURN_DETAIL_V1 where 1=1 "+condition+") A "
				+ "GROUP BY  A.MANAGE_BINNUM," //GET_SITE(A.ORGANIZATION_CODE,A.STORELOC) , 
				+ "A.im_itemnum,A.ECLUSIVE_REMARK,A.FAB,A.DESCRIPTION "
				+ "ORDER BY   A.MANAGE_BINNUM, A.im_itemnum";
		return queryBySQLToLinkMap(sql, param);
	}
	
public List getExclusiveReturnMDList(String condition,Map param){
		
//		String sql ="SELECT   ORGANIZATION_CODE,MANAGE_BINNUM,FAB,COUNT (1) ITEM_COUNT,"
//				+ "ROUND(SUM(LEAST(RATE,1)),4) ITEM_RATE,"
//				+ "ROUND (SUM (LEAST(RATE,1)) / COUNT (1), 4) ALL_RATE "
//				+ "FROM   (  SELECT   A.ORGANIZATION_CODE,A.MANAGE_BINNUM,A.im_itemnum,A.FAB,"
//				+ "SUM (DECODE (TYPE, 'RETURN', QUANTITY, 0)) / "
//				+ "SUM (DECODE (TYPE, 'ISSUE', ABS (QUANTITY), 0)) RATE "
//				+ "FROM (select * from MRO_RETURN_DETAIL_V where 1=1 "+condition+") A "
//				+ "where a.ECLUSIVE_REMARK IS NULL "
//				+ "HAVING   SUM (DECODE (TYPE, 'ISSUE', abs(QUANTITY), 0)) > 0 "
//				+ "GROUP BY   A.ORGANIZATION_CODE, A.MANAGE_BINNUM,A.im_itemnum,A.FAB) MA "
//				+ "GROUP BY   ORGANIZATION_CODE, MANAGE_BINNUM,FAB";
		String sql ="SELECT   MANAGE_BINNUM,FAB,COUNT (1) ITEM_COUNT,"
				+ "ROUND(SUM(LEAST(RATE,1)),4) ITEM_RATE,"
				+ "ROUND (SUM (LEAST(RATE,1)) / COUNT (1), 4) ALL_RATE "
				+ "FROM   (  SELECT   A.ORGANIZATION_CODE,A.MANAGE_BINNUM,A.im_itemnum,A.FAB,"//GET_SITE(A.ORGANIZATION_CODE,A.STORELOC) LOCATION_SITE,
				+ "SUM (DECODE (TYPE, 'RETURN', QUANTITY, 0)) / "
				+ "SUM (DECODE (TYPE, 'ISSUE', ABS (QUANTITY), 0)) RATE "
				+ "FROM (select * from MRO_RETURN_DETAIL_V1 where 1=1 "+condition+") A "
				+ "where a.ECLUSIVE_REMARK IS NULL "
				+ "HAVING   SUM (DECODE (TYPE, 'ISSUE', abs(QUANTITY), 0)) > 0 "
				+ "GROUP BY   A.MANAGE_BINNUM,A.im_itemnum,A.FAB) MA " //GET_SITE(A.ORGANIZATION_CODE,A.STORELOC) , 
				+ "GROUP BY   ORGANIZATION_CODE, MANAGE_BINNUM,FAB";
		return queryBySQLToLinkMap(sql, param);
	}
}
