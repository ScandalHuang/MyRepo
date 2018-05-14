package mro.app.plantAttribute.dao;

import java.util.List;

import mro.base.entity.SapPlantAttribute;
import mro.base.entity.SapProfitCenter;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListSapPlantAttributeDAO extends FactoryBaseDAO{
	
	public ListSapPlantAttributeDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public List getSapPlantAttribute(String condition){
		
		String sql ="SELECT * FROM SAP_PLANT_ATTRIBUTE " +
				"where 1=1 " + condition;
		return queryBySQLWithEntity(sql, SapPlantAttribute.class);
	}
	
	public List getPlantCode(){
		
		String sql ="SELECT distinct PLANT_CODE FROM SAP_PLANT_ATTRIBUTE order by  PLANT_CODE";

		return queryBySQL(sql);
	}
	public List getMaterialGroup(){
		
		String sql ="SELECT distinct MATERIAL_GROUP FROM SAP_PLANT_ATTRIBUTE order by  MATERIAL_GROUP";

		return queryBySQL(sql);
	}
	public List getSapProfitCenterList(){
		
		String sql ="SELECT * from SAP_PROFIT_CENTER ORDER BY PLANT_CODE";

		return queryBySQLWithEntity(sql, SapProfitCenter.class);
	}
	public SapProfitCenter getSapProfitCenter(String plantCode){
		
		String sql ="SELECT * from SAP_PROFIT_CENTER where PLANT_CODE='"+plantCode+"'";

		return uniQueryBySQLWithEntity(sql, SapProfitCenter.class);
	}
}
