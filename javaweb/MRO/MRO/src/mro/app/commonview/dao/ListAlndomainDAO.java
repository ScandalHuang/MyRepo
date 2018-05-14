package mro.app.commonview.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mro.base.entity.Alndomain;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListAlndomainDAO extends FactoryBaseDAO{
	
	public ListAlndomainDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public List getAlndomainCountByGroup(String condition){
		
		String sql ="select DOMAINID,COUNT(1) from ALNDOMAIN "
				+ "where 1=1 "+condition
				+ " GROUP BY DOMAINID";

		return queryBySQL(sql);
	}
	public List getAlndomainList(String condition){
		
		String sql ="select * "+
                "from ALNDOMAIN where 1=1 "+condition+" order by dump(VALUE,36) desc";

		return queryBySQLWithEntity(sql,Alndomain.class);
	}
	public Alndomain getAlndomain(String condition){
		
		String sql ="select * "+
                "from ALNDOMAIN where 1=1 "+condition;

		return uniQueryBySQLWithEntity(sql,Alndomain.class);
	}
	public Alndomain getMaxAlndomain(String condition,Map map){
		
		String sql ="select * "+
                "from ALNDOMAIN where 1=1 "+condition+" order by dump(VALUE,36) desc";
		Session session = getSession();
        Query query = session.createSQLQuery(sql).addEntity(Alndomain.class).setMaxResults(1);
        query.setProperties(map);
        return (Alndomain) query.uniqueResult();
	}
	public BigDecimal getAlndomainCount(String condition){
		
		String sql ="select count(1) "+
                "from ALNDOMAIN where 1=1 "+condition;

		return (BigDecimal) uniQueryBySQL(sql);
	}
	public BigDecimal getAItemspecCount(Alndomain alndomain){
		
		String sql ="select count(1) "
				+ "from a_itemspec where (CLASSSTRUCTUREID,ASSETATTRID,ALNVALUE) in ( "
				+ "select CLASSSPEC.CLASSSTRUCTUREID,CLASSSPEC.ASSETATTRID,ALNDOMAIN.description  "
				+ "from CLASSSPEC left join ALNDOMAIN on CLASSSPEC.domainid=ALNDOMAIN.domainid "
				+ "where CLASSSPEC.domainid='"+alndomain.getDomainid()+"' "
				+ "and alndomain.value='"+alndomain.getValue()+"')";

		return (BigDecimal) uniQueryBySQL(sql);
	}
}
