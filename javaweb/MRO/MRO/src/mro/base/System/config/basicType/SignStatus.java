package mro.base.System.config.basicType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.System.config.jsf.SystemConfigBean;

import com.inx.commons.util.JsfContextUtil;

public enum SignStatus {
	NEW,
	APPR,
	ACTIVE,
	STOPUSE,
	INPRG,
	REJECT,
	CHANGED,
	DRAFT,
	CAN,
	OPEN,
	CLOSE,
	COMB,
	SYNC,
	TRANSFER;
	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
	public  boolean editStatus(){
		if(name().equals(SignStatus.NEW.name())||
				name().equals(SignStatus.REJECT.name())||
				name().equals(SignStatus.DRAFT.name())){
			return true;
		}
		return false;
	}
	
	public static Map getStatusOpetion(List<String> statuses){
		Map<String, String> statusOption = new HashMap<String, String>();
		SystemConfigBean bean = JsfContextUtil
				.getBean(SystemConfigBean.class.getSimpleName());
		for (String staus : statuses) {
			statusOption.put(bean.getParameterMap().get(staus), staus);
		}
		return statusOption;
	}
}
