package mro.app.overview.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.overview.dao.ZErpUnProcessListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class ZErpUnProcessListBo {

	private ZErpUnProcessListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new ZErpUnProcessListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String locationSite,String[] organizationCode, String itemtype,String itemnum,
			String prnum,String description,String subinventory) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(locationSite)) {
			condition.append("AND exists (select 1 from location_map lm "
					+ "where B.ORGANIZATION_ID=lm.site_id "
					+ "and location_site = :locationSite) ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and exists (SELECT   1 FROM   LOCATION_MAP WHERE   site_id = B.ORGANIZATION_ID "
					+ "and ORGANIZATION_CODE in (:organizationCode)) ");
			param.put("organizationCode", organizationCode);
		}
		
		if (StringUtils.isNotBlank(itemtype)) {
			condition.append("AND part_no in (SELECT   ITEMNUM "
					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemtype "
					+ "OR EXISTS (SELECT   1 "
					+ "FROM   ITEM_MAPPING WHERE   OLD_MATNR = ITEMNUM "
					+ "AND NEW_MATNR LIKE :itemtype  AND NEW_MATNR IS NOT NULL "
					+ "AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemtype", itemtype+"%");
		}
		if (StringUtils.isNotBlank(itemnum)) {
			condition.append("AND part_no in (SELECT   ITEMNUM "
					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemnum "
					+ "OR EXISTS (SELECT   1 "
					+ "FROM   ITEM_MAPPING WHERE   OLD_MATNR = ITEMNUM "
					+ "AND NEW_MATNR LIKE :itemnum  AND NEW_MATNR IS NOT NULL "
					+ "AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemnum", itemnum+"%");
		}

		if (StringUtils.isNotBlank(prnum)) {
			condition.append("AND PR_NO like :prnum ");
			param.put("prnum", prnum+"%");
		}
		if (StringUtils.isNotBlank(description)) {
			condition.append("AND B.PART_NO_DESC like :description ");
			param.put("description", description+"%");
		}
		if (StringUtils.isNotBlank(subinventory)) {
			condition.append("AND B.DESTINATION_SUBINVENTORY = :subinventory ");
			param.put("subinventory", subinventory);
		}
		return dao.getList(condition.toString(), param);
	}

	@Transactional(readOnly=true)
    public Map getOrgs(String locationSite) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			param.put("locationSite", locationSite);
		}else{
			return null;
		}
		List<Map> list=dao.getOrgs(param);
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l.get("ORGANIZATION_NAME"), l.get("ORGANIZATION_CODE")));
		return option;
	}
}
