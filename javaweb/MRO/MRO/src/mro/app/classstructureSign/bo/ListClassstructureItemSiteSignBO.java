package mro.app.classstructureSign.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.classstructureSign.dao.ListClassstructureItemSiteSignDAO;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureItemSiteSign;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassstructureItemSiteSignBO {
   

    private ListClassstructureItemSiteSignDAO listClassstructureItemSiteSignDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listClassstructureItemSiteSignDAO=new ListClassstructureItemSiteSignDAO(sessionFactory);
    	
    }


	@Transactional(readOnly=true)
	public List<ClassstructureItemSiteSign> getClassstructureItemSiteSignList(String selectClassstructureid,
			String selectLocationSite){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and CLASSSTRUCTUREID = '"+selectClassstructureid+"'");
		}
		if(StringUtils.isNotBlank(selectLocationSite)){
			condition.append("and LOCATION_SITE = '"+selectLocationSite+"'");
		}
		return listClassstructureItemSiteSignDAO.getClassstructureItemSiteSignList(condition.toString());
	}
	@Transactional(readOnly=true)
	public ClassstructureItemSiteSign getClassstructureItemSiteSign(String selectClassstructureid,
			String selectLocationSite){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(selectClassstructureid)){
			condition.append("and CLASSSTRUCTUREID = '"+selectClassstructureid+"'");
		}
		if(StringUtils.isNotBlank(selectLocationSite)){
			condition.append("and LOCATION_SITE = '"+selectLocationSite+"'");
		}
		return listClassstructureItemSiteSignDAO.getClassstructureItemSiteSign(condition.toString());
	}
	@Transactional(readOnly=true)
	public Map getClassstructure(String classstructureid){
		StringBuffer condition = new StringBuffer();
		Map map=new LinkedHashMap<>();
		if(StringUtils.isNotBlank(classstructureid)){
			condition.append("and CLASSSTRUCTUREID like '"+classstructureid+"%'");
		}
		List<Classstructure> list=listClassstructureItemSiteSignDAO.getClassstructure(condition.toString());
		for(Classstructure c:list){
			map.put(c.getClassstructureid(), c.getClassstructureid());
		}
		return map;
	}	
	
	@Transactional(readOnly=false)
	public void delete(ClassstructureItemSiteSign[] list){
		for(ClassstructureItemSiteSign a:list){
			listClassstructureItemSiteSignDAO.delete(a);
		}
	}

}
