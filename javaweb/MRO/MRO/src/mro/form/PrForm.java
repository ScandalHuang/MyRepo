package mro.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.entity.PrMcParameter;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.LocationMapBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.bo.PersonBO;
import mro.base.bo.PrControlConfigBO;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;


public class PrForm extends PrSearchForm implements Serializable{
	
	private static final long serialVersionUID = 1250650963106912128L;
	
	private List<Prline> listPrline;
	private List<Prline> listDeletePrline;
	private List<Prline> listClosePrline;  //簽核被退回的PRline
	private List<Prline> listCombinePrline;  //簽核有壓數量PRline
	private Prline[] deleteListPrline;
	private Pr pr;
	private LocationMap locationMap; //pr對應的locationMap;
	private LocationSiteMap locationSiteMap; //pr對應的locationSiteMap;
	private PrControlConfig prControlConfig; //pr對應的控管設定;
	private PrMcParameter prMcParameter;
	public Map dowloadFileMap;
	public FileCategory fileHeaderType;
	public FileCategory fileLineType;
	private Long employeeType; // 人員搜索Type
	private long maxPrlineNum; // prline 項次
	private BigDecimal unUseBudget;  //可使用預算
	private boolean eqFlag;
	private boolean reasonFlag;
	private Map lineErrorMap;
	private Map prNameMap;//pr 相關人員姓名

	private long activeIndex;// 供應商清單 page
	private boolean editButton; // 編輯申請單
	private String signPreViewUrl; //簽核預覽網址
	private String signHistoryUrl; //簽核歷程網址
	
	private Person personForward; // 轉呈人員
	private String transferInfo; // 轉呈人員
	private String signComment; // 簽核意見

	
	private Pr[] prs;
	private List<String> notifyList;
	private String mailContent;
	
