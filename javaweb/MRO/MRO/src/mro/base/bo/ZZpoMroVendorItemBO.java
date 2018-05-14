package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ZZpoMroVendorItem;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ZZpoMroVendorItemBO {
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
    
	@Transactional(readOnly=true)
    public List<ZZpoMroVendorItem> getZZpoMroVendorItem(String segment1) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("segment1", segment1));
		
    	return commonDAO.query(ZZpoMroVendorItem.class, Arrays.asList(Order.desc("lastPoYear")), criterions);
    }
}
