package mro.app.system.service.Impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import mro.app.system.form.UserLocationSiteMapForm;
import mro.app.system.service.UserLocationSiteMapInterfaces;
import mro.base.bo.LocationSiteMapBO;
import mro.base.bo.UserLocationSiteMapBO;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.PFUtils;
import com.inx.commons.util.SpringContextUtil;

public class UserLocationSiteMapImpl implements UserLocationSiteMapInterfaces {

	private transient UserLocationSiteMapBO ulsBO;
	private transient LocationSiteMapBO lsmBO;
	
	public UserLocationSiteMapImpl(){
		ulsBO=SpringContextUtil.getBean(UserLocationSiteMapBO.class);
		lsmBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
	}
	
	@Override
	public void setParameter(UserLocationSiteMapForm form) {
		form.setLocationSiteMapALL(lsmBO.getLocationSiteMapList());
		
	}

	@Override
	public void setEmployee(UserLocationSiteMapForm form, Person person) {
		form.inital();
		if(person!=null){
			form.setPerson(person);
			form.setUserLocationSiteMaps(ulsBO.getList(person.getPersonId()));
			form.setLocationSiteMap(lsmBO.getLocationSiteMapByOrg(person.getOrganizationCode()));
			this.setUserLocationMenu(form);
		}
		
	}

	@Override
	public void update(UserLocationSiteMapForm form,Person login,GlobalGrowl message) {
		if(form.getPerson()!=null){
			ulsBO.update(form.getSites().getTarget(), form.getUserLocationSiteMaps(), 
					form.getPerson(), login);
			this.setEmployee(form, form.getPerson()); //重新載入
			if(message!=null){message.addInfoMessage("Save", "Save successful.");}
		}
	}

	@Override
	public void setUserLocationMenu(UserLocationSiteMapForm form) {
		List<LocationSiteMap> list=form.getUserLocationSiteMaps().stream().map(
				u->u.getLocationSiteMap()).collect(Collectors.toList());
		if(list.indexOf(form.getLocationSiteMap())==-1) list.add(form.getLocationSiteMap());
		form.setSites(PFUtils.getDualListModel(form.getLocationSiteMapALL(),
				new ArrayList(new LinkedHashSet<LocationSiteMap>(list)), false));
		
	}

}
