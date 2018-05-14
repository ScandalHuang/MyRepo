package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.base.bo.PrtypeActiveMemberBO;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrtypeActiveMember;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListPrtypeActiveMemberBean")
@ViewScoped
public class ListPrtypeActiveMemberBean implements Serializable {
	private static final long serialVersionUID = -8139619830424124371L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private List<PrtypeActiveMember> listPrtypeActiveMember;
	private String selectPrtype;
	private LocationSiteMap sLocationSiteMap; //選取的LOCATION_SITE
	private PrtypeActiveMember[] sPrtypeActiveMember; //選取的清單
	private boolean editButton = false; // 編輯申請單

	private transient PrtypeActiveMemberBO prtypeActiveMemberBO;
	public ListPrtypeActiveMemberBean() {

	}

	@PostConstruct
	public void init() {
		inital();
		prtypeActiveMemberBO=SpringContextUtil.getBean(PrtypeActiveMemberBO.class);
	}

	// =========================================inital===============================================================
	public void inital() { // 開立新單
		listPrtypeActiveMember = new ArrayList<>();
	}

	// ================================Prtype==================================================================
	public void onSearch() {
		this.inital();
		if (StringUtils.isNotBlank(selectPrtype) && sLocationSiteMap!=null) {
			listPrtypeActiveMember = prtypeActiveMemberBO.getPrtypeActiveMemberList(
					sLocationSiteMap,selectPrtype, null);
		}
	}

	public void onSelectAll(){
		sPrtypeActiveMember=(PrtypeActiveMember[]) ApplyPrUtils.onSelectAll(
				sPrtypeActiveMember,listPrtypeActiveMember);
	}
	public void onDelete(){
		GlobalGrowl message = new GlobalGrowl();
		prtypeActiveMemberBO.onDelete(sPrtypeActiveMember);
		message.addInfoMessage("Delete", "Delete successful!");
		this.onSearch();
	}
	public void uploadExcel(File file) throws Exception {
		if (file != null && StringUtils.isNotBlank(selectPrtype)) {				
				PrtypeActiveMember prtypeActiveMember=new PrtypeActiveMember();
				prtypeActiveMember.setLastupdateBy(loginInfoBean.getEmpNo());
				prtypeActiveMember.setLastupdateDate(new Date(System.currentTimeMillis()));
				prtypeActiveMember.setPrtype(selectPrtype);
				prtypeActiveMember.setLocationSiteMap(sLocationSiteMap);
				
				FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
				if (fileUploadExcelInterfaces.uploadExcel(prtypeActiveMember,file,
						Arrays.asList("LOCATION_SITE","PRTYPE","EMPLOYEE_NUM"),true,true) == true) {
					this.onSearch();
				}
		}

	}

	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public List<PrtypeActiveMember> getListPrtypeActiveMember() {
		return listPrtypeActiveMember;
	}

	public void setListPrtypeActiveMember(
			List<PrtypeActiveMember> listPrtypeActiveMember) {
		this.listPrtypeActiveMember = listPrtypeActiveMember;
	}

	public String getSelectPrtype() {
		return selectPrtype;
	}

	public void setSelectPrtype(String selectPrtype) {
		this.selectPrtype = selectPrtype;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}

	public PrtypeActiveMember[] getsPrtypeActiveMember() {
		return sPrtypeActiveMember;
	}

	public void setsPrtypeActiveMember(PrtypeActiveMember[] sPrtypeActiveMember) {
		this.sPrtypeActiveMember = sPrtypeActiveMember;
	}
	
}
