package mro.app.applyQuery.dao;

import mro.base.entity.AItemspec;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemQueryDao extends FactoryBaseDAO{
	
	public ApplyItemQueryDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public AItemspec getAItemspec(String condition) {

		String sql ="select * "+
                    "from  a_itemspec where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql, AItemspec.class);
	}
}
