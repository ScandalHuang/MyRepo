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
 * The persistent class for the L_SYNONYMDOMAIN database table.
 * 
 */
@Entity
@Table(name="L_SYNONYMDOMAIN")
public class LSynonymdomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="L_SYNONYMDOMAIN_LSYNONYMDOMAINID_GENERATOR", sequenceName="L_SYNONYMDOMAIN")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="L_SYNONYMDOMAIN_LSYNONYMDOMAINID_GENERATOR")
	@Column(name="L_SYNONYMDOMAINID")
	private long lSynonymdomainid;

	private String description;

	private String langcode;

	private BigDecimal ownerid;

	private String rowstamp;

    public LSynonymdomain() {
    }

	public long getLSynonymdomainid() {
		return this.lSynonymdomainid;
	}

	public void setLSynonymdomainid(long lSynonymdomainid) {
		this.lSynonymdomainid = lSynonymdomainid;
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