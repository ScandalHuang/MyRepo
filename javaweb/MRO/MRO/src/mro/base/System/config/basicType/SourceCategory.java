package mro.base.System.config.basicType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mro.base.bo.MroApplySignListBO;
import mro.base.entity.view.MroApplySignList;

import com.inx.commons.util.SpringContextUtil;


public enum SourceCategory {
	NEW_ITEM,
	CHANGE_ITEM,
	ITEM_SITE_TRANSFER,		//料號BY_SITE拋轉 申請
	CLASSSTRUCTURE_APPLY_SIGN;  //料號清單自動化
	
	public Map<BigDecimal, MroApplySignList> getInprgMap(String empNo){
		return this.getTaskIdMap(name(),empNo, SignStatus.INPRG, true);
	}
	public static Map<BigDecimal, MroApplySignList> getInprgMap(PrType prType,String empNo){
		return getTaskIdMap(prType.name(),empNo, SignStatus.INPRG, true);
	}
	public static Map<BigDecimal,MroApplySignList> getInprgMap(List list){
		Map<BigDecimal,MroApplySignList> map= getTaskIdMap(list, SignStatus.INPRG);
		map.forEach((k,v)->v.setActorName(v.getActorName()+"("+v.getActorId()+")"));
		return map;
	}
	public static Map<BigDecimal,MroApplySignList> getTaskIdMap(String sourceCategory,
			String empNo,SignStatus signStatus,boolean deputy){
		MroApplySignListBO bo=SpringContextUtil.getBean(MroApplySignListBO.class);
		return bo.getMap(bo.getList(empNo, null, signStatus, sourceCategory, deputy));
	}
	public static Map<BigDecimal,MroApplySignList> getTaskIdMap(List list,SignStatus signStatus){
		MroApplySignListBO bo=SpringContextUtil.getBean(MroApplySignListBO.class);
		return bo.getMap(bo.getListByEntity(signStatus, list));
	}
}
