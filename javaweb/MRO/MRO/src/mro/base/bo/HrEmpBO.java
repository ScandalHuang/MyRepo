package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.base.entity.HrEmp;
import mro.base.entity.SignTaskList;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
public class HrEmpBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly=true)
	public HrEmp getHrEmp(String empNo){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("empNo", empNo));
		return  (HrEmp) commonDAO.uniQuery(HrEmp.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public HrEmp getMamgerHrEmp(String empNo){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.propertyIn(HrEmp.class, "empNo", "managerId", 
				Restrictions.eq("empNo", empNo)));
		return  (HrEmp) commonDAO.uniQuery(HrEmp.class, null, criterions);
	}
	@Transactional(readOnly=true)
	public HrEmp getActiveHrEmp(String empNo){
		List criterions = new ArrayList();
		Date date=new Date();
		criterions.add(Restrictions.eq("empNo", empNo));
		criterions.add(Restrictions.or(
				Restrictions.ge("leaveDate", date), Restrictions.isNull("leaveDate")));
		return   (HrEmp) commonDAO.uniQuery(HrEmp.class, null, criterions);
	}
	
	@Transactional(readOnly=true)
	public List<HrEmp> getActiveHrEmp(List<String> empNo){
		List criterions = new ArrayList();
		Date date=new Date();
		criterions.add(RestrictionsUtils.in("empNo", empNo));
		criterions.add(Restrictions.or(
				Restrictions.ge("leaveDate", date), Restrictions.isNull("leaveDate")));
		return  commonDAO.query(HrEmp.class, null, criterions);
	}
	
	@Transactional(readOnly=true)
	public List<HrEmp> getHrEmpList(List<String> empNo){
		List criterions = new ArrayList();
		Date date=new Date();
		criterions.add(RestrictionsUtils.in("empNo", empNo));
		return  commonDAO.query(HrEmp.class, null, criterions);
	}
	
	@Transactional(readOnly=true)
	public List<HrEmp> getList(String value) {
		List criterions = new ArrayList();
		criterions.add(Restrictions.disjunction().add(
				RestrictionsUtils.like("empNo", value,MatchMode.START)).add(
				RestrictionsUtils.like("name", value,MatchMode.START)).add(
				RestrictionsUtils.like("deptNo", value.toUpperCase(),MatchMode.START)).add(
				RestrictionsUtils.like("domainAccount", value.toLowerCase(),MatchMode.START)));
		criterions.add(Restrictions.eq("perState", "1"));
		return  commonDAO.query(HrEmp.class, Arrays.asList(Order.asc("empNo")), criterions);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<String> getNotify(BigDecimal taskId) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(RestrictionsUtils.propertyIn(SignTaskList.class, 
				"empNo", "actorId",
				Restrictions.eq("signTask.taskId", taskId),
				Restrictions.ne("action", "SUBMIT")));
		criterions.add(Restrictions.eq("perState", "1"));
		criterions.add(Restrictions.isNotNull("EMail"));
		projectionList.add(Projections.distinct(Projections.property("EMail")));
		return commonDAO.query(HrEmp.class, null, criterions, projectionList);
	}

}
