package mro.app.commonview.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import mro.app.commonview.vo.ListClassspecVO;
import mro.base.entity.Assetattribute;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListClasssepcDAO extends FactoryBaseDAO{
	
	public ListClasssepcDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getListClassspec(String condition) {

		String sql ="select a3.*,a4.datatype,a4.description,a4.DECIMALPLACES "+
                    "from classspec a3 left join assetattribute a4 on a4.assetattrid = a3.assetattrid "+
                    "where 1 =1 "+condition+"  order by a3.ITEMSEQUENCE ";

        return queryBySQLWithEntity(sql,ListClassspecVO.class);
	}
	
	public ListClassspecVO getListClassspecVO(String condition) {

		String sql ="select a3.*,a4.datatype,a4.description,a4.DECIMALPLACES "+
                "from classspec a3 left join assetattribute a4 on a4.assetattrid = a3.assetattrid "+
                "where 1 =1 "+condition+"  order by a3.ITEMSEQUENCE ";


        return uniQueryBySQLWithEntity(sql,ListClassspecVO.class);
	}
	public Assetattribute getAssetattribute(String condition) {

		String sql ="select * from assetattribute "+
                    "where 1 =1 "+condition;

        return uniQueryBySQLWithEntity(sql,Assetattribute.class);
	}
}
