package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemControlListDao extends FactoryBaseDAO {

	public ItemControlListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String itemtype, String condition, Map param) {

		String sql = "with MATRECTRANS_T as (SELECT    a.ITEMNUM im_itemnum, "
				+ "a.* FROM   MATRECTRANS a UNION  SELECT   im.new_matnr im_itemnum, a.* "
				+ "FROM   MATRECTRANS a, ITEM_MAPPING IM WHERE a.ITEMNUM = IM.OLD_MATNR "
				+ "AND IM.NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL), "
				+ "MATUSETRANS_T as (SELECT    a.ITEMNUM im_itemnum, a.*  "
				+ "FROM   MATUSETRANS a UNION  SELECT   im.new_matnr im_itemnum, a.* "
				+ "FROM   MATUSETRANS a, ITEM_MAPPING IM WHERE a.ITEMNUM = IM.OLD_MATNR "
				+ "AND IM.NEW_MATNR IS NOT NULL AND OLD_MATNR IS NOT NULL) "
				+ "SELECT l.LOCATION_SITE 區域,L.ORGANIZATION_CODE \"廠別\", "
				+ "item.CLASSSTRUCTUREID \"類別\",iv.itemnum \"料號\","
				+ "item.DESCRIPTION \"品名\", GET_UNITCOST(iv.itemnum) \"UNITCOST\","
				+ "round(case when ITEM.COMMODITYGROUP='R1' THEN "
				//+ "(IV.CMOMINLEVEL/3)-IV.IQC-IV.STOCK "
				+ "(IV.CMOMINLEVEL / 30 * decode(IV.STOCK_DAYS,null,PCC.STOCK_DAYS,IV.STOCK_DAYS)) - nvl(IV.IQC,0) - nvl(IV.STOCK,0) " //20180111 add
				+ "when ITEM.COMMODITYGROUP IN('94','R2') THEN "
				//+ "(IV.ORIAVGUSEQTY/2)-IV.MCBSSONHAND-IV.IQC-IV.STOCK END,2) "
				+ "(IV.ORIAVGUSEQTY / 30 * decode(IV.STOCK_DAYS,null,PCC.STOCK_DAYS,IV.STOCK_DAYS)) - nvl(IV.MCBSSONHAND,0) - nvl(IV.IQC,0) - nvl(IV.STOCK,0) "
				+ "END ,2) \"建議叫貨 量\","
				+ "DECODE(ITEM.COMMODITYGROUP,'94',IV.ORIAVGUSEQTY,'R2',IV.ORIAVGUSEQTY,'R1',IV.CMOMINLEVEL) \"總月耗用需求量\",IV.SSTOCK \"總安全庫存數\", "
				+ "DECODE(ITEM.COMMODITYGROUP,'94',IV.CMOMINLEVEL,'R2',IV.CMOMINLEVEL) \"總重訂購量\", "
				+ "IV.MCBSSONHAND \"小庫房庫存\", IV.IQC,IV.STOCK,ia.MIN_BASIC_UNIT \"最小計量單位\","
				+ "IV.IDLEDAYS,IV.SESTOCK \"該Site費用倉總數\", IV.SDSTOCK \"該Site待轉售倉總數\", "
				+ "(select SUM(QUANTITY) from MATRECTRANS_T where issuetype='RECEIPT'  "
				+ "AND to_char(sysdate,'yyyy/MM')=to_char(TRANSDATE,'yyyy/MM')  "
				+ "AND IM_ITEMNUM=IV.ITEMNUM AND TOSTORELOC=IV.LOCATION "
				+ "and siteid=iv.siteid AND TRANS_TYPE NOT LIKE '1B%' "
				+ "AND TRANS_TYPE NOT LIKE '2B%') \"當月累計收料量\", (select SUM(abs(QUANTITY)) "
				+ "from MATUSETRANS_T where to_char(sysdate,'yyyy/MM')=to_char(TRANSDATE,'yyyy/MM')  "
				+ "AND IM_ITEMNUM=IV.ITEMNUM AND STORELOC=IV.LOCATION and siteid=iv.siteid "
				+ "AND TRANS_TYPE NOT LIKE '1B%' AND TRANS_TYPE NOT LIKE '2B%') \"當月累計領料\", "
				+ "(select max(bss.USECOUNTER) from BSSTRKHIST bss "
				+ "where iv.itemnum=bss.itemnum and iv.siteid=bss.siteid) \"半年內耗用次數\","
				+ " (select max(ib.HALFYEAR_ISSUE_COUNTER) from MATUSETRANS_HALF ib "
				+ "where iv.itemnum=ib.itemnum and iv.location=ib.storeloc ) \"半年內領用次數\", "
				+ "po.PO_STATUS, po.PO_NO, "
				+ "NVL (po.quantity, 0)- NVL (po.received_qty, 0) \"PO未交量\", po.PROMISED_DATE,"
				+ "vendor.newvendorname \"po供應商\",IV.LOCATION \"倉別\", po.INSPECTOR_EMP_NAME \"po驗收人\","
				+ " (SELECT   DEPT_CODE FROM   person WHERE   person_id = po.INSPECTOR_EMP_NUM) \"po驗收人部門\", "
				+ "PO.LINE_MEMO \"PO其他說明\", PO.NOTE_TO_AGENT \"PO採購員說明\", pr.PR_NO,"
				+ " pr.QUANTITY PR_QUANTITY, pr.INSPECTOR_NAME \"pr驗收人\", (SELECT   DEPT_CODE FROM   person "
				+ "WHERE   person_id = pr.INSPECTOR_EMP_NUM) \"pr驗收人部門\", PR.LINE_MEMO \"PR其他說明\","
				+ "PR.NOTE_TO_AGENT \"PR採購員說 明\",pr.REQUESTOR_NAME \"PR開單人\", (SELECT   DEPT_CODE "
				+ "FROM   person WHERE   person_id = pr.REQUESTOR_EMPLOYEE_NUMBER) \"PR開單人部門\","
				+ "iv.MCCOMMAND,item.status, "
				+ "IA.MC_ORDER_QUANTITY MOQ,ia.MC_MIN_PACKAGE_QUANTITY MPQ,IA.DELIVERYTIME,iv.STOCK_IDLEDAYS \"庫齡之最大值\" "
				+ "FROM INVENTORY IV LEFT JOIN LOCATION_MAP L ON IV.SITEID=L.SITE_ID "
				+ "left join item on iv.itemnum=item.itemnum "
				+ "LEFT JOIN Z_ZPO_MRO_UNPROCESS_PR_GRP_V1 pr ON iv.itemnum = pr.im_itemnum "
				+ "AND iv.location = pr.DESTINATION_SUBINVENTORY and iv.siteid=pr.ORGANIZATION_ID "
				+ "LEFT JOIN (select * FROM Z_ZPO_MRO_OPEN_PO_GROUP_V1 "
				+ "WHERE SHIPMENT_CLOSED_CODE !='CLOSED FOR RECEIVING' and (NVL (quantity, 0)- NVL (received_qty,0)) > 0) po  " //20170420 增加判斷數量>0
				+ "ON iv.itemnum = po.Im_itemnum AND iv.location = po.DESTINATION_SUBINVENTORY  "
				+ "and iv.siteid=po.ORGANIZATION_ID "
				//+ "LEFT JOIN ITEM_MAPPING im on iv.itemnum=im.NEW_MATNR " //20170420 mark 因為之前有94 & R2 料號並存過 , 現在已經轉換完成了 , 所以這個舊料號的程式可以拿掉了
				+ "left join item_attribute ia on item.itemid=ia.itemid "
				+ "left join VW_NEWVENDORCODE_EPMALL vendor on to_char(po.VENDOR_ID)=nvcid "
				+ "left join PR_CONTROL_CONFIG PCC ON ITEM.COMMODITYGROUP = PCC.COMMODITYGROUP AND L.LOCATION_SITE = PCC.LOCATION_SITE "
				+ "WHERE ((ITEM.COMMODITYGROUP IN ('94','R2') AND IV.CMOMMCONTROL='CONTROL_TYPE3' "
				+ ") OR ITEM.COMMODITYGROUP='R1') "
				+ "and ((L.LOCATION_SITE = 'TW_TN_TFT' and (nvl(IV.CMOMINLEVEL,0)+NVL (po.quantity,0)+nvl(IV.IQC,0)+nvl(IV.STOCK,0)+nvl(pr.QUANTITY,0)) > 0) or L.LOCATION_SITE <> 'TW_TN_TFT') "
				+ "and item.status !='STOPUSE'  and iv.location not like '%WS%' "
				+ "and exists (select 1 from CLASSSTRUCTURE where store_category='ZERS' and CLASSSTRUCTUREID=item.CLASSSTRUCTUREID) ";
				//+ "and iv.cmominlevel>0 "+ condition
		        if(itemtype.equals("R1")){
		        	sql += "AND EXISTS (SELECT 1 FROM INVENTORY IV2, LOCATION_MAP L2 "
			        	+"WHERE IV2.SITEID=L2.SITE_ID AND L2.LOCATION_SITE = L.LOCATION_SITE "
			        	+"AND IV.ITEMNUM = IV2.ITEMNUM and "
			        	+"((L2.LOCATION_SITE = 'TW_TN_TFT' and IV2.CMOMMCONTROL='CONTROL_TYPE3' ) or (L2.LOCATION_SITE <> 'TW_TN_TFT' and IV2.CMOMINLEVEL > 0))) ";
		        }
				sql += " "+ condition
				+ "order by iv.itemnum,iv.location ";
		return queryBySQLToLinkMap(sql, param);
	}
}