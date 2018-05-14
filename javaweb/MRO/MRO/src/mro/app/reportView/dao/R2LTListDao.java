package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2LTListDao extends FactoryBaseDAO {

	public R2LTListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
//for R2 Report
	public List getList(String itemnum, String description, Map param) {

		String sql = "SELECT A.ITEMNUM, A.DESCRIPTION, NVL(LT1, NVL(LT2, DELIVERYTIME)) as \"Spare parts  前置交貨期\", LT1 \"IEP 料號之LT最大值\", LT2 \"IEP 類別結構之LT最大值\", DELIVERYTIME \"MRO 料號主檔 LT\" FROM ( ";
		String ltSql = getLtSql(itemnum, description);
			sql= sql+ltSql+	") A order by itemnum ";
		return queryBySQLToLinkMap(sql, param);
	}
	//for PrlineImpl	
	public Map getItemLt(String itemnum) {
		Map ltMap = null;
		String ltSql = getLtSql(itemnum, "%");
		String sql= "SELECT A.ITEMNUM, NVL(LT1, NVL(LT2, DELIVERYTIME)) as ITEM_LT FROM ( "
		            + ltSql
		            + ") A order by itemnum ";
		List r2LtList = queryBySQLToLinkMap(sql, null);
		if(r2LtList!=null&&r2LtList.size()>0) {
			ltMap = (Map)r2LtList.get(0);
		}
		return ltMap;
	}

	private String getLtSql(String itemnum, String description) {
		String ltSql = "select * from MRO_EP_R2_LT_V where ITEMNUM LIKE '"+itemnum+"' "+ 
				"AND DESCRIPTION LIKE '"+description+"' ";
		
//				"SELECT ITEM.ITEMNUM, ITEM.DESCRIPTION, MAX(PIEP1.LT2) LT1, MAX(PIEP2.LT2) LT2, IA.DELIVERYTIME FROM ITEM "+
//				"JOIN ITEM_ATTRIBUTE IA "+
//				"ON IA.ITEMID=ITEM.ITEMID "+
//				"LEFT JOIN PIEP_VW_LEADTIME_4MRO PIEP1 "+
//				"ON ITEM.ITEMNUM = PIEP1.PARTNO "+
//				"LEFT JOIN PIEP_VW_LEADTIME_4MRO PIEP2 "+
//				"ON ITEM.CLASSSTRUCTUREID = PIEP2.MG "+
//				"WHERE ITEM.ITEMTYPE = 'R2' AND ITEM.ITEMNUM LIKE '"+itemnum+"' "+
//				"AND ITEM.DESCRIPTION LIKE '"+description+"' "+
//				"GROUP BY ITEM.ITEMNUM, ITEM.DESCRIPTION, ITEM.CLASSSTRUCTUREID, IA.DELIVERYTIME ";
		return ltSql;
	}	
}
