package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ClassstructureGp;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureGpBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid",
				classstructureid));
		return commonDAO.query(ClassstructureGp.class,
				Arrays.asList(Order.asc("classstructureid")), criterions);
	}
	@Transactional(readOnly = true)
	public ClassstructureGp getClassstructureGp(String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid",
				classstructureid));
		return (ClassstructureGp) commonDAO.uniQuery(ClassstructureGp.class,null, criterions);
	}

	@Transactional(readOnly = false)
	public void delete(ClassstructureGp[] list) {
		commonDAO.delete(list);
	}
}
