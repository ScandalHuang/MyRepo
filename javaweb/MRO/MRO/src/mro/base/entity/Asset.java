package mro.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ASSET database table.
 * 
 */
@Entity
public class Asset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ASSET_ROWSTAMP_GENERATOR", sequenceName="ASSETSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSET_ROWSTAMP_GENERATOR")
	private String rowstamp;

	private String ancestor;

	private BigDecimal assetid;

	private String assetnum;

	private String assettag;

	private String assettype;

	private BigDecimal assetuid;

	private BigDecimal autowogen;

	private String binnum;

	private BigDecimal budgetcost;

	private String calnum;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private BigDecimal children;

	private String classstructureid;

	private String conditioncode;

	private String description;

	private BigDecimal disabled;

	private String eq1;

	private String eq10;

	private String eq11;

	private BigDecimal eq12;

	private String eq2;

    @Temporal( TemporalType.TIMESTAMP)
	private Date eq23;

	private BigDecimal eq24;

	private String eq3;

	private String eq4;

	private BigDecimal eq5;

    @Temporal( TemporalType.TIMESTAMP)
	private Date eq6;

	private BigDecimal eq7;

	private String eq8;

	private String eq9;

	private String externalrefid;

	private String failurecode;

	private String glaccount;

	private String groupname;

	private BigDecimal hasld;

    @Temporal( TemporalType.TIMESTAMP)
	private Date installdate;

	private BigDecimal invcost;

	private BigDecimal isrunning;

	private String itemnum;

	private String itemsetid;

	private String itemtype;

	private String langcode;

	private String location;

	private BigDecimal mainthierchy;

	private String manufacturer;

	private BigDecimal moved;

	private String orgid;

	private String ownersysid;

	private String parent;

	private BigDecimal priority;

	private BigDecimal purchaseprice;

	private BigDecimal replacecost;

	private String rotsuspacct;

	private String sendersysid;

	private String serialnum;

	private String shiftnum;

	private String siteid;

	private String sourcesysid;

	private String status;

    @Temporal( TemporalType.TIMESTAMP)
	private Date statusdate;

	private String toolcontrolaccount;

	private BigDecimal toolrate;

	private BigDecimal totalcost;

	private double totdowntime;

	private BigDecimal totunchargedcost;

	private BigDecimal unchargedcost;

	@Column(name="\"USAGE\"")
	private String usage;

	private String vendor;

    @Temporal( TemporalType.TIMESTAMP)
	private Date warrantyexpdate;

	private BigDecimal ytdcost;

    public Asset() {
    }

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getAncestor() {
		return this.ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	public BigDecimal getAssetid() {
		return this.assetid;
	}

	public void setAssetid(BigDecimal assetid) {
		this.assetid = assetid;
	}

	public String getAssetnum() {
		return this.assetnum;
	}

	public void setAssetnum(String assetnum) {
		this.assetnum = assetnum;
	}

	public String getAssettag() {
		return this.assettag;
	}

	public void setAssettag(String assettag) {
		this.assettag = assettag;
	}

	public String getAssettype() {
		return this.assettype;
	}

	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}

	public BigDecimal getAssetuid() {
		return this.assetuid;
	}

	public void setAssetuid(BigDecimal assetuid) {
		this.assetuid = assetuid;
	}

	public BigDecimal getAutowogen() {
		return this.autowogen;
	}

	public void setAutowogen(BigDecimal autowogen) {
		this.autowogen = autowogen;
	}

	public String getBinnum() {
		return this.binnum;
	}

	public void setBinnum(String binnum) {
		this.binnum = binnum;
	}

	public BigDecimal getBudgetcost() {
		return this.budgetcost;
	}

	public void setBudgetcost(BigDecimal budgetcost) {
		this.budgetcost = budgetcost;
	}

	public String getCalnum() {
		return this.calnum;
	}

	public void setCalnum(String calnum) {
		this.calnum = calnum;
	}

	public String getChangeby() {
		return this.changeby;
	}

	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}

	public Date getChangedate() {
		return this.changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public BigDecimal getChildren() {
		return this.children;
	}

	public void setChildren(BigDecimal children) {
		this.children = children;
	}

	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public String getConditioncode() {
		return this.conditioncode;
	}

	public void setConditioncode(String conditioncode) {
		this.conditioncode = conditioncode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDisabled() {
		return this.disabled;
	}

	public void setDisabled(BigDecimal disabled) {
		this.disabled = disabled;
	}

	public String getEq1() {
		return this.eq1;
	}

	public void setEq1(String eq1) {
		this.eq1 = eq1;
	}

	public String getEq10() {
		return this.eq10;
	}

	public void setEq10(String eq10) {
		this.eq10 = eq10;
	}

	public String getEq11() {
		return this.eq11;
	}

	public void setEq11(String eq11) {
		this.eq11 = eq11;
	}

	public BigDecimal getEq12() {
		return this.eq12;
	}

	public void setEq12(BigDecimal eq12) {
		this.eq12 = eq12;
	}

	public String getEq2() {
		return this.eq2;
	}

	public void setEq2(String eq2) {
		this.eq2 = eq2;
	}

	public Date getEq23() {
		return this.eq23;
	}

	public void setEq23(Date eq23) {
		this.eq23 = eq23;
	}

	public BigDecimal getEq24() {
		return this.eq24;
	}

	public void setEq24(BigDecimal eq24) {
		this.eq24 = eq24;
	}

	public String getEq3() {
		return this.eq3;
	}

	public void setEq3(String eq3) {
		this.eq3 = eq3;
	}

	public String getEq4() {
		return this.eq4;
	}

	public void setEq4(String eq4) {
		this.eq4 = eq4;
	}

	public BigDecimal getEq5() {
		return this.eq5;
	}

	public void setEq5(BigDecimal eq5) {
		this.eq5 = eq5;
	}

	public Date getEq6() {
		return this.eq6;
	}

	public void setEq6(Date eq6) {
		this.eq6 = eq6;
	}

	public BigDecimal getEq7() {
		return this.eq7;
	}

	public void setEq7(BigDecimal eq7) {
		this.eq7 = eq7;
	}

	public String getEq8() {
		return this.eq8;
	}

	public void setEq8(String eq8) {
		this.eq8 = eq8;
	}

	public String getEq9() {
		return this.eq9;
	}

	public void setEq9(String eq9) {
		this.eq9 = eq9;
	}

	public String getExternalrefid() {
		return this.externalrefid;
	}

	public void setExternalrefid(String externalrefid) {
		this.externalrefid = externalrefid;
	}

	public String getFailurecode() {
		return this.failurecode;
	}

	public void setFailurecode(String failurecode) {
		this.failurecode = failurecode;
	}

	public String getGlaccount() {
		return this.glaccount;
	}

	public void setGlaccount(String glaccount) {
		this.glaccount = glaccount;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public BigDecimal getHasld() {
		return this.hasld;
	}

	public void setHasld(BigDecimal hasld) {
		this.hasld = hasld;
	}

	public Date getInstalldate() {
		return this.installdate;
	}

	public void setInstalldate(Date installdate) {
		this.installdate = installdate;
	}

	public BigDecimal getInvcost() {
		return this.invcost;
	}

	public void setInvcost(BigDecimal invcost) {
		this.invcost = invcost;
	}

	public BigDecimal getIsrunning() {
		return this.isrunning;
	}

	public void setIsrunning(BigDecimal isrunning) {
		this.isrunning = isrunning;
	}

	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getItemsetid() {
		return this.itemsetid;
	}

	public void setItemsetid(String itemsetid) {
		this.itemsetid = itemsetid;
	}

	public String getItemtype() {
		return this.itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getMainthierchy() {
		return this.mainthierchy;
	}

	public void setMainthierchy(BigDecimal mainthierchy) {
		this.mainthierchy = mainthierchy;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public BigDecimal getMoved() {
		return this.moved;
	}

	public void setMoved(BigDecimal moved) {
		this.moved = moved;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOwnersysid() {
		return this.ownersysid;
	}

	public void setOwnersysid(String ownersysid) {
		this.ownersysid = ownersysid;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

	public BigDecimal getPurchaseprice() {
		return this.purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public BigDecimal getReplacecost() {
		return this.replacecost;
	}

	public void setReplacecost(BigDecimal replacecost) {
		this.replacecost = replacecost;
	}

	public String getRotsuspacct() {
		return this.rotsuspacct;
	}

	public void setRotsuspacct(String rotsuspacct) {
		this.rotsuspacct = rotsuspacct;
	}

	public String getSendersysid() {
		return this.sendersysid;
	}

	public void setSendersysid(String sendersysid) {
		this.sendersysid = sendersysid;
	}

	public String getSerialnum() {
		return this.serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	public String getShiftnum() {
		return this.shiftnum;
	}

	public void setShiftnum(String shiftnum) {
		this.shiftnum = shiftnum;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSourcesysid() {
		return this.sourcesysid;
	}

	public void setSourcesysid(String sourcesysid) {
		this.sourcesysid = sourcesysid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusdate() {
		return this.statusdate;
	}

	public void setStatusdate(Date statusdate) {
		this.statusdate = statusdate;
	}

	public String getToolcontrolaccount() {
		return this.toolcontrolaccount;
	}

	public void setToolcontrolaccount(String toolcontrolaccount) {
		this.toolcontrolaccount = toolcontrolaccount;
	}

	public BigDecimal getToolrate() {
		return this.toolrate;
	}

	public void setToolrate(BigDecimal toolrate) {
		this.toolrate = toolrate;
	}

	public BigDecimal getTotalcost() {
		return this.totalcost;
	}

	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}

	public double getTotdowntime() {
		return this.totdowntime;
	}

	public void setTotdowntime(double totdowntime) {
		this.totdowntime = totdowntime;
	}

	public BigDecimal getTotunchargedcost() {
		return this.totunchargedcost;
	}

	public void setTotunchargedcost(BigDecimal totunchargedcost) {
		this.totunchargedcost = totunchargedcost;
	}

	public BigDecimal getUnchargedcost() {
		return this.unchargedcost;
	}

	public void setUnchargedcost(BigDecimal unchargedcost) {
		this.unchargedcost = unchargedcost;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getWarrantyexpdate() {
		return this.warrantyexpdate;
	}

	public void setWarrantyexpdate(Date warrantyexpdate) {
		this.warrantyexpdate = warrantyexpdate;
	}

	public BigDecimal getYtdcost() {
		return this.ytdcost;
	}

	public void setYtdcost(BigDecimal ytdcost) {
		this.ytdcost = ytdcost;
	}

}