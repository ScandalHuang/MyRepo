package mro.app.locationMgmt.utils;

import mro.base.bo.LocationMapBO;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListLocationMgmtUtils {
    
    public static String validate(String organizationCode){
    	StringBuffer warnMessage=new StringBuffer();
    	
    	LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
    	
    	if(StringUtils.isBlank(organizationCode)){
    		warnMessage.append("ORGANIZATION_CODE為必填欄位!<br />");
    	}else if(locationMapBO.getLocationMapByOcode(organizationCode)!=null){
    		warnMessage.append("ORGANIZATION_CODE已存在!<br />");
    	}
    	
    	return warnMessage.toString();
    }
}
