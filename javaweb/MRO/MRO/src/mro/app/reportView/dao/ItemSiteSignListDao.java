package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemSiteSignListDao extends FactoryBaseDAO {

	public ItemSiteSignListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT c.classstructureid  \"CLASSSTRUCTUREID(類別)\","
				+ "b.DESCRIPTION \"類別結構敘述\", c.LOCATION_SITE \"LOCATION_SITE(區域)\" ,"
				+ "c.DISCIPLINARY_BOARD \"DISCIPLINARY_BOARD(紀委會)\","
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.DISCIPLINARY_BOARD) \"紀委會簽核人員姓名\", "
				+ "c.buyer_empno \"BUYER_EMPNO(採購人員)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.buyer_empno) \"採購簽核人員姓名\", "
				+ "c.add_buyer_empno \"ADD_BUYER_EMPNO(ADD採購)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.add_buyer_empno) \"ADD採購簽核姓名\", "
				+ "c.ehs_empno \"EHS_EMPNO(環安簽核人員)\", (SELECT display_name "
				+ "FROM person WHERE person_id = c.ehs_empno) \"環安簽核人員姓名\", "
				+ "c.mc_empno \"MC_EMPNO(物管簽核人員)\", (SELECT display_name "
				+ "FROM person WHERE person_id = c.mc_empno) \"物管簽核人員姓名\", "
				+ "c.mc2_empno \"MC2_EMPNO(物管2簽核人員)\","
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.mc2_empno) \"物管2簽核人員姓名\", "
				+ "c.item_group_empno \"ITEM_GROUP_EMPNO(審核小組)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.item_group_empno) \"料號審核小組姓名\", "
				+ "final_appove_notify_empno  "
				+ "FROM CLASSSTRUCTURE_ITEM_SITE_SIGN c LEFT JOIN classstructure b "
				+ "ON c.classstructureid = b.classstructureid "
				+ "where 1=1 " + condition
				+ "order by c.classstructureid,c.LOCATION_SITE ";
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
