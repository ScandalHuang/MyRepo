package mro.app.alitem.bo;

import java.util.List;
import java.util.Map;

import mro.app.alitem.dao.Classstructure2ndListDao;

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
public class Classstructure2ndListBo {

    private Classstructure2ndListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new Classstructure2ndListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String classstructureid,String assetattrid) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(classstructureid)){
			condition.append("AND classstructureid = :classstructureid");
			param.put("classstructureid", classstructureid);
		}
		if(StringUtils.isNotBlank(assetattrid)){
			String assetattridT=assetattrid+"%";
			condition.append("AND assetattrid  like :assetattridT ");
			param.put("assetattridT", assetattridT);
		}
		
	   return dao.getList(condition.toString(),param);
	}
}
