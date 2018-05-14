package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemControlListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ItemControlListBo {

    private ItemControlListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemControlListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String itemtype,String itemnum) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND l.LOCATION_SITE = :locationSite ");
			param.put("locationSite", locationSite);
		}
		if(StringUtils.isNotBlank(itemtype)){
			condition.append("AND iv.itemnum  like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND iv.itemnum  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		
	   return dao.getList(itemtype, condition.toString(),param);
	}
}
