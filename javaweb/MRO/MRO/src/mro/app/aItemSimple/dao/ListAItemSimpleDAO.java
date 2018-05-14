package mro.app.aItemSimple.dao;

import java.util.List;

import mro.base.entity.AItemSimple;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListAItemSimpleDAO extends FactoryBaseDAO{
	
	public ListAItemSimpleDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List<AItemSimple> getAItemSimpleList(String condition){
		
		String sql ="SELECT * FROM A_ITEM_SIMPLE " +
				"where 1=1 " + condition +" order by UPLOAD_DATE desc";
        return queryBySQLWithEntity(sql, AItemSimple.class);
	}
	public AItemSimple getAItemSimple(String condition){
		
		String sql ="SELECT * FROM A_ITEM_SIMPLE " +
				"where 1=1 " + condition +" order by UPLOAD_DATE desc";
        return uniQueryBySQLWithEntity(sql, AItemSimple.class);
	}
	
}
