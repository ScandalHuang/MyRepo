package mro.base.entity;

// Generated 2013/1/7 �U�� 04:14:12 by Hibernate Tools 3.2.2.GA

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
 * AItemspec generated by hbm2java
 */
@Entity
@Table(name = "A_ITEMSPEC")
public class AItemspec implements java.io.Serializable {

	private BigDecimal AItemspecid;
	private AItem AItem;
	private BigDecimal allasspecusevalue=new BigDecimal(0);
	private BigDecimal alllocspecusevalue=new BigDecimal(0);
	private String alnvalue;
	private String assetattrid;
	private String changeby;
	private Date changedate;
	private String classstructureid;
	private BigDecimal displaysequence;
	private String is01;
	private String is02;
	private String is03;
	private Date is04;
	private BigDecimal is05;
	private String itemnum;
	private String itemsetid;
	private BigDecimal itemspecid;
	private String measureunitid;
	private BigDecimal numvalue;
	private String orgid;
	private BigDecimal rotating=new BigDecimal(0);
	private String section;
	private String eauditusername;
	private Date eaudittimestamp;
	private String eaudittype;
	private String esigtransid;
	private String duplicateitemspec;
	private String status;
	private String datatype;
	private BigDecimal itemrequirevalue;

	public AItemspec() {
	}

	public AItemspec(BigDecimal AItemspecid, AItem AItem,
			String eauditusername, Date eaudittimestamp, String eaudittype) {
		this.AItemspecid = AItemspecid;
		this.AItem = AItem;
		this.eauditusername = eauditusername;
		this.eaudittimestamp = eaudittimestamp;
		this.eaudittype = eaudittype;
	}

	public AItemspec(BigDecimal AItemspecid, AItem AItem,
			BigDecimal allasspecusevalue, BigDecimal alllocspecusevalue,
			String alnvalue, String assetattrid, String changeby,
			Date changedate, String classstructureid,
			BigDecimal displaysequence, String is01, String is02, String is03,
			Date is04, BigDecimal is05, String itemnum, String itemsetid,
			BigDecimal itemspecid, String measureunitid, BigDecimal numvalue,
			String orgid, BigDecimal rotating, String section,
			String eauditusername, Date eaudittimestamp, String eaudittype,
			String esigtransid, String duplicateitemspec, String status,String datatype,
			BigDecimal itemrequirevalue) {
		this.AItemspecid = AItemspecid;
		this.AItem = AItem;
		this.allasspecusevalue = allasspecusevalue;
		this.alllocspecusevalue = alllocspecusevalue;
		this.alnvalue = alnvalue;
		this.assetattrid = assetattrid;
		this.changeby = changeby;
		this.changedate = changedate;
		this.classstructureid = classstructureid;
		this.displaysequence = displaysequence;
		this.is01 = is01;
		this.is02 = is02;
		this.is03 = is03;
		this.is04 = is04;
		this.is05 = is05;
		this.itemnum = itemnum;
		this.itemsetid = itemsetid;
		this.itemspecid = itemspecid;
		this.measureunitid = measureunitid;
		this.numvalue = numvalue;
		this.orgid = orgid;
		this.rotating = rotating;
		this.section = section;
		this.eauditusername = eauditusername;
		this.eaudittimestamp = eaudittimestamp;
		this.eaudittype = eaudittype;
		this.esigtransid = esigtransid;
		this.duplicateitemspec = duplicateitemspec;
		this.status = status;
		this.datatype = datatype;
		this.itemrequirevalue =itemrequirevalue;
	}

	@Id
	@SequenceGenerator(name="A_ITEMSPECID_GENERATOR", sequenceName="AITEMSPEC_SEQ" ,allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="A_ITEMSPECID_GENERATOR")
	@Column(name = "A_ITEMSPECID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAItemspecid() {
		return this.AItemspecid;
	}

