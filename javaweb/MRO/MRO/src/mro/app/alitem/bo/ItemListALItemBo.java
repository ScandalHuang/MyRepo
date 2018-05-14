package mro.app.alitem.bo;

import java.util.List;

import mro.app.alitem.dao.ItemListALItemDao;
import mro.app.alitem.vo.ItemListALItemVO;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ItemListALItemBo {

    private ItemListALItemDao itemListALItemDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		itemListALItemDao = new ItemListALItemDao(sessionFactory);
	}
    
    @Transactional(readOnly=true)
    public List<ItemListALItemVO> getItemAltitemList(String selectItemCategory,String itemnum,String itemnumDescription,String altitemnum,String altItemnumDescription) {
    	
		StringBuffer condition=new StringBuffer();
		
		if(StringUtils.isNotBlank(selectItemCategory))
			condition.append("and ITEMNUM like '"+selectItemCategory+"%' ");
		
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and ITEMNUM like '"+itemnum.toUpperCase()+"%' ");
 
		if(StringUtils.isNotBlank(itemnumDescription))	
			condition.append("and itemnum_description like '"+itemnumDescription+"%' ");
		if(StringUtils.isNotBlank(altitemnum))
			condition.append("and altitemnum like '"+altitemnum+"%' ");
		if(StringUtils.isNotBlank(altItemnumDescription))
			condition.append("and alt_itemnum_description like '"+altItemnumDescription+"%' ");
	 
		
    	return itemListALItemDao.getItemAltitemList(condition.toString());
    }


}
