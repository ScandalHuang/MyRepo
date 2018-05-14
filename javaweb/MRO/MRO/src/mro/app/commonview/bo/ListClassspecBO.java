package mro.app.commonview.bo;

import java.util.List;

import mro.app.commonview.dao.ListClasssepcDAO;
import mro.app.commonview.vo.ListClassspecVO;
import mro.base.entity.Classspec;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListClassspecBO {
	
    private ListClasssepcDAO listClasssepcDAO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		listClasssepcDAO = new ListClasssepcDAO(sessionFactory);
	}
	
	
	@Transactional(readOnly=true)
	public List<ListClassspecVO> getListClassspec(
			String classstructureid,String dataType,boolean activeFlag){
		StringBuffer condition=new StringBuffer();
		
		if(StringUtils.isNotBlank(classstructureid)){ 
			condition.append("and a3.classstructureid = '"+classstructureid+"' ");
		}		
		if(StringUtils.isNotBlank(dataType)){ 
			condition.append("and a4.datatype = '"+dataType+"' ");
		}	
		if(activeFlag){
			condition.append("AND nvl(a3.INACTIVE_DATE,sysdate+1)>sysdate ");
		}
	   return listClasssepcDAO.getListClassspec(condition.toString());
	}
	@Transactional(readOnly=false)
	public void updateListClassspecVO(Classspec c){
		listClasssepcDAO.insertUpdate(c);
	}
}
