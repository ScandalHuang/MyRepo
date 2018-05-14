package mro.app.mcMgmtInterface.dao;

import java.util.List;

import mro.base.entity.ClassstructureUnit;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureUnitDAO extends FactoryBaseDAO{
	public ListClassstructureUnitDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getClassstructureUnitList(String condition){
		String sql="select * from CLASSSTRUCTURE_UNIT where 1=1 "+condition+" order by CLASSSTRUCTUREID";
		return queryBySQLWithEntity(sql, ClassstructureUnit.class);
	}
}
