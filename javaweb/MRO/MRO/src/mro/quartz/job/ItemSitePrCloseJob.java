package mro.quartz.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.base.System.config.basicType.PrType;
import mro.base.bo.InvbalancesBO;
import mro.base.bo.PersonBO;
import mro.base.entity.Invbalances;
import mro.base.entity.Person;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.hibernate.ActiveFlag;

/*
 * 未生效區域控管量下修
 */
public class ItemSitePrCloseJob {
	private List<String> prs = new ArrayList<String>();
	public ItemSitePrCloseJob(){
	}
	public void start(){
		InvbalancesBO invbalancesBO = SpringContextUtil.getBean(InvbalancesBO.class);
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
		
		Person person = personBO.getPerson("-1");
		// ================取得未生效區域但有控管量的清單======================
		List<Invbalances> list=invbalancesBO.getInvbalancesBySiteStop();
		
		for(Invbalances i:list){
			applyItemPrBo.onAutoPr(person, i.getLastrequestedby2(),
					PrType.getControlPrType(i.getItemnum().substring(0,2)), 
					i.getItemnum(), i.getSiteid(), i.getBinnum(), 
					new BigDecimal("0"), false, "未生效區域控管量下修",ActiveFlag.Y, null);
		}
	}

}
