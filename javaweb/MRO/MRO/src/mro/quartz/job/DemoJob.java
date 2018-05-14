package mro.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DemoJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("demoJob!!");
		
		
	}

}
