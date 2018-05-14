package mro.app.classstructureSign.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.classstructureSign.dao.ListClassstructureItemChangeSignDAO;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureItemchangeSign;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassstructureItemChangeSignBO {
   

    private ListClassstructureItemChangeSignDAO listClassstructureItemChangeSignDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructureItemChangeSignDAO=new ListClassstructureItemChangeSignDAO(sessionFactory);
    	
    }


	@Transactional(readOnly=true)
	public List<ClassstructureItemchangeSign> getClassstructureItemchangeSignList(String selectClassstructureid,
			String selectOrganizationCode){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and CLASSSTRUCTUREID = '"+selectClassstructureid+"'");
		}
		if(StringUtils.isNotBlank(selectOrganizationCode)){
			condition.append("and PLANT_CODE = '"+selectOrganizationCode+"'");
		}
		return listClassstructureItemChangeSignDAO.getClassstructureItemchangeSignList(condition.toString());
	}
	@Transactional(readOnly=true)
	public Map getClassstructure(String classstructureid){
		StringBuffer condition = new StringBuffer();
		Map map=new LinkedHashMap<>();
		if(StringUtils.isNotBlank(classstructureid)){
			condition.append("and CLASSSTRUCTUREID like '"+classstructureid+"%'");
		}
		List<Classstructure> list=listClassstructureItemChangeSignDAO.getClassstructure(condition.toString());
		for(Classstructure c:list){
			map.put(c.getClassstructureid(), c.getClassstructureid());
		}
		return map;
	}
	@Transactional(readOnly=true)
	public Map getOrganizationCode(String selectClassstructureid){
		StringBuffer condition = new StringBuffer();
		Map map=new LinkedHashMap<>();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and organization_code not in (select plant_code " +
					"from CLASSSTRUCTURE_ITEMCHANGE_SIGN where CLASSSTRUCTUREID='"+selectClassstructureid+"') ");
		}
		List list=listClassstructureItemChangeSignDAO.getOrganizationCode(condition.toString());
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
	@Transactional(readOnly=false)
	public void delete(ClassstructureItemchangeSign[] list){
		for(ClassstructureItemchangeSign a:list){
			listClassstructureItemChangeSignDAO.delete(a);
		}
	}
}
