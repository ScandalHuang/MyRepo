package mro.app.applyItem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.basicType.PrType;
import mro.base.bo.ClassstructureSignBO;
import mro.base.entity.ClassstructureSign;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.inx.commons.util.SpringContextUtil;

public class ApplyPrSignListImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {

		String siteid=ObjectUtils.toString(map.get("SITEID"));
		String classstructureid=ObjectUtils.toString(map.get("CLASSSTRUCTUREID"));
		String prtype=ObjectUtils.toString(map.get("PRTYPE"));
		
		ClassstructureSignBO classstructureSignBO=SpringContextUtil.getBean(ClassstructureSignBO.class);
		List<String> list=new ArrayList<String>();
			if(StringUtils.isBlank(classstructureid) || StringUtils.isBlank(siteid)){
				return null;
			}else{
				ClassstructureSign classstructureSign=classstructureSignBO.getClassstructureSign(
						siteid,classstructureid);
				if(classstructureSign!=null){
					if(prtype.equals(PrType.R1CONTROL.name()) || prtype.equals(PrType.R2CONTROL.name())){
						//2016/11/18 物管思珊要求加入
						if(StringUtils.isBlank(classstructureSign.getControlMcEmpno())) return null;
						list.add(classstructureSign.getControlMcEmpno());
					}else {
						//2016/11/18 物管思珊要求加入
						if(StringUtils.isBlank(classstructureSign.getPmreqMcEmpno())) return null;
						list.add(classstructureSign.getPmreqMcEmpno());
					}
				}else{
					list=null;
				}
			}
		return list;
	}

}
