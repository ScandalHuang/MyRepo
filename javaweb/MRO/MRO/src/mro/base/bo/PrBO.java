package mro.base.bo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.PrNumType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;
import mro.base.entity.LocationMap;
import mro.base.entity.Pr;
import mro.base.entity.Prline;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
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
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.ActiveFlag;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class PrBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly=false)
	public String getPrNum(String prtype){
		PrBO prBO=SpringContextUtil.getBean(PrBO.class);
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		DecimalFormat df = new DecimalFormat("00000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Long prnumCount=prBO.getPrNumCount(PrNumType.getPrnumTypeAll());
		if(prnumCount==0){
			functionBO.callProcedure("RESET_PRNUM_ID_SEQ");
		}
		return PrType.findValue(prtype).getValue()+prtype.substring(1,2)+
				sdf.format(new Date()) + df.format(functionBO.getSquence("PRNUM_ID_SEQ"));
	}
	
	@Transactional(readOnly = true)
	public long getPrNumCount(List<String> prnums){
		List criterions = new ArrayList();
		List criterions2 =new ArrayList();
		for(String prnum:prnums){
			criterions2.add(RestrictionsUtils.like("prnum", prnum, MatchMode.START));
		}
		criterions.add(RestrictionsUtils.disjunction(criterions2));
		
		return commonDAO.queryCount(Pr.class, criterions);
	}
	@Transactional(readOnly = true)
	public List<Pr> getPrList(String empNo,String selectPrtype,ItemStatusType itemStatusType){
		return getPrList(null,null,null, null, empNo, null, selectPrtype,null,null, itemStatusType);
	}
	@Transactional(readOnly = true)
	public List<Pr> getPrList(String locationSite,String siteid,String num,String status,String empNo,String MDept,
			String prtype,Date beginDate,Date endDate,ItemStatusType itemStatusType){
		List criterions = new ArrayList();
		if(StringUtils.isNotBlank(locationSite)){
			if(locationSite.equals("失效區域"))
				criterions.add(RestrictionsUtils.exists(LocationMap.class, "siteId", 
						Restrictions.eqProperty("siteId", Pr.class.getSimpleName()+".siteid"),
						Restrictions.isNull("locationSiteMap.locationSite")));
			else
				criterions.add(RestrictionsUtils.exists(LocationMap.class, "siteId", 
					Restrictions.eqProperty("siteId", Pr.class.getSimpleName()+".siteid"),
					RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		}
		criterions.add(RestrictionsUtils.eq("status", status));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.eq("requestedby2", empNo),
				RestrictionsUtils.eq("requestedby", empNo)));
		
		criterions.add(RestrictionsUtils.like("MDept", Utility.toUpperCase(MDept),MatchMode.START));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.gt("changedate", beginDate));
		criterions.add(RestrictionsUtils.lt("changedate", endDate!=null?
					new Date(endDate.getTime() + (1000 * 60 * 60 * 24)):null));
		criterions.add(RestrictionsUtils.not(RestrictionsUtils.in("status", itemStatusType.getValue())));
		

		if(StringUtils.isNotBlank(num)){
			List<Criterion> unionList=new ArrayList<Criterion>();
			unionList.add(RestrictionsUtils.like("prnum", num.toUpperCase(),MatchMode.START));
			unionList.add(RestrictionsUtils.propertyIn(Prline.class, "prnum", "prnum", 
						RestrictionsUtils.like("itemnum", num.toUpperCase(),MatchMode.START)));
			return commonDAO.unionQuery(Pr.class,Arrays.asList(Order.desc("issuedate")), criterions,unionList);
		}
		return commonDAO.query(Pr.class,Arrays.asList(Order.desc("issuedate")), criterions);
	}
	@Transactional(readOnly = true)
	public List<Pr> getPrListByTaskId(Collection<BigDecimal> taskIds, String prtype,
			String[] prnums,String requestedby2,String MDept ){
		if(CollectionUtils.isEmpty(taskIds)) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("taskId", taskIds));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.in("prnum", prnums));
		criterions.add(RestrictionsUtils.eq("requestedby2", requestedby2));
		criterions.add(RestrictionsUtils.eq("MDept", Utility.toUpperCase(MDept)));
		
		return commonDAO.query(Pr.class,Arrays.asList(Order.desc("issuedate")), criterions);
	}
	@Transactional(readOnly = true)
	public List<Pr> getPrList(String prnum,SignStatus status,String lastSigner,
			String siteid,Date transferDate,ActiveFlag transferFlag){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.like("prnum", Utility.toUpperCase(prnum),MatchMode.START));
		criterions.add(RestrictionsUtils.eq("status", ObjectUtils.toString(status)));
		criterions.add(RestrictionsUtils.eq("lastSigner", lastSigner));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		if(transferDate!=null){
			criterions.add(RestrictionsUtils.gt("transferDate", transferDate));
			criterions.add(RestrictionsUtils.lt("transferDate", new Date(transferDate.getTime() + (1000 * 60 * 60 * 24))));
		}
		criterions.add(RestrictionsUtils.eqActiveFlag("transferFlag", transferFlag));
		return commonDAO.query(Pr.class,Arrays.asList(Order.desc("prnum")), criterions);
	}
	@Transactional(readOnly = true)
	public List<Pr> getTransPrList(String num,SignStatus status,String lastSigner,
			String prtype,Date beginDate,Date endDate,String transferFlag){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("lastSigner", lastSigner));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.eq("status", status.toString()));
		if(StringUtils.isNotBlank(transferFlag)){
			if(transferFlag.equals("N")){
				criterions.add(RestrictionsUtils.disjunction(
						Restrictions.isNull("transferFlag"),
						RestrictionsUtils.eq("transferFlag", transferFlag)));
			}else{
				criterions.add(RestrictionsUtils.eq("transferFlag", transferFlag));
			}
		}
		criterions.add(RestrictionsUtils.gt("transferDate", beginDate));
		criterions.add(RestrictionsUtils.lt("transferDate", endDate!=null?
					new Date(endDate.getTime() + (1000 * 60 * 60 * 24)):null));
		
		criterions.add(RestrictionsUtils.exists(Prline.class, "prnum",
				Restrictions.eqProperty("prnum", Pr.class.getSimpleName()+".prnum"),
				RestrictionsUtils.in("lineClosedCode", new String[]{"OPEN","CLOSE"})));		
		
		if(StringUtils.isNotBlank(num)){
			List<Criterion> unionList=new ArrayList<Criterion>();
			unionList.add(RestrictionsUtils.like("prnum", num.toUpperCase(),MatchMode.START));
			unionList.add(RestrictionsUtils.propertyIn(Prline.class, "prnum", "prnum", 
						RestrictionsUtils.like("itemnum", num.toUpperCase(),MatchMode.START)));
			return commonDAO.unionQuery(Pr.class,Arrays.asList(Order.desc("issuedate")), criterions,unionList);
		}
		return commonDAO.query(Pr.class,Arrays.asList(Order.desc("statusdate")), criterions);
	}
}
