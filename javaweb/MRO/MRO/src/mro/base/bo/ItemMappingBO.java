package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.ItemMapping;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ItemMappingBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public ItemMapping getItemMapping(BigDecimal eaudittransid,String newMatnr) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("eaudittransid", eaudittransid));
		criterions.add(RestrictionsUtils.eq("newMatnr", newMatnr));
		return  (ItemMapping) commonDAO.uniQuery(ItemMapping.class, null, criterions);
	}
	@Transactional(readOnly = true)
	public List<ItemMapping> getList(String newMatnr) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("newMatnr", newMatnr));
		return  commonDAO.query(ItemMapping.class, null, criterions);
	}

}
