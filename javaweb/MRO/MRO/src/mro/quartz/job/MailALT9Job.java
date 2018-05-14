package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * MRO代理人待簽核通知
 */
public class MailALT9Job {
	public MailALT9Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT9");
	}

}
