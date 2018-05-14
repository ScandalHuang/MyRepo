package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2ItemMoniterListDao extends FactoryBaseDAO {

	public R2ItemMoniterListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT B.ITEMNUM \"申請單號\",B.CLASSSTRUCTUREID \"類別結構\",B.CREATE_DATE \"申請日期\","
				+ "B.EAUDITTIMESTAMP \"狀態最後更新日期\",nvl(B.STATUS,'廠商回補') \"申請單狀態\",A.SUPPLIER_FLAG \"供應商回補規格(Y/N)\","
				+ "A.OLD_MATNR \"舊料號\",(SELECT DESCRIPTION FROM ITEM WHERE ITEMNUM=A.OLD_MATNR) \"舊料號品名敘述\","
				+ "C.ITEMNUM \"新料號\",C.DESCRIPTION \"新料號品名敘述\","
				+ "(SELECT NAME FROM HR_EMP WHERE EMP_NO=B.CHANGEBY) \"回補OWER姓名\","
				+ "B.DEPT_NO \"OWER部門\","
				+ "(select SHORT_NAME from hr_org where DEPT_NO=b.DEPT_NO) \"OWER部門名稱\",ORGANIZATION_CODE \"OWER廠區\" "
				+ "FROM ITEM_MAPPING A LEFT JOIN A_ITEM B ON A.EAUDITTRANSID=B.EAUDITTRANSID "
				+ "LEFT JOIN ITEM C ON B.ITEMID=C.ITEMID "
				+ "where 1=1 and A.NEW_MATKL LIKE 'R2%' " + condition;
		return queryBySQLToLinkMap(sql, param);
	}
	public List getOption(){
		String sql = " SELECT PARAMETER_DESCRIPTION,PARAMETER_KEY FROM PARAMETER WHERE CATEGORY='PROCESS_CATEGORY' "
				+ "AND PARAMETER_KEY IN ('INPRG','SYNC','NEW','REJECT','STOPUSE','APPR') "
				+ "ORDER BY PARAMETER_DESCRIPTION";
		return queryBySQL(sql);
	}
}
