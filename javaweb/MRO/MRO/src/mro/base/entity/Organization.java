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
 * The persistent class for the ORGANIZATION database table.
 * 
 */
@Entity
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String orgid;

	private BigDecimal active;

	private String basecurrency1;

	private String basecurrency2;

	private String clearingacct;

	private String companysetid;

	private String description;

	private String enterby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date enterdate;

	private BigDecimal hasld;

	private String itemsetid;

	private String langcode;

	@Id
	@SequenceGenerator(name="ORGANIZATION_ORGID_GENERATOR", sequenceName="ORGANIZATIONSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORGANIZATION_ORGID_GENERATOR")
	private BigDecimal organizationid;

	private String rowstamp;

    public Organization() {
    }

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public BigDecimal getActive() {
		return this.active;
	}

	public void setActive(BigDecimal active) {
		this.active = active;
	}

	public String getBasecurrency1() {
		return this.basecurrency1;
	}

	public void setBasecurrency1(String basecurrency1) {
		this.basecurrency1 = basecurrency1;
	}

	public String getBasecurrency2() {
		return this.basecurrency2;
	}

	public void setBasecurrency2(String basecurrency2) {
		this.basecurrency2 = basecurrency2;
	}

	public String getClearingacct() {
		return this.clearingacct;
	}

	public void setClearingacct(String clearingacct) {
		this.clearingacct = clearingacct;
	}

	public String getCompanysetid() {
		return this.companysetid;
	}

	public void setCompanysetid(String companysetid) {
		this.companysetid = companysetid;
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

	public String getItemsetid() {
		return this.itemsetid;
	}

	public void setItemsetid(String itemsetid) {
		this.itemsetid = itemsetid;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public BigDecimal getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(BigDecimal organizationid) {
		this.organizationid = organizationid;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

}