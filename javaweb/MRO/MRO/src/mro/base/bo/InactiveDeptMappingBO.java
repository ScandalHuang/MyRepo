package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.entity.InactiveDeptMapping;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class InactiveDeptMappingBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List getList() {
		List criterions = new ArrayList();
		return commonDAO.query(InactiveDeptMapping.class,Arrays.asList(Order.asc("oldDeptCode")), criterions);
	}
	@Transactional(readOnly = false)
	public void delete(List<InactiveDeptMapping> list) {
		commonDAO.delete(list);
	}
}
