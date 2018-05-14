package mro.base.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.entity.SubinventoryConfig;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class SubinventoryConfigBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	/**  Map(ori_key+","+ori_location , assign_org)*/
	@Transactional(readOnly = true)
	public Map<String,SubinventoryConfig> getMap() {
		return getList().stream().collect(Collectors.toMap(
				l->l.getOriOrganizationCode()+","+l.getOriLocation(), l->l, (key1, key2) -> key2));
	}
	@Transactional(readOnly = true)
	public List<SubinventoryConfig> getList() {
		List criterions = new ArrayList();
		return commonDAO.query(SubinventoryConfig.class,null, criterions);
	}
}
