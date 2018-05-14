package mro.app.classstructureSign.dao;

import java.util.List;

import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureItemSign;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureItemSignDAO extends FactoryBaseDAO{
	
	public ListClassstructureItemSignDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getClassstructure(String condition){
		
		String sql ="SELECT * FROM CLASSSTRUCTURE " +
				"where haschildren=0 and (inactive_date>sysdate or inactive_date is null) " + condition;

		return queryBySQLWithEntity(sql,Classstructure.class);
	}
	
	public List getClassstructureItemSignList(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_ITEM_SIGN where 1=1 " + condition + " order by plant_code ";

		return queryBySQLWithEntity(sql,ClassstructureItemSign.class);
	}
	
	public List getOrganizationCode(String condition){
		
		String sql ="SELECT DISTINCT organization_code FROM LOCATION_MAP " +
				"where organization_code is not null " + condition + " order by organization_code ";

		return queryBySQL(sql);
	}

}
