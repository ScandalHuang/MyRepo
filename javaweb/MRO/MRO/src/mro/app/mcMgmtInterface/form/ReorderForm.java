package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReorderForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private List listReOrderItem; // 重訂購料號

	private Map organizationMap; // 廠區選單(siteid)
	private Map controlMap; // 控管模式選單
	private Map prtypeMap; // prtype選單

	private Map[] selectlistReOrderItem; // 選取重訂購料號

	private String sLocationSite;
	private String[] selectOrganization;
	private String sPrtype;
	private String selectitemnum;
	private String selectcmommcontrol;
	private Date needDate; // 轉單需求日

	public ReorderForm() {
		this.inital();
	}

	public void inital() {
		listReOrderItem = null;
		selectlistReOrderItem = null;
		needDate = null;
	}

	public List getListReOrderItem() {
		return listReOrderItem;
	}

	public void setListReOrderItem(List listReOrderItem) {
		this.listReOrderItem = listReOrderItem;
	}

	public Map getOrganizationMap() {
		return organizationMap;
	}

	public void setOrganizationMap(Map organizationMap) {
		this.organizationMap = organizationMap;
	}

	public Map getControlMap() {
		return controlMap;
	}

	public void setControlMap(Map controlMap) {
		this.controlMap = controlMap;
	}

	public Map[] getSelectlistReOrderItem() {
		return selectlistReOrderItem;
	}

	public void setSelectlistReOrderItem(Map[] selectlistReOrderItem) {
		this.selectlistReOrderItem = selectlistReOrderItem;
	}

	public String getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

	public String[] getSelectOrganization() {
		return selectOrganization;
	}

	public void setSelectOrganization(String[] selectOrganization) {
		this.selectOrganization = selectOrganization;
	}

	public String getSelectitemnum() {
		return selectitemnum;
	}

	public void setSelectitemnum(String selectitemnum) {
		this.selectitemnum = selectitemnum;
	}

	public String getSelectcmommcontrol() {
		return selectcmommcontrol;
	}

	public void setSelectcmommcontrol(String selectcmommcontrol) {
		this.selectcmommcontrol = selectcmommcontrol;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public String getsPrtype() {
		return sPrtype;
	}

	public void setsPrtype(String sPrtype) {
		this.sPrtype = sPrtype;
	}

	public Map getPrtypeMap() {
		return prtypeMap;
	}

	public void setPrtypeMap(Map prtypeMap) {
		this.prtypeMap = prtypeMap;
	}

}
