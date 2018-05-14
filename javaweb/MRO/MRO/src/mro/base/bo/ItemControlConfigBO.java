package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.ItemControlConfig;
import mro.base.entity.Person;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.hibernate.ActiveFlag;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemControlConfigBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = false)
	public void save(ItemControlConfig entity,String action,String column,Person person) {
		ItemControlConfig temp=getItemControlConfig(entity.getItemnum(), entity.getLocationSite());
		if(temp!=null) entity=temp; else entity.setId(null);
		if(action.equals("START")){
			ReflectUtils.setFieldValue(entity, column, ActiveFlag.N.name());
		}else if(action.equals("CLOSE")){
			ReflectUtils.setFieldValue(entity, column, ActiveFlag.Y.name());
		}
		entity.setLastUpdate(new Date(System.currentTimeMillis()));
		entity.setLastUpdateBy(person.getPersonId());
		commonDAO.insertUpdate(entity);
	}
	
	@Transactional(readOnly = true)
	public ItemControlConfig getItemControlConfig(String itemnum,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		return (ItemControlConfig) commonDAO.uniQuery(ItemControlConfig.class,null, criterions,
				new CtriteriaFetchMode("item",Criteria.LEFT_JOIN));
	}
	
	@Transactional(readOnly = true)
	public List<ItemControlConfig> getList(String locationSite,String reorderDisableFlag,
			String highlightDisableFlag) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		criterions.add(RestrictionsUtils.eq("reorderDisableFlag", reorderDisableFlag));
		criterions.add(RestrictionsUtils.eq("highlightDisableFlag", highlightDisableFlag));
		return commonDAO.query(ItemControlConfig.class,
				Arrays.asList(Order.asc("lastUpdate")), criterions,
				new CtriteriaFetchMode("item",Criteria.LEFT_JOIN));
	}
}