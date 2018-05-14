package mro.app.item.bo;

import java.math.BigDecimal;
import java.util.List;

import mro.app.item.dao.ListItemDAO;
import mro.app.item.vo.ListItemBeanAItemspecVO;
import mro.app.item.vo.ListItemBeanItemVO;
import mro.app.item.vo.ListItemBeanItemspecVO;
import mro.app.item.vo.ListItemBeanMatusetransVO;
import mro.app.item.vo.ListItemBeanSparepartVO;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.EamSnapshotBssO;
import mro.base.entity.EamSnapshotBssT;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.Matrectrans;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@Scope("prototype")
public class ListItemBO {

    private ListItemDAO listItemDAO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		listItemDAO = new ListItemDAO(sessionFactory);
	}

	@Transactional(readOnly=true)
    public List<ListItemBeanItemVO> getItemList(String selectItemCategory,String itemnum,String description,String classstructureid){
    	
		StringBuffer condition=new StringBuffer();

		
		if(StringUtils.isNotBlank(selectItemCategory)){ 
				condition.append("and item.CLASSSTRUCTUREID like '"+selectItemCategory+"%' ");
		}
		
		if(StringUtils.isNotBlank(itemnum)){
			if(itemnum.indexOf(",")==-1){
				condition.append("and item.ITEMNUM like '"+itemnum.toUpperCase()+"%' ");
			}else{
				String[] s=itemnum.split(",");
				String si="('0'";
				for(int i=0;i<s.length;i++)
					si=si+",'"+s[i]+"'";
				si=si+")";
				
				condition.append("and item.ITEMNUM in "+si+" ");
			}
		}

		if(StringUtils.isNotBlank(description))	
			condition.append("and upper(item.DESCRIPTION) like '%"+description.toUpperCase()+"%' ");
		if(StringUtils.isNotBlank(classstructureid))
			condition.append("and item.CLASSSTRUCTUREID like '"+classstructureid.toUpperCase()+"%' ");
		
    	return listItemDAO.getItemList(condition.toString());
    }

	@Transactional(readOnly = true)
	public ItemAttribute getItemAttribute(BigDecimal itemid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and ITEMID = " + itemid + " ");
		
		//ItemAttribute i=applyItemChangeDao.getItemAttribute(condition.toString());
		//AItemAttribute a= new AItemAttribute();
		//if(i!=null) {BeanUtils.copyProperties(i,a);}
		return listItemDAO.getItemAttribute(condition.toString());
	}
	
	@Transactional(readOnly=true)
    public List<ListItemBeanItemspecVO> getItemSpecList(String itemnum){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and isp.ITEMNUM='"+itemnum+"'");
		
    	return listItemDAO.getItemSpecList(condition.toString());
    }
	
	@Transactional(readOnly=true)
    public List<ListItemBeanAItemspecVO> getAItemSpecList(BigDecimal itemid){
    	
		StringBuffer condition=new StringBuffer();

		if(itemid!=null)
			condition.append("and A.EAUDITTRANSID IN (SELECT EAUDITTRANSID FROM " +
					"A_ITEM WHERE ITEMID='"+itemid+"')");
		
			condition.append("and AI.status IN ('"+SignStatus.APPR+"')");
		
    	return listItemDAO.getAItemSpecList(condition.toString());
    }
	
	@Transactional(readOnly=true)
    public List<Item> getAltitemList(BigDecimal itemid){
    	
		StringBuffer condition=new StringBuffer();

		if(itemid!=null)
			condition.append("and ITEMID='"+itemid+"'");
		
		
    	return listItemDAO.getAltitemList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List getInventoryList(String itemnum,String itemsetid){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and INVENTORY.ITEMNUM='"+itemnum+"'");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and INVENTORY.ITEMSETID='"+itemsetid+"'");
		
    	return listItemDAO.getInventoryList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List getInvvendorList(String itemnum,String itemsetid){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and INVVENDOR.ITEMNUM='"+itemnum+"'");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and INVVENDOR.ITEMSETID='"+itemsetid+"'");
		
    	return listItemDAO.getInvvendorList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List getprLineList(String itemnum,String itemsetid){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and prline.ITEMNUM in ("
				+ "SELECT   ITEMNUM FROM   ITEM "
				+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
				+ "UNION "
				+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
				+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
				+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and PRLINE.ITEMSETID='"+itemsetid+"'");
		
    	return listItemDAO.getprLineList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List getPrlineStatusList(String itemnum){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and prline.ITEMNUM in ("
				+ "SELECT   ITEMNUM FROM   ITEM "
				+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
				+ "UNION "
				+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
				+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
				+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
    	return listItemDAO.getPrlineStatusList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List getPolineList(String itemnum){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and Z_ERP_OPEN_PO.PART_NO in ("
					+ "SELECT   ITEMNUM FROM   ITEM "
					+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
					+ "UNION "
					+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
					+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
    	return listItemDAO.getPolineList(condition.toString());
    }
	@Transactional(readOnly=true)
    public List<EamSnapshotBssO> getEamSnapshotBssOList(String itemnum){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and ITEMNUM='"+itemnum+"'");
		
    	return listItemDAO.getEamSnapshotBssOList(condition.toString());
    }
	@Transactional(readOnly=true)
    public  List<ListItemBeanSparepartVO> getSparepartList(String itemnum,String itemsetid){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and SPAREPART.ITEMNUM='"+itemnum+"'");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and SPAREPART.ITEMSETID='"+itemsetid+"'");
		
    	return listItemDAO.getSparepartList(condition.toString());
    }
	@Transactional(readOnly=true)
    public  List<ListItemBeanMatusetransVO> getMatusetransList(String itemnum,String itemsetid){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and MATUSETRANS.ITEMNUM in ("
					+ "SELECT   ITEMNUM FROM   ITEM "
					+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
					+ "UNION "
					+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
					+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and MATUSETRANS.ITEMSETID='"+itemsetid+"'");

		condition.append("and MATUSETRANS.STORELOC NOT LIKE '%WS' ");
	
    	return listItemDAO.getMatusetransList(condition.toString());
    }
	
	@Transactional(readOnly=true)
    public  List<Matrectrans> getMatrectransList(String itemnum,String itemsetid,String issueType){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and MATRECTRANS.ITEMNUM in ("
					+ "SELECT   ITEMNUM FROM   ITEM "
					+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
					+ "UNION "
					+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
					+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
		if(StringUtils.isNotBlank(itemnum))
			condition.append("and MATRECTRANS.ITEMSETID='"+itemsetid+"'");
		
		if(StringUtils.isNotBlank(issueType))
			condition.append("and MATRECTRANS.ISSUETYPE='"+issueType+"'");
		
    	return listItemDAO.getMatrectransList(condition.toString());
    }
	@Transactional(readOnly=true)
    public  List<EamSnapshotBssT> getEamSnapshotBssTList(String itemnum){
    	
		StringBuffer condition=new StringBuffer();

		if(StringUtils.isNotBlank(itemnum))
			condition.append("and ITEMNUM in ("
					+ "SELECT   ITEMNUM FROM   ITEM "
					+ "WHERE   ITEMNUM LIKE '"+itemnum+"' "
					+ "UNION "
					+ "(SELECT   OLD_MATNR FROM   ITEM_MAPPING "
					+ "WHERE  NEW_MATNR LIKE '"+itemnum+"' "
					+ "AND NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL))");
		
		
    	return listItemDAO.getEamSnapshotBssTList(condition.toString());
    }
}
