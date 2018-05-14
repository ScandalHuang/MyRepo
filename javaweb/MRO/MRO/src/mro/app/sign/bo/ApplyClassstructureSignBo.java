package mro.app.sign.bo;

import java.util.Date;

import mro.app.applyItem.dao.ApplyClassstructureDao;
import mro.app.commonview.bo.ListAlndomainBO;
import mro.app.sign.dao.ApplyClassstructureSignDao;
import mro.base.System.config.basicType.ClassstructureApplyType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Alndomain;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.form.ClassstructureForm;
import mro.utility.ExceptionUtils;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class ApplyClassstructureSignBo {

	ApplyClassstructureDao applyClassstructureDao;
	ApplyClassstructureSignDao applyClassstructureSignDao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		applyClassstructureDao = new ApplyClassstructureDao(sessionFactory);
		applyClassstructureSignDao = new ApplyClassstructureSignDao(
				sessionFactory);
	}

	@Transactional(readOnly = false)
	public void onSign(ClassstructureForm classstructureForm, SignStatus action,
			String loginEmpNo) {
		boolean signStaus = false;
		String apprStatus = "";
		if (action.compareTo(SignStatus.REJECT)==0) {
			signStaus = WorkflowActionUtils.onReject(classstructureForm
					.getClassstructureHeaderApply().getTaskId(),
					loginEmpNo, classstructureForm.getSignComment());
		} else if (action.compareTo(SignStatus.TRANSFER)==0) {
			signStaus = WorkflowActionUtils.onTransfer(classstructureForm
					.getClassstructureHeaderApply().getTaskId(),
					loginEmpNo, classstructureForm.getSignComment(),
					classstructureForm.getPersonForward().getPersonId());
		} else if (action.compareTo(SignStatus.APPR)==0) {
			apprStatus = WorkflowActionUtils
					.onAppr(classstructureForm.getClassstructureHeaderApply()
							.getTaskId(), loginEmpNo,
							classstructureForm.getSignComment(), null, null);
			if (StringUtils.isNotBlank(apprStatus)) {
				signStaus = true;
			}
		}

		if (!signStaus) {
			ExceptionUtils.showFalilException(
					classstructureForm.getClassstructureHeaderApply().getApplyHeaderNum(),
					classstructureForm.getClassstructureHeaderApply().getTaskId(),
					"執行程序：" + action);
		} else {
			if (action.compareTo(SignStatus.REJECT)==0) {
				update(classstructureForm.getClassstructureHeaderApply(),
						action);
			} else if (action.compareTo(SignStatus.APPR)==0) {
				if (apprStatus.equals(SignStatus.APPR.toString())) { // Finally Approve
					onFinalAccept(classstructureForm, loginEmpNo);
				}
			}

		}
	}

	@Transactional(readOnly = false)   // 確定同意(最後簽核者)
	public void onFinalAccept(ClassstructureForm classstructureForm,
			String loginEmpNo) {
		setFinalAccept(classstructureForm);
	}
	@Transactional(readOnly = false)
	public void update(ClassstructureHeaderApply classstructureHeaderApply,
			SignStatus status) {
		classstructureHeaderApply.setStatus(status.toString());
		applyClassstructureSignDao.insertUpdate(classstructureHeaderApply);
	}

	@Transactional(readOnly = false)
	public void setFinalAccept(ClassstructureForm classstructureForm) {
		ClassstructureHeaderApply classstructureHeaderApply = classstructureForm
				.getClassstructureHeaderApply();
		ValidationUtils.validateStatus(classstructureHeaderApply.getApplyHeaderNum(),
				classstructureHeaderApply.getTaskId(),ItemStatusType.TYPE_PROCESS_AS);
		classstructureHeaderApply.setStatus(SignStatus.APPR.toString());
		for (ClassstructureLineApply l : classstructureForm
				.getListClassstructureLineApply()) {
			ListAlndomainBO listAlndomainBO = SpringContextUtil
					.getBean(ListAlndomainBO.class);
			Alndomain alndomain = Utility.nvlEntity(l.getAlndomain(), Alndomain.class);
			if (Utility.equals(classstructureHeaderApply.getAction(),
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ADD)) {
				Alndomain maxAlndomain = listAlndomainBO.getMaxAlndomain(
						l.getDomainid(), "");
				l.setValue(Integer.toString(Integer.parseInt(
						maxAlndomain != null ? maxAlndomain.getValue() : "0",
						36) + 1, 36));
				BeanUtils.copyProperties(l, alndomain);
			} else {
				if (Utility.equals(classstructureHeaderApply.getAction(),
						ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE)) {
					l.setInactiveDate(new Date(System.currentTimeMillis()));
				}
				alndomain.setInactiveDate(l.getInactiveDate());
			}
			applyClassstructureSignDao.insertUpdate(alndomain);
			l.setAlndomain(alndomain);
			applyClassstructureSignDao.insertUpdate(l);
		}
		applyClassstructureSignDao.insertUpdate(classstructureHeaderApply);
	}
}
