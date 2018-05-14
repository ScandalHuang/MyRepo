package mro.app.commonview.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.app.commonview.dao.ListClassstructureDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.Classstructure;
import mro.utility.SqlUtility;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Scope("prototype")
public class ListClassstructureBO {
   
    private ListClassstructureDAO listClassstructureDAO;
	
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	listClassstructureDAO=new ListClassstructureDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
    public List<Classstructure> getRecursiveClassstructureList(
    		int hasChildren,boolean activeFlag,String nonMaterial) {
		Map param=new HashMap<>();
		param.put("HASCHILDREN", hasChildren);

		StringBuffer condition=new StringBuffer(SqlUtility.getCondition(param));
		if(activeFlag){
			condition.append("and (INACTIVE_DATE > sysdate or INACTIVE_DATE is null) ");
		}
		if(StringUtils.isNotBlank(nonMaterial)){
			condition.append("and  classstructureid not like :NON_MATERIAL ");
			param.put("NON_MATERIAL", nonMaterial+"%");
		}
	   return listClassstructureDAO.getRecursiveClassstructureList(condition.toString(),param);
	}
	
	@Transactional(readOnly=true)
    public List<Classstructure> getClassstructurePhase(String id) {
		Map param=new HashMap<>();
		param.put("CLASSSTRUCTUREID", id);
	   return listClassstructureDAO.getClassstructurePhase(param);
	}	
	
	@Transactional(readOnly=true)
    public List<Classstructure> getchildList(String parent) {
    	
		StringBuffer condition=new StringBuffer();
		
		//======================類別id================================
		if(StringUtils.isNotBlank(parent)){ 
			condition.append("and parent = '"+parent+"' ");
		}
		
	   return listClassstructureDAO.getchildList(condition.toString());
	}	

	@Transactional(readOnly=true)
    public List<Classstructure> getchildList(int hasChildren) {
    	
		StringBuffer condition=new StringBuffer(); 
			condition.append("and HASCHILDREN = "+hasChildren+" ");
			condition.append("and (INACTIVE_DATE > sysdate or INACTIVE_DATE is null) ");
		
	   return listClassstructureDAO.getchildList(condition.toString());
	}	
		
	
	@Transactional(readOnly=true)
    public Classstructure getClassstructure(String id) {
		StringBuffer condition=new StringBuffer();
		//======================類別id================================
		if(StringUtils.isNotBlank(id)){ 
			condition.append("and CLASSSTRUCTUREID = '"+id+"' ");
		}
	   return listClassstructureDAO.getClassstructure(condition.toString());
	}	
	
	
	@Transactional(readOnly = true)
	public Classstructure  getClassstructure(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		return listClassstructureDAO.getClassstructure(condition.toString()); 
	}
	

	@Transactional(readOnly=false)
    public void addClassstructure(Classstructure classstructure) {
		listClassstructureDAO.insertUpdate(classstructure);
	}
	
	
}
