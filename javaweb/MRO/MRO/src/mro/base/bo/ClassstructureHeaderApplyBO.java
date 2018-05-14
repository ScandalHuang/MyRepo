package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.entity.AItem;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.entity.LocationMap;
import mro.base.entity.Pr;

import org.apache.commons.lang.StringUtils;
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
public class ClassstructureHeaderApplyBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List<Pr> getListByTaskId(List<BigDecimal> taskIds, String prtype,
			List<String> prnums,String requestedby2,String deptcode ){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("taskId", taskIds));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.in("prnum", prnums));
		criterions.add(RestrictionsUtils.eq("requestedby2", requestedby2));
		criterions.add(RestrictionsUtils.eq("deptcode", Utility.toUpperCase(deptcode)));
		
		return commonDAO.query(Pr.class,Arrays.asList(Order.desc("issuedate")), criterions);
	}
	@Transactional(readOnly = true)
	public List<ClassstructureHeaderApply> getApplyList(Set<BigDecimal> taskIds) {
		if(taskIds==null || taskIds.size()==0) return null;
		return this.getApplyList(null,null,new ArrayList(taskIds),null, null, null, null, null, null, null);
	}
	@Transactional(readOnly = true)
	public List<ClassstructureHeaderApply> getApplyList(String createBy,ItemStatusType itemStatusType) {
		return this.getApplyList(null,null,null,null, null, null, createBy, null, null, itemStatusType);
	}
	@Transactional(readOnly = true)
	public List<ClassstructureHeaderApply> getApplyList(String locationSite,String organizationCode,
			List<BigDecimal> taskIds,String num,String status,String action,String createBy,
			Date beginDate,Date endDate,ItemStatusType itemStatusType) {
		List criterions = new ArrayList();
		if(StringUtils.isNotBlank(locationSite)){
			criterions.add(RestrictionsUtils.exists(LocationMap.class, "organizationCode", 
					Restrictions.eqProperty("organizationCode", ClassstructureHeaderApply.class.getSimpleName()+".organizationCode"),
					RestrictionsUtils.eq("locationSiteMap.locationSite", locationSite)));
		}
		criterions.add(RestrictionsUtils.eq("organizationCode", organizationCode));
		criterions.add(RestrictionsUtils.in("taskId", taskIds));
		criterions.add(RestrictionsUtils.eq("status", status));
		criterions.add(RestrictionsUtils.eq("action", action));
		criterions.add(RestrictionsUtils.eq("createBy", createBy));
		
		criterions.add(RestrictionsUtils.gt("createDate", beginDate));
		criterions.add(RestrictionsUtils.lt("createDate", endDate!=null?
					new Date(endDate.getTime() + (1000 * 60 * 60 * 24)):null));
		
		criterions.add(RestrictionsUtils.not(RestrictionsUtils.in("status", 
				itemStatusType!=null?itemStatusType.getValue():null)));
		
		if(StringUtils.isNotBlank(num)){
			List<Criterion> unionList=new ArrayList<Criterion>();
			unionList.add(RestrictionsUtils.like("applyHeaderNum", num.toUpperCase(),MatchMode.START));
			unionList.add(RestrictionsUtils.propertyIn(ClassstructureLineApply.class, 
						"applyHeaderId", "classstructureHeaderApply.applyHeaderId", 
						RestrictionsUtils.disjunction(
						RestrictionsUtils.like("assetattrid", num.toUpperCase(),MatchMode.START),
						RestrictionsUtils.like("classstructureid", num.toUpperCase(),MatchMode.START))));
			return commonDAO.unionQuery(ClassstructureHeaderApply.class,
					Arrays.asList(Order.desc("createDate")), criterions,unionList);
		}
		
		return commonDAO.query(ClassstructureHeaderApply.class, 
				Arrays.asList(Order.desc("createDate")), criterions);
	}
}
