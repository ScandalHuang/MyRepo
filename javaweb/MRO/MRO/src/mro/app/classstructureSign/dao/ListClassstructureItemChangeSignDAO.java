package mro.app.classstructureSign.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureItemchangeSign;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureItemChangeSignDAO extends FactoryBaseDAO{
	
	public ListClassstructureItemChangeSignDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getClassstructure(String condition){
		
		String sql ="SELECT * FROM CLASSSTRUCTURE " +
				"where haschildren=0 and (inactive_date>sysdate or inactive_date is null) " + condition;

		return queryBySQLWithEntity(sql,Classstructure.class);
	}
	
	public List getClassstructureItemchangeSignList(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_ITEMCHANGE_SIGN where 1=1 " + condition + " order by plant_code ";

		return queryBySQLWithEntity(sql,ClassstructureItemchangeSign.class);
	}
	
	public List getOrganizationCode(String condition){
		
		String sql ="SELECT DISTINCT organization_code FROM LOCATION_MAP " +
				"where organization_code is not null " + condition + " order by organization_code ";

		return queryBySQL(sql);
	}
	
	public int getClassstructureItemChangeSignSize(String condition){
		
		String sql ="select count(1) from CLASSSTRUCTURE_ITEMCHANGE_SIGN where 1=1 "+condition;
		return ((BigDecimal) uniQueryBySQL(sql)).intValue();
	}
}
