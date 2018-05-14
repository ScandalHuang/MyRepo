package mro.app.reportView.bo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.MroWmsInfoListDao;

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
public class MroWmsInfoListBo {
    private MroWmsInfoListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new MroWmsInfoListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String location,
    		String itemtype,String description,String owerDept,String stockType,BigDecimal stockDay,
    		BigDecimal idleDay,String itemnum) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			condition.append("AND NVL(SC.LOCATION_SITE,LM.LOCATION_SITE)=:locationSite ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and ORGANIZATION_CODE in (:organizationCode) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(location)){
			condition.append("AND a.SUBINVENTORY like :location ");
			param.put("location", location+"%");
			
		}if(StringUtils.isNotBlank(itemtype)){
			condition.append("AND a.ITEM_NUM like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if(StringUtils.isNotBlank(description)){
			condition.append("AND exists (select 1 from item where itemnum=a.ITEM_NUM "
					+ "and Description like :description) ");
			param.put("description", "%"+description+"%");
		}
		if(StringUtils.isNotBlank(owerDept)){
			condition.append("AND A.BINNUM = :owerDept ");
			param.put("owerDept", owerDept);
		}
		if(StringUtils.isNotBlank(stockType)){
			condition.append("AND a.STOCK_TYPE like :stockType ");
			param.put("stockType", stockType+"%");
		}
		if(stockDay!=null){
			condition.append("AND A.STOCK_DAYS > :stockDay ");
			param.put("stockDay", stockDay);
		}

		if(idleDay!=null){
			condition.append("AND exists (select idledays from INVENTORY "
					+ "where itemnum=a.ITEM_NUM and LOCATION=a.SUBINVENTORY and siteid=a.org_id "
					+ "and idledays>:idleDay) ");
			param.put("idleDay", idleDay);
		}if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND a.ITEM_NUM like :itemnum ");
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
	@Transactional(readOnly=true)
	public Map getStockType() {
		List<String> list=dao.getStockType();
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l, l));
		return option;
	}
}
