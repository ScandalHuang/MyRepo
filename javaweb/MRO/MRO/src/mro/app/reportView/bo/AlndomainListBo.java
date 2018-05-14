package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.AlndomainListDao;

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
public class AlndomainListBo {

	private AlndomainListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new AlndomainListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String classstructureid,String atrid,String description) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(classstructureid)) {
			condition.append("AND c.classstructureid  = :classstructureid ");
			param.put("classstructureid", classstructureid);
		}
		if (StringUtils.isNotBlank(atrid)) {
			condition.append("AND b.ASSETATTRID  = :atrid ");
			param.put("atrid", atrid);
		}
		if (StringUtils.isNotBlank(description)) {
			condition.append("AND a.description  like :description ");
			param.put("description", description +"%");
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

	@Transactional(readOnly = true)
	public Map getAssetOption() {
		List<Object[]> list = dao.getAssetOption();
		Map option=new LinkedHashMap();
		list.stream().forEach(o->option.put(o[0], o[1]));
		return option;
	}
}
