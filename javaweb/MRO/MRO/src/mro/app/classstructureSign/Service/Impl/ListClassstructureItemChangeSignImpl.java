package mro.app.classstructureSign.Service.Impl;

import mro.app.classstructureSign.Service.ListClassstructureItemChangeSignInterface;
import mro.app.classstructureSign.bo.ListClassstructureItemChangeSignBO;
import mro.app.classstructureSign.form.ClassstructureItemChangeSignForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureItemChangeSignImpl implements ListClassstructureItemChangeSignInterface {

	@Override
	public void onSearch(ClassstructureItemChangeSignForm classstructureItemChangeSignForm) {
		ListClassstructureItemChangeSignBO listClassstructureItemChangeSignBO = SpringContextUtil.getBean(ListClassstructureItemChangeSignBO.class);
		if(StringUtils.isNotBlank(classstructureItemChangeSignForm.getSelectClassstructureid()) ||
				StringUtils.isNotBlank(classstructureItemChangeSignForm.getSelectOrganizationCode()) ){
			classstructureItemChangeSignForm.setListClassstructureItemchangeSign(
					listClassstructureItemChangeSignBO.getClassstructureItemchangeSignList(
							classstructureItemChangeSignForm.getSelectClassstructureid(),
							classstructureItemChangeSignForm.getSelectOrganizationCode()));
		}else{
			classstructureItemChangeSignForm.intial();
		}
		
	}

	@Override
	public void onDelete(ClassstructureItemChangeSignForm classstructureItemChangeSignForm) {
		ListClassstructureItemChangeSignBO listClassstructureItemChangeSignBO = SpringContextUtil.getBean(ListClassstructureItemChangeSignBO.class);
		listClassstructureItemChangeSignBO.delete(classstructureItemChangeSignForm.getDeleteClassstructureItemchangeSign());
		this.onSearch(classstructureItemChangeSignForm);
	}

	@Override
	public void setParameter(ClassstructureItemChangeSignForm classstructureItemChangeSignForm) {
		ListClassstructureItemChangeSignBO listClassstructureItemChangeSignBO = SpringContextUtil.getBean(ListClassstructureItemChangeSignBO.class);
		classstructureItemChangeSignForm.setClassstructureMap(listClassstructureItemChangeSignBO.getClassstructure(""));
		classstructureItemChangeSignForm.setOrganizationCodeMap(listClassstructureItemChangeSignBO.getOrganizationCode(""));
	}


}
