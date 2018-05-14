package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.SignTask;
import mro.base.entity.view.HrDeputyActiveV;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroSignTaskListVBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	public List<MroSignTaskListV> getListBySource(List<MroSignTaskListV> source,String lastSignPhase) {
		List<MroSignTaskListV> target=new ArrayList<MroSignTaskListV>();
		for(MroSignTaskListV m:source){
			if(Utility.equals(m.getLastSignPhase(), lastSignPhase)){
				target.add(m);
			}
		}
		return target;
	}
	@Transactional(readOnly = true)
	public List<MroSignTaskListV> getListByTaskId(BigDecimal taskId,String lastSignPhase) {
		return this.getListByTaskId(Arrays.asList(taskId), lastSignPhase);
	}
	@Transactional(readOnly = true)
	public List<MroSignTaskListV> getListByTaskId(List<BigDecimal> taskId,String lastSignPhase) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.in("taskId", taskId));
		criterions.add(RestrictionsUtils.eq("lastSignPhase", lastSignPhase));
		return commonDAO.query(MroSignTaskListV.class, Arrays.asList(Order.asc("signSeqId")), criterions);
	}
	
	@Transactional(readOnly = true)
	public List<MroSignTaskListV> getList(String createBy, String sourceCategory,SignStatus status) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.propertyIn(SignTask.class, "taskId", "taskId", 
				RestrictionsUtils.eq("createBy", createBy),
				RestrictionsUtils.eq("sourceCategory", sourceCategory),
				RestrictionsUtils.eq("status", status.toString())));
		criterions.add(RestrictionsUtils.eq("lastSignPhase", "Y"));
		return commonDAO.query(MroSignTaskListV.class, null, criterions);
	}
	
	@Transactional(readOnly = true)
	public int getNowListCount(BigDecimal taskId,String empNo) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(RestrictionsUtils.eq("taskId", taskId));
		criterions.add(RestrictionsUtils.disjunction(
				RestrictionsUtils.eq("actorId", empNo),
				RestrictionsUtils.propertyIn(HrDeputyActiveV.class, "actorId", "empNo",
						RestrictionsUtils.eq("deputyNo", empNo))));
		criterions.add(RestrictionsUtils.eq("lastSignPhase", "Y"));
		projectionList.add(Projections.rowCount());
		return ((Number) commonDAO.uniQuery(
				MroSignTaskListV.class, null, criterions, projectionList)).intValue();
	}
	
	public Map getListMap(List<MroSignTaskListV> list) {
		Map map=new HashedMap();
		for(MroSignTaskListV m:list){
			map.put(m.getTaskId(), m);
		}
		return map;
	}
}
