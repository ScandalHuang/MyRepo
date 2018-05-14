package mro.app.overview.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import mro.app.overview.dao.R2InvbalancesListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class R2InvbalancesListBo {

    private R2InvbalancesListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new R2InvbalancesListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String location,
    		String itemnum,String description,String deptNo) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND exists ( select 1 from location_map lm where A1.siteid=lm.site_id "
					+ "and location_site = :locationSite) ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and exists (SELECT   1 FROM   LOCATION_MAP WHERE   site_id = a1.siteid "
					+ "and ORGANIZATION_CODE in (:organizationCode)) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(location)){
			condition.append("AND a1.LOCATION like :location ");
			param.put("location", location+"%");
		}
		
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND a1.itemnum like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		
		if(StringUtils.isNotBlank(description)){
			condition.append("AND a5.description  like :description ");
			param.put("description", description+"%");
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND a1.DEPTCODE  = :deptNo ");
			param.put("deptNo", deptNo);
		}
		
	   return dao.getList(condition.toString(),param);
	}
	
	@Transactional(readOnly=true)
    public Map getOrgs(String locationSite) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			param.put("locationSite", locationSite);
		}else{
			return null;
		}
		List<Map> list=dao.getOrgs(param);
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l.get("ORGANIZATION_NAME"), l.get("ORGANIZATION_CODE")));
		return option;
	}
}
