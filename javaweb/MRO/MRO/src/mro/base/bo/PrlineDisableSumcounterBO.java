package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacility;
import mro.base.entity.Prline;
import mro.base.entity.PrlineDisableSumcounter;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class PrlineDisableSumcounterBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(LocationSiteMap locationSiteMap) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
				Restrictions.eqProperty("organizationCode", PrlineDisableSumcounter.class.getSimpleName()+".organizationCode"),
				RestrictionsUtils.eq("locationSiteMap", locationSiteMap)));
		return commonDAO.query(
				PrlineDisableSumcounter.class,
				Arrays.asList(Order.asc("organizationCode"),
						Order.asc("itemnum")), criterions);
	}

	@Transactional(readOnly = false)
	public List<String> getValidatePrline(List<Prline> prlines,
			String siteId, String deptCode) {
		List criterions = new ArrayList();
		List<String> items = new ArrayList<String>();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(RestrictionsUtils.exists(LocationMap.class, 
				"organizationCode", Restrictions.eqProperty("organizationCode", 
				PrlineDisableSumcounter.class.getSimpleName()+".organizationCode"),
				Restrictions.eq("siteId", siteId)));

		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.eq("deptCode", deptCode),
				Restrictions.isNull("deptCode")));

		for (Prline prline : prlines) {
			items.add(prline.getItemnum());
		}
		criterions.add(RestrictionsUtils.in("itemnum", items));
		projectionList.add(Projections.distinct(Projections.property("itemnum")));
		if (Utility.isNotEmpty(prlines)) {
			return commonDAO.query(PrlineDisableSumcounter.class, null,
					criterions, projectionList);
		} else {
			return new ArrayList<String>();
		}

	}

	@Transactional(readOnly = false)
	public void delete(List<PrlineDisableSumcounter> list) {
		commonDAO.delete(list);
	}
}
