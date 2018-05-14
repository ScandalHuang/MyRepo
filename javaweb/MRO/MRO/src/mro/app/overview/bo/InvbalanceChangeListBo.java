package mro.app.overview.bo;

import java.util.List;
import java.util.Map;

import mro.app.overview.dao.InvbalanceChangeListDao;

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
public class InvbalanceChangeListBo {

	private InvbalanceChangeListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new InvbalanceChangeListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String locationSite, String itemtype,String itemnum,
			String description,String prnum,String name,String deptNo) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(locationSite)) {
			if(locationSite.equals("失效區域"))
				condition.append("AND exists (select 1 from location_map lm "
						+ "where A1.siteid=lm.site_id and location_site is null) ");
			else
				condition.append("AND exists (select 1 from location_map lm "
					+ "where A1.siteid=lm.site_id "
					+ "and location_site = :locationSite) ");
			param.put("locationSite", locationSite);
		}
		if (StringUtils.isNotBlank(itemtype)) {
			condition.append("AND a1.itemnum  in (SELECT   ITEMNUM "
					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemtype "
					+ "OR EXISTS (SELECT   1 "
					+ "FROM   ITEM_MAPPING WHERE   OLD_MATNR = ITEMNUM "
					+ "AND NEW_MATNR LIKE :itemtype  AND NEW_MATNR IS NOT NULL "
					+ "AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemtype", itemtype+"%");
		}
		if (StringUtils.isNotBlank(itemnum)) {
			condition.append("AND a1.itemnum  in (SELECT   ITEMNUM "
					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemnum "
					+ "OR EXISTS (SELECT   1 "
					+ "FROM   ITEM_MAPPING WHERE   OLD_MATNR = ITEMNUM "
					+ "AND NEW_MATNR LIKE :itemnum  AND NEW_MATNR IS NOT NULL "
					+ "AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemnum", itemnum+"%");
		}

		if (StringUtils.isNotBlank(prnum)) {
			condition.append("AND a1.prnum  like :prnum ");
			param.put("prnum", prnum+"%");
		}
		if (StringUtils.isNotBlank(description)) {
			condition.append("AND a1.description like :description ");
			param.put("description", description+"%");
		}
		if (StringUtils.isNotBlank(name)) {
			condition.append("AND  a3.DISPLAY_NAME like :name ");
			param.put("name", name+"%");
		}
		if (StringUtils.isNotBlank(deptNo)) {
			condition.append("AND  a1.m_dept = :deptNo ");
			param.put("deptNo", deptNo);
		}
		return dao.getList(condition.toString(), param);
	}
}
