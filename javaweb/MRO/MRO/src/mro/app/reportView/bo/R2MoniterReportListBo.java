package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.R2MoniterReportListDao;

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
public class R2MoniterReportListBo {

	private R2MoniterReportListDao dao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		dao = new R2MoniterReportListDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList(String locationSite, String organizationCode,
			String deptNo,String name) {
		StringBuffer condition = new StringBuffer();
		Map param = new HashedMap();

		if (StringUtils.isNotBlank(locationSite)) {
			condition.append("AND B.LOCATION_SITE  = :locationSite ");
			param.put("locationSite", locationSite);
		}
		if (StringUtils.isNotBlank(organizationCode)) {
			condition.append("AND A.organization_code = :organizationCode ");
			param.put("organizationCode", organizationCode);
		}
		if (StringUtils.isNotBlank(deptNo)) {
			condition.append("AND  A.dept_no = :deptNo ");
			param.put("deptNo", deptNo);
		}
		if (StringUtils.isNotBlank(name)) {
			condition.append("AND A.changeby in (select emp_no from hr_emp where name = :name) ");
			param.put("name", name);
		}
		return dao.getList(condition.toString(), param);
	}
}
