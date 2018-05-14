package mro.app.reportView.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.reportView.dao.BuyerItemSupplierListDao;
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
public class BuyerItemSupplierListBo {

    private BuyerItemSupplierListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new BuyerItemSupplierListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String itemtype,String itemnum,String description,
    		String classstructureid,String vendor,Date cStrDate,
    		Date cEndDate,Date uStrDate,Date uEndDate){
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		if(StringUtils.isNotBlank(itemtype)){
			condition.append("AND I.ITEMNUM  like :itemtype ");
			param.put("itemtype", itemtype+"%");
		}
		if(StringUtils.isNotBlank(itemnum)){
			condition.append("AND I.ITEMNUM  like :itemnum ");
			param.put("itemnum", itemnum+"%");
		}
		if(StringUtils.isNotBlank(description)){
			condition.append("AND I.DESCRIPTION  like :description ");
			param.put("description", description+"%");
		}
		if(StringUtils.isNotBlank(classstructureid)){
			condition.append("AND I.CLASSSTRUCTUREID  like :classstructureid ");
			param.put("classstructureid", classstructureid+"%");
		}

		if(StringUtils.isNotBlank(vendor)){
			condition.append("AND exists (select 1 from vendorAll where i.itemnum=vendorAll.itemnum "
					+ "and NEWVENDORNAME like :vendor) ");
			param.put("vendor", "%"+vendor+"%");
		}
		if(cStrDate!=null){
			condition.append("AND  I.CREATE_DATE >= :cStrDate ");
			param.put("cStrDate", cStrDate);
		}
		if(cEndDate!=null){
			condition.append("AND  I.CREATE_DATE < :cEndDate ");
			param.put("cEndDate", DateUtils.getAddDate(cEndDate,1));
		}
		if(uStrDate!=null){
			condition.append("AND I.CHANGE_LAST_UPDATE >= :uStrDate ");
			param.put("uStrDate", uStrDate);
		}
		if(uEndDate!=null){
			condition.append("AND  I.CREATE_DATE  < :uEndDate ");
			param.put("uEndDate", DateUtils.getAddDate(uEndDate,1));
		}
	   return dao.getList(condition.toString(),param);
	}

	@Transactional(readOnly = true)
	public Map getOption() {
		List<Object[]> list = dao.getOption();
		Map option=new LinkedHashMap();
		list.stream().forEach(o->option.put(o[0], o[1]));
		return option;
	}
}
