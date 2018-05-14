package mro.app.sapAccess.dao;

import java.util.List;

import mro.base.entity.Item;
import mro.base.entity.ItemSiteTransferLog;
import mro.base.entity.SapAccessLogEq;
import mro.base.entity.SapAccessLogPn;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class SapAccessQueryDao extends FactoryBaseDAO{
	
	public SapAccessQueryDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getItemSiteTransferLogList(String condition){
		String sql ="select * "+
                "from ITEM_SITE_TRANSFER_LOG   " +
                "where 1=1 "+condition+" order by ITEMNUM DESC";

		return queryBySQLWithEntity(sql,ItemSiteTransferLog.class);
	}
	
	public List getSapLogPNList(String condition){
		String sql ="SELECT a.*,b.* "
				+ "FROM sap_access_log_pn a ,item b "
                + "where a.material=b.itemnum  "+condition+" order by a.MATERIAL DESC";
        Session session = getSession();
        Query query = session.createSQLQuery(sql)
        		.addEntity(SapAccessLogPn.class)
        		.addEntity(Item.class);
        return query.list();
	}
	public List getSapLogEqList(String condition){
		String sql ="SELECT a.*,b.* "
				+ "FROM SAP_ACCESS_LOG_EQ a ,item b "
                + "where a.material=b.itemnum  "+condition+" order by a.MATERIAL DESC";
        Session session = getSession();
        Query query = session.createSQLQuery(sql)
        		.addEntity(SapAccessLogEq.class)
        		.addEntity(Item.class);
        return query.list();
	}
	
	public List getEnableItemList(String condition){
		String sql ="SELECT A.* FROM ITEM A LEFT JOIN ITEM_SITE B "
				+ "ON A.ITEMNUM=B.ITEMNUM WHERE B.ENABLE_FLAG='Y' "+condition;
		return queryBySQLWithEntity(sql,Item.class);
	}
}
