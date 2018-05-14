package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.LocationMapListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class LocationMapListBo {

	private LocationMapListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new LocationMapListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String locationSite,String organizationCode) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(locationSite)) {
			if(locationSite.equals("失效廠區"))
				condition.append("AND \"SITE名稱\" is null "
						+ "and \"廠區代碼\" is not null ");
			else
				condition.append("AND \"SITE名稱\"  = :locationSite ");
			param.put("locationSite", locationSite);
		}if (StringUtils.isNotBlank(organizationCode)) {
			condition.append("AND \"廠區代碼\"  = :organizationCode ");
			param.put("organizationCode", organizationCode);
		}
		return dao.getList(condition.toString(), param);
	}
}
