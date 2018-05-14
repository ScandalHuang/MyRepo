package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class R2MoniterReportListDao extends FactoryBaseDAO {

	public R2MoniterReportListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "WITH items AS (SELECT   organization_code, dept_no,changeby,count(1) TOTAL "
				+ "FROM   a_item a WHERE   EXISTS (SELECT   1 "
				+ "FROM   ITEM_MAPPING WHERE   EAUDITTRANSID = A.EAUDITTRANSID "
				+ "AND NEW_MATKL LIKE 'R2%' ) and status is not null "
				+ "GROUP BY   organization_code, dept_no,changeby), "
				+ "items_ATOTAL AS (SELECT   organization_code, dept_no,changeby,count(1) ATOTAL "
				+ "FROM   a_item a WHERE   EXISTS (SELECT   1 FROM   ITEM_MAPPING "
				+ "WHERE   EAUDITTRANSID = A.EAUDITTRANSID "
				+ "AND NEW_MATKL LIKE 'R2%') and status IN ('SYNC', 'APPR') "
				+ "and status is not null "
				+ "GROUP BY   organization_code, dept_no,changeby), "
				+ "items_NTOTAL AS (SELECT   organization_code, dept_no,changeby,count(1) NTOTAL "
				+ "FROM   a_item a WHERE   EXISTS (SELECT   1 FROM   ITEM_MAPPING "
				+ "WHERE   EAUDITTRANSID = A.EAUDITTRANSID "
				+ "AND NEW_MATKL LIKE 'R2%') and status NOT IN ('SYNC', 'APPR') "
				+ "and status is not null GROUP BY   organization_code, dept_no,changeby) "
				+ "SELECT   TRUNC (SYSDATE) \"資料日期\", "
				+ "A.organization_code \"廠別\",B.LOCATION_SITE \"SITE\","
				+ "A.dept_no \"部門\", "
				+ "(select SHORT_NAME from hr_org where DEPT_NO=a.DEPT_NO) \"部門名稱\", "
				+ "(SELECT display_name FROM PERSON WHERE PERSON_ID=A.changeby) \"回補人\","
				+ "TOTAL \"應處理筆數\", ATOTAL \"已處理筆數\", NTOTAL \"未處理筆數\","
				+ "ROUND (ATOTAL / TOTAL * 100, 0) || '%' \"執行率\" from ( "
				+ "select organization_code, dept_no,TOTAL,changeby, "
				+ "nvl((select ATOTAL from items_ATOTAL at "
				+ "where organization_code=i.organization_code and  "
				+ "dept_no=i.dept_no and changeby=i.changeby),0) ATOTAL , "
				+ "nvl((select NTOTAL from items_NTOTAL at "
				+ "where organization_code=i.organization_code and  "
				+ " dept_no=i.dept_no and changeby=i.changeby),0) NTOTAL  from items i) a "
				+ "LEFT JOIN LOCATION_MAP B ON A.organization_code=B.organization_code "
				+ "where 1=1 " + condition
				+ " ORDER BY  A.organization_code, A.dept_no ";
		return queryBySQLToLinkMap(sql, param);
	}
}
