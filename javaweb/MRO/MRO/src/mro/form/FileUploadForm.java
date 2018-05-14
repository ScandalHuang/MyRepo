package mro.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;


public class FileUploadForm implements Serializable{
	private static final long serialVersionUID = 3552147958851457939L;
	private List<byte[]> listFile;
	private List<Attachment> listAttachment;
	private int sizeLimit;
	private String allowTypes;
	private long fileLimit;
	private String allowTypesText;
	private FileCategory fileCategory;
	private Object keyId;
	private boolean editButton; // 編輯

	//==============Log 頁面更新====================================
    private Map dowloadFileMap;   //Attachment map
    private String updateView;   //更新view
	
	public FileUploadForm(){
		intial();
	}
	
	public void intial(){
		listFile = new ArrayList<>();
		listAttachment = new ArrayList<Attachment>();
		sizeLimit=0;
		allowTypes = "";
		fileLimit = 0;
		allowTypesText="";
		fileCategory = null;
		keyId = "";
		editButton = false;
		dowloadFileMap=null;
		updateView="";
	}

	public List<byte[]> getListFile() {
		return listFile;
	}

	public void setListFile(List<byte[]> listFile) {
		this.listFile = listFile;
	}

	public List<Attachment> getListAttachment() {
		return listAttachment;
	}

	public void setListAttachment(List<Attachment> listAttachment) {
		this.listAttachment = listAttachment;
	}

	public int getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public String getAllowTypes() {
		return allowTypes;
	}

	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}

	public long getFileLimit() {
		return fileLimit;
	}

	public void setFileLimit(long fileLimit) {
		this.fileLimit = fileLimit;
	}

	public String getAllowTypesText() {
		return allowTypesText;
	}

	public void setAllowTypesText(String allowTypesText) {
		this.allowTypesText = allowTypesText;
	}

	public FileCategory getFileCategory() {
		return fileCategory;
	}

	public void setFileCategory(FileCategory fileCategory) {
		this.fileCategory = fileCategory;
	}

	public Object getKeyId() {
		return keyId;
	}

	public void setKeyId(Object keyId) {
		this.keyId = keyId;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}

	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}
	
}
