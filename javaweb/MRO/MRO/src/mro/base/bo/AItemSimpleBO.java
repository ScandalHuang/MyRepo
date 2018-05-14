package mro.base.bo;

import mro.base.entity.AItemSimple;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class AItemSimpleBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = false)
	public void updateErrorLog(AItemSimple aItemSimple,String errorLog){
		aItemSimple.setErrorLog(errorLog);
		commonDAO.insertUpdate(aItemSimple);
	}
	
}
