package mro.app.alitem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class Classstructure2ndListDao extends FactoryBaseDAO {

	public Classstructure2ndListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select CLASSSTRUCTUREID \"類別\","
				+ "(select DESCRIPTION from classstructure "
				+ "where CLASSSTRUCTUREID=a.CLASSSTRUCTUREID) \"類別名稱\",ASSETATTRID \"屬性\","
				+ "(select DESCRIPTION from ASSETATTRIBUTE where ASSETATTRID=a.ASSETATTRID) \"屬性名稱\","
				+ "LAST_UPDATE_BY \"最後異動人\",LAST_UPDATE \"更新日期\"  "
				+ "from CLASSSTRUCTURE_SECOND_SOURCE a "
				+ "where 1=1 " + condition
				+ " order by CLASSSTRUCTUREID,ASSETATTRID ";
		return queryBySQLToLinkMap(sql, param);
	}
}
