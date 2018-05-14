package mro.app.r2Correct.vo;

import java.io.Serializable;

import javax.persistence.Column;

public class AItemCorrect implements Serializable {
	private static final long serialVersionUID = 4576427071486876926L;
	private String itemnum;
	private String changeby;
	private String organizationCode;
	private String classstructureid;
	private String delete;
	private boolean updateFlag;  //更新狀況
	private boolean executeFlag;//是否曾處理更新
	private String errorMsg;
	
	public AItemCorrect(){
		updateFlag=false;
		executeFlag=false;
	}
	@Column(name = "ITEMNUM")
	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "CHANGEBY")
	public String getChangeby() {
		return changeby;
	}
	@Column(name = "CLASSSTRUCTUREID")
	public String getClassstructureid() {
		return classstructureid;
	}
	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}
	@Column(name = "DELETE")
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}

	@Column(name = "ORGANIZATION_CODE")
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}
	public boolean isUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isExecuteFlag() {
		return executeFlag;
	}
	public void setExecuteFlag(boolean executeFlag) {
		this.executeFlag = executeFlag;
	}
	
}
