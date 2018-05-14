package mro.app.signTask.service;

import java.util.Map;

import mro.base.entity.MailQueue;


/**
                                                                             
 -------------------------------------------------------------------------------------------------
 Project Name : MRO                                                                                                                                         
 Class Name   : NotifyInterface.java                                                                        
 Purpose      : 通知節點                                                                
 ---------------------------------------------------------------------------------------------------
 Modification Log                                                                                        
 Date        Ver. #      Programmer     Description                                            
 2015/03/02  1.0		 hongjie.wu         Create        
 ---------------------------------------------------------------------------------------------------
 */
public interface NotifyInterface {
	
	public MailQueue onMailNotify(Map map);

}