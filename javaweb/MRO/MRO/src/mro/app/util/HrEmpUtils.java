package mro.app.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.base.bo.HrEmpBO;
import mro.base.entity.HrEmp;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class HrEmpUtils {

	public static List<String> getActiveList(List<String> actors){
		List<String> list=new ArrayList<String>();
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		if(Utility.isNotEmpty(actors)){
			for(HrEmp hremp:hrEmpBO.getActiveHrEmp(actors)){
				list.add(hremp.getEmpNo());
			}
		}
		return list;
	}
	public static Map<String,String> getMapList(List<String> actors){
		Map<String, String> map = new LinkedHashMap<String, String>();
		HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		if(Utility.isNotEmpty(actors)){
			for(HrEmp hremp:hrEmpBO.getHrEmpList(actors)){
				map.put(hremp.getEmpNo(),hremp.getName()); 
			}
		}
		return map;
	}
}
