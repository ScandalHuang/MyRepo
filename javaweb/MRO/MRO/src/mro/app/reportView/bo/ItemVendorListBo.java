package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemVendorListDao;

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
public class ItemVendorListBo {

    private ItemVendorListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemVendorListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String  cItemnum,String  itemnum) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(cItemnum)){
			condition.append("AND itemnum like :cItemnum ");
			param.put("cItemnum", cItemnum+"%");
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND itemnum = :itemnum ");
			param.put("itemnum",itemnum);
		}
		
	   return dao.getList(condition.toString(),param);
	}
}
