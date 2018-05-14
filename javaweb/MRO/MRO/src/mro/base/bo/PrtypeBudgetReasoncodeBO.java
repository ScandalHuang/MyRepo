package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.PrtypeBudgetReasoncode;

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
import com.inx.commons.util.JsfContextUtil;

@Component
public class PrtypeBudgetReasoncodeBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Map<String,String> getOption(String prtype){
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		criterions.add(Restrictions.eq("prtype", prtype));
		
		projectionList.add(Projections.groupProperty("budgetType"));

		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		Map<String,String> option=new LinkedHashMap<String,String>();
		for(Object s:commonDAO.query(PrtypeBudgetReasoncode.class, 
				Arrays.asList(Order.asc("budgetType")), criterions, projectionList)){
			option.put(bean.getParameterMap().get(s.toString()), s.toString());
		}
		return option;
	}
}
