package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.bo.ClassstructureItemSiteSignBO;
import mro.base.bo.ItemSiteBO;
import mro.base.entity.ClassstructureItemSiteSign;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferSignListImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {
		List<String> itemList=map.get("itemList")!= null?(List<String>) map.get("itemList"):null;
		String action = map.get("ACTION") != null ? 
				map.get("ACTION").toString() : "";
		String locationSite = map.get("LOCATION_SITE") != null ? 
				map.get("LOCATION_SITE").toString() : "";
		String classstructureid = map.get("CLASSSTRUCTUREID") != null ? 
						map.get("CLASSSTRUCTUREID").toString() : "";
		//=====================解凍筆數=============================
		ItemSiteBO itemSiteBO = SpringContextUtil.getBean(ItemSiteBO.class);
		Long scount=itemSiteBO.getCountExists(itemList,locationSite);
		
		//====================簽核人員===============================
		ClassstructureItemSiteSignBO cBo=SpringContextUtil.getBean(ClassstructureItemSiteSignBO.class);
		ClassstructureItemSiteSign classstructureItemSign = cBo.get(classstructureid,locationSite);
		
		List<String> list = new LinkedList<String>();
		if (classstructureItemSign != null) {
			 //生效在加簽環安(非解凍筆數>0)
			if(action.equals(LocationSiteActionType.I.name()) && itemList.size()>0 && scount!=itemList.size()){
				list.add(classstructureItemSign.getEhsEmpno());
			}
			list.add(classstructureItemSign.getMcEmpno());
			list.add(classstructureItemSign.getMc2Empno());
			list.add(classstructureItemSign.getItemGroupEmpno());
		} else {
			list = null;
		}
		return list;
	}

}
