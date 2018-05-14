package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import mro.app.commonview.jsf.ListItemCommonBean;
import mro.app.mcMgmtInterface.bo.ListLampControlBO;
import mro.app.mcMgmtInterface.form.LampControlForm;
import mro.app.mcMgmtInterface.vo.LampList;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.bo.LampControlHeaderBO;
import mro.base.entity.Item;
import mro.base.entity.LampControlHeader;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.hibernate.ActiveFlag;

@ManagedBean(name = "ListLampControlBean")
@ViewScoped
public class ListLampControlBean implements Serializable {
	private static final long serialVersionUID = 2001412729372987279L;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;
	
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private LampControlForm lampControlForm;
	private transient LampControlHeaderBO lampControlHeaderBO;
	private transient ListLampControlBO listLampControlBO;
	
	
	public ListLampControlBean() {

	}

	@PostConstruct
	public void init() {
		lampControlHeaderBO=SpringContextUtil.getBean(LampControlHeaderBO.class);
		listLampControlBO=SpringContextUtil.getBean(ListLampControlBO.class);
		lampControlForm =new LampControlForm(loginInfoBean.getPerson(),4);
		//==========================料號搜索條件=========================================
		listItemCommonBean.setClassstructure(ItemStatusType.TYPE_ACS);
		listItemCommonBean.setLocationSiteInfo(
				loginInfoBean.getLocationMap().getLocationSiteMap().getLocationSite(),
				LocationSiteActionType.I);
	}
	
	public void search() {
		lampControlForm.intial();
		lampControlForm.setList(lampControlHeaderBO.getListALL(
				lampControlForm.getItemnum(), lampControlForm.getOrganizationCode(),
				lampControlForm.getDeptCode(), lampControlForm.getCreateDate(), 
				ActiveFlag.getValue(lampControlForm.getActiveFlag()),
				lampControlForm.getWeekRage()));
	}
	public void onEditLine(RowEditEvent event) {// 更新需求量
		GlobalGrowl message = new GlobalGrowl();
		LampList lampList=(LampList) event.getObject();
		listLampControlBO.update(lampList, loginInfoBean.getPerson());
		message.addInfoMessage("Update", "料號:"+lampList.getHeader().getItemnum()+"更新成功!");
	}
	//================取得料號==============================
	public void setSelectItem(Item item) { 
		lampControlForm.addList(lampControlHeaderBO.onAdd(
				new LampControlHeader(), lampControlForm, item, loginInfoBean.getPerson()));
	}
	// ==========================================================================================

	public ListItemCommonBean getListItemCommonBean() {
		return listItemCommonBean;
	}

	public void setListItemCommonBean(ListItemCommonBean listItemCommonBean) {
		this.listItemCommonBean = listItemCommonBean;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public LampControlForm getLampControlForm() {
		return lampControlForm;
	}

	public void setLampControlForm(LampControlForm lampControlForm) {
		this.lampControlForm = lampControlForm;
	}

}
