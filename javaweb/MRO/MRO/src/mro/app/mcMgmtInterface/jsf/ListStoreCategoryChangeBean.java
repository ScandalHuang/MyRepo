package mro.app.mcMgmtInterface.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.jsf.FileUploadExcelBean;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.mcMgmtInterface.service.Impl.ListStoreCategoryChangeImpl;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.InventoryBO;
import mro.base.bo.LocationMapBO;
import mro.base.entity.Inventory;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "ListStoreCategoryChangeBean")
@ViewScoped
public class ListStoreCategoryChangeBean implements Serializable {
	private static final long serialVersionUID = 6777115897690985052L;

	@ManagedProperty(value = "#{FileUploadExcelBean}")
	private FileUploadExcelBean fileUploadExcelBean;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private List list;
	private Map organizationMap;
	private Map prtypeOption;
	private Map controlShow; //說明
	private Map controlOption; //說明
	private String selectOrganization;
	private String sLocationSite;
	private String prtype;
	private String selectitemnum;
	private String[] selectControlType;
	private String diffControlType;
	

	public ListStoreCategoryChangeBean() {

	}

	@PostConstruct
	public void init() {
		prtypeOption=PrType.getReorderOption();
		controlShow=new TreeMap(Utility.swapMap(ParameterType.CONTROL_TYPE.getOption()));
		controlOption=ParameterType.CONTROL_TYPE.getOption();
	}

	public void search() {
		InventoryBO inventoryBO=SpringContextUtil.getBean(InventoryBO.class);
		list=inventoryBO.getInventorys(sLocationSite, selectOrganization, prtype, selectitemnum, selectControlType, diffControlType, true);
	}
	
	public void changeSite(){
		if(StringUtils.isBlank(sLocationSite)){
			organizationMap.clear();
			return;
		}
		LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		organizationMap=locationMapBO.getLocationMapOptionBySite(
				locationMapBO.getLocationMapList(sLocationSite));
	}
	
	public void uploadExcel(File file) {
		List<String> keys=Arrays.asList("ITEMNUM","LOCATION","SITEID");
		FileUploadExceImpl uploadImpl = new FileUploadExceImpl();
		List<Inventory> list=uploadImpl.converList(new Inventory(), file, keys,new GlobalGrowl());
		if (list!=null && list.stream().filter(l -> !loginInfoBean.validateUserLSMap(l.getSiteid()))
				.findAny().orElse(null) != null) {
			GlobalGrowl msg=new GlobalGrowl();
			msg.addErrorMessage("上傳錯誤!","EXCLE內有非權限的SITEID");
		}else{		
			if (uploadImpl.uploadExcel(list,keys,false,true,
					ListStoreCategoryChangeImpl.class) == true) {
				this.search();
			}
		}
	}

	// ==========================================================================================


	public FileUploadExcelBean getFileUploadExcelBean() {
		return fileUploadExcelBean;
	}

	public void setFileUploadExcelBean(FileUploadExcelBean fileUploadExcelBean) {
		this.fileUploadExcelBean = fileUploadExcelBean;
	}

	public Map getOrganizationMap() {
		return organizationMap;
	}

	public void setOrganizationMap(Map organizationMap) {
		this.organizationMap = organizationMap;
	}

	public String getSelectOrganization() {
		return selectOrganization;
	}

	public void setSelectOrganization(String selectOrganization) {
		this.selectOrganization = selectOrganization;
	}

	public String getSelectitemnum() {
		return selectitemnum;
	}

	public void setSelectitemnum(String selectitemnum) {
		this.selectitemnum = selectitemnum;
	}

	public Map getPrtypeOption() {
		return prtypeOption;
	}

	public void setPrtypeOption(Map prtypeOption) {
		this.prtypeOption = prtypeOption;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

	public Map getControlShow() {
		return controlShow;
	}

	public void setControlShow(Map controlShow) {
		this.controlShow = controlShow;
	}

	public String getPrtype() {
		return prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public Map getControlOption() {
		return controlOption;
	}

	public void setControlOption(Map controlOption) {
		this.controlOption = controlOption;
	}

	public String[] getSelectControlType() {
		return selectControlType;
	}

	public void setSelectControlType(String[] selectControlType) {
		this.selectControlType = selectControlType;
	}

	public String getDiffControlType() {
		return diffControlType;
	}

	public void setDiffControlType(String diffControlType) {
		this.diffControlType = diffControlType;
	}
	
}
