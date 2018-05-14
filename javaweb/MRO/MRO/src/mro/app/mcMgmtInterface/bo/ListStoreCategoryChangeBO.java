package mro.app.mcMgmtInterface.bo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.mcMgmtInterface.dao.ListStoreCategoryChangeDAO;
import mro.base.entity.Inventory;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListStoreCategoryChangeBO {
   

    private ListStoreCategoryChangeDAO listStorCategoryChangeDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listStorCategoryChangeDAO=new ListStoreCategoryChangeDAO(sessionFactory);
    	
    }
	
	@Transactional(readOnly=false)
	public void update(List<Inventory> inventories){
		for(Inventory inventory:inventories){
			Map param=new HashMap();
			param.put("MCCOMMAND", inventory.getMccommand());
			param.put("CMOMMCONTROL", inventory.getCmommcontrol());
			param.put("ITEMNUM", inventory.getItemnum());
			param.put("LOCATION", inventory.getLocation());
			param.put("SITEID", inventory.getSiteid());
			param.put("STOCK_DAYS", inventory.getStockdays());
			if(inventory.getStockdays()==null){
				listStorCategoryChangeDAO.update2(param);
			}else{
				listStorCategoryChangeDAO.update(param);
			}
		}
	}
	
	@Transactional(readOnly=true)
	public Map getLocation(String siteId){
		if(StringUtils.isBlank(siteId)) return new LinkedHashMap<>();
		List list=listStorCategoryChangeDAO.getLocation(siteId);
		Map map=new LinkedHashMap<>();
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
}
