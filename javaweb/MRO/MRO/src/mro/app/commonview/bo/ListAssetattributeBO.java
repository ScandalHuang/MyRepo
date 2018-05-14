package mro.app.commonview.bo;

import java.math.BigDecimal;
import java.util.List;

import mro.app.commonview.dao.ListAssetattributeDAO;
import mro.base.System.config.SystemConfig;
import mro.base.entity.Assetattribute;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListAssetattributeBO {
   
    private ListAssetattributeDAO listAssetattributeDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listAssetattributeDAO=new ListAssetattributeDAO(sessionFactory);
    	
    }
	
	@Transactional(readOnly=true)
	public List getMeasureunitList(){	
		
	   return listAssetattributeDAO.getMeasureunitList();
	}

	@Transactional(readOnly=true)
	public int getAssetattributeSize(String assetattrid){
		StringBuffer condtion = new StringBuffer();
		if(!StringUtils.isEmpty(assetattrid)){
			condtion.append("and ASSETATTRID = '"+assetattrid+"'");
		}
		return listAssetattributeDAO.getAssetattributeSize(condtion.toString());
	}
	
	@Transactional(readOnly=false)
	public void updateAssetattribute( Assetattribute a){
		if(a.getDatatype().equals(SystemConfig.numeric)){
			if(a.getDecimalplaces()==null){
				a.setDecimalplaces(new BigDecimal("0"));
			}
		}else{
			a.setDecimalplaces(null);
		}
		listAssetattributeDAO.insertUpdate(a);
	}
}
