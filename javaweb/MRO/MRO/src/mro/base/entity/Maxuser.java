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
 * The persistent class for the MAXUSER database table.
 * 
 */
@Entity
public class Maxuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MAXUSER_PERSONID_GENERATOR", sequenceName="MAXUSERSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MAXUSER_PERSONID_GENERATOR")
	private String personid;

	private String databaseuserid;

	private String defsite;

	private String defstoreroom;

	private BigDecimal failedlogins;

	private BigDecimal forceexpiration;

	private String loginid;

	private BigDecimal maxuserid;

	private String memo;

	private byte[] password;

    @Temporal( TemporalType.TIMESTAMP)
	private Date pwexpiration;

	private byte[] pwhintanswer;

	private String pwhintquestion;

	private BigDecimal querywithsite;

    @Temporal( TemporalType.TIMESTAMP)
	private Date restartdate;

	private String rowstamp;

	private String status;

	private String storeroomsite;

	private BigDecimal sysuser;

	@Column(name="\"TYPE\"")
	private String type;

	private String userid;

    public Maxuser() {
    }

	public String getPersonid() {
		return this.personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getDatabaseuserid() {
		return this.databaseuserid;
	}

	public void setDatabaseuserid(String databaseuserid) {
		this.databaseuserid = databaseuserid;
	}

	public String getDefsite() {
		return this.defsite;
	}

	public void setDefsite(String defsite) {
		this.defsite = defsite;
	}

	public String getDefstoreroom() {
		return this.defstoreroom;
	}

	public void setDefstoreroom(String defstoreroom) {
		this.defstoreroom = defstoreroom;
	}

	public BigDecimal getFailedlogins() {
		return this.failedlogins;
	}

	public void setFailedlogins(BigDecimal failedlogins) {
		this.failedlogins = failedlogins;
	}

	public BigDecimal getForceexpiration() {
		return this.forceexpiration;
	}

	public void setForceexpiration(BigDecimal forceexpiration) {
		this.forceexpiration = forceexpiration;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public BigDecimal getMaxuserid() {
		return this.maxuserid;
	}

	public void setMaxuserid(BigDecimal maxuserid) {
		this.maxuserid = maxuserid;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public byte[] getPassword() {
		return this.password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Date getPwexpiration() {
		return this.pwexpiration;
	}

	public void setPwexpiration(Date pwexpiration) {
		this.pwexpiration = pwexpiration;
	}

	public byte[] getPwhintanswer() {
		return this.pwhintanswer;
	}

	public void setPwhintanswer(byte[] pwhintanswer) {
		this.pwhintanswer = pwhintanswer;
	}

	public String getPwhintquestion() {
		return this.pwhintquestion;
	}

	public void setPwhintquestion(String pwhintquestion) {
		this.pwhintquestion = pwhintquestion;
	}

	public BigDecimal getQuerywithsite() {
		return this.querywithsite;
	}

	public void setQuerywithsite(BigDecimal querywithsite) {
		this.querywithsite = querywithsite;
	}

	public Date getRestartdate() {
		return this.restartdate;
	}

	public void setRestartdate(Date restartdate) {
		this.restartdate = restartdate;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStoreroomsite() {
		return this.storeroomsite;
	}

	public void setStoreroomsite(String storeroomsite) {
		this.storeroomsite = storeroomsite;
	}

	public BigDecimal getSysuser() {
		return this.sysuser;
	}

	public void setSysuser(BigDecimal sysuser) {
		this.sysuser = sysuser;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}