package mro.app.reportView.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class EamPmreqListDao extends FactoryBaseDAO {

	public EamPmreqListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "   SELECT   l.ORGANIZATION_CODE 廠別, prline.STOREROOM 倉別, "
				+ "prline.itemnum 料號, prline.description \"品名敘述\", "
				+ "prline.PMREQQTY 需求數量, prline.CMOREMARK \"用途說明\", "
				+ "prline.REQDELIVERYDATE 需求日, prline.prnum 保養需求單號, "
				+ "pr.REASONCODE 申請目的, prline.deptcode 申請部門, pr.REQUESTEDBY2 申請人工號, "
				+ "(SELECT   DISPLAY_NAME FROM   person "
				+ "WHERE   person_id = pr.REQUESTEDBY2) 申請人姓名, pr.status 保養需求單狀態, "
				+ "DECODE (prline.APPROVE, 1, 'OPEN',  0, 'CLOSE') prline狀態, "
				+ "PR.ISSUEDATE \"開單日期\", prline.ERP_PO PO_NO, prline.ERP_PRNUM PR_NO "
				+ "FROM eam_pr pr  JOIN eam_prline prline ON pr.prnum = prline.prnum "
				+ "LEFT JOIN location_map l ON pr.siteid = l.site_id "
				+ "where 1=1 " + condition
				+ "ORDER BY   PR.PRNUM, PRLINE.PRLINENUM ";
		return queryBySQLToLinkMap(sql, param);
	}
}
