package mro.app.reportView.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemListDao;
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
public class ItemListBo {

    private ItemListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String itemtype,String classstructureid,
    		String itemnum,String storageCategory,
    		String description,String locationSite,Date cStrDate,
    		Date cEndDate,String sLocationSite,String secondItem) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemtype)){
			condition.append("AND i.ITEMNUM  like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if(StringUtils.isNotBlank(classstructureid)){
			condition.append("AND i.CLASSSTRUCTUREID = :classstructureid ");
			param.put("classstructureid", classstructureid);
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND i.ITEMNUM  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(storageCategory)){
			condition.append("AND c.STORE_CATEGORY   = :storageCategory ");
			param.put("storageCategory", storageCategory);
		}
		if(StringUtils.isNotBlank(description)){
			condition.append("AND i.description  like :description ");
			param.put("description", description+"%");
		}
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND exists (SELECT 1 FROM ITEM_SITE "
					+ "WHERE ENABLE_FLAG='Y' AND LOCATION_SITE=:locationSite "
					+ "and i.itemnum=ITEMNUM) ");
			param.put("locationSite", locationSite);
		}
		if(cStrDate!=null){
			condition.append("and i.CREATE_DATE >= :cStrDate ");
			param.put("cStrDate", cStrDate);
		}
		if(cEndDate!=null){
			condition.append("and i.CREATE_DATE < :cEndDate ");
			param.put("cEndDate", DateUtils.getAddDate(cEndDate, 1));
		}

		if(StringUtils.isNotBlank(sLocationSite)){
			condition.append("AND exists(SELECT 1 FROM ITEM_SITE "
					+ "WHERE ENABLE_FLAG IS NULL AND LOCATION_SITE=:sLocationSite "
					+ "and i.itemnum=ITEMNUM) ");
			param.put("sLocationSite", sLocationSite);
		}

		if(StringUtils.isNotBlank(secondItem)){
			condition.append("AND i.itemnum in (select (select itemnum "
					+ "from item where itemid=item_attribute.itemid) itemnum  "
					+ "from item_attribute where SECOND_SOURCE_ITEMNUM IS NOT NULL "
					+ "union select SECOND_SOURCE_ITEMNUM "
					+ "from item_attribute where SECOND_SOURCE_ITEMNUM  IS NOT NULL) ");
			param.put("secondItem", secondItem);
		}
	   return dao.getList(condition.toString(),param);
	}
	@Transactional(readOnly = true)
	public Map getOption() {
		List<Object[]> list = dao.getOption();
		Map option=new LinkedHashMap();
		list.stream().forEach(o->option.put(o[0], o[1]));
		return option;
	}
}
