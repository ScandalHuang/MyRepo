package mro.app.mcMgmtInterface.bo;

import mro.app.mcMgmtInterface.dao.ListMroOrgFacilityDAO;
import mro.base.entity.MroOrgFacility;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListMroOrgFacilityBO {
   

    private ListMroOrgFacilityDAO listMroOrgFacilityDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listMroOrgFacilityDAO=new ListMroOrgFacilityDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void delete(MroOrgFacility[] list){
		for(MroOrgFacility a:list){
			listMroOrgFacilityDAO.delete(a);
		}
	}
	@Transactional(readOnly=false)
	public void updateShortName(){
		listMroOrgFacilityDAO.updateShortName();
	}

}
