package mro.app.reportView.bo;

import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.IpmBuyerPreductReportDao;
import mro.app.reportView.form.IpmBuyerPreductReportForm;
import mro.base.System.config.basicType.ItemStatusType;
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
public class IpmBuyerPreductReportBo {

    private IpmBuyerPreductReportDao ipmBuyerPreductReportDao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	ipmBuyerPreductReportDao=new IpmBuyerPreductReportDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(IpmBuyerPreductReportForm form) {
		StringBuffer prC=new StringBuffer();
		StringBuffer poC=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(form.getItemnum())){
			prC.append("and  p.PART_NO in ("
					+ "select :itemnum from dual  "
					+ "union select new_matnr from item_mapping where old_matnr=:itemnum "
					+ "and new_matnr is not null and OLD_MATNR is not null "
					+ "union "
					+ "select old_matnr from item_mapping where new_matnr=:itemnum "
					+ "and new_matnr is not null and OLD_MATNR is not null) ");
			param.put("itemnum", form.getItemnum().toUpperCase());
		}
		
		if(StringUtils.isNotBlank(form.getClassstructureid())){
			prC.append("AND EXISTS (select itemnum from item "
					+ "where CLASSSTRUCTUREID like :classstructureid AND ITEMNUM=p.PART_NO ) ");
			param.put("classstructureid", form.getClassstructureid()+"%");
		}
		
		if(Utility.isNotEmpty(form.getOrganizationCode())){
			prC.append("and  p.SITEID in :organizationCode ");
			param.put("organizationCode", form.getOrganizationCode());
		}
		
		if(Utility.isNotEmpty(form.getLocationSite())){
			prC.append("and exists (SELECT 1 FROM LOCATION_MAP "
					+ "WHERE SITE_ID=p.SITEID and location_site in :locationSite) ");
			param.put("locationSite", form.getLocationSite());
		}
		
		if(form.getStDate()!=null){
			//TN20160900089 
//			prC.append("and trunc(PM_DATE) >= :stDate ");
//			poC.append("and trunc(PO_APPROVED_DATE) >= :stDate ");
			//TN20161200075 
			prC.append("and trunc(SUBMIT_DATE) >= :stDate ");
			param.put("stDate", DateUtils.getOnlyDate(form.getStDate()));
		}
		if(form.getEdDate()!=null){
			//TN20160900089 
//			prC.append("and trunc(PM_DATE) < :edDate ");
//			poC.append("and trunc(PO_APPROVED_DATE) < :edDate ");
			//TN20161200075 
			prC.append("and trunc(SUBMIT_DATE) < :edDate ");
			param.put("edDate", DateUtils.getOnlyDate(form.getEdDate()));
		}
	   return ipmBuyerPreductReportDao.getList(prC.toString(), poC.toString(), param);
	}
}
