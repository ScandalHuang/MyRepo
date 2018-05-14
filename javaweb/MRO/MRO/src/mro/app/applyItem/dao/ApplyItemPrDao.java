package mro.app.applyItem.dao;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class ApplyItemPrDao extends FactoryBaseDAO{
	public ApplyItemPrDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public BigDecimal getUnUseBudget(String siteid,String deptcode,String prtype){
		String sql =" select UNUSE_BUDGET from mro_prtype_budget_v v "
				+ " WHERE dept_no=GET_DEPT_UP('"+deptcode+"',(select dept_level from hr_org where dept_no=v.dept_no)) "
				+ "AND budget_month = TO_CHAR (SYSDATE, 'yyyy/MM') "
				+ "AND budget_type IN (SELECT budget_type FROM prtype_budget_reasoncode WHERE prtype = '"+prtype+"')";
		String sum=ObjectUtils.toString(uniQueryBySQL(sql));
        return StringUtils.isNotBlank(sum)?new BigDecimal(sum):null;
	}

	public void deltePrline(String prid){
		Session session = getSession();
		String sql ="delete prline " +
				"where prid='"+prid+"'";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
	}
	public void deltePrlineAssigned(String prid){
		Session session = getSession();
		String sql ="delete PrlineAssigned " +
				"where prid='"+prid+"'";
        Query query = session.createQuery(sql);
        query.executeUpdate();
	}
}
