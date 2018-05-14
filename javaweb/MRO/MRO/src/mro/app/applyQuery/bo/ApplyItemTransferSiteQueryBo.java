package mro.app.applyQuery.bo;

import java.math.BigDecimal;
import java.util.List;

import mro.app.applyQuery.dao.ApplyItemTransferSiteQuerynDao;
import mro.base.entity.ItemTransferLineApply;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ApplyItemTransferSiteQueryBo {

	ApplyItemTransferSiteQuerynDao applyItemTransferSiteQuerynDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		applyItemTransferSiteQuerynDao=new ApplyItemTransferSiteQuerynDao(sessionFactory);
    }
	@Transactional(readOnly = true)
	public List<ItemTransferLineApply> getItemTransferLineApplyList(BigDecimal applyHeaderId){
		StringBuffer str=new StringBuffer();
		if(applyHeaderId!=null){
			str.append(" and APPLY_HEADER_ID='"+applyHeaderId+"'");
		}
		return applyItemTransferSiteQuerynDao.getItemTransferLineApplyList(str.toString());
	}
}
