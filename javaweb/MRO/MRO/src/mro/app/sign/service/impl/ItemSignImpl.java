package mro.app.sign.service.impl;

import java.util.ArrayList;
import java.util.List;

import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.sign.bo.ApplyItemSignBo;
import mro.app.sign.service.ItemSignInterface;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PersonBO;
import mro.base.entity.LocationMap;
import mro.base.entity.Person;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.form.ItemForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ItemSignImpl implements ItemSignInterface {
	public ItemSignImpl() {

	}

	@Override
	public boolean onTransfer(ItemForm itemForm, String loginEmpNo) {
		ApplyItemSignBo applyItemSignBo = SpringContextUtil
				.getBean(ApplyItemSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (itemForm.getPersonForward() != null
				&& StringUtils.isNotBlank(itemForm.getPersonForward()
						.getPersonId())) {
			applyItemSignBo.onSign(itemForm, SignStatus.TRANSFER, loginEmpNo);
			message.addInfoMessage("轉呈申請單", "轉呈申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("轉呈失敗", "請填寫轉呈人員!");
		}
		return false;
	}

	@Override
	public boolean onRejectToNew(ItemForm itemForm, String loginEmpNo) {
		ApplyItemSignBo applyItemSignBo = SpringContextUtil
				.getBean(ApplyItemSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (StringUtils.isNotBlank(itemForm.getSignComment())) {
			applyItemSignBo.onSign(itemForm, SignStatus.REJECT, loginEmpNo);
			message.addInfoMessage("退回申請單", "退回申請單重新修改 成功!");
			return true;
		} else {
			message.addWarnMessage("退回申請單失敗", "退回申請單必須填寫說明!");
		}
		return false;
	}
	
	@Override
	public boolean onAccept(ItemForm itemForm, String loginEmpNo) {
		ApplyItemSignBo applyItemSignBo = SpringContextUtil.getBean(ApplyItemSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (!itemForm.getaItem().getCommoditygroup().equals(ItemType.R94.toString())) { // 94不驗證
			String warnMessage = ApplyUtils.validateFinallyAppr(itemForm.getaItem());
			if (warnMessage.length() > 0) {
				message.addErrorMessage("ERROR", warnMessage);
				return false;
			}
		}
		applyItemSignBo.onSign(itemForm, SignStatus.APPR, loginEmpNo);
		message.addInfoMessage("簽核申請單", "簽核申請單 成功!");
		return true;
	}

	@Override
	public ItemForm setTransferEmployee(ItemForm itemForm,
			Person person) {
		itemForm.setPersonForward(person.getPersonId() != null ? person
				: null);
		if (itemForm.getPersonForward() != null) {
			itemForm.setPersonForward(person);
			itemForm.setTransferInfo(person.getDisplayName() + " ( "
					+ person.getPersonId() + " )");
		} else {
			itemForm.setTransferInfo("");
		}
		return itemForm;
	}

	@Override
	public void onFinalApprNoSign(ItemForm itemForm) {   //直接finally appr 不需簽核
		ApplyItemSignBo applyItemSignBo = SpringContextUtil.getBean(ApplyItemSignBo.class);
		applyItemSignBo.onFinalAccept(itemForm,applyItemSignBo.getOriItemnum(itemForm));
		
	}
}
