package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.Invvendor;
import mro.base.entity.VwNewvendorcodeEpmall;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class VwNewvendorcodeEpmallBO {
	
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
    public VwNewvendorcodeEpmall getListByNvcId(Object nvcid) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("nvcid", nvcid));
		
    	return (VwNewvendorcodeEpmall) commonDAO.uniQuery(VwNewvendorcodeEpmall.class, null,criterions);
    }
	
	@Transactional(readOnly=true)
    public VwNewvendorcodeEpmall getListByOracleID(Object oracleVendorId) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.eq("oracleVendorId", oracleVendorId));
		
    	return (VwNewvendorcodeEpmall) commonDAO.uniQuery(VwNewvendorcodeEpmall.class, null,criterions);
    }
	
	@Transactional(readOnly=true)
    public List<VwNewvendorcodeEpmall> getList(String vatRegistration,String name) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.like("registrationnum", vatRegistration,MatchMode.START));
		criterions.add(RestrictionsUtils.like("newvendorname", name,MatchMode.START));
		
    	return commonDAO.query(VwNewvendorcodeEpmall.class, null,criterions);
    }
	
	@Transactional(readOnly=true)
    public List<VwNewvendorcodeEpmall> getListByItemnum(String itemnum) {
		List criterions=new ArrayList();
		criterions.add(RestrictionsUtils.propertyIn(Invvendor.class,"nvcid","vendor", 
				RestrictionsUtils.eq("itemnum", itemnum)));
		
    	return commonDAO.query(VwNewvendorcodeEpmall.class, null,criterions);
    }
	
}
