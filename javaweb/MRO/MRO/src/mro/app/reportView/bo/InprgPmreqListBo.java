package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.InprgPmreqListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class InprgPmreqListBo {

    private InprgPmreqListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new InprgPmreqListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String prtype,String itemnum,String sName,String resoncode){
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();

		if(StringUtils.isNotBlank(prtype)){
			condition.append("AND pr.prtype = :prtype ");
			param.put("prtype", prtype);
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND pl.itemnum  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(sName)){
			condition.append("AND st.ACTOR_ID  = :sName ");
			param.put("sName", sName);
		}
		if(StringUtils.isNotBlank(resoncode)){
			condition.append("AND PR.REASONCODE = :resoncode ");
			param.put("resoncode", resoncode);
		}
	   return dao.getList(condition.toString(),param);
	}
}
