package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mro.base.System.config.SystemConfig;
import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;
import mro.base.entity.SignTask;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class SignSourceCategoryBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
	public Map getMap (String systemName){
		Map map=new HashedMap();
		List<SignSourceCategory> list=getList(systemName);
		for(SignSourceCategory s:list){
			map.put(s.getSourceCategoryKey(), s.getDescription());
		}
		return map;
	}
	@Transactional(readOnly=true)
	public List<SignSourceCategory> getList (String systemName){
		List criterions = new ArrayList();

		criterions.add(Restrictions.eq("signSourceSystem.sourceSystemKey", systemName));
		
		return commonDAO.query(SignSourceCategory.class, 
				Arrays.asList(Order.asc("sourceCategoryKey")), criterions,
				new CtriteriaFetchMode("signSourceSystem"));
	}
	@Transactional(readOnly=true)
	public List<SignSourceCategory> getList (SignSourceSystem signSourceSystem){
		List criterions = new ArrayList();

		criterions.add(Restrictions.eq("signSourceSystem", signSourceSystem));
		
		return commonDAO.query(SignSourceCategory.class, Arrays.asList(Order.asc("sourceCategoryKey")), criterions);
	}
	@Transactional(readOnly=true)
	public SignSourceCategory getSignSourceCategory(BigDecimal taskId){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.exists(SignTask.class, "taskId",
				Restrictions.eqProperty("sourceCategory", 
						SignSourceCategory.class.getSimpleName()+".sourceCategoryKey"),
				Restrictions.eq("taskId", taskId)));
		criterions.add(RestrictionsUtils.propertyEq(
				SignSourceSystem.class, "signSourceSystem.sourceSystemId", "sourceSystemId", 
				Restrictions.eq("sourceSystemKey", SystemConfig.SYSTEMNAME)));
		return (SignSourceCategory) commonDAO.uniQuery(SignSourceCategory.class, null, criterions);
	}
}
