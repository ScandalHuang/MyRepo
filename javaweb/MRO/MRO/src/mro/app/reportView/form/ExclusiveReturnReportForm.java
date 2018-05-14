package mro.app.reportView.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExclusiveReturnReportForm implements Serializable {
	private static final long serialVersionUID = 2570523590374533130L;
	private List list;
	private String organizationCode;
	private String deptNo;
	private String itemnum;
	private Date stDate;
	private Date issueEdDate;
	private Date returnEdDate;
	private String slocationSite;
	
	private List list2;
	private String organizationCode2;
	private String deptNo2;
	private Date stDate2;
	private Date issueEdDate2;
	private Date returnEdDate2;
	private String slocationSite2;
	
	public ExclusiveReturnReportForm(){
		intial();
		list=new ArrayList();
		list2=new ArrayList();
	}
	
	public void intial(){
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getIssueEdDate() {
		return issueEdDate;
	}

	public void setIssueEdDate(Date issueEdDate) {
		this.issueEdDate = issueEdDate;
	}

	public Date getReturnEdDate() {
		return returnEdDate;
	}

	public void setReturnEdDate(Date returnEdDate) {
		this.returnEdDate = returnEdDate;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List getList2() {
		return list2;
	}

	public void setList2(List list2) {
		this.list2 = list2;
	}

	public String getOrganizationCode2() {
		return organizationCode2;
	}

	public void setOrganizationCode2(String organizationCode2) {
		this.organizationCode2 = organizationCode2;
	}

	public String getDeptNo2() {
		return deptNo2;
	}

	public void setDeptNo2(String deptNo2) {
		this.deptNo2 = deptNo2;
	}

	public Date getStDate2() {
		return stDate2;
	}

	public void setStDate2(Date stDate2) {
		this.stDate2 = stDate2;
	}

	public Date getIssueEdDate2() {
		return issueEdDate2;
	}

	public void setIssueEdDate2(Date issueEdDate2) {
		this.issueEdDate2 = issueEdDate2;
	}

	public Date getReturnEdDate2() {
		return returnEdDate2;
	}

	public void setReturnEdDate2(Date returnEdDate2) {
		this.returnEdDate2 = returnEdDate2;
	}

	public String getSlocationSite() {
		return slocationSite;
	}

	public void setSlocationSite(String slocationSite) {
		this.slocationSite = slocationSite;
	}

	public String getSlocationSite2() {
		return slocationSite2;
	}

	public void setSlocationSite2(String slocationSite2) {
		this.slocationSite2 = slocationSite2;
	}
	
}
