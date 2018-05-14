package mro.base.entity;

// Generated 2013/2/25 �W�� 10:00:13 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AItem generated by hbm2java
 */
@Entity
@Table(name = "A_ITEM")
public class AItem implements java.io.Serializable {

	private BigDecimal eaudittransid;
	private BigDecimal attachonissue=new BigDecimal(0);
	private BigDecimal capitalized=new BigDecimal(0);
	private String classstructureid;
	private String commodity;
	private String commoditygroup;
	private BigDecimal conditionenabled=new BigDecimal(0);
	private String description;
	private String externalrefid;
	private String groupname;
	private BigDecimal hasld=new BigDecimal(0);
	private String in19;
	private String in20;
	private String in21;
	private Date in22;
	private BigDecimal in23;
	private String in24;
	private String in25;
	private String in26;
	private String in27;
	private BigDecimal inspectionrequired=new BigDecimal(0);
	private BigDecimal iskit=new BigDecimal(0);
	private String issueunit;
	private Item item;
	private String itemnum;
	private String itemsetid="C-ITEM1";
	private String itemtype;
	private String langcode="ZH";
	private String lottype="NOLOT";
	private String metername;
	private String msdsnum;
	private String orderunit;
	private BigDecimal outside=new BigDecimal(0);
	private String ownersysid;
	private BigDecimal prorate=new BigDecimal(0);
	private BigDecimal rotating=new BigDecimal(0);
	private String sendersysid;
	private String sourcesysid;
	private BigDecimal sparepartautoadd=new BigDecimal(1);
	private String status;
	private String eauditusername;
	private Date eaudittimestamp;
	private String eaudittype;
	private String esigtransid;
	private String quotation;
	private String prnum;
	private String duplicateitem;
	private String oriitemnum;
	private String changeby;
	private String irnum;
	private String emailalarm;
	private BigDecimal alarmflag=new BigDecimal(0);
	private BigDecimal stockflag=new BigDecimal(0);
	private BigDecimal effectflag=new BigDecimal(0);
	private String mccommand;
	private BigDecimal maxidledays;
	private String mcpurcommand;
	private BigDecimal moq=new BigDecimal(1);
	private BigDecimal spq=new BigDecimal(1);
	// FetchType.LAZY 影響 itemImpl.setApplyChangeMap beansUtil
//	private Set<AItemAttribute> AItemAttributes = new HashSet<AItemAttribute>(0);  
	private String remark;
	private String storageCondition;
	private BigDecimal totalShelfLife;
	private BigDecimal minShelfLife;
	private Date createDate;
	private BigDecimal taskId;
	private String organizationCode;
	private BigDecimal preEaudittransid;
	private String validateDescription;
	private String moqMpqFlag;
	private String deptNo;
	private String changeRemark;
	
	public AItem() {
	}

	public AItem(BigDecimal eaudittransid, String eauditusername,
			Date eaudittimestamp, String eaudittype) {
		this.eaudittransid = eaudittransid;
		this.eauditusername = eauditusername;
		this.eaudittimestamp = eaudittimestamp;
		this.eaudittype = eaudittype;
	}

