package mro.app.sign.service.impl;

import mro.app.sign.bo.ApplyItemTransferSiteSignBo;
import mro.app.sign.service.ItemTransferSiteSignInterface;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Person;
import mro.form.ItemTransferSiteForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ItemTransferSiteSignImpl implements ItemTransferSiteSignInterface {

	@Override
	public boolean onTransfer(ItemTransferSiteForm itemTransferSiteForm,
			String loginEmpNo) {
		ApplyItemTransferSiteSignBo applyItemTransferSiteSignBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (itemTransferSiteForm.getPersonForward() != null) {
			applyItemTransferSiteSignBo.onSign(itemTransferSiteForm, SignStatus.TRANSFER, loginEmpNo);
			message.addInfoMessage("轉呈申請單", "轉呈申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("轉呈失敗", "請填寫轉呈人員!");
		}
		return false;
	}

	@Override
	public boolean onRejectToNew(ItemTransferSiteForm itemTransferSiteForm,
			String loginEmpNo) {
		ApplyItemTransferSiteSignBo applyItemTransferSiteSignBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		if (StringUtils.isNotBlank(itemTransferSiteForm.getSignComment())) {

			applyItemTransferSiteSignBo.onSign(itemTransferSiteForm, SignStatus.REJECT, loginEmpNo);
			message.addInfoMessage("退回申請單", "退回申請單重新修改 成功!");
			return true;
		} else {
			message.addWarnMessage("退回申請單失敗", "退回申請單必須填寫說明!");
		}
		return false;
	}


	@Override
	public boolean onAccept(ItemTransferSiteForm itemTransferSiteForm,
			String loginEmpNo) {
		ApplyItemTransferSiteSignBo applyItemTransferSiteSignBo = SpringContextUtil
				.getBean(ApplyItemTransferSiteSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		applyItemTransferSiteSignBo.onSign(itemTransferSiteForm, SignStatus.APPR, loginEmpNo);
		message.addInfoMessage("簽核申請單", "簽核申請單 成功!");
		return true;
	}

	@Override
	public ItemTransferSiteForm setTransferEmployee(
			ItemTransferSiteForm itemTransferSiteForm,
			Person person) {
		itemTransferSiteForm.setPersonForward(person.getPersonId()!=null?person:null);
		if(itemTransferSiteForm.getPersonForward()!=null){
			itemTransferSiteForm.setPersonForward(person);
			itemTransferSiteForm.setTransferInfo(person.getDisplayName() + " ( "
					+ person.getPersonId() + " )");
		}else{
			itemTransferSiteForm.setTransferInfo("");
		}
		return itemTransferSiteForm;
	}

}
