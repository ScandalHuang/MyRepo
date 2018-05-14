package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.InventoryStockReportDao;

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
public class InventoryStockReportBO {

    private InventoryStockReportDao inventoryStockReportDao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	inventoryStockReportDao=new InventoryStockReportDao(sessionFactory);
    }
    @Transactional(readOnly=true)
    public List getStocks(String itemnum,String location) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("and  ITEMNUM LIKE :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		
		if(StringUtils.isNotBlank(location)){
			condition.append("and  location =:location ");
			param.put("location", location);
		}
	   return inventoryStockReportDao.getStocks(condition.toString(),param);
	}
	@Transactional(readOnly=true)
    public List getWmsStockDetailAlls(String itemNum,String subinventory) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemNum)){
			condition.append("and  ITEM_NUM =:itemNum ");
			param.put("itemNum", itemNum);
		}
		
		if(StringUtils.isNotBlank(subinventory)){
			condition.append("and  subinventory =:subinventory ");
			param.put("subinventory", subinventory);
		}
	   return inventoryStockReportDao.getWmsStockDetailAlls(condition.toString(),param);
	}
	
	@Transactional(readOnly=true)
    public List getOpenPos(String itemNum,String subinventory,boolean po,boolean iqc) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemNum)){
			condition.append("and  IM_ITEMNUM =:itemNum ");
			param.put("itemNum", itemNum);
		}
		 
		if(StringUtils.isNotBlank(subinventory)){
			condition.append("and  destination_subinventory =:subinventory ");
			param.put("subinventory", subinventory);
		}
		if(po){
			condition.append("and  (NVL (quantity, 0) - NVL (received_qty, 0))>0 ");
		}
		if(iqc){
			condition.append("and  UNDELIVER_QTY>0 ");
		}
	   return inventoryStockReportDao.getOpenPos(condition.toString(),param);
	}

	@Transactional(readOnly=true)
    public List getUnProcessPrs(String itemNum,String subinventory) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemNum)){
			condition.append("and  IM_ITEMNUM =:itemNum ");
			param.put("itemNum", itemNum);
		}
		 
		if(StringUtils.isNotBlank(subinventory)){
			condition.append("and  destination_subinventory =:subinventory ");
			param.put("subinventory", subinventory);
		}
		condition.append("and  quantity>0 ");
	   return inventoryStockReportDao.getUnProcessPrs(condition.toString(),param);
	}

	@Transactional(readOnly=true)
    public List getBss(String itemNum,String siteid) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		if(StringUtils.isNotBlank(itemNum)){
			condition.append("and  ITEMNUM =:itemNum ");
			param.put("itemNum", itemNum);
		}
		if(StringUtils.isNotBlank(itemNum)){
			condition.append("and  siteid =:siteid ");
			param.put("siteid", siteid);
		}
		condition.append("and  qty>0 ");
	   return inventoryStockReportDao.getBss(condition.toString(),param);
	}
}
