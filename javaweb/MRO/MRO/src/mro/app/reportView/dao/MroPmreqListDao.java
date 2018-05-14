package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class MroPmreqListDao extends FactoryBaseDAO {

	public MroPmreqListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT  (select lm.LOCATION_SITE from location_map lm "
				+ "where  l.ORGANIZATION_CODE=lm.ORGANIZATION_CODE) 區域, "
				+ "l.ORGANIZATION_CODE 廠別,prline.STOREROOM 倉別, "
				+ "(SELECT   PARAMETER_VALUE FROM   PARAMETER "
				+ "WHERE   CATEGORY = 'PRTYPE' AND PARAMETER_key = pr.prtype)  申請單分類, "
				+ "prline.itemnum 料號, prline.description \"品名敘述\", "
				+ "prline.PMREQQTY 需求數量, prline.CMOREMARK \"用途說明\", "
				+ "prline.REQDELIVERYDATE 需求日, prline.prnum 申請單號, "
				+ "(SELECT PARAMETER_VALUE FROM PARAMETER  "
				+ "WHERE CATEGORY='R2_PMREQ_REASONCODE' AND PARAMETER_KEY= pr.REASONCODE) 申請目的, "
				+ "prline.m_dept 申請部門, pr.REQUESTEDBY2 申請人工號, "
				+ "(SELECT   DISPLAY_NAME FROM   person WHERE person_id = pr.REQUESTEDBY2) 申請人姓名, "
				+ "pr.status 申請單狀態, (select actor_id ||'('|| actor_name||')' "
				+ "from MRO_APPLY_SIGN_LIST  "
				+ "where task_id=pr.task_id and status='INPRG') \"待簽核人員\", "
				+ "prline.LINE_CLOSED_CODE prline狀態, PR.ISSUEDATE \"開單日期\", "
				+ "PR.changedate \"送審日期\", PR.statusdate \"狀態變更日期\", "
				+ "prline.ERP_PO PO_NO, prline.ERP_PO_STATUS PO狀態, "
				+ "(SELECT   NEWVENDORNAME FROM   VW_NEWVENDORCODE_EPMALL "
				+ "WHERE   ORACLE_VENDOR_ID = prline.PO_VENDOR_ID) PO供應商, "
				+ "prline.PO_PROMISED_DATE PO交期, "
				+ "(SELECT DELI_DATE FROM SRM_VW_PO_DELI_DATA_2MRO  "
				+ "WHERE PONO=prline.ERP_PO AND (POITEM=PRLINE.ERP_PO_LINE_NUM "
				+ "OR POITEM=TO_CHAR (TO_NUMBER (PRLINE.ERP_PO_LINE_NUM)))) 供應商confirm交期, "
				+ "(SELECT   DISPLAY_NAME FROM   person "
				+ "WHERE   person_id = prline.ERP_PO_BUYER_EMP_NO)  PO採購員, "
				+ "prline.ERP_PRNUM PR_NO, prline.ERP_PR_STATUS PR狀態, "
				+ "(SELECT   DISPLAY_NAME FROM   person "
				+ "WHERE   person_id = prline.ERP_PR_BUYER_EMP_NO) \"預設採購員\", "
				+ "prline.ERP_BUYER_RETURN_NOTE \"採購退回說明\", "
				+ "NVL(TO_CHAR(PRLINE.EP_INTERFACE_ID),PRLINE.IEP_INTERFACE_ID) \"拋轉ID\"   "
				+ "FROM pr LEFT JOIN prline ON pr.prid = prline.prid "
				+ "LEFT JOIN location_map l ON pr.siteid = l.site_id "
				+ " WHERE   pr.prtype IN ('R1PMREQ', 'R2PMREQ','R1REORDER','R2REORDER')  "
				+ condition
				+ " ORDER BY PR.PRNUM,PRLINE.PRLINENUM ";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOrgs(Map param,String condition) {
		String sql = "select ORGANIZATION_NAME,ORGANIZATION_CODE from location_map "
				+ "where organization_code is not null "+condition
				+ "order by ORGANIZATION_CODE";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getStatus() {
		String sql = "SELECT PARAMETER_VALUE,PARAMETER_KEY "
				+ "FROM PARAMETER WHERE CATEGORY='PROCESS_CATEGORY' "
				+ "AND PARAMETER_KEY IN ('APPR','INPRG','REJECT','CAN') ";
		return queryBySQL(sql);
	}
}
