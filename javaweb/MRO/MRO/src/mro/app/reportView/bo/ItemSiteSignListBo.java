package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemSiteSignListDao;

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
public class ItemSiteSignListBo {

	private ItemSiteSignListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new ItemSiteSignListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String classstructureid,String locationSite) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(classstructureid)) {
			condition.append("AND c.classstructureid  = :classstructureid ");
			param.put("classstructureid", classstructureid);
		}if (StringUtils.isNotBlank(locationSite)) {
			condition.append("AND c.location_Site  = :locationSite ");
			param.put("locationSite", locationSite);
		}
		return dao.getList(condition.toString(), param);
	}

	@Transactional(readOnly = true)
	public Map getOption() {
		List<Object[]> list = dao.getOption();
		Map option=new LinkedHashMap();
		list.stream().forEach(o->option.put(o[0], o[1]));
		return option;
	}
}
