package mro.app.reportView.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ItemTransListDao;
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
public class ItemTransListBo {

    private ItemTransListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new ItemTransListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String locationSite,String[] organizationCode,String itemnum,
    		String deptNo,String sourceType,String refno,
    		Date strDate,Date endDate) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			if(locationSite.equals("失效區域"))
				condition.append("and nvl(sc.location_site,lm.location_site) is null ");
			else
			condition.append("AND nvl(sc.location_site,lm.location_site) = :locationSite ");
			param.put("locationSite", locationSite);
		}
		
		if(Utility.isNotEmpty(organizationCode)){
			condition.append("and lm.organization_code in (:organizationCode) ");
			param.put("organizationCode", organizationCode);
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND a.ITEMnum  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND a.BINNUM in (SELECT DEPT_NO FROM hr_org  "
					+ "START WITH DEPT_NO=:deptNo  CONNECT BY PRIOR DEPT_NO=TOP_DEPT) ");
			param.put("deptNo", deptNo);
		}
		if(StringUtils.isNotBlank(sourceType)){
			condition.append("AND a.SOURCE_TYPE  = :sourceType ");
			param.put("sourceType", sourceType);
		}
		if(StringUtils.isNotBlank(refno)){
			condition.append("AND a.REFWO  = :refno ");
			param.put("refno", refno);
		}if(strDate!=null){
			condition.append("and a.ACTUALDATE >= :strDate ");
			param.put("strDate", strDate);
		}
		if(endDate!=null){
			condition.append("and a.ACTUALDATE < :endDate ");
			param.put("endDate", DateUtils.getAddDate(endDate, 1));
		}
		
	   return dao.getList(condition.toString(),param);
	}
	
	@Transactional(readOnly=true)
    public Map getOrgs(String locationSite) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(locationSite)){
			if(locationSite.equals("失效區域")){
				condition.append("and LOCATION_SITE is null ");
				condition.append("and organization_code not in ('B002','B009','B010') ");
			}else{
				condition.append("and LOCATION_SITE =:locationSite ");
				param.put("locationSite", locationSite);
			}
		}else{
			return null;
		}
		List<Map> list=dao.getOrgs(param,condition.toString());
		Map option=new LinkedHashMap();
		list.stream().forEach(l->option.put(l.get("ORGANIZATION_NAME"), l.get("ORGANIZATION_CODE")));
		return option;
	}
}
