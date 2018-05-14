package mro.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;
import mro.base.entity.Pr;


public class PrSearchForm implements Serializable{
	private static final long serialVersionUID = 2923220358941175095L;
	private List<Pr> listPr;
	private Map signMap;//申請單簽核人員
	private Map selectStatus; // 申請單狀態(下拉選單)
	private Map prtypeOption; //prtype(下拉選單)
	private Map applyPurpose; //申請目的
	private Map priorityOption; //優先順序
	private String sLocationSite;
	private String sSiteid;
	private String prnum;
	private Date beginDate;
	private Date endDate;
	private String status;
	private String requestby2;
	private String MDept;
	private String selectPrtype;
	private String selectSystem;
	private String transferFlag;
	private Map requestedby2NameMap;//申請人姓名
	
	public PrSearchForm(){
		
	}

	public List<Pr> getListPr() {
		return listPr;
	}

	public void setListPr(List<Pr> listPr) {
		this.listPr = listPr;
	}

	public Map getSignMap() {
		return signMap;
	}

	public void setSignMap(Map signMap) {
		this.signMap = signMap;
	}

	public Map getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(Map selectStatus) {
		this.selectStatus = selectStatus;
	}

	public Map getPrtypeOption() {
		return prtypeOption;
	}

	public void setPrtypeOption(Map prtypeOption) {
		this.prtypeOption = prtypeOption;
	}

	public Map getApplyPurpose() {
		return applyPurpose;
	}

	public void setApplyPurpose(Map applyPurpose) {
		this.applyPurpose = applyPurpose;
	}

	public Map getPriorityOption() {
		return priorityOption;
	}

	public void setPriorityOption(Map priorityOption) {
		this.priorityOption = priorityOption;
	}

	public String getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

	public String getsSiteid() {
		return sSiteid;
	}

	public void setsSiteid(String sSiteid) {
		this.sSiteid = sSiteid;
	}

	public String getPrnum() {
		return prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
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

	public String getRequestby2() {
		return requestby2;
	}

	public void setRequestby2(String requestby2) {
		this.requestby2 = requestby2;
	}

	public String getMDept() {
		return MDept;
	}

	public void setMDept(String mDept) {
		MDept = mDept;
	}

	public String getSelectPrtype() {
		return selectPrtype;
	}

	public void setSelectPrtype(String selectPrtype) {
		this.selectPrtype = selectPrtype;
	}

	public String getSelectSystem() {
		return selectSystem;
	}

	public void setSelectSystem(String selectSystem) {
		this.selectSystem = selectSystem;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public Map getRequestedby2NameMap() {
		return requestedby2NameMap;
	}

	public void setRequestedby2NameMap(Map requestedby2NameMap) {
		this.requestedby2NameMap = requestedby2NameMap;
	}
	
}
