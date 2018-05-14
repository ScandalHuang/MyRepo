package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class AlndomainListDao extends FactoryBaseDAO {

	public AlndomainListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "select a.classstructureid ||'('||c.DESCRIPTION||')'  MATERIAL_GROUP,"
				+ "b.ASSETATTRID, b.description ATTRIBUTE_DESCRIPTION,"
				+ "a.itemsequence ITEMSEQUENCE,"
				+ "decode(a.itemrequirevalue,'1','Y','N') ITEMREQUIREVALUE,a.value ,"
				+ "a.description,a.inactive_date from (select b.classstructureid, "
				+ "b.assetattrid, b.itemsequence, b.itemrequirevalue, a.value, "
				+ "a.description, a.inactive_date "
				+ "from alndomain a  "
				+ "left join classspec b on a.domainid = b.domainid  "
				+ "where nvl(b.inactive_date,sysdate+1)>sysdate) a "
				+ "left join assetattribute b on a.assetattrid = b.assetattrid "
				+ "left join CLASSSTRUCTURE c on a.classstructureid=c.classstructureid "
				+ "where 1=1 " + condition
				+ "order by a.classstructureid,a.itemsequence,dump(a.value) ";				
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOption() {
		String sql = "SELECT classstructureid || '('||DESCRIPTION ||')' key ,"
				+ "classstructureid "
				+ "from classstructure  where HASCHILDREN=0 "
				+ "group by classstructureid,DESCRIPTION order by classstructureid";
		return queryBySQL(sql);
	}


	public List getAssetOption() {
		String sql = "select DESCRIPTION,ASSETATTRID "
				+ "from ASSETATTRIBUTE order by ASSETATTRID";
		return queryBySQL(sql);
	}
}
