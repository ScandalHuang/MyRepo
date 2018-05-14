package mro.app.mcMgmtInterface.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.MroInvbalancesHighlightV;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemHighlightDAO extends FactoryBaseDAO{
	
	public ListItemHighlightDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List<MroInvbalancesHighlightV> getHighlightList(String condition){
		String sql="SELECT ROWNUM,V.* FROM MRO_INVBALANCES_HIGHLIGHT_V V WHERE 1=1 "+condition;
        return queryBySQLWithEntity(sql, MroInvbalancesHighlightV.class);
	}
	
	public void updateInvbalances(BigDecimal invbalancesid,
			String lastHighlightResponseBy,Date lastHighlightResponseDate){
		Map param=new HashMap<>();
        Session session = getSession();
        StringBuffer sql=new StringBuffer("update Invbalances set ");
        if(lastHighlightResponseDate==null){//物管重新啟動highlight
            sql.append("LAST_HIGHLIGHT_REPONSE_BY='', ");
            sql.append("LAST_HIGHLIGHT_REPONSE_DATE=null, ");
            sql.append("HIGHLIGHT_INITIAL_DATE=sysdate ");
        }else{
            sql.append("LAST_HIGHLIGHT_REPONSE_BY=:lastHighlightResponseBy, ");
            sql.append("LAST_HIGHLIGHT_REPONSE_DATE=:lastHighlightResponseDate ");
            param.put("lastHighlightResponseBy", lastHighlightResponseBy);
            param.put("lastHighlightResponseDate", lastHighlightResponseDate);
        	
        }
        sql.append("WHERE invbalancesid=:invbalancesid");
        
        param.put("invbalancesid", invbalancesid);
        
        Query query = session.createSQLQuery(sql.toString());
        query.setProperties(param);
        query.executeUpdate();
        
		
	}
	
	public void updateNonHighLight(){
        Session session = getSession();
		String sql="update invbalances a "
				+ "set LAST_HIGHLIGHT_REPONSE_BY='',LAST_HIGHLIGHT_REPONSE_DATE=null,"
				+ "HIGHLIGHT_INITIAL_DATE=null "
				+ "where not exists (select 1 from MRO_INVBALANCES_HIGHLIGHT_V v "
				+ "where  (SUMOFCONTER_FLAG='Y' OR THREE_MONTH_AVG_FLAG='Y') "
				+ "and v.INVBALANCESID=a.INVBALANCESID)";
		Query query = session.createSQLQuery(sql.toString());
		query.executeUpdate();
	}
		
}
