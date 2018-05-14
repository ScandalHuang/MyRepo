package mro.app.classstructureSign.Service.Impl;

import mro.app.classstructureSign.Service.ListClassstructureItemSiteSignInterface;
import mro.app.classstructureSign.bo.ListClassstructureItemSiteSignBO;
import mro.app.classstructureSign.form.ClassstructureItemSiteSignForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureItemSiteSignImpl implements ListClassstructureItemSiteSignInterface {

	@Override
	public void onSearch(ClassstructureItemSiteSignForm classstructureItemSiteSignForm) {
		ListClassstructureItemSiteSignBO listClassstructureItemSiteSignBO = SpringContextUtil.getBean(ListClassstructureItemSiteSignBO.class);
		if(StringUtils.isNotBlank(classstructureItemSiteSignForm.getSelectClassstructureid()) ||
				StringUtils.isNotBlank(classstructureItemSiteSignForm.getSelectLocationSite()) ){
						classstructureItemSiteSignForm.setListClassstructureItemSiteSign(
								listClassstructureItemSiteSignBO.getClassstructureItemSiteSignList(
										classstructureItemSiteSignForm.getSelectClassstructureid(),
										classstructureItemSiteSignForm.getSelectLocationSite()));
		}else{
			classstructureItemSiteSignForm.intial();
		}
		
	}

	@Override
	public void onDelete(ClassstructureItemSiteSignForm classstructureItemSiteSignForm) {
		ListClassstructureItemSiteSignBO listClassstructureItemSiteSignBO = SpringContextUtil.getBean(ListClassstructureItemSiteSignBO.class);
		listClassstructureItemSiteSignBO.delete(classstructureItemSiteSignForm.getDeleteClassstructureItemSiteSign());
		this.onSearch(classstructureItemSiteSignForm);
	}

	@Override
	public void setParameter(ClassstructureItemSiteSignForm classstructureItemSiteSignForm) {
		ListClassstructureItemSiteSignBO listClassstructureItemSiteSignBO = SpringContextUtil.getBean(ListClassstructureItemSiteSignBO.class);
		classstructureItemSiteSignForm.setClassstructureMap(listClassstructureItemSiteSignBO.getClassstructure(""));
	}


}
