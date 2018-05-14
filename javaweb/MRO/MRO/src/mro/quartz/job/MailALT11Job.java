package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * MRO 通知：控管量Monitor下修清單
 */
public class MailALT11Job {
	public MailALT11Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT11");
	}

}
