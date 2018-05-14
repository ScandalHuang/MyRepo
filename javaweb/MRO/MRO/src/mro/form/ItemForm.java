package mro.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.entity.AItem;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemSimple;
import mro.base.entity.Attachment;
import mro.base.entity.ItemMapping;
import mro.base.entity.Itemspec;
import mro.base.entity.Person;

public class ItemForm extends ItemSearchForm implements Serializable {
	
	private static final long serialVersionUID = 4583102010401129672L;
	
	private boolean editButton; // 編輯申請單
	private List<ListAInvvendorVO> listListAInvvendorVO; // 供應商
	private List<AItemSecondItemnum> listAItemSecondItemnum; // 新增替代料號
	private List<ListAItemspecVO> listAItemspecVO; // 分類屬性
	private Map<String,Itemspec> itemSpecMap; //替代料號spec
	private List<Attachment> listAttachment;//附檔
	public Map dowloadFileMap;
	private Map<String, String> aInvvendorVOMap; // 供應商list
	private AItem aItem; // 選取申請單
	private AItemAttribute aItemAttribute; // 申請單其他屬性
	private ItemMapping itemMapping;
	public String sStructs1;// 大分類
	public String sStructs2;// 中分類
	public String sStructs3;// 小分類
	public String sStructs4;// 類別結構
	private String storageCategory;  //存貨/費用
	private String emp;// 變更人
	private int activeIndex;// 供應商清單 page
	private String signHistoryUrl; //簽核歷程網址
	private String signPreViewUrl; //簽核預覽網址
	
	private Map<String, Map<String,String>> alndomainMap; // 規格選單
	private Map<String, Map<String,String>> measureunitDomainMap; // 規格單位選單
	private Map minBasicUnit; //最小計量單位
	private Map packageUnit; //包裝單位
	
	private Person personForward; // 轉呈人員
	private String transferInfo; // 轉呈人員
	private String signComment; // 簽核意見
	
	private AItemSimple aItemSimple;  //簡易版料號規格異動單
	private Map specEditMap;//簡易版料號規格異動Map
	private boolean simpleSignFlag;//簡易版料號規格異動Flag
	private boolean vendorRemarkFlag;//簡易版料號規格異動Flag
	
	private Map applyChangeMap;//異動欄位

	public ItemForm(){
	}
	public void itemIntial(){
		editButton=false;
		listListAInvvendorVO = new ArrayList<ListAInvvendorVO>();
		listAItemSecondItemnum = new ArrayList<AItemSecondItemnum>();
		listAItemspecVO = new ArrayList<ListAItemspecVO>();
		listAttachment = new ArrayList<Attachment>();
		dowloadFileMap = FileCategory.getDownloadFileMap();
		aInvvendorVOMap = new LinkedHashMap<String, String>();
		aItem=null;
		aItemAttribute=null;
		sStructs1 = "";// 大分類
		sStructs2 = "";// 中分類
		sStructs3 = "";// 小分類
		sStructs4 = "";
		storageCategory = "";
		emp = "";
		activeIndex = 0;
		signHistoryUrl="";
		personForward=null;
		transferInfo="";
		signPreViewUrl="";
		signComment="";
		alndomainMap= new LinkedHashMap<>();
		measureunitDomainMap= new LinkedHashMap<>();
		minBasicUnit= new LinkedHashMap<>();
		packageUnit= new LinkedHashMap<>();
		aItemSimple=null;
		specEditMap = new LinkedHashMap<>();
		simpleSignFlag=false;
		vendorRemarkFlag=false;
		applyChangeMap = new LinkedHashMap<>();
		itemMapping=null;
		itemSpecMap=new HashMap<String, Itemspec>();
	}
	
