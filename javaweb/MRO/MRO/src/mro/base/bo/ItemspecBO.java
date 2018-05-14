package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Classspec;
import mro.base.entity.ClassstructureSecondSource;
import mro.base.entity.Itemspec;

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
public class ItemspecBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Map<String, Itemspec> getMap(List<Itemspec> list){
		Map<String, Itemspec> map=new LinkedHashMap<String, Itemspec>();
		for(Itemspec itemspec:list){
			map.put(itemspec.getAssetattrid(),itemspec);
		}
		return map;
	}
	@Transactional(readOnly = true)
	public Map<String, Itemspec> getMap(BigDecimal itemid,String classstructureid){
		return this.getMap(getList(itemid,classstructureid));
	}
	
	@Transactional(readOnly = true)
	public List<Itemspec> getList(BigDecimal itemid,String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("item.itemid", itemid));
		if(StringUtils.isNotBlank(classstructureid)){
			criterions.add(RestrictionsUtils.exists(Classspec.class, "assetattrid",
				Restrictions.eqProperty("assetattrid", Itemspec.class.getSimpleName()+".assetattrid"),
				Restrictions.eq("classstructureid", classstructureid),
				Restrictions.or(Restrictions.isNull("inactiveDate"),
						RestrictionsUtils.ge("inactiveDate", new Date()))));
		}
		return  commonDAO.query(Itemspec.class, 
				Arrays.asList(Order.asc("displaysequence")), criterions);
	}
	
	@Transactional(readOnly = true)
	public List<Itemspec> getListBySecondSource(String itemnum) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.exists(ClassstructureSecondSource.class, "assetattrid",
				Restrictions.eqProperty("assetattrid", Itemspec.class.getSimpleName()+".assetattrid"),
				Restrictions.eqProperty("classstructureid", Itemspec.class.getSimpleName()+".classstructureid")));
		return  commonDAO.query(Itemspec.class, Arrays.asList(Order.asc("displaysequence")), criterions);
	}
}
