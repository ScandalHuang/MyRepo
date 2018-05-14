package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.view.HrDeputyActiveV;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class HrDeputyActiveVBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	//=================代理人+登錄者==================
	@Transactional(readOnly=true)
	public List getUnionEmp(String deputyNo){
		List empNos=this.getEmpList(deputyNo);
		empNos.add(deputyNo);
		return empNos;
	}
	
	@Transactional(readOnly=true)
	public List getEmpList(String deputyNo){
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(Restrictions.eq("deputyNo", deputyNo));
		projectionList.add(Projections.groupProperty("empNo"));
		return commonDAO.query(HrDeputyActiveV.class, null, criterions, projectionList);
	}
	@Transactional(readOnly=true)
	public HrDeputyActiveV getHrDeputyActiveV(String empNo){
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(Restrictions.eq("empNo", empNo));
		return (HrDeputyActiveV) commonDAO.uniQuery(HrDeputyActiveV.class, null, criterions);
	}
}
