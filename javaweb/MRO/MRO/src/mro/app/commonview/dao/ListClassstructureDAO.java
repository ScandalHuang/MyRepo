package mro.app.commonview.dao;

import java.util.List;
import java.util.Map;

import mro.base.entity.Classstructure;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureDAO extends FactoryBaseDAO{
	
	public ListClassstructureDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getchildList(String condition) {

		String sql ="select * "+
                    "from classstructure where 1=1 "+condition+" ORDER BY CLASSSTRUCTUREID";

        return queryBySQLWithEntity(sql,Classstructure.class);
	}
	
	public List getRecursiveClassstructureList(String condition,Map param) {

		String sql ="SELECT (SELECT RTRIM(XMLAGG(XMLELEMENT(e, DESCRIPTION||'/ ') "
				+ "ORDER BY CLASSSTRUCTUREID).EXTRACT('//text()')) "
				+ "FROM CLASSSTRUCTURE WHERE PARENT!='ROOT' "
				+ "START WITH CLASSSTRUCTUREID=A.CLASSSTRUCTUREID "
				+ "CONNECT BY PRIOR PARENT=CLASSSTRUCTUREID) DESCRIPTION,A.* "
				+ "FROM CLASSSTRUCTURE A where 1=1 "+condition+" ORDER BY CLASSSTRUCTUREID";

        return queryBySQLWithEntity(sql,Classstructure.class,param);
	}
	
	public List getClassstructurePhase(Map param) {

		String sql ="SELECT * FROM CLASSSTRUCTURE "
				+ "START WITH CLASSSTRUCTUREID=:CLASSSTRUCTUREID "
				+ "CONNECT BY PRIOR PARENT=CLASSSTRUCTUREID "
				+ "ORDER BY CLASSSTRUCTUREID";

        return queryBySQLWithEntity(sql,Classstructure.class,param);
	}
	
	public Classstructure getClassstructure(String condition) {

		String sql ="select * "+
                    "from classstructure where 1=1 "+condition+" ORDER BY CLASSSTRUCTUREID";

        return uniQueryBySQLWithEntity(sql,Classstructure.class);
	}

}
