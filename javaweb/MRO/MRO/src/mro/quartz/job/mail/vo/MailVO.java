package mro.quartz.job.mail.vo;

import java.util.ArrayList;
import java.util.List;

public class MailVO implements java.io.Serializable{
	
	private String mailFrom;
	private String subject;
	private String content;
	private List<String> mailTo;
	private List<String> mailCC;
	private List<String> mailBCC;
	
	public MailVO() {
		mailTo=new ArrayList<String>();
		mailCC=new ArrayList<String>();
		mailBCC=new ArrayList<String>();
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getMailTo() {
		return mailTo;
	}
	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}
	public List<String> getMailCC() {
		return mailCC;
	}
	public void setMailCC(List<String> mailCC) {
		this.mailCC = mailCC;
	}
	public List<String> getMailBCC() {
		return mailBCC;
	}
	public void setMailBCC(List<String> mailBCC) {
		this.mailBCC = mailBCC;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
}
