package mro.app.reportView.bo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemSiteListDao;
import mro.utility.DateUtils;

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
public class ItemSiteListBo {

    private ItemSiteListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemSiteListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String itemnum,String enableFlag,String locationSite,
    		String plant ,Date strDate,Date endDate) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND A.ITEMNUM like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(enableFlag)){
			if(enableFlag.toUpperCase().indexOf("NULL")!=-1)
				condition.append("AND A.ENABLE_FLAG  is null ");
			else
				condition.append("AND A.ENABLE_FLAG  = :enableFlag ");
			param.put("enableFlag", enableFlag);
		}
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND A.LOCATION_SITE  = :locationSite ");
			param.put("locationSite", locationSite);
		}
		if(StringUtils.isNotBlank(plant)){
			condition.append("AND C.ORGANIZATION_CODE  = :plant ");
			param.put("plant", plant);
		}
		if(strDate!=null){
			condition.append("AND  A.LAST_UPDATE_DATE  >= :strDate ");
			param.put("strDate", strDate);
		}
		if(endDate!=null){
			condition.append("AND  A.LAST_UPDATE_DATE  < :endDate ");
			param.put("endDate", DateUtils.getAddDate(endDate,1));
		}
	   return dao.getList(condition.toString(),param);
	}
}
