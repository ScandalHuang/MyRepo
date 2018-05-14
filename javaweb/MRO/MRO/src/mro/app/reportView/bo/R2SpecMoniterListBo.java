package mro.app.reportView.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.reportView.dao.R2SpecMoniterListDao;

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
public class R2SpecMoniterListBo {

    private R2SpecMoniterListDao dao;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	dao=new R2SpecMoniterListDao(sessionFactory);
    }

	@Transactional(readOnly=true)
    public List getList(String oldMatnr,String newMatnr ,String assetattrid,
    		String aFlag,String supplierFlag) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashedMap();
		
		if(StringUtils.isNotBlank(oldMatnr)){
			condition.append("AND  \"舊料號\" like :oldMatnr ");
			param.put("oldMatnr", oldMatnr+"%");
		}
		if(StringUtils.isNotBlank(newMatnr)){
			condition.append("AND \"新料號\"   like :newMatnr ");
			param.put("newMatnr", newMatnr+"%");
		}
		if(StringUtils.isNotBlank(assetattrid)){
			condition.append("AND \"屬性\"  = :assetattrid ");
			param.put("assetattrid", assetattrid);
		}
		if(StringUtils.isNotBlank(aFlag)){
			condition.append("AND (CASE WHEN NVL(\"新料號數值\",'NULL')="
					+ "NVL(\"舊料號數值\",'NULL') THEN 'N' ELSE 'Y' END)=:aFlag ");
			param.put("aFlag", aFlag);
		}
		if(StringUtils.isNotBlank(supplierFlag)){
			if(supplierFlag.toUpperCase().indexOf("NULL")!=-1)
				condition.append("AND \"供應商回補規格(Y/N)\"  is null ");
			else
				condition.append("AND \"供應商回補規格(Y/N)\"  = :supplierFlag ");
			param.put("supplierFlag", supplierFlag);
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
