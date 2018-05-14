package mro.app.locationMgmt.bo;

import java.util.Date;

import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
@Scope("prototype")
public class ListLocationMgmtBO {

	private CommonDAO commonDAO;
	
	@Autowired
	@Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
		commonDAO = new CommonDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void updateLocationMap(LocationMap locationMap,String empNo) {
		locationMap.setCreateBy(empNo);
		locationMap.setCreateDate(new Date(System.currentTimeMillis()));
		commonDAO.insertUpdate(locationMap); 
	}
	@Transactional(readOnly=false)
	public void updateLocationSiteMap(LocationSiteMap locationSiteMap,String empNo) {
		locationSiteMap.setLastUpdatedBy(empNo);
		locationSiteMap.setLastUpdateDate(new Date(System.currentTimeMillis()));
		commonDAO.insertUpdate(locationSiteMap); 
	}
}
