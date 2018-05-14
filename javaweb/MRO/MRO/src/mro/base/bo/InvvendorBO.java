package mro.base.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Invvendor;
import mro.base.entity.VwNewvendorcodeEpmall;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class InvvendorBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	/*
	 * hongjie.wu
	 * 2015.03.04
	 * return item對應的合法供應商筆數
	 */
	@Transactional(readOnly=true)
    public Map getActiveVendorCountByItem(List<String> itemList) {
		List criterions = new ArrayList();
		Map map=new HashMap();
		ProjectionList projectionList = Projections.projectionList();
		
		criterions.add(Restrictions.in("itemnum", itemList));
		criterions.add(Restrictions.eq("disabled", NumberUtils.createBigDecimal("0")));
		criterions.add(RestrictionsUtils.exists(VwNewvendorcodeEpmall.class, "nvcid", 
				Restrictions.eqProperty("nvcid", Invvendor.class.getSimpleName()+".vendor")));
		
		projectionList.add(Projections.groupProperty("itemnum"));
		projectionList.add(Projections.rowCount());
		List<Object[] > list=commonDAO.query(Invvendor.class, null, criterions, projectionList);
		for(Object[] o:list){
			map.put(o[0], ((Long)o[1]).intValue());
		}
		return  map;
	}
}
