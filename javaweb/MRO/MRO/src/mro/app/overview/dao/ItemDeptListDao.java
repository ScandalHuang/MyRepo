package mro.app.overview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ItemDeptListDao extends FactoryBaseDAO {

	public ItemDeptListDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List getList(String condition, Map param) {

		String sql = "SELECT   id.deptcode \"部門代碼\", id.itemnum \"料號\", "
				+ "item.description \"品名\", "
				+ "(select USECOUNTER from BSSTRKHIST bss where id.itemnum=bss.itemnum "
				//+ "and bss.siteid=a.siteid and get_dept_up(id.deptcode,40)=bss.m_dept ) \"近半年耗用月次數\", "//201708 revise
				+ "and bss.siteid=a.siteid and GET_COSTCENTER(id.deptcode)=bss.m_dept ) \"近半年耗用月次數\", "
				+ "inb.ORIAVGUSEQTY \"平均月耗用量\",inb.sstock \"最低安全庫存\", "
				+ "(SELECT LOCATION_SITE FROM LOCATION_MAP WHERE SITE_ID=A.SITEID) \"區域\", "
				+ "(SELECT ORGANIZATION_CODE FROM LOCATION_MAP WHERE  SITE_ID=A.SITEID) \"FAB\", "
				+ "inb.MINLEVEL \"重訂購量\", a.LOCATION \"倉別\", NVL (a.stock, 0) \"庫存\", "
				+ "NVL (a.poqty, 0) \"PO\", NVL (a.prqty, 0) \"PR\", "
				+ "NVL (a.iqc, 0) \"IQC(待驗)\", "
				+ "(SELECT   NVL (SUM (c.qty), 0) FROM   eam_snapshot_bss_o c "
				+ "WHERE  c.itemnum=id.itemnum and c.siteid = a.siteid) \"BSS\", "
				+ "NVL (a.sestock, 0) \"該Site費用倉總數\", NVL (prl.pmreqqty, 0) \"PR未送審\", "
				+ "A.SDSTOCK \"該Site待轉售倉總數\",a.MCCOMMAND \"庫存變更分類說明\" "
				+ "FROM  item_dept id LEFT JOIN item ON id.itemnum = item.itemnum "
				+ "LEFT JOIN (select * from inventory where location NOT LIKE '%WS%') a "
				+ "ON item.itemnum = a.itemnum "
				+ "left join invbalances inb on id.itemnum=inb.itemnum and id.DEPTCODE=inb.DEPTCODE "
				+ "and a.location=inb.location LEFT JOIN (  SELECT   itemnum, "
				+ "siteid, storeroom, SUM (pmreqqty) pmreqqty FROM   prline "
				+ "WHERE       erp_pr_status IS NULL AND prtype = 'R2REORDER' "
				+ "AND exists (select 1 from pr where prid=prline.prid and status='APPR') "
				+ "AND line_closed_code = 'OPEN' "
				+ "GROUP BY   itemnum, siteid, storeroom) prl "
				+ "ON     a.itemnum = prl.itemnum AND a.LOCATION = prl.storeroom "
				+ "AND a.siteid = prl.siteid   "
				+ "where 1=1 "+ condition
				+ "ORDER BY   id.itemnum,id.deptcode,a.LOCATION ";
		return queryBySQLToLinkMap(sql, param);
	}

	public List getOrgs(Map param) {

		String sql = "SELECT * FROM (select ORGANIZATION_NAME,ORGANIZATION_CODE from location_map "
				+ "where organization_code is not null and location_site is not null "
				+ "and LOCATION_SITE=:locationSite "
				+ "UNION "
				+ "select NONVALUE_PLANT,NONVALUE_PLANT from location_map "
				+ "where NONVALUE_PLANT is not null and location_site is not null and LOCATION_SITE=:locationSite) "
				+ "order by ORGANIZATION_CODE";
		return queryBySQLToLinkMap(sql, param);
	}
}
