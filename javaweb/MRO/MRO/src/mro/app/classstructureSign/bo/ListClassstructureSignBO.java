package mro.app.classstructureSign.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.classstructureSign.dao.ListClassstructureSignDAO;
import mro.base.entity.ClassstructureSign;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassstructureSignBO {
   

    private ListClassstructureSignDAO listClassstructureSignDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructureSignDAO=new ListClassstructureSignDAO(sessionFactory);
    	
    }


	@Transactional(readOnly=true)
	public List<ClassstructureSign> getClassstructureSignList(String selectClassstructureid,
			String selectOrganizationCode){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and CLASSSTRUCTUREID = '"+selectClassstructureid+"'");
		}
		if(StringUtils.isNotBlank(selectOrganizationCode)){
			condition.append("and PLANT_CODE = '"+selectOrganizationCode+"'");
		}
		return listClassstructureSignDAO.getClassstructureSignList(condition.toString());
	}
	@Transactional(readOnly=true)
	public Map getOrganizationCode(String selectClassstructureid){
		StringBuffer condition = new StringBuffer();
		Map map=new LinkedHashMap<>();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and organization_code not in (select plant_code " +
					"from CLASSSTRUCTURE_SIGN where CLASSSTRUCTUREID='"+selectClassstructureid+"') ");
		}
		condition.append("and LOCATION_SITE is not null ");
		List list=listClassstructureSignDAO.getOrganizationCode(condition.toString());
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
	
	@Transactional(readOnly=false)
	public void delete(ClassstructureSign[] list){
		for(ClassstructureSign a:list){
			listClassstructureSignDAO.delete(a);
		}
	}
}
