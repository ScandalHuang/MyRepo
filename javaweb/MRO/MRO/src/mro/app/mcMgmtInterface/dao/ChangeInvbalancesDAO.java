package mro.app.mcMgmtInterface.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ChangeInvbalancesDAO extends FactoryBaseDAO {


	public ChangeInvbalancesDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	public List getInvbalancesList(String condition,Map param){
		
		String sql ="select ib.invbalancesid,"
				+ "(SELECT  ORGANIZATION_CODE FROM LOCATION_MAP WHERE SITE_ID=SITEID) ORGANIZATION_CODE,"
				+ "IB.LOCATION ,IB.ITEMNUM ,"
				+ "(SELECT DESCRIPTION FROM ITEM WHERE ITEMNUM=IB.ITEMNUM) DESCRIPTION,"
				+ "IB.DEPTCODE ,IB.ORIAVGUSEQTY ,IB.SSTOCK ,"
				+ "IB.MINLEVEL ,"
				+ "IB.LASTREQUESTEDBY2 ,EMP.NAME "
				+ "FROM INVBALANCES IB LEFT JOIN HR_EMP EMP ON IB.LASTREQUESTEDBY2=EMP.EMP_NO "
				+ "where NVL(IB.MINLEVEL,0)>0 "+condition +" order by ib.itemnum";
       return queryBySQLToLinkMap(sql, param);
	}
	public void onUpdate(Map param){
		String sql=" UPDATE INVBALANCES "
				+ "SET LASTREQUESTEDBY2=:LASTREQUESTEDBY2,LASTREQUESTDATE=sysdate "
				+ "WHERE INVBALANCESID=:INVBALANCESID";
		modifyBySQL(sql, param);
	}
}
