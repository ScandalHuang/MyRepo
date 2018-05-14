package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.classstructureSign.bo.ListClassstructureApplySignBO;
import mro.app.signTask.service.ActorInterface;
import mro.base.entity.ClassstructureApplySign;

import com.inx.commons.util.SpringContextUtil;

public class ApplyClassstructureSignListImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {
		String classstructureid=map.get("CLASSSTRUCTUREID")!=null?map.get("CLASSSTRUCTUREID").toString():"";
		
		ListClassstructureApplySignBO listClassstructureApplySignBO=SpringContextUtil.getBean(ListClassstructureApplySignBO.class);
		List<String> list=new LinkedList<String>();
		ClassstructureApplySign classstructureApplySign=listClassstructureApplySignBO.getClassstructureApplySign(
				new String[]{"classstructureid"}, classstructureid);
		if(classstructureApplySign!=null){
			list.add(classstructureApplySign.getMGSigner());
		}else{
			list=null;
		}
		
		return list;
	}


}
