package mro.base.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mro.app.mcMgmtInterface.form.LampControlForm;
import mro.base.entity.Item;
import mro.base.entity.LampControlHeader;
import mro.base.entity.Person;
import mro.utility.DateUtils;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.ActiveFlag;
import com.inx.commons.util.hibernate.CtriteriaFetchMode;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class LampControlHeaderBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	@Transactional(readOnly = false)
	public LampControlHeader onAdd(LampControlHeader header,LampControlForm form,Item item,Person person){
		Date date=new Date(System.currentTimeMillis());
		header.setItem(item);
		header.setItemnum(item.getItemnum());
		header.setCreateBy(person.getPersonId());
		header.setCreateDate(date);
		header.setOrganizationCode(form.getOrganizationCode());
		header.setDeptCode(form.getDeptCode());
		header.setLastUpdateBy(person.getPersonId());
		header.setLastUpdate(date);
		header.setDeleted(null);
		commonDAO.insertUpdate(header);
		return header;
		
	}	@Transactional(readOnly = true)
	public LampControlHeader getList(String itemnum,String organizationCode,
			String deptCode,ActiveFlag activeFlag) {
		List criterions = new ArrayList();
		
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("organizationCode", organizationCode));
		criterions.add(RestrictionsUtils.eq("deptCode", deptCode));
		if(activeFlag!=null){
			if(activeFlag.compareTo(ActiveFlag.Y)==0){
				criterions.add(RestrictionsUtils.disjunction(
						Restrictions.isNull("deleted"),
						RestrictionsUtils.eq("deleted", "Y")));
			}else if(activeFlag.compareTo(ActiveFlag.N)==0){
				criterions.add(RestrictionsUtils.eq("deleted", "N"));
			}
		}
		
		return (LampControlHeader) commonDAO.uniQuery(LampControlHeader.class,null, criterions);
	}
	@Transactional(readOnly = true)
	public List<LampControlHeader> getListALL(String itemnum,String organizationCode,
			String deptCode,Date createDate,ActiveFlag activeFlag,int rage) {
		List criterions = new ArrayList();
		
		List<LampControlHeader> listALL=new ArrayList<LampControlHeader>(); //不包含join
		List<LampControlHeader> list=new ArrayList<LampControlHeader>(); //包含join
		
		criterions.add(RestrictionsUtils.eq("itemnum", itemnum));
		criterions.add(RestrictionsUtils.eq("organizationCode", organizationCode));
		criterions.add(RestrictionsUtils.eq("deptCode", deptCode));
		
		if(activeFlag!=null){
			if(activeFlag.compareTo(ActiveFlag.Y)==0){
				criterions.add(RestrictionsUtils.disjunction(
						Restrictions.isNull("deleted"),
						RestrictionsUtils.eq("deleted", "N")));
			}else if(activeFlag.compareTo(ActiveFlag.N)==0){
				criterions.add(RestrictionsUtils.eq("deleted", "Y"));
			}
		}
		//====================不包含join============================
		listALL=commonDAO.query(LampControlHeader.class, 
				Arrays.asList(Order.asc("organizationCode"),Order.asc("itemnum"),
						Order.asc("deptCode")), criterions);
		
		//===========================================================
		if(createDate!=null){
			criterions.add(RestrictionsUtils.ge("lampControlLines.controlDateStart", 
					DateUtils.getFirstDayOfWeek(DateUtils.getOnlyDate(createDate))));
			criterions.add(RestrictionsUtils.le("lampControlLines.controlDateStart", 
					DateUtils.getFirstDayOfWeek(DateUtils.getAddDate(
							DateUtils.getOnlyDate(createDate),7*(rage-1)))));
		}
		//====================包含join============================
		list=commonDAO.query(LampControlHeader.class, 
				Arrays.asList(Order.asc("organizationCode"),Order.asc("itemnum"),
						Order.asc("deptCode"),
						Order.asc("lampControlLines.controlDateStart")), criterions,
				new CtriteriaFetchMode("lampControlLines",Criteria.LEFT_JOIN));
		
		for(LampControlHeader l:listALL){
			if(list.contains(l)){
				l=list.get(list.indexOf(l));
			}else{
				l.setLampControlLines(null);
			}
		}
		return listALL;
	}
}
