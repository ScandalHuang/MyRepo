package mro.app.sign.dao;

import java.util.List;

import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;
import mro.base.entity.AItemspec;
import mro.base.entity.LocationSiteMap;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemSignDao extends FactoryBaseDAO{
	
	public ApplyItemSignDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public AItemspec getAItemspec(String condition) {

		String sql ="select * "+
                    "from  a_itemspec where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql, AItemspec.class);
	}
	public String getItemNotify(String itemnum){
		String sql="select FINAL_APPOVE_NOTIFY_EMPNO from CLASSSTRUCTURE_ITEM_SIGN where " +
				"(CLASSSTRUCTUREID,PLANT_CODE) in ( " +
				"select CLASSSTRUCTUREID,ORGANIZATION_CODE from a_item where itemnum ='"+itemnum+"')";
		Object object=uniQueryBySQL(sql);
		return object!=null?(String)object:"" ;
	}
	
	public int updateItem(String itemnum,SignStatus status){
		String sql ="update Item set STATUS='"+status+"' where 1=1 and itemnum='"+itemnum+"'";
		return modifyBySQL(sql);
	}
	public LocationSiteMap getLocationSiteMap(String oranizationCode){
		String sql="select * from location_site_map where location_site=( "
				+ "select distinct location_site from location_map  where ORGANIZATION_CODE='"+oranizationCode+"')";
		return uniQueryBySQLWithEntity(sql, LocationSiteMap.class);
	}
}
