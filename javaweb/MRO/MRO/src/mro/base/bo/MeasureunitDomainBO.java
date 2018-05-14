package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.MeasureunitDomain;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MeasureunitDomainBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public Map getMap(String classstructureid,boolean active){
		Map<String, Map> map=new HashMap<String, Map>();
		for(MeasureunitDomain m:getList(classstructureid,active)){
			String assetattrid=m.getDomainid().replace(classstructureid, "");
			if(map.get(assetattrid)==null){
				map.put(assetattrid, new HashMap<String,String>());
			}
			map.get(assetattrid).put(m.getDescription().toUpperCase(), m.getDescription().toUpperCase());
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public List<MeasureunitDomain> getList(String domainid,boolean inactive) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.like("domainid", domainid,MatchMode.START));
		if(inactive){
			criterions.add(Restrictions.or(Restrictions.isNull("inactiveDate"),
					RestrictionsUtils.ge("inactiveDate", new Date())));
		}
		return  commonDAO.query(MeasureunitDomain.class, 
				Arrays.asList(Order.asc("domainid"),Order.asc("description")), criterions);
	}
	@Transactional(readOnly = true)
	public Long getCount(String domainid,boolean inactive) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.like("domainid", domainid,MatchMode.START));
		if(inactive){
			criterions.add(Restrictions.or(Restrictions.isNull("inactiveDate"),
					RestrictionsUtils.ge("inactiveDate", new Date())));
		}
		return  commonDAO.queryCount(MeasureunitDomain.class, criterions);
	}
	
}
