package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * 料號凍結生效&需求部門之控管量系統自動歸零通知
 */
public class MailALT19Job {
	public MailALT19Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT19");
	}

}
