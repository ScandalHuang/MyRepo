package mro.quartz.job.mail.bo;

import java.util.List;

import mro.quartz.job.mail.dao.MailAlertListDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class MailAlertListBo {

    private MailAlertListDao mailAlertListDao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	mailAlertListDao=new MailAlertListDao(sessionFactory);
    }
    @Transactional(readOnly=true)
    public List getSQLList (String sql){
    	return mailAlertListDao.getSQLList(sql);
    }
}
