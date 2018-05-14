package mro.app.applyItem.service;

import java.math.BigDecimal;
import java.util.List;

import mro.base.System.config.basicType.ActionType;
import mro.base.entity.Item;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.Person;
import mro.form.ItemTransferSiteForm;

public interface ItemTransferSiteInterface {
	public ItemTransferSiteForm selectApply(ItemTransferSiteForm itemTransferSiteForm,
			ItemTransferHeaderApply itemTransferHeaderApply);
	
	public ItemTransferSiteForm onSignPreView(ItemTransferSiteForm itemTransferSiteForm);
	
	public void onDelete(ItemTransferSiteForm itemTransferSiteForm);
	
	public void addItem(ItemTransferSiteForm itemTransferSiteForm,Item item,String lineRemark);
	
	public void deleteItemTransferLineApply(ItemTransferSiteForm itemTransferSiteForm,
			ItemTransferLineApply itemTransferLineApply);
	
	public boolean onApplySave(ItemTransferSiteForm itemTransferSiteForm,ActionType type);
	
	public List getItemList(List<ItemTransferLineApply> listItemTransferLineApply);

	public void onBatchApply(ItemTransferSiteForm form,Person person);//拆單
	//===================================File============================================
	public void setDownLoadFile(ItemTransferSiteForm form);
	
	//==============================extraRemark(pr申請單與控管量)===============
	public void setExtraRemark(ItemTransferSiteForm itemTransferSiteForm);
	
	//==================================預設參數===============================
	public void setParameter(ItemTransferSiteForm itemTransferSiteForm);
	
	//==================================簽核參數===============================
	public String getSignParameter(ItemTransferSiteForm itemTransferSiteForm);

	public BigDecimal getItemCost(List<ItemTransferLineApply> listItemTransferLineApply);
}
