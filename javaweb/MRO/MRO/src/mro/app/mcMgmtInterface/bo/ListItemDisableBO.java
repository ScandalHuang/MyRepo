package mro.app.mcMgmtInterface.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mro.app.mcMgmtInterface.dao.ListItemDisableDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.Item;
import mro.base.entity.ItemDisableLog;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListItemDisableBO {
   

    private ListItemDisableDAO listItemDisableDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listItemDisableDAO=new ListItemDisableDAO(sessionFactory);
    	
    }
    
	@Transactional(readOnly=true)
	public List getItemDisableLogList(String[] columnName,Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listItemDisableDAO.getItemDisableLogList(condition.toString());
    }
	
	@Transactional(readOnly=false)
	public void update(Item item,String remark,String disableFlag,String empNo){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String disableFlagRemark=StringUtils.isNotBlank(remark)?
				sdf.format(new Date())+" "+remark : "";
		disableFlagRemark=disableFlagRemark+"(異動人工號："+empNo+")";
		disableFlag=disableFlag==null?"":disableFlag;
		
		ItemDisableLog itemDisableLog=new ItemDisableLog();
		itemDisableLog.setItemnum(item.getItemnum());
		itemDisableLog.setItemid(item.getItemid());
		itemDisableLog.setDisableFlag(disableFlag);
		itemDisableLog.setCreateBy(empNo);
		itemDisableLog.setCreateDate(new Date(System.currentTimeMillis()));
		itemDisableLog.setRemark(disableFlagRemark);
//		listItemDisableDAO.updateEpSystemItems(item.getItemnum(),disableFlag);
		listItemDisableDAO.updateItem(item.getItemnum(),disableFlag,disableFlagRemark);
		listItemDisableDAO.insertUpdate(itemDisableLog);
	}
}