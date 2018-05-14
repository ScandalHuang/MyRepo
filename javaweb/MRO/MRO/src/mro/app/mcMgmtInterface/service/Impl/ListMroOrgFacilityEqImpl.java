package mro.app.mcMgmtInterface.service.Impl;

import mro.app.mcMgmtInterface.form.MroOrgFacilityEqForm;
import mro.app.mcMgmtInterface.service.ListMroOrgFacilityEqInterface;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.MroOrgFacilityEqBO;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

public class ListMroOrgFacilityEqImpl implements ListMroOrgFacilityEqInterface {
	@Override
	public MroOrgFacilityEqForm onSearch(MroOrgFacilityEqForm mroOrgFacilityEqForm) {
		MroOrgFacilityEqBO bo = SpringContextUtil.getBean(MroOrgFacilityEqBO.class);
		mroOrgFacilityEqForm.intial();
		if(StringUtils.isNotBlank(mroOrgFacilityEqForm.getSelectReason()) &&
				mroOrgFacilityEqForm.getsLocationSiteMap() != null){
			mroOrgFacilityEqForm.setListMroOrgFacilityEq(bo.getList(
					mroOrgFacilityEqForm.getsLocationSiteMap(),
					mroOrgFacilityEqForm.getSelectReason(),
					mroOrgFacilityEqForm.getSelectDeptNo()));
		}
		mroOrgFacilityEqForm.copyList();
		return mroOrgFacilityEqForm;
	}
	@Override
	public MroOrgFacilityEqForm onDelete(MroOrgFacilityEqForm mroOrgFacilityEqForm) {
		MroOrgFacilityEqBO bo = SpringContextUtil.getBean(MroOrgFacilityEqBO.class);
		bo.delete(mroOrgFacilityEqForm.getDeleteMroOrgFacilityEq());
		return onSearch(mroOrgFacilityEqForm);
	}
	
	@Override
	public MroOrgFacilityEqForm setParameter(MroOrgFacilityEqForm mroOrgFacilityEqForm) {
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		mroOrgFacilityEqForm.setReasonOption(bean.getParameterOption().get(
				ParameterType.R2_PMREQ_REASONCODE));
		return mroOrgFacilityEqForm;
	}

}
