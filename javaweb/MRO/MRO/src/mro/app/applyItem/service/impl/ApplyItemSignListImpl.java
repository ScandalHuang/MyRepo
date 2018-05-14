package mro.app.applyItem.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.Eaudittype;
import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.ItemType;
import mro.base.bo.ClassstructureGpBO;
import mro.base.entity.ClassstructureGp;
import mro.base.entity.ClassstructureItemSign;
import mro.base.entity.ClassstructureItemchangeSign;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ApplyItemSignListImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {

		ApplyItemBo applyItemBo=SpringContextUtil.getBean(ApplyItemBo.class);
		List<String> list=new LinkedList<String>();
		String classstructureid=map.get("CLASSSTRUCTUREID")!=null?map.get("CLASSSTRUCTUREID").toString():"";
		String organizationCode=map.get("ORGANIZATION_CODE")!=null?map.get("ORGANIZATION_CODE").toString():"";
		String itemnum=map.get("ITEMNUM")!=null?map.get("ITEMNUM").toString():"";
		String gpControlFlag=ObjectUtils.toString(map.get("GP_CONTROL_FLAG"));
		String gpProductCategory=ObjectUtils.toString(map.get("GP_PRODUCT_CATEGORY"));
		String gpDeliveryType=ObjectUtils.toString(map.get("GP_DELIVERY_TYPE"));
		String gpRemainType=ObjectUtils.toString(map.get("GP_REMAIN_TYPE"));
		
		if(map.get("EAUDITTYPE")!=null && 
				Eaudittype.valueOf(ObjectUtils.toString(map.get("EAUDITTYPE"))).equals(Eaudittype.U)){  //規格異動
			ClassstructureItemchangeSign classstructureItemchangeSign=new ClassstructureItemchangeSign();
			classstructureItemchangeSign=applyItemBo.getClassstructureItemchangeSign(
					classstructureid,organizationCode);
			if(classstructureItemchangeSign!=null){
				list.add(classstructureItemchangeSign.getEhsEmpno());
				//===============gp簽核=================================
				if(this.setGPFlag(gpControlFlag, classstructureid,gpProductCategory, gpDeliveryType, gpRemainType)){
					list.add(classstructureItemchangeSign.getGpEmpno());
				}
				//===========================================================================
				list.add(classstructureItemchangeSign.getBondedEmpno());
				list.add(classstructureItemchangeSign.getBuyerEmpno());
				
				if(Utility.equalsOR(classstructureid.substring(0,2), ItemType.R2,ItemType.R94)){
					if(classstructureItemchangeSign.getMcEmpno()!=null){
						list.add(classstructureItemchangeSign.getMcEmpno());
					}
				}else{
					List grouplist=applyItemBo.getClassstructureItemchangeSignGroup(classstructureid,"MC_EMPNO",itemnum);
					if(grouplist!=null && grouplist.size()>0){
						for(Object o:grouplist){
							list.add(o.toString());
						}
					}
				}
				list.add(classstructureItemchangeSign.getItemGroupEmpno());
			}
		}else if(map.get("EAUDITTYPE")!=null && 
				Eaudittype.valueOf(ObjectUtils.toString(map.get("EAUDITTYPE"))).equals(Eaudittype.I)){  //料號新增	
			ClassstructureItemSign classstructureItemSign=applyItemBo.getClassstructureItemSign(
					classstructureid,organizationCode);
			if(classstructureItemSign!=null){
				list.add(classstructureItemSign.getBuyerEmpno());
				list.add(classstructureItemSign.getMcEmpno());
				list.add(classstructureItemSign.getEhsEmpno());

				//===============gp簽核=================================
				if(this.setGPFlag(gpControlFlag, classstructureid,gpProductCategory, gpDeliveryType, gpRemainType)){
					list.add(classstructureItemSign.getGpEmpno());
				}
				//=======================================================
				list.add(classstructureItemSign.getItemGroupEmpno());
			}
		}else{
			list=null;
		}
		return list;
	}
	
	private boolean setGPFlag(String gpControlFlag,String classstructureid,
			String gpProductCategory,String gpDeliveryType,String gpRemainType){
		if(gpControlFlag.equals("Y")){//申請物料之風險等級為中、高風險
			return true;
		}
		//========申請者有異動Defalt Value===================
		ClassstructureGpBO classstructureGpBO=SpringContextUtil.getBean(ClassstructureGpBO.class);
		ClassstructureGp gp=classstructureGpBO.getClassstructureGp(classstructureid);
		if(gp!=null && (StringUtils.isBlank(gpProductCategory) ||
			!GpType.valueOf(gpProductCategory).vGpType2() ||
			!gp.getDeliveryType().equals(gpDeliveryType)||
			!gp.getRemainType().equals(gpRemainType))){
			return true;
		}
		
		return false;
	}

}
