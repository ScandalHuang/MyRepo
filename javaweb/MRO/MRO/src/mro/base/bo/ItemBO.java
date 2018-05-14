package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.entity.ClassstructurePrtype;
import mro.base.entity.Inventory;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemSite;
import mro.base.entity.Prline;
import mro.base.entity.VwNewvendorcodeEpmall;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Item getItem(String itemnum){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		return (Item) commonDAO.uniQuery(Item.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public List<Item> getItemList(String itemnum){
		return this.getItemList(null, itemnum, null, null,null,null,false);
	}
	
	@Transactional(readOnly = true)
	public List<Item> getItemList(String classstructureid,String itemnum,String description,
			Map<String,String> map,boolean secondSourceType){
		return this.getItemList(classstructureid, itemnum, description, map,null,null,secondSourceType);
	}
	
	@Transactional(readOnly = true)
	public List<Item> getItemList(String classstructureid,String itemnum,String description,
			Map<String,String> map,String locationSite,LocationSiteActionType actionType,
			boolean secondSourceType){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		criterions.add(RestrictionsUtils.like("itemnum", Utility.toUpperCase(itemnum),MatchMode.START));
		criterions.add(RestrictionsUtils.like("description", Utility.toUpperCase(description),MatchMode.START));
		criterions.add(RestrictionsUtils.in("classstructureid", map==null?null:map.values()));
		
		if(actionType!=null && StringUtils.isNotBlank(locationSite)){
			if(actionType.compareTo(LocationSiteActionType.I)==0){
				criterions.add(RestrictionsUtils.disjunction(
						Restrictions.eq("commoditygroup", ItemType.R94.toString()),
						RestrictionsUtils.exists(ItemSite.class, "locationSite", 
						Restrictions.eq("enableFlag", "Y"),Restrictions.eq("locationSite", locationSite),
						Restrictions.eqProperty("itemnum",Item.class.getSimpleName()+".itemnum"))));
				
			}else if(actionType.compareTo(LocationSiteActionType.S)==0){
				criterions.add(RestrictionsUtils.disjunction(
						Restrictions.eq("commoditygroup", ItemType.R94.toString()),
						RestrictionsUtils.notExists(ItemSite.class, "locationSite", 
						Restrictions.eq("enableFlag", "Y"),Restrictions.eq("locationSite", locationSite),
						Restrictions.eqProperty("itemnum",Item.class.getSimpleName()+".itemnum"))));
				
			}
		}
		if(secondSourceType){
			criterions.add(RestrictionsUtils.exists(ItemAttribute.class, "item.itemid", 
					Restrictions.eqProperty("item.itemid",Item.class.getSimpleName()+".itemid"),
					Restrictions.isNull("secondSourceItemnum")));
		}
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	@Transactional(readOnly = true)
	public List getInactiveItemSiteBySite(List<String> itemList,String locationSite){
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("itemnum", itemList));
		//2014/02/07 94 暫不做區域判斷
		criterions.add(Restrictions.ne("commoditygroup", ItemType.R94.toString()));
		criterions.add(RestrictionsUtils.notExists(ItemSite.class, "locationSite", 
				Restrictions.eq("enableFlag", "Y"),Restrictions.eq("locationSite", locationSite),
				Restrictions.eqProperty("itemnum",Item.class.getSimpleName()+".itemnum")));
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	@Transactional(readOnly = true)
	public List getActiveItemSiteBySite(List<String> itemList,String locationSite){
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("itemnum", itemList));
		criterions.add(Restrictions.ne("commoditygroup", ItemType.R94.toString()));
		criterions.add(RestrictionsUtils.exists(ItemSite.class, "locationSite", 
				Restrictions.eq("enableFlag", "Y"),Restrictions.eq("locationSite", locationSite),
				Restrictions.eqProperty("itemnum",Item.class.getSimpleName()+".itemnum")));
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	@Transactional(readOnly = true)
	public List getNeStautsItem(List<String> itemList,List<String> status){
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("itemnum", itemList));
		criterions.add(Restrictions.not(Restrictions.in("status", status)));
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}	
	/*
	 * hongjie.wu
	 * 2015.03.06
	 * 驗證廠商是否合法  且 存在INVVENDOR 裡面
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Item> validateVmVendorByPrline(List<Prline> prlines) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		List<String> itemList=new ArrayList<String>();
		for(Prline prline:prlines){
			if(StringUtils.isNotBlank(prline.getVendor())){
				itemList.add(prline.getItemnum());
				criterionL.add(RestrictionsUtils.conjunction(
						Restrictions.eq("itemnum", prline.getItemnum()),
						Restrictions.eq("vendor", prline.getVendor()),
						Restrictions.eq("disabled", NumberUtils.createBigDecimal("0")),
						RestrictionsUtils.exists(VwNewvendorcodeEpmall.class, "nvcid", 
						Restrictions.eqProperty("nvcid", Invvendor.class.getSimpleName()+".vendor"))));
			}
		}
		if(Utility.isNotEmpty(itemList)){
			criterions.add(Restrictions.in("itemnum", itemList));
			criterions.add(RestrictionsUtils.propertyNotIn(Invvendor.class, "itemnum", "itemnum", 
							RestrictionsUtils.disjunction(criterionL)));
			return  commonDAO.query(Item.class, Arrays.asList(Order.asc("itemnum")), criterions);
		}
		return new ArrayList<Item>();
	}
	
	/*
	 * hongjie.wu
	 * 2015.03.16
	 * 驗證料號類別結構
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Item> validateClassstructureid(List<String> itemList,String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("itemnum", itemList));
		criterions.add(Restrictions.ne("classstructureid", classstructureid));
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.04.27
	 * 驗證ClassstructurePrtype
	 * return 錯誤的list
	 */
	@Transactional(readOnly=true)
    public List<Item> validateClassstructurePrtype(List<String> itemList,String prtype) {
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("itemnum", itemList));
		if(StringUtils.isNotBlank(prtype)){
			criterions.add(RestrictionsUtils.notExists(ClassstructurePrtype.class, 
				"classstructureid", 
				Restrictions.eqProperty("classstructureid", 
						Item.class.getSimpleName()+".classstructureid"),
				Restrictions.eq("prtype", prtype)));
		}
		return commonDAO.query(Item.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	/*
	 * hongjie.wu
	 * 2016.09.12
	 * 驗證 預設控管模 式
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Item> notInventory(List<Prline> prlines) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("itemnum",prlines.stream().map(l->l.getItemnum()).collect(Collectors.toList())));
		prlines.stream().forEach(l->{
			criterions.add(RestrictionsUtils.notExists(Inventory.class, "itemnum", 
					Restrictions.eqProperty("itemnum",Item.class.getSimpleName()+".itemnum"),
					Restrictions.eq("siteid",l.getSiteid()),
					Restrictions.eq("location",l.getStoreroom()),
					Restrictions.isNotNull("cmommcontrol")));
		});
		return  commonDAO.query(Item.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
}