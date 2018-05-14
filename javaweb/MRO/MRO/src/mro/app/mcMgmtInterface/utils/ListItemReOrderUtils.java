package mro.app.mcMgmtInterface.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import mro.base.entity.Person;

import com.inx.commons.jsf.GlobalGrowl;

public class ListItemReOrderUtils {

	public static Map onReorderTransferToPr(Map[] selectlistReOrderItem){
		Map<String,List> map=new LinkedHashMap<String,List>();
		for(Map m:selectlistReOrderItem){ 
			if(map.get(m.get("SITEID"))==null){
				map.put(m.get("SITEID").toString(), new ArrayList<>());
			}
			map.get(m.get("SITEID")).add(m);
		}
		return map;
	}
	// ==========================================驗證=====================================================
	public static boolean validate(Map[] selectlistReOrderItem,GlobalGrowl message) {
		StringBuffer warnMessage=new StringBuffer();
		if(selectlistReOrderItem.length==0){
			warnMessage.append("請選取重訂購料號!<br/>");
		}else{
			warnMessage.append(validateQty(selectlistReOrderItem));
		}
		if (warnMessage.length() > 0) {
			message.addErrorMessage("Error",warnMessage.toString());
			return false;
		} else {
			return true;
		}
	}
	//===驗證總開單量是否等於個別開單量
	public static String validateQty(Map[] selectlistReOrderItem){
		StringBuffer warnMessage=new StringBuffer();
		Map<BigDecimal,BigDecimal> oriQty=new HashMap<BigDecimal, BigDecimal>();
		Map<BigDecimal,BigDecimal> newQty=new HashMap<BigDecimal, BigDecimal>();
		Map<BigDecimal,Map> newMap=new HashMap<BigDecimal, Map>();
		Stream.of(selectlistReOrderItem).forEach(l->{
			BigDecimal inventoryid=(BigDecimal)l.get("INVENTORYID");
			BigDecimal newRateQty=(BigDecimal)l.get("NEW_RATE_QTY");
			oriQty.put(inventoryid, (BigDecimal)l.get("REORDER_QTY"));
			newMap.put(inventoryid, l);
			newQty.put(inventoryid, newQty.get(inventoryid)==null?newRateQty:newQty.get(inventoryid).add(newRateQty));
		});
		oriQty.forEach((k,v)->{
			if(v.compareTo(newQty.get(k))!=0){
				Map map=newMap.get(k);
				warnMessage.append("料號:"+map.get("ITEMNUM")+",倉別:"+map.get("LOCATION")+",未全部選取!<br/>");
			}
		});
		return warnMessage.toString();
	}
}
