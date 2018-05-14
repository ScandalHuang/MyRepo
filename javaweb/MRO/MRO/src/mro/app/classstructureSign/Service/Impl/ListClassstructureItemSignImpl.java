package mro.app.classstructureSign.Service.Impl;

import mro.app.classstructureSign.Service.ListClassstructureItemSignInterface;
import mro.app.classstructureSign.bo.ListClassstructureItemSignBO;
import mro.app.classstructureSign.form.ClassstructureItemSignForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureItemSignImpl implements ListClassstructureItemSignInterface {

	@Override
	public void onSearch(ClassstructureItemSignForm classstructureItemSignForm) {
		ListClassstructureItemSignBO listClassstructureItemSignBO = SpringContextUtil.getBean(ListClassstructureItemSignBO.class);
		if(StringUtils.isNotBlank(classstructureItemSignForm.getSelectClassstructureid()) ||
				StringUtils.isNotBlank(classstructureItemSignForm.getSelectOrganizationCode()) ){
			classstructureItemSignForm.setListClassstructureItemSign(
					listClassstructureItemSignBO.getClassstructureItemSignList(
							classstructureItemSignForm.getSelectClassstructureid(),
							classstructureItemSignForm.getSelectOrganizationCode()));
		}else{
			classstructureItemSignForm.intial();
		}
		
	}

	@Override
	public void onDelete(ClassstructureItemSignForm classstructureItemSignForm) {
		ListClassstructureItemSignBO listClassstructureItemSignBO = SpringContextUtil.getBean(ListClassstructureItemSignBO.class);
		listClassstructureItemSignBO.delete(classstructureItemSignForm.getDeleteClassstructureItemSign());
		this.onSearch(classstructureItemSignForm);
	}

	@Override
	public void setParameter(ClassstructureItemSignForm classstructureItemSignForm) {
		ListClassstructureItemSignBO listClassstructureItemSignBO = SpringContextUtil.getBean(ListClassstructureItemSignBO.class);
		classstructureItemSignForm.setClassstructureMap(listClassstructureItemSignBO.getClassstructure(""));
		classstructureItemSignForm.setOrganizationCodeMap(listClassstructureItemSignBO.getOrganizationCode(""));
	}


}
