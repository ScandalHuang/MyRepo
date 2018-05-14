package mro.app.applyItem.service;

import java.math.BigDecimal;

import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.Classstructure;
import mro.base.entity.Item;
import mro.base.entity.Person;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.form.ItemForm;

import org.primefaces.json.JSONException;

public interface ItemInterface {
	public ItemForm setParameter(ItemForm itemForm);
	public ItemForm setChangeParameter(ItemForm itemForm);
	
	public ItemForm selectApply(ItemForm itemForm,AItem aItem);
	
	public ItemForm setClassstructureuid(ItemForm itemForm,String classstructureid,
			String loginUserId,Person person); // 新增分類(草稿用)
	
	public void setDownLoadFile(ItemForm itemForm); // 取得下載檔案
	
	public void onApplyDelete(ItemForm itemForm); // 刪除申請單
	
	public ItemForm setCompany(ItemForm itemForm,VwNewvendorcodeEpmall vwNewvendorcodeEpmall);
	
	public ItemForm deleteinvvendor(ItemForm itemForm,ListAInvvendorVO l);
	
	public ItemForm setSelectItem(ItemForm itemForm,Item item);
	
	public ItemForm deleteAItemSecondItemnum(ItemForm itemForm,AItemSecondItemnum a);
	
	public void setGpDefault(ItemForm itemForm,String classstructureid);  // GP DEFAULT VALUE SET

	public void setSecondSource(ItemForm itemForm);  // SecondSource VALUE SET
	
	/** 2nd source料號規格異動 remark*/
	public void setSecondSourceRemark(ItemForm itemForm);
	
	public ItemForm setAItemSimple(ItemForm itemForm);
	public void setUnitCost(ItemForm itemForm);
	public ItemForm setUnitMap(ItemForm itemForm,String classstructureid);
	public ItemForm setClassstructurePhase(ItemForm itemForm,String classstructureid);
	public void setStorageCategory(ItemForm itemForm,Classstructure classstructure);
	public ItemForm setTaskId(ItemForm itemForm, String ItemNum,String loginEmpNo);
	public ItemForm setApplySubmit(ItemForm itemForm);
	public boolean onApplySave(ItemForm itemForm,String action,String loginEmpNo);
	public void onApplySave(ItemForm itemForm,BigDecimal Eaudittransid,String itemNum, String action);
	public ItemForm setApplyChangeMap(ItemForm preItemForm,ItemForm afterItemForm) throws Exception;
	public ItemForm setPreItemForm(ItemForm afterItemForm); 
	//===================================ProceessId========================================
	public int getSignProcessId(ItemForm itemForm); 
	public ItemForm onSignPreView(ItemForm itemForm,String loginEmpNo);
	public boolean simpleSignFlag(ItemForm itemForm);
	//==================================是否需附指廠說明===============================
	public boolean onVendorRemarkFlag(ItemForm itemForm);
	//==================================簽核參數===============================
	public String getSignParameter(ItemForm itemForm,String loginEmpNo) throws JSONException;
}
