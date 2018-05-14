package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.LampControlHeader;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacility;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroOrgFacilityBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<MroOrgFacility> getList(LocationSiteMap locationSiteMap){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		return commonDAO.query(MroOrgFacility.class,null, criterions);
	}
	@Transactional(readOnly = true)
	public List<MroOrgFacility> getListByLamp(List<LampControlHeader> headers){
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(LampControlHeader h:headers){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("organizationCode",h.getOrganizationCode()),
					Restrictions.eq("deptNo",h.getDeptCode())));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		
		return commonDAO.query(MroOrgFacility.class,null, criterions);
	}
}
