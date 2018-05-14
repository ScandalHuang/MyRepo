package mro.app.commonview.dao;

import java.math.BigDecimal;
import java.util.List;

import mro.base.entity.Attachment;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class FileUploadDAO extends FactoryBaseDAO{
	
	public FileUploadDAO(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public BigDecimal getFileId(){
		String sql ="select FILE_ID_SEQ.nextval from dual ";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        return (BigDecimal) query.uniqueResult();
	}
	public List getAttachmentList(String condition){
		String sql ="select * from ATTACHMENT  where 1=1 "+condition+" order by FILE_ID";
		return queryBySQLWithEntity(sql,Attachment.class);
	}
	public Attachment getAttachment(String condition){
		String sql ="select * from ATTACHMENT  where 1=1 "+condition;
		return uniQueryBySQLWithEntity(sql,Attachment.class);
	}
	public void deleteAttachmentList(String condition){
        String sql="delete ATTACHMENT  where 1=1 "+condition;
        Session session = getSession();
        session.createSQLQuery(sql).executeUpdate();
	}

	public List getAttachmentChangeCount(String prtItemnum,String aferItemnum){
		String sql ="select file_category,count(1) from (( "
				+ "select file_name,file_category from attachment where key_id='"+prtItemnum+"' "
				+ "minus select file_name,file_category from attachment where key_id='"+aferItemnum+"') "
				+ "union ( "
				+ "select file_name,file_category from attachment where key_id='"+aferItemnum+"' "
				+ "minus select file_name,file_category from attachment where key_id='"+prtItemnum+"')) "
				+ "group by file_category";
		return queryBySQL(sql);
	}
}

