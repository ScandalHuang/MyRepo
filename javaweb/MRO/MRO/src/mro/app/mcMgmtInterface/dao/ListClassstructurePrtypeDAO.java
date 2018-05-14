package mro.app.mcMgmtInterface.dao;

import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructurePrtype;
import mro.base.entity.PrtypeBudget;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructurePrtypeDAO extends FactoryBaseDAO{
	public ListClassstructurePrtypeDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	public ClassstructurePrtype getClassstructurePrtype(String condition){
		String sql ="select * from CLASSSTRUCTURE_PRTYPE where 1=1 "+condition;
        return uniQueryBySQLWithEntity(sql, ClassstructurePrtype.class);
	}
	public List getClassstructurePrtypeList(String condition){
		String sql ="select ROWNUM,cp.*,P.PARAMETER_VALUE PRTYPE_VALUE,c.DESCRIPTION from CLASSSTRUCTURE_PRTYPE cp  "
				+ "left join PARAMETER p on cp.PRTYPE=p.PARAMETER_KEY "
				+ "left join CLASSSTRUCTURE c on cp.CLASSSTRUCTUREID=c.CLASSSTRUCTUREID "
				+ "where 1=1 "+condition;
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
        return query.list();
	}
	public void deleteClassstructurePrtypeList(String condition){
		String sql ="delete CLASSSTRUCTURE_PRTYPE where 1=1 "+condition;
        modifyBySQL(sql);
	}
	public void deletePrtypeActiveMember(String prtype){
		String sql ="delete from PRTYPE_ACTIVE_MEMBER where prtype='"+prtype+"'";
        modifyBySQL(sql);
	}
}
