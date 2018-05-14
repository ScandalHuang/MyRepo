package mro.base.bo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.entity.ParameterMap;
import mro.base.entity.Parameter;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class ParameterBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public Map getParameterAllMap(){
		Map<ParameterType,ParameterMap> map=ParameterType.getInitialMap();
		for(Parameter p:getList()){
			//parameter map
			map.get(ParameterType.getEnum(p.getCategory())).getMap().put(
					p.getParameterKey(), p.getParameterValue());
			//parameter option(刪除的選項不顯示)
			if(p.getDelted().equals("0")){
				map.get(ParameterType.getEnum(p.getCategory())).getOption().put(
						p.getParameterValue(),p.getParameterKey());
			}
		}
		return map;
	}
	@Transactional(readOnly = true)
	public Map getParameterMap(){
		Map<ParameterType,ParameterMap> map=ParameterType.getInitialMap();
		for(Parameter p:getList()){
			//parameter map
			map.get(ParameterType.getEnum(p.getCategory())).getMap().put(
					p.getParameterKey(), p.getParameterValue());
			//parameter option(刪除的選項不顯示)
			if(p.getDelted().equals("0")){
				map.get(ParameterType.getEnum(p.getCategory())).getOption().put(
						p.getParameterValue(),p.getParameterKey());
			}
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public List<Parameter> getList(){
		return commonDAO.query(Parameter.class,Arrays.asList(Order.asc("parameterValue")), null);
	}
}