	public void newItem(String organizationCode){
		aItem=new AItem();
		aItemAttribute=new AItemAttribute();
		aItem.setStorageCondition("05");
		aItem.setOrganizationCode(organizationCode);
		aItemAttribute.setCurrencyCode(SystemConfig.defaultCurrency);
		aItemAttribute.setMcOrderQuantity(new BigDecimal(1));
		aItemAttribute.setMcMinPackageQuantity(new BigDecimal(1));
		aItemAttribute.setMcUseFrequencyType("C");
		editButton = true;
		activeIndex = 1;
	}
	public void setSecondDefault(){
		if(!ObjectUtils.toString(aItemAttribute.getSecondItemFlag()).equals("Y")||
				StringUtils.isBlank(aItemAttribute.getSecondItemType())){
			aItemAttribute.setSecondItemType(null);
			aItemAttribute.setSecondSourceItemnum(null);
			itemSpecMap.clear();
			listAItemSecondItemnum.clear();
		}else{
			if(SecondItemType.valueOf(aItemAttribute.getSecondItemType()).secondSource()){
				listAItemSecondItemnum.clear();
				if(StringUtils.isBlank(aItemAttribute.getSecondSourceItemnum())){
					itemSpecMap.clear();
				}
			}else{
				aItemAttribute.setSecondSourceItemnum(null);
				itemSpecMap.clear();
			}
		}
	}
	public List<ListAInvvendorVO> getListListAInvvendorVO() {
		return listListAInvvendorVO;
	}
	public void setListListAInvvendorVO(List<ListAInvvendorVO> listListAInvvendorVO) {
		this.listListAInvvendorVO = listListAInvvendorVO;
	}
	public List<AItemSecondItemnum> getListAItemSecondItemnum() {
		return listAItemSecondItemnum;
	}
	public void setListAItemSecondItemnum(
			List<AItemSecondItemnum> listAItemSecondItemnum) {
		this.listAItemSecondItemnum = listAItemSecondItemnum;
	}
	public List<ListAItemspecVO> getListAItemspecVO() {
		return listAItemspecVO;
	}
	public void setListAItemspecVO(List<ListAItemspecVO> listAItemspecVO) {
		this.listAItemspecVO = listAItemspecVO;
	}
	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}
	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}
	public Map<String, String> getaInvvendorVOMap() {
		return aInvvendorVOMap;
	}
	public void setaInvvendorVOMap(Map<String, String> aInvvendorVOMap) {
		this.aInvvendorVOMap = aInvvendorVOMap;
	}
	public AItem getaItem() {
		return aItem;
	}
	public void setaItem(AItem aItem) {
		this.aItem = aItem;
	}
	public AItemAttribute getaItemAttribute() {
		return aItemAttribute;
	}
	public void setaItemAttribute(AItemAttribute aItemAttribute) {
		this.aItemAttribute = aItemAttribute;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public int getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
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
	public List<Attachment> getListAttachment() {
		return listAttachment;
	}
	public void setListAttachment(List<Attachment> listAttachment) {
		this.listAttachment = listAttachment;
	}
	public Map<String, Map<String,String>> getAlndomainMap() {
		return alndomainMap;
	}
	public void setAlndomainMap(Map<String, Map<String,String>> alndomainMap) {
		this.alndomainMap = alndomainMap;
	}
	public Map<String, Map<String,String>> getMeasureunitDomainMap() {
		return measureunitDomainMap;
	}
	public void setMeasureunitDomainMap(Map<String, Map<String,String>> measureunitDomainMap) {
		this.measureunitDomainMap = measureunitDomainMap;
	}
	public Map getMinBasicUnit() {
		return minBasicUnit;
	}
	public void setMinBasicUnit(Map minBasicUnit) {
		this.minBasicUnit = minBasicUnit;
	}
	public Map getPackageUnit() {
		return packageUnit;
	}
	public void setPackageUnit(Map packageUnit) {
		this.packageUnit = packageUnit;
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
	public AItemSimple getaItemSimple() {
		return aItemSimple;
	}
	public void setaItemSimple(AItemSimple aItemSimple) {
		this.aItemSimple = aItemSimple;
	}
	public Map getSpecEditMap() {
		return specEditMap;
	}
	public void setSpecEditMap(Map specEditMap) {
		this.specEditMap = specEditMap;
	}
	public boolean isSimpleSignFlag() {
		return simpleSignFlag;
	}
	public void setSimpleSignFlag(boolean simpleSignFlag) {
		this.simpleSignFlag = simpleSignFlag;
	}
	public Map getApplyChangeMap() {
		return applyChangeMap;
	}
	public void setApplyChangeMap(Map applyChangeMap) {
		this.applyChangeMap = applyChangeMap;
	}
	public String getsStructs1() {
		return sStructs1;
	}
	public void setsStructs1(String sStructs1) {
		this.sStructs1 = sStructs1;
	}
	public String getsStructs2() {
		return sStructs2;
	}
	public void setsStructs2(String sStructs2) {
		this.sStructs2 = sStructs2;
	}
	public String getsStructs3() {
		return sStructs3;
	}
	public void setsStructs3(String sStructs3) {
		this.sStructs3 = sStructs3;
	}
	public String getsStructs4() {
		return sStructs4;
	}
	public void setsStructs4(String sStructs4) {
		this.sStructs4 = sStructs4;
	}
	public boolean isVendorRemarkFlag() {
		return vendorRemarkFlag;
	}
	public void setVendorRemarkFlag(boolean vendorRemarkFlag) {
		this.vendorRemarkFlag = vendorRemarkFlag;
	}
	public ItemMapping getItemMapping() {
		return itemMapping;
	}
	public void setItemMapping(ItemMapping itemMapping) {
		this.itemMapping = itemMapping;
	}
	public String getStorageCategory() {
		return storageCategory;
	}
	public void setStorageCategory(String storageCategory) {
		this.storageCategory = storageCategory;
	}
	public Map<String, Itemspec> getItemSpecMap() {
		return itemSpecMap;
	}
	public void setItemSpecMap(Map<String, Itemspec> itemSpecMap) {
		this.itemSpecMap = itemSpecMap;
	}
	
}
