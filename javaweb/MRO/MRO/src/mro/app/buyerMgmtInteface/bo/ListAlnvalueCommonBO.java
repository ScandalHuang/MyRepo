package mro.app.buyerMgmtInteface.bo;

import java.util.List;

import mro.app.buyerMgmtInteface.dao.ListAlnvalueCommonDAO;
import mro.app.commonview.dao.ListAssetattributeDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.AlndomainCommon;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListAlnvalueCommonBO {
   

    private ListAlnvalueCommonDAO listAlnvalueCommonDAO;
    private ListAssetattributeDAO listAssetattributeDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listAlnvalueCommonDAO=new ListAlnvalueCommonDAO(sessionFactory);
    	listAssetattributeDAO=new ListAssetattributeDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void delete(AlndomainCommon[] list){
		for(AlndomainCommon a:list){
			listAlnvalueCommonDAO.delete(a);
		}
	}
	@Transactional(readOnly=true)
	public List getAlndomainCommonList(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listAlnvalueCommonDAO.getAlndomainCommonList(condition.toString());
	}

}
