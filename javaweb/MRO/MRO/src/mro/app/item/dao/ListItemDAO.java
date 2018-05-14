package mro.app.item.dao;

import java.util.List;

import mro.app.item.vo.ListItemBeanAItemspecVO;
import mro.app.item.vo.ListItemBeanInvvendorVO;
import mro.app.item.vo.ListItemBeanItemVO;
import mro.app.item.vo.ListItemBeanItemspecVO;
import mro.app.item.vo.ListItemBeanMatusetransVO;
import mro.app.item.vo.ListItemBeanSparepartVO;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.EamSnapshotBssO;
import mro.base.entity.EamSnapshotBssT;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.Matrectrans;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListItemDAO extends FactoryBaseDAO{

	public ListItemDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List getItemList(String condition) {

		String sql ="select ITEM.*, "+
					"(select DISPLAY_NAME from person where person_id = item.CREATE_BY) DISPLAY_NAME, " +
					"CLASSSTRUCTURE.DESCRIPTION CLASSSTRUCTURE_DESCRIPTION "+
					"from item "+
					"LEFT JOIN item_attribute  on item_attribute.itemid=item.itemid " +
					"LEFT JOIN CLASSSTRUCTURE ON CLASSSTRUCTURE.CLASSSTRUCTUREID=ITEM.CLASSSTRUCTUREID " +
					" where 1=1 and ( ITEM.status = 'ACTIVE' or ITEM.status = 'CHANGED' "
					+ " OR ITEM.status = 'STOPUSE' OR ITEM.status = 'SYNC') "+condition+" ";

        return queryBySQLWithEntity(sql,ListItemBeanItemVO.class);
	}
	public ItemAttribute getItemAttribute(String condition) {

		String sql ="select * "+
                    "from ITEM_ATTRIBUTE where 1=1 "+condition;

        return uniQueryBySQLWithEntity(sql,ItemAttribute.class);
	}
	public List getItemSpecList(String condition) {

		String sql ="select isp.*," +
				"ase.DESCRIPTION  ASSETATTRIBUTE_DESCRIPTION " +
				"from ITEMSPEC isp " +
				"left join ASSETATTRIBUTE ase on ase.ASSETATTRID= isp.ASSETATTRID " +
				"where 1=1 " + condition +"  order by isp.DISPLAYSEQUENCE ";

        return queryBySQLWithEntity(sql,ListItemBeanItemspecVO.class);
	}
	
	public List getAItemSpecList(String condition) {

		String sql ="select a.*,p.DISPLAY_NAME,AAT.DESCRIPTION " +
				"from A_ITEMSPEC a ,PERSON p,ASSETATTRIBUTE aat ,A_ITEM AI " +
				"where  " +
				"a.CHANGEBY =p.PERSON_ID(+) and  " +
				"a.ASSETATTRID = aat.ASSETATTRID(+) AND " + 
				"a.eaudittransid = ai.eaudittransid(+) " + condition +
				"  order by a.EAUDITTRANSID desc  ,a.DISPLAYSEQUENCE ";

        return queryBySQLWithEntity(sql,ListItemBeanAItemspecVO.class);
	}
	
	public List<Item> getAltitemList(String condition) {

		String sql ="SELECT * FROM ITEM WHERE ITEMNUM in ( " +
				"SELECT SECOND_ITEMNUM FROM ITEM_SECOND_ITEMNUM WHERE 1=1 " + condition +")  " ;
        return queryBySQLWithEntity(sql,Item.class);

	}
	public List getInventoryList(String condition) {

		String sql ="SELECT NVL ((SELECT location_site FROM subinventory_config "
				+ "WHERE ori_organization_code = (SELECT organization_code "
				+ "FROM location_map WHERE site_id = INVENTORY.siteid) AND ori_location = INVENTORY.LOCATION and rownum = 1), "//201703 加上B000mapping多plant判斷只抓一筆site
				+ "(SELECT location_site FROM location_map WHERE site_id = INVENTORY.siteid)) location_site,"
				+ "INVENTORY.siteid,INVENTORY.location,INVENTORY.cmominlevel,"
				+ "INVENTORY.oriavguseqty,INVENTORY.sstock,INVENTORY.iqc,INVENTORY.stock,"
				+ "item_attribute.deliverytime,INVENTORY.idledays,INVENTORY.mccommand,"
				+ "GET_UNITCOST_BYSITE(inventory.itemnum,INVENTORY.siteid) STDCOST,"
				+ "item.commoditygroup,INVENTORY.POQTY,INVENTORY.PRQTY " 
				+ "FROM INVENTORY,item,item_attribute "
				+ "WHERE inventory.itemnum=item.itemnum(+) and item.itemid=item_attribute.itemid(+) "
				+ condition;
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
		return query.list();

	}
	public List<EamSnapshotBssO> getEamSnapshotBssOList(String condition) {

		String sql ="select * from EAM_SNAPSHOT_BSS_O where 1=1 "+condition;
        return queryBySQLWithEntity(sql,EamSnapshotBssO.class);

	}
	public List<ListItemBeanInvvendorVO> getInvvendorList(String condition) {

		String sql ="select ROWNUM,VENDOR,MODELNUM, " +
				"(SELECT newvendorname FROM VW_NEWVENDORCODE_EPMALL WHERE  INVVENDOR.VENDOR=NVCID)NAME" +
				",MCPURCOMMAND " +
				"from INVVENDOR ,ITEM " +
				"where  INVVENDOR.ITEMNUM=ITEM.ITEMNUM(+) " +
				"AND INVVENDOR.ITEMSETID=ITEM.ITEMSETID(+) and INVVENDOR.vendor != 'MM' " +
				"and INVVENDOR.DISABLED=0 "+condition;
        return queryBySQLWithEntity(sql,ListItemBeanInvvendorVO.class);

	}
//modified by r.c.; http://misplm2.cminl.oa/jira/browse/CSP-91	
	public List getprLineList(String condition) {

		String sql ="SELECT PR.SITEID,prline.itemnum,PRLINE.storeroom,prline.prnum,PR.STATUS,prline.minlevel,"
				+ "prline.unitcost,prline.currencycode,prline.linecost,item.COMMODITYGROUP,"
				+ "prline.davguseqty,prline.sstock,to_char(PR.ISSUEDATE,'yyyy/MM/dd hh24:mi:ss') ISSUEDATE,(select person.DISPLAY_NAME from "
				+ "person where PR.REQUESTEDBY2=person.PERSON_ID) DISPLAY_NAME,pr.m_dept "+
				"FROM PRLINE,PR,item WHERE PRLINE.PRNUM=PR.PRNUM AND PRLINE.SITEID=PR.SITEID  "
				+ "and prline.itemnum=item.itemnum  and " +
				"PR.STATUS not in ('CAN','DRAFT','REJECT') AND PR.PRTYPE IN('R1CONTROL', 'R2CONTROL') "+condition+
				" ORDER BY PRLINE.prnum DESC";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
		return query.list();

	}
	public List getPrlineStatusList(String condition) {
		String sql = "SELECT PRLINE.*,PR.SITEID,PR.STATUS,PR.PRTYPE,to_char(PRLINE.EPMALL_CREATE_DATE,'yyyy/MM/dd hh24:mi:ss') EPMALL_CREATE_DATE,"
				+ "(SELECT display_name from person where person_id=pr.requestedby2) display_name, "
				+ "(SELECT display_name from person where person_id=prline.ERP_PR_BUYER_EMP_NO) pr_buyer_name,pr.m_dept "
				+ "from prline,PR "
				+ "where PRLINE.PRID=PR.PRID AND PR.STATUS not in ('CAN','DRAFT','REJECT') "
				+ "AND PR.PRTYPE IN('"+PrType.R1PMREQ.name()+"', '"+PrType.R2PMREQ.name()+
				"', '"+PrType.R1REORDER.name()+"', '"+PrType.R2REORDER.name()+"') "
				+ "and prline.LINE_CLOSED_CODE !='"+SignStatus.CLOSE+"' "
				+ "and (prline.EPMALL_PR_LINE_CANCEL_FLAG is null or "
				+ "prline.EPMALL_PR_LINE_CANCEL_FLAG='N') "
				+ "and nvl(ERP_PO_STATUS,'Incomplete')='Incomplete'  " + condition +
		       "ORDER BY prline.prnum DESC,prline.PRLINENUM ";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
		return query.list();

	}
	public List getPolineList(String condition) {
		String sql ="SELECT   Z_ERP_OPEN_PO.*,prline.prnum, prline.REQUESTEDBY2, prline.M_DEPT,"
				+ "(QUANTITY-RECEIVED_QTY) ERPUNRECEIVEDQTY, "
				+ "(SELECT display_name from person where person_id=prline.requestedby2) display_name, "
				+ "(SELECT display_name from person where person_id=Z_ERP_OPEN_PO.BUYER_EMPLOYEE_NUMBER) po_buyer_name,"
				+ "(SELECT NEWVENDORNAME from VW_NEWVENDORCODE_EPMALL "
				+ "	where nvcID=Z_ERP_OPEN_PO.VENDOR_ID) po_VENDOR "
				+ "FROM Z_ERP_OPEN_PO LEFT JOIN prline "
				+ "ON Z_ERP_OPEN_PO.PO_LINE_ID = prline.ERP_POLINE and Z_ERP_OPEN_PO.part_no=prline.itemnum "
				+ "WHERE 1=1 "+condition+
		       " ORDER BY Z_ERP_OPEN_PO.PO_NO DESC,Z_ERP_OPEN_PO.PO_LINE_NO ";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); //hashmap
		return query.list();

	}
