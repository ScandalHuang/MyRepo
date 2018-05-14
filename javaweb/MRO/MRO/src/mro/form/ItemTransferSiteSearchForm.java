package mro.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.base.entity.ItemTransferHeaderApply;

public class ItemTransferSiteSearchForm implements Serializable{
	private static final long serialVersionUID = 6201514042176241855L;
	private List<ItemTransferHeaderApply> listItemTransferHeaderApply;
	private Map signMap;//申請單簽核人員
	private Map selectStatus; // 申請單狀態(下拉選單)
	private Map actionTypeOption; // 拋轉ACTION選項
	private Map actionTypeMapping; // 拋轉ACTION mapping
	private Map lineStatusMapping; // line status mapping
	private Map siteOption;
	
	private String locationSite;
	private String applyHeaderNum;
	private Date beginDate;
	private Date endDate;
	private String status;
	private String createBy;
	private String action;
	
	public List<ItemTransferHeaderApply> getListItemTransferHeaderApply() {
		return listItemTransferHeaderApply;
	}
	public void setListItemTransferHeaderApply(
			List<ItemTransferHeaderApply> listItemTransferHeaderApply) {
		this.listItemTransferHeaderApply = listItemTransferHeaderApply;
	}
	public Map getSelectStatus() {
		return selectStatus;
	}
	public void setSelectStatus(Map selectStatus) {
		this.selectStatus = selectStatus;
	}
	public Map getActionTypeOption() {
		return actionTypeOption;
	}
	public void setActionTypeOption(Map actionTypeOption) {
		this.actionTypeOption = actionTypeOption;
	}
	public Map getActionTypeMapping() {
		return actionTypeMapping;
	}
	public void setActionTypeMapping(Map actionTypeMapping) {
		this.actionTypeMapping = actionTypeMapping;
	}
	public Map getLineStatusMapping() {
		return lineStatusMapping;
	}
	public void setLineStatusMapping(Map lineStatusMapping) {
		this.lineStatusMapping = lineStatusMapping;
	}
	public String getApplyHeaderNum() {
		return applyHeaderNum;
	}
	public void setApplyHeaderNum(String applyHeaderNum) {
		this.applyHeaderNum = applyHeaderNum;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map getSignMap() {
		return signMap;
	}
	public void setSignMap(Map signMap) {
		this.signMap = signMap;
	}
	public Map getSiteOption() {
		return siteOption;
	}
	public void setSiteOption(Map siteOption) {
		this.siteOption = siteOption;
	}
	public String getLocationSite() {
		return locationSite;
	}
	public void setLocationSite(String locationSite) {
		this.locationSite = locationSite;
	}
	
}
