package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.basicType.Eaudittype;
import mro.base.entity.ClassstructureItemSign;
import mro.base.entity.ClassstructureItemchangeSign;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemDisciplinarySignImpl implements ActorInterface {

	/*
	 * 紀委會簽核人員
	 */
	@Override
	public List getActor(Map map) {
		ApplyItemBo applyItemBo=SpringContextUtil.getBean(ApplyItemBo.class);
		List<String> list=new LinkedList<String>();
		String classstructureid=ObjectUtils.toString(map.get("CLASSSTRUCTUREID"));
		String organizationCode=ObjectUtils.toString(map.get("ORGANIZATION_CODE"));
		String eaudittype=ObjectUtils.toString(map.get("EAUDITTYPE"));
		
		if(StringUtils.isBlank(eaudittype)) return null;
		
		if(Eaudittype.valueOf(eaudittype).equals(Eaudittype.U)){  //規格異動
			ClassstructureItemchangeSign entity=applyItemBo.getClassstructureItemchangeSign(
					classstructureid,organizationCode);
			if(entity==null) return null;
			list.add(entity.getDisciplinaryBoard());
		}else if(Eaudittype.valueOf(eaudittype).equals(Eaudittype.I)){  //料號新增	
			ClassstructureItemSign entity=applyItemBo.getClassstructureItemSign(
					classstructureid,organizationCode);
			if(entity==null) return null;
			list.add(entity.getDisciplinaryBoard());
		}
		return list;
	}
	
}
