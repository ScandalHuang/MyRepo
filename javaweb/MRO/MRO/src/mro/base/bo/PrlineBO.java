package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.basicType.SignStatus;
import mro.base.dao.OracleFunctionDAO;
import mro.base.entity.Bsstrkhist;
import mro.base.entity.Invbalances;
import mro.base.entity.Inventory;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.entity.view.EpMroItemCategoryV;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.SessionFactory;
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
public class PrlineBO {

	private CommonDAO commonDAO;
	private OracleFunctionDAO oracleFunctionDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
		oracleFunctionDAO = new OracleFunctionDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Long getPrlineCount(BigDecimal prid,SignStatus signStatus) {
		List criterions = new ArrayList();
		
		criterions.add(RestrictionsUtils.eq("pr.prid", prid));
		criterions.add(RestrictionsUtils.eq("lineClosedCode", 
				signStatus!=null?signStatus.toString():signStatus));
		return commonDAO.queryCount(Prline.class, criterions);
	}
	@Transactional(readOnly = true)
	public Long getPrlineCount(BigDecimal prid,boolean prpoFlag) {
		List criterions = new ArrayList();
		
		criterions.add(RestrictionsUtils.eq("pr.prid", prid));
		if(prpoFlag){
			criterions.add(RestrictionsUtils.disjunction(
					Restrictions.isNotNull("erpPo"),
					Restrictions.eq("erpPrStatus", "Approved")));
		}
		return commonDAO.queryCount(Prline.class, criterions);
		
	}

