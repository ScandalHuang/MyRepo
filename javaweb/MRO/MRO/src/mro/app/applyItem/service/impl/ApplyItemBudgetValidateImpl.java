package mro.app.applyItem.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.app.signTask.service.ValidateInterface;

import com.inx.commons.util.SpringContextUtil;

public class ApplyItemBudgetValidateImpl implements ValidateInterface {

	@Override
	public boolean onSignVaildate(Map map) {

		String prtype=map.get("PRTYPE")!=null?map.get("PRTYPE").toString():"";
		String deptcode=map.get("DEPTCODE")!=null?map.get("DEPTCODE").toString():"";
		String price=map.get("price")!=null?map.get("price").toString():"";
		String siteid=map.get("SITEID")!=null?map.get("SITEID").toString():"";
		
		ApplyItemPrBo applyItemPrBo = SpringContextUtil.getBean(ApplyItemPrBo.class);
		BigDecimal prtypeBudget=applyItemPrBo.getUnUseBudget(siteid,deptcode,prtype);
		if(prtypeBudget!=null){
			BigDecimal budget=prtypeBudget.subtract(new BigDecimal(price));
			return budget.compareTo(new BigDecimal("0"))==-1?
					true:false;
		}
		return false;
	}

}
