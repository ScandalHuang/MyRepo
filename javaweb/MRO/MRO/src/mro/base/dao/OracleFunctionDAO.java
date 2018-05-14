package mro.base.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Item;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class OracleFunctionDAO extends FactoryBaseDAO {
	public OracleFunctionDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	};
	
	public BigDecimal getSumofcounter(String itemnum, String deptcode) {
		String sql = "SELECT   GET_SUMOFCONTER (:ITEMNUM, :DEPTCODE) FROM DUAL";
		Map param=new HashMap();
		param.put("ITEMNUM", itemnum);
		param.put("DEPTCODE", deptcode);
		return (BigDecimal) uniQueryBySQL(sql, param);
	}
	public String getDeptUp(String deptcode, int deptLevel) {
		String sql = "SELECT   GET_DEPT_UP (:deptcode, :deptLevel) FROM DUAL";
		Map param=new HashMap();
		param.put("deptcode", deptcode);
		param.put("deptLevel", deptLevel);
		return (String) uniQueryBySQL(sql, param);
	}
	public BigDecimal getUnicost(String itemnum) {
		String sql = "SELECT   GET_UNITCOST(:itemnum) FROM DUAL";
		Map param=new HashMap();
		param.put("itemnum", itemnum);
		return (BigDecimal) uniQueryBySQL(sql, param);
	}
	
	public List<Item> validateSumofcounter(String condition, Map param) {
		String sql = "SELECT * from item where "+condition;
		return queryBySQLWithEntity(sql, Item.class,param);
	}
	
	public BigDecimal getIssitecontrol(String itemnum, String siteid) {
		String sql = "SELECT count(*) num "+
		"  FROM INVBALANCES B "+
		" WHERE     B.ITEMNUM = :ITEMNUM "+
		"       AND B.SITEID IN "+
		"              (SELECT A2.SITE_ID "+
		"                 FROM LOCATION_MAP A1, LOCATION_MAP A2 "+
		"                WHERE     A1.LOCATION_SITE IS NOT NULL "+
		"                      AND A1.LOCATION_SITE = A2.LOCATION_SITE "+
		"                      AND A2.LOCATION_SITE IS NOT NULL "+
		"                      AND A1.SITE_ID = :SITEID) "+
		"       AND B.MINLEVEL > 0";
		Map param=new HashMap();
		param.put("ITEMNUM", itemnum);
		param.put("SITEID", siteid);
		return (BigDecimal) uniQueryBySQL(sql, param);
	}
}
