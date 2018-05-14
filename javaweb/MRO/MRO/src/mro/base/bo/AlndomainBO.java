package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Alndomain;
import mro.base.entity.ClassstructureLineApply;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class AlndomainBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Map getDomainIdCount(String classstructureid) {
		List criterions = new ArrayList();
		Map toDoMap=new HashMap();
		ProjectionList projectionList = Projections.projectionList();

		criterions.add(RestrictionsUtils.like("domainid", classstructureid,MatchMode.START));
		criterions.add(Restrictions.or(Restrictions.isNull("inactiveDate"),
				RestrictionsUtils.ge("inactiveDate", new Date())));

		projectionList.add(Projections.groupProperty("domainid"));
		projectionList.add(Projections.rowCount());
		List<Object[] > list=commonDAO.query(Alndomain.class, null, criterions, projectionList);
		for(Object[] o:list){
			toDoMap.put(o[0], ((Long)o[1]).intValue());
		}
		return  toDoMap;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Map<String,String>> getMap(String classstructureid,boolean active){
		Map<String, Map<String,String>> map=new HashMap<String, Map<String,String>>();
		for(Alndomain m:getList(classstructureid,active)){
			String assetattrid=m.getDomainid().replace(classstructureid, "");
			if(map.get(assetattrid)==null){
				map.put(assetattrid, new HashMap<String,String>());
			}
			map.get(assetattrid).put(m.getDescription().toUpperCase(), m.getDescription().toUpperCase());
		}
		return map;
	}
	@Transactional(readOnly = true)
	public List<Alndomain> getAlndomain(BigDecimal alndomainid) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.eq("alndomainid", alndomainid));
		return  (List<Alndomain>) commonDAO.uniQuery(Alndomain.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public List<Alndomain> getList(String domainid,boolean inactive) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.like("domainid", domainid,MatchMode.START));
		if(inactive){
			criterions.add(Restrictions.or(Restrictions.isNull("inactiveDate"),
					RestrictionsUtils.ge("inactiveDate", new Date())));
		}
		return  commonDAO.query(Alndomain.class, 
				Arrays.asList(Order.asc("domainid"),Order.asc("description")), criterions);
	}
	
	 /*
	  * 屬性編號+清單敘述是否存在
	  */
	@Transactional(readOnly = true)
	public List<Alndomain> getAlndomain(List<ClassstructureLineApply> validateList,Boolean inactive) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(ClassstructureLineApply c:validateList){
				criterionL.add(RestrictionsUtils.conjunction(
						Restrictions.eq("domainid",c.getDomainid()),
						Restrictions.ilike("description",c.getDescription())));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		if(inactive!=null && inactive){
			criterions.add(Restrictions.or(Restrictions.isNull("inactiveDate"),
					RestrictionsUtils.ge("inactiveDate", new Date())));
		}else if(inactive!=null && !inactive){
			criterions.add(RestrictionsUtils.le("inactiveDate", new Date()));
		}
		return commonDAO.query(Alndomain.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public Alndomain getAlndomain(String domainid,String description) {
		List criterions = new ArrayList();

		criterions.add(Restrictions.eq("domainid",domainid));
		criterions.add(Restrictions.ilike("description",description));
		return  (Alndomain) commonDAO.uniQuery(Alndomain.class, null, criterions);
	}
}