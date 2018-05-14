package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.SapAccessLog;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class SapAccessLogBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List getList(String material,String plant,String storageLocation,String materialGroup){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("material", material));
		criterions.add(RestrictionsUtils.eq("plant", plant));
		criterions.add(RestrictionsUtils.eq("storageLocation", storageLocation));
		criterions.add(RestrictionsUtils.eq("materialGroup", materialGroup));
		return commonDAO.query(SapAccessLog.class,Arrays.asList(Order.desc("aiId")), criterions);
	}
}
