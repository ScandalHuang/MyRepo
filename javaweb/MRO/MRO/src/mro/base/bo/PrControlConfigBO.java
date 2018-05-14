package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import mro.base.entity.Inventory;
import mro.base.entity.Item;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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
public class PrControlConfigBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrControlConfig prControlConfig) {
		commonDAO.delete(prControlConfig);
	}
	@Transactional(readOnly = false)
	public void update(PrControlConfig prControlConfig,Person person) {
		prControlConfig.setLastUpdateBy(person.getPersonId());
		prControlConfig.setLastUpdate(new Date(System.currentTimeMillis()));
		commonDAO.insertUpdate(prControlConfig);
	}
	@Transactional(readOnly = true)
	public PrControlConfig getPrControlConfig(Inventory inventory) {
		
		return  getPrControlConfigBySiteId(inventory.getItemnum(), inventory.getSiteid());
	}
	
	@Transactional(readOnly = true)
	public PrControlConfig getPrControlConfigBySiteId(String itemnum,String siteId) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(Item.class, "commoditygroup", 
				Restrictions.eq("itemnum", itemnum),
				Restrictions.eqProperty("commoditygroup",PrControlConfig.class.getSimpleName()+".commoditygroup")));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "siteId", 
				Restrictions.eq("siteId", siteId),
				Restrictions.eqProperty("locationSiteMap.locationSite",PrControlConfig.class.getSimpleName()+".locationSite")));
		return (PrControlConfig) commonDAO.uniQuery(PrControlConfig.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public PrControlConfig getPrControlConfig(String commoditygroup,String locationSite) {
		List<PrControlConfig> list=this.getList(commoditygroup, locationSite);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Transactional(readOnly = true)
	public List getList(String commoditygroup,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("commoditygroup", commoditygroup));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		return  commonDAO.query(PrControlConfig.class, Arrays.asList(Order.asc("locationSite")), criterions);
	}
	@Transactional(readOnly = true)
	public List getList(List<String> locationSites) {
		if(CollectionUtils.isEmpty(locationSites)) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("locationSite",locationSites));
		return  commonDAO.query(PrControlConfig.class, Arrays.asList(Order.asc("locationSite")), criterions);
	}
}
