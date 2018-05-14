package mro.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the L_ALNDOMAIN database table.
 * 
 */
@Entity
@Table(name="L_ALNDOMAIN")
public class LAlndomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="L_ALNDOMAIN_LALNDOMAINID_GENERATOR", sequenceName="L_ALNDOMAINSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="L_ALNDOMAIN_LALNDOMAINID_GENERATOR")
	@Column(name="L_ALNDOMAINID")
	private long lAlndomainid;

	private String description;

	private String langcode;

	private BigDecimal ownerid;

	private String rowstamp;

    public LAlndomain() {
    }

	public long getLAlndomainid() {
		return this.lAlndomainid;
	}

	public void setLAlndomainid(long lAlndomainid) {
		this.lAlndomainid = lAlndomainid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public BigDecimal getOwnerid() {
		return this.ownerid;
	}

	public void setOwnerid(BigDecimal ownerid) {
		this.ownerid = ownerid;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

}