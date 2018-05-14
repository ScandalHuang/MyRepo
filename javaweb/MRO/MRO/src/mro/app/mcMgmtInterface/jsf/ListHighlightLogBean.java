package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.bo.ListItemHighlightBO;
import mro.app.mcMgmtInterface.form.HighlightForm;
import mro.base.bo.InvbalancesHighlightLogBO;
import mro.base.entity.InvbalancesHighlightLog;
import mro.base.entity.Pr;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListHighlightLogBean")
@ViewScoped
public class ListHighlightLogBean implements Serializable {
	private static final long serialVersionUID = 6414160351664691513L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private HighlightForm highlightForm;
	private transient InvbalancesHighlightLogBO invbalancesHighlightLogBO;
	private transient ListItemHighlightBO listItemHighlightBO;
	
	public ListHighlightLogBean() {

	}

	@PostConstruct
	public void init() {
		highlightForm=new HighlightForm();
		invbalancesHighlightLogBO=SpringContextUtil.getBean(InvbalancesHighlightLogBO.class);
		listItemHighlightBO=SpringContextUtil.getBean(ListItemHighlightBO.class);
		highlightForm.setParameter();
	}
	
	public void searchLog() {
		highlightForm.intial();
		highlightForm.setListInvbalancesHighlightLog(
				invbalancesHighlightLogBO.getList(
				highlightForm.getMroInvbalancesHighlightV().getItemnum(),
				highlightForm.getMroInvbalancesHighlightV().getBinnum(),
				highlightForm.getMroInvbalancesHighlightV().getSiteid(),
				new BigDecimal("0")));
		highlightForm.setInvbalancesHighlightLog(new InvbalancesHighlightLog());
		highlightForm.setChangeQty();
	}
	
	public void onUpdate(String action){
		GlobalGrowl message = new GlobalGrowl();
			if(listItemHighlightBO.updateHighLightLog(highlightForm, loginInfoBean.getPerson(),action,message)){
				Pr pr=highlightForm.getInvbalancesHighlightLog().getPr();
				String title=pr!=null?"<br/>PRNUM:"+pr.getPrnum():"";
				message.addInfoMessage("Successful", "Update successful."+title);
				this.searchLog();
			}
			highlightForm.setUpdatePage(true);
	}
	public void onDelete(InvbalancesHighlightLog invbalancesHighlightLog){
		GlobalGrowl message = new GlobalGrowl();
		listItemHighlightBO.deleteHighLightLog(invbalancesHighlightLog, loginInfoBean.getPerson());
		this.searchLog();
		message.addInfoMessage("Successful", "Delete successful.");
	}
	
	public void onUpdatePage() throws NoSuchMethodException, 
		SecurityException, IllegalAccessException, InvocationTargetException{
		if(highlightForm.getObject()!=null && highlightForm.isUpdatePage()){
			Method method=highlightForm.getObject().getClass().getMethod("search");
			method.invoke(highlightForm.getObject());
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(highlightForm.getUpdateView())){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(highlightForm.getUpdateView());
			}
		}
	}
	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public HighlightForm getHighlightForm() {
		return highlightForm;
	}

	public void setHighlightForm(HighlightForm highlightForm) {
		this.highlightForm = highlightForm;
	}

}
