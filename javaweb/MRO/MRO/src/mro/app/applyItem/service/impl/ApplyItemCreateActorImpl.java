package mro.app.applyItem.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.signTask.service.ActorInterface;
import mro.base.System.config.basicType.PrType;
import mro.base.bo.PersonBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.Item;
import mro.base.entity.Person;
import mro.base.entity.Prline;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemCreateActorImpl implements ActorInterface {

	@Override
	public List getActor(Map map) {

		List<String> list=new LinkedList<String>();
		
		String itemnum=map.get("ITEMNUM")!=null?map.get("ITEMNUM").toString():"";
		PrlineBO prlineBO=SpringContextUtil.getBean(PrlineBO.class);
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
		String actor="";
			
		Prline prline=prlineBO.getMaxEnterPrline(itemnum, Arrays.asList(
				PrType.R1PMREQ.name(),PrType.R2PMREQ.name()));
		if(prline !=null){
			actor=prline.getRequestedby2();
		}else{
			Item item=listItemCommonBO.getItem(itemnum);
			actor=item.getCreateBy();
		}
		

		Person person=personBO.getPerson(actor);

		if(person==null){
			actor="";
		}
		list.add(actor);

		
		return list;
	}

}
