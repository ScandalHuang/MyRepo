package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ClassstructureApplySignListDao extends FactoryBaseDAO {

	public ClassstructureApplySignListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT c.classstructureid  \"CLASSSTRUCTUREID(類別)\", "
				+ "b.DESCRIPTION \"類別結構敘述\",  C.M_G_SIGNER \"M_G_SIGNER(M.G總Owner審核)\","
				+ "(select display_name from person where PERSON_ID=C.M_G_SIGNER )  \"M.G姓名\" "
				+ "from CLASSSTRUCTURE_APPLY_SIGN c "
				+ "LEFT JOIN CLASSSTRUCTURE B ON c.CLASSSTRUCTUREID=B.CLASSSTRUCTUREID "
				+ "where 1=1 " + condition;
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