	public AItem(BigDecimal eaudittransid, BigDecimal attachonissue,
			BigDecimal capitalized, String classstructureid, String commodity,
			String commoditygroup, BigDecimal conditionenabled,
			String description, String externalrefid, String groupname,
			BigDecimal hasld, String in19, String in20, String in21, Date in22,
			BigDecimal in23, String in24, String in25, String in26,
			String in27, BigDecimal inspectionrequired, BigDecimal iskit,
			String issueunit, Item item, String itemnum,
			String itemsetid, String itemtype, String langcode, String lottype,
			String metername, String msdsnum, String orderunit,
			BigDecimal outside, String ownersysid, BigDecimal prorate,
			BigDecimal rotating, String sendersysid, String sourcesysid,
			BigDecimal sparepartautoadd, String status, String eauditusername,
			Date eaudittimestamp, String eaudittype, String esigtransid,
			String quotation, String prnum, String duplicateitem,
			String oriitemnum, String changeby, String irnum,
			String emailalarm, BigDecimal alarmflag, BigDecimal stockflag,
			BigDecimal effectflag, String mccommand, BigDecimal maxidledays,
			String mcpurcommand, BigDecimal moq, BigDecimal spq,
//			Set<AItemAttribute> AItemAttributes,
			BigDecimal taskId,
			String remark,String storageCondition,BigDecimal totalShelfLife,BigDecimal minShelfLife,
			Date createDate,String organizationCode,BigDecimal preEaudittransid,
			String validateDescription, String moqMpqFlag,String deptNo,String changeRemark) {
		this.eaudittransid = eaudittransid;
		this.attachonissue = attachonissue;
		this.capitalized = capitalized;
		this.classstructureid = classstructureid;
		this.commodity = commodity;
		this.commoditygroup = commoditygroup;
		this.conditionenabled = conditionenabled;
		this.description = description;
		this.externalrefid = externalrefid;
		this.groupname = groupname;
		this.hasld = hasld;
		this.in19 = in19;
		this.in20 = in20;
		this.in21 = in21;
		this.in22 = in22;
		this.in23 = in23;
		this.in24 = in24;
		this.in25 = in25;
		this.in26 = in26;
		this.in27 = in27;
		this.inspectionrequired = inspectionrequired;
		this.iskit = iskit;
		this.issueunit = issueunit;
		this.item = item;
		this.itemnum = itemnum;
		this.itemsetid = itemsetid;
		this.itemtype = itemtype;
		this.langcode = langcode;
		this.lottype = lottype;
		this.metername = metername;
		this.msdsnum = msdsnum;
		this.orderunit = orderunit;
		this.outside = outside;
		this.ownersysid = ownersysid;
		this.prorate = prorate;
		this.rotating = rotating;
		this.sendersysid = sendersysid;
		this.sourcesysid = sourcesysid;
		this.sparepartautoadd = sparepartautoadd;
		this.status = status;
		this.eauditusername = eauditusername;
		this.eaudittimestamp = eaudittimestamp;
		this.eaudittype = eaudittype;
		this.esigtransid = esigtransid;
		this.quotation = quotation;
		this.prnum = prnum;
		this.duplicateitem = duplicateitem;
		this.oriitemnum = oriitemnum;
		this.changeby = changeby;
		this.irnum = irnum;
		this.emailalarm = emailalarm;
		this.alarmflag = alarmflag;
		this.stockflag = stockflag;
		this.effectflag = effectflag;
		this.mccommand = mccommand;
		this.maxidledays = maxidledays;
		this.mcpurcommand = mcpurcommand;
		this.moq = moq;
		this.spq = spq;
//		this.AItemAttributes = AItemAttributes;
		this.taskId=taskId;
		this.remark = remark;
		this.storageCondition = storageCondition;
		this.totalShelfLife = totalShelfLife;
		this.minShelfLife = minShelfLife;
		this.createDate=createDate;
		this.organizationCode=organizationCode;
		this.preEaudittransid = preEaudittransid;
		this.validateDescription = validateDescription;
		this.moqMpqFlag = moqMpqFlag;
		this.deptNo = deptNo;
		this.changeRemark = changeRemark;
	}

	@Id
/*	@SequenceGenerator(name="EAUDITTRANSID_GENERATOR", sequenceName="EAUDITSEQ" ,allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EAUDITTRANSID_GENERATOR")
	*/@Column(name = "EAUDITTRANSID", unique = true, nullable = false)
	public BigDecimal getEaudittransid() {
		return this.eaudittransid;
	}

	public void setEaudittransid(BigDecimal eaudittransid) {
		this.eaudittransid = eaudittransid;
	}

	@Column(name = "ATTACHONISSUE", precision = 22, scale = 0)
	public BigDecimal getAttachonissue() {
		return this.attachonissue;
	}

	public void setAttachonissue(BigDecimal attachonissue) {
		this.attachonissue = attachonissue;
	}

	@Column(name = "CAPITALIZED", precision = 22, scale = 0)
	public BigDecimal getCapitalized() {
		return this.capitalized;
	}

	public void setCapitalized(BigDecimal capitalized) {
		this.capitalized = capitalized;
	}

