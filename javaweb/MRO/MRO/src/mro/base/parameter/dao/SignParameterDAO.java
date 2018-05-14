package mro.base.parameter.dao;

import java.util.List;

import mro.base.entity.SignParameter;
import mro.base.entity.SignSourceSystem;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class SignParameterDAO extends FactoryBaseDAO{
	
	public SignParameterDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List<SignParameter> getSignParameterList(String condition) {

		String sql ="select * from sign_parameter  where DELTED=0 "+condition+" order by PARAMETER_ID";

        return queryBySQLWithEntity(sql,SignParameter.class);
	}
	public List<SignSourceSystem> getSignSourceSystemList() {

		String sql ="select * from SIGN_SOURCE_SYSTEM  where DELTED=0 order by SOURCE_SYSTEM_ID";

        return queryBySQLWithEntity(sql,SignSourceSystem.class);
	}
}
