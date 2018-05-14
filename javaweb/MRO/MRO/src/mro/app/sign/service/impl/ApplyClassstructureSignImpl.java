package mro.app.sign.service.impl;

import mro.app.sign.bo.ApplyClassstructureSignBo;
import mro.app.sign.service.ApplyClassstructureSignInterface;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.bo.ClassstructureHeaderApplyBO;
import mro.base.entity.Person;
import mro.form.ClassstructureForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ApplyClassstructureSignImpl implements
		ApplyClassstructureSignInterface {
	private transient ClassstructureHeaderApplyBO classstructureHeaderApplyBO;
	
	public ApplyClassstructureSignImpl(){
		classstructureHeaderApplyBO=SpringContextUtil.getBean(ClassstructureHeaderApplyBO.class);
	}

	@Override
	public ClassstructureForm getApplySignList(
			ClassstructureForm classstructureForm, String empNo, int activeI) {
		classstructureForm.intial();
		classstructureForm.setListClassstructureHeaderApply(null);
		classstructureForm.setListClassstructureHeaderApply(
				classstructureHeaderApplyBO.getApplyList(
				SourceCategory.CLASSSTRUCTURE_APPLY_SIGN.getInprgMap(empNo).keySet()));
		classstructureForm.setActiveIndex(activeI);
		return classstructureForm;
	}

	@Override
	public ClassstructureForm setTransferEmployee(
			ClassstructureForm classstructureForm, Person person) {
		classstructureForm.setPersonForward(person.getPersonId() != null ? person
						: null);
		if (classstructureForm.getPersonForward() != null) {
			classstructureForm.setPersonForward(person);
			classstructureForm.setTransferInfo(person.getDisplayName()
					+ " ( " + person.getPersonId() + " )");
		} else {
			classstructureForm.setTransferInfo("");
		}
		return classstructureForm;
	}

	@Override
	public boolean onTransfer(ClassstructureForm classstructureForm,
			String loginEmpNo) {
		ApplyClassstructureSignBo applyClassstructureSignBo = SpringContextUtil
				.getBean(ApplyClassstructureSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (classstructureForm.getPersonForward() != null) {
			applyClassstructureSignBo.onSign(classstructureForm,
					SignStatus.TRANSFER, loginEmpNo);
			message.addInfoMessage("轉呈申請單", "轉呈申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("轉呈失敗", "請填寫轉呈人員!");
		}
		return false;
	}

	@Override
	public boolean onRejectToNew(ClassstructureForm classstructureForm,
			String loginEmpNo) {
		ApplyClassstructureSignBo applyClassstructureSignBo = SpringContextUtil
				.getBean(ApplyClassstructureSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (StringUtils.isNotBlank(classstructureForm.getSignComment())) {
			applyClassstructureSignBo.onSign(classstructureForm,
					SignStatus.REJECT, loginEmpNo);
			message.addInfoMessage("退回申請單", "退回申請單重新修改 成功!");
			return true;
		} else {
			message.addWarnMessage("退回申請單失敗", "退回申請單必須填寫說明!");
		}
		return false;
	}

	@Override
	public boolean onAccept(ClassstructureForm classstructureForm,
			String loginEmpNo) {
		ApplyClassstructureSignBo applyClassstructureSignBo = SpringContextUtil
				.getBean(ApplyClassstructureSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		applyClassstructureSignBo.onSign(classstructureForm, SignStatus.APPR,
				loginEmpNo);
		message.addInfoMessage("簽核申請單", "簽核申請單 成功!");
		return true;
	}


}
