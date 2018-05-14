package mro.base.entity;

// Generated 2013/12/2 �W�� 09:19:02 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InvbalancesInactiveRecode generated by hbm2java
 */
@Entity
@Table(name = "INVBALANCES_INACTIVE_RECODE") 
public class InvbalancesInactiveRecode implements java.io.Serializable {

	private BigDecimal id;
	private BigDecimal invbalancesid;
	private String itemnum;
	private String location;
	private String binnum;
	private Date lastupdateDate;
	private String lastupdateBy;
	private BigDecimal oldMinlevel;
	private BigDecimal newMinlevel;
	private String remark;
	private BigDecimal oldSstock;
	private BigDecimal newSstock;
	private BigDecimal oldOriavguseqty;
	private BigDecimal newOriavguseqty;
	private String lastrequestedby2;

	public InvbalancesInactiveRecode() {
	}

	public InvbalancesInactiveRecode(BigDecimal id, BigDecimal invbalancesid,
			String itemnum, String location, String lastupdateBy) {
		this.id = id;
		this.invbalancesid = invbalancesid;
		this.itemnum = itemnum;
		this.location = location;
		this.lastupdateBy = lastupdateBy;
	}

	public InvbalancesInactiveRecode(BigDecimal id, BigDecimal invbalancesid,
			String itemnum, String location, String binnum,
			Date lastupdateDate, String lastupdateBy, BigDecimal oldMinlevel,
			BigDecimal newMinlevel,String remark,BigDecimal oldSstock,BigDecimal newSstock,
			BigDecimal oldOriavguseqty,BigDecimal newOriavguseqty,String lastrequestedby2) {
		this.id = id;
		this.invbalancesid = invbalancesid;
		this.itemnum = itemnum;
		this.location = location;
		this.binnum = binnum;
		this.lastupdateDate = lastupdateDate;
		this.lastupdateBy = lastupdateBy;
		this.oldMinlevel = oldMinlevel;
		this.newMinlevel = newMinlevel;
		this.remark = remark;
		this.oldSstock = oldSstock;
		this.newSstock = newSstock;
		this.oldOriavguseqty = oldOriavguseqty;
		this.newOriavguseqty = newOriavguseqty;
		this.lastrequestedby2 = lastrequestedby2;
	}

	@Id
	@SequenceGenerator(name="ID_GENERATOR", sequenceName="INVBALANCES_INACTIVES_SEQ" ,allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_GENERATOR")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "INVBALANCESID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvbalancesid() {
		return this.invbalancesid;
	}

	public void setInvbalancesid(BigDecimal invbalancesid) {
		this.invbalancesid = invbalancesid;
	}

	@Column(name = "ITEMNUM", nullable = false, length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "LOCATION", nullable = false, length = 16)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "BINNUM", length = 30)
	public String getBinnum() {
		return this.binnum;
	}

	public void setBinnum(String binnum) {
		this.binnum = binnum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTUPDATE_DATE", length = 7)
	public Date getLastupdateDate() {
		return this.lastupdateDate;
	}

	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}

	@Column(name = "LASTUPDATE_BY", nullable = false, length = 16)
	public String getLastupdateBy() {
		return this.lastupdateBy;
	}

	public void setLastupdateBy(String lastupdateBy) {
		this.lastupdateBy = lastupdateBy;
	}

	@Column(name = "OLD_MINLEVEL", precision = 22, scale = 0)
	public BigDecimal getOldMinlevel() {
		return this.oldMinlevel;
	}

	public void setOldMinlevel(BigDecimal oldMinlevel) {
		this.oldMinlevel = oldMinlevel;
	}

	@Column(name = "NEW_MINLEVEL", precision = 22, scale = 0)
	public BigDecimal getNewMinlevel() {
		return this.newMinlevel;
	}

	public void setNewMinlevel(BigDecimal newMinlevel) {
		this.newMinlevel = newMinlevel;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "OLD_SSTOCK")
	public BigDecimal getOldSstock() {
		return oldSstock;
	}

	public void setOldSstock(BigDecimal oldSstock) {
		this.oldSstock = oldSstock;
	}
	@Column(name = "NEW_SSTOCK")
	public BigDecimal getNewSstock() {
		return newSstock;
	}

	public void setNewSstock(BigDecimal newSstock) {
		this.newSstock = newSstock;
	}
	@Column(name = "OLD_ORIAVGUSEQTY")
	public BigDecimal getOldOriavguseqty() {
		return oldOriavguseqty;
	}

	public void setOldOriavguseqty(BigDecimal oldOriavguseqty) {
		this.oldOriavguseqty = oldOriavguseqty;
	}
	@Column(name = "NEW_ORIAVGUSEQTY")
	public BigDecimal getNewOriavguseqty() {
		return newOriavguseqty;
	}

	public void setNewOriavguseqty(BigDecimal newOriavguseqty) {
		this.newOriavguseqty = newOriavguseqty;
	}
	@Column(name = "LASTREQUESTEDBY2")
	public String getLastrequestedby2() {
		return lastrequestedby2;
	}

	public void setLastrequestedby2(String lastrequestedby2) {
		this.lastrequestedby2 = lastrequestedby2;
	}

}