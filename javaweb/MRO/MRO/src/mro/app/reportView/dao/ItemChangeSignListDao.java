package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemChangeSignListDao extends FactoryBaseDAO {

	public ItemChangeSignListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT c.classstructureid  \"CLASSSTRUCTUREID(類別)\","
				+ "b.DESCRIPTION \"類別結構敘述\",  c.plant_code \"PLANT_CODE\","
				+ "c.DISCIPLINARY_BOARD \"DISCIPLINARY_BOARD(紀委會)\","
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.DISCIPLINARY_BOARD) \"紀委會簽核人員姓名\", "
				+ "c.ehs_empno \"EHS_EMPNO(環安簽核人員)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.ehs_empno) \"環安簽核人員姓名\", "
				+ "c.gp_empno \"GP_EMPNO(GP簽核人員)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.gp_empno) \"GP簽核人員姓名\", "
				+ "c.BONDED_EMPNO \"BONDED_EMPNO(保稅人員)\", "
				+ "(SELECT display_name FROM person  "
				+ "WHERE person_id = c.bonded_empno) \"保稅簽核人員姓名\", "
				+ "c.buyer_empno \"BUYER_EMPNO(採購人員)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.buyer_empno) \"採購簽核人員姓名\", "
				+ "c.mc_empno \"MC_EMPNO(物管簽核人員)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.mc_empno) \"物管簽核人員姓名\", "
				+ "c.item_group_empno \"ITEM_GROUP_EMPNO(審核小組)\", "
				+ "(SELECT display_name FROM person "
				+ "WHERE person_id = c.item_group_empno) \"料號審核小組姓名\", "
				+ "final_appove_notify_empno  "
				+ "FROM classstructure_itemchange_sign c LEFT JOIN classstructure b "
				+ "ON c.classstructureid = b.classstructureid "
				+ "where 1=1 " + condition
				+ "order by c.classstructureid,c.PLANT_CODE ";
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
