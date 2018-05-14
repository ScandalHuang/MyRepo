package mro.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SITE database table.
 * 
 */
@Entity
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String siteid;

	private BigDecimal active;

	private String billtoaddresscode;

	private String billtolaborcode;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private String contact;

	private String contact1;

	private String contact2;

	private String contact3;

	private String contactgroup;

	private String description;

	private String enterby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date enterdate;

	private BigDecimal hasld;

	private String langcode;

	private BigDecimal mtimes;

	private BigDecimal opspan;

	private String orgid;

	private String rowstamp;

	private String shiptoaddresscode;

	private String shiptolaborcode;

	@Id
	@SequenceGenerator(name="SITE_SITEID_GENERATOR", sequenceName="SITESEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SITE_SITEID_GENERATOR")
	private BigDecimal siteuid;

    public Site() {
    }

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public BigDecimal getActive() {
		return this.active;
	}

	public void setActive(BigDecimal active) {
		this.active = active;
	}

	public String getBilltoaddresscode() {
		return this.billtoaddresscode;
	}

	public void setBilltoaddresscode(String billtoaddresscode) {
		this.billtoaddresscode = billtoaddresscode;
	}

	public String getBilltolaborcode() {
		return this.billtolaborcode;
	}

	public void setBilltolaborcode(String billtolaborcode) {
		this.billtolaborcode = billtolaborcode;
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

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact1() {
		return this.contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getContact2() {
		return this.contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getContact3() {
		return this.contact3;
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3;
	}

	public String getContactgroup() {
		return this.contactgroup;
	}

	public void setContactgroup(String contactgroup) {
		this.contactgroup = contactgroup;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnterby() {
		return this.enterby;
	}

	public void setEnterby(String enterby) {
		this.enterby = enterby;
	}

	public Date getEnterdate() {
		return this.enterdate;
	}

	public void setEnterdate(Date enterdate) {
		this.enterdate = enterdate;
	}

	public BigDecimal getHasld() {
		return this.hasld;
	}

	public void setHasld(BigDecimal hasld) {
		this.hasld = hasld;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public BigDecimal getMtimes() {
		return this.mtimes;
	}

	public void setMtimes(BigDecimal mtimes) {
		this.mtimes = mtimes;
	}

	public BigDecimal getOpspan() {
		return this.opspan;
	}

	public void setOpspan(BigDecimal opspan) {
		this.opspan = opspan;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getShiptoaddresscode() {
		return this.shiptoaddresscode;
	}

	public void setShiptoaddresscode(String shiptoaddresscode) {
		this.shiptoaddresscode = shiptoaddresscode;
	}

	public String getShiptolaborcode() {
		return this.shiptolaborcode;
	}

	public void setShiptolaborcode(String shiptolaborcode) {
		this.shiptolaborcode = shiptolaborcode;
	}

	public BigDecimal getSiteuid() {
		return this.siteuid;
	}

	public void setSiteuid(BigDecimal siteuid) {
		this.siteuid = siteuid;
	}

}