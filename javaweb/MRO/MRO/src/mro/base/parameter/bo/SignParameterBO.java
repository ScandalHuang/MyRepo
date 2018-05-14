package mro.base.parameter.bo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.SignParameter;
import mro.base.entity.SignSourceSystem;
import mro.base.parameter.dao.SignParameterDAO;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class SignParameterBO {


    private SignParameterDAO signParameterDAO;
    
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		signParameterDAO=new SignParameterDAO(sessionFactory);
    }
	
	@Transactional(readOnly=true)
    public Map<String,String> getParameterMenu(String category){
    	
		List<SignParameter> parameterList=new ArrayList<SignParameter>();
		Map map= new LinkedHashMap<String,String>();
		StringBuffer condition=new StringBuffer();
		
		if(StringUtils.isNotBlank(category))
			condition.append("and CATEGORY = '"+category.toUpperCase()+"' ");
		
		parameterList=signParameterDAO.getSignParameterList(condition.toString());
		
		for(SignParameter parameter : parameterList){
			map.put(parameter.getParameterValue(),parameter.getParameterKey());
		}
		
		return map;
    }

	@Transactional(readOnly=true)
    public Map<String,String> getSignSourceSystemList(){
    	
		List<SignSourceSystem> parameterList=new ArrayList<SignSourceSystem>();
		Map map= new LinkedHashMap<String,String>();
		
		parameterList=signParameterDAO.getSignSourceSystemList();
		
		for(SignSourceSystem s : parameterList){
			map.put(s.getSourceSystemKey(),s.getSourceSystemValue());
		}
		
		return map;
    }
	@Transactional(readOnly=true)
    public Map<String,String> getSignSourceSystemMap(){
    	
		List<SignSourceSystem> parameterList=new ArrayList<SignSourceSystem>();
		Map map= new LinkedHashMap<String,String>();
		
		parameterList=signParameterDAO.getSignSourceSystemList();
		
		for(SignSourceSystem s : parameterList){
			map.put(s.getSourceSystemKey(),s.getDescription());
		}
		
		return map;
    }
}
