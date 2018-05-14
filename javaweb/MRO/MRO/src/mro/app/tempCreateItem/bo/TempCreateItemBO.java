package mro.app.tempCreateItem.bo;

import java.util.List;

import mro.app.tempCreateItem.dao.TempCreateItemDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.AItem;
import mro.base.entity.Item;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class TempCreateItemBO {

	TempCreateItemDAO tempCreateItemDAO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		tempCreateItemDAO=new TempCreateItemDAO(sessionFactory);
    }
	
	@Transactional(readOnly=true)
	public List<Item> getItemList(){
		StringBuffer str=new StringBuffer("");
		return tempCreateItemDAO.getItemList(str.toString());
	}
	
	@Transactional(readOnly=true)
	public List<AItem> getAItemList(){
		StringBuffer str=new StringBuffer("");
		return tempCreateItemDAO.getAItemList(str.toString());
	}
	@Transactional(readOnly=true)
	public List<AItem> getAItemList(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils(columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		return tempCreateItemDAO.getAItemList(condition.toString()); 
	}
	@Transactional(readOnly=false)
	public void updateAItem(AItem aItem){
		tempCreateItemDAO.insertUpdate(aItem);
	}
	@Transactional(readOnly=false)
	public void updateItem(Item item){
		tempCreateItemDAO.insertUpdate(item);
	}
}
