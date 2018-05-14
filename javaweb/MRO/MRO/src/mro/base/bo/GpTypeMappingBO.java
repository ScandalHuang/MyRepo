package mro.base.bo;

import java.util.ArrayList;
import java.util.List;

import mro.base.entity.GpTypeMapping;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class GpTypeMappingBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public GpTypeMapping get(String deliveryType,String remainType) {
		List criterions = new ArrayList();
		if(StringUtils.isBlank(deliveryType) && StringUtils.isBlank(remainType) ){
			criterions.add(Restrictions.isNull("deliveryType"));
			criterions.add(Restrictions.isNull("remainType"));
		}else{
			criterions.add(RestrictionsUtils.eq("deliveryType",deliveryType));
			criterions.add(RestrictionsUtils.eq("remainType",remainType));
		}
		return (GpTypeMapping) commonDAO.uniQuery(GpTypeMapping.class,null, criterions);
	}
}