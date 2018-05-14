package mro.base.entity;

// Generated 2014/6/6 �W�� 11:16:59 by Hibernate Tools 3.4.0.CR1

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
 * ItemDisableLog generated by hbm2java
 */
@Entity
@Table(name = "ITEM_DISABLE_LOG")
public class ItemDisableLog implements java.io.Serializable {

	private BigDecimal itemDisableLogId;
	private BigDecimal itemid;
	private String itemnum;
	private String remark;
	private String createBy;
	private Date createDate;
	private String disableFlag;
	
	public ItemDisableLog() {
	}

	public ItemDisableLog(BigDecimal itemDisableLogId) {
		this.itemDisableLogId = itemDisableLogId;
	}

	public ItemDisableLog(BigDecimal itemDisableLogId, BigDecimal itemid,
			String itemnum, String remark, String createBy, Date createDate,String disableFlag) {
		this.itemDisableLogId = itemDisableLogId;
		this.itemid = itemid;
		this.itemnum = itemnum;
		this.remark = remark;
		this.createBy = createBy;
		this.createDate = createDate;
		this.disableFlag = disableFlag;
	}

	@Id
	@SequenceGenerator(name="ITEM_DISABLE_LOG_ID_GENERATOR", sequenceName="ITEM_DISABLE_LOG_SEQ" ,allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_DISABLE_LOG_ID_GENERATOR")
	@Column(name = "ITEM_DISABLE_LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getItemDisableLogId() {
		return this.itemDisableLogId;
	}

	public void setItemDisableLogId(BigDecimal itemDisableLogId) {
		this.itemDisableLogId = itemDisableLogId;
	}

	@Column(name = "ITEMID", precision = 22, scale = 0)
	public BigDecimal getItemid() {
		return this.itemid;
	}

	public void setItemid(BigDecimal itemid) {
		this.itemid = itemid;
	}

	@Column(name = "ITEMNUM", length = 30)
	public String getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	@Column(name = "REMARK", length = 300)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_BY", length = 30)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "DISABLE_FLAG", length = 30)
	public String getDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}

}