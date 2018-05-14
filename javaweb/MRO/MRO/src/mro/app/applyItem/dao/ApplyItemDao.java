package mro.app.applyItem.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.base.entity.AItem;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.ClassstructureItemSign;
import mro.base.entity.ClassstructureItemchangeSign;
import mro.base.entity.Item;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemDao extends FactoryBaseDAO{
	
	public ApplyItemDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//=====2013/6/21料號產生使用=====
	public void updateErrorCode(BigDecimal EAUDITTRANSID,String errorCode){
        Session session = getSession();
        Query query = session.createSQLQuery("update A_ITEM_TEMP_ERROR_CODE " +
        		"set ERROR_CODE='"+errorCode+"'  where EAUDITTRANSID=? ");
		    query.setParameter(0, EAUDITTRANSID);
		    query.executeUpdate();
	}
	
	public void deleteAInvvedor(BigDecimal EAUDITTRANSID){
        Session session = getSession();
        Query query = session.createSQLQuery("delete A_INVVENDOR  where EAUDITTRANSID=? ");
		    query.setParameter(0, EAUDITTRANSID);
		    query.executeUpdate();
	}
	public void deleteAItemSecondItemnum(BigDecimal EAUDITTRANSID){
        Session session = getSession();
        Query query = session.createSQLQuery("delete A_ITEM_SECOND_ITEMNUM  where EAUDITTRANSID=? ");
		    query.setParameter(0, EAUDITTRANSID);
		    query.executeUpdate();
	}
	public void deleteAItemspec(String ITEMNUM){
        Session session = getSession();
        Query query = session.createSQLQuery("delete A_Itemspec  where ITEMNUM=? ");
		    query.setParameter(0, ITEMNUM);
		    query.executeUpdate();
	}
	public void deleteAItemAttribute(String eaudittransid){
        Session session = getSession();
        Query query = session.createSQLQuery("delete A_ITEM_ATTRIBUTE  where EAUDITTRANSID=? ");
		    query.setParameter(0, eaudittransid);
		    query.executeUpdate();
	}
	public List getAItemList(String condition){
		
		String sql ="select * "+
                "from A_ITEM where 1=1 "+condition+" order by EAUDITTIMESTAMP DESC";

		return queryBySQLWithEntity(sql,AItem.class);
	}
	public AItem getAItem(String condition){
		
		String sql ="select * "+
                "from A_ITEM where 1=1 "+condition+" order by EAUDITTIMESTAMP DESC";

		return uniQueryBySQLWithEntity(sql,AItem.class);
	}
	public List getItemList(String condition,Map param){
		String sql ="select * "+
                "from ITEM where 1=1 "+condition;
        return queryBySQLWithEntity(sql, Item.class, param);
	}
	public BigDecimal getItemCount(String condition){
		String sql ="select count(1) "+
                "from ITEM where 1=1 "+condition;
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        return (BigDecimal) query.uniqueResult();
	}
	public List getAItemList(String condition,Map param){
		String sql ="select * "+
                "from A_ITEM where 1=1 "+condition;
        return queryBySQLWithEntity(sql, AItem.class, param);
	}

	public AItemspec getAItemspec(String condition) {

		String sql ="select * "+
                    "from A_ITEMSPEC where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql,AItemspec.class);
	}
	public AItemAttribute getAItemAttribute(String condition) {

		String sql ="select * "+
                    "from A_ITEM_ATTRIBUTE where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql,AItemAttribute.class);
	}
	public List getListAItemspecVOList(String condition,String preCondition) {

		String sql ="select a.*,b.description,b.DECIMALPLACES"+preCondition+
                    "from A_ITEMSPEC a " +
                    "left join assetattribute b on  a.assetattrid = b.assetattrid " +
                    "where 1=1 "+condition+" order by a.DISPLAYSEQUENCE ";

        return queryBySQLWithEntity(sql,ListAItemspecVO.class);
	}
	public ListAItemspecVO getListAItemspecVO(String condition,String eaudittransid) {

		String sql ="select a.*,b.datatype,b.description,b.DECIMALPLACES,c.itemrequirevalue "+
                    "from (select * from a_itemspec where eaudittransid='"+eaudittransid+"')  a " +
                    "left join assetattribute b on  a.assetattrid = b.assetattrid " +
                    "left join CLASSSPEC c on  a.assetattrid = c.assetattrid " +
                    "and a.CLASSSTRUCTUREID=c.CLASSSTRUCTUREID " +
                    "where 1=1 "+condition+"   ";

        return uniQueryBySQLWithEntity(sql,ListAItemspecVO.class);
	}
	public List getListAInvvendorVO(String condition) {

		String sql ="select a.*,b.newvendorname,b.registrationnum "+
                    "from a_invvendor a left join VW_NEWVENDORCODE_EPMALL b " +
                    "on a.vendor=b.nvcid" +
                    " where 1=1 "+condition+ " order by a.a_Invvendorid";

        return queryBySQLWithEntity(sql,ListAInvvendorVO.class);
	}
	public List getListAItemSecondItemnum(String condition) {
		String sql ="Select * from A_ITEM_SECOND_ITEMNUM"+
                    " where 1=1 "+condition+ " order by SECOND_ITEMNUM";

        return queryBySQLWithEntity(sql,AItemSecondItemnum.class);
	}
	
	public ClassstructureItemSign getClassstructureItemSign(String classstructureuid,String organizationCode){
		String sql ="SELECT * FROM CLASSSTRUCTURE_ITEM_SIGN WHERE CLASSSTRUCTUREID='"+classstructureuid+"'" +
				"and PLANT_CODE='"+organizationCode+"'";
		return uniQueryBySQLWithEntity(sql, ClassstructureItemSign.class);
	 
	}
	public ClassstructureItemchangeSign getClassstructureItemchangeSign(String classstructureuid,String organizationCode){
		String sql ="SELECT * FROM CLASSSTRUCTURE_ITEMCHANGE_SIGN WHERE CLASSSTRUCTUREID='"+classstructureuid+"'" +
				"and PLANT_CODE='"+organizationCode+"'";
		return uniQueryBySQLWithEntity(sql, ClassstructureItemchangeSign.class);
	 
	}
	public List getClassstructureItemchangeSignGroup(String classstructureuid,String column,String itemnum){
		String sql ="SELECT distinct "+column
				+ " FROM CLASSSTRUCTURE_ITEMCHANGE_SIGN WHERE CLASSSTRUCTUREID='"+classstructureuid+"' "
						+ "AND "+column+" IS NOT NULL and plant_code in ("
						+ "select distinct c.organization_code from item_site a "
						+ "left join location_map c on a.location_site=c.location_site  "
						+ "where a.itemnum='"+itemnum+"' and nvl(ENABLE_FLAG,'N')='Y') " ;
		return queryBySQL(sql);
	 
	}
	public List getClassstructureItemSign(String classstructureuid){
		String sql ="SELECT * FROM CLASSSTRUCTURE_ITEM_SIGN WHERE CLASSSTRUCTUREID='"+classstructureuid+"'";
		return  queryBySQLWithEntity(sql, ClassstructureItemSign.class);
	 
	}
	public BigDecimal getRate(String condition){
		String sql ="SELECT SHOW_CONVERSION_RATE FROM MRO_Z_ZGL_DAILY_RATES "
				+ "WHERE TRUNC(CONVERSION_DATE)=TRUNC(SYSDATE) "+condition;
		return  (BigDecimal) uniQueryBySQL(sql);
	}
	public List getR2AItemCount(AItem aItem,String condition,Map param){
		String sql ="select distinct a.itemnum from a_item a " +
				"left join A_ITEMSPEC  b on A.EAUDITTRANSID=B.EAUDITTRANSID " +
				"where a.STATUS='INPRG' and a.classstructureid='"+aItem.getClassstructureid()+"' "
				 +condition;
        return queryBySQLByMap(sql, param);
	}

	public List getR2ItemCount(AItem aItem,String condition,Map param){
		String sql ="select distinct B.itemnum from ITEMSPEC  b  " +
				"where B.classstructureid='"+aItem.getClassstructureid()+"' " 
				+condition;
        return queryBySQLByMap(sql,param);
	}
	public BigDecimal getItemMappingCount(BigDecimal eaudittransid,BigDecimal itemId){
		String sql ="select count(1) from ITEM_MAPPING "
				+ "where EAUDITTRANSID='"+eaudittransid+"' or item_id='"+itemId+"'";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        return (BigDecimal) query.uniqueResult();
	}
	public List getRateCurrency(String toCurrency){
		String sql ="SELECT FROM_CURRENCY FROM MRO_Z_ZGL_DAILY_RATES "
				+ "WHERE TRUNC(CONVERSION_DATE)=TRUNC(SYSDATE) "
				+ "AND TO_CURRENCY='"+toCurrency+"'";
		return  queryBySQL(sql);
	}
}
