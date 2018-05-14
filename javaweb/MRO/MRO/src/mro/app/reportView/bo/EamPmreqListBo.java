package mro.app.reportView.bo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.EamPmreqListDao;
import mro.utility.DateUtils;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class EamPmreqListBo {

    private EamPmreqListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new EamPmreqListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String itemnum,String prnum,String deptNo,
    		Date strDate,Date endDate) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND prline.itemnum like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(prnum)){
			condition.append("AND  prline.prnum  like :prnum ");
			param.put("prnum", prnum+"%");
		}
		if(StringUtils.isNotBlank(deptNo)){
			condition.append("AND prline.deptcode like :deptNo ");
			param.put("deptNo", deptNo+"%");
		}
		if(strDate!=null){
			condition.append("AND  PR.ISSUEDATE  >= :strDate ");
			param.put("strDate", strDate);
		}
		if(endDate!=null){
			condition.append("AND  PR.ISSUEDATE  < :endDate ");
			param.put("endDate", DateUtils.getAddDate(endDate,1));
		}
	   return dao.getList(condition.toString(),param);
	}
}
