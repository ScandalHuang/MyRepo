package mro.app.signTask.dao;


import java.math.BigDecimal;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;

public class SignTaskDao extends FactoryBaseDAO {

	public SignTaskDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
		
	}
	public void updateSeq(BigDecimal taskId,BigDecimal seq){
		Session session = getSession();
		String sql="update SignTaskList set signSeqId=signSeqId+1 " +
				"where signSeqId>? and signTask.taskId=? and signDate is null ";
		Query query = session.createQuery(sql);
		query.setParameter(0, seq);
		query.setParameter(1, taskId);
	    query.executeUpdate();
		
	}
	public void deleteSeq(BigDecimal taskId,BigDecimal seq){
		Session session = getSession();
		String sql="delete SignTaskList " +
				"where signSeqId>? and signTask.taskId=? and signDate is null ";
		Query query = session.createQuery(sql);
		query.setParameter(0, seq);
		query.setParameter(1, taskId);
	    query.executeUpdate();
		
	}
	
}
