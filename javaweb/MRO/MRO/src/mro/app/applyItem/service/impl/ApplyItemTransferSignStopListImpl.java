package mro.app.applyItem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.app.util.HrEmpUtils;
import mro.base.bo.ItemSiteBO;
import mro.base.entity.ItemSite;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferSignStopListImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {

		List<String> itemList=map.get("itemList")!= null?(List<String>) map.get("itemList"):null;
		String locationSite = map.get("LOCATION_SITE") != null ? 
				map.get("LOCATION_SITE").toString() : "";
		ItemSiteBO itemSiteBO = SpringContextUtil
				.getBean(ItemSiteBO.class);
		List<String> actorList=new ArrayList<String>();
		if(itemList!=null && itemList.size()>0){
			List<ItemSite> listItemSite=itemSiteBO.getItemSiteBySite(itemList, locationSite);
			
			for(ItemSite itemsite:listItemSite){
				actorList.add(itemsite.getLastUpdatedBy());
			}
		}
		
		//============by pass 驗證=====================
		List<String> activeList=new ArrayList<String>(); 
		if(actorList.size()!=0){
			activeList=HrEmpUtils.getActiveList(actorList);
		}
		
		if(activeList.size()==0){
			activeList.add("");
		}
		
		return activeList;
	}

}
