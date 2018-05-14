package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrtypeBudget;
import mro.base.entity.PrtypeBudgetReasoncode;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class PrtypeBudgetBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = false)
	public void delete(List<PrtypeBudget> list) {
		commonDAO.delete(list);
	}
	
    @Transactional(readOnly=true)
    public List<PrtypeBudget> getPrtypeBudgetList(LocationSiteMap locationSiteMap,
    		String prtype,String budgetType,String budgetMonth){
		List criterions = new ArrayList();
		if(StringUtils.isNotBlank(prtype)){
			criterions.add(RestrictionsUtils.propertyIn(PrtypeBudgetReasoncode.class,
					"budgetType", "budgetType", RestrictionsUtils.eq("prtype", prtype)));
		}
		criterions.add(RestrictionsUtils.eq("budgetType", budgetType));
		criterions.add(RestrictionsUtils.eq("budgetMonth", budgetMonth));
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		
		return commonDAO.query(PrtypeBudget.class, Arrays.asList(Order.asc("deptNo")), criterions);
	}
}
