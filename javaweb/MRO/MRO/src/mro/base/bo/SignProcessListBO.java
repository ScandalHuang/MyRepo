package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.SignProcessList;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class SignProcessListBO {
	
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
    
	@Transactional(readOnly=true)
    public List<SignProcessList> getList(BigDecimal processId) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("signProcess.processId", processId));
		
    	return commonDAO.query(SignProcessList.class,Arrays.asList(Order.asc("signSequence")),criterions);
    }
}
