package mro.app.mcMgmtInterface.service.Impl;

import mro.app.mcMgmtInterface.bo.ListClassstructureUnitBO;
import mro.app.mcMgmtInterface.form.ClassstructureUnitForm;
import mro.app.mcMgmtInterface.service.ListClassstructureUnitInterface;

import com.inx.commons.util.SpringContextUtil;

public class ListClassstructureUnitImpl implements ListClassstructureUnitInterface {

	@Override
	public ClassstructureUnitForm onSearch(ClassstructureUnitForm classstructureUnitForm) {
		ListClassstructureUnitBO listClassstructureUnitBO = SpringContextUtil.getBean(ListClassstructureUnitBO.class);
		classstructureUnitForm.intial();
		classstructureUnitForm.setListClassstructureUnit(
					listClassstructureUnitBO.getClassstructureUnitList(
					new String[]{"classstructureid"},classstructureUnitForm.getSelectclassstructureid()));
		classstructureUnitForm.copyList();
		return classstructureUnitForm;
	}

	@Override
	public ClassstructureUnitForm onDelete(ClassstructureUnitForm classstructureUnitForm) {
		ListClassstructureUnitBO listClassstructureUnitBO = SpringContextUtil.getBean(ListClassstructureUnitBO.class);
		listClassstructureUnitBO.delete(classstructureUnitForm.getDeleteClassstructureUnit());
		return onSearch(classstructureUnitForm);
	}

	@Override
	public ClassstructureUnitForm setParameter(
			ClassstructureUnitForm classstructureUnitForm) {
		// TODO Auto-generated method stub
		return null;
	}


}
