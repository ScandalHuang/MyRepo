package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ItemSite;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.SapPlantAttribute;
import mro.base.entity.SapProfitCenter;

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
public class SapPlantAttributeBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List getListByPlant(String plantCode,String materialGroup){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("materialGroup", materialGroup));
		criterions.add(RestrictionsUtils.eq("plantCode", plantCode));
		criterions.add(RestrictionsUtils.exists(SapProfitCenter.class, "plantCode", 
				Restrictions.eqProperty("plantCode", SapPlantAttribute.class.getSimpleName()+".plantCode"),
				Restrictions.isNotNull("profitCenter")));
		
		return commonDAO.query(SapPlantAttribute.class,Arrays.asList(Order.asc("plantCode")), criterions);
	}
	@Transactional(readOnly = true)
	public List getListBySite(String locationSite,String materialGroup,boolean nonValueFlag){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("materialGroup", materialGroup));
		
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.exists(LocationMap.class, "organizationCode",
						Restrictions.eqProperty("organizationCode", SapPlantAttribute.class.getSimpleName()+".plantCode"),
						RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)),
						!nonValueFlag?null:
						RestrictionsUtils.exists(LocationMap.class, "nonvaluePlant",
								Restrictions.eqProperty("nonvaluePlant", SapPlantAttribute.class.getSimpleName()+".plantCode"),
								RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite))
				));
		
		criterions.add(RestrictionsUtils.exists(SapProfitCenter.class, "plantCode", 
				Restrictions.eqProperty("plantCode", SapPlantAttribute.class.getSimpleName()+".plantCode"),
				Restrictions.isNotNull("profitCenter")));
		
		return commonDAO.query(SapPlantAttribute.class,Arrays.asList(Order.asc("plantCode")), criterions);
	}
	
	@Transactional(readOnly = true)
	public List getListByItem(String itemnum,String materialGroup){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("materialGroup", materialGroup));
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.exists(LocationMap.class, "organizationCode",
						Restrictions.eqProperty("organizationCode", SapPlantAttribute.class.getSimpleName()+".plantCode"),
						RestrictionsUtils.propertyIn(ItemSite.class, "locationSiteMap.locationSite", "locationSite",
								RestrictionsUtils.eq("itemnum", itemnum),
								RestrictionsUtils.eq("enableFlag", "Y"))),
						RestrictionsUtils.exists(LocationMap.class, "nonvaluePlant",
								Restrictions.eqProperty("nonvaluePlant", SapPlantAttribute.class.getSimpleName()+".plantCode"),
								RestrictionsUtils.propertyIn(ItemSite.class, "locationSiteMap.locationSite", "locationSite",
										RestrictionsUtils.eq("itemnum", itemnum),
										RestrictionsUtils.eq("enableFlag", "Y")))
				));
		
		criterions.add(RestrictionsUtils.exists(SapProfitCenter.class, "plantCode", 
				Restrictions.eqProperty("plantCode", SapPlantAttribute.class.getSimpleName()+".plantCode"),
				Restrictions.isNotNull("profitCenter")));
		
		return commonDAO.query(SapPlantAttribute.class,Arrays.asList(Order.asc("plantCode")), criterions);
	}
}
