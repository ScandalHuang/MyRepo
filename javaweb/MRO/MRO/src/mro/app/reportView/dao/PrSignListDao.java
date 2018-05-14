package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class PrSignListDao extends FactoryBaseDAO {

	public PrSignListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT c.classstructureid  \"CLASSSTRUCTUREID(類別)\", "
				+ "b.DESCRIPTION \"類別結構敘述\", c.PLANT_CODE, "
				+ "c.PMREQ_MC_EMPNO \"PMREQ_MC_EMPNO(非控管)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.PMREQ_MC_EMPNO) \"非控管簽核人員姓名\", "
				+ "c.CONTROL_MC_EMPNO \"CONTROL_MC_EMPNO(控管)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.CONTROL_MC_EMPNO) \"控管簽核人員姓名\" "
				+ "FROM CLASSSTRUCTURE_SIGN c LEFT JOIN classstructure b "
				+ "ON c.classstructureid = b.classstructureid "
				+ "where 1=1 " + condition
				+ "order by c.classstructureid,c.PLANT_CODE ";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOption() {

		String sql = "SELECT classstructureid || '('||DESCRIPTION ||')' key ,"
				+ "classstructureid "
				+ "from classstructure  where HASCHILDREN=0 "
				+ "group by classstructureid,DESCRIPTION order by classstructureid";
		return queryBySQL(sql);
	}
}
