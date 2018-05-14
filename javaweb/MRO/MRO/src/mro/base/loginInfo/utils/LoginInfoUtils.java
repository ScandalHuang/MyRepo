package mro.base.loginInfo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inx.base.bo.MenuSystemMenuGroupBO;
import com.inx.base.entity.SystemMenuGroup;
import com.inx.commons.util.SpringContextUtil;

public class LoginInfoUtils {

	public static boolean getRoleValidate(Map systemMenuGroupMapAll,Object... object){
		for(Object o:object){
			if(systemMenuGroupMapAll.get(o)!=null){
				return true;
			}
		}
		return false;
	}
	public static Map getRoleMap(String empNo){
		MenuSystemMenuGroupBO mSystemMenuGroupBO=SpringContextUtil.getBean(MenuSystemMenuGroupBO.class);
    	Map map=new HashMap<>();
    	List<SystemMenuGroup> list=mSystemMenuGroupBO.getListByEmp(empNo);
    	for(SystemMenuGroup l:list){
    		map.put(l.getGroupName(), l.getGroupDesc());
    	}
		return map;
	}
}
