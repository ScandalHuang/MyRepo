package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.entity.AItem;
import mro.base.entity.AItemSimple;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
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
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class AItemBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<AItem> getList(Set<BigDecimal> taskIds){
		if(taskIds==null || taskIds.size()==0) return null;
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("taskId", taskIds));	
		return commonDAO.query(AItem.class,Arrays.asList(Order.desc("itemnum")), criterions);
	}
	@Transactional(readOnly = true)
	public AItem getAItem(BigDecimal eaudittransid,boolean itemFlag){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("eaudittransid", eaudittransid));
		
		return (AItem) commonDAO.uniQuery(AItem.class,null, criterions,
				itemFlag? new CtriteriaFetchMode("item",Criteria.LEFT_JOIN):null);
	}
	@Transactional(readOnly = true)
	public List getAItemList(List<String> itemList,String locationSite,ItemStatusType itemStatusType){
		List criterions = new ArrayList();
		criterions.add(Restrictions.in("oriitemnum", itemList));
		criterions.add(Restrictions.in("status", itemStatusType.getValue()));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "organizationCode",
				Restrictions.eqProperty("organizationCode", AItem.class.getSimpleName()+".organizationCode"),
				RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		
		return commonDAO.query(AItem.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
	@Transactional(readOnly = true)
	public List getAItemList(String locationSite,String organizationCode,String num,String description,
			String status,String changeby,String itemType,
			String deptNo,Date beginDate,Date endDate,ItemStatusType itemStatusType,
			boolean changeFlag){
		List criterions = new ArrayList();
		if(StringUtils.isNotBlank(locationSite)){
			criterions.add(RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
					Restrictions.eqProperty("organizationCode", AItem.class.getSimpleName()+".organizationCode"),
					RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		}
		criterions.add(RestrictionsUtils.eq("organizationCode", organizationCode));
		criterions.add(RestrictionsUtils.eq("status", status));
		criterions.add(RestrictionsUtils.eq("changeby", changeby));
		criterions.add(RestrictionsUtils.like("classstructureid", itemType,MatchMode.START));
		criterions.add(RestrictionsUtils.like("description", Utility.toUpperCase(description),MatchMode.ANYWHERE));
		criterions.add(RestrictionsUtils.like("deptNo", deptNo,MatchMode.START));
		criterions.add(RestrictionsUtils.gt("createDate", beginDate));
		criterions.add(RestrictionsUtils.lt("createDate", endDate!=null?
					new Date(endDate.getTime() + (1000 * 60 * 60 * 24)):null));
		criterions.add(RestrictionsUtils.not(RestrictionsUtils.in("status", itemStatusType.getValue())));

		criterions.add(changeFlag?Restrictions.isNotNull("oriitemnum"):Restrictions.isNull("oriitemnum"));
		
		if(StringUtils.isNotBlank(num)){
			List<Criterion> unionList=new ArrayList<Criterion>();
			unionList.add(RestrictionsUtils.like("itemnum", num.toUpperCase(),MatchMode.START));
			unionList.add(RestrictionsUtils.like("item.itemnum", num.toUpperCase(),MatchMode.START));
			return commonDAO.unionQuery(AItem.class,Arrays.asList(Order.desc("itemnum")), criterions,
					unionList,Arrays.asList(new CtriteriaFetchMode("item",Criteria.LEFT_JOIN)));
		}
		return commonDAO.query(AItem.class,Arrays.asList(Order.asc("itemnum")), criterions,
				new CtriteriaFetchMode("item",Criteria.LEFT_JOIN));
	}
	
	@Transactional(readOnly = true)
	public List<AItem> getAItemListBySimple(Date date){
		List criterions = new ArrayList();
		Calendar calendar = Calendar.getInstance() ;
		criterions.add(RestrictionsUtils.exists(AItemSimple.class, "applyNum",
				Restrictions.eqProperty("applyNum", AItem.class.getSimpleName()+".itemnum"),
				RestrictionsUtils.gt("uploadDate", date),
				RestrictionsUtils.lt("uploadDate", new Date(date.getTime() + (1000 * 60 * 60 * 24)))));
		
		return commonDAO.query(AItem.class,Arrays.asList(Order.asc("itemnum")), criterions);
	}
}
