package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2SpecMoniterListDao extends FactoryBaseDAO {

	public R2SpecMoniterListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "  SELECT AL.*,CASE WHEN NVL(\"新料號數值\",'NULL')=NVL(\"舊料號數值\",'NULL') THEN 'N' "
				+ "ELSE 'Y' END \"屬性數值不同\" "
				+ "FROM (SELECT a.new_matkl \"類別結構\",A.SUPPLIER_FLAG \"供應商回補規格(Y/N)\","
				+ "A.OLD_MATNR \"舊料號\",(select description from item where ITEMNUM=A.OLD_MATNR) \"舊料號品名\","
				+ "a.new_matnr \"新料號\",c.description \"新料號品名\",c.create_by \"回補OWER\","
				+ "(SELECT NAME FROM HR_EMP WHERE EMP_NO=c.create_by) \"回補OWER姓名\","
				+ "D.ASSETATTRID \"屬性\",DECODE(D.DATATYPE,'ALN',D.ALNVALUE,'NUMERIC',D.NUMVALUE||D.MEASUREUNITID) \"新料號數值\","
				+ "(SELECT DECODE(DATATYPE,'ALN',ALNVALUE,'NUMERIC',NUMVALUE||MEASUREUNITID) "
				+ "FROM ITEMSPEC WHERE ITEMNUM=A.OLD_MATNR AND ASSETATTRID=D.ASSETATTRID) \"舊料號數值\" "
				+ "FROM ITEM_MAPPING A LEFT JOIN ITEM C ON a.ITEM_ID=C.ITEMID "
				+ "left join itemspec d on a.ITEM_ID=d.itemid "
				+ "WHERE A.NEW_MATKL LIKE 'R2%' AND A.ITEM_ID IS NOT NULL "
				+ "ORDER BY a.new_matnr,D.DISPLAYSEQUENCE) AL "
				+ "where 1=1 " + condition;
		return queryBySQLToLinkMap(sql, param);
	}
	public List getOption(){
		String sql = " SELECT ASSETATTRID||'-'||DESCRIPTION,ASSETATTRID "
				+ "FROM ASSETATTRIBUTE ORDER BY ASSETATTRID";
		return queryBySQL(sql);
	}
}
