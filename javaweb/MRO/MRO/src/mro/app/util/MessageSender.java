package mro.app.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mro.app.util.vo.MailVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.SignTaskStatus;

import org.apache.commons.lang3.StringUtils;



public class MessageSender {	 
		private static final String sign_notify="文件簽核通知";
		private static final String sign_reject_notify="文件退件通知";
		private static final String sign_cancel_notify="文件取消通知";
		private static final String sign_forward_notify="文件轉呈通知";
		private static final String end_notify="文件同意通知";
		
		 //private String user;
		 //private String password;
		 private String mailhost;
		 private String from;
		 private String mailer;
		 private String rootpath;
		 private String rootproject;
		 private String domain;
		 
	 public MessageSender(){
			 mailhost="tnsmtp.cminl.oa";
			 from="MailSender_MRO@cminl.oa";
			 rootpath="http://mro.cminl.oa";
			 rootproject=SystemConfig.SYSTEMNAME;
			 mailer = "sendhtml";
			 domain="@innolux.com";
	 }
	 public void runDeliver(MailVO mailVO,String mailContent,List<String> notifyList,List<String> otherNotifyList)
	 {
		 
		 //String to="hongjie.wu@innolux.com";
		 //String cc=mailVO.getSendmail()+"@innolux.com";
	     //=============================================================================
		 String mailtype=null;
	     String subject = null;
			if(mailVO.getMailtype().compareTo(SignTaskStatus.INPRG)==0){
				mailtype = sign_notify;
			}else if(mailVO.getMailtype().compareTo(SignTaskStatus.REJECT)==0){
				mailtype = sign_reject_notify;
			}else if(mailVO.getMailtype().compareTo(SignTaskStatus.CANCEL)==0){
				mailtype = sign_cancel_notify;
			}else if(mailVO.getMailtype().compareTo(SignTaskStatus.TRANSFER)==0){
				mailtype = sign_forward_notify;
			}else if(mailVO.getMailtype().compareTo(SignTaskStatus.APPR)==0){
				mailtype = end_notify;
			}
			
		subject="MRO系統："+mailtype+"[申請單："+mailVO.getIssueid()+"]";
		if(StringUtils.isNotBlank(mailVO.getSubject())){
			subject=subject+mailVO.getSubject();
		}
//		subject=subject+" (系統測試信 請忽略)";
		//==============================================================================	
		StringBuffer body = new StringBuffer();
		body.append("<a href="+rootpath+"/"+rootproject+" >MRO系統"+"</a><br>");
		body.append("文件申請人："+mailVO.getApplyempname()+"("+mailVO.getApplyempno()+")"+"<br>");
		body.append("前次簽核人員："+mailVO.getSendempname()+"("+mailVO.getSendempno()+")"+"<br>");
		body.append("簽核日期："+mailVO.getSendDate()+"<br>");
		body.append("文件編號："+mailVO.getIssueid()+"<br>");
		body.append("申請單類別："+mailVO.getApplyDescription()+"<br>");
		body.append("簽核類型："+mailtype+"<br>");
		body.append("簽核說明："+mailVO.getComment()+"<br><br>");
		if(mailVO.getMailtype().compareTo(SignTaskStatus.INPRG)==0 || 
				mailVO.getMailtype().compareTo(SignTaskStatus.TRANSFER)==0){ 
			body.append("本次簽核人員："+mailVO.getSignempname()+"("+mailVO.getSignempno()+")"+"<br>");
		}
		if(StringUtils.isNotBlank(mailContent)){
			body.append(mailContent);
		}
//		System.out.println(body);
		//body.append("MAIL："+mailVO.getDomainaccount()+"<br>");
		//===============================================================================
			
	     try {
	         Properties props = System.getProperties();
	         if(mailhost != null)
	         {
	             props.put("mail.smtp.host", mailhost);
	         }
	         Session session = Session.getInstance(props, null);
	         session.setDebug(false);
	         MimeMessage msg = new MimeMessage(session);
	         if(from != null)
	         { 
					msg.setFrom(new InternetAddress(from));
	         } else
	         {
	             msg.setFrom();
	         }
	         if(StringUtils.isNotBlank(mailVO.getSignmail())){
	        	 msg.setRecipients(Message.RecipientType.TO,
	        	        InternetAddress.parse(mailAddress(mailVO.getSignmail()), false));
	         }
	         
	         //=================================結案通知==============================================
	         if(notifyList!=null){
	        	 for(String mail:notifyList){
	        		 if(StringUtils.isNotBlank(mail)){
	    	             msg.addRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(mailAddress(mail), false));
	        		 }
	        	 }
	         }
	         if(otherNotifyList!=null){
		         for(String mail:otherNotifyList){
		        	 if(StringUtils.isNotBlank(mail)){
		        		 msg.addRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(mailAddress(mail), false));
		        	 }
	        	 }
	         }
	        /* msg.setRecipients(Message.RecipientType.CC,
	        	        InternetAddress.parse(cc, false));*/
	         /*msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to, false));
	         log.debug("MAIL TO =====" + to);
	         if(cc != null)
	         {
	             msg.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(cc, false));
	         }
	         log.debug("MAIL CC =====" + cc);
	         if(bcc != null)
	         {
	             msg.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
	         }
	         */
	         msg.setSubject(subject, "big5");
	         msg.setContent(body.toString(),"text/html; charset=utf-8");
	         //msg.setHeader("X-Mailer", mailer);
	         msg.setSentDate(new Date());
	         Transport.send(msg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
	 }
	 public String mailAddress(String mail){
		 if(mail.indexOf("@")==-1){
			 mail= mail+"@innolux.com";
		 }
		 return mail;
	 }
	public String getMailhost() {
		return mailhost;
	}
	public String getFrom() {
		return from;
	}
	public String getMailer() {
		return mailer;
	}
	public String getRootpath() {
		return rootpath;
	}
	public String getRootproject() {
		return rootproject;
	}
	public String getDomain() {
		return domain;
	}
	 
}
