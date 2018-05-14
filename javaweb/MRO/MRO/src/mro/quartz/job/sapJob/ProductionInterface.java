package mro.quartz.job.sapJob;

import mro.base.System.config.SystemConfig;

import com.inx.commons.util.Utility;


/**
                                                                             
 -------------------------------------------------------------------------------------------------
 Project Name : Report                                                                                                                                         
 Class Name   : ClientJobInterface.java                                                                        
 Purpose      : 程式啟動job                                                                  
 ---------------------------------------------------------------------------------------------------
 Modification Log                                                                                        
 Date        Ver. #      Programmer     Description                                            
 2016/01/26  1.0		 hongjie.wu         Create        
 ---------------------------------------------------------------------------------------------------
 */
public interface ProductionInterface {
	
	//是否為正式機
	public final boolean production=Utility.validateHostName(SystemConfig.PRODUCTION_MAP);

}