package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ClassSpecListDao extends FactoryBaseDAO {

	public ClassSpecListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "WITH DOMAIN AS ( "
				+ "SELECT DOMAINID,COUNT(1) TOTAL FROM ALNDOMAIN  "
				+ "WHERE NVL(INACTIVE_DATE,SYSDATE+1)>SYSDATE "
				+ "GROUP BY DOMAINID),"
				+ "measureunitDomain as( "
				+ "select domainid,RTRIM(XMLAGG(XMLELEMENT(e, value||',') ORDER BY value).EXTRACT('//text()'))  value "
				+ "from MEASUREUNIT_DOMAIN group by domainid) "
				+ "SELECT A.CLASSSTRUCTUREID \"類別結構\",A.ASSETATTRID \"屬性\","
				+ "B.DESCRIPTION \"敘述\",B.DATATYPE \"資料類型\",B.DECIMALPLACES \"小數位數\" "
				+ ",A.ITEMSEQUENCE \"順序\",DECODE(A.ITEMREQUIREVALUE,'1','Y','0','N') \"必填\", "
				+ "CASE WHEN (SELECT TOTAL FROM DOMAIN WHERE DOMAINID=A.DOMAINID) "
				+ "IS NULL THEN 'FREE_KEYIN' ELSE  '清單' END \"輸入方式\", "
				+ "(select value  from measureunitDomain "
				+ "where domainid=a.domainid) \"分類屬性單位\" "
				+ "FROM CLASSSPEC A LEFT JOIN ASSETATTRIBUTE B ON A.ASSETATTRID=B.ASSETATTRID "
				+ "WHERE NVL(A.INACTIVE_DATE,SYSDATE+1)>SYSDATE " + condition
				+ "ORDER BY A.CLASSSTRUCTUREID,A.ITEMSEQUENCE";
		return queryBySQLToLinkMap(sql, param);
	}
}
