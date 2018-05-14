package JUnitTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.app.applyItem.Utils.ApplyItemTransferSiteUtils;
import mro.app.applyItem.service.ItemTransferSiteInterface;
import mro.app.applyItem.service.impl.ItemTransferSiteImpl;
import mro.app.applyQuery.bo.ApplyItemTransferSiteQueryBo;
import mro.base.System.config.basicType.PrType;
import mro.base.bo.PersonBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.Person;
import mro.form.ItemTransferSiteForm;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import JUnitTest.dao.testDao;

import com.inx.commons.dao.CommonDAO;

@SuppressWarnings("unused")
@RunWith(SpringWithJNDIRunner.class)
@ContextConfiguration(locations = "classpath:beans-config.xml")
public class ApplyItemTransferSiteTest{
	
	private CommonDAO commonDAO;
	private testDao testDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
		testDao = new testDao(sessionFactory);
	}
	@Autowired
	ApplyItemTransferSiteQueryBo applyItemTransferSiteQueryBo;
	
	@Test
	@Transactional(readOnly=true)
	public void submit(){
		List<ItemTransferHeaderApply> listHeader=testDao.getList();
		ItemTransferSiteInterface itemTransferSiteInterface = new ItemTransferSiteImpl();
		for(ItemTransferHeaderApply header:listHeader){
			ItemTransferSiteForm form=new ItemTransferSiteForm();
			form=itemTransferSiteInterface.selectApply(form,header);
			String warnMessage=ApplyItemTransferSiteUtils.vaildate(form);
			System.out.println(header.getApplyHeaderNum());
			System.out.println(warnMessage);
		}
	}
	
	public static void main(String[] args){
	
		System.out.println((int) Double.parseDouble("10"));
	}
}
