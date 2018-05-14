package mro.app.commonview.dao;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListPrlineDAO extends FactoryBaseDAO{
	
	public ListPrlineDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
}
