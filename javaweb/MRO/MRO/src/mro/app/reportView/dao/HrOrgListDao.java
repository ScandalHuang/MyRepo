package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class HrOrgListDao extends FactoryBaseDAO {

	public HrOrgListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT dept_no \"部門代碼\", short_name \"部門名稱\", "
				+ "NVL2(dept_level,'AVTIVE','INACTIVE') STATUS, manager_id \"主管工號\","
				+ "(SELECT NAME FROM hr_emp WHERE emp_no = ho.manager_id) \"主管姓名\", "
				+ "TOP_DEPT \"上層部門代碼\" FROM hr_org ho "
				+ "where 1=1 " + condition;
		return queryBySQLToLinkMap(sql, param);
	}
}
