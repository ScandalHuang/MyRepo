package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class PrtypeBudgetListDao extends FactoryBaseDAO {

	public PrtypeBudgetListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "  SELECT   LOCATION_SITE,dept_no \"部門代碼\","
				+ "budget_type_name \"申請類型\",budget_month \"預算月份\","
				+ "total_budget \"總預算\",use_budget \"已使用預算\","
				+ "control_budget \"安全庫存控管金額\", "
				+ "UNUSE_BUDGET \"可使用預算\", "
				+ "REMARK \"備註\" FROM   mro_prtype_budget_v "
				+ "where 1=1 " + condition
				+ " ORDER BY   budget_month, dept_no, BUDGET_TYPE ";
		return queryBySQLToLinkMap(sql, param);
	}
}
