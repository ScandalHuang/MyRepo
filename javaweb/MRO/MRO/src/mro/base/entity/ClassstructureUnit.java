package mro.base.entity;

// Generated 2013/4/26 �U�� 01:27:48 by Hibernate Tools 3.2.2.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ClassstructureUnit generated by hbm2java
 */
@Entity
@Table(name = "CLASSSTRUCTURE_UNIT")
public class ClassstructureUnit implements java.io.Serializable {

	private String classstructureid;
	private String minBasicUnit;
	private String packageUnit;
	private Date updateDate;
	private String updateBy;

	public ClassstructureUnit() {
	}

	public ClassstructureUnit(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public ClassstructureUnit(String classstructureid, String minBasicUnit,
			String packageUnit, Date updateDate, String updateBy) {
		this.classstructureid = classstructureid;
		this.minBasicUnit = minBasicUnit;
		this.packageUnit = packageUnit;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
	}

	@Id
	@Column(name = "CLASSSTRUCTUREID", unique = true, nullable = false, length = 26)
	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	@Column(name = "MIN_BASIC_UNIT", length = 300)
	public String getMinBasicUnit() {
		return this.minBasicUnit;
	}

	public void setMinBasicUnit(String minBasicUnit) {
		this.minBasicUnit = minBasicUnit;
	}

	@Column(name = "PACKAGE_UNIT", length = 300)
	public String getPackageUnit() {
		return this.packageUnit;
	}

	public void setPackageUnit(String packageUnit) {
		this.packageUnit = packageUnit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "UPDATE_BY", length = 30)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}