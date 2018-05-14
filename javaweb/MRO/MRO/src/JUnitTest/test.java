package JUnitTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.basicType.PrType;
import mro.base.bo.PersonBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.Person;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@SuppressWarnings("unused")
@RunWith(SpringWithJNDIRunner.class)
@ContextConfiguration(locations = "classpath:beans-config.xml")
public class test{
	
	private CommonDAO commonDAO;
//	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Autowired
	PersonBO personBO;
	@Autowired
	PrlineBO prlineBO;
	
	@Test
	@Transactional(readOnly=true)
	public void testprlineBo(){
		 prlineBO.getMaxEnterPrline("R15080103UY0", Arrays.asList(
					PrType.R1PMREQ.name(),PrType.R2PMREQ.name()));
	}
//	@Test
	@Transactional(readOnly=true)
	public void test(){
		List criterions = new ArrayList();
		criterions.add(Restrictions.eq("personId", "11097226"));
		 commonDAO.uniQuery(Person.class, null, criterions);
	}
	
//	@Test
	@Transactional(readOnly=true)
	public void test2(){
		 personBO.getPersonList("11097226", true);
	}
}
