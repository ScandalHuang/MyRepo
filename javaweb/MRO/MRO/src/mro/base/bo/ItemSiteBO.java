package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ItemSite;
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
public class ItemSiteBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public ItemSite getItemSite(String itemnum,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		return  (ItemSite) commonDAO.uniQuery(ItemSite.class,null, criterions);
	}
	
	@Transactional(readOnly = true)
	public ItemSite getItemSiteByOrgCode(String itemnum,String organizationCode) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("enableFlag", "Y"));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
				Restrictions.eqProperty("locationSiteMap.locationSite",ItemSite.class.getSimpleName()+".locationSite"),
				Restrictions.eq("organizationCode", organizationCode)));
		
		return  (ItemSite) commonDAO.uniQuery(ItemSite.class,null, criterions);
	}

	@Transactional(readOnly = true)
	public ItemSite getItemSiteByNonValueP(String itemnum,String nonvaluePlant) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("enableFlag", "Y"));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "nonvaluePlant", 
				Restrictions.eqProperty("locationSiteMap.locationSite",ItemSite.class.getSimpleName()+".locationSite"),
				Restrictions.eq("nonvaluePlant", nonvaluePlant)));
		
		return  (ItemSite) commonDAO.uniQuery(ItemSite.class,null, criterions);
	}
	
	@Transactional(readOnly = true)
	public List getItemSiteList(String itemnum,boolean activeFlag) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		if(activeFlag){
			criterions.add(RestrictionsUtils.eq("enableFlag", "Y"));
		}else{
			criterions.add(RestrictionsUtils.disjunction(
					RestrictionsUtils.eq("enableFlag", "N"),
					Restrictions.isNull("enableFlag")));
		}
		
		return  commonDAO.query(ItemSite.class, Arrays.asList(Order.asc("locationSite")), criterions);
	}

	@Transactional(readOnly = true)
	public Long getCountExists(List<String> itemList,String locationSite){
		List criterions = new ArrayList();
		criterions.add(Restrictions.isNull("enableFlag"));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		criterions.add(RestrictionsUtils.in("itemnum", itemList));
		return commonDAO.queryCount(ItemSite.class, criterions);
	}
	@Transactional(readOnly = true)
	public List getItemSiteBySite(List<String> itemList,String locationSite){
		List criterions = new ArrayList();
		criterions.add(Restrictions.isNull("enableFlag"));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		criterions.add(RestrictionsUtils.in("itemnum", itemList));
		return commonDAO.query(ItemSite.class,Arrays.asList(Order.asc("lastUpdateDate")), criterions);
	}
}