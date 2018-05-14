package mro.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;



public class ItemTransferSiteForm extends ItemTransferSiteSearchForm implements
		Serializable {

	private static final long serialVersionUID = -5973837690075595500L;
	private List<ItemTransferLineApply> listItemTransferLineApply;
	private List<ItemTransferLineApply> listDeleteItemTransferLineApply;
	private ItemTransferHeaderApply itemTransferHeaderApply;
	
	private List<Map<String,String>> batchList;

	//=================file========================
	public Map dowloadFileMap;
	public FileCategory fileHeaderType;
	public FileCategory fileLineType;

	private int activeIndex;// 清單 page
	private boolean editButton; // 編輯申請單
	private String signHistoryUrl; // 簽核歷程網址

	private Person personForward; // 轉呈人員
	private String transferInfo; // 轉呈人員
	private String signComment; // 簽核意見
	private String signPreViewUrl; // 簽核預覽網址

	public ItemTransferSiteForm() {
		itemTransferSiteIntial();
	}

	public void itemTransferSiteIntial() {
		listItemTransferLineApply = new ArrayList<ItemTransferLineApply>();
		listDeleteItemTransferLineApply = new ArrayList<ItemTransferLineApply>();
		batchList = new ArrayList<Map<String,String>>();
		itemTransferHeaderApply = null;
		activeIndex = 0;
		editButton = false;
		signHistoryUrl = "";
		personForward = null;
		transferInfo = "";
		signComment = "";
		signPreViewUrl = "";

		dowloadFileMap = FileCategory.getDownloadFileMap();
		fileHeaderType=null;
		fileLineType=null;
	}
	
	public void onNewApply(Person person,LocationSiteMap locationSiteMap){
		editButton=true;
		activeIndex=1;
		itemTransferHeaderApply=new ItemTransferHeaderApply();
		itemTransferHeaderApply.setStatus(SignStatus.NEW.toString());
		itemTransferHeaderApply.setCreateBy(person.getPersonId()); // 申請人工號
		itemTransferHeaderApply.setDeptcode(person.getDeptCode());// 申請人部門
		itemTransferHeaderApply.setLocationSite(locationSiteMap!=null?locationSiteMap.getLocationSite():null);
	}
	public void onNewApply(Person person, String locationSite,String action,String classstructureid ){
		this.onNewApply(person, null);
		itemTransferHeaderApply.setLocationSite(locationSite);
		itemTransferHeaderApply.setAction(action);
		itemTransferHeaderApply.setClassstructureid(classstructureid);
	}
	
	public List<String> getLineIds(){
		return listItemTransferLineApply.stream().map(
				l->l.getApplyLineId().toString()).collect(Collectors.toList());
	}

	public List<ItemTransferLineApply> getListItemTransferLineApply() {
		return listItemTransferLineApply;
	}

	public void setListItemTransferLineApply(
			List<ItemTransferLineApply> listItemTransferLineApply) {
		this.listItemTransferLineApply = listItemTransferLineApply;
	}

	public List<ItemTransferLineApply> getListDeleteItemTransferLineApply() {
		return listDeleteItemTransferLineApply;
	}

	public void setListDeleteItemTransferLineApply(
			List<ItemTransferLineApply> listDeleteItemTransferLineApply) {
		this.listDeleteItemTransferLineApply = listDeleteItemTransferLineApply;
	}

	public ItemTransferHeaderApply getItemTransferHeaderApply() {
		return itemTransferHeaderApply;
	}

	public void setItemTransferHeaderApply(
			ItemTransferHeaderApply itemTransferHeaderApply) {
		this.itemTransferHeaderApply = itemTransferHeaderApply;
	}

	public List<Map<String, String>> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Map<String, String>> batchList) {
		this.batchList = batchList;
	}

	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}

	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}

	public FileCategory getFileHeaderType() {
		return fileHeaderType;
	}

	public void setFileHeaderType(FileCategory fileHeaderType) {
		this.fileHeaderType = fileHeaderType;
	}

	public FileCategory getFileLineType() {
		return fileLineType;
	}

	public void setFileLineType(FileCategory fileLineType) {
		this.fileLineType = fileLineType;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public String getSignHistoryUrl() {
		return signHistoryUrl;
	}

	public void setSignHistoryUrl(String signHistoryUrl) {
		this.signHistoryUrl = signHistoryUrl;
	}

	public Person getPersonForward() {
		return personForward;
	}

	public void setPersonForward(Person personForward) {
		this.personForward = personForward;
	}

	public String getTransferInfo() {
		return transferInfo;
	}

	public void setTransferInfo(String transferInfo) {
		this.transferInfo = transferInfo;
	}

	public String getSignComment() {
		return signComment;
	}

	public void setSignComment(String signComment) {
		this.signComment = signComment;
	}

	public String getSignPreViewUrl() {
		return signPreViewUrl;
	}

	public void setSignPreViewUrl(String signPreViewUrl) {
		this.signPreViewUrl = signPreViewUrl;
	}
	
	
	
}
