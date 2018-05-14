package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemApplySingListDao extends FactoryBaseDAO {

	public ItemApplySingListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT c.classstructureid  \"CLASSSTRUCTUREID(類別)\", "
				+ "b.DESCRIPTION \"類別結構敘述\",  C.PLANT_CODE \"PLANT_CODE\", "
				+ "c.DISCIPLINARY_BOARD \"DISCIPLINARY_BOARD(紀委會)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.DISCIPLINARY_BOARD) \"紀委會簽核人員姓名\", "
				+ "C.BUYER_EMPNO \"BUYER_EMPNO(採購人員)\", "
				+ "(select display_name from person where PERSON_ID=C.BUYER_EMPNO )  \"採購簽核人員姓名\", "
				+ "C.MC_EMPNO \"MC_EMPNO(物管簽核人員)\", "
				+ "(select display_name from person where PERSON_ID=C.MC_EMPNO  )  \"物管簽核人員姓名\","
				+ " C.EHS_EMPNO \"EHS_EMPNO(環安簽核人員)\",  "
				+ "(select display_name from person where PERSON_ID= C.EHS_EMPNO  )  \"環安簽核人員姓名\","
				+ " C.GP_EMPNO \"GP_EMPNO(GP簽核人員)\", "
				+ "(select display_name from person where PERSON_ID=C.GP_EMPNO  )  \"GP簽核人員姓名\","
				+ "C.ITEM_GROUP_EMPNO \"ITEM_GROUP_EMPNO(審核小組)\","
				+ "(select display_name from person where PERSON_ID=C.ITEM_GROUP_EMPNO  )  \"審核小組姓名\","
				+ "FINAL_APPOVE_NOTIFY_EMPNO "
				+ "FROM CLASSSTRUCTURE_ITEM_SIGN C "
				+ "LEFT JOIN CLASSSTRUCTURE B ON c.CLASSSTRUCTUREID=B.CLASSSTRUCTUREID "
				+ "where 1=1 " + condition;
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
