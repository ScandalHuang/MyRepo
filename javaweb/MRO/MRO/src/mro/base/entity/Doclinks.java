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
 * The persistent class for the DOCLINKS database table.
 * 
 */
@Entity
public class Doclinks implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCLINKS_DOCLINKSID_GENERATOR", sequenceName="DOCLINKSIDSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCLINKS_DOCLINKSID_GENERATOR")
	private long doclinksid;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private BigDecimal copylinktowo;

	private String createby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date createdate;

	private BigDecimal docinfoid;

	private String doctype;

	private String document;

	private String docversion;

	private BigDecimal getlatestversion;

	private BigDecimal ownerid;

	private String ownertable;

	private BigDecimal printthrulink;

	private String reference;

	private String rowstamp;

    public Doclinks() {
    }

	public long getDoclinksid() {
		return this.doclinksid;
	}

	public void setDoclinksid(long doclinksid) {
		this.doclinksid = doclinksid;
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

	public BigDecimal getCopylinktowo() {
		return this.copylinktowo;
	}

	public void setCopylinktowo(BigDecimal copylinktowo) {
		this.copylinktowo = copylinktowo;
	}

	public String getCreateby() {
		return this.createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public BigDecimal getDocinfoid() {
		return this.docinfoid;
	}

	public void setDocinfoid(BigDecimal docinfoid) {
		this.docinfoid = docinfoid;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDocument() {
		return this.document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocversion() {
		return this.docversion;
	}

	public void setDocversion(String docversion) {
		this.docversion = docversion;
	}

	public BigDecimal getGetlatestversion() {
		return this.getlatestversion;
	}

	public void setGetlatestversion(BigDecimal getlatestversion) {
		this.getlatestversion = getlatestversion;
	}

	public BigDecimal getOwnerid() {
		return this.ownerid;
	}

	public void setOwnerid(BigDecimal ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnertable() {
		return this.ownertable;
	}

	public void setOwnertable(String ownertable) {
		this.ownertable = ownertable;
	}

	public BigDecimal getPrintthrulink() {
		return this.printthrulink;
	}

	public void setPrintthrulink(BigDecimal printthrulink) {
		this.printthrulink = printthrulink;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

}