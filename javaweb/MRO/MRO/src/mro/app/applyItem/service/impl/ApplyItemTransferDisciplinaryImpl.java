package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.base.bo.ClassstructureItemSiteSignBO;
import mro.base.entity.ClassstructureItemSiteSign;

import org.apache.commons.lang3.ObjectUtils;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemTransferDisciplinaryImpl implements ActorInterface {
	
	/*
	 * 紀委會簽核人員
	 */
	
	@Override
	public List getActor(Map map) {
		List<String> list = new LinkedList<String>();
		String locationSite = ObjectUtils.toString(map.get("LOCATION_SITE"));
		String classstructureid = ObjectUtils.toString(map.get("CLASSSTRUCTUREID"));
		//====================簽核人員===============================
		ClassstructureItemSiteSignBO cBo=SpringContextUtil.getBean(ClassstructureItemSiteSignBO.class);
		ClassstructureItemSiteSign entity = cBo.get(classstructureid,locationSite);
		if(entity == null) return null;
		
		list.add(entity.getDisciplinaryBoard());
		return list;
	}

}
