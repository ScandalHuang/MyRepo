package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemCostListDao;

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
public class ItemCostListBo {

	private ItemCostListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new ItemCostListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String itemtype, String itemnum,String description) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();


		if (StringUtils.isNotBlank(itemtype)) {
			condition.append("AND itemnum  like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if (StringUtils.isNotBlank(itemnum)) {
			condition.append("AND itemnum like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if (StringUtils.isNotBlank(description)) {
			condition.append("AND description like :description ");
			param.put("description", description+"%");
		}
		return dao.getList(condition.toString(), param);
	}
}
