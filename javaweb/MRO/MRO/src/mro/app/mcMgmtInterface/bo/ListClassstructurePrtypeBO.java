package mro.app.mcMgmtInterface.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.commonview.dao.ListItemCommonDAO;
import mro.app.mcMgmtInterface.dao.ListClassstructurePrtypeDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructurePrtype;
import mro.base.entity.PrtypeBudget;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ListClassstructurePrtypeBO {
   
	ListClassstructurePrtypeDAO listClassstructurePrtypeDAO;
	ListItemCommonDAO listItemCommonDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructurePrtypeDAO=new ListClassstructurePrtypeDAO(sessionFactory);
    	listItemCommonDAO=new ListItemCommonDAO(sessionFactory);
    	
    }
    
	@Transactional(readOnly=false)
	public void updateClassstructurePrtype(String prtype,String classstructureid,String empNo){
		ClassstructurePrtype classstructurePrtype=getClassstructurePrtype(
				new String[]{"prtype","classstructureid"},prtype,classstructureid);
		if(classstructurePrtype==null){ classstructurePrtype=new ClassstructurePrtype();}
		classstructurePrtype.setPrtype(prtype);
		classstructurePrtype.setClassstructureid(classstructureid);
		classstructurePrtype.setLastUpdate(new Date(System.currentTimeMillis()));
		classstructurePrtype.setLastUpdateBy(empNo);
		listClassstructurePrtypeDAO.insertUpdate(classstructurePrtype);
	}
	@Transactional(readOnly=false)
	public void deleteClassstructurePrtypeList(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils(columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		listClassstructurePrtypeDAO.deleteClassstructurePrtypeList(condition.toString());
	}
	@Transactional(readOnly=true)
	public Map getClassstructure(String prtype,String itemCategory){
		StringBuffer condition=new StringBuffer();
		if(StringUtils.isNotBlank(itemCategory)){
			condition.append(" and CLASSSTRUCTUREID like '"+itemCategory+"%' ");
		}
		condition.append(" and CLASSSTRUCTUREID not in ( "
				+ "select CLASSSTRUCTUREID from CLASSSTRUCTURE_PRTYPE where 1=1 and prtype='"+prtype+"' )");
		
//		condition.append(" and nvl(INACTIVE_DATE,sysdate+1) > sysdate ");
		
		Map map = new LinkedHashMap<>();
			List<Classstructure> list=listItemCommonDAO.getClassstructure(condition.toString());
			for(Classstructure c:list){
				map.put(c.getClassstructureid()+"("+c.getDescription()+")",c.getClassstructureid()); 
			}
		return map;
	}
	
    @Transactional(readOnly=true)
    public ClassstructurePrtype getClassstructurePrtype(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils(columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		return listClassstructurePrtypeDAO.getClassstructurePrtype(condition.toString());
	}
    
    @Transactional(readOnly=true)
    public List<ClassstructurePrtype> getClassstructurePrtypeList(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils(columnName);
		StringBuffer condition=systemUtils.getConditions(new String[]{"=","like"},object);
		return listClassstructurePrtypeDAO.getClassstructurePrtypeList(condition.toString());
	}
}
