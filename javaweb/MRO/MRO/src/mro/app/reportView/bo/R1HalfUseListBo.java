package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.R1HalfUseListDao;

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
public class R1HalfUseListBo {

    private R1HalfUseListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new R1HalfUseListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String deptNo,
    		String itemnum,String location) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND lm.LOCATION_SITE = :locationSite ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and lm.ORGANIZATION_CODE in (:organizationCode) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND a.M_BINNUM  = :deptNo ");
			param.put("deptNo", deptNo);
		}
		
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND a.itemnum in ( SELECT   ITEMNUM "
					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemnum "
					+ "UNION (SELECT   OLD_MATNR FROM   ITEM_MAPPING "
					+ "WHERE       NEW_MATNR LIKE :itemnum "
					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(location)){
			condition.append("AND a.STORELOC like :location ");
			param.put("location", location+"%");
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