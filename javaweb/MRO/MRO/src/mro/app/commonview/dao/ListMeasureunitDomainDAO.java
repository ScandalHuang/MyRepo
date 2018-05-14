package mro.app.commonview.dao;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListMeasureunitDomainDAO extends FactoryBaseDAO{
	public ListMeasureunitDomainDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public BigDecimal getMeasureunitDomainCount(String condition){
		
		String sql ="select count(1) "+
                "from MEASUREUNIT_DOMAIN where 1=1 "+condition;

		return (BigDecimal) uniQueryBySQL(sql);
	}
}
