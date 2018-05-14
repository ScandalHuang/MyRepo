package mro.app.applyItem.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemSecondItemnum;
import mro.base.entity.Itemspec;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemChangeDao extends FactoryBaseDAO{
	
	public ApplyItemChangeDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	public List getBuyerSignList(){
		String sql ="select distinct BUYER_EMPNO from CLASSSTRUCTURE_ITEMCHANGE_SIGN "
				+ "where BUYER_EMPNO is not null";
		return queryBySQL(sql);
	}
	
	public List getItemList(String condition){
		String sql ="select * "+
                "from ITEM where 1=1 "+condition+" order by ITEMID DESC";
		return queryBySQLWithEntity(sql,Item.class);
	}
	public List getAItemListJoinSimple(String condition){
		String sql ="SELECT A.* FROM A_ITEM A LEFT JOIN A_ITEM_SIMPLE B "
				+ "ON A.ITEMNUM=B.APPLY_NUM where 1=1 "+condition+" order by EAUDITTRANSID DESC";
		return queryBySQLWithEntity(sql,AItem.class);
	}
	public List getAItemList(String condition){
		String sql ="select * "+
                "from A_ITEM where 1=1 "+condition+" order by EAUDITTRANSID DESC";
		return queryBySQLWithEntity(sql,AItem.class);
	}
	public List getInvvendorList(String condition){
		String sql ="select * "+
                "from INVVENDOR where 1=1 "+condition+" order by invvendorid";
		return queryBySQLWithEntity(sql,Invvendor.class);
	}
	public List getItemSecondItemnumList(String condition){
		String sql ="select * "+
                "from ITEM_SECOND_ITEMNUM where 1=1 "+condition+" order by SECOND_ITEMNUM";
		return queryBySQLWithEntity(sql,ItemSecondItemnum.class);
	}
	public List getItemspecList(String condition){
		String sql ="select * "+
                "from ITEMSPEC where 1=1 "+condition+" order by DISPLAYSEQUENCE";
		return queryBySQLWithEntity(sql,Itemspec.class);
	}
	
	public Itemspec getItemspec(String condition){
		String sql ="select * "+
                "from ITEMSPEC where 1=1 "+condition;
		return uniQueryBySQLWithEntity(sql,Itemspec.class);
	}
	public ItemAttribute getItemAttribute(String condition) {

		String sql ="select * "+
                    "from ITEM_ATTRIBUTE where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql,ItemAttribute.class);
	}
	public int updateItem(String itemnum,SignStatus status){
		String sql ="update Item set STATUS='"+status+"' where 1=1 and itemnum='"+itemnum+"'";
		return modifyBySQL(sql);
	}
	public BigDecimal getChangeItemCount(BigDecimal itemid,Date createDate){
		String sql ="select count(1) from a_item where eaudittransid = " +
				" (select eaudittransid from item_attribute where itemid=?)"+
				" and EAUDITTIMESTAMP>?";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setBigDecimal(0, itemid);
        query.setTimestamp(1, createDate);
        return (BigDecimal) query.uniqueResult();
	}
	public BigDecimal getSecondItemChangeCount(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		String sql ="select count(1) from ((select second_itemnum from A_ITEM_SECOND_ITEMNUM "
				+ "where EAUDITTRANSID="+preEaudittransid+" minus "
				+ "select second_itemnum from A_ITEM_SECOND_ITEMNUM "
				+ "where EAUDITTRANSID="+afterEaudittransid+") union "
				+ "(select second_itemnum from A_ITEM_SECOND_ITEMNUM "
				+ "where EAUDITTRANSID="+afterEaudittransid+" minus "
				+ "select second_itemnum from A_ITEM_SECOND_ITEMNUM "
				+ "where EAUDITTRANSID="+preEaudittransid+"))";
		return (BigDecimal) uniQueryBySQL(sql);
	}
	public BigDecimal getItemVendorChangeCount(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		String sql ="select count(1) from ((select VENDOR from A_INVVENDOR "
				+ "where EAUDITTRANSID="+preEaudittransid+" minus "
				+ "select VENDOR from A_INVVENDOR "
				+ "where EAUDITTRANSID="+afterEaudittransid+") union "
						+ "(select VENDOR from A_INVVENDOR "
				+ "where EAUDITTRANSID="+afterEaudittransid+" minus "
				+ "select VENDOR from A_INVVENDOR "
				+ "where EAUDITTRANSID="+preEaudittransid+"))";
		return (BigDecimal) uniQueryBySQL(sql);
	}
	public List getItemSpecChange(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		String sql ="select * from (SELECT ASSETATTRID,ALNVALUE,NUMVALUE,measureunitid FROM a_itemspec "
				+ "where EAUDITTRANSID="+afterEaudittransid+" minus "
				+ "SELECT ASSETATTRID,ALNVALUE,NUMVALUE,measureunitid FROM a_itemspec "
				+ "where EAUDITTRANSID="+preEaudittransid+")";
		return queryBySQL(sql);
	}
}
