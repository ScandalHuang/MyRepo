package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class LocationMapBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
	public LocationMap getLocationMapBySId(String siteId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("siteId", siteId));
		return  (LocationMap) commonDAO.uniQuery(LocationMap.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public LocationMap getLocationMapByOldsiteId(String oldsiteId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("oldsiteId", oldsiteId));
		return  (LocationMap) commonDAO.uniQuery(LocationMap.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public LocationMap getLocationMapByOcode(String organizationCode){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("organizationCode", organizationCode));
		return  (LocationMap) commonDAO.uniQuery(LocationMap.class, null, criterions, 
				new CtriteriaFetchMode("locationSiteMap",Criteria.LEFT_JOIN));
	}
	@Transactional(readOnly=true)
	public List<LocationMap> getLocationMapList(String locationSite){
		return this.getLocationMapList(StringUtils.isNotBlank(locationSite)?
				Arrays.asList(new LocationSiteMap(locationSite)):null);
	}
	@Transactional(readOnly=true)
	public List<LocationMap> getLocationMapList(LocationSiteMap locationSiteMap){
		return this.getLocationMapList(Arrays.asList(locationSiteMap));
		
	}
	@Transactional(readOnly=true)
	public List<LocationMap> getLocationMapList(List<LocationSiteMap> locationSiteMap){
		List criterions = new ArrayList();
		criterions.add(Restrictions.isNotNull("siteId"));
		criterions.add(Restrictions.isNotNull("locationSiteMap.locationSite"));
		criterions.add(RestrictionsUtils.in("locationSiteMap", locationSiteMap));
		return  commonDAO.query(LocationMap.class, Arrays.asList(Order.asc("organizationName")), 
				criterions,new CtriteriaFetchMode("locationSiteMap",Criteria.LEFT_JOIN));
	}
	@Transactional(readOnly=true)
	public List<LocationMap> getLocationAllList(){
		List criterions = new ArrayList();
		return  commonDAO.query(LocationMap.class, 
				Arrays.asList(Order.asc("locationSiteMap.locationSite"),Order.asc("organizationName")), 
				criterions);
	}
	public Map getLocationMapOptionByCodeSite(List<LocationMap> locationMaps){
    	Map map=new LinkedHashMap<>();
    	for(LocationMap l:locationMaps){
    		map.put(l.getOrganizationCode(), l.getSiteId());
    	}
		return map;
	}
	public Map getLocationMapOptionBySite(List<LocationMap> locationMaps){
    	Map map=new LinkedHashMap<>();
    	for(LocationMap l:locationMaps){
    		map.put(l.getOrganizationName(), l.getSiteId());
    	}
		return map;
	}
	public Map getLocationMapOptionByOCode(List<LocationMap> locationMaps){
    	Map map=new LinkedHashMap<>();
    	for(LocationMap l:locationMaps){
    		map.put(l.getOrganizationName(), l.getOrganizationCode());
    	}
		return map;
	}
	@Transactional(readOnly=true)
	public Map getLocationMapAll(List<LocationMap> list){
    	Map map=new LinkedHashMap<>();
    	for(LocationMap l:list){
    		if(l.getSiteId()!=null && l.getOrganizationCode()!=null){
	    		map.put(l.getSiteId(), l.getOrganizationName());
	    		map.put(l.getOrganizationCode(),l.getOrganizationName());
    		}
    	}
		return map;
	}
}
