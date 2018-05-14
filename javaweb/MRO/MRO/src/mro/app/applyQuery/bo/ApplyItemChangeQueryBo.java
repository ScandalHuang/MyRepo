package mro.app.applyQuery.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mro.app.applyQuery.dao.ApplyItemChangeQueryDao;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ApplyItemChangeQueryBo {

	ApplyItemChangeQueryDao applyItemChangeQueryDao;
	
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	applyItemChangeQueryDao=new ApplyItemChangeQueryDao(sessionFactory);
    }
	
	@Transactional(readOnly=true)
    public List<AItem> getAItemSignList(	 String itemnum,
	 Date beginDate,
	 Date endDate,
	 String status,
	 String deptCode,
	 String changeby,String selectItemCategory) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer str=new StringBuffer();
		if(StringUtils.isNotBlank(itemnum)){
			str.append("and (a.itemnum like '"+itemnum.toUpperCase()+"%' ");
			str.append("or a.oriitemnum like '"+itemnum.toUpperCase()+"%' ");
			str.append("or a.DESCRIPTION LIKE '%"+itemnum.toUpperCase()+"%') ");
			}
		if(StringUtils.isNotBlank(status)){
			str.append("and a.STATUS ='"+status+"' ");}
		
		if(StringUtils.isNotBlank(changeby)){
			str.append("and a.changeby ='"+changeby+"' ");
		}
		if(StringUtils.isNotBlank(selectItemCategory)){
			str.append("and a.COMMODITYGROUP ='"+selectItemCategory+"' ");
		}
		if(StringUtils.isNotBlank(deptCode)){
			str.append("and b.dept_Code LIKE '"+deptCode.toUpperCase()+"%' ");}
/*		if(StringUtils.isNotBlank(classstructureid)){
			str.append("and a.classstructureid LIKE '"+classstructureid.toUpperCase()+"%' ");}*/
		
		if(beginDate!=null){
			str.append("and trunc(a.EAUDITTIMESTAMP) >= to_date('"+sdf.format(beginDate)+"','yyyy/mm/dd') ");}
		if(endDate!=null){
				str.append("and trunc(a.EAUDITTIMESTAMP) <= to_date('"+sdf.format(endDate)+"','yyyy/mm/dd') ");}
			
			str.append("and a.STATUS !='"+SignStatus.NEW+"' ");
			str.append("and a.ORIITEMNUM is not null ");
		
	   return applyItemChangeQueryDao.getAItemList(str.toString());
	}
}
