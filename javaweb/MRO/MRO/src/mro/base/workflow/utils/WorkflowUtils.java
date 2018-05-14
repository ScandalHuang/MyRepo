package mro.base.workflow.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mro.app.locationMgmt.bo.ListLocationMgmtBO;
import mro.app.signTask.jsf.SignTaskBean;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AItem;
import mro.base.entity.Pr;

import com.inx.commons.util.SpringContextUtil;

public class WorkflowUtils {
	public static List<BigDecimal> getTaskId(String empNo, SignStatus status,
			String sourceSystem, String sourceCategory,boolean deputy){
		SignTaskBean signTaskBean=new SignTaskBean();
		return signTaskBean.getWaitSignTask(empNo, status,sourceSystem,sourceCategory,deputy);
	}
	
	//=======================取得待簽核清單==================================
	public static List<BigDecimal> getSignList(String empNo,String category){
		SignTaskBean signTaskBean=new SignTaskBean();
		List<BigDecimal> s=signTaskBean.getWaitSignTask(empNo, SignStatus.INPRG,SystemConfig.SYSTEMNAME,category,true);
		return s;
	}
	//=======================取得會簽人員====================================
	public static String getCounterArray(Pr pr){
		HashSet<String> hashSet = new HashSet<String>(); 
		List<String> list=new ArrayList<String>();
		if(pr.getCountersign1()!=null  && hashSet.add(pr.getCountersign1())){
			list.add(pr.getCountersign1());
		}
		if(pr.getCountersign2()!=null && hashSet.add(pr.getCountersign2())){
			list.add(pr.getCountersign2());
		}
		return ListToString(list);
		
	}

	//=======================取得簽核編號====================================
	public static int getItemApplySignProcessId(AItem aItem){
		return 10010;
	}
	public static int getItemChengeSignProcessId(AItem aItem){
		return 10002;
	}
	public static int getItemTransferProcessId(){
		return 10115;
	}
	public static int getClassstructureApplyProcessId(){
		return 10120;
	}
	public static int getR1ControlSignProcessId(Pr pr){
		ListLocationMgmtBO listLocationMgmtBO=SpringContextUtil.getBean(ListLocationMgmtBO.class);
		return 10011;
		//2014/06/09 取消寧波指動簽核
//		LocationMap locationMap=listLocationMgmtBO.getLocationMap(new String[]{"siteId"},
//				pr.getSiteid());
//		if(locationMap.getLocationGroup().equals("NB")){   //寧波控管
//			return 10013;
//		}else if(locationMap.getLocationGroup().equals("TN")){   //寧波控管
//			return 10011;
//		}else{
//			return 0;
//		}
	}
	public static int getR1PmreqSignProcessId(Pr pr){
		return 10012;
		//2014/06/09 取消寧波指動簽核
//		LocationMap locationMap=listLocationMgmtBO.getLocationMap(new String[]{"siteId"},
//				pr.getSiteid());
//		if(locationMap.getLocationGroup().equals("NB")){   //寧波控管
//			return 10014;
//		}else if(locationMap.getLocationGroup().equals("TN")){   //寧波控管
//			return 10012;
//		}else{
//			return 0;
//		}
	}
	public static int getPrSignProcessId(Pr pr){
		if(pr.getPrtype().equals(PrType.R18_STATIONERY.name())){   //文具
			return 10118;
		}else{
			return 0;
		}
	}
	public static int getR2ControlSignProcessId(){
		return 10117;
	}
	public static int getR2PmreqSignProcessId(){
		return 10116;
	}
	//==========================utils==============================================
	public static String ArrayToString(String[] array){
		StringBuffer str=new StringBuffer();
		if(array!=null && array.length>0){
			for(String s:array){
				if(str.length()>0){str.append(",");}
				str.append(s);
			}
		}		
		return str==null?null:str.toString();
	}
	public static String ListToString(List<String> list){
		StringBuffer str=new StringBuffer();
		if(list!=null && list.size()>0){
			for(String s:list){
				if(str.length()>0){str.append(",");}
				str.append(s);
			}
		}		
		return str==null?null:str.toString();
	}
}
