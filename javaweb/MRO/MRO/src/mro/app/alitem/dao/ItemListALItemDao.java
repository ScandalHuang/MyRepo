package mro.app.alitem.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import mro.app.alitem.vo.ItemListALItemVO;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemListALItemDao extends FactoryBaseDAO{
	
	public ItemListALItemDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getItemAltitemList(String condition) {

	 
	/*	String sql =" select a.rowid||b.rowid as row_key,t.ITEMNUM ,a.description itemnum_description,t.altitemnum,b.description alt_itemnum_description, " +
				    " t.NOTATION,t.ENTERDATE,t.REQUESTEDBY2 "+
				    "  from ALTITEM t left join item a on  t.itemnum=a.itemnum and t.itemsetid=a.itemsetid "+  
                    " left join item b on t.altitemnum=b.itemnum  and t.itemsetid=b.itemsetid "+
			        " where 1=1 "+condition+" order by t.itemnum "; */
		String sql=" select * from (select B.item_Second_Itemnum_Id,a.ITEMNUM ,a.description itemnum_description,b.second_itemnum altitemnum ," +
				"(select description from item where itemnum=B.SECOND_ITEMNUM) alt_itemnum_description " +
				"from item a left join ITEM_SECOND_ITEMNUM b " +
				"on b.itemid=a.itemid where b.SECOND_ITEMNUM is not null) "
				+ "where  1=1 "+condition+"  "+
                      "   order by itemnum" ;
        return queryBySQLWithEntity(sql,ItemListALItemVO.class);
	}
}
