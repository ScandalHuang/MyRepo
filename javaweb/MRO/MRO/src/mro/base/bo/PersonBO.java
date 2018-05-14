package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.Person;

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
public class PersonBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
	public Person getPerson(String personId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("personId", personId));
		return  (Person) commonDAO.uniQuery(Person.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public Person getActivePerson(String personId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("personId", personId));
		criterions.add(RestrictionsUtils.eq("status", "ACTIVE"));
		return  (Person) commonDAO.uniQuery(Person.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public List<Person> getPersonList(String value,boolean activeStatus) {
		List criterions = new ArrayList();
		Date date=new Date();
		if(activeStatus){
			criterions.add(RestrictionsUtils.eq("status", "ACTIVE"));
		}
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.like("personId", value, MatchMode.START),
				RestrictionsUtils.like("displayName", value, MatchMode.START),
				RestrictionsUtils.like("deptCode", value, MatchMode.START),
				RestrictionsUtils.like("MDeptCode", value, MatchMode.START)));
		return commonDAO.query(Person.class, Arrays.asList(Order.asc("personId")), criterions);
	}
	@Transactional(readOnly=true)
	public List<Person> getPersonList(String personId,String empName,String deptCode,
			String supervisorNo,String supervisorName) {
		List criterions = new ArrayList();
		Date date=new Date();
		criterions.add(RestrictionsUtils.like("personId", personId,MatchMode.START));
		criterions.add(RestrictionsUtils.like("displayName", empName,MatchMode.START));
		criterions.add(RestrictionsUtils.like("deptCode", deptCode,MatchMode.START));
		criterions.add(RestrictionsUtils.like("supervisor", supervisorNo,MatchMode.START));
		criterions.add(RestrictionsUtils.like("supervisorName", supervisorName,MatchMode.START));
		return commonDAO.query(Person.class, Arrays.asList(Order.asc("personId")), criterions);
	}
}