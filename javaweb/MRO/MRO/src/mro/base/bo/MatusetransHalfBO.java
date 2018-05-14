package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.MatusetransHalf;
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
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MatusetransHalfBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public MatusetransHalf getMatusetransHalf(String itemnum,String storeloc,String binnum,String siteid) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("storeloc", storeloc));
		criterions.add(RestrictionsUtils.eq("MBinnum", binnum));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		
		return  (MatusetransHalf) commonDAO.uniQuery(MatusetransHalf.class, null, criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.05.18
	 * 驗證領用資訊
	 * controlFlag=true : 近半年部門最大單月領用數量,近半年部門平均月領用數量 ,近半年領用月次數[主]
	 * controlFlag=false : 近半年領用月次數[主]
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<MatusetransHalf> validateByPrline(List<Prline> prlines,boolean controlFlag) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("itemnum",prline.getItemnum()),
					Restrictions.eq("MBinnum",prline.getMDept()),
					Restrictions.eq("siteid",prline.getSiteid()),
					Restrictions.eq("storeloc",prline.getStoreroom()),
						RestrictionsUtils.disjunction(
								Restrictions.ne("halfyearIssueCounter", prline.getHalfyearIssueCounter()),
								controlFlag?Restrictions.ne("maxhdqty", prline.getMaxhdqty()):null,
								controlFlag?Restrictions.ne("avghdqty", prline.getAvghdqty()):null,
								controlFlag?Restrictions.ne("maxhdqtyThree", prline.getMaxhdqtyThree()):null,
								controlFlag?Restrictions.ne("avghdqtyThree", prline.getAvghdqtyThree()):null)));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return  commonDAO.query(MatusetransHalf.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
}