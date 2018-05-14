package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.bo.ClassstructureItemSiteSignBO;
import mro.base.bo.ItemSiteBO;
import mro.base.entity.ClassstructureItemSiteSign;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferAddBuyerSignImpl implements ActorInterface {

	/** 新增廠區家簽add_buyer */
	@Override
	public List getActor(Map map) {
		List<String> list = new LinkedList<String>();
		String action = map.get("ACTION") != null ? map.get("ACTION")
				.toString() : "";
		String locationSite = map.get("LOCATION_SITE") != null ? 
				map.get("LOCATION_SITE").toString() : "";
		String classstructureid = map.get("CLASSSTRUCTUREID") != null ? 
				map.get("CLASSSTRUCTUREID").toString() : "";
		List<String> itemList = map.get("itemList") != null ? (List<String>) map
						.get("itemList") : null;
		ClassstructureItemSiteSignBO cBo=SpringContextUtil.getBean(ClassstructureItemSiteSignBO.class);
		ClassstructureItemSiteSign classstructureItemSign = cBo.get(classstructureid,locationSite);
		
		if(classstructureItemSign!=null){
			if(action.equals(LocationSiteActionType.I.name())){
				ItemSiteBO itemSiteBO = SpringContextUtil.getBean(ItemSiteBO.class);
				// 解凍筆數
				Long scount = itemSiteBO.getCountExists(itemList, locationSite);
				if(itemList.size()>scount){ //原筆數大於解凍筆數代表有新增廠區
					list.add(classstructureItemSign.getAddBuyerEmpno());
				}
			}
			if(list.size()==0) list.add("");
		}
		return list;
	}

}
