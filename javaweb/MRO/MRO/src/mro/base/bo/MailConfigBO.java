package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.MailConfig;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inx.commons.dao.CommonDAO;

@Component
public class MailConfigBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public  MailConfig getMailConfig(String appId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("appId", appId));
		
		return (MailConfig) commonDAO.uniQuery(MailConfig.class,null, criterions);
	}
}
