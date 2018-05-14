package mro.app.sign.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.applyItem.service.impl.PrImpl;
import mro.app.sign.bo.ApplyItemPrSignBo;
import mro.app.sign.bo.ApplyItemSignBo;
import mro.app.sign.service.PrSignInterface;
import mro.app.util.HrEmpUtils;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.PrLineType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.bo.PrBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.PrForm;

import org.apache.commons.lang.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class PrSignImpl implements PrSignInterface {
	public PrSignImpl() {

	}

	@Override
	public boolean onTransfer(PrForm prForm, String loginEmpNo) {
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.TRANSFER, new StringBuffer());
		if (warnMessage.length() == 0) {
			applyItemPrSignBo.onSign(prForm, SignStatus.TRANSFER,
					loginEmpNo);
			message.addInfoMessage("轉呈申請單", "轉呈申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("轉呈失敗", warnMessage.toString());
		}
		return false;
	}

	@Override
	public boolean onRejectToNew(PrForm prForm, String loginEmpNo) {
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.REJECT, new StringBuffer());
		if (warnMessage.length() == 0) {
			applyItemPrSignBo.onSign(prForm, SignStatus.REJECT, loginEmpNo);
			message.addInfoMessage("退回申請單", "退回申請單重新修改 成功!");
			return true;
		} else {
			message.addWarnMessage("退回申請單失敗", warnMessage.toString());
		}
		return false;
	}

	@Override
	public boolean onCanSign(PrForm prForm, String loginEmpNo) { // 簽核通過後,在轉單時取消該申請單
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.CAN, new StringBuffer());
		if (warnMessage.length() == 0) {
			applyItemPrSignBo.onCanSign(prForm, loginEmpNo);
			message.addInfoMessage("取消申請單", "取消申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("取消申請單失敗", warnMessage.toString());
		}
		return false;
	}

	@Override
	public boolean onCan(PrForm prForm, String loginEmpNo) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.CAN, new StringBuffer());
		if (warnMessage.length() == 0) {
			applyItemPrSignBo.onSign(prForm, SignStatus.CAN, loginEmpNo);
			message.addInfoMessage("取消申請單", "取消申請單 成功!");
			return true;
		} else {
			message.addWarnMessage("取消申請單失敗", warnMessage.toString());
		}
		return false;
	}

	@Override
	public boolean onAccept(PrForm prForm, String loginEmpNo) {
		GlobalGrowl message = new GlobalGrowl();
		PrlineBO prlineBO=SpringContextUtil.getBean(PrlineBO.class);
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);

		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.APPR, new StringBuffer());
		if (warnMessage.length() == 0) {
			// =============================PRLine
			// 是否有全部通過============================================
			String prlineStatus = "PR LINE狀態：";
			if (prForm.getListDeletePrline().size() > 0
					|| prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.CLOSE)>0
					|| prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.COMB)>0){
				prlineStatus = prlineStatus + PrLineType.PART.getValue();
			} else {
				prlineStatus = prlineStatus+PrLineType.ALL.getValue();
			}
			if (prForm.getListDeletePrline().size() > 0			
					|| prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.COMB)>0){
				prlineStatus = prlineStatus 
						+" (此申請單內含與物控廠區訂單合併入貨之料號，請務必至MRO 系統確認入貨廠區及入貨日期，該物料入貨當週無領出，視同取消需求) ";
			}
			prForm.setMailContent(prlineStatus);
			applyItemPrSignBo.onSign(prForm, SignStatus.APPR, loginEmpNo);

			message.addInfoMessage("簽核申請單", prForm.getPr().getPrnum()
					+ "簽核申請單 成功!");

			return true;
		} else {
			message.addWarnMessage("簽核錯誤", warnMessage.toString());
		}
		return false;
	}

	@Override
	public PrForm setTransferEmployee(PrForm prForm, Person person) {
		prForm.setPersonForward(person.getPersonId() != null ? person : null);
		if (prForm.getPersonForward() != null) {
			prForm.setPersonForward(person);
			prForm.setTransferInfo(person.getDisplayName() + " ( "
					+ person.getPersonId() + " )");
		} else {
			prForm.setTransferInfo("");
		}
		return prForm;
	}

	@Override
	public boolean onMultiAppr(PrForm prForm, String loginEmpNo) {
		GlobalGrowl message = new GlobalGrowl();
		if (prForm.getPrs().length > 0) {
			for (Pr p : prForm.getPrs()) {
				PrImpl prImpl = new PrImpl();
				prForm = prImpl.selectApply(prForm, p);
				onAccept(prForm, loginEmpNo);
			}
			return true;
		} else {
			message.addWarnMessage("Warn", "請選取申請單!");
		}
		return false;
	}

	@Override
	public PrForm setlistUnSignPr(PrForm prForm, String empNo) {
		PrBO prBO = SpringContextUtil.getBean(PrBO.class);
		prForm.setListPr(prBO.getPrListByTaskId(
				SourceCategory.getInprgMap(
						PrType.valueOf(prForm.getSelectPrtype()), empNo).keySet(),
						prForm.getSelectPrtype(),StringUtils.split(prForm.getPrnum(),","),
						prForm.getRequestby2(),prForm.getMDept()));
		prForm.setRequestedby2NameMap(requestedby2NameMap(prForm));
		return prForm;
	}
	
	@Override
	public Map<String, String> requestedby2NameMap(PrForm prForm) {
		if(prForm.getListPr()==null){return null;}
		List<String> actors=new ArrayList<String>();
		for(Pr pr:prForm.getListPr()){
			actors.add(pr.getRequestedby2());
		}
		return HrEmpUtils.getMapList(actors);
	}


	@Override
	public boolean onClosePrLine(PrForm prForm) {
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.APPR, new StringBuffer());
		if (warnMessage.length() == 0) {
			ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
			applyItemPrSignBo.onClosePrLine(prForm);
			message.addInfoMessage("申請單更新成功", prForm.getPr().getPrnum()
					+ "更新成功!");
			return true;
		} else {
			message.addErrorMessage("申請單更新失敗", warnMessage.toString());
		}
		return false;
	}
	
	@Override
	public boolean onCombinePrLine(PrForm prForm) {
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer warnMessage = ApplyPrUtils.prSignValidation(prForm,
				SignStatus.APPR, new StringBuffer());
		if (warnMessage.length() == 0) {
			ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
			applyItemPrSignBo.onCombinePrLine(prForm);
			message.addInfoMessage("申請單更新成功", prForm.getPr().getPrnum()
					+ "更新成功!");
			return true;
		} else {
			message.addErrorMessage("申請單更新失敗", warnMessage.toString());
		}
		return false;
	}

	@Override
	public void onFinalApprNoSign(PrForm prForm) {
		ApplyItemPrSignBo applyItemPrSignBo = SpringContextUtil.getBean(ApplyItemPrSignBo.class);
		prForm.setMailContent(PrLineType.ALL.name());
		applyItemPrSignBo.updatePrFinal(prForm, SignStatus.APPR, null);
	}
}
