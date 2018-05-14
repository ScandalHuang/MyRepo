package mro.viewForm;

import java.io.Serializable;

import mro.base.System.config.basicType.PrType;
import mro.form.PrForm;

import org.apache.commons.lang3.StringUtils;


public class PrQueryAppView implements Serializable {
	
	private static final long serialVersionUID = -5407799212942130582L;
	
	private boolean deleteApprButton;  //刪除申請單
	private boolean canButton;  //取消申請單
	private boolean saveButton;  //儲存申請單
	private boolean closePrline;//close Prline
	private boolean combinePrline;//combine Prline
	private boolean editButton; //編輯申請單
	private boolean transferFlag; //epmall傳送申請單
	private boolean prTransferFlag; //pr是否已經傳送過
	
	public PrQueryAppView(){
		this.inital();
	}
	public void inital(){
		deleteApprButton=false;
		canButton=false;
		saveButton=false;
		closePrline=false;
		combinePrline=false;
		editButton=false;
		transferFlag=false;
		prTransferFlag=false;
	}
	
	public void setView(PrForm prForm,String loginEmpNo){
		this.inital();
		if(prForm.getRequestby2().equals(loginEmpNo) && 
			prForm.getPr()!=null && StringUtils.isNotBlank(prForm.getPr().getPrnum())){
			prTransferFlag=true;
		}
		if (prForm.getTransferFlag().equals("N")
				&& prForm.getRequestby2().equals(loginEmpNo)) {
			transferFlag=true;
			if(prForm.getPr()!=null && StringUtils.isNotBlank(prForm.getPr().getPrnum())){
				saveButton = true;
				closePrline = true;
				combinePrline = true;
				if (prForm.getSelectPrtype().equals(PrType.R1REORDER.toString())
						|| prForm.getSelectPrtype().equals(PrType.R2REORDER.toString())) {
					editButton = true;
				} else {
					canButton = true;
				}
			}
			if (prForm.getSelectPrtype().equals(PrType.R1REORDER.toString())
					|| prForm.getSelectPrtype().equals(PrType.R2REORDER.toString())) {
				deleteApprButton = true;
			}
		}
	}
	

	public boolean isDeleteApprButton() {
		return deleteApprButton;
	}
	public void setDeleteApprButton(boolean deleteApprButton) {
		this.deleteApprButton = deleteApprButton;
	}
	public boolean isEditButton() {
		return editButton;
	}
	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public boolean isCanButton() {
		return canButton;
	}

	public void setCanButton(boolean canButton) {
		this.canButton = canButton;
	}
	public boolean isSaveButton() {
		return saveButton;
	}
	public void setSaveButton(boolean saveButton) {
		this.saveButton = saveButton;
	}
	public boolean isClosePrline() {
		return closePrline;
	}
	public void setClosePrline(boolean closePrline) {
		this.closePrline = closePrline;
	}
	public boolean isCombinePrline() {
		return combinePrline;
	}
	public void setCombinePrline(boolean combinePrline) {
		this.combinePrline = combinePrline;
	}
	public boolean isTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(boolean transferFlag) {
		this.transferFlag = transferFlag;
	}
	public boolean isPrTransferFlag() {
		return prTransferFlag;
	}
	public void setPrTransferFlag(boolean prTransferFlag) {
		this.prTransferFlag = prTransferFlag;
	}
	
}
