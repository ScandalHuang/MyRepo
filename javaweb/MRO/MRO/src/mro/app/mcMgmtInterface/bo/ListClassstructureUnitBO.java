package mro.app.mcMgmtInterface.bo;

import java.util.List;

import mro.app.mcMgmtInterface.dao.ListClassstructureUnitDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.ClassstructureUnit;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassstructureUnitBO {
   

    private ListClassstructureUnitDAO listClassstructureUnitDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructureUnitDAO=new ListClassstructureUnitDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void delete(ClassstructureUnit[] list){
		for(ClassstructureUnit a:list){
			listClassstructureUnitDAO.delete(a);
		}
	}
	
	@Transactional(readOnly=true)
	public List getClassstructureUnitList(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listClassstructureUnitDAO.getClassstructureUnitList(condition.toString());
	}

}
