package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.R2LTListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class R2LTListBo {

    private R2LTListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new R2LTListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String itemnum, String description) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemnum)){
//			condition.append("AND inv.itemnum in ( SELECT   ITEMNUM "
//					+ "FROM   ITEM WHERE   ITEMNUM LIKE :itemnum "
//					+ "UNION (SELECT   OLD_MATNR FROM   ITEM_MAPPING "
//					+ "WHERE       NEW_MATNR LIKE :itemnum "
//					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL)) ");
			param.put("itemnum", itemnum+"%");
		}
		
	   return dao.getList(itemnum+"%","%"+description+"%",param);
	}
	
	
}
