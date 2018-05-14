package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrtypeActiveMember;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class PrtypeActiveMemberBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
    public List<PrtypeActiveMember> getPrtypeActiveMemberList(
    		LocationSiteMap locationSiteMap,String prtype,String employeeNum) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.eq("employeeNum", employeeNum));
		
		return  commonDAO.query(PrtypeActiveMember.class, null , criterions);
	}
	
	@Transactional(readOnly=true)
    public PrtypeActiveMember getPrtypeActiveMember(
    		LocationSiteMap locationSiteMap,String prtype,String employeeNum) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		criterions.add(RestrictionsUtils.eq("employeeNum", employeeNum));
		
		return  (PrtypeActiveMember) commonDAO.uniQuery(PrtypeActiveMember.class, null , criterions);
	}
	@Transactional(readOnly=true)
    public long getCount(LocationSiteMap locationSiteMap,String prtype) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("locationSiteMap", locationSiteMap));
		criterions.add(RestrictionsUtils.eq("prtype", prtype));
		return  commonDAO.queryCount(PrtypeActiveMember.class, criterions);
	}

	@Transactional(readOnly=false)
    public void onDelete(PrtypeActiveMember[] sPrtypeActiveMember) {
		commonDAO.delete(sPrtypeActiveMember);
	}
}
