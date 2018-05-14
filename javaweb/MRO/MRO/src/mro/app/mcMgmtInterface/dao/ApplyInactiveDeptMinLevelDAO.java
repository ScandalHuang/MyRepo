package mro.app.mcMgmtInterface.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Invbalances;
import mro.base.entity.Inventory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;

import com.inx.commons.dao.FactoryBaseDAO;


public class ApplyInactiveDeptMinLevelDAO extends FactoryBaseDAO {

	class MyResultTransformer implements ResultTransformer{
		 @Override
			public Object transformTuple(Object[] tuple, String[] aliases) {   
			    Map result = new LinkedHashMap(tuple.length);   
			    for ( int i=0; i<tuple.length; i++ ) {   
			        String alias = aliases[i];   
			        if ( alias!=null ) {   
			            result.put( alias, tuple[i] );   
			        }   
			    } 
			 
			    return result;   
			}
		 public List transformList(List list) { return list; }
	}
	
	public ApplyInactiveDeptMinLevelDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	public List getInactiveDeptList(){
		
		String sql ="SELECT ITEMNUM 料號,"
				+ "(select DESCRIPTION from item where item.itemnum=INVBALANCES_INACTIVE_RECODE.itemnum) 品名,"
				+ "LOCATION 倉別,BINNUM 部門, "
				+ "OLD_MINLEVEL \"舊重訂購量(R1月用量)\",NEW_MINLEVEL \"新重訂購量(R1月用量)\","
				+ "OLD_SSTOCK 舊最低安全存量,NEW_SSTOCK 新最低安全存量,"
				+ "OLD_ORIAVGUSEQTY 舊平均月耗用量,NEW_ORIAVGUSEQTY 新平均月耗用量"
				+ ",LASTUPDATE_DATE 更新時間 ,"
				+ "LASTUPDATE_BY 工號,"
				+ "(SELECT DISPLAY_NAME FROM PERSON WHERE PERSON_ID=LASTUPDATE_BY) 姓名,remark 失效說明  "
				+ "FROM INVBALANCES_INACTIVE_RECODE "
				+ "where trunc(lastupdate_date) between sysdate-1095 and sysdate "
				+ "order by lastupdate_date desc";
       Session session = getSession();
       Query query = session.createSQLQuery(sql);
       query.setResultTransformer(new MyResultTransformer());  //linkedhashmap
       //query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
       return query.list();
	}
	public Inventory getInventory(String condition){
		String sql="select * from Inventory WHERE 1=1 "+condition;
		return uniQueryBySQLWithEntity(sql, Inventory.class);
	}
}
