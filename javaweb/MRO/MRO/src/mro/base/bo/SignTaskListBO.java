package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.SignTask;
import mro.base.entity.SignTaskList;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class SignTaskListBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public SignTaskList getNowSignTaskList(BigDecimal taskId) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.propertyEq(SignTask.class,
				"taskListId", "signTaskList.taskListId", Restrictions.eq("taskId", taskId)));
		return (SignTaskList) commonDAO.uniQuery(SignTaskList.class, null,
				criterions);
	}

	public SignTaskList getNextSignTaskList(BigDecimal taskId, BigDecimal seq) {
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("signTask.taskId", taskId));
		criterions.add(Restrictions.eq("signSeqId", seq));
		criterions.add(Restrictions.isNull("signDate"));
		return (SignTaskList) commonDAO.uniQuery(SignTaskList.class, null,
				criterions);
	}
	public BigDecimal getMaxeSeqId(BigDecimal taskId) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(Restrictions.eq("signTask.taskId", taskId));
		projectionList.add(Projections.max("signSeqId"));
		BigDecimal b=(BigDecimal) commonDAO.uniQuery(SignTaskList.class, null, criterions, projectionList);
        return b==null?new BigDecimal("0"):b.add(new BigDecimal("1"));
	}
}
