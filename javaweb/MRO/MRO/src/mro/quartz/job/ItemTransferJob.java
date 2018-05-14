package mro.quartz.job;
import mro.quartz.job.sapJob.Job.Mro2Sap;
/*
 * SAP ORACLE 料號拋轉
 */
public class ItemTransferJob {
	public ItemTransferJob(){
	}
	public void start() throws Exception{
		Mro2Sap mro2Sap=new Mro2Sap();
		
		mro2Sap.start();
		 
	}

}
