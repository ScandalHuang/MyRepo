package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.PrtypeBudgetListDao;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class PrtypeBudgetListBo {

    private PrtypeBudgetListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new PrtypeBudgetListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String deptNo,String budgetType ,String budgetMonth) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND DEPT_NO IN (SELECT DEPT_NO  FROM hr_org  "
					+ "WHERE  DEPT_LEVEL IS NOT NULL START WITH DEPT_NO =:deptNo "
					+ "CONNECT  BY PRIOR TOP_DEPT = DEPT_NO)");
			param.put("deptNo", deptNo);
		}
		if(StringUtils.isNotBlank(budgetType)){
			condition.append("AND budget_Type  = :budgetType ");
			param.put("budgetType", budgetType);
		}
		if(StringUtils.isNotBlank(budgetMonth)){
			condition.append("AND budget_Month  = :budgetMonth ");
			param.put("budgetMonth", budgetMonth);
		}
	   return dao.getList(condition.toString(),param);
	}
}
