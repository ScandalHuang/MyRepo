package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroInventoryIdleList;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroInventoryIdleListBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getListByOrg(LocationSiteMap locationSiteMap,String organizationCode) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("organizationCode", organizationCode));
		criterions.add(RestrictionsUtils.eq("locationSiteMap",locationSiteMap));
		return commonDAO.query(MroInventoryIdleList.class,
				Arrays.asList(Order.asc("organizationCode"),
						Order.asc("itemnum")), criterions);
	}
	@Transactional(readOnly = false)
	public void delete(List<MroInventoryIdleList> list) {
		commonDAO.delete(list);
	}
}
