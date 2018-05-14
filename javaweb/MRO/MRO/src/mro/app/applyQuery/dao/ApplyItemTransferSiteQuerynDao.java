package mro.app.applyQuery.dao;

import java.util.List;

import mro.base.entity.ItemTransferLineApply;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemTransferSiteQuerynDao extends FactoryBaseDAO{
	
	public ApplyItemTransferSiteQuerynDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public List getItemTransferLineApplyList(String condition){
		String sql="select * from ITEM_TRANSFER_LINE_APPLY  " +
				"where  1=1 " +condition + " order by LINE_NUM";
		return queryBySQLWithEntity(sql, ItemTransferLineApply.class);
	}
}
