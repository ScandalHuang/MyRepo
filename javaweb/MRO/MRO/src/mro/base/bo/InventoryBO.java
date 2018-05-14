package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.basicType.ItemType;
import mro.base.entity.ClassstructureSubinventory;
import mro.base.entity.Inventory;
import mro.base.entity.Item;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroInventoryIdleList;
import mro.base.entity.Prline;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class InventoryBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
	public Inventory getInventory(String itemnum,String itemsetid,String siteid,String location){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("itemsetid", itemsetid));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		criterions.add(RestrictionsUtils.eq("location", location));
		return (Inventory) commonDAO.uniQuery(Inventory.class, null, criterions);
	}
	@Transactional(readOnly = true)
	public List<Inventory> getInventorys(String locationSite,String siteid, 
				String prtype, String itemnum, String[] selectControlType,
				String diffControlType, boolean sotckFlag) {
		List criterions = new ArrayList();
		
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		criterions.add(RestrictionsUtils.like("itemnum", itemnum,MatchMode.START));
		if(StringUtils.isNotBlank(prtype)){
			criterions.add(RestrictionsUtils.like("itemnum",ItemType.getItemType(prtype).name(),MatchMode.START));
		}
		if(StringUtils.isNotBlank(locationSite)){
			criterions.add(RestrictionsUtils.exists(LocationMap.class, "siteId", 
					Restrictions.eqProperty("siteId",Inventory.class.getSimpleName()+".siteid"),
					RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		}
		if(selectControlType != null) {
			criterions.add(RestrictionsUtils.in("cmommcontrol", selectControlType));
		}
		if(StringUtils.isNotBlank(locationSite)&&StringUtils.isNotBlank(diffControlType)&&diffControlType.equals("Y")){
			LocationSiteMap locationSiteMap = new LocationSiteMap();
			locationSiteMap.setLocationSite(locationSite);
			criterions.add(
					Restrictions.sqlRestriction(" exists (select 1 from LOCATION_MAP m, Inventory i where m.location_site = '"+locationSite+"' and i.siteid = m.site_id and "
							+ "nvl(this_.cmommcontrol,'xxx') <> nvl(i.cmommcontrol,'xxx') and this_.ITEMNUM = i.ITEMNUM"
							+ ")")
//					RestrictionsUtils.exists(Inventory.class, "itemnum", 
//					Restrictions.eqProperty("itemnum",Inventory.class.getSimpleName()+".itemnum"),
//					Restrictions.neProperty("cmommcontrol",Inventory.class.getSimpleName()+".cmommcontrol"),					
//					RestrictionsUtils.propertyIn(LocationMap.class, "siteid", "organizationCode", 
//							Restrictions.eq("locationSiteMap", locationSiteMap))
//					)
					);
		} else if(StringUtils.isNotBlank(diffControlType)&&diffControlType.equals("Y")){
			criterions.add(
					Restrictions.sqlRestriction(" exists (select 1 from Inventory i where "
							+ "this_.cmommcontrol <> i.cmommcontrol and this_.ITEMNUM = i.ITEMNUM"
							+ ")"));
		}
		if(sotckFlag){ //pr預設倉別
			criterions.add(RestrictionsUtils.exists(ClassstructureSubinventory.class, "subinventory", 
					Restrictions.eqProperty("subinventory",Inventory.class.getSimpleName()+".location"),
					RestrictionsUtils.propertyEq(Item.class, "classstructureid", "classstructureid", 
						Restrictions.eqProperty("itemnum",Inventory.class.getSimpleName()+".itemnum")),
					RestrictionsUtils.propertyEq(LocationMap.class, "plantCode", "organizationCode", 
						Restrictions.eqProperty("siteId",Inventory.class.getSimpleName()+".siteid"))));
		}
		return commonDAO.query(Inventory.class,
				Arrays.asList(Order.asc("siteid"),Order.asc("itemnum")), criterions);
		
	}
	@Transactional(readOnly = true)
	public List<Inventory> getInventorys(String itemnum,String storeroom,String orgid) {
		List criterions = new ArrayList();
		
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.like("location", storeroom));
		criterions.add(RestrictionsUtils.eq("orgid", orgid));
		return commonDAO.query(Inventory.class,
				Arrays.asList(Order.asc("itemnum"),Order.asc("siteid")), criterions);
		
	}
	@Transactional(readOnly = true)
	public List<Inventory> getInventoryStock(String itemnum,String location) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		BigDecimal defaultS=new BigDecimal("0");
		criterions.add(RestrictionsUtils.like("itemnum", itemnum,MatchMode.START));
		criterions.add(RestrictionsUtils.eq("location", location));
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.gt("stock",defaultS),
				RestrictionsUtils.gt("iqc",defaultS),
				RestrictionsUtils.gt("mcbssonhand",defaultS),
				RestrictionsUtils.gt("sestock",defaultS),
				RestrictionsUtils.gt("sdstock",defaultS),
				RestrictionsUtils.gt("prqty",defaultS),
				RestrictionsUtils.gt("poqty",defaultS)
				));
		return commonDAO.query(Inventory.class,
				Arrays.asList(Order.asc("itemnum"),Order.asc("siteid")), criterions);
		
	}
	/*
	 * hongjie.wu
	 * 2015.03.04
	 * 驗證sstock
	 * 2016.09.12 新增控管模 式
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Inventory> validateInventoryByPrline(List<Prline> prlines) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(Restrictions.eq(
					"itemnum", prline.getItemnum()), Restrictions.eq("siteid",
					prline.getSiteid()), Restrictions.eq("location",
					prline.getStoreroom()), RestrictionsUtils.disjunction(
					Restrictions.ne("sstock", prline.getInvsstock()),
					RestrictionsUtils.conjunction(
							Restrictions.isNotNull("cmommcontrol"),
							Restrictions.ne("cmommcontrol",
									ObjectUtils.toString(prline.getCmommcontrol(),"null"))))));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return  commonDAO.query(Inventory.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}

	/*
	 * hongjie.wu
	 * 2016.11.30
	 * 取得MRO_INVENTORY_IDLE_LIST對應inventory還有庫存的清單
	 */
	@Transactional(readOnly=true)
    public List<Inventory> getInventoryIdleList(List items,LocationSiteMap locationSiteMap) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.gt("stock", new BigDecimal("0")));
		if(locationSiteMap!=null){
			criterions.add(RestrictionsUtils.exists(MroInventoryIdleList.class, "itemnum",
					Restrictions.eqProperty("itemnum",Inventory.class.getSimpleName()+".itemnum"),
					Restrictions.eqProperty("location",Inventory.class.getSimpleName()+".location"),
					RestrictionsUtils.propertyEq(LocationMap.class, "organizationCode", "organizationCode", 
							Restrictions.eqProperty("siteId",Inventory.class.getSimpleName()+".siteid")),
							Restrictions.eq("locationSiteMap", locationSiteMap)
					));
		}
		criterions.add(RestrictionsUtils.gt("stock", new BigDecimal("0")));
		criterions.add(RestrictionsUtils.in("itemnum", items));
		return  commonDAO.query(Inventory.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
}