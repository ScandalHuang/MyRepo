package mro.utility;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

public class JsfContextUtils {
	public static void updateView(String view){
		if(StringUtils.isNotBlank(view)){
			RequestContext context = RequestContext.getCurrentInstance();  
			context.update(view);
		}
	}
	
	public static void executeView(String view){
		if(StringUtils.isNotBlank(view)){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute(view);
		}
	}
}
