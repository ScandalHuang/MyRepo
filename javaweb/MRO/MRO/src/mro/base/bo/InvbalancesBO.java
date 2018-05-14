package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.HrOrg;
import mro.base.entity.Invbalances;
import mro.base.entity.ItemSite;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacility;
import mro.base.entity.Prline;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.ActiveFlag;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class InvbalancesBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public Invbalances getInvbalance(BigDecimal invbalancesid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("invbalancesid", invbalancesid));
		return  (Invbalances) commonDAO.uniQuery(Invbalances.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public List getInactiveDept(List<LocationSiteMap> locationSiteMaps) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.gt("minlevel", new BigDecimal("0")));
		criterions.add(RestrictionsUtils.propertyIn(HrOrg.class, "binnum", "deptNo", 
				RestrictionsUtils.disjunction(Restrictions.isNull("deptLevel"),
						Restrictions.eq("deptLevel", new BigDecimal("0")))));
		criterions.add(RestrictionsUtils.exists(LocationMap.class, "siteId", 
				Restrictions.eqProperty("siteId", Invbalances.class.getSimpleName()+".siteid"),
				RestrictionsUtils.in("locationSiteMap", locationSiteMaps)));
		return  commonDAO.query(Invbalances.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public BigDecimal getSstockSum(String itemnum,String location,String siteid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("location", location));	
		criterions.add(RestrictionsUtils.eq("siteid", siteid));	
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("sstock"));
		return (BigDecimal)commonDAO.query(Invbalances.class, null, criterions, projectionList, null);
	}
	
	@Transactional(readOnly = true)
	public List<Invbalances> getInvbalancesBySite(List<String> itemnum,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.gt("minlevel", new BigDecimal("0")));
		criterions.add(RestrictionsUtils.in("itemnum", itemnum));
		criterions.add(RestrictionsUtils.exists(LocationMap.class,
				"siteId",Restrictions.eqProperty("siteId",Invbalances.class.getSimpleName()+".siteid"),
				RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		return commonDAO.query(Invbalances.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
	/*
	 * hongjie.wu
	 * 2016.05.09
	 * 取得未生效區域但有控管量的清單
	 */
	@Transactional(readOnly = true)
	public List<Invbalances> getInvbalancesBySiteStop() {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.gt("minlevel", new BigDecimal("0")));
		criterions.add(RestrictionsUtils.notExists(ItemSite.class,"itemnum",
				RestrictionsUtils.eq("enableFlag", ActiveFlag.Y.name()),
				Restrictions.eqProperty("itemnum",Invbalances.class.getSimpleName()+".itemnum"),
				RestrictionsUtils.exists(LocationMap.class, "locationSiteMap.locationSite", 
						Restrictions.eqProperty("locationSiteMap.locationSite",ItemSite.class.getSimpleName()+".locationSite"),
						Restrictions.eqProperty("siteId",Invbalances.class.getSimpleName()+".siteid"))));
		return commonDAO.query(Invbalances.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
	
	@Transactional(readOnly = true)
	public Invbalances getInvbalance(String itemnum,String deptcode,String location,String siteid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("location", location));
		criterions.add(RestrictionsUtils.eq("deptcode", deptcode));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		return  (Invbalances) commonDAO.uniQuery(Invbalances.class, null, criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.03.04
	 * controlFlag=true : 驗證PRLINE的月用量,原重訂購量,原平均月耗用量,原最低安全存量是否與設定檔一致
	 * controlFlag=false : 驗證PRLINE的月用量是否與設定檔一致
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Invbalances> validateInvbalancesByPrline(List<Prline> prlines,boolean controlFlag) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			BigDecimal oriMinlevel=prline.getOriminlevel();
			if(controlFlag){  //94,R2控管申請單(原重訂購量)
				oriMinlevel=oriMinlevel.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("itemnum",prline.getItemnum()),
					Restrictions.eq("deptcode",prline.getMDept()),
					Restrictions.eq("siteid",prline.getSiteid()),
					Restrictions.eq("location",prline.getStoreroom()),
						RestrictionsUtils.disjunction(
								RestrictionsUtils.conjunction(Restrictions.isNotNull("minlevel"),
								Restrictions.ne("minlevel", oriMinlevel)),
								controlFlag?RestrictionsUtils.conjunction(Restrictions.isNotNull("oriavguseqty"),
								Restrictions.ne("oriavguseqty", prline.getOriavguseqty())):null,
								controlFlag?RestrictionsUtils.conjunction(Restrictions.isNotNull("sstock"),
								Restrictions.ne("sstock", prline.getOrisstock())):null)));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return  commonDAO.query(Invbalances.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
}
