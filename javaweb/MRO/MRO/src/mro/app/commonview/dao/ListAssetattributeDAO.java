package mro.app.commonview.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.Assetattribute;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListAssetattributeDAO extends FactoryBaseDAO{
	
	public ListAssetattributeDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public Assetattribute getAssetAttribute(String condition){

		String sql ="select * "+
                    "from assetattribute where 1=1 "+condition;

        return uniQueryBySQLWithBeanEntity(sql,Assetattribute.class);
	}
	public int getAssetattributeSize(String condition){
		
		String sql ="select count(1) from ASSETATTRIBUTE where 1=1 "+condition;
		return ((BigDecimal) uniQueryBySQL(sql)).intValue();
	}
	public List getMeasureunitList() {

		String sql ="select MEASUREUNITID "+
                    "from measureunit order by MEASUREUNITID ";

        return queryBySQL(sql);
	}
}
