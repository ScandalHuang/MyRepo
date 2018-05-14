package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ClassstructureUnit;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class ClassstructureUnitBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public ClassstructureUnit getClassstructureUnit(String classstructureid){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("classstructureid", classstructureid));
		return (ClassstructureUnit) commonDAO.uniQuery(ClassstructureUnit.class,null, criterions);
	}
}
