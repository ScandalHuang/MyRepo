package mro.app.reportView.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.MroPmreqListDao;
import mro.utility.DateUtils;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class MroPmreqListBo {

    private MroPmreqListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new MroPmreqListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String itemnum,
    		String prtype,String prnum,String status,String requestby2,
    		Date cStrDate,Date cEndDate,String deptNo,Date sStrDate,Date sEndDate,
    		String sName) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			if(locationSite.equals("失效區域"))
				condition.append("AND exists (select 1  from location_map lm "
						+ "where  l.ORGANIZATION_CODE=lm.ORGANIZATION_CODE "
						+ "and lm.LOCATION_SITE is null)");
			else 
				condition.append("AND exists (select 1 "
					+ "from location_map lm "
					+ "where  l.ORGANIZATION_CODE=lm.ORGANIZATION_CODE "
					+ "and lm.LOCATION_SITE=:locationSite)");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and l.ORGANIZATION_CODE in (:organizationCode) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND prline.itemnum  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		
		if(StringUtils.isNotBlank(prtype)){
			condition.append("AND pr.prtype = :prtype ");
			param.put("prtype", prtype);
		}
		if(StringUtils.isNotBlank(prnum)){
			condition.append("AND pr.prnum like :prnum ");
			param.put("prnum", prnum+"%");
		}
		if(StringUtils.isNotBlank(status)){
			condition.append("AND pr.status = :status ");
			param.put("status", status);
		}
		if(StringUtils.isNotBlank(requestby2)){
			condition.append("AND pr.REQUESTEDBY2 = :requestby2 ");
			param.put("requestby2", requestby2);
		}

		if(cStrDate!=null){
			condition.append("AND  PR.ISSUEDATE  >= :cStrDate ");
			param.put("cStrDate", cStrDate);
		}
		if(cEndDate!=null){
			condition.append("AND  PR.ISSUEDATE  < :cEndDate ");
			param.put("cEndDate", DateUtils.getAddDate(cEndDate,1));
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND prline.m_dept like :deptNo ");
			param.put("deptNo", deptNo+"%");
		}


		if(sStrDate!=null){
			condition.append("AND  PR.statusdate  >= :sStrDate ");
			param.put("sStrDate", sStrDate);
		}
		if(sEndDate!=null){
			condition.append("AND  PR.statusdate  < :sEndDate ");
			param.put("sEndDate", DateUtils.getAddDate(sEndDate,1));
		}
		if(StringUtils.isNotBlank(sName)){
			condition.append("AND exists (select 1 "
					+ "from MRO_APPLY_SIGN_LIST  "
					+ "where task_id=pr.task_id and status='INPRG'"
					+ "and actor_id ||'('|| actor_name||')' like :sName) ");
			param.put("sName", "%"+sName+"%");
		}
	   return dao.getList(condition.toString(),param);
	}
	
	@Transactional(readOnly=true)
    public Map getOrgs(String locationSite) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			if(locationSite.equals("失效區域")){
				condition.append("and LOCATION_SITE is null and ORGANIZATION_CODE not like 'B%' ");
			}else{
				condition.append("and LOCATION_SITE =:locationSite ");
				param.put("locationSite", locationSite);
			}
		}else return null;
		List<Map> list=dao.getOrgs(param,condition.toString());
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l.get("ORGANIZATION_NAME"), l.get("ORGANIZATION_CODE")));
		return option;
	}

	@Transactional(readOnly=true)
	public Map getStatus() {
		List<Object[]> list=dao.getStatus();
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l[0], l[1]));
		return option;
	}
}