//end of modified	
	public List<ListItemBeanSparepartVO> getSparepartList(String condition) {

		String sql ="select SPAREPART.*,(select description from ASSET " +
				"where ASSETNUM=SPAREPART.ASSETNUM AND SITEID=SPAREPART.SITEID) ASSET_DESCRIPTION " +
				"from SPAREPART WHERE 1=1 " +condition+
				"ORDER BY SPAREPART.ASSETNUM ";
        return queryBySQLWithEntity(sql,ListItemBeanSparepartVO.class);

	}
	public List<ListItemBeanMatusetransVO> getMatusetransList(String condition) {

		String sql ="SELECT   MATUSETRANS.*,(select ASSETNUM from WORKORDER " +
				"where WONUM=MATUSETRANS.REFWO AND SITEID=MATUSETRANS.SITEID) WORKORDER_ASSETNUM "+
				"FROM MATUSETRANS WHERE  1=1 " +condition+
				"ORDER BY MATUSETRANS.transdate DESC ";
        return queryBySQLWithEntity(sql,ListItemBeanMatusetransVO.class);
	}
	public List<Matrectrans> getMatrectransList(String condition) {

		String sql ="SELECT   MATRECTRANS.* " +
				"FROM MATRECTRANS where 1=1 AND TOSTORELOC NOT LIKE '%WS' " +condition+
				"ORDER BY MATRECTRANS.transdate DESC ";
        return queryBySQLWithEntity(sql,Matrectrans.class);
	}
	public List<EamSnapshotBssT> getEamSnapshotBssTList(String condition) {

		String sql ="SELECT  * " +
				"FROM EAM_SNAPSHOT_BSS_T  where 1=1 " +condition+
				"order by trkdate desc  ";
        return queryBySQLWithEntity(sql,EamSnapshotBssT.class);
	}
}
