package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.app.mcMgmtInterface.form.MroOrgFacilityEqForm;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacility;
import mro.base.entity.MroOrgFacilityEq;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroOrgFacilityEqBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<MroOrgFacilityEq> getList(MroOrgFacilityEqForm form){
		return this.getList(form.getsLocationSiteMap(),form.getSelectReason(),form.getSelectDeptNo());
	}
	@Transactional(readOnly = true)
	public List<MroOrgFacilityEq> getList(LocationSiteMap locationSiteMap,String reasoncode,
			String deptNo){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		criterions.add(RestrictionsUtils.eq("reasoncode", reasoncode));
		if(StringUtils.isNotBlank(deptNo)){
			criterions.add(RestrictionsUtils.propertyIn(MroOrgFacility.class, 
					"fab", "fab", RestrictionsUtils.eq("deptNo", deptNo)));
		}
		return commonDAO.query(MroOrgFacilityEq.class,null, criterions);
	}

	@Transactional(readOnly=false)
	public void delete(MroOrgFacilityEq[] list){
		for(MroOrgFacilityEq a:list){
			commonDAO.delete(a);
		}
	}
}
