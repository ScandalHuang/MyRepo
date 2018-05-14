package mro.app.sign.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.AItem;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.Itemspec;
import mro.base.entity.LocationMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemChangeSignDao extends FactoryBaseDAO{
	
	public ApplyItemChangeSignDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getAItemList(String condition){
		
		String sql ="select * "+
                "from A_ITEM where 1=1 "+condition+" order by EAUDITTRANSID DESC";

		return queryBySQLWithEntity(sql,AItem.class);
	}
	public Item getItem(int itemid){
			String sql ="select * "+
	                "from ITEM where  itemid="+itemid;
			return uniQueryBySQLWithEntity(sql,Item.class);

	}
	public Itemspec getItemspec(int itemid,String assetattrid){
		String sql ="select * "+
                "from ITEMSPEC where itemid="+itemid+" and ASSETATTRID='"+assetattrid+"'";
		return uniQueryBySQLWithEntity(sql,Itemspec.class);
	}
	public Invvendor getInvvendor(int itemid,String vendor){
		String sql ="select * "+
                "from INVVENDOR where itemid="+itemid+" and vendor='"+vendor+"'";
		return uniQueryBySQLWithEntity(sql,Invvendor.class);
	}
	public ItemAttribute getItemAttribute(int itemid){
		String sql ="select * "+
                "from ITEM_ATTRIBUTE where itemid="+itemid+" ";
		return uniQueryBySQLWithEntity(sql,ItemAttribute.class);
	}
	public int updateInvvendor(int itemid){
		String sql ="update INVVENDOR set DISABLED=1 where  itemid="+itemid;
		return modifyBySQL(sql);
	}
	public void deleteAItemSecondItemnum(BigDecimal itemId){
        Session session = getSession();
        Query query = session.createSQLQuery("delete ITEM_SECOND_ITEMNUM  where itemId=? ");
		    query.setParameter(0, itemId);
		    query.executeUpdate();
	}
	public void deleteItemSpec(BigDecimal itemId){
        Session session = getSession();
        Query query = session.createSQLQuery("delete itemspec  where itemId=? ");
		    query.setParameter(0, itemId);
		    query.executeUpdate();
	}
}
