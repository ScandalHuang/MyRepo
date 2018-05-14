package mro.app.util.vo;

import mro.base.System.config.basicType.SignTaskStatus;

public class MailVO implements java.io.Serializable{
	private static final long serialVersionUID = -2005045931209566849L;
	private SignTaskStatus mailtype;
	private String Subject;
	private String sendDate;
	private String issueid;
	private String sendempno;
	private String sendempname;
	private String signempno;
	private String signempname;
	private String comment;
	private String signmail;
	private String domainaccount;
	private String applyempno;
	private String applyempname;

	private String applyDescription;
	
	public SignTaskStatus getMailtype() {
		return mailtype;
	}
	public void setMailtype(SignTaskStatus mailtype) {
		this.mailtype = mailtype;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getIssueid() {
		return issueid;
	}
	public void setIssueid(String issueid) {
		this.issueid = issueid;
	}
	public String getSendempno() {
		return sendempno;
	}
	public void setSendempno(String sendempno) {
		this.sendempno = sendempno;
	}
	public String getSendempname() {
		return sendempname;
	}
	public void setSendempname(String sendempname) {
		this.sendempname = sendempname;
	}
	public String getSignempno() {
		return signempno;
	}
	public void setSignempno(String signempno) {
		this.signempno = signempno;
	}
	public String getSignempname() {
		return signempname;
	}
	public void setSignempname(String signempname) {
		this.signempname = signempname;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDomainaccount() {
		return domainaccount;
	}
	public void setDomainaccount(String domainaccount) {
		this.domainaccount = domainaccount;
	}
	public String getSignmail() {
		return signmail;
	}
	public void setSignmail(String signmail) {
		this.signmail = signmail;
	}
	public String getApplyempno() {
		return applyempno;
	}
	public void setApplyempno(String applyempno) {
		this.applyempno = applyempno;
	}
	public String getApplyempname() {
		return applyempname;
	}
	public void setApplyempname(String applyempname) {
		this.applyempname = applyempname;
	}
	public String getApplyDescription() {
		return applyDescription;
	}
	public void setApplyDescription(String applyDescription) {
		this.applyDescription = applyDescription;
	}
	
	
}
