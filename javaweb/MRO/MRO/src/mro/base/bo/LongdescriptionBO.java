package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.Longdescription;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class LongdescriptionBO {
	
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
    
	@Transactional(readOnly=true)
    public Longdescription getLongdescription(BigDecimal invbalancesid) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("ldkey", invbalancesid));
		
    	return (Longdescription) commonDAO.uniQuery(Longdescription.class, null, criterions);
    }
}
