package mro.app.commonview.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.Classstructure;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.LocationSiteMap;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemCommonDAO extends FactoryBaseDAO{
	
	public ListItemCommonDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getItemList(String condition){
		String sql ="select ITEM.* "+
                "from ITEM " +
                "where 1=1 "+condition+" order by itemnum ";
		return queryBySQLWithEntity(sql,Item.class);
	}
	public Item getItem(String itemnum){
		String sql ="select * from item   " +
                "where 1=1 and ITEMNUM='"+itemnum+"' order by itemnum";
		return uniQueryBySQLWithEntity(sql,Item.class);
	}
	public Item getItem(BigDecimal itemid){
		String sql ="select * "+
                "from ITEM where 1=1 and itemid='"+itemid+"'";
		return uniQueryBySQLWithEntity(sql,Item.class);
	}
	public ItemAttribute getItemAttribute(String condition) {

		String sql ="select * "+
                    "from ITEM_ATTRIBUTE where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql,ItemAttribute.class);
	}
	public List getClassstructure(String condition){
		String sql ="SELECT * FROM CLASSSTRUCTURE " +
				"where haschildren=0  " + condition;
		return queryBySQLWithEntity(sql,Classstructure.class);
	}
	public List<ItemTransferLineApply> getItemTransferLineApplyJoinHeaderList(String condition){
		String sql ="SELECT A.* FROM ITEM_TRANSFER_LINE_APPLY A "
				+ "LEFT JOIN ITEM_TRANSFER_HEADER_APPLY B "
				+ "ON A.APPLY_HEADER_ID=B.APPLY_HEADER_ID WHERE 1=1 "+condition;
		return queryBySQLWithEntity(sql,ItemTransferLineApply.class);
	}
	public List getItemMappingList(String condition) {

		String sql ="SELECT  OLD_MATNR from ITEM_MAPPING  where 1=1 " +condition+
				"order by NEW_MATNR,OLD_MATNR desc  ";
        return queryBySQL(sql);
	}
	public List<LocationSiteMap> getLocationSiteMapList(String condition){
		String sql ="select * from LOCATION_SITE_MAP where  1=1  "+condition +" ORDER BY LOCATION_SITE";
		return queryBySQLWithEntity(sql,LocationSiteMap.class);
	}
}
