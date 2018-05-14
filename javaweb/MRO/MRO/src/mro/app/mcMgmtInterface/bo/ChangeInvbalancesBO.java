package mro.app.mcMgmtInterface.bo;

import java.util.List;
import java.util.Map;

import mro.app.mcMgmtInterface.dao.ChangeInvbalancesDAO;
import mro.utility.SqlUtility;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Scope("prototype")
public class ChangeInvbalancesBO {

	private ChangeInvbalancesDAO changeInvbalancesDAO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		changeInvbalancesDAO=new ChangeInvbalancesDAO(sessionFactory);
		
	}
	
	@Transactional(readOnly=true)
	public List getInvbalancesList(String deptCode){
		Map param=new HashedMap();
		param.put("DEPTCODE", deptCode);
		return changeInvbalancesDAO.getInvbalancesList(SqlUtility.getCondition(param),param);
	}
	
	@Transactional(readOnly=false)
	public void onUpdate(Map invbalances){
		Map param=new HashedMap();
		param.put("INVBALANCESID", invbalances.get("INVBALANCESID"));
		param.put("LASTREQUESTEDBY2", invbalances.get("NEW_LASTREQUESTEDBY2"));
		changeInvbalancesDAO.onUpdate(param);
		
	}
}
