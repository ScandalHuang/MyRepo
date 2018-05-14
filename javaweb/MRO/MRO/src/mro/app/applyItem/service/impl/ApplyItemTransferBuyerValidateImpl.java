package mro.app.applyItem.service.impl;

import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ValidateInterface;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.bo.ItemSiteBO;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferBuyerValidateImpl implements ValidateInterface {

	@Override
	public boolean onSignVaildate(Map map) {
		String action = map.get("ACTION") != null ? 
				map.get("ACTION").toString() : "";
		String locationSite = map.get("LOCATION_SITE") != null ? 
				map.get("LOCATION_SITE").toString() : "";
		List<String> itemList=map.get("itemList")!= null?(List<String>) map.get("itemList"):null;
		
		ItemSiteBO itemSiteBO = SpringContextUtil
				.getBean(ItemSiteBO.class);
		if(itemList!=null && itemList.size()>0){
			//解凍筆數
			Long scount=itemSiteBO.getCountExists(itemList,locationSite);
			//解凍加簽採購到部級
			if(action.equals(LocationSiteActionType.S.name()) || scount>0){
				return true;
			}
		}
		return false;
	}

}