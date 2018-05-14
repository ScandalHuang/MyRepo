package mro.app.r2Correct.dao;

import java.util.Map;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class AItemCorrectDao extends FactoryBaseDAO{
	public AItemCorrectDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public AItem getAIteList(Map param){
		String sql="select * from a_item "
				+ "where itemnum = :itemnum and (status is null or status ='"+SignStatus.NEW+"') "
				+ "and  eaudittransid in ( "
				+ "select eaudittransid from item_mapping where  NEW_MATKL like 'R2%' AND ITEM_ID IS NULL)";
		return uniQueryBySQLWithEntity(sql, AItem.class,param);
	}
	
	public void deleteItemMapping(Map param){
		String sql="delete item_mapping  "
				+ "where  eaudittransid in  "
				+ "(select eaudittransid from a_item where itemnum=:itemnum) ";
		modifyBySQL(sql,param);
	}
	
	public int updateChangeby(Map param){
		String sql="update a_item set changeby=:changeby, "
				+ "eauditusername = upper((select DOMAIN_ACCOUNT from hr_emp where EMP_NO=:changeby)), "
				+ "dept_no = (select dept_no from hr_emp where EMP_NO=:changeby) "
				+ "where itemnum=:itemnum and (status is null or status ='"+SignStatus.NEW+"') "
				+ "and  eaudittransid in ( "
				+ "select eaudittransid from item_mapping where  NEW_MATKL like 'R2%' AND ITEM_ID IS NULL)";
		return modifyBySQL(sql,param);
	}
	public int updateOrganizationCode(Map param){
		String sql="update a_item set ORGANIZATION_CODE=:organizationCode "
				+ "where itemnum=:itemnum and (status is null or status ='"+SignStatus.NEW+"') "
				+ "and  eaudittransid in ( "
				+ "select eaudittransid from item_mapping where  NEW_MATKL like 'R2%' AND ITEM_ID IS NULL)";
		return modifyBySQL(sql,param);
	}
	public int updateClassstructureid(Map param){
		String sql="update a_item set commodity=:commodity, "
				+ "commoditygroup = :commoditygroup,classstructureid=:classstructureid "
				+ "where itemnum=:itemnum and (status is null or status ='"+SignStatus.NEW+"') "
				+ "and  eaudittransid in ( "
				+ "select eaudittransid from item_mapping where  NEW_MATKL like 'R2%' AND ITEM_ID IS NULL)";
		return modifyBySQL(sql,param);
	}
	public int updateItemMapping(Map param){
		String sql="update item_mapping  "
				+ "set NEW_MATKL=:classstructureid "
				+ "where  eaudittransid in  "
				+ "(select eaudittransid from a_item where itemnum=:itemnum) ";
		return modifyBySQL(sql,param);
	}
}
