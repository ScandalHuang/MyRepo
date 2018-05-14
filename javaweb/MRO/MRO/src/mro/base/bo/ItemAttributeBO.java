package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.ItemAttribute;
import mro.base.entity.Prline;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemAttributeBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
    public Long getCountBy2nd(String secondSourceItemnum) {
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("secondSourceItemnum", secondSourceItemnum));
		return  commonDAO.queryCount(ItemAttribute.class, criterions);
	}
	
	
//    public List getItemAttribute(String itemnum) {
//		List criterions = new ArrayList();
//		criterions.add(Restrictions.eq("itemnum", itemnum));
//		return  commonDAO.query(ItemAttribute.class, null, criterions);
//	}
	
	/*
	 * hongjie.wu
	 * 2015.03.04
	 * 驗證PRLINE的經濟訂購量,最小包裝數,異動Lead Time是否與設定檔一致
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<ItemAttribute> validateItemAttributeByPrline(List<Prline> prlines) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("item.itemnum",prline.getItemnum()),
						RestrictionsUtils.disjunction(
								Restrictions.ne("mcOrderQuantity", prline.getMcOrderQuantity()),
								Restrictions.ne("mcMinPackageQuantity", prline.getMcMinPackageQuantity())
								//Restrictions.ne("deliverytime", prline.getOrideliverytime())
								)));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return  commonDAO.query(ItemAttribute.class, Arrays.asList(Order.asc("item.itemid")), criterions,
				new CtriteriaFetchMode("item"));
	}
}
