package mro.app.sapAccess.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.jsf.ListItemCommonBean;
import mro.app.plantAttribute.bo.ListSapPlantAttributeBO;
import mro.app.sapAccess.bo.SapAccessQueryBo;
import mro.app.sapAccess.utils.SapAccessQueryUtils;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.bo.SapAccessLogBO;
import mro.base.entity.Item;
import mro.base.entity.ItemSiteTransferLog;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.SapForm;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="SapAccessQueryBean")
@ViewScoped
public class SapAccessQueryBean implements Serializable {
	private static final long serialVersionUID = -3039037568386837556L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	 
	@ManagedProperty(value = "#{ListItemCommonBean}")
	private ListItemCommonBean listItemCommonBean;
	
	private SapForm sapForm;
	private transient SapAccessLogBO sapAccessLogBO;
 	
	public SapAccessQueryBean() {

	}

	@PostConstruct
	public void init() {
		sapAccessLogBO=SpringContextUtil.getBean(SapAccessLogBO.class);
		sapForm = new SapForm();
		ListSapPlantAttributeBO listSapPlantAttributeBO = SpringContextUtil
				.getBean(ListSapPlantAttributeBO.class);
		sapForm.setPlantCodeMap(listSapPlantAttributeBO.getPlantCode());
		//itemCommon傳送條件
		listItemCommonBean.setClassstructure(ItemStatusType.TYPE_AC);
	}

	// ==========================================action===============================================
	public void onSearch() {  //SAP傳檔查詢
		SapAccessQueryBo sapAccessQueryBo=SpringContextUtil.getBean(SapAccessQueryBo.class);
		sapForm.setListItemSiteTransferLog(sapAccessQueryBo.getItemSiteTransferLogList(sapForm));
	}
	public void onSearchPN() {  //SAP_PN傳檔查尋
		SapAccessQueryBo sapAccessQueryBo=SpringContextUtil.getBean(SapAccessQueryBo.class);
		sapForm.setListSapAccessLogPn(sapAccessQueryBo.getSapLogPNList(sapForm));
	}
	public void onSearchEQ() {  //SAP_PN傳檔查尋
		SapAccessQueryBo sapAccessQueryBo=SpringContextUtil.getBean(SapAccessQueryBo.class);
		sapForm.setListSapAccessLogEq(sapAccessQueryBo.getSapLogEqList(sapForm));
	}
	public void getSapLogList(ItemSiteTransferLog isLog) { //SAP log傳檔查詢
		sapForm.setListLogList(sapAccessLogBO.getList(isLog.getItemnum(), 
				isLog.getPlantCode(),isLog.getStorageLocation(), isLog.getMaterialGroup()));
	} 
	public void addItemSiteTransferLog() {  //料號重傳
		GlobalGrowl message = new GlobalGrowl();
		SapAccessQueryBo sapAccessQueryBo=SpringContextUtil.getBean(SapAccessQueryBo.class);
		String errorInfo=SapAccessQueryUtils.vaildate(sapForm);
		if(errorInfo.length()==0){
			if(sapForm.getAddType().equals("bySite")){
					sapAccessQueryBo.addItemSiteTransferLogBySite(sapForm, loginInfoBean.getEmpNo());
			}else if(sapForm.getAddType().equals("byPlant")){
					sapAccessQueryBo.addItemSiteTransferLogByPlantCode(sapForm, loginInfoBean.getEmpNo());
			}
			message.addInfoMessage("Success", "SAP重傳新增成功，系統將會自動轉拋SAP，請於隔日在確認!");
		}else{
			message.addErrorMessage("ERROR",errorInfo);
		}
	}
	public void setSelectItem(Item item) { // 取得料號
		if(item!=null){
			sapForm.setAddItemnum(item.getItemnum());
		}
	}
//====================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public ListItemCommonBean getListItemCommonBean() {
		return listItemCommonBean;
	}

	public void setListItemCommonBean(ListItemCommonBean listItemCommonBean) {
		this.listItemCommonBean = listItemCommonBean;
	}

	public SapForm getSapForm() {
		return sapForm;
	}

	public void setSapForm(SapForm sapForm) {
		this.sapForm = sapForm;
	}
 
}
