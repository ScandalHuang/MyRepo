package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.System.config.SystemConfig;
import mro.base.entity.SignProcess;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class SignProcessBO {
	
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
    public Map getMap(boolean active){
    	Map map=new LinkedHashMap();
    	List<SignProcess> list=getList(active);
    	for(SignProcess s:list){
    		map.put(s.getSourceCategory(), 0);
    	}
    	return map;
    }
    
	@Transactional(readOnly=true)
    public List<SignProcess> getList(boolean active) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("sourceSystem", SystemConfig.SYSTEMNAME));
		
		if(active){
			criterions.add(Restrictions.or(
					RestrictionsUtils.gt("inactiveDate", new Date()),
					Restrictions.isNull("inactiveDate")));
		}
		
    	return commonDAO.query(SignProcess.class,Arrays.asList(Order.asc("sourceCategory")),criterions);
    }
	@Transactional(readOnly=true)
    public SignProcess getSignProcess(BigDecimal processId,boolean active){
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("processId", processId));
		if(active){
			criterions.add(Restrictions.or(
					RestrictionsUtils.gt("inactiveDate", new Date()),
					Restrictions.isNull("inactiveDate")));
		}
		
    	return (SignProcess) commonDAO.uniQuery(SignProcess.class,null,criterions);
    }
}
