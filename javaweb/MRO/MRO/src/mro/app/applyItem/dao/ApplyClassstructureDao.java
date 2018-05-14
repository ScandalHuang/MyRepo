package mro.app.applyItem.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import mro.base.entity.ClassstructureLineApply;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyClassstructureDao extends FactoryBaseDAO{
	public ApplyClassstructureDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public ClassstructureLineApply getClassstructureLineApply(String condition,Map map){
		String sql="select * from CLASSSTRUCTURE_LINE_APPLY  " +
				"where  1=1 " +condition ;
		Session session = getSession();
        Query query = session.createSQLQuery(sql).addEntity(ClassstructureLineApply.class).setMaxResults(1);;
        query.setProperties(map);
        return (ClassstructureLineApply) query.uniqueResult();
	}
	
	public Object getValidateClassstructureLineApply(String condition,Map map){
		String sql="select COUNT(1) from CLASSSTRUCTURE_HEADER_APPLY A " + 
				"LEFT JOIN CLASSSTRUCTURE_LINE_APPLY B ON A.APPLY_HEADER_ID=B.APPLY_HEADER_ID " +
				"where  1=1 " +condition ;
		Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setProperties(map);
        return query.uniqueResult();
	}
	
	public void onDeleteApplyLine(BigDecimal applyHeaderId){
		String sql ="delete from CLASSSTRUCTURE_LINE_APPLY where APPLY_HEADER_ID=:APPLY_HEADER_ID";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setBigDecimal("APPLY_HEADER_ID", applyHeaderId);
		query.executeUpdate();
	}
	public String getHeaderNum() {
		DecimalFormat df = new DecimalFormat("00000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		StringBuffer sb = new StringBuffer();
		Session session = getSession();
		BigDecimal prnum;
		int issuetotal;
		
			sb.append("SELECT COUNT(1) as issuetotal FROM CLASSSTRUCTURE_HEADER_APPLY ");
			sb.append("WHERE APPLY_HEADER_NUM LIKE 'CHA" + sdf.format(new Date()) + "%'");
	        
	        Query query = session.createSQLQuery(sb.toString());
	        issuetotal=((BigDecimal) query.uniqueResult()).intValue();
	        
			if(issuetotal==0)
			{
				query = session.createSQLQuery("{call RESET_NUM_SEQ('CLASSSTRUCTURE_NUM_APPLY_SEQ')}");
				query.executeUpdate();
			}
			query = session.createSQLQuery("SELECT CLASSSTRUCTURE_NUM_APPLY_SEQ.NEXTVAL FROM DUAL");
			prnum=(BigDecimal)query.uniqueResult();
			
		return "CHA"+sdf.format(new Date()) + df.format(prnum);
	}
}
