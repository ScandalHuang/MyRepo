package mro.app.classstructureSign.bo;

import java.util.List;

import mro.app.classstructureSign.dao.ListClassstructureApplySignDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.ClassstructureApplySign;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassstructureApplySignBO {
   

    private ListClassstructureApplySignDAO listClassstructureApplySignDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructureApplySignDAO=new ListClassstructureApplySignDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void delete(ClassstructureApplySign[] list){
		for(ClassstructureApplySign a:list){
			listClassstructureApplySignDAO.delete(a);
		}
	}
	
	@Transactional(readOnly=true)
	public List getClassstructureApplySignList(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listClassstructureApplySignDAO.getClassstructureApplySignList(condition.toString());
	}
	@Transactional(readOnly=true)
	public ClassstructureApplySign getClassstructureApplySign(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listClassstructureApplySignDAO.getClassstructureApplySign(condition.toString());
	}

}
