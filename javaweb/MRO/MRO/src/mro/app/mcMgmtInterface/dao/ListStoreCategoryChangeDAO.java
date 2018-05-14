package mro.app.mcMgmtInterface.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListStoreCategoryChangeDAO extends FactoryBaseDAO{
	
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
	
	public ListStoreCategoryChangeDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getLocation(String siteId){
		
		String sql ="select DISTINCT LOCATION from inventory where siteId='"+siteId+"' ORDER BY LOCATION";

		return queryBySQL(sql);
	}
	
	public void update(Map param){
        String sql = "update inventory set mccommand=:MCCOMMAND,CMOMMCONTROL=:CMOMMCONTROL, STOCK_DAYS=:STOCK_DAYS "
				+ "where itemnum=:ITEMNUM and location=:LOCATION and siteid=:SITEID";
		modifyBySQL(sql, param);
	}
	public void update2(Map param){
        String sql = "update inventory set mccommand=:MCCOMMAND,CMOMMCONTROL=:CMOMMCONTROL, STOCK_DAYS=null "
				+ "where itemnum=:ITEMNUM and location=:LOCATION and siteid=:SITEID";
		modifyBySQL(sql, param);
	}
	
}
