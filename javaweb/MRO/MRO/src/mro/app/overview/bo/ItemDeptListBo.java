package mro.app.overview.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.overview.dao.ItemDeptListDao;

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
public class ItemDeptListBo {

    private ItemDeptListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemDeptListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String deptNo) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND exists(SELECT 1 FROM LOCATION_MAP "
					+ "WHERE SITE_ID=A.SITEID and LOCATION_SITE=:locationSite) ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and exists(SELECT 1 FROM LOCATION_MAP "
					+ "WHERE  SITE_ID=A.SITEID and ORGANIZATION_CODE in (:organizationCode)) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND id.deptcode  like  :deptNo ");
			param.put("deptNo", deptNo+"%");
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
