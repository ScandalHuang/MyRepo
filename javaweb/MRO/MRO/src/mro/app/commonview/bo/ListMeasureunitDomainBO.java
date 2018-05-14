package mro.app.commonview.bo;

import java.math.BigDecimal;

import mro.app.commonview.dao.ListMeasureunitDomainDAO;
import mro.base.entity.MeasureunitDomain;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListMeasureunitDomainBO {
    
    private ListMeasureunitDomainDAO listMeasureunitDomainDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		listMeasureunitDomainDAO=new ListMeasureunitDomainDAO(sessionFactory);
    }
	
	@Transactional(readOnly=true)
	public BigDecimal getMeasureunitDomainCount(MeasureunitDomain m){
		StringBuffer condition=new StringBuffer();
		condition.append("and domainid = '"+m.getDomainid()+"'");
		condition.append("and description = '"+m.getDescription()+"'");
		if(m.getMeasureunitDomainId()!=null){
			condition.append(" and MEASUREUNIT_DOMAIN_ID!="+m.getMeasureunitDomainId()+" ");
		}

		BigDecimal o =listMeasureunitDomainDAO.getMeasureunitDomainCount(condition.toString());
		return o==null?new BigDecimal("0"):o;
	}
	
	@Transactional(readOnly=false)
	public void updateMeasureunitDomain(MeasureunitDomain a){
		a.setValue(a.getDescription().toUpperCase());
		listMeasureunitDomainDAO.insertUpdate(a);
	}
	
}
