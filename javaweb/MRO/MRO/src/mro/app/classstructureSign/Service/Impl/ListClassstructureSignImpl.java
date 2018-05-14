package mro.app.classstructureSign.Service.Impl;

import mro.app.classstructureSign.Service.ListClassstructureSignInterface;
import mro.app.classstructureSign.bo.ListClassstructureItemSignBO;
import mro.app.classstructureSign.bo.ListClassstructureSignBO;
import mro.app.classstructureSign.form.ClassstructureSignForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureSignImpl implements ListClassstructureSignInterface {

	@Override
	public void onSearch(ClassstructureSignForm classstructureSignForm) {
		
		ListClassstructureSignBO listClassstructureSignBO = SpringContextUtil.getBean(ListClassstructureSignBO.class);
		if(StringUtils.isNotBlank(classstructureSignForm.getSelectClassstructureid()) ||
				StringUtils.isNotBlank(classstructureSignForm.getSelectOrganizationCode()) ){classstructureSignForm.setListClassstructureSign(
						listClassstructureSignBO.getClassstructureSignList(
								classstructureSignForm.getSelectClassstructureid(),classstructureSignForm.getSelectOrganizationCode()));
		}else{
			classstructureSignForm.intial();
		}
		
	}

	@Override
	public void onDelete(ClassstructureSignForm classstructureSignForm){
		ListClassstructureSignBO listClassstructureSignBO = SpringContextUtil.getBean(ListClassstructureSignBO.class);
		listClassstructureSignBO.delete(classstructureSignForm.getDeleteClassstructureSign());
		this.onSearch(classstructureSignForm);
	}

	@Override
	public void setParameter(ClassstructureSignForm classstructureSignForm){
		ListClassstructureItemSignBO listClassstructureItemSignBO = SpringContextUtil
				.getBean(ListClassstructureItemSignBO.class);
		ListClassstructureSignBO listClassstructureSignBO = SpringContextUtil
				.getBean(ListClassstructureSignBO.class);
		classstructureSignForm.setClassstructureMap(listClassstructureItemSignBO.getClassstructure(""));
		classstructureSignForm.setOrganizationCodeMap(listClassstructureSignBO.getOrganizationCode(""));
	}


}
