package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.ZErpUnprocessPr;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ZErpUnprocessPrBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<ZErpUnprocessPr> getZErpUnprocessPrListBySite(List<String> partNo,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("partNo", partNo));
		criterions.add(RestrictionsUtils.exists(LocationMap.class,
				"siteId",Restrictions.eqProperty("siteId",ZErpUnprocessPr.class.getSimpleName()+".organizationId"),
				RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		return commonDAO.query(ZErpUnprocessPr.class, Arrays.asList(Order.asc("partNo")), criterions);
	}
	
}
