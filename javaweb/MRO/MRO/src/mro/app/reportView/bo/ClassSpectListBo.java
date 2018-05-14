package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ClassSpecListDao;

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
public class ClassSpectListBo {

	private ClassSpecListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new ClassSpecListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String itemtype, String classstructureid) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(itemtype)) {
			condition.append("AND A.CLASSSTRUCTUREID  like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if (StringUtils.isNotBlank(classstructureid)) {
			condition.append("AND A.CLASSSTRUCTUREID  like :classstructureid ");
			param.put("classstructureid", classstructureid+"%");
		}
		return dao.getList(condition.toString(), param);
	}
}
