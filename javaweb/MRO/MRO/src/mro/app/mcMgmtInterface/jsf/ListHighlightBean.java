package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.jsf.ListItemCommonBean;
import mro.app.mcMgmtInterface.bo.ListItemHighlightBO;
import mro.app.mcMgmtInterface.bo.ListStoreCategoryChangeBO;
import mro.app.mcMgmtInterface.form.HighlightForm;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.bo.LocationMapBO;
import mro.base.entity.Item;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListHighlightBean")
@ViewScoped
public class ListHighlightBean implements Serializable {
	private static final long serialVersionUID = 4903505052035387366L;

	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;
	
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private HighlightForm highlightForm;
	private LocationMapBO locationMapBO;
	
	
	public ListHighlightBean() {

	}

	@PostConstruct
	public void init() {
		LocationMapBO locationMapBO = SpringContextUtil.getBean(LocationMapBO.class);
		highlightForm =new HighlightForm();
		highlightForm.setParameter();
		
		
		//2016/10/13 由tw_oracle改為site 權限
		highlightForm.setOrganizationOption(locationMapBO.getLocationMapOptionBySite(
				locationMapBO.getLocationMapList(new ArrayList(loginInfoBean.getUserLSMap().values()))));
		
		
		
		if(loginInfoBean.isMcRole() || loginInfoBean.isAdminRole()){ //物管管理介面
			highlightForm.setEditButton(true);
		}else{
			highlightForm.setSelectDeptcode(loginInfoBean.getPerson().getMDeptCode());
			highlightForm.setEditButton(false);
		}
		listItemCommonBean.setClassstructure(ItemStatusType.TYPE_ACSS);
	}
	
	public void search() {
		highlightForm.intial();
		ListItemHighlightBO listItemHighlightBO=SpringContextUtil.getBean(ListItemHighlightBO.class);
		highlightForm.setListHighlight(listItemHighlightBO.getHighlightList(highlightForm)); 
	}

	
	//==========================================================================================
	//================取得料號==============================
	public void setSelectItem(Item item) { 
		highlightForm.setSelectitemnum(item.getItemnum());
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

	public HighlightForm getHighlightForm() {
		return highlightForm;
	}

	public void setHighlightForm(HighlightForm highlightForm) {
		this.highlightForm = highlightForm;
	}

}
