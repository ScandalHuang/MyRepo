package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.base.bo.ClassstructureItemSiteSignBO;
import mro.base.entity.ClassstructureItemSiteSign;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferBuyerSignImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {
		List<String> list = new LinkedList<String>();
		String locationSite = map.get("LOCATION_SITE") != null ? 
				map.get("LOCATION_SITE").toString() : "";
		String classstructureid = map.get("CLASSSTRUCTUREID") != null ? 
				map.get("CLASSSTRUCTUREID").toString() : "";
		ClassstructureItemSiteSignBO cBo=SpringContextUtil.getBean(ClassstructureItemSiteSignBO.class);
		ClassstructureItemSiteSign classstructureItemSign = cBo.get(classstructureid,locationSite);

		if (classstructureItemSign != null) {
			list.add(classstructureItemSign.getBuyerEmpno());
		}
		return list;
	}

}
