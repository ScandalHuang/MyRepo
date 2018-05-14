package mro.quartz.job;

import mro.quartz.job.mail.service.Impl.MailAlertListImpl;
/*
 * R1非週期入料之庫存 Monitor 機制通知
 */
public class MailALT16Job {
	public MailALT16Job(){
	}
	public void start() throws Exception{
		MailAlertListImpl impl=new MailAlertListImpl();
		impl.sendMailMain("ALT16");
	}

}
