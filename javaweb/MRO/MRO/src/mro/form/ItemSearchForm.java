package mro.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.entity.AItem;
import mro.base.entity.LocationSiteMap;
import mro.utility.JsfContextUtils;

import org.primefaces.component.datatable.DataTable;

public class ItemSearchForm implements Serializable{
	private static final long serialVersionUID = -5251684322000101528L;
	private List<AItem> listAItem;
	private List<AItem> filterListAItem;
	private Map signMap;//申請單簽核人員
	private Map selectStatus; // 申請單狀態(下拉選單)
	private String sLocationSite;
	private String sOrganizationCode;
	private String itemnum;
	private String description;
	private Date beginDate;
	private Date endDate;
	private String status;
	private String deptCode;
	private String changeby;	
	private String selectItemCategory;
	private Map requestedby2NameMap;//申請人姓名
	private Map gpProduction; // 產品/製程類別選單
	private Map gpDelivery; // 是否隨產品出貨選單
	private Map gpRemain; // 是否殘留在產品內部選單
	private Map storageCondition; // 儲存條件
	private Map currencyOption; // 幣別選項
	private Map mcUseFrequencyTypeOption; // 耗用分類選單
	private Map inspectionTypeOption; //驗收分類
	private Map buyerSignMap; //採購簽核人員
	private Map itemCategoryOption;  //料號大分類
	private Map secondItemOption;  //替代料號選單
	
	private Map<String,String> selectClasstrctureMap;
	private String selectClasstrcture;

	public void listIntial(){
		listAItem= new ArrayList<AItem>();
		filterListAItem= new ArrayList<AItem>();
		signMap=new HashMap();
	}
	public void copyListAItem(){
		filterListAItem=listAItem;
		JsfContextUtils.executeView("PF('listAItem').clearFilters();");
	}
	public List<AItem> getListAItem() {
		return listAItem;
	}
	public void setListAItem(List<AItem> listAItem) {
		this.listAItem = listAItem;
	}
	public Map getSelectStatus() {
		return selectStatus;
	}
	public void setSelectStatus(Map selectStatus) {
		this.selectStatus = selectStatus;
	}
	public String getItemnum() {
		return itemnum;
	}
	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getChangeby() {
		return changeby;
	}
	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}
	public String getSelectItemCategory() {
		return selectItemCategory;
	}
	public void setSelectItemCategory(String selectItemCategory) {
		this.selectItemCategory = selectItemCategory;
	}
	public Map getRequestedby2NameMap() {
		return requestedby2NameMap;
	}
	public void setRequestedby2NameMap(Map requestedby2NameMap) {
		this.requestedby2NameMap = requestedby2NameMap;
	}
	public Map getGpProduction() {
		return gpProduction;
	}
	public void setGpProduction(Map gpProduction) {
		this.gpProduction = gpProduction;
	}
	public Map getGpDelivery() {
		return gpDelivery;
	}
	public void setGpDelivery(Map gpDelivery) {
		this.gpDelivery = gpDelivery;
	}
	public Map getGpRemain() {
		return gpRemain;
	}
	public void setGpRemain(Map gpRemain) {
		this.gpRemain = gpRemain;
	}
	public Map getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(Map storageCondition) {
		this.storageCondition = storageCondition;
	}
	public Map getCurrencyOption() {
		return currencyOption;
	}
	public void setCurrencyOption(Map currencyOption) {
		this.currencyOption = currencyOption;
	}
	public Map getMcUseFrequencyTypeOption() {
		return mcUseFrequencyTypeOption;
	}
	public void setMcUseFrequencyTypeOption(Map mcUseFrequencyTypeOption) {
		this.mcUseFrequencyTypeOption = mcUseFrequencyTypeOption;
	}
	public Map getInspectionTypeOption() {
		return inspectionTypeOption;
	}
	public void setInspectionTypeOption(Map inspectionTypeOption) {
		this.inspectionTypeOption = inspectionTypeOption;
	}
	public Map<String, String> getSelectClasstrctureMap() {
		return selectClasstrctureMap;
	}
	public void setSelectClasstrctureMap(Map<String, String> selectClasstrctureMap) {
		this.selectClasstrctureMap = selectClasstrctureMap;
	}
	public String getSelectClasstrcture() {
		return selectClasstrcture;
	}
	public void setSelectClasstrcture(String selectClasstrcture) {
		this.selectClasstrcture = selectClasstrcture;
	}
	public Map getBuyerSignMap() {
		return buyerSignMap;
	}
	public void setBuyerSignMap(Map buyerSignMap) {
		this.buyerSignMap = buyerSignMap;
	}
	public Map getSignMap() {
		return signMap;
	}
	public void setSignMap(Map signMap) {
		this.signMap = signMap;
	}
	public Map getItemCategoryOption() {
		return itemCategoryOption;
	}
	public void setItemCategoryOption(Map itemCategoryOption) {
		this.itemCategoryOption = itemCategoryOption;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<AItem> getFilterListAItem() {
		return filterListAItem;
	}
	public void setFilterListAItem(List<AItem> filterListAItem) {
		this.filterListAItem = filterListAItem;
	}
	public Map getSecondItemOption() {
		return secondItemOption;
	}
	public void setSecondItemOption(Map secondItemOption) {
		this.secondItemOption = secondItemOption;
	}
	public String getsLocationSite() {
		return sLocationSite;
	}
	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}
	public String getsOrganizationCode() {
		return sOrganizationCode;
	}
	public void setsOrganizationCode(String sOrganizationCode) {
		this.sOrganizationCode = sOrganizationCode;
	}
	
}
