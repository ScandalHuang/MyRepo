package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Assetattribute;

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
public class AssetattributeBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	public Map<String, Assetattribute> getMap(List<Assetattribute> list){
		Map<String, Assetattribute> map=new HashMap<String, Assetattribute>();
		for(Assetattribute assetattribute:list){
			map.put(assetattribute.getAssetattrid(),assetattribute);
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Assetattribute> getMap(BigDecimal applyHeaderId,Class entity){
		return getMap(getList(null,null,applyHeaderId,null,entity));
	}

	@Transactional(readOnly = true)
	public Map<String, Assetattribute> getMap(String classstructureid,Class entity){
		return getMap(getList(null,classstructureid,null,null,entity));
	}
	@Transactional(readOnly = true)
	public List<Assetattribute> getListNonIn(List nonAssetattridList) {
		return getList(null,null,null,nonAssetattridList,null);
	}
	@Transactional(readOnly = true)
	public List<Assetattribute> getList() {
		return getList(null,null,null,null,null);
	}
	@Transactional(readOnly = true)
	public List<Assetattribute> getList(String assetattrid,
			String classstructureid,BigDecimal applyHeaderId,
			List nonAssetattridList,Class entity) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("assetattrid", assetattrid));
		criterions.add(RestrictionsUtils.not(RestrictionsUtils.in("assetattrid", nonAssetattridList)));
		if(StringUtils.isNotBlank(classstructureid) || applyHeaderId!=null){
			criterions.add(RestrictionsUtils.exists(entity, "assetattrid",
				Restrictions.eqProperty("assetattrid", entity.getSimpleName()+".assetattrid"),
				RestrictionsUtils.eq("classstructureid", classstructureid),
				RestrictionsUtils.eq("classstructureHeaderApply.applyHeaderId", applyHeaderId)));
		}
		return  commonDAO.query(Assetattribute.class, 
				Arrays.asList(Order.asc("assetattrid")), criterions);
	}
}
