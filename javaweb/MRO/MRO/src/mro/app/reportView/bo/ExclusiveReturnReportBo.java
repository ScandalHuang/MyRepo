package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.ExclusiveReturnReportDao;
import mro.app.reportView.form.ExclusiveReturnReportForm;
import mro.utility.DateUtils;

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
public class ExclusiveReturnReportBo {

    private ExclusiveReturnReportDao exclusiveReturnReportDao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	exclusiveReturnReportDao=new ExclusiveReturnReportDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getExclusiveReturnList(ExclusiveReturnReportForm form) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(form.getSlocationSite())){
			if(form.getSlocationSite().equals("失效區域"))
				condition.append("and exists (select 1 from location_map where Location_site is null "
						+ "and Organization_Code=MRO_RETURN_DETAIL_V1.Organization_Code)" );
			else
			condition.append("and exists (select 1 from location_map where Location_site =:locationSite "
					+ "and Organization_Code=MRO_RETURN_DETAIL_V1.Organization_Code)" );
			param.put("locationSite", form.getSlocationSite());
		}
//		if(StringUtils.isNotBlank(form.getOrganizationCode())){
//			condition.append("and  Organization_Code =:organizationCode ");
//			param.put("organizationCode", form.getOrganizationCode());
//		}
		
		if(StringUtils.isNotBlank(form.getDeptNo())){
			condition.append("and  BINNUM IN ("
					+ "SELECT DEPT_NO  FROM hr_org WHERE DEPT_LEVEL IS NOT NULL "
					+ "START WITH DEPT_NO=:deptNo CONNECT BY PRIOR DEPT_NO=TOP_DEPT) ");
			param.put("deptNo", form.getDeptNo().toUpperCase()+"");
		}
		if(StringUtils.isNotBlank(form.getItemnum())){
			condition.append("and  itemnum in ("
					+ "select :itemnum from dual  "
					+ "union select new_matnr from item_mapping where old_matnr=:itemnum "
					+ "and new_matnr is not null and OLD_MATNR is not null "
					+ "union "
					+ "select old_matnr from item_mapping where new_matnr=:itemnum "
					+ "and new_matnr is not null and OLD_MATNR is not null) ");
			param.put("itemnum", form.getItemnum().toUpperCase());
		}
		
		if(form.getStDate()!=null){
			condition.append("and trunc(ACTUALDATE) >= :stDate ");
			param.put("stDate", DateUtils.getOnlyDate(form.getStDate()));
		}
		if(form.getIssueEdDate()!=null ||form.getReturnEdDate()!=null){
			condition.append("and ( ");
			if(form.getIssueEdDate()!=null){
				condition.append(" (trunc(ACTUALDATE) < :issueEdDate and type='ISSUE') ");
				param.put("issueEdDate", DateUtils.getAddDate(
						DateUtils.getOnlyDate(form.getIssueEdDate()), 1));
			}		
			if(form.getReturnEdDate()!=null){
				condition.append("or (trunc(ACTUALDATE) < :returnEdDate and type='RETURN') ");
				param.put("returnEdDate", DateUtils.getAddDate(
						DateUtils.getOnlyDate(form.getReturnEdDate()), 1));
			}
			condition.append(" ) ");
		}	
	   return exclusiveReturnReportDao.getExclusiveReturnList(condition.toString(),param);
	}
	@Transactional(readOnly=true)
    public List getExclusiveReturnMDList(ExclusiveReturnReportForm form) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();

		
		if(StringUtils.isNotBlank(form.getSlocationSite2())){
			if(form.getSlocationSite().equals("失效區域"))
			condition.append("and exists (select 1 from location_map where Location_site is null "
					+ "and Organization_Code=MRO_RETURN_DETAIL_V1.Organization_Code)" );
		else
			condition.append("and exists (select 1 from location_map where Location_site =:locationSite2 "
					+ "and Organization_Code=MRO_RETURN_DETAIL_V1.Organization_Code)" );
			param.put("locationSite2", form.getSlocationSite2());
		}
		
//		if(StringUtils.isNotBlank(form.getOrganizationCode2())){
//			condition.append("and  Organization_Code =:organizationCode2 ");
//			param.put("organizationCode2", form.getOrganizationCode2());
//		}
		
		if(form.getStDate2()!=null){
			condition.append("and trunc(ACTUALDATE) >= :stDate2 ");
			param.put("stDate2", DateUtils.getOnlyDate(form.getStDate2()));
		}
		if(form.getIssueEdDate2()!=null ||form.getReturnEdDate2()!=null){
			condition.append("and ( ");
			if(form.getIssueEdDate2()!=null){
				condition.append(" (trunc(ACTUALDATE) < :issueEdDate2 and type='ISSUE') ");
				param.put("issueEdDate2", DateUtils.getAddDate(
						DateUtils.getOnlyDate(form.getIssueEdDate2()), 1));
			}		
			if(form.getReturnEdDate2()!=null){
				condition.append("or (trunc(ACTUALDATE) < :returnEdDate2 and type='RETURN') ");
				param.put("returnEdDate2", DateUtils.getAddDate(
						DateUtils.getOnlyDate(form.getReturnEdDate2()), 1));
			}
			condition.append(" ) ");
		}	
		

		if(StringUtils.isNotBlank(form.getDeptNo2())){
			condition.append("and  BINNUM IN ("
					+ "SELECT DEPT_NO  FROM hr_org WHERE DEPT_LEVEL IS NOT NULL "
					+ "START WITH DEPT_NO=:getDeptNo2 CONNECT BY PRIOR DEPT_NO=TOP_DEPT) ");
			param.put("getDeptNo2", form.getDeptNo2().toUpperCase()+"");
		}
	   return exclusiveReturnReportDao.getExclusiveReturnMDList(
			   condition.toString(),param);
	}
}
