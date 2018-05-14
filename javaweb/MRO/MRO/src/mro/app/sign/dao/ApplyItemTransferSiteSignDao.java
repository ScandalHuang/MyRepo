package mro.app.sign.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.ItemSite;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.LocationMap;
import mro.base.entity.Longdescription;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemTransferSiteSignDao extends FactoryBaseDAO{
	
	public ApplyItemTransferSiteSignDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public List getItemTransferHeaderApplyList(String condition){
		String sql="select * from ITEM_TRANSFER_HEADER_APPLY  " +
				"where  1=1 " +condition + " order by CREATE_DATE desc";
		return queryBySQLWithEntity(sql, ItemTransferHeaderApply.class);
	}
	public ItemTransferHeaderApply getItemTransferHeaderApply(String condition){
		String sql="select * from ITEM_TRANSFER_HEADER_APPLY  " +
				"where  1=1 " +condition;
		return uniQueryBySQLWithEntity(sql, ItemTransferHeaderApply.class);
	}
	public Longdescription getLongdescription(BigDecimal invbalancesid){
		String sql="select * from LONGDESCRIPTION  " +
				"where  LDKEY= " +invbalancesid;
		return uniQueryBySQLWithEntity(sql, Longdescription.class);
	}
	public List getItemTransferLineApplyList(String condition){
		String sql="select * from ITEM_TRANSFER_LINE_APPLY  " +
				"where  1=1 " +condition + " order by LINE_NUM";
		return queryBySQLWithEntity(sql, ItemTransferLineApply.class);
	}
}
