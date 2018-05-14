package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.reportView.dao.R2ItemMoniterListDao;

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
public class R2ItemMoniterListBo {

	private R2ItemMoniterListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new R2ItemMoniterListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String itemnum, String oldMatnr,String name,String status,
			String supplierFlag,String organizationCode) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(itemnum)) {
			condition.append("AND B.ITEMNUM  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if (StringUtils.isNotBlank(oldMatnr)) {
			condition.append("AND A.OLD_MATNR like :oldMatnr ");
			param.put("oldMatnr", oldMatnr+"%");
		}
		if (StringUtils.isNotBlank(name)) {
			condition.append("AND b.CHANGEBY in (select emp_no from hr_emp where name like :name) ");
			param.put("name", name+"%");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("AND nvl(B.STATUS,'廠商回補') =:status ");
			param.put("status", status);
		}
		if(StringUtils.isNotBlank(supplierFlag)){
			if(supplierFlag.toUpperCase().indexOf("NULL")!=-1)
				condition.append("AND A.SUPPLIER_FLAG  is null ");
			else
				condition.append("AND A.SUPPLIER_FLAG  = :supplierFlag ");
			param.put("supplierFlag", supplierFlag);
		}if(StringUtils.isNotBlank(organizationCode)){
			if(organizationCode.toUpperCase().indexOf("NULL")!=-1)
				condition.append("AND ORGANIZATION_CODE  is null ");
			else
				condition.append("AND ORGANIZATION_CODE  = :organizationCode ");
			param.put("organizationCode", organizationCode);
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
