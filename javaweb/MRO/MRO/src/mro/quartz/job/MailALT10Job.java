package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * MRO 控管量 Monitor 異常通知
 */
public class MailALT10Job {
	public MailALT10Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT10");
	}

}
