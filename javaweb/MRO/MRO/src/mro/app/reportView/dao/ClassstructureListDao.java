package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ClassstructureListDao extends FactoryBaseDAO {

	public ClassstructureListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select a.CLASSSTRUCTUREID \"類別結構\", "
				+ "(SELECT RTRIM(XMLAGG(XMLELEMENT(e, DESCRIPTION||'/ ')  "
				+ "ORDER BY CLASSSTRUCTUREID).EXTRACT('//text()')) "
				+ "FROM CLASSSTRUCTURE WHERE PARENT!='ROOT' "
				+ "START WITH CLASSSTRUCTUREID=A.CLASSSTRUCTUREID "
				+ "CONNECT BY PRIOR PARENT=CLASSSTRUCTUREID) \"敘述\", "
				+ "(select parameter_value from parameter where category='SAP_STORE_CATEGORY' and"
				+ " parameter_key=a.STORE_CATEGORY) \"庫存分類\",A.INACTIVE_DATE \"失效日期\" "
				+ " from classstructure a where a.HASCHILDREN=0 "+ condition
				+ "order by classstructureid";
		return queryBySQLToLinkMap(sql, param);
	}
}
