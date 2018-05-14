package mro.app.commonview.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.inx.commons.jsf.GlobalGrowl;

public interface FileUploadExcelInterfaces {
	
	public void message(List entities);
	
	public boolean message(GlobalGrowl globalGrowl,String msg);

	public List converList(Object entity,File file,List<String> keyList,GlobalGrowl globalGrowl);  //file 轉換成list
	
	public boolean uploadExcel(Object entity, File file,List<String> keyList,
			boolean deleteFlag,boolean message);
	
	public boolean uploadExcel(Object entity, File file,List<String> keyList,
			boolean deleteFlag,boolean message,Class clazz);
	
	public boolean uploadExcel(List entities, List<String> keyList,
			boolean deleteFlag,boolean message, Class clazz);
	
	public List uploadExcelToList(Object entity, File file,List<String> keyList,
			boolean deleteFlag,boolean message,Class clazz);
	
	public List uploadExcelToList(List entities,List<String> keyList,
			boolean deleteFlag,Class clazz);
	
	public Object convertValue(HSSFCell cell,String column,Map<String,Class> entityType);
	
	/** 驗證entity內 key List是否有重複 */
	public String validatDuplicate(List entities,List<String> keyList,Map<String,String> entityMap);

	/** 驗證entity內 key 是否都有值 */
	public String validateKey(List entities,List<String> keyList,Map<String,String> entityMap);

}
