package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.Person;
import mro.base.entity.PrControlConfig;
import mro.base.entity.PrtypeEpr;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class PrtypeEprBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrtypeEpr prtypeEpr) {
		commonDAO.delete(prtypeEpr);
	}
	@Transactional(readOnly = false)
	public void update(PrtypeEpr prtypeEpr,Person person) {
		prtypeEpr.setLastUpdateBy(person.getPersonId());
		prtypeEpr.setLastUpdate(new Date(System.currentTimeMillis()));
		commonDAO.insertUpdate(prtypeEpr);
	}
	
	@Transactional(readOnly = true)
	public PrtypeEpr getPrtypeEpr(String prtype,String locationSite) {
		List<PrtypeEpr> list=this.getList(prtype, locationSite);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Transactional(readOnly = true)
	public List getList(String prtype,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		return  commonDAO.query(PrtypeEpr.class, Arrays.asList(Order.asc("locationSite")), criterions);
	}

	@Transactional(readOnly = true)
	public List getList(List<String> locationSites) {
		if(CollectionUtils.isEmpty(locationSites)) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("locationSite",locationSites));
		return  commonDAO.query(PrtypeEpr.class, Arrays.asList(Order.asc("locationSite")), criterions);
	}
}
