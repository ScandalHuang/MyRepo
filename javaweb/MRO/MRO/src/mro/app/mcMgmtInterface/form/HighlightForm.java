package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.InvbalancesHighlightLog;
import mro.base.entity.MroInvbalancesHighlightV;

import org.apache.commons.lang3.ObjectUtils;

import com.inx.commons.util.JsfContextUtil;


public class HighlightForm implements Serializable{
	private static final long serialVersionUID = 8241444227655293177L;
	private List<MroInvbalancesHighlightV> listHighlight;
	private List<InvbalancesHighlightLog> listInvbalancesHighlightLog;
	private InvbalancesHighlightLog invbalancesHighlightLog;
	private MroInvbalancesHighlightV mroInvbalancesHighlightV;
	//==============search========================
	private String selectItemCategory;
	private String selectDeptcode;
	private String selectitemnum;
	private String selectSiteId;
	private String prType;
	private String reasonType;
	private String monitorType; //只有兩種，懶的在寫到parameter:近半年領耗用次數未達3次/控管量高估 
	private boolean searchHistory = false; 
	//==============================================================
	private BigDecimal changeQty;
	//==============Log 頁面更新====================================
	private boolean updatePage=false;   //更新頁面
    private Object object;   //來源Bean
    private String updateView;   //更新view
	//==============預設參數===============================
	private Map organizationOption;
	private Map<String,Map> reasonOption;
	private Map itemCategoryOption; // 耗用大分類
	private boolean editButton = false; // 編輯
	
	public HighlightForm(){
		intial();
	}
	
	public void intial(){
		listHighlight=new ArrayList<>();
		listInvbalancesHighlightLog=new ArrayList<>();
		invbalancesHighlightLog=new InvbalancesHighlightLog();
		updatePage=false;
		changeQty=null;
	}	
	
	public void setChangeQty(){
		changeQty=mroInvbalancesHighlightV.getThreeMonthAvgRecommand();
	}
	
	public void setParameter() {
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		this.reasonOption=new LinkedHashMap<String, Map>();
		this.reasonOption.put(ItemType.R1.toString(),
				bean.getParameterOption().get(ParameterType.R1_HIGHLIGHT_REASON_TYPE));
		this.reasonOption.put(ItemType.R2.toString(),
				bean.getParameterOption().get(ParameterType.R2_HIGHLIGHT_REASON_TYPE));
		this.itemCategoryOption=new LinkedHashMap();

		//移除未用到選項，itemCategoryOption還是必須要由parameter取得，這是為了統一控管
		Map itemCategoryOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
		for(Object key:itemCategoryOption.keySet()){
			if(reasonOption.get(ObjectUtils.toString(key))!=null){
				this.itemCategoryOption.put(key, itemCategoryOption.get(key));
			}
		}
	}

	public List<MroInvbalancesHighlightV> getListHighlight() {
		return listHighlight;
	}

	public void setListHighlight(List<MroInvbalancesHighlightV> listHighlight) {
		this.listHighlight = listHighlight;
	}

	public List<InvbalancesHighlightLog> getListInvbalancesHighlightLog() {
		return listInvbalancesHighlightLog;
	}

	public void setListInvbalancesHighlightLog(
			List<InvbalancesHighlightLog> listInvbalancesHighlightLog) {
		this.listInvbalancesHighlightLog = listInvbalancesHighlightLog;
	}

	public InvbalancesHighlightLog getInvbalancesHighlightLog() {
		return invbalancesHighlightLog;
	}

	public void setInvbalancesHighlightLog(
			InvbalancesHighlightLog invbalancesHighlightLog) {
		this.invbalancesHighlightLog = invbalancesHighlightLog;
	}

	public MroInvbalancesHighlightV getMroInvbalancesHighlightV() {
		return mroInvbalancesHighlightV;
	}

	public void setMroInvbalancesHighlightV(
			MroInvbalancesHighlightV mroInvbalancesHighlightV) {
		this.mroInvbalancesHighlightV = mroInvbalancesHighlightV;
	}

	public String getSelectItemCategory() {
		return selectItemCategory;
	}

	public void setSelectItemCategory(String selectItemCategory) {
		this.selectItemCategory = selectItemCategory;
	}

	public String getSelectDeptcode() {
		return selectDeptcode;
	}

	public void setSelectDeptcode(String selectDeptcode) {
		this.selectDeptcode = selectDeptcode;
	}

	public String getSelectitemnum() {
		return selectitemnum;
	}

	public void setSelectitemnum(String selectitemnum) {
		this.selectitemnum = selectitemnum;
	}

	public String getSelectSiteId() {
		return selectSiteId;
	}

	public void setSelectSiteId(String selectSiteId) {
		this.selectSiteId = selectSiteId;
	}

	public String getPrType() {
		return prType;
	}

	public void setPrType(String prType) {
		this.prType = prType;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public boolean isSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(boolean searchHistory) {
		this.searchHistory = searchHistory;
	}

	public BigDecimal getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(BigDecimal changeQty) {
		this.changeQty = changeQty;
	}

	public boolean isUpdatePage() {
		return updatePage;
	}

	public void setUpdatePage(boolean updatePage) {
		this.updatePage = updatePage;
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

	public Map getOrganizationOption() {
		return organizationOption;
	}

	public void setOrganizationOption(Map organizationOption) {
		this.organizationOption = organizationOption;
	}

	public Map<String, Map> getReasonOption() {
		return reasonOption;
	}

	public void setReasonOption(Map<String, Map> reasonOption) {
		this.reasonOption = reasonOption;
	}

	public Map getItemCategoryOption() {
		return itemCategoryOption;
	}

	public void setItemCategoryOption(Map itemCategoryOption) {
		this.itemCategoryOption = itemCategoryOption;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

}
