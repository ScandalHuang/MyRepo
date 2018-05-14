package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.mcMgmtInterface.form.ReorderForm;
import mro.app.mcMgmtInterface.service.Impl.ReOrderImpl;
import mro.base.bo.LocationMapBO;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListItemR2ReOrderBean")
@ViewScoped
public class ListItemR2ReOrderBean implements Serializable {
	private static final long serialVersionUID = 2767638574924702145L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	private ReorderForm form;
	private ReOrderImpl reOrderImpl;
	private transient LocationMapBO locationMapBO;
	
	public ListItemR2ReOrderBean() {
	}

	@PostConstruct
	public void init() {
		locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		reOrderImpl=new ReOrderImpl();
		form=new ReorderForm();
		reOrderImpl.setParamter(form);
		this.initial();	
	}

	public void initial(){
		form.inital();
	}
	
	public void search() {
		reOrderImpl.search(form, loginInfoBean.getPerson());
	}
	
	public void onReOrder(){
		GlobalGrowl message = new GlobalGrowl();
		reOrderImpl.onReOrderToPR(form, loginInfoBean.getPerson(), loginInfoBean.getUserId(), message);
	}
	public void changeSite(){
		if(StringUtils.isBlank(form.getsLocationSite())){
			form.getOrganizationMap().clear();
			return;
		}
		form.setOrganizationMap(locationMapBO.getLocationMapOptionByCodeSite(
		locationMapBO.getLocationMapList(form.getsLocationSite())));
	}

	public void onSelectAll() { // 選取全部申請單
		form.setSelectlistReOrderItem((Map[])ApplyPrUtils.onSelectAll(
				form.getSelectlistReOrderItem(), form.getListReOrderItem()));
	}

	// ==========================================================================================
	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ReorderForm getForm() {
		return form;
	}

	public void setForm(ReorderForm form) {
		this.form = form;
	}

}
