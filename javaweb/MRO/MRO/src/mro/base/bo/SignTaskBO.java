package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.SignTask;

import org.apache.commons.lang3.StringUtils;
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
public class SignTaskBO {

	private CommonDAO commonDAO;
	
	@Autowired
	private HrDeputyActiveVBO hrDeputyActiveVBO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
	public SignTask getSignTask(BigDecimal taskId){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("taskId", taskId));
		return  (SignTask) commonDAO.uniQuery(SignTask.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public List<BigDecimal> getWaitSignTask(String empno,String applyNum, SignStatus status,
			String sourceSystem, String sourceCategory,boolean deputy) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(RestrictionsUtils.eq("sourceSystem", sourceSystem));
		criterions.add(RestrictionsUtils.eq("sourceCategory", sourceCategory));
		criterions.add(RestrictionsUtils.eq("applyNum", applyNum));
		criterions.add(RestrictionsUtils.eq("status", status.toString()));
		if(StringUtils.isNotBlank(empno)){
			criterions.add(RestrictionsUtils.in("actorId",
				deputy?hrDeputyActiveVBO.getUnionEmp(empno):Arrays.asList(empno)));
		}
		projectionList.add(Projections.distinct(Projections.property("taskId")));
		return  commonDAO.query(SignTask.class, null, criterions,projectionList);
	}

}
