package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.LocationMap;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemTransferLineApplyBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<String> getInprgItem(List<String> itemnum,String siteid,List<String> signStausList,String action) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();		
		criterions.add(RestrictionsUtils.in("itemnum", itemnum));
		criterions.add(RestrictionsUtils.exists(ItemTransferHeaderApply.class, "applyHeaderId",
				RestrictionsUtils.eq("action", action),
				RestrictionsUtils.in("status", signStausList),
				Restrictions.eqProperty("applyHeaderId", 
						ItemTransferLineApply.class.getSimpleName()+".itemTransferHeaderApply.applyHeaderId"),
				RestrictionsUtils.exists(LocationMap.class,"locationSiteMap.locationSite", 
					Restrictions.eqProperty("locationSiteMap.locationSite",ItemTransferHeaderApply.class.getSimpleName()+".locationSite"),
					RestrictionsUtils.eq("siteId", siteid))));
		
		projectionList.add(Projections.groupProperty("itemnum"));
		
		return commonDAO.query(ItemTransferLineApply.class, null, criterions,projectionList);
	}
	@Transactional(readOnly = true)
	public List<ItemTransferLineApply> getItemTransferLineApplyList(List<String> itemnum,
			String locationSite,String action,String... status){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("itemTransferHeaderApply.locationSite", locationSite));
		criterions.add(RestrictionsUtils.eq("itemTransferHeaderApply.action", action));
		criterions.add(RestrictionsUtils.in("itemTransferHeaderApply.status", status));
		return commonDAO.query(ItemTransferLineApply.class, Arrays.asList(Order.asc("itemnum")), criterions,
				new CtriteriaFetchMode("itemTransferHeaderApply"));
	}
}
