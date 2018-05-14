package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.AlndomainCommon;
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
public class AlndomainCommonBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Map<String,String>> getMap(String itemtype,String classstructureid){
		Map<String, Map<String,String>> map=new HashMap<String, Map<String,String>>();
		for(AlndomainCommon m:getList(itemtype,classstructureid)){
			if(map.get(m.getAssetattrid())==null){
				map.put(m.getAssetattrid(), new HashMap<String,String>());
			}
			map.get(m.getAssetattrid()).put(m.getDescription().toUpperCase(), m.getDescription().toUpperCase());
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public List<AlndomainCommon> getList(String itemtype,String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemtype", itemtype));
		if(StringUtils.isNotBlank(classstructureid)){
			criterions.add(RestrictionsUtils.exists(Classspec.class, "assetattrid",
				Restrictions.eqProperty("assetattrid", AlndomainCommon.class.getSimpleName()+".assetattrid"),
				Restrictions.eq("classstructureid", classstructureid),
				Restrictions.or(Restrictions.isNull("inactiveDate"),
						RestrictionsUtils.ge("inactiveDate", new Date()))));
		}
		return  commonDAO.query(AlndomainCommon.class, 
				Arrays.asList(Order.asc("description")), criterions);
	}

}
