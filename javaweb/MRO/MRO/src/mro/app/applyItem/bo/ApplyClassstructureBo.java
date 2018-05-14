package mro.app.applyItem.bo;

import java.util.Date;

import mro.app.applyItem.dao.ApplyClassstructureDao;
import mro.app.util.StringUtilsConvert;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.form.ClassstructureForm;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("prototype")
public class ApplyClassstructureBo {

	private ApplyClassstructureDao applyClassstructureDao;
	private Object itemForm;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		applyClassstructureDao = new ApplyClassstructureDao(sessionFactory);
	}

	@Transactional(readOnly = false)
	public void onDelteApply(ClassstructureHeaderApply classstructureHeaderApply) {
		applyClassstructureDao.onDeleteApplyLine(classstructureHeaderApply
				.getApplyHeaderId());
		applyClassstructureDao.delete(classstructureHeaderApply);

	}

	@Transactional(readOnly = true)
	public String getHeaderNum() {
		return applyClassstructureDao.getHeaderNum();
	}

	@Transactional(readOnly = false)
	public void onApplySave(ClassstructureForm classstructureForm, String type) {
		if (classstructureForm.getClassstructureHeaderApply()
				.getApplyHeaderNum() == null) { // 第一次儲存
			classstructureForm.getClassstructureHeaderApply()
					.setApplyHeaderNum(this.getHeaderNum());// 申請單編號
			classstructureForm.getClassstructureHeaderApply().setCreateDate(
					new Date(System.currentTimeMillis()));
		} else { // 驗證申請單是否在流程中
			ValidationUtils.validateStatus(
					classstructureForm.getClassstructureHeaderApply().getApplyHeaderNum(),
					classstructureForm.getClassstructureHeaderApply().getTaskId(),
					ItemStatusType.TYPE_PROCESS_AIS);
		}
		if (type.equals("submit")) {
			classstructureForm.getClassstructureHeaderApply().setStatus(
					SignStatus.INPRG.toString());
		}

		classstructureForm.getClassstructureHeaderApply().setLastUpdateDate(
				new Date(System.currentTimeMillis()));
		applyClassstructureDao.insertUpdate(classstructureForm
				.getClassstructureHeaderApply());
		// ==========刪除line=======
		applyClassstructureDao.onDeleteApplyLine(classstructureForm
				.getClassstructureHeaderApply().getApplyHeaderId());
		// ==========新增/更新line=======
		for (ClassstructureLineApply i : classstructureForm
				.getListClassstructureLineApply()) {
			i.setApplyLineId(null);
			if(StringUtils.isNotBlank(i.getDomainid())){
				i.setAssetattrid(i.getDomainid().substring(
					i.getDomainid().length() - 4, i.getDomainid().length()));
			}
			//全型轉半型
			i.setValue(StringUtilsConvert.convertToHalfWidth(i.getValue()));
			i.setClassstructureHeaderApply(classstructureForm
					.getClassstructureHeaderApply());
			i.setClassstructureid(classstructureForm
					.getClassstructureHeaderApply().getClassstructureid());
			i.setCreateBy(classstructureForm.getClassstructureHeaderApply()
					.getCreateBy());
			i.setCreateDate(classstructureForm.getClassstructureHeaderApply()
					.getCreateDate());
			applyClassstructureDao.insertUpdate(i);
		}

	}
}
