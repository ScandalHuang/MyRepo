package mro.app.classstructureSign.dao;

import java.util.List;

import mro.base.entity.ClassstructureApplySign;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureApplySignDAO extends FactoryBaseDAO{
	public ListClassstructureApplySignDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getClassstructureApplySignList(String condition){
		String sql="select * from CLASSSTRUCTURE_APPLY_SIGN where 1=1 "+condition+" order by CLASSSTRUCTUREID";
        return queryBySQLWithEntity(sql, ClassstructureApplySign.class);
	}
	
	public ClassstructureApplySign getClassstructureApplySign(String condition){
		String sql="select * from CLASSSTRUCTURE_APPLY_SIGN where 1=1 "+condition+" ";
        return uniQueryBySQLWithEntity(sql, ClassstructureApplySign.class);
	}
}
