package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ClassstructureItemSiteSign;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureItemSiteSignBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public ClassstructureItemSiteSign get(String classstructureid,String locationSite){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		
		return (ClassstructureItemSiteSign) commonDAO.uniQuery(ClassstructureItemSiteSign.class,null, criterions);
	}
}
