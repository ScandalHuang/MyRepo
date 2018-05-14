package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.Bsstrkhist;
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
public class BsstrkhistBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Bsstrkhist getBsstrkhist(String itemnum,String dept,String siteid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("MDept", dept));
		criterions.add(RestrictionsUtils.eq("siteid", siteid));
		return (Bsstrkhist) commonDAO.uniQuery(Bsstrkhist.class, null, criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.03.04
	 * controlFlag=true : 半年部門耗用量,半年部門耗用金額 ,近半年部門最大單月耗用數量,近半年部門最大單月耗用金額 
	 * 					     近半年部門平均月耗用數量,近半年部門平均月耗用金額 ,近半年耗用月次數[主]
	 * controlFlag=false : 半年部門耗用量,近半年耗用月次數 ,近半年部門最大單月耗用數量,近半年部門平均月耗用數量 
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Bsstrkhist> validateBsstrkhistByPrline(List<Prline> prlines,boolean costFlag) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("itemnum",prline.getItemnum()),
					Restrictions.eq("MDept",prline.getMDept()),
					Restrictions.eq("siteid",prline.getSiteid()),
						RestrictionsUtils.disjunction(
								Restrictions.ne("hdqty", prline.getHdqty()),
								Restrictions.ne("usecounter", prline.getUsecounter()),
								Restrictions.ne("maxhdqty", prline.getMaxhdqty()),
								Restrictions.ne("avghdqty", prline.getAvghdqty()),
								Restrictions.ne("hdqtyThree", prline.getHdqtyThree()),
								Restrictions.ne("maxhdqtyThree", prline.getMaxhdqtyThree()),
								Restrictions.ne("avghdqtyThree", prline.getAvghdqtyThree()),
								costFlag?Restrictions.ne("hdcost", prline.getHdcost()):null,
								costFlag?Restrictions.ne("maxhdcost", prline.getMaxhdcost()):null,
								costFlag?Restrictions.ne("avghdcost", prline.getAvghdcost()):null,
								costFlag?Restrictions.ne("hdcostThree", prline.getHdcostThree()):null,
								costFlag?Restrictions.ne("maxhdcostThree", prline.getMaxhdcostThree()):null,
								costFlag?Restrictions.ne("avghdcostThree", prline.getAvghdcostThree()):null)));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return  commonDAO.query(Bsstrkhist.class, Arrays.asList(Order.asc("itemnum")), criterions);
	}
}
