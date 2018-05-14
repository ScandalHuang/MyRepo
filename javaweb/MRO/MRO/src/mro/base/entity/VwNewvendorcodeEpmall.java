package mro.base.entity;

// Generated 2015/1/7 �U�� 01:34:40 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "VW_NEWVENDORCODE_EPMALL")
public class VwNewvendorcodeEpmall implements java.io.Serializable {

	private BigDecimal oracleVendorId;
	private String oracleVendorCode;
	private String sapVendorId;
	private String sapVendorCode;
	private String nvcid;
	private String newvendorcode;
	private String newvendorname;
	private String registrationnum;

	public VwNewvendorcodeEpmall() {
	}

	public VwNewvendorcodeEpmall(String nvcid) {
		this.nvcid = nvcid;
	}

	public VwNewvendorcodeEpmall(BigDecimal oracleVendorId,
			String oracleVendorCode, String sapVendorId, String sapVendorCode,
			String nvcid, String newvendorcode, String newvendorname,
			String registrationnum) {
		this.oracleVendorId = oracleVendorId;
		this.oracleVendorCode = oracleVendorCode;
		this.sapVendorId = sapVendorId;
		this.sapVendorCode = sapVendorCode;
		this.nvcid = nvcid;
		this.newvendorcode = newvendorcode;
		this.newvendorname = newvendorname;
		this.registrationnum = registrationnum;
	}
	@Id
	@Column(name = "NVCID", nullable = false, length = 24)
	public String getNvcid() {
		return this.nvcid;
	}

	public void setNvcid(String nvcid) {
		this.nvcid = nvcid;
	}
	
	@Column(name = "ORACLE_VENDOR_ID", precision = 22, scale = 0)
	public BigDecimal getOracleVendorId() {
		return this.oracleVendorId;
	}

	public void setOracleVendorId(BigDecimal oracleVendorId) {
		this.oracleVendorId = oracleVendorId;
	}

	@Column(name = "ORACLE_VENDOR_CODE", length = 72)
	public String getOracleVendorCode() {
		return this.oracleVendorCode;
	}

	public void setOracleVendorCode(String oracleVendorCode) {
		this.oracleVendorCode = oracleVendorCode;
	}

	@Column(name = "SAP_VENDOR_ID", length = 48)
	public String getSapVendorId() {
		return this.sapVendorId;
	}

	public void setSapVendorId(String sapVendorId) {
		this.sapVendorId = sapVendorId;
	}

	@Column(name = "SAP_VENDOR_CODE", length = 72)
	public String getSapVendorCode() {
		return this.sapVendorCode;
	}

	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}


	@Column(name = "NEWVENDORCODE", length = 48)
	public String getNewvendorcode() {
		return this.newvendorcode;
	}

	public void setNewvendorcode(String newvendorcode) {
		this.newvendorcode = newvendorcode;
	}

	@Column(name = "NEWVENDORNAME", length = 720)
	public String getNewvendorname() {
		return this.newvendorname;
	}

	public void setNewvendorname(String newvendorname) {
		this.newvendorname = newvendorname;
	}

	@Column(name = "REGISTRATIONNUM", length = 72)
	public String getRegistrationnum() {
		return this.registrationnum;
	}

	public void setRegistrationnum(String registrationnum) {
		this.registrationnum = registrationnum;
	}

}
