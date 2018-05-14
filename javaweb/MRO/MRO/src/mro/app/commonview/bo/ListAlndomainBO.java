package mro.app.commonview.bo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mro.app.commonview.dao.ListAlndomainDAO;
import mro.app.util.SystemUtils;
import mro.base.entity.Alndomain;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;



@Component
@Scope("prototype")
public class ListAlndomainBO {
	
    private ListAlndomainDAO listAlndomainDAO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		listAlndomainDAO = new ListAlndomainDAO(sessionFactory);
	}
	
	
	@Transactional(readOnly=true)
	public Map getAlndomainCountByGroup(String classstructureid,boolean activeFlag){
		StringBuffer condition=new StringBuffer();
		condition.append(" and domainid like  '"+classstructureid+"%' ");

		
		if(activeFlag){ //只取生效的清單
			condition.append(" and nvl(inactive_date,sysdate+1)>sysdate ");
		}

		return onListToMap(listAlndomainDAO.getAlndomainCountByGroup(condition.toString()));
	}
	
	@Transactional(readOnly=true)
	public BigDecimal getAlndomainCount(Alndomain alndomain,boolean activeFlag){
		StringBuffer condition=new StringBuffer();
		condition.append("and domainid = '"+alndomain.getDomainid()+"'");
		if(StringUtils.isNotBlank(alndomain.getDescription())){
			condition.append("and description = '"+alndomain.getDescription()+"'");
		}
		if(alndomain.getAlndomainid()!=null){
			condition.append(" and alndomainid!="+alndomain.getAlndomainid()+" ");
		}
		if(activeFlag){ //只取生效的清單
			condition.append(" and nvl(inactive_date,sysdate+1)>sysdate ");
		}

		BigDecimal o =listAlndomainDAO.getAlndomainCount(condition.toString());
		return o==null?new BigDecimal("0"):o;
	}
	@Transactional(readOnly=true)
	public BigDecimal getAItemspecCount(Alndomain alndomain){
		BigDecimal o =listAlndomainDAO.getAItemspecCount(alndomain);
		return o==null?new BigDecimal("0"):o;
	}
	@Transactional(readOnly=true)
	public List<Alndomain> getAlndomainList(String alndomainid){
		StringBuffer condtion = new StringBuffer();
		
		condtion.append("and domainid like  '"+alndomainid+"%'");

		return listAlndomainDAO.getAlndomainList(condtion.toString());
	}
	@Transactional(readOnly=true)
	public List<Alndomain> getAlndomainList(String alndomainid,boolean inactiveB){
		StringBuffer condtion = new StringBuffer();
		
		condtion.append(" and domainid like  '"+alndomainid+"%' ");
		if(inactiveB){
			condtion.append(" and nvl(INACTIVE_DATE,sysdate+1) > sysdate ");
		}else{
			condtion.append(" and nvl(INACTIVE_DATE,sysdate+1) <= sysdate ");
		}

		return listAlndomainDAO.getAlndomainList(condtion.toString());
	}
	@Transactional(readOnly=true)
	public Alndomain getAlndomain(String domainid,String description){
		StringBuffer condtion = new StringBuffer();
		
		condtion.append("and domainid = '"+domainid+"'");
		condtion.append("and upper(description) = '"+Utility.toUpperCase(description)+"'");

		return listAlndomainDAO.getAlndomain(condtion.toString());
	}
	
	@Transactional(readOnly=true)
	public Alndomain getMaxAlndomain(String domainid,String description){
		StringBuffer condtion = new StringBuffer();
		Map map=new HashedMap();
		if(StringUtils.isNotBlank(domainid)){
			condtion.append("and DOMAINID = :DOMAINID ");
			map.put("DOMAINID", domainid.toUpperCase());
		}
		if(StringUtils.isNotBlank(description)){
			condtion.append("and upper(DESCRIPTION) = :DESCRIPTION ");
			map.put("DESCRIPTION", description.toUpperCase());
		}
		
		return listAlndomainDAO.getMaxAlndomain(condtion.toString(),map);
	}
	
	@Transactional(readOnly=true)
	public Alndomain getAlndomain(String[] columnName, Object... object){
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
		return listAlndomainDAO.getAlndomain(condition.toString());
	}
	@Transactional(readOnly=false)
	public void updateAlndomain(Alndomain a){
		if(StringUtils.isBlank(a.getValue())){
			ListAlndomainBO listAlndomainBO=SpringContextUtil.getBean(ListAlndomainBO.class);
			Alndomain maxAlndomain=listAlndomainBO.getMaxAlndomain(a.getDomainid(),"");
			a.setValue(Integer.toString(Integer.parseInt(
					maxAlndomain!=null?maxAlndomain.getValue():"0",36)+1,36));
		}
		listAlndomainDAO.insertUpdate(a);
	}
	@Transactional(readOnly=false)
	public void deleteAlndomain(Alndomain a){
		listAlndomainDAO.delete(a);
	}
	
	public Map onListToMap(List<Object[]> list){
		Map map=new HashedMap();
		if(list !=null){
			for(Object[] o:list){
				map.put(o[0], o[1]);
			}
		}
		return map;
	}
	
}
