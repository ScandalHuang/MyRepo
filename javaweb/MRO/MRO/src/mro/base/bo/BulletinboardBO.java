package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.Bulletinboard;
import mro.base.entity.BulletinboardSite;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.collections.CollectionUtils;
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
public class BulletinboardBO {
	
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
    
	@Transactional(readOnly=true)
    public List<Bulletinboard> getBulletinboard(String postby) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("postby", postby));
		
    	return commonDAO.query(Bulletinboard.class, Arrays.asList(Order.desc("postdate")), criterions);
    }
	@Transactional(readOnly=true)
    public List<Bulletinboard> getBulletinboard(boolean limitDate,List<LocationSiteMap> locationSiteMaps) {
		if(CollectionUtils.isEmpty(locationSiteMaps)) return new ArrayList<Bulletinboard>();
		List criterions=new ArrayList();
		if (limitDate) {
			Date date=new Date();
			criterions.add(RestrictionsUtils.le("postdate", date));
			criterions.add(RestrictionsUtils.ge("expiredate", date));
		}
		criterions.add(RestrictionsUtils.disjunction(
			RestrictionsUtils.exists(BulletinboardSite.class, 
				"id", Restrictions.eqProperty("bulletinboard.bulletinboardid", 
						Bulletinboard.class.getSimpleName()+".bulletinboardid"),
						Restrictions.in("locationSiteMap", locationSiteMaps)),
			RestrictionsUtils.notExists(BulletinboardSite.class, 
				"id", Restrictions.eqProperty("bulletinboard.bulletinboardid", 
						Bulletinboard.class.getSimpleName()+".bulletinboardid"))));
		
    	return commonDAO.query(Bulletinboard.class, Arrays.asList(Order.desc("postdate")), criterions);
    }
}
