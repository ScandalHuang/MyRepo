package mro.app.classstructureSign.dao;

import java.util.List;

import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureItemSiteSign;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClassstructureItemSiteSignDAO extends FactoryBaseDAO{
	
	public ListClassstructureItemSiteSignDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getClassstructure(String condition){
		
		String sql ="SELECT * FROM CLASSSTRUCTURE " +
				"where haschildren=0 and (inactive_date>sysdate or inactive_date is null) " + condition;

		return queryBySQLWithEntity(sql,Classstructure.class);
	}
	
	public List getClassstructureItemSiteSignList(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_ITEM_SITE_SIGN where 1=1 " + condition + " order by CLASSSTRUCTUREID ";

		return queryBySQLWithEntity(sql,ClassstructureItemSiteSign.class);
	}
	public ClassstructureItemSiteSign getClassstructureItemSiteSign(String condition){
		
		String sql ="select * "+
                "from CLASSSTRUCTURE_ITEM_SITE_SIGN where 1=1 " + condition + " order by CLASSSTRUCTUREID ";

		return uniQueryBySQLWithEntity(sql,ClassstructureItemSiteSign.class);
	}
	
}
