package mro.app.commonview.bo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.app.commonview.dao.FileUploadDAO;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.RestrictionsUtils;




@Component
@Scope("prototype")
public class FileUploadBO {

    private FileUploadDAO fileUploadDAO;
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	fileUploadDAO=new FileUploadDAO(sessionFactory);
    	commonDAO = new CommonDAO(sessionFactory);
    }
    
	@Transactional(readOnly=true)
	public BigDecimal getFileId(){
		return fileUploadDAO.getFileId();
	}
	
	@Transactional(readOnly = false)
	public void updateExcel(List<Object> objects,List<String> keyList,boolean deleteFlag,Map<String,String> entityMap) {
		if(deleteFlag && Utility.isNotEmpty(keyList)){
			List criterions = new ArrayList();
			List criterionsO = new ArrayList();
			for(int i=0;i<objects.size();i++){
				List criterionsk = new ArrayList();
				for(String s:keyList){
					String column=entityMap.get(s);
					try {
						Object value=PropertyUtils.getSimpleProperty(objects.get(i), column);
						if(ObjectUtils.toString(value).toUpperCase().equals("NULL")){
							criterionsk.add(Restrictions.isNull(column));
							ReflectUtils.setFieldValue(objects.get(i), column, null);
						}else{
							criterionsk.add(RestrictionsUtils.eq(column, value));
						}
					} catch (IllegalAccessException | InvocationTargetException
							| NoSuchMethodException e) {
						throw new RuntimeException(e.getMessage());
					}	
				}
				criterionsO.add(RestrictionsUtils.conjunction(criterionsk));
				if((i+1)%10000==0 || (i+1)==objects.size()){
					criterions.add(RestrictionsUtils.disjunction(criterionsO));
					List<Object> list=commonDAO.query(objects.get(0).getClass(), null, criterions);
					commonDAO.delete(list.toArray());
					criterionsO.clear();
					criterions.clear();
				}
			}
		}
		commonDAO.insertUpdate(objects.toArray());
	}
	
	@Transactional(readOnly=true)
	public List<Attachment> getAttachmentList(String keyId,FileCategory fileCategory,String fileId){
		StringBuffer condition=new StringBuffer(); 
		if(StringUtils.isNotBlank(keyId)){
			condition.append("and KEY_ID = '"+keyId+"' ");
		}
		if(fileCategory!=null){
			condition.append("and FILE_CATEGORY = '"+fileCategory+"' ");
		}
		if(StringUtils.isNotBlank(fileId)){
			condition.append("and FILE_ID < "+fileId+" ");
		}
		return fileUploadDAO.getAttachmentList(condition.toString());
	}
	@Transactional(readOnly=true)
	public Attachment getAttachment(String fileId){
		StringBuffer condition=new StringBuffer(); 
		if(StringUtils.isNotBlank(fileId)){
			condition.append("and FILE_ID = '"+fileId+"' ");
		}
		return fileUploadDAO.getAttachment(condition.toString());
	}
	@Transactional(readOnly=true)
	public Attachment getAttachment(String keyId,FileCategory fileCategory){
		StringBuffer condition=new StringBuffer(); 
		if(StringUtils.isNotBlank(keyId)){
			condition.append("and KEY_ID = '"+keyId+"' ");
		}
		if(fileCategory!=null){
			condition.append("and FILE_CATEGORY = '"+fileCategory+"' ");
		}
		return fileUploadDAO.getAttachment(condition.toString());
	}
	@Transactional(readOnly=true)
	public void deleteAttachmentList(String keyId,FileCategory fileCategory,String fileId){
		
		StringBuffer condition=new StringBuffer(); 
		if(StringUtils.isNotBlank(keyId)){
			condition.append("and KEY_ID = '"+keyId+"' ");
		}
		if(fileCategory!=null){
			condition.append("and FILE_CATEGORY = '"+fileCategory+"' ");
		}
		if(StringUtils.isNotBlank(fileId)){
			condition.append("and FILE_ID < "+fileId+" ");
		}
		 fileUploadDAO.deleteAttachmentList(condition.toString());
	}
	
	@Transactional(readOnly=true)
	public Map getAttachmentChangeCount(String prtItemnum,String aferItemnum){
		List<Object[]> list=fileUploadDAO.getAttachmentChangeCount(prtItemnum,aferItemnum);
		return attachmentMap(list,FileCategory.getDownloadFileMap());
	}
	
	
	@Transactional(readOnly=false)
	public void saveFile(Attachment attachment){
		fileUploadDAO.insert(attachment);
	}
	@Transactional(readOnly=false)
	public void deleteFile(Attachment attachment){
		fileUploadDAO.delete(attachment);
	}
	
	public Map attachmentMap(List<Object[]> list,Map map){
		for(Object[] o:list){
			if(o[0] instanceof Long || o[0] instanceof BigDecimal){
				map.put(o[0], o[1]);
			}else{
				map.put(FileCategory.valueOf(o[0].toString()), o[1].toString());
			}
		}
		return map;
	}
}
