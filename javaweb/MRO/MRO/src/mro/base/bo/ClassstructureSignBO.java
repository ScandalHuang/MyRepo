package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ClassstructureSign;
import mro.base.entity.Item;
import mro.base.entity.LocationMap;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureSignBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getClassstructureSignByItem(List<String> itemList,String siteId){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(Item.class, "itemnum",
				Restrictions.in("itemnum", itemList),
				Restrictions.eqProperty("classstructureid", 
						ClassstructureSign.class.getSimpleName()+".classstructureid")));
		criterions.add((RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
				Restrictions.eq("siteId", siteId),
				Restrictions.eqProperty("organizationCode", 
						ClassstructureSign.class.getSimpleName()+".plantCode"))));
		return commonDAO.query(ClassstructureSign.class,Arrays.asList(Order.asc("classstructureid")), criterions);
	}
	@Transactional(readOnly = true)
	public ClassstructureSign getClassstructureSign(String siteId,String classstructureid){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		criterions.add((RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
				Restrictions.eq("siteId", siteId),
				Restrictions.eqProperty("organizationCode", 
						ClassstructureSign.class.getSimpleName()+".plantCode"))));
		return (ClassstructureSign) commonDAO.uniQuery(ClassstructureSign.class,null, criterions);
	}
}
