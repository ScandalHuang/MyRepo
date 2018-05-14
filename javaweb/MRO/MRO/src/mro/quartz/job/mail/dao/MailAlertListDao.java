package mro.quartz.job.mail.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import com.inx.commons.dao.FactoryBaseDAO;


public class MailAlertListDao extends FactoryBaseDAO{
	
	public MailAlertListDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public List getSQLList (String sql){
		return queryBySQLToLinkMap(sql, null);
	}
}

