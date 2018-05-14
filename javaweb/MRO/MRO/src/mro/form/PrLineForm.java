package mro.form;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.Utility;

import lombok.Data;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.util.HrEmpUtils;
import mro.base.System.config.basicType.ActionType;
import mro.base.entity.Prline;


public class PrLineForm implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String prlineMethod="setSelectPrline";
	
	private Prline prline;
	private PrForm prForm;
	private ItemForm itemForm; // 料號phase

	private boolean editButton; // 編輯申請單
	private ActionType actionType; // add/update

	private Object object;
	private String updateView;

	public PrLineForm() {
		this.prInital();
	}

	public void prInital() {
		prline = null;
		editButton = false;
		actionType = null;
	}

	/** 新增 PRLINE 料號 */
	public void addPrLine(long maxPrlineNum, PrForm prForm) {
		editButton = true;
		this.prForm = prForm;
		prline = new Prline(prForm.getPr());
		prline.setPrlinenum(maxPrlineNum);
		actionType = ActionType.add;
	}

	/** 更新 & VIEW PRLINE 料號 */
	public void onPrLine(Prline p, boolean e, PrForm prForm) {
		this.prForm = prForm;
		prline = p;
		editButton = e;
		this.setItemForm();
		if (e)
			actionType = ActionType.update; // 更新
		this.setPrForm();
	}

	/** 更新ItemForm */
	public void setItemForm() {
		itemForm = new ItemForm();
		// ===更新料號phase==
		if (StringUtils.isNotBlank(prline.getPrnum()) &&
				StringUtils.isNotBlank(prline.getClassstructureid())) {
			ItemInterface itemInterface = new ItemImpl();
			itemForm = itemInterface.setClassstructurePhase(itemForm,
					prline.getClassstructureid());
		}

	}

	/** 更新PrForm */
	public void setPrForm() {
		// ====更新人員姓名清單========
		if (Utility.isValueNotEmpty(prline.getErpPrBuyerEmpNo(),
				prline.getErpPoBuyerEmpNo())) {
			prForm.getPrNameMap().putAll(
					HrEmpUtils.getMapList(Arrays.asList(
							prline.getErpPrBuyerEmpNo(),
							prline.getErpPoBuyerEmpNo())));
		}
	}
	/** 儲存前更新 */
	public void preSave(){
		if(StringUtils.isBlank(prline.getVendor()))  prline.setVendorRemark(null);
	}

	public Prline getPrline() {
		return prline;
	}

	public void setPrline(Prline prline) {
		this.prline = prline;
	}

	public PrForm getPrForm() {
		return prForm;
	}

	public void setPrForm(PrForm prForm) {
		this.prForm = prForm;
	}

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

	public static String getPrlinemethod() {
		return prlineMethod;
	}
	
	
}
