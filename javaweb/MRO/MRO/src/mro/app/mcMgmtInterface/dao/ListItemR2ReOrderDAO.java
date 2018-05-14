package mro.app.mcMgmtInterface.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemR2ReOrderDAO extends FactoryBaseDAO{
	
	public ListItemR2ReOrderDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
		
	public List getR2InventoryList(String condition){
		
		String sql ="SELECT P2.*,(SELECT M_DEPT_CODE FROM PERSON "
				+ "WHERE PERSON_ID=P2.INSPECTOR) INSPECTOR_DEPTCODE FROM (SELECT   p1.*,"
				+ "DECODE(INVBALANCE_ADJUST_FLAG,'N',NEW_CONTROL,NEW_CONTROL- INVBALANCES_PRQTY"
				+ "- INVBALANCES_POQTY) NEW_MINLEVEL,"
				+ "(SELECT   PERSON_ID FROM   PERSON WHERE   STATUS = 'ACTIVE' AND PERSON_ID =("
				+ "NVL2 ((SELECT   M_DEPT_CODE FROM   PERSON WHERE   PERSON_ID = P1.LASTREQUESTEDBY2 "
				+ "AND M_DEPT_CODE = p1.deptcode AND STATUS = 'ACTIVE'),"
				+ "P1.LASTREQUESTEDBY2,(SELECT   MANAGER_ID "
				+ "FROM   hr_org WHERE   DEPT_NO = P1.deptcode)))) INSPECTOR "
				+ "FROM   (SELECT   ib.INVBALANCESID,ib.deptcode,DECODE (ib.LASTREQUESTEDBY2,'-1',"
				+ "(SELECT   MANAGER_ID FROM   hr_org WHERE   DEPT_NO = ib.deptcode),NVL ("
				+ "(SELECT   SUPERVISOR FROM   person WHERE   person_id = ib.LASTREQUESTEDBY2 "
				+ "AND status != 'ACTIVE'),ib.LASTREQUESTEDBY2)) LASTREQUESTEDBY2, "
				+ "nvl(ib.prqty,0) INVBALANCES_PRQTY,nvl(ib.poqty,0) INVBALANCES_POQTY,"
				+ "NVL (ib.oriavguseqty, 0) INVBALANCES_ORIAVGUSEQTY,NVL (ib.minlevel, 0) INVBALANCES_MINLEVEL,"
				+ "(DECODE (INVBALANCE_ADJUST,'CMOMINLEVEL',ib.minlevel,"
				+ "'ORIAVGQTY',ib.oriavguseqty)* INVBALANCE_ADJUST_MULTIPLE) NEW_CONTROL,"
				+ "reorder.* from INVENTORY_CONTROL reorder "
				+ "LEFT JOIN invbalances ib ON ib.itemnum = reorder.itemnum "
				+ "AND ib.location = reorder.location and ib.siteid=reorder.siteid "
				+ "WHERE   ib.minlevel > 0) P1) P2 "
				+ "where 1=1 "+condition
				+ "ORDER BY   LOCATION, itemnum";
        return queryBySQLToLinkMap(sql, null);
	}
}
