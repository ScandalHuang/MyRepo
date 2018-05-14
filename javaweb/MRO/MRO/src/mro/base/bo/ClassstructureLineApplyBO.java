package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.ClassstructureLineApply;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
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
public class ClassstructureLineApplyBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<ClassstructureLineApply> getList(BigDecimal applyHeaderId) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureHeaderApply.applyHeaderId", applyHeaderId));
		
		return commonDAO.query(ClassstructureLineApply.class, 
				Arrays.asList(Order.asc("lineNum")), criterions,
				new CtriteriaFetchMode("alndomain", Criteria.LEFT_JOIN));
	}
	/*
	 * hongjie.wu
	 * 2015.05.22
	 * 驗證domainid & description有無簽核中申請單
	 * return 簽核中的LIST
	 */
	@Transactional(readOnly = true)
	public List<ClassstructureLineApply> getValidateList(BigDecimal applyHeaderId,
			SignStatus signStatus,List<ClassstructureLineApply> validateList) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		criterions.add(RestrictionsUtils.ne("classstructureHeaderApply.applyHeaderId", applyHeaderId));
		criterions.add(RestrictionsUtils.eq("classstructureHeaderApply.status", signStatus.toString()));
		for(ClassstructureLineApply c:validateList){
			if(StringUtils.isNotBlank(c.getDescription())){
				criterionL.add(RestrictionsUtils.conjunction(
						Restrictions.eq("domainid",c.getDomainid()),
						Restrictions.ilike("description",c.getDescription())));
			}
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		return commonDAO.query(ClassstructureLineApply.class, 
				Arrays.asList(Order.asc("lineNum")), criterions,
				Arrays.asList(new CtriteriaFetchMode("classstructureHeaderApply")));
	}
}
