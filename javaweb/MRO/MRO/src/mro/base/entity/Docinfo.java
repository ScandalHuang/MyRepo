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
 * The persistent class for the DOCINFO database table.
 * 
 */
@Entity
public class Docinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCINFO_DOCINFOID_GENERATOR", sequenceName="DOCINFOIDSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCINFO_DOCINFOID_GENERATOR")
	private long docinfoid;

	private String application;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private String createby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date createdate;

	private String description;

	private String dmsname;

	private String doclocation;

	private String doctype;

	private String document;

	private BigDecimal hasld;

	private String langcode;

	private BigDecimal printthrulinkdflt;

	private BigDecimal revision;

	private String rowstamp;

	private BigDecimal show;

	private String status;

    @Temporal( TemporalType.TIMESTAMP)
	private Date statusdate;

	private String urlname;

	private String urlparam1;

	private String urlparam2;

	private String urlparam3;

	private String urlparam4;

	private String urlparam5;

	private String urltype;

	private BigDecimal usedefaultfilepath;

    public Docinfo() {
    }

	public long getDocinfoid() {
		return this.docinfoid;
	}

	public void setDocinfoid(long docinfoid) {
		this.docinfoid = docinfoid;
	}

	public String getApplication() {
		return this.application;
	}

	public void setApplication(String application) {
		this.application = application;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDmsname() {
		return this.dmsname;
	}

	public void setDmsname(String dmsname) {
		this.dmsname = dmsname;
	}

	public String getDoclocation() {
		return this.doclocation;
	}

	public void setDoclocation(String doclocation) {
		this.doclocation = doclocation;
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

	public BigDecimal getPrintthrulinkdflt() {
		return this.printthrulinkdflt;
	}

	public void setPrintthrulinkdflt(BigDecimal printthrulinkdflt) {
		this.printthrulinkdflt = printthrulinkdflt;
	}

	public BigDecimal getRevision() {
		return this.revision;
	}

	public void setRevision(BigDecimal revision) {
		this.revision = revision;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public BigDecimal getShow() {
		return this.show;
	}

	public void setShow(BigDecimal show) {
		this.show = show;
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

	public String getUrlname() {
		return this.urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}

	public String getUrlparam1() {
		return this.urlparam1;
	}

	public void setUrlparam1(String urlparam1) {
		this.urlparam1 = urlparam1;
	}

	public String getUrlparam2() {
		return this.urlparam2;
	}

	public void setUrlparam2(String urlparam2) {
		this.urlparam2 = urlparam2;
	}

	public String getUrlparam3() {
		return this.urlparam3;
	}

	public void setUrlparam3(String urlparam3) {
		this.urlparam3 = urlparam3;
	}

	public String getUrlparam4() {
		return this.urlparam4;
	}

	public void setUrlparam4(String urlparam4) {
		this.urlparam4 = urlparam4;
	}

	public String getUrlparam5() {
		return this.urlparam5;
	}

	public void setUrlparam5(String urlparam5) {
		this.urlparam5 = urlparam5;
	}

	public String getUrltype() {
		return this.urltype;
	}

	public void setUrltype(String urltype) {
		this.urltype = urltype;
	}

	public BigDecimal getUsedefaultfilepath() {
		return this.usedefaultfilepath;
	}

	public void setUsedefaultfilepath(BigDecimal usedefaultfilepath) {
		this.usedefaultfilepath = usedefaultfilepath;
	}

}