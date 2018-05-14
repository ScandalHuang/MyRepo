package mro.app.aItemSimple.bo;

import java.util.List;

import mro.app.aItemSimple.dao.ListAItemSimpleDAO;
import mro.app.aItemSimple.form.AItemSimpleForm;
import mro.app.util.SystemUtils;
import mro.base.entity.AItem;
import mro.base.entity.AItemSimple;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListAItemSimpleBO {
   

    private ListAItemSimpleDAO listAItemSimpleDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listAItemSimpleDAO=new ListAItemSimpleDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void update(AItemSimple aItemSimple,AItem aitem){
		aItemSimple.setApplyNum(aitem.getItemnum());
		aItemSimple.setCreateDate(aitem.getEaudittimestamp());
		listAItemSimpleDAO.insertUpdate(aItemSimple);
	}
	@Transactional(readOnly=true)
	public List<AItemSimple> getAItemSimpleList(AItemSimpleForm aItemSimpleForm){
		StringBuffer condition=new StringBuffer();
		if(StringUtils.isNotBlank(aItemSimpleForm.getApplyNumFlag())){
			condition.append(this.getCondition(aItemSimpleForm.getApplyNumFlag(), "APPLY_NUM"));
		}
		if(StringUtils.isNotBlank(aItemSimpleForm.getAssetattridFlag())){
			condition.append(this.getCondition(aItemSimpleForm.getAssetattridFlag(), "ASSETATTRID"));
		}
		if(StringUtils.isNotBlank(aItemSimpleForm.getMoqMpqFlag())){
			condition.append(" and nvl(MOQ_MPQ_FLAG,'N')='"+aItemSimpleForm.getMoqMpqFlag()+"'");
		}
		if(StringUtils.isNotBlank(aItemSimpleForm.getOriitemnum())){
			condition.append(" and ORIITEMNUM like '"+aItemSimpleForm.getOriitemnum()+"%'");
		}
		if(StringUtils.isNotBlank(aItemSimpleForm.getChangeby())){
			condition.append(" and CHANGEBY like '"+aItemSimpleForm.getChangeby()+"'");
		}
		return listAItemSimpleDAO.getAItemSimpleList(condition.toString());
	}
	@Transactional(readOnly=true)
	public AItemSimple getAItemSimple(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listAItemSimpleDAO.getAItemSimple(condition.toString());
	}

	
	public String getCondition(String flag,String columnName){
		if(flag.equals("Y")){
			return " AND "+columnName+" IS NOT NULL";
		}else{
			return " AND "+columnName+" IS  NULL";
		}
	}
}
