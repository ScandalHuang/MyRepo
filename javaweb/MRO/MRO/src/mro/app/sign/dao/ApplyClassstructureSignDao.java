package mro.app.sign.dao;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyClassstructureSignDao extends FactoryBaseDAO{
	
	public ApplyClassstructureSignDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
