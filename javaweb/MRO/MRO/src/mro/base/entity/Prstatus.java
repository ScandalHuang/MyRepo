package mro.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PRSTATUS database table.
 * 
 */
@Entity
public class Prstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRSTATUS_PRSTATUSID_GENERATOR", sequenceName="PRSTATUSIDSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRSTATUS_PRSTATUSID_GENERATOR")
	private long prstatusid;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private String memo;

	private String orgid;

	private String prnum;

	private String rowstamp;

	private String siteid;

	private String status;

    public Prstatus() {
    }

	public long getPrstatusid() {
		return this.prstatusid;
	}

	public void setPrstatusid(long prstatusid) {
		this.prstatusid = prstatusid;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getPrnum() {
		return this.prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}