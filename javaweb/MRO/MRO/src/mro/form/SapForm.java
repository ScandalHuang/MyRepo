package mro.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.base.entity.ItemSiteTransferLog;
import mro.base.entity.SapAccessLog;

public class SapForm implements Serializable{
	private static final long serialVersionUID = -4669772086516859408L;
	private List<SapAccessLog> listLogList;
	private List<ItemSiteTransferLog> listItemSiteTransferLog;
	private List listErpTrans;
	
	private List listSapAccessLogPn;
	
	private List listSapAccessLogEq;
	
	private Date beginDate;
	private Date endDate;
	private String status;
	private String material;
	private String applyCategory;
	
	private Date beginDate2;
	private Date endDate2;
	private String status2;
	private String in21;
	private String material2;
	
	private String material3;
	private Date beginDate3;
	private Date endDate3;
	private String status3;
	
	private Date beginDate4;
	private Date endDate4;
	private String status4;
	private String material4;
	
	private String addSite;
	private String addPlantCode;
	private String addItemnum;
	private String addType;
	private Map plantCodeMap;
	
	public SapForm(){
		
	}
	
	public List<SapAccessLog> getListLogList() {
		return listLogList;
	}

	public void setListLogList(List<SapAccessLog> listLogList) {
		this.listLogList = listLogList;
	}

	public List getListSapAccessLogPn() {
		return listSapAccessLogPn;
	}

	public void setListSapAccessLogPn(List listSapAccessLogPn) {
		this.listSapAccessLogPn = listSapAccessLogPn;
	}

	public List<ItemSiteTransferLog> getListItemSiteTransferLog() {
		return listItemSiteTransferLog;
	}

	public void setListItemSiteTransferLog(
			List<ItemSiteTransferLog> listItemSiteTransferLog) {
		this.listItemSiteTransferLog = listItemSiteTransferLog;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getApplyCategory() {
		return applyCategory;
	}

	public void setApplyCategory(String applyCategory) {
		this.applyCategory = applyCategory;
	}

	public Date getBeginDate2() {
		return beginDate2;
	}

	public void setBeginDate2(Date beginDate2) {
		this.beginDate2 = beginDate2;
	}

	public Date getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(Date endDate2) {
		this.endDate2 = endDate2;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getMaterial2() {
		return material2;
	}

	public void setMaterial2(String material2) {
		this.material2 = material2;
	}

	public String getAddSite() {
		return addSite;
	}

	public void setAddSite(String addSite) {
		this.addSite = addSite;
	}

	public String getAddPlantCode() {
		return addPlantCode;
	}

	public void setAddPlantCode(String addPlantCode) {
		this.addPlantCode = addPlantCode;
	}

	public String getAddItemnum() {
		return addItemnum;
	}

	public void setAddItemnum(String addItemnum) {
		this.addItemnum = addItemnum;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public Map getPlantCodeMap() {
		return plantCodeMap;
	}

	public void setPlantCodeMap(Map plantCodeMap) {
		this.plantCodeMap = plantCodeMap;
	}

	public Date getBeginDate3() {
		return beginDate3;
	}

	public void setBeginDate3(Date beginDate3) {
		this.beginDate3 = beginDate3;
	}

	public Date getEndDate3() {
		return endDate3;
	}

	public void setEndDate3(Date endDate3) {
		this.endDate3 = endDate3;
	}

	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}

	public String getMaterial3() {
		return material3;
	}

	public void setMaterial3(String material3) {
		this.material3 = material3;
	}

	public List getListErpTrans() {
		return listErpTrans;
	}

	public void setListErpTrans(List listErpTrans) {
		this.listErpTrans = listErpTrans;
	}
	public String getIn21() {
		return in21;
	}
	public void setIn21(String in21) {
		this.in21 = in21;
	}
	public Date getBeginDate4() {
		return beginDate4;
	}
	public void setBeginDate4(Date beginDate4) {
		this.beginDate4 = beginDate4;
	}
	public Date getEndDate4() {
		return endDate4;
	}
	public void setEndDate4(Date endDate4) {
		this.endDate4 = endDate4;
	}
	public String getStatus4() {
		return status4;
	}
	public void setStatus4(String status4) {
		this.status4 = status4;
	}
	public String getMaterial4() {
		return material4;
	}
	public void setMaterial4(String material4) {
		this.material4 = material4;
	}
	public List getListSapAccessLogEq() {
		return listSapAccessLogEq;
	}
	public void setListSapAccessLogEq(List listSapAccessLogEq) {
		this.listSapAccessLogEq = listSapAccessLogEq;
	}
	
}
