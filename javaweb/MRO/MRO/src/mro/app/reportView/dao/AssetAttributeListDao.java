package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class AssetAttributeListDao extends FactoryBaseDAO {

	public AssetAttributeListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select ASSETATTRID \"屬性\",DESCRIPTION \"敘述\","
				+ "DATATYPE \"資料類型\",DECIMALPLACES \"小數位數\" "
				+ "from ASSETATTRIBUTE "
				+ "where 1=1 " + condition
				+ "order by ASSETATTRID ";
		return queryBySQLToLinkMap(sql, param);
	}
}
