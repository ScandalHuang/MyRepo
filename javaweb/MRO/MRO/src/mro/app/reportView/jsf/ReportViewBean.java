package mro.app.reportView.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="ReportViewBean")
@ViewScoped
public class ReportViewBean implements Serializable {
	private static final long serialVersionUID = -3110929921077871998L;
	private String url;
	private String name; 
	
	public ReportViewBean() {
	}
	
	@PostConstruct
	public void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		//ReportServerBO reportServerBO =SpringContextUtil.getBean(ReportServerBO.class);
		name= paramMap.get("NAME");  //REPORT ID
		//url=reportServerBO.getReportServer(new BigDecimal("2")).getUrl()+"?NAME="+name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
