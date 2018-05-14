package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.InvbalancesHighlightLog;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class InvbalancesHighlightLogBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List getList(String itemnum,String deptcode,String siteId,BigDecimal deleted){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("deptcode", deptcode));
		criterions.add(RestrictionsUtils.eq("siteId", siteId));
		criterions.add(RestrictionsUtils.eq("siteId", siteId));
		criterions.add(RestrictionsUtils.eq("deleted", deleted));
		
		return commonDAO.query(InvbalancesHighlightLog.class,
				Arrays.asList(Order.desc("highlightResponseLogId")), criterions);
	}
}
