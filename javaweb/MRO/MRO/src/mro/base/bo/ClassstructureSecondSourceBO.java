package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureSecondSource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureSecondSourceBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = false)
	public void delete(ClassstructureSecondSource[] list) {
		commonDAO.delete(list);
	}
    public Map<String,ClassstructureSecondSource> getMap(List<ClassstructureSecondSource> list){
    	Map map=new LinkedHashMap();
    	list.stream().forEach((l)->map.put(l.getAssetattrid(), l));
		return map;
	}
    @Transactional(readOnly=true)
    public List<ClassstructureSecondSource> getList(String classstructureid){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		
		return commonDAO.query(ClassstructureSecondSource.class, 
				Arrays.asList(Order.asc("classstructureid")), criterions);
	}
}
