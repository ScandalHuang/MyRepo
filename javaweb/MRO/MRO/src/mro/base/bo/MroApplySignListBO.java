package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.entity.view.MroApplySignList;

import org.apache.commons.lang3.StringUtils;
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
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroApplySignListBO {
	@Autowired
	private HrDeputyActiveVBO hrDeputyActiveVBO;
	
	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public <T> List<MroApplySignList> getListByEntity(SignStatus status,List<T> list){
		if(!Utility.isNotEmpty(list)) return null;
		List taskIds=list.stream().map(
				t->ReflectUtils.getFieldValue(t, "taskId")).collect(Collectors.toList());
		return this.getList(status, taskIds);
	}
	
	@Transactional(readOnly = true)
	public List<MroApplySignList> getList(SignStatus status,List<BigDecimal> taskIds){
		if(!Utility.isNotEmpty(taskIds)) return null;
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("status", status.toString()));
		criterions.add(RestrictionsUtils.in("taskId", taskIds));
		return commonDAO.query(MroApplySignList.class, Arrays.asList(Order.desc("applyNum")), criterions);
	}
	@Transactional(readOnly=true)
	public List<MroApplySignList> getList(String empno,String applyNum, SignStatus status,
			String sourceCategory,boolean deputy) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("sourceSystem", SystemConfig.SYSTEMNAME));
		criterions.add(RestrictionsUtils.eq("sourceCategory", sourceCategory));
		criterions.add(RestrictionsUtils.eq("applyNum", applyNum));
		criterions.add(RestrictionsUtils.eq("status", status.toString()));
		if(StringUtils.isNotBlank(empno)){
			criterions.add(RestrictionsUtils.in("actorId",
				deputy?hrDeputyActiveVBO.getUnionEmp(empno):Arrays.asList(empno)));
		}
		return  commonDAO.query(MroApplySignList.class, Arrays.asList(Order.desc("applyNum")), criterions);
	}
	
	@Transactional(readOnly = true)
	public Map getSignMap(SignStatus mroStatus, List actorIds) {
		List criterions = new ArrayList();
		Map toDoMap=new HashMap();
		ProjectionList projectionList = Projections.projectionList();

		criterions.add(RestrictionsUtils.eq("status", mroStatus.toString()));
		criterions.add(RestrictionsUtils.in("actorId", actorIds));
		criterions.add(Restrictions.eq("sourceSystem", SystemConfig.SYSTEMNAME));

		projectionList.add(Projections.groupProperty("sourceCategory"));
		projectionList.add(Projections.count("sourceCategory"));
		List<Object[] > list=commonDAO.query(MroApplySignList.class, null, criterions, projectionList);
		for(Object[] o:list){
			toDoMap.put(o[0], ((Long)o[1]).intValue());
		}
		return  toDoMap;
	}
	
	public Map getMap(List<MroApplySignList> list) {
		if(!Utility.isNotEmpty(list)) return new HashMap<BigDecimal,MroApplySignList>();
		Map<BigDecimal,MroApplySignList> map=new HashMap<BigDecimal, MroApplySignList>();
		list.stream().forEach(l->map.put(l.getTaskId(), l));
		return map;
	}

}
