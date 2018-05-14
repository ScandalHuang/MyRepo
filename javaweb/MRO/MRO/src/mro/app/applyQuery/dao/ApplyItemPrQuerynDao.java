package mro.app.applyQuery.dao;

import java.util.List;

import mro.base.entity.Person;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemPrQuerynDao extends FactoryBaseDAO{
	
	public ApplyItemPrQuerynDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	private String getPrView(Person person){
		return "WITH prV AS (SELECT   pr.*,nvl(pr.vendor,prline.VENDOR) new_vendor,"
				+ "prline.ITEMNUM,prline.STOREROOM,prline.PMREQQTY,prline.unitcost,"
				+ "prline.INSPECTION_EMPNO,prline.NOTE_TO_AGENT,prline.VENDOR_REMARK,"
				+ "prline.prlineid,prline.LINE_CLOSED_CODE,prline.REQDELIVERYDATE,"
				+ "NVL (DECODE ((SELECT   EPR_REQUESTEDBY2_TYPE FROM   PRTYPE_EPR "
				+ "WHERE   location_site =(SELECT   location_site FROM   location_map "
				+ "WHERE   site_id = pr.siteid) AND prtype = pr.prtype),'REQUESTEDBY2_TYPE1',"
				+ "NVL ((SELECT   person_id FROM   person WHERE   PERSON_ID = pr.REQUESTEDBY2 "
				+ "AND STATUS = 'ACTIVE'),(SELECT   person_id FROM   person "
				+ "WHERE   PERSON_ID = pr.deptsupervisor AND STATUS = 'ACTIVE')),"
				+ "'REQUESTEDBY2_TYPE2',prline.INSPECTION_EMPNO),'"+person.getPersonId()+"') "
				+ "EPR_REQUESTEDBY2 FROM  pr  LEFT JOIN prline ON pr.prid = prline.prid) ";
	}
	public int mroPrToErp(Person person,String prnum,String lineMemo,int otherDeliveryTime,boolean reorder){
		String prView=this.getPrView(person);
		String deliverTime="TRUNC(GREATEST(sysdate+nvl(  nvl( NVL(r2V.LT1, NVL(r2V.LT2, r2V.DELIVERYTIME)) ,ia.DELIVERYTIME)  ,0)+"+otherDeliveryTime+",nvl(prv.REQDELIVERYDATE,sysdate))),";
		if(reorder){
			deliverTime="trunc(nvl(prV.REQDELIVERYDATE,sysdate+nvl(  nvl( NVL(r2V.LT1, NVL(r2V.LT2, r2V.DELIVERYTIME)) ,ia.DELIVERYTIME)  ,0)+"+otherDeliveryTime+")),";
		}
		String sql=null;
			sql="insert into AP_IEP.pr_interface@DBL_PIEP_APMRO4PR(H_SYS,"
					+ "H_FORM_TYPE,H_APP_NO,H_PLANT,H_EXPLANATION,H_ISASSIGNVENDOR,I_VENDORCODE,"
					+ "H_REQ_DEPT,I_MATNO,I_STO_LOC,I_QTY,I_CURRENCY,I_PRICE,"
					+ "I_DELI_DATE,I_REQ_NO,I_MATGROUP,I_INSPECTOR,I_EXPLANATION,APID,APNO,CREATEAT)"
					+prView+ " SELECT 'MRO',0,'"+person.getPersonId()+"',prv.siteid,'"+lineMemo+"',"
					+ "nvl2(prv.vendor,1,0),v.SAP_VENDOR_CODE,"
					+ "(select DEPT_CODE from person where person_id= EPR_REQUESTEDBY2),"
					+ "prv.ITEMNUM,prv.STOREROOM,prv.PMREQQTY,'TWD',prv.unitcost,"+deliverTime
					+ "prV.EPR_REQUESTEDBY2,i.CLASSSTRUCTUREID,"
					+ "nvl(prV.INSPECTION_EMPNO,prV.REQUESTEDBY2),"
					+ "'"+lineMemo+":' ||  prV.PRNUM || nvl2(prV.REASONCODE,'申請目的：' || "
					+ "(select PARAMETER_VALUE from PARAMETER where PARAMETER_KEY=prV.REASONCODE "
					+ "and CATEGORY='R2_PMREQ_REASONCODE') || chr(10) ,'') || "
					+ "nvl2(prV.NOTE_TO_AGENT, prV.NOTE_TO_AGENT || Chr(10),'' ) ||"
					//+ "|| '"+lineMemo+":' || prV.PRNUM || "
					+ "nvl2(prV.VENDOR_REMARK,Chr(10) ||'指定供應商說明:'|| prV.VENDOR_REMARK,'')"
					+ "|| decode(substr(prtype,0,2),'R2',Chr(10) || substr(prV.extra_remark,0,1500),'') " // 20170817 add
					+ ",prV.prlineid,prV.prid,sysdate "
					+ "FROM prV "
					+ "left join item i on prV.itemnum=i.itemnum "
					+ "left join item_attribute ia on i.itemid=ia.itemid "
					+ "left join MRO_EP_R2_LT_V r2V on prV.itemnum=r2V.itemnum "
					+ "left join vw_newvendorcode_epmall v on prV.new_vendor=v.nvcid "
					+ "WHERE prV.PRNUM = '"+prnum+"' and prV.LINE_CLOSED_CODE='OPEN'";
		return modifyBySQL(sql);
	}
		
	public int updatePrLine(String prnum){
		String interfaceSQL=",IEP_INTERFACE_ID=(SELECT MAX(INTERFACEID) "
					+ "FROM AP_IEP.pr_interface@DBL_PIEP_APMRO4PR "
					+ "WHERE APID=to_char(prline.PRLINEID) and IS_PROCESS=0 and H_SYS='MRO' ) ";
		
		String sql="update prline set ERP_PRNUM='',ERP_PRLINE='',ERP_POLINE='',ERP_PO='',"
				+ "ERP_PR_STATUS='',ERP_PO_STATUS='',ERP_PR_BUYER_EMP_NO='',"
				+ "ERP_PO_BUYER_EMP_NO=''"+interfaceSQL
				+ "where prnum='"+prnum+"' AND LINE_CLOSED_CODE='OPEN'";
		return modifyBySQL(sql);
	}
	public List getApplyPrlineList(String condition){
		String sql="select p.prnum 申請單號,prl.itemnum 料號,prl.STOREROOM 倉庫,"
				+ "prl.description 品名,prl.PMREQQTY 數量,"
				+ "prl.unitcost 價格,prl.linecost 總價,prl.min_basic_unit 單位,prl.currencycode 幣別 ,"
				+ "prl.DELIVERYTIME,prl.REQDELIVERYDATE 需求日,p.requestedby 填單人 ,p.requestedby2 申請人,"
				+ "HE.DEPT_NO 申請人部門代碼,he.EXT_NO 申請人分機,LM.ORGANIZATION_CODE 申請廠區,"
				+ "LM.ORGANIZATION_NAME 申請人廠區代碼,prl.CMOREMARK 備註  "
				+ "from Prline prl  left join pr p on prl.prid=p.prid "
				+ "left join hr_emp he on p.REQUESTEDBY2=he.emp_no "
				+ "LEFT JOIN LOCATION_MAP LM ON P.SITEID=LM.SITE_ID "
				+ "where  1=1 " +condition + " order by prl.prlinenum";
		return queryBySQLToLinkMap(sql, null);
	}
}
