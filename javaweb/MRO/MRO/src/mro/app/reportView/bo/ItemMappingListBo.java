package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemMappingListDao;

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
public class ItemMappingListBo {

	private ItemMappingListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new ItemMappingListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String newMatnr,String oldMatnr,String locationSite) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(newMatnr)) {
			condition.append("AND new_Matnr  like :newMatnr ");
			param.put("newMatnr", newMatnr+"%");
		}
		if (StringUtils.isNotBlank(oldMatnr)) {
			condition.append("AND old_Matnr  like :oldMatnr ");
			param.put("oldMatnr", oldMatnr);
		}
		if (StringUtils.isNotBlank(locationSite)) {
			condition.append("AND new_matnr in ("
					+ "select ITEMNUM  from ITEM_SITE WHERE LOCATION_SITE=:locationSite "
					+ "AND ENABLE_FLAG='Y') ");
			param.put("locationSite", locationSite);
		}
		return dao.getList(condition.toString(), param);
	}
}