	@Transactional(readOnly = true)
	public Prline getMaxEnterPrline(String itemnum,List<String> prtypes){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("lineClosedCode", SignStatus.OPEN.toString()));
		criterions.add(RestrictionsUtils.eq("pr.status", SignStatus.APPR.toString()));
		criterions.add(RestrictionsUtils.in("pr.prtype", prtypes));
		criterions.add(RestrictionsUtils.exists(Person.class, "personId", 
				Restrictions.eq("status", SignStatus.ACTIVE.toString()),
				Restrictions.eqProperty("personId", Prline.class.getSimpleName()+".requestedby2")));
		return (Prline) commonDAO.uniQuery(Prline.class, Arrays.asList(Order.desc("enterdate")), criterions,
				new CtriteriaFetchMode("pr"));
	}
	@Transactional(readOnly = true)
	public List<Prline> getPrlineList(BigDecimal prid,SignStatus signStatus) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("pr.prid", prid));
		criterions.add(RestrictionsUtils.eq("lineClosedCode", 
				signStatus!=null?signStatus.toString():signStatus));
		
		List<Prline> list = commonDAO.query(Prline.class, Arrays.asList(Order.asc("prlinenum")), criterions);
		List<Prline> newlist = new ArrayList<Prline>();
		for(Prline prline:list){
			BigDecimal issitecontrol = oracleFunctionDAO.getIssitecontrol(prline.getItemnum(), prline.getSiteid());
			
			if(issitecontrol!=null&&issitecontrol.compareTo(new BigDecimal(0))>0){
				prline.setIssitecontrol("Y");
			} else 
				prline.setIssitecontrol("N");
			newlist.add(prline);
		}
		return newlist;
		
	}
	@Transactional(readOnly = true)
	public List<Prline> getPrlineBySite(List<String> itemnum,String locationSite,SignStatus status) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("lineClosedCode", SignStatus.OPEN.toString()));
		criterions.add(RestrictionsUtils.in("itemnum", itemnum));
		criterions.add(RestrictionsUtils.exists(Pr.class,
				"prnum", Restrictions.eq("status", status.toString()),
				Restrictions.eqProperty("prnum",Prline.class.getSimpleName()+".prnum")));
		criterions.add(RestrictionsUtils.exists(LocationMap.class,
				"siteId",Restrictions.eqProperty("siteId",Prline.class.getSimpleName()+".siteid"),
				RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		return commonDAO.query(Prline.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
	
	@Transactional(readOnly = true)
	public List<Prline> getPrlineByInvantory(String prnum) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("prnum", prnum));
		criterions.add(RestrictionsUtils.exists(Inventory.class, "itemnum", 
				Restrictions.eqProperty("itemsetid", Prline.class.getSimpleName()+".itemsetid"),
				Restrictions.eqProperty("siteid", Prline.class.getSimpleName()+".siteid"),
				Restrictions.eqProperty("itemnum",Prline.class.getSimpleName()+".itemnum"),
				Restrictions.eqProperty("location",Prline.class.getSimpleName()+".storeroom"),
				Restrictions.neProperty("sstock",Prline.class.getSimpleName()+".invsstock")));
		
		return commonDAO.query(Prline.class, Arrays.asList(Order.asc("prlinenum")), criterions);
	}
	@Transactional(readOnly = true)
	public List<Prline> getPrlineByInvbalances(String prnum) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("prnum", prnum));
		criterions.add(RestrictionsUtils.exists(Invbalances.class, "itemnum", 
				Restrictions.eqProperty("itemnum",Prline.class.getSimpleName()+".itemnum"),
				Restrictions.eqProperty("location",Prline.class.getSimpleName()+".storeroom"),
				Restrictions.eqProperty("deptcode",Prline.class.getSimpleName()+".deptcode"),
				Restrictions.neProperty("minlevel",Prline.class.getSimpleName()+".oriminlevel")));
		
		return commonDAO.query(Prline.class, Arrays.asList(Order.asc("prlinenum")), criterions);
	}
	
	@Transactional(readOnly = true)
	public List<Prline> getPrlineList(List<String> itemnum,String MDept,String prtype
			,String siteid,SignStatus prStatus,SignStatus lineSatus){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("lineClosedCode", lineSatus.toString()));
		criterions.add(RestrictionsUtils.eq("pr.MDept", MDept));
		criterions.add(RestrictionsUtils.eq("pr.prtype", prtype));
		criterions.add(RestrictionsUtils.eq("pr.siteid", siteid));
		criterions.add(RestrictionsUtils.eq("pr.status", prStatus.toString()));
		List<Prline> list = commonDAO.query(Prline.class, Arrays.asList(Order.asc("itemnum")), criterions,
				new CtriteriaFetchMode("pr"));
		List<Prline> newlist = new ArrayList<Prline>();
		for(Prline prline:list){
			BigDecimal issitecontrol = oracleFunctionDAO.getIssitecontrol(prline.getItemnum(), prline.getSiteid());
			
			if(issitecontrol!=null&&issitecontrol.compareTo(new BigDecimal(0))>0){
				prline.setIssitecontrol("Y");
			} else 
				prline.setIssitecontrol("N");
			newlist.add(prline);
		}
		return newlist;
	}
	/*
	 * 2015.03.06
	 * hongjie.wu
	 * 安全存量驗證	
	 * (nvl(NEWSSTOCK,0) !=0 or nvl(SSTOCK,0) !=0)
	 */
	@Transactional(readOnly = true)
	public List<Prline> validateSstock (List<String> itemList,String siteid){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("itemnum", itemList));
		criterions.add(RestrictionsUtils.eq("lineClosedCode", SignStatus.OPEN.toString()));
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.conjunction(Restrictions.isNotNull("newsstock"),
						Restrictions.ne("newsstock", NumberUtils.createBigDecimal("0"))),
				RestrictionsUtils.conjunction(Restrictions.isNotNull("sstock"),
						Restrictions.ne("sstock", NumberUtils.createBigDecimal("0")))));
		criterions.add(RestrictionsUtils.eq("pr.siteid", siteid));
		criterions.add(RestrictionsUtils.eq("pr.status", SignStatus.INPRG.toString()));
		return commonDAO.query(Prline.class, Arrays.asList(Order.asc("itemnum")), criterions,
				new CtriteriaFetchMode("pr"));
	}
}