	@Column(name = "CLASSSTRUCTUREID", length = 26)
	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	@Column(name = "COMMODITY", length = 8)
	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	@Column(name = "COMMODITYGROUP", length = 8)
	public String getCommoditygroup() {
		return this.commoditygroup;
	}

	public void setCommoditygroup(String commoditygroup) {
		this.commoditygroup = commoditygroup;
	}

	@Column(name = "CONDITIONENABLED", precision = 22, scale = 0)
	public BigDecimal getConditionenabled() {
		return this.conditionenabled;
	}

	public void setConditionenabled(BigDecimal conditionenabled) {
		this.conditionenabled = conditionenabled;
	}

	@Column(name = "DESCRIPTION", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "EXTERNALREFID", length = 10)
	public String getExternalrefid() {
		return this.externalrefid;
	}

	public void setExternalrefid(String externalrefid) {
		this.externalrefid = externalrefid;
	}

	@Column(name = "GROUPNAME", length = 10)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "HASLD", precision = 22, scale = 0)
	public BigDecimal getHasld() {
		return this.hasld;
	}

	public void setHasld(BigDecimal hasld) {
		this.hasld = hasld;
	}

	@Column(name = "IN19", length = 10)
	public String getIn19() {
		return this.in19;
	}

	public void setIn19(String in19) {
		this.in19 = in19;
	}

	@Column(name = "IN20", length = 10)
	public String getIn20() {
		return this.in20;
	}

	public void setIn20(String in20) {
		this.in20 = in20;
	}

	@Column(name = "IN21", length = 10)
	public String getIn21() {
		return this.in21;
	}

	public void setIn21(String in21) {
		this.in21 = in21;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN22", length = 7)
	public Date getIn22() {
		return this.in22;
	}

	public void setIn22(Date in22) {
		this.in22 = in22;
	}

	@Column(name = "IN23", precision = 15)
	public BigDecimal getIn23() {
		return this.in23;
	}

	public void setIn23(BigDecimal in23) {
		this.in23 = in23;
	}

	@Column(name = "IN24", length = 10)
	public String getIn24() {
		return this.in24;
	}

	public void setIn24(String in24) {
		this.in24 = in24;
	}

	@Column(name = "IN25", length = 10)
	public String getIn25() {
		return this.in25;
	}

	public void setIn25(String in25) {
		this.in25 = in25;
	}

	@Column(name = "IN26", length = 10)
	public String getIn26() {
		return this.in26;
	}

	public void setIn26(String in26) {
		this.in26 = in26;
	}

	@Column(name = "IN27", length = 10)
	public String getIn27() {
		return this.in27;
	}

	public void setIn27(String in27) {
		this.in27 = in27;
	}

	@Column(name = "INSPECTIONREQUIRED", precision = 22, scale = 0)
	public BigDecimal getInspectionrequired() {
		return this.inspectionrequired;
	}

	public void setInspectionrequired(BigDecimal inspectionrequired) {
		this.inspectionrequired = inspectionrequired;
	}

	@Column(name = "ISKIT", precision = 22, scale = 0)
	public BigDecimal getIskit() {
		return this.iskit;
	}

	public void setIskit(BigDecimal iskit) {
		this.iskit = iskit;
	}

	@Column(name = "ISSUEUNIT", length = 50)
	public String getIssueunit() {
		return this.issueunit;
	}

	public void setIssueunit(String issueunit) {
		this.issueunit = issueunit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEMID")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "ITEMNUM", length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "ITEMSETID", length = 8)
	public String getItemsetid() {
		return this.itemsetid;
	}

	public void setItemsetid(String itemsetid) {
		this.itemsetid = itemsetid;
	}

	@Column(name = "ITEMTYPE", length = 10)
	public String getItemtype() {
		return this.itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	@Column(name = "LANGCODE", length = 4)
	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	@Column(name = "LOTTYPE", length = 16)
	public String getLottype() {
		return this.lottype;
	}

	public void setLottype(String lottype) {
		this.lottype = lottype;
	}

	@Column(name = "METERNAME", length = 20)
	public String getMetername() {
		return this.metername;
	}

	public void setMetername(String metername) {
		this.metername = metername;
	}

	@Column(name = "MSDSNUM", length = 10)
	public String getMsdsnum() {
		return this.msdsnum;
	}

	public void setMsdsnum(String msdsnum) {
		this.msdsnum = msdsnum;
	}

	@Column(name = "ORDERUNIT", length = 50)
	public String getOrderunit() {
		return this.orderunit;
	}

	public void setOrderunit(String orderunit) {
		this.orderunit = orderunit;
	}

	@Column(name = "OUTSIDE", precision = 22, scale = 0)
	public BigDecimal getOutside() {
		return this.outside;
	}

	public void setOutside(BigDecimal outside) {
		this.outside = outside;
	}

	@Column(name = "OWNERSYSID", length = 10)
	public String getOwnersysid() {
		return this.ownersysid;
	}

	public void setOwnersysid(String ownersysid) {
		this.ownersysid = ownersysid;
	}

	@Column(name = "PRORATE", precision = 22, scale = 0)
	public BigDecimal getProrate() {
		return this.prorate;
	}

	public void setProrate(BigDecimal prorate) {
		this.prorate = prorate;
	}

	@Column(name = "ROTATING", precision = 22, scale = 0)
	public BigDecimal getRotating() {
		return this.rotating;
	}

	public void setRotating(BigDecimal rotating) {
		this.rotating = rotating;
	}

	@Column(name = "SENDERSYSID", length = 50)
	public String getSendersysid() {
		return this.sendersysid;
	}

	public void setSendersysid(String sendersysid) {
		this.sendersysid = sendersysid;
	}

	@Column(name = "SOURCESYSID", length = 10)
	public String getSourcesysid() {
		return this.sourcesysid;
	}

	public void setSourcesysid(String sourcesysid) {
		this.sourcesysid = sourcesysid;
	}

	@Column(name = "SPAREPARTAUTOADD", precision = 22, scale = 0)
	public BigDecimal getSparepartautoadd() {
		return this.sparepartautoadd;
	}

	public void setSparepartautoadd(BigDecimal sparepartautoadd) {
		this.sparepartautoadd = sparepartautoadd;
	}

	@Column(name = "STATUS", length = 15)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "EAUDITUSERNAME", nullable = false, length = 18)
	public String getEauditusername() {
		return this.eauditusername;
	}

	public void setEauditusername(String eauditusername) {
		this.eauditusername = eauditusername;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EAUDITTIMESTAMP", nullable = false, length = 7)
	public Date getEaudittimestamp() {
		return this.eaudittimestamp;
	}

	public void setEaudittimestamp(Date eaudittimestamp) {
		this.eaudittimestamp = eaudittimestamp;
	}

	@Column(name = "EAUDITTYPE", nullable = false, length = 1)
	public String getEaudittype() {
		return this.eaudittype;
	}

	public void setEaudittype(String eaudittype) {
		this.eaudittype = eaudittype;
	}

	@Column(name = "ESIGTRANSID", length = 40)
	public String getEsigtransid() {
		return this.esigtransid;
	}

	public void setEsigtransid(String esigtransid) {
		this.esigtransid = esigtransid;
	}

	@Column(name = "QUOTATION", length = 50)
	public String getQuotation() {
		return this.quotation;
	}

	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}

	@Column(name = "PRNUM", length = 80)
	public String getPrnum() {
		return this.prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	@Column(name = "DUPLICATEITEM", length = 30)
	public String getDuplicateitem() {
		return this.duplicateitem;
	}

	public void setDuplicateitem(String duplicateitem) {
		this.duplicateitem = duplicateitem;
	}

	@Column(name = "ORIITEMNUM", length = 30)
	public String getOriitemnum() {
		return this.oriitemnum;
	}

	public void setOriitemnum(String oriitemnum) {
		this.oriitemnum = oriitemnum;
	}

	@Column(name = "CHANGEBY", length = 30)
	public String getChangeby() {
		return this.changeby;
	}

	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}

	@Column(name = "IRNUM", length = 30)
	public String getIrnum() {
		return this.irnum;
	}

	public void setIrnum(String irnum) {
		this.irnum = irnum;
	}

	@Column(name = "EMAILALARM", length = 250)
	public String getEmailalarm() {
		return this.emailalarm;
	}

	public void setEmailalarm(String emailalarm) {
		this.emailalarm = emailalarm;
	}

	@Column(name = "ALARMFLAG", precision = 22, scale = 0)
	public BigDecimal getAlarmflag() {
		return this.alarmflag;
	}

	public void setAlarmflag(BigDecimal alarmflag) {
		this.alarmflag = alarmflag;
	}

	@Column(name = "STOCKFLAG", precision = 22, scale = 0)
	public BigDecimal getStockflag() {
		return this.stockflag;
	}

	public void setStockflag(BigDecimal stockflag) {
		this.stockflag = stockflag;
	}

	@Column(name = "EFFECTFLAG", precision = 22, scale = 0)
	public BigDecimal getEffectflag() {
		return this.effectflag;
	}

	public void setEffectflag(BigDecimal effectflag) {
		this.effectflag = effectflag;
	}

	@Column(name = "MCCOMMAND", length = 250)
	public String getMccommand() {
		return this.mccommand;
	}

	public void setMccommand(String mccommand) {
		this.mccommand = mccommand;
	}

	@Column(name = "MAXIDLEDAYS", precision = 16)
	public BigDecimal getMaxidledays() {
		return this.maxidledays;
	}

	public void setMaxidledays(BigDecimal maxidledays) {
		this.maxidledays = maxidledays;
	}

	@Column(name = "MCPURCOMMAND", length = 250)
	public String getMcpurcommand() {
		return this.mcpurcommand;
	}

	public void setMcpurcommand(String mcpurcommand) {
		this.mcpurcommand = mcpurcommand;
	}

	@Column(name = "MOQ", precision = 15)
	public BigDecimal getMoq() {
		return this.moq;
	}

	public void setMoq(BigDecimal moq) {
		this.moq = moq;
	}

	@Column(name = "SPQ", precision = 15)
	public BigDecimal getSpq() {
		return this.spq;
	}

	public void setSpq(BigDecimal spq) {
		this.spq = spq;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "AItem")
//	public Set<AItemAttribute> getAItemAttributes() {
//		return this.AItemAttributes;
//	}
//
//	public void setAItemAttributes(Set<AItemAttribute> AItemAttributes) {
//		this.AItemAttributes = AItemAttributes;
//	}
	@Column(name = "TASK_ID")
	public BigDecimal getTaskId() {
		return taskId;
	}

	public void setTaskId(BigDecimal taskId) {
		this.taskId = taskId;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "STORAGE_CONDITION")
	public String getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	@Column(name = "TOTAL_SHELF_LIFE")
	public BigDecimal getTotalShelfLife() {
		return totalShelfLife;
	}
	
	public void setTotalShelfLife(BigDecimal totalShelfLife) {
		this.totalShelfLife = totalShelfLife;
	}
	@Column(name = "MIN_SHELF_LIFE")
	public BigDecimal getMinShelfLife() {
		return minShelfLife;
	}

	public void setMinShelfLife(BigDecimal minShelfLife) {
		this.minShelfLife = minShelfLife;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "ORGANIZATION_CODE", length = 20)
	public String getOrganizationCode() {
		return this.organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Column(name = "PRE_EAUDITTRANSID")
	public BigDecimal getPreEaudittransid() {
		return preEaudittransid;
	}

	public void setPreEaudittransid(BigDecimal preEaudittransid) {
		this.preEaudittransid = preEaudittransid;
	}
	
	@Column(name = "VALIDATE_DESCRIPTION")
	public String getValidateDescription() {
		return validateDescription;
	}

	public void setValidateDescription(String validateDescription) {
		this.validateDescription = validateDescription;
	}	
	@Column(name = "MOQ_MPQ_FLAG", length = 1)
	public String getMoqMpqFlag() {
		return this.moqMpqFlag;
	}

	public void setMoqMpqFlag(String moqMpqFlag) {
		this.moqMpqFlag = moqMpqFlag;
	}
	@Column(name = "DEPT_NO")
	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "CHANGE_REMARK")
	public String getChangeRemark() {
		return changeRemark;
	}

	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}
}