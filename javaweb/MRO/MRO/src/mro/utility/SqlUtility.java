package mro.utility;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
*
* @author Administrator
*/
public class SqlUtility {
    /**
     * 產生SQL 條件
     * example : "pro1=:pro1"
     * @param param  條件參數
     * @return
     */
	public static String getCondition(Map param) {
		StringBuffer condition = new StringBuffer();
		Iterator iter = param.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Entry) iter.next();
			if (entry.getValue() != null) {
				Object key = entry.getKey();
				String value = ":" + key;
				if(entry.getValue().toString().toUpperCase().indexOf("UPPER")!=-1){
					key="UPPER("+key+")";
				}

				if (entry.getValue() instanceof List || entry.getValue() instanceof String[]) {
					condition.append(" and " + key.toString() + " in (" + value
							+ ") ");
				}else if(entry.getValue() instanceof Date){
					condition.append(" and trunc(" + key.toString() + ") = trunc(" + value
							+ ") ");
				} else if (entry.getValue().toString().indexOf("%") != -1) {
					condition.append(" and " + key.toString() + " like "
							+ value + " ");
				} else if (entry.getValue().toString().indexOf("null") != -1) {
					condition.append(" and " + key.toString() +  value + " ");
				}  else {
					condition.append(" and " + key.toString() + " = " + value
							+ " ");
				}
			}
		}
		return condition.toString();
	}

    /**
     *條件Map 
     * @param columnName 欄位名稱
     * @param object 數值
     * @return
     */
	public static Map getSqlParameter(String[] columnName, Object... object) {
		Map map = new LinkedHashMap<>();
		if (columnName != null) {
			int i=0;
			for (Object o : object) {
				map.put(columnName[i], o);
				i++;
			}
		}
		return map;
	}
}
