package mro.base.bo;

import java.util.List;

import mro.base.entity.SignSourceSystem;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class SignSourceSystemBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
	public List<SignSourceSystem> getList (){
		return commonDAO.query(SignSourceSystem.class, null, null);
	}
}
