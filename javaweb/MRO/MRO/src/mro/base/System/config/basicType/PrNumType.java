package mro.base.System.config.basicType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public enum PrNumType {
	RM,  //控管請購
	RC,  //一般請購
	RR;  //重訂購
	
	public static List<String> getPrnumTypeAll(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<String> list=new ArrayList<String>();
		for(PrNumType p:PrNumType.values()){
			list.add(p.toString()+"_"+sdf.format(new Date()));
		}
		return list;
	}
}
