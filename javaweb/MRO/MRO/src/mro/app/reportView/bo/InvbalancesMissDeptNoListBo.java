package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.InvbalancesMissDeptNoListDao;

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
public class InvbalancesMissDeptNoListBo {
    private InvbalancesMissDeptNoListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new InvbalancesMissDeptNoListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String location,
    		String itemtype,String itemnum) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND exists (SELECT   1 "
					+ "FROM   LOCATION_MAP WHERE   SITE_ID = SITEID and LOCATION_SITE=:locationSite) ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and  exists (SELECT  1 FROM   LOCATION_MAP "
					+ " WHERE   SITE_ID = SITEID and ORGANIZATION_CODE in (:organizationCode)) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(location)){
			condition.append("AND IB.LOCATION like :location ");
			param.put("location", location+"%");
			
		}if(StringUtils.isNotBlank(itemtype)){
			condition.append("AND IB.ITEMNUM like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND IB.ITEMNUM like :itemnum ");
			param.put("itemnum", itemnum+"%");
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
