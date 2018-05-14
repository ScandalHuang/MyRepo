package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.reportView.dao.AlndomainCommontListDao;

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
public class AlndomainCommontListBo {

	private AlndomainCommontListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new AlndomainCommontListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String assetattrid, String itemtype) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(assetattrid)) {
			condition.append("AND ASSETATTRID  = :assetattrid ");
			param.put("assetattrid", assetattrid);
		}
		if (StringUtils.isNotBlank(itemtype)) {
			condition.append("AND ITEMTYPE =:itemtype ");
			param.put("itemtype", itemtype);
		}
		return dao.getList(condition.toString(), param);
	}

	@Transactional(readOnly = true)
	public Map getOption() {
		List<String> list = dao.getOption();
		Map option=new LinkedHashMap();
		dao.getOption().stream().forEach(o->option.put(o, o));
		return option;
	}
}
