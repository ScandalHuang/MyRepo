package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.AItemspec;
import mro.base.entity.Classspec;

import org.apache.commons.lang3.StringUtils;
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
public class AItemspecBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public Map<String, AItemspec> getMap(BigDecimal eaudittransid,String classstructureid){
		Map<String, AItemspec> map=new HashMap<String, AItemspec>();
		for(AItemspec aItemspec:getList(eaudittransid,classstructureid)){
			map.put(aItemspec.getAssetattrid(),aItemspec);
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public List<AItemspec> getList(BigDecimal eaudittransid,String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("AItem.eaudittransid", eaudittransid));
		if(StringUtils.isNotBlank(classstructureid)){
			criterions.add(RestrictionsUtils.exists(Classspec.class, "assetattrid",
				Restrictions.eqProperty("assetattrid", AItemspec.class.getSimpleName()+".assetattrid"),
				Restrictions.eq("classstructureid", classstructureid),
				Restrictions.or(Restrictions.isNull("inactiveDate"),
						RestrictionsUtils.ge("inactiveDate", new Date()))));
		}
		return  commonDAO.query(AItemspec.class, 
				Arrays.asList(Order.asc("displaysequence")), criterions);
	}

}
