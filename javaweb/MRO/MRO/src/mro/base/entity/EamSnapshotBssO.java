package mro.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the EAM_SNAPSHOT_BSS_O database table.
 * 
 */
@Entity
@Table(name="EAM_SNAPSHOT_BSS_O")
public class EamSnapshotBssO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EAM_SNAPSHOT_BSS_O_ROWSTAMP_GENERATOR", sequenceName="EAM_SNAPSHOT_BSS_OSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EAM_SNAPSHOT_BSS_O_ROWSTAMP_GENERATOR")
	private String rowstamp;

	private String bssloc;

	private String dept;

	private String itemnum;

	private String locationdesc;

	private BigDecimal qty;

	private BigDecimal qty1;

	private BigDecimal qty2;

	private BigDecimal qty3;

	private BigDecimal qty4;

	private BigDecimal qty5;

	private String shop;

	private String siteid;

	private String sublocation;

    @Temporal( TemporalType.TIMESTAMP)
	private Date transferdate;

    public EamSnapshotBssO() {
    }

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getBssloc() {
		return this.bssloc;
	}

	public void setBssloc(String bssloc) {
		this.bssloc = bssloc;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getLocationdesc() {
		return this.locationdesc;
	}

	public void setLocationdesc(String locationdesc) {
		this.locationdesc = locationdesc;
	}
	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getQty1() {
		return this.qty1;
	}

	public void setQty1(BigDecimal qty1) {
		this.qty1 = qty1;
	}

	public BigDecimal getQty2() {
		return this.qty2;
	}

	public void setQty2(BigDecimal qty2) {
		this.qty2 = qty2;
	}

	public BigDecimal getQty3() {
		return this.qty3;
	}

	public void setQty3(BigDecimal qty3) {
		this.qty3 = qty3;
	}

	public BigDecimal getQty4() {
		return this.qty4;
	}

	public void setQty4(BigDecimal qty4) {
		this.qty4 = qty4;
	}

	public BigDecimal getQty5() {
		return this.qty5;
	}

	public void setQty5(BigDecimal qty5) {
		this.qty5 = qty5;
	}

	public String getShop() {
		return this.shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSublocation() {
		return this.sublocation;
	}

	public void setSublocation(String sublocation) {
		this.sublocation = sublocation;
	}

	public Date getTransferdate() {
		return this.transferdate;
	}

	public void setTransferdate(Date transferdate) {
		this.transferdate = transferdate;
	}

}