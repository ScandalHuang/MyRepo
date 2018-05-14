package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.HrOrgListDao;

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
public class HrOrgListBo {

    private HrOrgListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new HrOrgListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String dept,String manager,String employee,String ext) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(dept)){
			String deptT=dept+"%";
			condition.append("AND DEPT_NO IN ("
					+ "select DEPT_NO from hr_org where dept_no like :deptT "
					+ "UNION select DEPT_NO from hr_org WHERE short_name like :deptT) ");
			param.put("deptT", deptT);
		}
		if(StringUtils.isNotBlank(manager)){
			String managerT=manager+"%";
			condition.append("AND manager_id  IN ("
					+ "select emp_no from hr_emp where emp_no like :managerT "
					+ "UNION select emp_no from hr_emp WHERE name like :managerT) ");
			param.put("managerT", managerT);
		}
		if(StringUtils.isNotBlank(employee)){
			String employeeT=employee+"%";
			condition.append("AND dept_no  IN ("
					+ "select dept_no  from hr_emp where emp_no like :employeeT "
					+ "UNION select dept_no from hr_emp WHERE name like :employeeT) ");
			param.put("employeeT", employeeT);
		}
		if(StringUtils.isNotBlank(ext)){
			condition.append("AND dept_no IN ("
					+ "select dept_no from hr_emp where REGEXP_LIKE( EXT_NO,:ext ) ) ");
			param.put("ext", ext);
		}
		
	   return dao.getList(condition.toString(),param);
	}
}
