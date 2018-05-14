package mro.base.entity;

// Generated 2013/2/25 �W�� 10:03:29 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Item generated by hbm2java
 */
@Entity
@Table(name = "ITEM", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ITEMNUM", "ITEMSETID" }))
public class Item implements java.io.Serializable {

	private BigDecimal itemid;
	private String itemnum;
	private String description;
	private BigDecimal rotating;
	private String lottype;
	private BigDecimal capitalized;
	private String msdsnum;
	private BigDecimal outside;
	private String in19;
	private String in20;
	private String in21;
	private Date in22;
	private BigDecimal in23;
	private BigDecimal sparepartautoadd;
	private String classstructureid;
	private BigDecimal inspectionrequired;
	private String sourcesysid;
	private String ownersysid;
	private String externalrefid;
	private String in24;
	private String in25;
	private String in26;
	private String in27;
	private String sendersysid;
	private String itemsetid;
	private String orderunit;
	private String issueunit;
	private BigDecimal conditionenabled;
	private String groupname;
	private String metername;
	private String commoditygroup;
	private String commodity;
	private String itemtype;
	private BigDecimal prorate;
	private BigDecimal iskit;
	private String langcode;
	private BigDecimal attachonissue;
	private BigDecimal hasld;
	private String status;
	private String quotation;
	private String prnum;
	private String duplicateitem;
	private String oriitemnum;
	private String changeby;
	private String irnum;
	private String emailalarm;
	private BigDecimal alarmflag;
	private BigDecimal stockflag;
	private BigDecimal effectflag;
	private String mccommand;
	private BigDecimal maxidledays;
	private String mcpurcommand;
	private BigDecimal moq;
	private BigDecimal spq;
	private Set<ItemAttribute> itemAttributes = new HashSet<ItemAttribute>(0);
	private String remark;
	private String storageCondition = "06";
	private BigDecimal totalShelfLife;
	private BigDecimal minShelfLife;
	private String disableFlag;
	private Date createDate;
	private String createBy;
	private String validateDescription;
	private String createByDeptNo;
	private Date changeLastUpdate;
	private String disableFlagRemark;
	private String createOrganizationCode;

	public Item() {
	}

	public Item(BigDecimal itemid, String itemnum, String classstructureid,
			String itemtype) {
		this.itemid = itemid;
		this.itemnum = itemnum;
		this.classstructureid = classstructureid;
		this.itemtype = itemtype;
	}

	public Item(BigDecimal itemid, String itemnum, String description,
			BigDecimal rotating, String lottype, BigDecimal capitalized,
			String msdsnum, BigDecimal outside, String in19, String in20,
			String in21, Date in22, BigDecimal in23,
			BigDecimal sparepartautoadd, String classstructureid,
			BigDecimal inspectionrequired, String sourcesysid,
			String ownersysid, String externalrefid, String in24, String in25,
			String in26, String in27, String sendersysid, String itemsetid,
			String orderunit, String issueunit, BigDecimal conditionenabled,
			String groupname, String metername, String commoditygroup,
			String commodity, String itemtype, BigDecimal prorate,
			BigDecimal iskit, String langcode, BigDecimal attachonissue,
			BigDecimal hasld, String status, String quotation, String prnum,
			String duplicateitem, String oriitemnum, String changeby,
			String irnum, String emailalarm, BigDecimal alarmflag,
			BigDecimal stockflag, BigDecimal effectflag, String mccommand,
			BigDecimal maxidledays, String mcpurcommand, BigDecimal moq,
			BigDecimal spq, Set<ItemAttribute> itemAttributes, String remark,
			String storageCondition, BigDecimal totalShelfLife,
			BigDecimal minShelfLife, String disableFlag, Date createDate,
			String createBy, String validateDescription, String createByDeptNo,
			Date changeLastUpdate, String disableFlagRemark,
			String createOrganizationCode) {
		this.itemid = itemid;
		this.itemnum = itemnum;
		this.description = description;
		this.rotating = rotating;
		this.lottype = lottype;
		this.capitalized = capitalized;
		this.msdsnum = msdsnum;
		this.outside = outside;
		this.in19 = in19;
		this.in20 = in20;
		this.in21 = in21;
		this.in22 = in22;
		this.in23 = in23;
		this.sparepartautoadd = sparepartautoadd;
		this.classstructureid = classstructureid;
		this.inspectionrequired = inspectionrequired;
		this.sourcesysid = sourcesysid;
		this.ownersysid = ownersysid;
		this.externalrefid = externalrefid;
		this.in24 = in24;
		this.in25 = in25;
		this.in26 = in26;
		this.in27 = in27;
		this.sendersysid = sendersysid;
		this.itemsetid = itemsetid;
		this.orderunit = orderunit;
		this.issueunit = issueunit;
		this.conditionenabled = conditionenabled;
		this.groupname = groupname;
		this.metername = metername;
		this.commoditygroup = commoditygroup;
		this.commodity = commodity;
		this.itemtype = itemtype;
		this.prorate = prorate;
		this.iskit = iskit;
		this.langcode = langcode;
		this.attachonissue = attachonissue;
		this.hasld = hasld;
		this.status = status;
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
		this.itemAttributes = itemAttributes;
		this.remark = remark;
		this.storageCondition = storageCondition;
		this.totalShelfLife = totalShelfLife;
		this.minShelfLife = minShelfLife;
		this.disableFlag = disableFlag;
		this.createDate = createDate;
		this.createBy = createBy;
		this.validateDescription = validateDescription;
		this.createByDeptNo = createByDeptNo;
		this.changeLastUpdate = changeLastUpdate;
		this.disableFlagRemark = disableFlagRemark;
		this.createOrganizationCode = createOrganizationCode;
	}

	@Id
	/*
	 * @SequenceGenerator(name="ITEMID_GENERATOR", sequenceName="ITEMSEQ"
	 * ,allocationSize=0)
	 * 
	 * @GeneratedValue(strategy=GenerationType.SEQUENCE,
	 * generator="ITEMID_GENERATOR")
	 */@Column(name = "ITEMID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getItemid() {
		return this.itemid;
	}

	public void setItemid(BigDecimal itemid) {
		this.itemid = itemid;
	}

	@Column(name = "ITEMNUM", nullable = false, length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "DESCRIPTION", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ROTATING", precision = 22, scale = 0)
	public BigDecimal getRotating() {
		return this.rotating;
	}

	public void setRotating(BigDecimal rotating) {
		this.rotating = rotating;
	}

	@Column(name = "LOTTYPE", length = 16)
	public String getLottype() {
		return this.lottype;
	}

	public void setLottype(String lottype) {
		this.lottype = lottype;
	}

	@Column(name = "CAPITALIZED", precision = 22, scale = 0)
	public BigDecimal getCapitalized() {
		return this.capitalized;
	}

	public void setCapitalized(BigDecimal capitalized) {
		this.capitalized = capitalized;
	}

	@Column(name = "MSDSNUM", length = 10)
	public String getMsdsnum() {
		return this.msdsnum;
	}

	public void setMsdsnum(String msdsnum) {
		this.msdsnum = msdsnum;
	}

	@Column(name = "OUTSIDE", precision = 22, scale = 0)
	public BigDecimal getOutside() {
		return this.outside;
	}

	public void setOutside(BigDecimal outside) {
		this.outside = outside;
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

	@Column(name = "SPAREPARTAUTOADD", precision = 22, scale = 0)
	public BigDecimal getSparepartautoadd() {
		return this.sparepartautoadd;
	}

	public void setSparepartautoadd(BigDecimal sparepartautoadd) {
		this.sparepartautoadd = sparepartautoadd;
	}

	@Column(name = "CLASSSTRUCTUREID", nullable = false, length = 26)
	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	@Column(name = "INSPECTIONREQUIRED", precision = 22, scale = 0)
	public BigDecimal getInspectionrequired() {
		return this.inspectionrequired;
	}

	public void setInspectionrequired(BigDecimal inspectionrequired) {
		this.inspectionrequired = inspectionrequired;
	}

	@Column(name = "SOURCESYSID", length = 10)
	public String getSourcesysid() {
		return this.sourcesysid;
	}

	public void setSourcesysid(String sourcesysid) {
		this.sourcesysid = sourcesysid;
	}

	@Column(name = "OWNERSYSID", length = 10)
	public String getOwnersysid() {
		return this.ownersysid;
	}

	public void setOwnersysid(String ownersysid) {
		this.ownersysid = ownersysid;
	}

	@Column(name = "EXTERNALREFID", length = 10)
	public String getExternalrefid() {
		return this.externalrefid;
	}

	public void setExternalrefid(String externalrefid) {
		this.externalrefid = externalrefid;
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

	@Column(name = "SENDERSYSID", length = 50)
	public String getSendersysid() {
		return this.sendersysid;
	}

	public void setSendersysid(String sendersysid) {
		this.sendersysid = sendersysid;
	}

	@Column(name = "ITEMSETID", length = 8)
	public String getItemsetid() {
		return this.itemsetid;
	}

	public void setItemsetid(String itemsetid) {
		this.itemsetid = itemsetid;
	}

	@Column(name = "ORDERUNIT", length = 50)
	public String getOrderunit() {
		return this.orderunit;
	}

	public void setOrderunit(String orderunit) {
		this.orderunit = orderunit;
	}

	@Column(name = "ISSUEUNIT", length = 50)
	public String getIssueunit() {
		return this.issueunit;
	}

	public void setIssueunit(String issueunit) {
		this.issueunit = issueunit;
	}

	@Column(name = "CONDITIONENABLED", precision = 22, scale = 0)
	public BigDecimal getConditionenabled() {
		return this.conditionenabled;
	}

	public void setConditionenabled(BigDecimal conditionenabled) {
		this.conditionenabled = conditionenabled;
	}

	@Column(name = "GROUPNAME", length = 10)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "METERNAME", length = 20)
	public String getMetername() {
		return this.metername;
	}

	public void setMetername(String metername) {
		this.metername = metername;
	}

	@Column(name = "COMMODITYGROUP", length = 8)
	public String getCommoditygroup() {
		return this.commoditygroup;
	}

	public void setCommoditygroup(String commoditygroup) {
		this.commoditygroup = commoditygroup;
	}

	@Column(name = "COMMODITY", length = 8)
	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	@Column(name = "ITEMTYPE", nullable = false, length = 10)
	public String getItemtype() {
		return this.itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	@Column(name = "PRORATE", precision = 22, scale = 0)
	public BigDecimal getProrate() {
		return this.prorate;
	}

	public void setProrate(BigDecimal prorate) {
		this.prorate = prorate;
	}

	@Column(name = "ISKIT", precision = 22, scale = 0)
	public BigDecimal getIskit() {
		return this.iskit;
	}

	public void setIskit(BigDecimal iskit) {
		this.iskit = iskit;
	}

	@Column(name = "LANGCODE", length = 4)
	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	@Column(name = "ATTACHONISSUE", precision = 22, scale = 0)
	public BigDecimal getAttachonissue() {
		return this.attachonissue;
	}

	public void setAttachonissue(BigDecimal attachonissue) {
		this.attachonissue = attachonissue;
	}

	@Column(name = "HASLD", precision = 22, scale = 0)
	public BigDecimal getHasld() {
		return this.hasld;
	}

	public void setHasld(BigDecimal hasld) {
		this.hasld = hasld;
	}

	@Column(name = "STATUS", length = 15)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "IRNUM", length = 80)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
	public Set<ItemAttribute> getItemAttributes() {
		return this.itemAttributes;
	}

	public void setItemAttributes(Set<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
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

	@Column(name = "DISABLE_FLAG")
	public String getDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_BY")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "VALIDATE_DESCRIPTION")
	public String getValidateDescription() {
		return validateDescription;
	}

	public void setValidateDescription(String validateDescription) {
		this.validateDescription = validateDescription;
	}

	@Column(name = "CREATE_BY_DEPT_NO")
	public String getCreateByDeptNo() {
		return createByDeptNo;
	}

	public void setCreateByDeptNo(String createByDeptNo) {
		this.createByDeptNo = createByDeptNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHANGE_LAST_UPDATE")
	public Date getChangeLastUpdate() {
		return changeLastUpdate;
	}

	public void setChangeLastUpdate(Date changeLastUpdate) {
		this.changeLastUpdate = changeLastUpdate;
	}

	@Column(name = "DISABLE_FLAG_REMAK")
	public String getDisableFlagRemark() {
		return disableFlagRemark;
	}

	public void setDisableFlagRemark(String disableFlagRemark) {
		this.disableFlagRemark = disableFlagRemark;
	}

	@Column(name = "CREATE_ORGANIZATION_CODE")
	public String getCreateOrganizationCode() {
		return createOrganizationCode;
	}

	public void setCreateOrganizationCode(String createOrganizationCode) {
		this.createOrganizationCode = createOrganizationCode;
	}

}