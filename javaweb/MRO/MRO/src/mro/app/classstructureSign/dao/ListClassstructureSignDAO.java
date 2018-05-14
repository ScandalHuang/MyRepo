package mro.app.classstructureSign.dao;

import java.util.List;

import mro.base.entity.ClassstructureSign;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureSignDAO extends FactoryBaseDAO{
	
	public ListClassstructureSignDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getOrganizationCode(String condition){
		
		String sql ="SELECT DISTINCT organization_code FROM LOCATION_MAP " +
				"where organization_code is not null " + condition + " order by organization_code ";

		return queryBySQL(sql);
	}
	
	public List getClassstructureSignList(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_SIGN  where 1=1 " + condition + " order by CLASSSTRUCTUREID ";

		return queryBySQLWithEntity(sql,ClassstructureSign.class);
	}
	public ClassstructureSign getClassstructureSign(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_SIGN where 1=1 " + condition + " order by CLASSSTRUCTUREID ";

		return uniQueryBySQLWithEntity(sql,ClassstructureSign.class);
	}
}
