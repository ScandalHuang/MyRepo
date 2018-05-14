package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ClassstructureSubinventory;
import mro.base.entity.LocationMap;
import mro.base.entity.Prline;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureSubinventoryBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public ClassstructureSubinventory getSubinventor(String classstructureid,String siteid){
		List criterions = new ArrayList();
		String main=ClassstructureSubinventory.class.getSimpleName();
		criterions.add(Restrictions.eq("classstructureid", classstructureid));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, 
				"organizationCode", 
				Restrictions.eqProperty("organizationCode", main+".plantCode"),
				Restrictions.eq("siteId", siteid)));
		
		return (ClassstructureSubinventory) commonDAO.uniQuery(ClassstructureSubinventory.class,null, criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.05.29
	 * 驗證PRLINE的預設倉別是否有誤
	 * return 錯誤的類別LIST
	 */
	@Transactional(readOnly=true)
    public List<ClassstructureSubinventory> validatePrlineBySubinventory(List<Prline> prlines) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		String main=ClassstructureSubinventory.class.getSimpleName();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("classstructureid", prline.getClassstructureid()),
					RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
							Restrictions.eqProperty("organizationCode", main+".plantCode"),
							Restrictions.eq("siteId", prline.getSiteid())),
					Restrictions.ne("subinventory", prline.getStoreroom())));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		
		return  commonDAO.query(ClassstructureSubinventory.class, 
				Arrays.asList(Order.asc("classstructureid")), criterions);
	}
}