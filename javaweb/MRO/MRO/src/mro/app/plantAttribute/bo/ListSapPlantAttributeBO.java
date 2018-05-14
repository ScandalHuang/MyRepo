package mro.app.plantAttribute.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.plantAttribute.dao.ListSapPlantAttributeDAO;
import mro.base.entity.SapPlantAttribute;
import mro.base.entity.SapProfitCenter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListSapPlantAttributeBO {
   

    private ListSapPlantAttributeDAO listSapPlantAttributeDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listSapPlantAttributeDAO=new ListSapPlantAttributeDAO(sessionFactory);
    	
    }
    @Transactional(readOnly=false)
	public void onDelete(SapPlantAttribute[] sapPlantAttributes){
    	listSapPlantAttributeDAO.delete(sapPlantAttributes);
	}
    
	@Transactional(readOnly=false)
	public void updateSapProfitCenter(SapProfitCenter sapProfitCenter,String empNO){
		if(StringUtils.isNotBlank(sapProfitCenter.getProfitCenter())){
			sapProfitCenter.setCreateBy(empNO);
			sapProfitCenter.setCreateDate(new Date(System.currentTimeMillis()));
			listSapPlantAttributeDAO.insertUpdate(sapProfitCenter);
		}else{
			listSapPlantAttributeDAO.delete(sapProfitCenter);
		}
	}
	@Transactional(readOnly=true)
	public List<SapProfitCenter> getSapProfitCenterList(){
		return listSapPlantAttributeDAO.getSapProfitCenterList();
	}
	@Transactional(readOnly=true)
	public SapProfitCenter getSapProfitCenter(String plantCode){
		return listSapPlantAttributeDAO.getSapProfitCenter(plantCode);
	}
	@Transactional(readOnly=true)
	public List getSapPlantAttribute(String plantCode,String materialGroup){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(plantCode)){
			condition.append(" and Plant_code ='"+plantCode+"'");
		}
		if(StringUtils.isNotBlank(materialGroup)){
			condition.append(" and MATERIAL_GROUP ='"+materialGroup+"'");
		}
		return listSapPlantAttributeDAO.getSapPlantAttribute(condition.toString());
	}
	
	@Transactional(readOnly=true)
	public Map getPlantCode(){
		List list=listSapPlantAttributeDAO.getPlantCode();
		Map map=new LinkedHashMap<>();
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
	@Transactional(readOnly=true)
	public Map getMaterialGroup(){
		List list=listSapPlantAttributeDAO.getMaterialGroup();
		Map map=new LinkedHashMap<>();
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
}
