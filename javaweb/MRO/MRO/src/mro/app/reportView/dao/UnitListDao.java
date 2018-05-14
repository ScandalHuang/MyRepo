package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class UnitListDao extends FactoryBaseDAO {

	public UnitListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT A.CLASSSTRUCTUREID||'('||B.DESCRIPTION||')'  CLASSSTRUCTUREID,"
				+ "A.MIN_BASIC_UNIT, A.PACKAGE_UNIT  "
				+ "FROM CLASSSTRUCTURE_UNIT A "
				+ "LEFT JOIN CLASSSTRUCTURE B ON A.CLASSSTRUCTUREID=B.CLASSSTRUCTUREID "
				+ "where 1=1 " + condition
				+ "order by a.classstructureid ";				
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
