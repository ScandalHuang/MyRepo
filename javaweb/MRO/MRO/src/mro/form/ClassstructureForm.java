package mro.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.commonview.vo.ListClassspecVO;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Alndomain;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.entity.LocationMap;
import mro.base.entity.Person;

public class ClassstructureForm implements Serializable{
	
	private static final long serialVersionUID = -8917079291183357874L;
	
	private List<ClassstructureHeaderApply> listClassstructureHeaderApply;
	private List<ClassstructureLineApply> listClassstructureLineApply;
	private ClassstructureHeaderApply classstructureHeaderApply;
	

	private List<ListClassspecVO> listClassspec;
	private Map<String,List<Alndomain>> listAlndomainMap;
	

	public Map dowloadFileMap;

	private Person person; //申請人
	
	private int activeIndex;// 清單 page
	private boolean editButton; // 編輯申請單
	private String signHistoryUrl; //簽核歷程網址
	private Map assetAttributeMap;
	
	private Person personForward; // 轉呈人員
	private String transferInfo; // 轉呈人員
	private String signComment; // 簽核意見
	private String signPreViewUrl; //簽核預覽網址
	private Map signMap;//申請單簽核人員
	//==============================Parameter==============================================
	private Map actionOption;
	private Map selectStatus;
	//=============================Search=========================================
	private String sLocationSite;
	private String sOrganizationCode;
	private String applyHeaderNum;
	private String action;
	private Date beginDate;
	private Date endDate;
	private String status;
	private String createBy;
	
	public ClassstructureForm(){
		intial();
	}
	
	public void intial(){
		listClassstructureLineApply = new ArrayList<>();
		classstructureHeaderApply=null;
		listClassspec = new ArrayList<>();
		listAlndomainMap  = null;
		assetAttributeMap=null;
		dowloadFileMap = FileCategory.getDownloadFileMap();
		person=null;
		activeIndex = 0;
		editButton = false;
		signHistoryUrl="";
		personForward=null;
		transferInfo="";
		signComment="";
		signPreViewUrl="";
	}
	public void  newApply(Person person,LocationMap locationMap){
		intial();
		classstructureHeaderApply=new ClassstructureHeaderApply();
		editButton=true;
		classstructureHeaderApply.setStatus(SignStatus.NEW.toString());
		classstructureHeaderApply.setCreateBy(person.getPersonId()); // 申請人工號
		classstructureHeaderApply.setDeptcode(person.getDeptCode());// 申請人部門
		classstructureHeaderApply.setOrganizationCode(locationMap.getOrganizationCode());
		activeIndex=1;
	}
	
	public List<ClassstructureHeaderApply> getListClassstructureHeaderApply() {
		return listClassstructureHeaderApply;
	}
	public void setListClassstructureHeaderApply(
			List<ClassstructureHeaderApply> listClassstructureHeaderApply) {
		this.listClassstructureHeaderApply = listClassstructureHeaderApply;
	}
	public List<ClassstructureLineApply> getListClassstructureLineApply() {
		return listClassstructureLineApply;
	}
	public void setListClassstructureLineApply(
			List<ClassstructureLineApply> listClassstructureLineApply) {
		this.listClassstructureLineApply = listClassstructureLineApply;
	}
	public ClassstructureHeaderApply getClassstructureHeaderApply() {
		return classstructureHeaderApply;
	}
	public void setClassstructureHeaderApply(
			ClassstructureHeaderApply classstructureHeaderApply) {
		this.classstructureHeaderApply = classstructureHeaderApply;
	}
	public int getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	public boolean isEditButton() {
		return editButton;
	}
	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}
	public String getSignHistoryUrl() {
		return signHistoryUrl;
	}
	public void setSignHistoryUrl(String signHistoryUrl) {
		this.signHistoryUrl = signHistoryUrl;
	}
	
	public String getTransferInfo() {
		return transferInfo;
	}
	public Person getPersonForward() {
		return personForward;
	}

	public void setPersonForward(Person personForward) {
		this.personForward = personForward;
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
	public String getSignPreViewUrl() {
		return signPreViewUrl;
	}
	public void setSignPreViewUrl(String signPreViewUrl) {
		this.signPreViewUrl = signPreViewUrl;
	}
	public Map getSignMap() {
		return signMap;
	}
	public void setSignMap(Map signMap) {
		this.signMap = signMap;
	}
	public String getApplyHeaderNum() {
		return applyHeaderNum;
	}
	public void setApplyHeaderNum(String applyHeaderNum) {
		this.applyHeaderNum = applyHeaderNum;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Map getActionOption() {
		return actionOption;
	}
	public void setActionOption(Map actionOption) {
		this.actionOption = actionOption;
	}
	public List<ListClassspecVO> getListClassspec() {
		return listClassspec;
	}
	public void setListClassspec(List<ListClassspecVO> listClassspec) {
		this.listClassspec = listClassspec;
	}
	public Map<String, List<Alndomain>> getListAlndomainMap() {
		return listAlndomainMap;
	}

	public void setListAlndomainMap(Map<String, List<Alndomain>> listAlndomainMap) {
		this.listAlndomainMap = listAlndomainMap;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(Map selectStatus) {
		this.selectStatus = selectStatus;
	}

	public Map getAssetAttributeMap() {
		return assetAttributeMap;
	}

	public void setAssetAttributeMap(Map assetAttributeMap) {
		this.assetAttributeMap = assetAttributeMap;
	}

	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}

	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}

	public String getsLocationSite() {
		return sLocationSite;
	}

	public void setsLocationSite(String sLocationSite) {
		this.sLocationSite = sLocationSite;
	}

	public String getsOrganizationCode() {
		return sOrganizationCode;
	}

	public void setsOrganizationCode(String sOrganizationCode) {
		this.sOrganizationCode = sOrganizationCode;
	}

}
