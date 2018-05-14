package mro.base.entity;

// Generated 2015/1/28 �U�� 02:03:53 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * PrlineAssigned generated by hbm2java
 */
@Entity
@Table(name = "PRLINE_ASSIGNED")
public class PrlineAssigned implements java.io.Serializable {

	private String assignedId;
	private String prnum;
	private Pr pr;
	private Prline prline;
	private String itemnum;
	private String location;
	private BigDecimal minlevel;
	private String deptCode;
	private BigDecimal rate;
	private BigDecimal rateQty;
	private BigDecimal mcMinPackageQuantity;
	private String lastrequestedby2;
	private Date createDate;
	private BigDecimal demandQty;
	private BigDecimal reorderQty;
	private String cmommcontrol;
	private BigDecimal prqty;
	private BigDecimal poqty;

	public PrlineAssigned() {
	}
	public PrlineAssigned(Pr pr,Prline prline) {
		this.prnum = pr.getPrnum();
		this.pr = pr;
		this.prline = prline;
	}
	public PrlineAssigned(String assignedId, String prnum, Pr pr,
			Prline prline) {
		this.assignedId = assignedId;
		this.prnum = prnum;
		this.pr = pr;
		this.prline = prline;
	}

	public PrlineAssigned(String assignedId, String prnum, Pr pr,
			Prline prline, String itemnum, String location,
			BigDecimal minlevel, String deptCode, BigDecimal rate,
			BigDecimal rateQty, BigDecimal mcMinPackageQuantity,
			String lastrequestedby2, Date createDate,BigDecimal demandQty,
			BigDecimal reorderQty,String cmommcontrol,
			BigDecimal prqty,BigDecimal poqty) {
		this.assignedId = assignedId;
		this.prnum = prnum;
		this.pr = pr;
		this.prline = prline;
		this.itemnum = itemnum;
		this.location = location;
		this.minlevel = minlevel;
		this.deptCode = deptCode;
		this.rate = rate;
		this.rateQty = rateQty;
		this.mcMinPackageQuantity = mcMinPackageQuantity;
		this.lastrequestedby2 = lastrequestedby2;
		this.createDate = createDate;
		this.demandQty = demandQty;
		this.reorderQty = reorderQty;
		this.cmommcontrol = cmommcontrol;
		this.prqty = prqty;
		this.poqty = poqty;
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid2")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ASSIGNED_ID", unique = true, nullable = false, length = 36)
	public String getAssignedId() {
		return this.assignedId;
	}

	public void setAssignedId(String assignedId) {
		this.assignedId = assignedId;
	}

	@Column(name = "PRNUM", nullable = false, length = 80)
	public String getPrnum() {
		return this.prnum;
	}

	public void setPrnum(String prnum) {
		this.prnum = prnum;
	}

	@Column(name = "ITEMNUM", length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "LOCATION", length = 16)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "MINLEVEL", precision = 22, scale = 0)
	public BigDecimal getMinlevel() {
		return this.minlevel;
	}

	public void setMinlevel(BigDecimal minlevel) {
		this.minlevel = minlevel;
	}

	@Column(name = "DEPT_CODE", length = 12)
	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "RATE", precision = 22, scale = 0)
	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "RATE_QTY", precision = 22, scale = 0)
	public BigDecimal getRateQty() {
		return this.rateQty;
	}

	public void setRateQty(BigDecimal rateQty) {
		this.rateQty = rateQty;
	}

	@Column(name = "MC_MIN_PACKAGE_QUANTITY", precision = 22, scale = 0)
	public BigDecimal getMcMinPackageQuantity() {
		return this.mcMinPackageQuantity;
	}

	public void setMcMinPackageQuantity(BigDecimal mcMinPackageQuantity) {
		this.mcMinPackageQuantity = mcMinPackageQuantity;
	}

	@Column(name = "LASTREQUESTEDBY2", length = 30)
	public String getLastrequestedby2() {
		return this.lastrequestedby2;
	}

	public void setLastrequestedby2(String lastrequestedby2) {
		this.lastrequestedby2 = lastrequestedby2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	@Column(name = "REORDER_QTY")
	public BigDecimal getReorderQty() {
		return reorderQty;
	}

	public void setReorderQty(BigDecimal reorderQty) {
		this.reorderQty = reorderQty;
	}
	
	@Column(name = "DEMAND_QTY")
	public BigDecimal getDemandQty() {
		return demandQty;
	}

	public void setDemandQty(BigDecimal demandQty) {
		this.demandQty = demandQty;
	}
	@Column(name = "CMOMMCONTROL")
	public String getCmommcontrol() {
		return cmommcontrol;
	}

	public void setCmommcontrol(String cmommcontrol) {
		this.cmommcontrol = cmommcontrol;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRID")
	public Pr getPr() {
		return pr;
	}

	public void setPr(Pr pr) {
		this.pr = pr;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRLINEID")
	public Prline getPrline() {
		return prline;
	}

	public void setPrline(Prline prline) {
		this.prline = prline;
	}
	
	@Column(name = "PRQTY")
	public BigDecimal getPrqty() {
		return prqty;
	}
	public void setPrqty(BigDecimal prqty) {
		this.prqty = prqty;
	}
	
	@Column(name = "POQTY")
	public BigDecimal getPoqty() {
		return poqty;
	}
	public void setPoqty(BigDecimal poqty) {
		this.poqty = poqty;
	}


}