package mro.app.applyQuery.dao;

import java.util.List;

import mro.base.entity.AItem;
import mro.base.entity.AItemspec;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemChangeQueryDao extends FactoryBaseDAO{
	
	public ApplyItemChangeQueryDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getAItemList(String condition){
		
		String sql ="select a.* "+
                "from A_ITEM a left join person b on a.changeby=b.person_id " +
                "where 1=1 "+condition+" order by a.EAUDITTRANSID DESC";

		return queryBySQLWithEntity(sql,AItem.class);
	}
	public AItemspec getAItemspec(String condition) {

		String sql ="select * "+
                    "from  a_itemspec where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql, AItemspec.class);
	}
}
