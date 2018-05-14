package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.Bulletinboard;
import mro.base.entity.BulletinboardSite;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class BulletinboardSiteBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List<BulletinboardSite> getList(Bulletinboard bulletinboard){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("bulletinboard", bulletinboard));	
		return commonDAO.query(BulletinboardSite.class,null, criterions, 
				new CtriteriaFetchMode("locationSiteMap",Criteria.LEFT_JOIN));
	}
}
