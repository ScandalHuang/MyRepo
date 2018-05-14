package mro.app.overview.bo;

import java.util.Date;
import java.util.List;

import mro.app.overview.dao.ListItemDeptDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.ItemDept;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListItemDeptBO {
   

    private ListItemDeptDAO listItemDeptDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	listItemDeptDAO=new ListItemDeptDAO(sessionFactory);
    	
    }
	@Transactional(readOnly=false)
	public void delete(ItemDept[] list){
		for(ItemDept a:list){
			listItemDeptDAO.delete(a);
		}
	}
	@Transactional(readOnly=false)
	public void insert(String empNO,String deptCode,String itemnum){
		listItemDeptDAO.delete(deptCode,itemnum);
		ItemDept itemDept=new ItemDept();
		itemDept.setDeptcode(deptCode);
		itemDept.setItemnum(itemnum);
		itemDept.setLastupdateBy(empNO);
		itemDept.setLastupdateDate(new Date(System.currentTimeMillis()));
		listItemDeptDAO.insertUpdate(itemDept);
	}
	
	@Transactional(readOnly=false)
	public void delete(ItemDept itemDept){
		listItemDeptDAO.delete(itemDept);
	}
	
	@Transactional(readOnly=true)
	public List getItemDeptList(String[] columnName,Object...object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
    	return listItemDeptDAO.getItemDeptList(condition.toString());
	}

}
