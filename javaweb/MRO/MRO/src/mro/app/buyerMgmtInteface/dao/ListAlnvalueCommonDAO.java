package mro.app.buyerMgmtInteface.dao;

import java.util.List;

import mro.base.entity.AlndomainCommon;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListAlnvalueCommonDAO extends FactoryBaseDAO{
	public ListAlnvalueCommonDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getAlndomainCommonList(String condition){
		String sql="select * from ALNDOMAIN_COMMON where 1=1 "+condition+" order by dump(VALUE,36) desc";
		return queryBySQLWithEntity(sql, AlndomainCommon.class);
	}
	
}
