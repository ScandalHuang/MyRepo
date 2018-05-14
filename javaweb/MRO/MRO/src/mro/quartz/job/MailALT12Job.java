package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * SAP程式更新失敗通知!!
 */
public class MailALT12Job {
	public MailALT12Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT12");
	}

}
