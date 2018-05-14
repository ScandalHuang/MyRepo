package mro.app.applyItem.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mro.base.entity.ClassstructureItemSiteSign;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemTransferSiteDao extends FactoryBaseDAO{
	public ApplyItemTransferSiteDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public List getItemTransferHeaderApplyList(String condition){
		String sql="select * from ITEM_TRANSFER_HEADER_APPLY  " +
				"where  1=1 " +condition + " order by CREATE_DATE desc";
		return queryBySQLWithEntity(sql, ItemTransferHeaderApply.class);
	}
	public List getItemTransferLineApplyList(String condition){
		String sql="select * from ITEM_TRANSFER_LINE_APPLY  " +
				"where  1=1 " +condition + " order by LINE_NUM";
		return queryBySQLWithEntity(sql, ItemTransferLineApply.class);
	}
	public void onDeleteApplyLine(BigDecimal applyHeaderId){
		String sql ="delete from ITEM_TRANSFER_LINE_APPLY where APPLY_HEADER_ID='"+applyHeaderId+"'";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	public String getHeaderNum() {
		DecimalFormat df = new DecimalFormat("00000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		StringBuffer sb = new StringBuffer();
		Session session = getSession();
		BigDecimal prnum;
		int issuetotal;
		
			sb.append("SELECT COUNT(1) as issuetotal FROM ITEM_TRANSFER_HEADER_APPLY ");
			sb.append("WHERE APPLY_HEADER_NUM LIKE 'IST" + sdf.format(new Date()) + "%'");
	        
	        Query query = session.createSQLQuery(sb.toString());
	        issuetotal=((BigDecimal) query.uniqueResult()).intValue();
	        
			if(issuetotal==0)
			{
				query = session.createSQLQuery("{call RESET_TRANSFER_NUM_APPLY_SEQ()}");
				query.executeUpdate();
			}
			query = session.createSQLQuery("SELECT ITEM_TRANSFER_NUM_APPLY_SEQ.NEXTVAL FROM DUAL");
			prnum=(BigDecimal)query.uniqueResult();
			
		return "IST"+sdf.format(new Date()) + df.format(prnum);
	}
	
	public List getItemTransferApplyCost(BigDecimal applyHeaderId){
		String sql="select max(b.unitcost) cost from ITEM_TRANSFER_LINE_APPLY a, ITEM_ATTRIBUTE b  " +
				"where a.itemid=b.itemid and a.APPLY_HEADER_ID='"+applyHeaderId+"'";
		return queryBySQLToMap(sql, null);
	}
}
