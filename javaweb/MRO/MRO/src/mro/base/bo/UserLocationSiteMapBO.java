package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.UserLocationSiteMap;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class UserLocationSiteMapBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<UserLocationSiteMap> getList(String empNo){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("empNo", empNo));	
		return commonDAO.query(UserLocationSiteMap.class,
				Arrays.asList(Order.asc("locationSiteMap.locationSite")), criterions,
				new CtriteriaFetchMode("locationSiteMap",Criteria.LEFT_JOIN));
	}
	@Transactional(readOnly=false)
	public void update(List<LocationSiteMap> adds,List<UserLocationSiteMap> deletes,
			Person addPerson,Person login){
		commonDAO.delete(deletes);
		adds.stream().forEach(a->{
			commonDAO.insertUpdate(new UserLocationSiteMap(null,addPerson.getPersonId(),
					a,login.getPersonId(),new Date(System.currentTimeMillis())));
		});
		
	}
}
