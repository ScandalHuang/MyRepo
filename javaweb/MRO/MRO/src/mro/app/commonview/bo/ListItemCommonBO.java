package mro.app.commonview.bo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.commonview.dao.ListItemCommonDAO;
import mro.app.reportView.dao.R2LTListDao;
import mro.app.util.SystemUtils;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ListItemCommonBO {

    private ListItemCommonDAO listItemCommonDAO;
    private R2LTListDao r2LTListDao;
    
    @Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		listItemCommonDAO=new ListItemCommonDAO(sessionFactory);
		r2LTListDao=new R2LTListDao(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public Item getItem(String itemnum){
		return listItemCommonDAO.getItem(itemnum);
	}
	@Transactional(readOnly = true)
	public Item getItem(BigDecimal itemid){
		return listItemCommonDAO.getItem(itemid);
	}
	@Transactional(readOnly = true)
	public String getOriItemDescription(BigDecimal itemid){
		Item item=listItemCommonDAO.getItem(itemid);
		return item==null?null:item.getDescription();
	}
	@Transactional(readOnly = true)
	public Map getR2ItemLt(String itemnum){
		return r2LTListDao.getItemLt(itemnum);
	}
	
	
	/* 產生料號規格異動清單
	 * hongjie.wu
	 * 2013/11/4
	 * */
	@Transactional(readOnly = true)
	public List<Item> getItemChangeListTemp(String classstructureid) {

		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(classstructureid)) {
			condition.append("and ITEM.CLASSSTRUCTUREID in " +
					"("+classstructureid+" )");
		}
		condition.append("and status='"+SignStatus.ACTIVE+"'");
		condition.append(" and  itemnum not in( "
				+ "select ORIITEMNUM from a_item where status in ('NEW','INPRG') "
				+ "AND ORIITEMNUM IS NOT NULL)");
		
		return listItemCommonDAO.getItemList(condition.toString());
	}
	
	@Transactional(readOnly = true)
	public ItemAttribute getItemAttribute(int itemid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and itemid = " + itemid + " ");
		
		ItemAttribute a=listItemCommonDAO.getItemAttribute(condition.toString());

		return a==null?new ItemAttribute():a;
	}
	
	@Transactional(readOnly = true)
	public List<Item>  getItemList(String itemnumType,String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		if(StringUtils.isNotBlank(itemnumType)){
			condition.append(" and itemnum like '"+itemnumType+"%' ");
		}
		return listItemCommonDAO.getItemList(condition.toString()); 
	}
	@Transactional(readOnly=true)
    public  List getItemMappingList(String[] columnName,Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listItemCommonDAO.getItemMappingList(condition.toString());
    }
	@Transactional(readOnly = true)
	public List<LocationSiteMap>  getLocationSiteMapList(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		return listItemCommonDAO.getLocationSiteMapList(condition.toString()); 
	}
	
	@Transactional(readOnly = true)
	public Map getSiteMap(){ //TABLE:LOCATION_SITE_MAP也須更新
		Map map=new LinkedHashMap();
		for(LocationSiteMap itemSite:getLocationSiteMapList(null)){
			map.put(itemSite.getLocationSite(), itemSite.getLocationSite());
		}
		return map;
	}
}