package mro.app.commonview.bo;

import mro.app.commonview.dao.ListPrlineDAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ListPrlineBo {


    private ListPrlineDAO listPrlineDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		listPrlineDAO=new ListPrlineDAO(sessionFactory);
    }
}
