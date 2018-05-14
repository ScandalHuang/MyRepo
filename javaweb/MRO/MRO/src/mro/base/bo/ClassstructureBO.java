package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructurePrtype;
import mro.base.entity.Prline;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class ClassstructureBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
	public Map<String,String> getClassstructureMap(String classstructureid,String prtype) {
		Map map=new LinkedHashMap<>();
		List<Classstructure> list=getClassstructureList(classstructureid, prtype);
		for(Classstructure c:list){
			map.put(c.getClassstructureid(), c.getClassstructureid()+"("+c.getDescription()+")");
		}
		return map;
	}
	@Transactional(readOnly=true)
	public Map<String,String> getChildMap(String classstructureid) {
		Map map=new LinkedHashMap<>();
		List<Classstructure> list=getClassstructureChildList(classstructureid);
		for(Classstructure c:list){
			map.put(c.getDescription()+"_"+c.getClassstructureid(),c.getClassstructureid());
		}
		return map;
	}
	@Transactional(readOnly=true)
    public List<Classstructure> getClassstructureList(String classstructureid,String prtype) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		criterions.add(RestrictionsUtils.eq("haschildren", (long)0));
		if(StringUtils.isNotBlank(prtype)){
			criterions.add(RestrictionsUtils.exists(ClassstructurePrtype.class, 
				"classstructureid", 
				Restrictions.eqProperty("classstructureid", 
						Classstructure.class.getSimpleName()+".classstructureid"),
				Restrictions.eq("prtype", prtype)));
		}
		
		return  commonDAO.query(Classstructure.class, Arrays.asList(Order.asc("classstructureid")), criterions);
	}
	
	@Transactional(readOnly=true)
    public List<Classstructure> getClassstructureChildList(String classstructureid) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.eq("parent", classstructureid));
		return  commonDAO.query(Classstructure.class, Arrays.asList(Order.asc("classstructureid")), criterions);
	}
	
	@Transactional(readOnly=true)
    public List<Classstructure> getListById(String sStructs1,String sStructs2,String sStructs3,
    		String sStructs4,String description) {
		List criterions = new ArrayList();

		criterions.add(RestrictionsUtils.like("classstructureid", sStructs1,MatchMode.START));
		criterions.add(RestrictionsUtils.like("classstructureid", sStructs2,MatchMode.START));
		criterions.add(RestrictionsUtils.like("classstructureid", sStructs3,MatchMode.START));
		criterions.add(RestrictionsUtils.like("classstructureid", sStructs4,MatchMode.START));

		criterions.add(RestrictionsUtils.like("description", description,MatchMode.ANYWHERE));
		
		return  commonDAO.query(Classstructure.class, Arrays.asList(Order.asc("classstructureid")), criterions);
	}
	@Transactional(readOnly=true)
    public long getClassstructure(String classstructureid,boolean inactiveFlag) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		if(inactiveFlag){
			criterions.add(RestrictionsUtils.disjunction(
					Restrictions.isNull("inactiveDate"),
					RestrictionsUtils.ge("inactiveDate", new Date())));
		}
		return  commonDAO.queryCount(Classstructure.class, criterions);
	}
		
	@Transactional(readOnly=true)
    public Classstructure getClassstructure(String classstructureid) {
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("classstructureid", classstructureid));
		return  (Classstructure) commonDAO.query(Classstructure.class, null, criterions);
	}
	
	/*
	 * hongjie.wu
	 * 2015.03.03
	 * 驗證PRLINE的庫存分類是否與設定檔一致
	 * return 不一致的LIST
	 */
	@Transactional(readOnly=true)
    public List<Classstructure> validatePrlineByStorage(List<Prline> prlines) {
		List criterions = new ArrayList();
		List<Criterion> criterionL = new ArrayList<Criterion>();
		for(Prline prline:prlines){
			criterionL.add(RestrictionsUtils.conjunction(
					Restrictions.eq("classstructureid", prline.getClassstructureid()),
					Restrictions.ne("storeCategory", prline.getStoreCategory())));
		}
		criterions.add(RestrictionsUtils.disjunction(criterionL));
		
		return  commonDAO.query(Classstructure.class, Arrays.asList(Order.asc("classstructureid")), criterions);
	}
}
