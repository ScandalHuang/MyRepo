package mro.utility;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {
	/**
	 * 取得Object的變數 List<變數名稱>
	 * @param clazz :entity
	 */
	public static List<String> getObjectMap(Class clazz){
		List<String> list=new ArrayList<String>();
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor pd : info.getPropertyDescriptors()){
				list.add(pd.getName());
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}
}