	public PrForm(){
		this.prInital();
	}
	public void prInital(){
		listPrline = new ArrayList<Prline>();
		listDeletePrline = new ArrayList<Prline>();
		listClosePrline = new  ArrayList<Prline>();
		listCombinePrline = new  ArrayList<Prline>();
		deleteListPrline=null;
		pr = new Pr();
		locationMap=null;
		prControlConfig=null;
		prMcParameter =null;
		dowloadFileMap = FileCategory.getDownloadFileMap();
		employeeType=null;
		maxPrlineNum = 1;
		activeIndex = 0;
		editButton = false;
		signPreViewUrl="";
		signHistoryUrl="";
		personForward=null;
		transferInfo="";
		signComment="";
		prs=null;
		eqFlag=false;
		reasonFlag=false;
		mailContent="";
		notifyList=null;
		lineErrorMap = new LinkedHashMap<>();
		fileHeaderType=null;
		fileLineType=null;
		prNameMap= new LinkedHashMap<>();
		unUseBudget=null;
	}
	/* 2016/05/10
	 * requestedby2 資訊更新
	 */
	public void onNewPr(Person person,String prType,String siteid,String deptcode,String requestedby2){
		this.onNewPr(person, prType);
		if(StringUtils.isNotBlank(siteid)){
			pr.setSiteid(siteid);
		}
		if(StringUtils.isNotBlank(deptcode)){
			pr.setDeptcode(deptcode);
			pr.setMDept(deptcode);
		}
		if(StringUtils.isNotBlank(requestedby2)){
			PersonBO bo=SpringContextUtil.getBean(PersonBO.class);
			Person person2=bo.getPerson(requestedby2);
			pr.setRequestedby2(person2.getPersonId());
			pr.setDeptsupervisor(StringUtils.isNotBlank(person2.getSupervisor())?
					person2.getSupervisor():null);
		}
	}
	public void onNewPr(Person person,String prType){
		editButton=true;
		pr.setStatus(SignStatus.DRAFT.toString());
		pr.setTotalcost(new BigDecimal(0));
		pr.setRequestedby(person.getPersonId()); // 填寫人
		pr.setChangeby(person.getNotesId().toUpperCase()); // 異動人
		prNameMap.put(person.getPersonId(), person.getDisplayName());
		pr.setPrtype(prType);
		pr.setCurrencycode("TWD"); //MRO申請單以台幣簽合
		this.setRequestedby2(person);
	}
	public void setRequestedby2(Person person){
		pr.setRequestedby2(person.getPersonId());
		pr.setDeptsupervisor(person.getSupervisor());
		prNameMap.put(person.getSupervisor(), person.getSupervisorName());
		pr.setDeptcode(person.getDeptCode());
		pr.setMDept(person.getMDeptCode());
		pr.setSiteid(person.getSiteId());
	}
	public void setLocationMap(){
		if(StringUtils.isNotBlank(pr.getSiteid())){
			LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
			LocationSiteMapBO locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
			SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
			if(bean!=null) locationMap=bean.getLocationMap().get(pr.getSiteid());
			else locationMap=locationMapBO.getLocationMapBySId(pr.getSiteid());

			locationSiteMap=locationSiteMapBO.getLocationSiteMapBySiteId(pr.getSiteid());
		}
	}
	public void setPrControlConfig(){
		if(locationMap==null ||locationMap.getLocationSiteMap()==null) return;
		PrControlConfigBO bo = SpringContextUtil
				.getBean(PrControlConfigBO.class);
		prControlConfig = (PrControlConfig) Utility.nvlEntity(bo
				.getPrControlConfig(ItemType.getItemType(pr.getPrtype())
						.toString(), locationMap.getLocationSiteMap()
						.getLocationSite()), PrControlConfig.class);
	}
	public List<String> getLineIds(){
		List<Prline> prlines=new ArrayList<>();
		prlines.addAll(listPrline);
		prlines.addAll(listClosePrline);
		prlines.addAll(listCombinePrline);
		return prlines.stream().map(
				l->l.getPrlineid().toString()).collect(Collectors.toList());
	}
	public List<Prline> getListPrline() {
		return listPrline;
	}
	public void setListPrline(List<Prline> listPrline) {
		this.listPrline = listPrline;
	}
	public List<Prline> getListDeletePrline() {
		return listDeletePrline;
	}
	public void setListDeletePrline(List<Prline> listDeletePrline) {
		this.listDeletePrline = listDeletePrline;
	}
	public List<Prline> getListClosePrline() {
		return listClosePrline;
	}
	public void setListClosePrline(List<Prline> listClosePrline) {
		this.listClosePrline = listClosePrline;
	}
	public List<Prline> getListCombinePrline() {
		return listCombinePrline;
	}
	public void setListCombinePrline(List<Prline> listCombinePrline) {
		this.listCombinePrline = listCombinePrline;
	}
	public Prline[] getDeleteListPrline() {
		return deleteListPrline;
	}
	public void setDeleteListPrline(Prline[] deleteListPrline) {
		this.deleteListPrline = deleteListPrline;
	}
	public Pr getPr() {
		return pr;
	}
	public void setPr(Pr pr) {
		this.pr = pr;
	}
	public LocationMap getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(LocationMap locationMap) {
		this.locationMap = locationMap;
	}
	public LocationSiteMap getLocationSiteMap() {
		return locationSiteMap;
	}
	public void setLocationSiteMap(LocationSiteMap locationSiteMap) {
		this.locationSiteMap = locationSiteMap;
	}
	public PrControlConfig getPrControlConfig() {
		return prControlConfig;
	}
	public void setPrControlConfig(PrControlConfig prControlConfig) {
		this.prControlConfig = prControlConfig;
	}
	public PrMcParameter getPrMcParameter() {
		return prMcParameter;
	}
	public void setPrMcParameter(PrMcParameter prMcParameter) {
		this.prMcParameter = prMcParameter;
	}
	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}
	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}
	public FileCategory getFileHeaderType() {
		return fileHeaderType;
	}
	public void setFileHeaderType(FileCategory fileHeaderType) {
		this.fileHeaderType = fileHeaderType;
	}
	public FileCategory getFileLineType() {
		return fileLineType;
	}
	public void setFileLineType(FileCategory fileLineType) {
		this.fileLineType = fileLineType;
	}
	public Long getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(Long employeeType) {
		this.employeeType = employeeType;
	}
	public long getMaxPrlineNum() {
		return maxPrlineNum;
	}
	public void setMaxPrlineNum(long maxPrlineNum) {
		this.maxPrlineNum = maxPrlineNum;
	}
	public BigDecimal getUnUseBudget() {
		return unUseBudget;
	}
	public void setUnUseBudget(BigDecimal unUseBudget) {
		this.unUseBudget = unUseBudget;
	}
	public boolean isEqFlag() {
		return eqFlag;
	}
	public void setEqFlag(boolean eqFlag) {
		this.eqFlag = eqFlag;
	}
	public boolean isReasonFlag() {
		return reasonFlag;
	}
	public void setReasonFlag(boolean reasonFlag) {
		this.reasonFlag = reasonFlag;
	}
	public Map getLineErrorMap() {
		return lineErrorMap;
	}
	public void setLineErrorMap(Map lineErrorMap) {
		this.lineErrorMap = lineErrorMap;
	}
	public Map getPrNameMap() {
		return prNameMap;
	}
	public void setPrNameMap(Map prNameMap) {
		this.prNameMap = prNameMap;
	}
	public long getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(long activeIndex) {
		this.activeIndex = activeIndex;
	}
	public boolean isEditButton() {
		return editButton;
	}
	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}
	public String getSignPreViewUrl() {
		return signPreViewUrl;
	}
	public void setSignPreViewUrl(String signPreViewUrl) {
		this.signPreViewUrl = signPreViewUrl;
	}
	public String getSignHistoryUrl() {
		return signHistoryUrl;
	}
	public void setSignHistoryUrl(String signHistoryUrl) {
		this.signHistoryUrl = signHistoryUrl;
	}
	public Person getPersonForward() {
		return personForward;
	}
	public void setPersonForward(Person personForward) {
		this.personForward = personForward;
	}
	public String getTransferInfo() {
		return transferInfo;
	}
	public void setTransferInfo(String transferInfo) {
		this.transferInfo = transferInfo;
	}
	public String getSignComment() {
		return signComment;
	}
	public void setSignComment(String signComment) {
		this.signComment = signComment;
	}
	public Pr[] getPrs() {
		return prs;
	}
	public void setPrs(Pr[] prs) {
		this.prs = prs;
	}
	public List<String> getNotifyList() {
		return notifyList;
	}
	public void setNotifyList(List<String> notifyList) {
		this.notifyList = notifyList;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	
}