	public void setAItemspecid(BigDecimal AItemspecid) {
		this.AItemspecid = AItemspecid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EAUDITTRANSID", nullable = false)
	public AItem getAItem() {
		return this.AItem;
	}

	public void setAItem(AItem AItem) {
		this.AItem = AItem;
	}

	@Column(name = "ALLASSPECUSEVALUE", precision = 22, scale = 0)
	public BigDecimal getAllasspecusevalue() {
		return this.allasspecusevalue;
	}

	public void setAllasspecusevalue(BigDecimal allasspecusevalue) {
		this.allasspecusevalue = allasspecusevalue;
	}

	@Column(name = "ALLLOCSPECUSEVALUE", precision = 22, scale = 0)
	public BigDecimal getAlllocspecusevalue() {
		return this.alllocspecusevalue;
	}

	public void setAlllocspecusevalue(BigDecimal alllocspecusevalue) {
		this.alllocspecusevalue = alllocspecusevalue;
	}

	@Column(name = "ALNVALUE", length = 200)
	public String getAlnvalue() {
		return this.alnvalue;
	}

	public void setAlnvalue(String alnvalue) {
		this.alnvalue = alnvalue;
	}

	@Column(name = "ASSETATTRID", length = 8)
	public String getAssetattrid() {
		return this.assetattrid;
	}

	public void setAssetattrid(String assetattrid) {
		this.assetattrid = assetattrid;
	}

	@Column(name = "CHANGEBY", length = 30)
	public String getChangeby() {
		return this.changeby;
	}

	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHANGEDATE", length = 7)
	public Date getChangedate() {
		return this.changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	@Column(name = "CLASSSTRUCTUREID", length = 26)
	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	@Column(name = "DISPLAYSEQUENCE", precision = 22, scale = 0)
	public BigDecimal getDisplaysequence() {
		return this.displaysequence;
	}

	public void setDisplaysequence(BigDecimal displaysequence) {
		this.displaysequence = displaysequence;
	}

	@Column(name = "IS01", length = 10)
	public String getIs01() {
		return this.is01;
	}

	public void setIs01(String is01) {
		this.is01 = is01;
	}

	@Column(name = "IS02", length = 10)
	public String getIs02() {
		return this.is02;
	}

	public void setIs02(String is02) {
		this.is02 = is02;
	}

	@Column(name = "IS03", length = 10)
	public String getIs03() {
		return this.is03;
	}

	public void setIs03(String is03) {
		this.is03 = is03;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IS04", length = 7)
	public Date getIs04() {
		return this.is04;
	}

	public void setIs04(Date is04) {
		this.is04 = is04;
	}

	@Column(name = "IS05", precision = 15)
	public BigDecimal getIs05() {
		return this.is05;
	}

	public void setIs05(BigDecimal is05) {
		this.is05 = is05;
	}

	@Column(name = "ITEMNUM", length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "ITEMSETID", length = 8)
	public String getItemsetid() {
		return this.itemsetid;
	}

	public void setItemsetid(String itemsetid) {
		this.itemsetid = itemsetid;
	}

	@Column(name = "ITEMSPECID", precision = 22, scale = 0)
	public BigDecimal getItemspecid() {
		return this.itemspecid;
	}

	public void setItemspecid(BigDecimal itemspecid) {
		this.itemspecid = itemspecid;
	}

	@Column(name = "MEASUREUNITID", length = 50)
	public String getMeasureunitid() {
		return this.measureunitid;
	}

	public void setMeasureunitid(String measureunitid) {
		this.measureunitid = measureunitid;
	}

	@Column(name = "NUMVALUE", precision = 10)
	public BigDecimal getNumvalue() {
		return this.numvalue;
	}

	public void setNumvalue(BigDecimal numvalue) {
		this.numvalue = numvalue;
	}

	@Column(name = "ORGID", length = 8)
	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@Column(name = "ROTATING", precision = 22, scale = 0)
	public BigDecimal getRotating() {
		return this.rotating;
	}

	public void setRotating(BigDecimal rotating) {
		this.rotating = rotating;
	}

	@Column(name = "SECTION", length = 10)
	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Column(name = "EAUDITUSERNAME", nullable = false, length = 18)
	public String getEauditusername() {
		return this.eauditusername;
	}

	public void setEauditusername(String eauditusername) {
		this.eauditusername = eauditusername;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EAUDITTIMESTAMP", nullable = false, length = 7)
	public Date getEaudittimestamp() {
		return this.eaudittimestamp;
	}

	public void setEaudittimestamp(Date eaudittimestamp) {
		this.eaudittimestamp = eaudittimestamp;
	}

	@Column(name = "EAUDITTYPE", nullable = false, length = 1)
	public String getEaudittype() {
		return this.eaudittype;
	}

	public void setEaudittype(String eaudittype) {
		this.eaudittype = eaudittype;
	}

	@Column(name = "ESIGTRANSID", length = 40)
	public String getEsigtransid() {
		return this.esigtransid;
	}

	public void setEsigtransid(String esigtransid) {
		this.esigtransid = esigtransid;
	}

	@Column(name = "DUPLICATEITEMSPEC", length = 50)
	public String getDuplicateitemspec() {
		return this.duplicateitemspec;
	}

	public void setDuplicateitemspec(String duplicateitemspec) {
		this.duplicateitemspec = duplicateitemspec;
	}

	@Column(name = "STATUS", length = 15)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "DATATYPE")
	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	@Column(name = "ITEMREQUIREVALUE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getItemrequirevalue() {
		return this.itemrequirevalue;
	}

	public void setItemrequirevalue(BigDecimal itemrequirevalue) {
		this.itemrequirevalue = itemrequirevalue;
	}

}