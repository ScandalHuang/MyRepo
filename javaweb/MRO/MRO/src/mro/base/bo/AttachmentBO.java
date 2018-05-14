package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class AttachmentBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = true)
	public List getCount(List<String> keyIds,FileCategory fileCategory) {
		List criterions = new ArrayList();
		ProjectionList projectionList = Projections.projectionList();
		if(fileCategory!=null){
			criterions.add(RestrictionsUtils.eq("fileCategory", fileCategory.name()));
		}
		criterions.add(RestrictionsUtils.in("keyId", keyIds));
		projectionList.add(Projections.groupProperty("keyId"));
		projectionList.add(Projections.groupProperty("fileCategory"));
		projectionList.add(Projections.rowCount());
		return commonDAO.query(Attachment.class,null, criterions,projectionList);
	}
	@Transactional(readOnly=true)
	public Map getMap(String keyId,FileCategory fileCategory,boolean headerFlag){
		return this.getMap(Arrays.asList(keyId), fileCategory, headerFlag);
	}
	@Transactional(readOnly=true)
	public Map getMap(List<String> keyIds,FileCategory fileCategory,boolean headerFlag){
		Map map=new HashMap<>();
		
		//===================initial map==========================
		if(!headerFlag){  
			keyIds.stream().forEach(l->map.put(new BigDecimal(l), 0));
		}else{
			if(fileCategory !=null){
				map.put(fileCategory, 0);
			}
		}
		
		List<Object[]> list=this.getCount(keyIds, fileCategory);
		for(Object[] o:list){
			if(headerFlag) map.put(FileCategory.valueOf(o[1].toString()), o[2]);
			else map.put(new BigDecimal(o[0].toString()), o[2]);
		}
		return map;
	}
}
