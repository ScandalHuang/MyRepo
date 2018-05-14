package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.lang3.StringUtils;
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
public class LocationSiteMapBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
	public Map<String,String> getOption(){
		Map<String,String> map=new LinkedHashMap<String,String>();
		for(LocationSiteMap s:getLocationSiteMapList()){
			map.put(s.getLocationSite(), s.getLocationSite());
		}
		return map;
	}
	@Transactional(readOnly=true)
	public List<LocationSiteMap> getLocationSiteMapList(){
		return  commonDAO.query(LocationSiteMap.class, Arrays.asList(Order.asc("locationSite")), null);
	}
	@Transactional(readOnly=true)
	public LocationSiteMap getLocationSiteMapByOrg(String organizationCode){
		if(StringUtils.isBlank(organizationCode)) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "locationSiteMap.locationSite", 
			Restrictions.eq("organizationCode", organizationCode),
			Restrictions.eqProperty("locationSiteMap.locationSite", LocationSiteMap.class.getSimpleName()+".locationSite")));
		return  (LocationSiteMap) commonDAO.uniQuery(LocationSiteMap.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public LocationSiteMap getLocationSiteMapBySiteId(String siteId){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "locationSiteMap.locationSite", 
			Restrictions.eq("siteId", siteId),
			Restrictions.eqProperty("locationSiteMap.locationSite", LocationSiteMap.class.getSimpleName()+".locationSite")));
		return  (LocationSiteMap) commonDAO.uniQuery(LocationSiteMap.class, null, criterions);
	}
	public Map getMap(List<LocationSiteMap> LocationSiteMaps){
    	Map map=new LinkedHashMap<>();
    	for(LocationSiteMap l:LocationSiteMaps){
    		map.put(l.getLocationSite(),l.getLocationSite());
    	}
		return map;
	}
}
