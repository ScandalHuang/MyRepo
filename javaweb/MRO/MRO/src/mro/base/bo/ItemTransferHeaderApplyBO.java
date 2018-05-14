package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemTransferHeaderApplyBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}	
	@Transactional(readOnly = true)
	public List<ItemTransferHeaderApply> getList(Set<BigDecimal> taskIds){
		if(taskIds ==null || taskIds.size()==0) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("taskId", taskIds));
		return commonDAO.query(ItemTransferHeaderApply.class,
				Arrays.asList(Order.desc("applyHeaderId")), criterions);
	}
	@Transactional(readOnly = true)
	public List<ItemTransferHeaderApply> getList(BigDecimal min,BigDecimal max,String status){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.ge("applyHeaderId", min));
		criterions.add(RestrictionsUtils.le("applyHeaderId", max));
		criterions.add(RestrictionsUtils.eq("status", status));
		return commonDAO.query(ItemTransferHeaderApply.class,
				Arrays.asList(Order.desc("applyHeaderId")), criterions);
	}
	@Transactional(readOnly = true)
	public List<ItemTransferHeaderApply> getItemTransferHeaderApply(
			String num,String status,String action,String createBy,Date beginDate,Date endDate,
			ItemStatusType itemStatusType,String locationSite) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("action", action));
		criterions.add(RestrictionsUtils.eq("createBy", createBy));
		criterions.add(RestrictionsUtils.eq("status", status));
		criterions.add(RestrictionsUtils.eq("locationSite", locationSite));
		
		criterions.add(RestrictionsUtils.gt("createDate", beginDate));
		criterions.add(RestrictionsUtils.lt("createDate", endDate!=null?
					new Date(endDate.getTime() + (1000 * 60 * 60 * 24)):null));
		criterions.add(RestrictionsUtils.not(RestrictionsUtils.in("status", itemStatusType.getValue())));
		
		if(StringUtils.isNotBlank(num)){
			List<Criterion> unionList=new ArrayList<Criterion>();
			unionList.add(RestrictionsUtils.like("applyHeaderNum", num.toUpperCase(),MatchMode.START));
			unionList.add(RestrictionsUtils.propertyIn(ItemTransferLineApply.class, 
						"applyHeaderId", "itemTransferHeaderApply.applyHeaderId", 
						RestrictionsUtils.like("itemnum", num.toUpperCase(),MatchMode.START)));
			return commonDAO.unionQuery(ItemTransferHeaderApply.class,
					Arrays.asList(Order.desc("createDate")), criterions,unionList);
		}
		
		return commonDAO.query(ItemTransferHeaderApply.class, 
				Arrays.asList(Order.desc("createDate")), criterions);
	}
}
