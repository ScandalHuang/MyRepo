package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class InprgPmreqListDao extends FactoryBaseDAO {

	public InprgPmreqListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "with wms_qty as (SELECT item_num,subinventory,ORG_ID,stock_type,"
				+ "SUM(real_QTY) real_QTY FROM ( "
				+ " select item_num,subinventory,ORG_ID, case when stock_type in ("
				+ "'保養需求單','風險備庫','安全庫存控管','例外管理') then 'PO入料' ELSE '退料&廠調' "
				+ "END stock_type ,real_QTY from WMS_STOCK_DETAIL_ALL) "
				+ "group by item_num,ORG_ID,"
				+ "subinventory,stock_type) SELECT   l.ORGANIZATION_CODE 廠別,"
				+ "st.ACTOR_ID 待簽核人員, pl.itemnum 料號,pl.PMREQQTY 申請數量,pr.prnum 申請單號, "
				+ "(SELECT PARAMETER_VALUE FROM PARAMETER WHERE CATEGORY=  "
				+ "'R2_PMREQ_REASONCODE' AND PARAMETER_KEY=PR.REASONCODE) 申請目的,( "
				+ "SELECT   PARAMETER_VALUE FROM   PARAMETER "
				+ "WHERE   CATEGORY = 'PRTYPE' AND PARAMETER_key = pr.prtype) "
				+ "申請單分類, pr.requestedby2 申請人工號,  (SELECT   DISPLAY_NAME "
				+ "FROM   person WHERE   person_id = pr.requestedby2) "
				+ "申請人姓名, pl.DESCRIPTION \"品名\",pl.m_dept 申請部門, "
				+ "pl.ENTERDATE 申請日,pl.LINECOST,pl.CMOREMARK \"用途說明\", "
				+ " pl.DELIVERYTIME 前置交貨期, "
				+ "greatest((case when i.COMMODITYGROUP='94' OR  i.COMMODITYGROUP='R2' THEN "
				+ "SYSDATE + 24 + pl.DELIVERYTIME " //20171122 lt 修改 R1 3->6 R2 18->24
				+ "ELSE SYSDATE + 6 + pl.DELIVERYTIME END),nvl(pl.REQDELIVERYDATE,sysdate)) 預定交貨日,"
				+ "(select organization_code from location_map where site_id=iv.siteid) 庫存廠別, iv.location 倉別,"
				+ "iv.STOCK 庫存總數量,wi.real_QTY 庫存數量,wi. stock_type 庫存類別, "
				+ "iv.IDLEDAYS, iv.STOCK_IDLEDAYS 庫齡之最大值, epo.PO_NO,epo.part_no po料號,"
				+ " NVL (epo.quantity, 0)- NVL (epo.received_qty, 0) PO未交量,  "
				+ "epo.PROMISED_DATE, epo.INSPECTOR_EMP_NAME po驗收人,"
				+ "(SELECT   DEPT_CODE FROM   person WHERE  person_id = epo.INSPECTOR_EMP_NUM) po驗收人部門,"
				+ "epo.LINE_MEMO PO其他說明, epo.NOTE_TO_AGENT PO採購員說明, epr.PR_NO,epr.part_no pr料號,"
				+ " epr.QUANTITY PR_QUANTITY, epr.INSPECTOR_NAME pr驗收人, "
				+ "(SELECT   DEPT_CODE FROM   person WHERE   person_id = epr.INSPECTOR_EMP_NUM) pr驗收人部門,"
				+ "epr.LINE_MEMO PR其他說明, epr.NOTE_TO_AGENT PR採購員說明, "
				+ "pl.USECOUNTER \"近半年月耗用次數\", ib.ORIAVGUSEQTY 平均月耗用量, "
				+ "ib.sstock 最低安全存量, "
				+ "DECODE (i.COMMODITYGROUP,  '94',  ib.MINLEVEL, 'R2', ib.MINLEVEL) 重訂購量, "
				+ "pl.HALFYEAR_ISSUE_COUNTER R1領用次數, DECODE (i.COMMODITYGROUP, 'R1', ib.MINLEVEL) R1月用量, "
				+ "iv.MCCOMMAND, "
				+ "(SELECT NEWVENDORNAME  FROM VW_NEWVENDORCODE_EPMALL "
				+ "WHERE NVCID=pl.VENDOR) 指定供應商, pl.VENDOR_REMARK 指定供應商說明,"
				+ "PR.EXTRA_REMARK \"額外說明\" "
				+ "FROM  pr LEFT JOIN    prline pl  ON pr.PRID = pl.prid  LEFT JOIN "
				+ "location_map l ON pr.siteid = site_id LEFT JOIN "
				+ "(SELECT * FROM INVENTORY WHERE LOCATION NOT LIKE '%WS' AND STOCK>0 "
				+" and (location NOT like '347%' "
	            +"  OR (location like '347%' AND siteid NOT IN ('B002','B009','B010'))) "    
				+") iv "
				+ "ON  pl.itemnum = iv.itemnum LEFT JOIN "
				+ "item i   ON  pl.itemnum = i.itemnum  LEFT JOIN invbalances ib ON "
				+ " pl.STOREROOM = ib.LOCATION and pl.siteid=ib.siteid "
				+ "AND pl.itemnum = ib.itemnum  AND pr.M_DEPT "
				+ "= ib.deptcode and iv.siteid=ib.siteid "
				+ "LEFT JOIN  Z_ZPO_MRO_UNPROCESS_PR_GRP_V1 epr ON pl.itemnum "
				+ "= epr.im_itemnum AND pl.STOREROOM = epr.DESTINATION_SUBINVENTORY "
				+ "and pl.siteid=epr.organization_id LEFT JOIN Z_ZPO_MRO_OPEN_PO_GROUP_V1 epo "
				+ "ON pl.itemnum = epo.im_itemnum  AND pl.STOREROOM = epo.DESTINATION_SUBINVENTORY  "
				+ "and pl.siteid=epo.organization_id "
				+ "LEFT JOIN SIGN_TASK st on pr.task_id=st.task_id "
				+ "left join wms_qty wi on IV.itemnum=wi.item_num "
				+ "and iv.location=wi.subinventory and iv.siteid=wi.ORG_ID "
				+ "WHERE   pr.status = 'INPRG' "
				+ "AND pr.prtype IN ('R1PMREQ', 'R2PMREQ') and pl.LINE_CLOSED_CODE='OPEN' "
				//+ "and (iv.location is null OR iv.location NOT like '347%' OR (iv.location  like '347%' " //20170303 add null
				//+ "AND iv.siteid NOT IN ('B002','B009','B010'))) "
				+ condition
				+ "ORDER BY   pr.PRNUM, pl.PRLINENUM,iv.itemnum,iv.location";
		return queryBySQLToLinkMap(sql, param);
	}
}
