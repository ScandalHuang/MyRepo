package mro.base.System.config.basicType;

import java.util.Map;
import java.util.TreeMap;

import mro.base.System.config.jsf.SystemConfigBean;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.Utility;


public enum PrType {
	R18_STATIONERY(PrNumType.RC),  
	R1CONTROL(PrNumType.RM),  
	R1PMREQ(PrNumType.RC),    
	R2CONTROL(PrNumType.RM),  
	R2PMREQ(PrNumType.RC),  
	R2REORDER(PrNumType.RR),  
	R1REORDER(PrNumType.RR);  
	
	private PrNumType value;
	

	private PrType(PrNumType value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
	
	public static PrType findValue(String key){
		return PrType.valueOf(key);
	}
	
	/*
	 * 取得DELIVERY_DAY
	 */
	public int getDeliveryDay(){
		if(name().equals(R1PMREQ.name())
				||name().equals(R1CONTROL.name()) //201712 add
				||name().equals(R1REORDER.name())){ //201712 add
			return 6;//20171205 原 3
		} 
		return 24;//20171205 原 18
	}
	/*
	 * 取得控管type
	 */
	public static String getControlPrType(String itemCategory){
		if(Utility.equals(itemCategory, ItemType.R1)){
			return PrType.R1CONTROL.name();
		}else if(Utility.equals(itemCategory, ItemType.R2)){
			return PrType.R2CONTROL.name();
		}
		return null;
	}
	/*
	 * 取得prtype name
	 */
	public String getPrtypeName(){
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		return bean.getParameterMap().get(name());
	}
	/*
	 * 全部 PRTYPE OPTION
	 */
	public static Map getALLPrTypeOption(){ 
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		return bean.getParameterOption().get(ParameterType.PRTYPE);
	}
	/*
	 * reorder option
	 */
	public static Map getReorderOption(){ 
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		Map option=new TreeMap();
		option.put(bean.getParameterMap().get(PrType.R1REORDER.name()),PrType.R1REORDER.name());
		option.put(bean.getParameterMap().get(PrType.R2REORDER.name()),PrType.R2REORDER.name());
		return option;
	}
	/*
	 * 拋轉prtype OPTION
	 */
	public static Map getTransPrTypeOption(){ 
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		Map prtypeOption=new TreeMap();
		prtypeOption.putAll(getALLPrTypeOption());
		prtypeOption.remove(bean.getParameterMap().get(PrType.R1CONTROL.name()));
		prtypeOption.remove(bean.getParameterMap().get(PrType.R2CONTROL.name()));
		return prtypeOption;
	}
	/*
	 * 申請需求用的prtype OPTION
	 */
	public static Map getApplyPrTypeOption(){
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		Map prtypeOption=new TreeMap();
		prtypeOption.putAll(getALLPrTypeOption());
		prtypeOption.remove(bean.getParameterMap().get(PrType.R1REORDER.name()));
		prtypeOption.remove(bean.getParameterMap().get(PrType.R2REORDER.name()));
		return prtypeOption;
	}
	

	public PrNumType getValue() {
        return value;
    }
}
