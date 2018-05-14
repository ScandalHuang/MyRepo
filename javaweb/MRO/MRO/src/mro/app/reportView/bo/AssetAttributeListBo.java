package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.AssetAttributeListDao;

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
public class AssetAttributeListBo {

	private AssetAttributeListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new AssetAttributeListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String assetattrid, String description) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(assetattrid)) {
			condition.append("AND ASSETATTRID   like :assetattrid ");
			param.put("assetattrid", assetattrid+"%");
		}
		if (StringUtils.isNotBlank(description)) {
			condition.append("AND DESCRIPTION  like :description ");
			param.put("description", description+"%");
		}
		return dao.getList(condition.toString(), param);
	}

}
