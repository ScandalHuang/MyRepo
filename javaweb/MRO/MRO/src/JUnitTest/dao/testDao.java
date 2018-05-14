package JUnitTest.dao;

import java.util.List;

import mro.base.entity.ItemTransferHeaderApply;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class testDao extends FactoryBaseDAO{
	
	public testDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getList() {
		String sql="SELECT * FROM ITEM_TRANSFER_HEADER_APPLY "
				+ "WHERE apply_header_id between 1986 and 2362 "
				+ "ORDER BY APPLY_HEADER_ID ASC" ;
        return queryBySQLWithEntity(sql,ItemTransferHeaderApply.class);
	}
}
