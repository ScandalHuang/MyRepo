package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class AlndomainCommontListDao extends FactoryBaseDAO {

	public AlndomainCommontListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select ITEMTYPE \"料號類型\",ASSETATTRID \"屬性\","
				+ "VALUE \"清單編號\" ,DESCRIPTION \"清單敘述\"  "
				+ "from ALNDOMAIN_COMMON "
				+ "where 1=1 " + condition
				+ " ORDER BY ITEMTYPE,ASSETATTRID,VALUE ";
		return queryBySQLToLinkMap(sql, param);
	}
	public List getOption(){
		String sql = " SELECT DISTINCT ASSETATTRID from ALNDOMAIN_COMMON";
		return queryBySQL(sql);
	}
}
