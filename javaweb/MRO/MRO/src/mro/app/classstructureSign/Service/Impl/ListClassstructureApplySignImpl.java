package mro.app.classstructureSign.Service.Impl;

import mro.app.classstructureSign.Service.ListClassstructureApplySignInterface;
import mro.app.classstructureSign.bo.ListClassstructureApplySignBO;
import mro.app.classstructureSign.form.ClassstructureApplySignForm;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureApplySignImpl implements ListClassstructureApplySignInterface {

	@Override
	public ClassstructureApplySignForm onSearch(ClassstructureApplySignForm cassstructureApplySignForm) {
		ListClassstructureApplySignBO listClassstructureApplySignBO = SpringContextUtil.getBean(ListClassstructureApplySignBO.class);
		cassstructureApplySignForm.intial();
		cassstructureApplySignForm.setListClassstructureApplySign(
				listClassstructureApplySignBO.getClassstructureApplySignList(
					new String[]{"classstructureid"},cassstructureApplySignForm.getSelectclassstructureid()));
		cassstructureApplySignForm.copyList();
		return cassstructureApplySignForm;
	}

	@Override
	public ClassstructureApplySignForm onDelete(ClassstructureApplySignForm classstructureApplySignForm){
		ListClassstructureApplySignBO listClassstructureApplySignBO = SpringContextUtil.getBean(ListClassstructureApplySignBO.class);
		listClassstructureApplySignBO.delete(classstructureApplySignForm.getDeleteClassstructureApplySign());
		return onSearch(classstructureApplySignForm);
	}

	@Override
	public ClassstructureApplySignForm setParameter(
			ClassstructureApplySignForm classstructureApplySignForm) {
		// TODO Auto-generated method stub
		return null;
	}


}
