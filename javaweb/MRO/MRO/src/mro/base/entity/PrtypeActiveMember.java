package mro.base.entity;

// Generated 2014/3/25 �W�� 10:06:26 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PrtypeActiveMember generated by hbm2java
 */
@Entity
@Table(name = "PRTYPE_ACTIVE_MEMBER")
public class PrtypeActiveMember implements java.io.Serializable {

	private BigDecimal prtypeActiveMemberId;
	private String employeeNum;
	private String prtype;
	private Date lastupdateDate;
	private String lastupdateBy;
	private LocationSiteMap locationSiteMap;

	public PrtypeActiveMember() {
	}

	public PrtypeActiveMember(BigDecimal prtypeActiveMemberId,
			String employeeNum, String prtype,LocationSiteMap locationSiteMap) {
		this.prtypeActiveMemberId = prtypeActiveMemberId;
		this.employeeNum = employeeNum;
		this.prtype = prtype;
	}

	public PrtypeActiveMember(BigDecimal prtypeActiveMemberId,
			String employeeNum, String prtype, Date lastupdateDate,
			String lastupdateBy,LocationSiteMap locationSiteMap) {
		this.prtypeActiveMemberId = prtypeActiveMemberId;
		this.employeeNum = employeeNum;
		this.prtype = prtype;
		this.lastupdateDate = lastupdateDate;
		this.lastupdateBy = lastupdateBy;
		this.locationSiteMap = locationSiteMap;
	}

	@Id
	@SequenceGenerator(name="PRTYPE_ACTIVE_MEMBER_ID_GENERATOR", sequenceName="PRTYPE_ACTIVE_MEMBER_SEQ",allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRTYPE_ACTIVE_MEMBER_ID_GENERATOR")
	@Column(name = "PRTYPE_ACTIVE_MEMBER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPrtypeActiveMemberId() {
		return this.prtypeActiveMemberId;
	}

	public void setPrtypeActiveMemberId(BigDecimal prtypeActiveMemberId) {
		this.prtypeActiveMemberId = prtypeActiveMemberId;
	}

	@Column(name = "EMPLOYEE_NUM", nullable = false, length = 10)
	public String getEmployeeNum() {
		return this.employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	@Column(name = "PRTYPE", nullable = false, length = 30)
	public String getPrtype() {
		return this.prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTUPDATE_DATE", length = 7)
	public Date getLastupdateDate() {
		return this.lastupdateDate;
	}

	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}

	@Column(name = "LASTUPDATE_BY", length = 16)
	public String getLastupdateBy() {
		return this.lastupdateBy;
	}

	public void setLastupdateBy(String lastupdateBy) {
		this.lastupdateBy = lastupdateBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATION_SITE")
	public LocationSiteMap getLocationSiteMap() {
		return locationSiteMap;
	}

	public void setLocationSiteMap(LocationSiteMap locationSiteMap) {
		this.locationSiteMap = locationSiteMap;
	}

}