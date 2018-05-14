package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class CreateActorImpl implements ActorInterface {
	
	/*
	 * 申請人
	 */
	
	@Override
	public List getActor(Map map) {
		List<String> list = new LinkedList<String>();
		String createBy = ObjectUtils.toString(map.get("empno"));
		if(StringUtils.isBlank(createBy)) return null;
		list.add(createBy);
		return list;
	}

}
