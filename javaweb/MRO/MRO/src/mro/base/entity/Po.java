package mro.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PO database table.
 * 
 */
@Entity
public class Po implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PO_POID_GENERATOR", sequenceName="POSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PO_POID_GENERATOR")
	private long poid;

	private String billto;

	private String billtoattn;

	private BigDecimal buyahead;

	private String buyercompany;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private String contact;

	private BigDecimal contractrefid;

	private String contractrefnum;

	private BigDecimal contractrefrev;

	private BigDecimal contreleaseseq;

	private String currencycode;

	private String customernum;

	private String description;

    @Temporal( TemporalType.TIMESTAMP)
	private Date ecomstatusdate;

    @Temporal( TemporalType.TIMESTAMP)
	private Date enddate;

	@Column(name="ERP_PO")
	private String erpPo;

	private String erpremark;

    @Temporal( TemporalType.TIMESTAMP)
	private Date exchangedate;

	private BigDecimal exchangerate;

	private BigDecimal exchangerate2;

	private String externalrefid;

	private String fob;

    @Temporal( TemporalType.TIMESTAMP)
	private Date followupdate;

	private String freightterms;

	private BigDecimal hasld;

	private BigDecimal historyflag;

	private BigDecimal inclusive1;

	private BigDecimal inclusive2;

	private BigDecimal inclusive3;

	private BigDecimal inclusive4;

	private BigDecimal inclusive5;

	private BigDecimal inspectionrequired;

	private BigDecimal internal;

	private String langcode;

	private String mnetsent;

    @Temporal( TemporalType.TIMESTAMP)
	private Date orderdate;

	private String orgid;

	private String originalponum;

	private String ownersysid;

	private String paymentterms;

	private BigDecimal payonreceipt;

	private String po1;

	private BigDecimal po10;

	private String po2;

	private String po3;

	private String po4;

	private String po5;

	private BigDecimal po6;

    @Temporal( TemporalType.TIMESTAMP)
	private Date po7;

	private BigDecimal po8;

	private BigDecimal po9;

	private String ponum;

	private String potype;

	private BigDecimal priority;

	private String purchaseagent;

	private String receipts;

    @Temporal( TemporalType.TIMESTAMP)
	private Date requireddate;

	private String rowstamp;

	private String sendersysid;

	private String shipto;

	private String shiptoattn;

	private String shipvia;

	private String siteid;

	private String sourcesysid;

    @Temporal( TemporalType.TIMESTAMP)
	private Date startdate;

	private String status;

    @Temporal( TemporalType.TIMESTAMP)
	private Date statusdate;

	private String storeloc;

	private String storelocsiteid;

	private BigDecimal totalcost;

	private BigDecimal totaltax1;

	private BigDecimal totaltax2;

	private BigDecimal totaltax3;

	private BigDecimal totaltax4;

	private BigDecimal totaltax5;

    @Temporal( TemporalType.TIMESTAMP)
	private Date vendeliverydate;

	private String vendor;

    public Po() {
    }

	public long getPoid() {
		return this.poid;
	}

	public void setPoid(long poid) {
		this.poid = poid;
	}

	public String getBillto() {
		return this.billto;
	}

	public void setBillto(String billto) {
		this.billto = billto;
	}

	public String getBilltoattn() {
		return this.billtoattn;
	}

	public void setBilltoattn(String billtoattn) {
		this.billtoattn = billtoattn;
	}

	public BigDecimal getBuyahead() {
		return this.buyahead;
	}

	public void setBuyahead(BigDecimal buyahead) {
		this.buyahead = buyahead;
	}

	public String getBuyercompany() {
		return this.buyercompany;
	}

	public void setBuyercompany(String buyercompany) {
		this.buyercompany = buyercompany;
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

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public BigDecimal getContractrefid() {
		return this.contractrefid;
	}

	public void setContractrefid(BigDecimal contractrefid) {
		this.contractrefid = contractrefid;
	}

	public String getContractrefnum() {
		return this.contractrefnum;
	}

	public void setContractrefnum(String contractrefnum) {
		this.contractrefnum = contractrefnum;
	}

	public BigDecimal getContractrefrev() {
		return this.contractrefrev;
	}

	public void setContractrefrev(BigDecimal contractrefrev) {
		this.contractrefrev = contractrefrev;
	}

	public BigDecimal getContreleaseseq() {
		return this.contreleaseseq;
	}

	public void setContreleaseseq(BigDecimal contreleaseseq) {
		this.contreleaseseq = contreleaseseq;
	}

	public String getCurrencycode() {
		return this.currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getCustomernum() {
		return this.customernum;
	}

	public void setCustomernum(String customernum) {
		this.customernum = customernum;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEcomstatusdate() {
		return this.ecomstatusdate;
	}

	public void setEcomstatusdate(Date ecomstatusdate) {
		this.ecomstatusdate = ecomstatusdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getErpPo() {
		return this.erpPo;
	}

	public void setErpPo(String erpPo) {
		this.erpPo = erpPo;
	}

	public String getErpremark() {
		return this.erpremark;
	}

	public void setErpremark(String erpremark) {
		this.erpremark = erpremark;
	}

	public Date getExchangedate() {
		return this.exchangedate;
	}

	public void setExchangedate(Date exchangedate) {
		this.exchangedate = exchangedate;
	}

	public BigDecimal getExchangerate() {
		return this.exchangerate;
	}

	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}

	public BigDecimal getExchangerate2() {
		return this.exchangerate2;
	}

	public void setExchangerate2(BigDecimal exchangerate2) {
		this.exchangerate2 = exchangerate2;
	}

	public String getExternalrefid() {
		return this.externalrefid;
	}

	public void setExternalrefid(String externalrefid) {
		this.externalrefid = externalrefid;
	}

	public String getFob() {
		return this.fob;
	}

	public void setFob(String fob) {
		this.fob = fob;
	}

	public Date getFollowupdate() {
		return this.followupdate;
	}

	public void setFollowupdate(Date followupdate) {
		this.followupdate = followupdate;
	}

	public String getFreightterms() {
		return this.freightterms;
	}

	public void setFreightterms(String freightterms) {
		this.freightterms = freightterms;
	}

	public BigDecimal getHasld() {
		return this.hasld;
	}

	public void setHasld(BigDecimal hasld) {
		this.hasld = hasld;
	}

	public BigDecimal getHistoryflag() {
		return this.historyflag;
	}

	public void setHistoryflag(BigDecimal historyflag) {
		this.historyflag = historyflag;
	}

	public BigDecimal getInclusive1() {
		return this.inclusive1;
	}

	public void setInclusive1(BigDecimal inclusive1) {
		this.inclusive1 = inclusive1;
	}

	public BigDecimal getInclusive2() {
		return this.inclusive2;
	}

	public void setInclusive2(BigDecimal inclusive2) {
		this.inclusive2 = inclusive2;
	}

	public BigDecimal getInclusive3() {
		return this.inclusive3;
	}

	public void setInclusive3(BigDecimal inclusive3) {
		this.inclusive3 = inclusive3;
	}

	public BigDecimal getInclusive4() {
		return this.inclusive4;
	}

	public void setInclusive4(BigDecimal inclusive4) {
		this.inclusive4 = inclusive4;
	}

	public BigDecimal getInclusive5() {
		return this.inclusive5;
	}

	public void setInclusive5(BigDecimal inclusive5) {
		this.inclusive5 = inclusive5;
	}

	public BigDecimal getInspectionrequired() {
		return this.inspectionrequired;
	}

	public void setInspectionrequired(BigDecimal inspectionrequired) {
		this.inspectionrequired = inspectionrequired;
	}

	public BigDecimal getInternal() {
		return this.internal;
	}

	public void setInternal(BigDecimal internal) {
		this.internal = internal;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getMnetsent() {
		return this.mnetsent;
	}

	public void setMnetsent(String mnetsent) {
		this.mnetsent = mnetsent;
	}

	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOriginalponum() {
		return this.originalponum;
	}

	public void setOriginalponum(String originalponum) {
		this.originalponum = originalponum;
	}

	public String getOwnersysid() {
		return this.ownersysid;
	}

	public void setOwnersysid(String ownersysid) {
		this.ownersysid = ownersysid;
	}

	public String getPaymentterms() {
		return this.paymentterms;
	}

	public void setPaymentterms(String paymentterms) {
		this.paymentterms = paymentterms;
	}

	public BigDecimal getPayonreceipt() {
		return this.payonreceipt;
	}

	public void setPayonreceipt(BigDecimal payonreceipt) {
		this.payonreceipt = payonreceipt;
	}

	public String getPo1() {
		return this.po1;
	}

	public void setPo1(String po1) {
		this.po1 = po1;
	}

	public BigDecimal getPo10() {
		return this.po10;
	}

	public void setPo10(BigDecimal po10) {
		this.po10 = po10;
	}

	public String getPo2() {
		return this.po2;
	}

	public void setPo2(String po2) {
		this.po2 = po2;
	}

	public String getPo3() {
		return this.po3;
	}

	public void setPo3(String po3) {
		this.po3 = po3;
	}

	public String getPo4() {
		return this.po4;
	}

	public void setPo4(String po4) {
		this.po4 = po4;
	}

	public String getPo5() {
		return this.po5;
	}

	public void setPo5(String po5) {
		this.po5 = po5;
	}

	public BigDecimal getPo6() {
		return this.po6;
	}

	public void setPo6(BigDecimal po6) {
		this.po6 = po6;
	}

	public Date getPo7() {
		return this.po7;
	}

	public void setPo7(Date po7) {
		this.po7 = po7;
	}

	public BigDecimal getPo8() {
		return this.po8;
	}

	public void setPo8(BigDecimal po8) {
		this.po8 = po8;
	}

	public BigDecimal getPo9() {
		return this.po9;
	}

	public void setPo9(BigDecimal po9) {
		this.po9 = po9;
	}

	public String getPonum() {
		return this.ponum;
	}

	public void setPonum(String ponum) {
		this.ponum = ponum;
	}

	public String getPotype() {
		return this.potype;
	}

	public void setPotype(String potype) {
		this.potype = potype;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

	public String getPurchaseagent() {
		return this.purchaseagent;
	}

	public void setPurchaseagent(String purchaseagent) {
		this.purchaseagent = purchaseagent;
	}

	public String getReceipts() {
		return this.receipts;
	}

	public void setReceipts(String receipts) {
		this.receipts = receipts;
	}

	public Date getRequireddate() {
		return this.requireddate;
	}

	public void setRequireddate(Date requireddate) {
		this.requireddate = requireddate;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public String getSendersysid() {
		return this.sendersysid;
	}

	public void setSendersysid(String sendersysid) {
		this.sendersysid = sendersysid;
	}

	public String getShipto() {
		return this.shipto;
	}

	public void setShipto(String shipto) {
		this.shipto = shipto;
	}

	public String getShiptoattn() {
		return this.shiptoattn;
	}

	public void setShiptoattn(String shiptoattn) {
		this.shiptoattn = shiptoattn;
	}

	public String getShipvia() {
		return this.shipvia;
	}

	public void setShipvia(String shipvia) {
		this.shipvia = shipvia;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSourcesysid() {
		return this.sourcesysid;
	}

	public void setSourcesysid(String sourcesysid) {
		this.sourcesysid = sourcesysid;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
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

	public String getStoreloc() {
		return this.storeloc;
	}

	public void setStoreloc(String storeloc) {
		this.storeloc = storeloc;
	}

	public String getStorelocsiteid() {
		return this.storelocsiteid;
	}

	public void setStorelocsiteid(String storelocsiteid) {
		this.storelocsiteid = storelocsiteid;
	}

	public BigDecimal getTotalcost() {
		return this.totalcost;
	}

	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}

	public BigDecimal getTotaltax1() {
		return this.totaltax1;
	}

	public void setTotaltax1(BigDecimal totaltax1) {
		this.totaltax1 = totaltax1;
	}

	public BigDecimal getTotaltax2() {
		return this.totaltax2;
	}

	public void setTotaltax2(BigDecimal totaltax2) {
		this.totaltax2 = totaltax2;
	}

	public BigDecimal getTotaltax3() {
		return this.totaltax3;
	}

	public void setTotaltax3(BigDecimal totaltax3) {
		this.totaltax3 = totaltax3;
	}

	public BigDecimal getTotaltax4() {
		return this.totaltax4;
	}

	public void setTotaltax4(BigDecimal totaltax4) {
		this.totaltax4 = totaltax4;
	}

	public BigDecimal getTotaltax5() {
		return this.totaltax5;
	}

	public void setTotaltax5(BigDecimal totaltax5) {
		this.totaltax5 = totaltax5;
	}

	public Date getVendeliverydate() {
		return this.vendeliverydate;
	}

	public void setVendeliverydate(Date vendeliverydate) {
		this.vendeliverydate = vendeliverydate;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}