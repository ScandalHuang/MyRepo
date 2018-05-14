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
 * The persistent class for the WORKORDER database table.
 * 
 */
@Entity
public class Workorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="WORKORDER_WORKORDERID_GENERATOR", sequenceName="WORKORDERSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WORKORDER_WORKORDERID_GENERATOR")
	private long workorderid;

    @Temporal( TemporalType.TIMESTAMP)
	private Date achduedate;

	private BigDecimal achieve;

	private String achimprove;

    @Temporal( TemporalType.TIMESTAMP)
	private Date actfinish;

	private BigDecimal actlabcost;

	private double actlabhrs;

	private BigDecimal actmatcost;

	private BigDecimal actservcost;

    @Temporal( TemporalType.TIMESTAMP)
	private Date actstart;

	private BigDecimal acttoolcost;

	private BigDecimal assetlocpriority;

	private String assetnum;

	private String backoutplan;

	private BigDecimal calcpriority;

	private String calendar;

	private String changeby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date changedate;

	private BigDecimal chargestore;

	private BigDecimal check01;

	private BigDecimal check02;

	private BigDecimal check03;

	private BigDecimal check04;

	private BigDecimal check05;

	private BigDecimal check06;

	private BigDecimal check07;

	private BigDecimal check08;

	private BigDecimal check09;

	private BigDecimal check10;

	private String checknote01;

	private String checknote02;

	private String checknote03;

	private String checknote04;

	private String checknote05;

	private String checknote06;

	private String checknote07;

	private String checknote08;

	private String checknote09;

	private String checknote10;

	private String classstructureid;

	private String commodity;

	private String commoditygroup;

	private String contract;

	private String craft;

	private String createby;

	private String crewid;

	private String dccnum;

	private String dccver;

	private BigDecimal deleteflag;

	private String deptcode;

	private String description;

	private BigDecimal disabled;

	private BigDecimal divisor;

	private BigDecimal downtime;

	private String engdesc;

	private String engnum;

	private String ensupervisor;

	private String environment;

	private BigDecimal estatapprlabcost;

	private double estatapprlabhrs;

	private BigDecimal estatapprmatcost;

	private BigDecimal estatapprservcost;

	private BigDecimal estatapprtoolcost;

	private double estdur;

	private BigDecimal estlabcost;

	private double estlabhrs;

	private BigDecimal estmatcost;

	private BigDecimal estservcost;

	private BigDecimal esttoolcost;

	private String executer;

	private String externalrefid;

	private String facility;

    @Temporal( TemporalType.TIMESTAMP)
	private Date faildate;

	private String failreason;

	private String failurecode;

	private String fincntrlid;

	private String generatedforpo;

	private BigDecimal genforpolineid;

	private String glaccount;

	private BigDecimal haschildren;

	private BigDecimal hasfollowupwork;

	private BigDecimal hasld;

	private BigDecimal historyflag;

	private String inspector;

	private BigDecimal interruptible;

	private BigDecimal istask;

	private String jpnum;

	private String jpoint01;

	private String jpoint02;

	private String jpoint03;

	private String jpoint04;

	private String jpoint05;

	private String jpoint06;

	private String jpoint07;

	private String jpoint08;

	private String jpoint09;

	private String jpoint10;

	private String justifypriority;

	private String langcode;

	private String lead;

	private String location;

	private String mandesc;

	private String mannum;

	private String masupervisor;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate01;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate02;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate03;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate04;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate05;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate06;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate07;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate08;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate09;

    @Temporal( TemporalType.TIMESTAMP)
	private Date measuredate10;

	private BigDecimal measurementvalue;

	private BigDecimal measurementvalue01;

	private BigDecimal measurementvalue02;

	private BigDecimal measurementvalue03;

	private BigDecimal measurementvalue04;

	private BigDecimal measurementvalue05;

	private BigDecimal measurementvalue06;

	private BigDecimal measurementvalue07;

	private BigDecimal measurementvalue08;

	private BigDecimal measurementvalue09;

	private BigDecimal measurementvalue10;

	private BigDecimal measuremin01;

	private BigDecimal measuremin02;

	private BigDecimal measuremin03;

	private BigDecimal measuremin04;

	private BigDecimal measuremin05;

	private BigDecimal measuremin06;

	private BigDecimal measuremin07;

	private BigDecimal measuremin08;

	private BigDecimal measuremin09;

	private BigDecimal measuremin10;

	private BigDecimal measuresub01;

	private BigDecimal measuresub02;

	private BigDecimal measuresub03;

	private BigDecimal measuresub04;

	private BigDecimal measuresub05;

	private BigDecimal measuresub06;

	private BigDecimal measuresub07;

	private BigDecimal measuresub08;

	private BigDecimal measuresub09;

	private BigDecimal measuresub10;

	private String movetobinnum;

	private String movetoloc;

	private String movetoparent;

    @Temporal( TemporalType.TIMESTAMP)
	private Date newschcomp;

    @Temporal( TemporalType.TIMESTAMP)
	private Date newschstart;

	private String notachreason;

	private String observation;

	private String onbehalfof;

	private String orgid;

	private String origrecordclass;

	private String origrecordid;

	private String oriwonum;

	private BigDecimal outlabcost;

	private BigDecimal outmatcost;

	private BigDecimal outtoolcost;

	private String owner;

	private String ownergroup;

	private String ownersysid;

	private String parent;

	private String parent2;

	private BigDecimal parentchgsstatus;

	private BigDecimal performmoveto;

	private String persongroup;

	private String phone;

	private BigDecimal pmcounter;

	private String pmdelayreason;

    @Temporal( TemporalType.TIMESTAMP)
	private Date pmduedate;

    @Temporal( TemporalType.TIMESTAMP)
	private Date pmextdate;

	private String pmfailreason;

	private String pmimproveplan;

    @Temporal( TemporalType.TIMESTAMP)
	private Date pmnextduedate;

	private String pmnum;

	@Column(name="PMNUM_ORIGIN")
	private String pmnumOrigin;

	private double pmschedule;

	private BigDecimal pmschupdate;

	private BigDecimal pmschupdate2;

	private String pmsupervisor;

	private String point01;

	private String point02;

	private String point03;

	private String point04;

	private String point05;

	private String point06;

	private String point07;

	private String point08;

	private String point09;

	private String point10;

	private String pointnum;

	private String problemcode;

	private String reasonforchange;

	private double remdur;

    @Temporal( TemporalType.TIMESTAMP)
	private Date reportdate;

	private String reportedby;

    @Temporal( TemporalType.TIMESTAMP)
	private Date respondby;

	private String risk;

	private String rowstamp;

    @Temporal( TemporalType.TIMESTAMP)
	private Date schedfinish;

    @Temporal( TemporalType.TIMESTAMP)
	private Date schedstart;

	private BigDecimal schimport;

	private String schmonth;

	private String schyear;

	private String sendersysid;

	private String siteid;

	private String sourcesysid;

	private String status;

    @Temporal( TemporalType.TIMESTAMP)
	private Date statusdate;

	private BigDecimal success;

    @Temporal( TemporalType.TIMESTAMP)
	private Date sucduedate;

	private String sucimprove;

	private String supervisor;

    @Temporal( TemporalType.TIMESTAMP)
	private Date targcompdate;

    @Temporal( TemporalType.TIMESTAMP)
	private Date targstartdate;

	private BigDecimal taskid;

	private String vendor;

	private String verification;

	private String whomischangefor;

	private BigDecimal woacceptscharges;

	private String woclass;

	private String woeq1;

	private String woeq10;

	private String woeq11;

	private BigDecimal woeq12;

    @Temporal( TemporalType.TIMESTAMP)
	private Date woeq13;

	private BigDecimal woeq14;

	private String woeq2;

	private String woeq3;

	private String woeq4;

	private BigDecimal woeq5;

    @Temporal( TemporalType.TIMESTAMP)
	private Date woeq6;

	private BigDecimal woeq7;

	private String woeq8;

	private String woeq9;

	private String wogroup;

	private String wojo1;

	private String wojo2;

	private String wojo3;

	private BigDecimal wojo4;

	private String wojo5;

	private String wojo6;

	private String wojo7;

	private String wojo8;

	private String wojp1;

	private String wojp2;

	private String wojp3;

	private BigDecimal wojp4;

    @Temporal( TemporalType.TIMESTAMP)
	private Date wojp5;

	private String wol1;

	private String wol2;

	private BigDecimal wol3;

    @Temporal( TemporalType.TIMESTAMP)
	private Date wol4;

	private String wolablnk;

	private String wolo1;

	private BigDecimal wolo10;

	private String wolo2;

	private String wolo3;

	private String wolo4;

	private String wolo5;

	private BigDecimal wolo6;

    @Temporal( TemporalType.TIMESTAMP)
	private Date wolo7;

	private BigDecimal wolo8;

	private String wolo9;

	private String wonum;

	private BigDecimal wopriority;

	private String woprogress;

	private String worklocation;

	private String worktype;

	private String worts1;

	private String worts2;

	private String worts3;

    @Temporal( TemporalType.TIMESTAMP)
	private Date worts4;

	private BigDecimal worts5;

	private BigDecimal wosequence;

    public Workorder() {
    }

	public long getWorkorderid() {
		return this.workorderid;
	}

	public void setWorkorderid(long workorderid) {
		this.workorderid = workorderid;
	}

	public Date getAchduedate() {
		return this.achduedate;
	}

	public void setAchduedate(Date achduedate) {
		this.achduedate = achduedate;
	}

	public BigDecimal getAchieve() {
		return this.achieve;
	}

	public void setAchieve(BigDecimal achieve) {
		this.achieve = achieve;
	}

	public String getAchimprove() {
		return this.achimprove;
	}

	public void setAchimprove(String achimprove) {
		this.achimprove = achimprove;
	}

	public Date getActfinish() {
		return this.actfinish;
	}

	public void setActfinish(Date actfinish) {
		this.actfinish = actfinish;
	}

	public BigDecimal getActlabcost() {
		return this.actlabcost;
	}

	public void setActlabcost(BigDecimal actlabcost) {
		this.actlabcost = actlabcost;
	}

	public double getActlabhrs() {
		return this.actlabhrs;
	}

	public void setActlabhrs(double actlabhrs) {
		this.actlabhrs = actlabhrs;
	}

	public BigDecimal getActmatcost() {
		return this.actmatcost;
	}

	public void setActmatcost(BigDecimal actmatcost) {
		this.actmatcost = actmatcost;
	}

	public BigDecimal getActservcost() {
		return this.actservcost;
	}

	public void setActservcost(BigDecimal actservcost) {
		this.actservcost = actservcost;
	}

	public Date getActstart() {
		return this.actstart;
	}

	public void setActstart(Date actstart) {
		this.actstart = actstart;
	}

	public BigDecimal getActtoolcost() {
		return this.acttoolcost;
	}

	public void setActtoolcost(BigDecimal acttoolcost) {
		this.acttoolcost = acttoolcost;
	}

	public BigDecimal getAssetlocpriority() {
		return this.assetlocpriority;
	}

	public void setAssetlocpriority(BigDecimal assetlocpriority) {
		this.assetlocpriority = assetlocpriority;
	}

	public String getAssetnum() {
		return this.assetnum;
	}

	public void setAssetnum(String assetnum) {
		this.assetnum = assetnum;
	}

	public String getBackoutplan() {
		return this.backoutplan;
	}

	public void setBackoutplan(String backoutplan) {
		this.backoutplan = backoutplan;
	}

	public BigDecimal getCalcpriority() {
		return this.calcpriority;
	}

	public void setCalcpriority(BigDecimal calcpriority) {
		this.calcpriority = calcpriority;
	}

	public String getCalendar() {
		return this.calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
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

	public BigDecimal getChargestore() {
		return this.chargestore;
	}

	public void setChargestore(BigDecimal chargestore) {
		this.chargestore = chargestore;
	}

	public BigDecimal getCheck01() {
		return this.check01;
	}

	public void setCheck01(BigDecimal check01) {
		this.check01 = check01;
	}

	public BigDecimal getCheck02() {
		return this.check02;
	}

	public void setCheck02(BigDecimal check02) {
		this.check02 = check02;
	}

	public BigDecimal getCheck03() {
		return this.check03;
	}

	public void setCheck03(BigDecimal check03) {
		this.check03 = check03;
	}

	public BigDecimal getCheck04() {
		return this.check04;
	}

	public void setCheck04(BigDecimal check04) {
		this.check04 = check04;
	}

	public BigDecimal getCheck05() {
		return this.check05;
	}

	public void setCheck05(BigDecimal check05) {
		this.check05 = check05;
	}

	public BigDecimal getCheck06() {
		return this.check06;
	}

	public void setCheck06(BigDecimal check06) {
		this.check06 = check06;
	}

	public BigDecimal getCheck07() {
		return this.check07;
	}

	public void setCheck07(BigDecimal check07) {
		this.check07 = check07;
	}

	public BigDecimal getCheck08() {
		return this.check08;
	}

	public void setCheck08(BigDecimal check08) {
		this.check08 = check08;
	}

	public BigDecimal getCheck09() {
		return this.check09;
	}

	public void setCheck09(BigDecimal check09) {
		this.check09 = check09;
	}

	public BigDecimal getCheck10() {
		return this.check10;
	}

	public void setCheck10(BigDecimal check10) {
		this.check10 = check10;
	}

	public String getChecknote01() {
		return this.checknote01;
	}

	public void setChecknote01(String checknote01) {
		this.checknote01 = checknote01;
	}

	public String getChecknote02() {
		return this.checknote02;
	}

	public void setChecknote02(String checknote02) {
		this.checknote02 = checknote02;
	}

	public String getChecknote03() {
		return this.checknote03;
	}

	public void setChecknote03(String checknote03) {
		this.checknote03 = checknote03;
	}

	public String getChecknote04() {
		return this.checknote04;
	}

	public void setChecknote04(String checknote04) {
		this.checknote04 = checknote04;
	}

	public String getChecknote05() {
		return this.checknote05;
	}

	public void setChecknote05(String checknote05) {
		this.checknote05 = checknote05;
	}

	public String getChecknote06() {
		return this.checknote06;
	}

	public void setChecknote06(String checknote06) {
		this.checknote06 = checknote06;
	}

	public String getChecknote07() {
		return this.checknote07;
	}

	public void setChecknote07(String checknote07) {
		this.checknote07 = checknote07;
	}

	public String getChecknote08() {
		return this.checknote08;
	}

	public void setChecknote08(String checknote08) {
		this.checknote08 = checknote08;
	}

	public String getChecknote09() {
		return this.checknote09;
	}

	public void setChecknote09(String checknote09) {
		this.checknote09 = checknote09;
	}

	public String getChecknote10() {
		return this.checknote10;
	}

	public void setChecknote10(String checknote10) {
		this.checknote10 = checknote10;
	}

	public String getClassstructureid() {
		return this.classstructureid;
	}

	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}

	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getCommoditygroup() {
		return this.commoditygroup;
	}

	public void setCommoditygroup(String commoditygroup) {
		this.commoditygroup = commoditygroup;
	}

	public String getContract() {
		return this.contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getCraft() {
		return this.craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public String getCreateby() {
		return this.createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getCrewid() {
		return this.crewid;
	}

	public void setCrewid(String crewid) {
		this.crewid = crewid;
	}

	public String getDccnum() {
		return this.dccnum;
	}

	public void setDccnum(String dccnum) {
		this.dccnum = dccnum;
	}

	public String getDccver() {
		return this.dccver;
	}

	public void setDccver(String dccver) {
		this.dccver = dccver;
	}

	public BigDecimal getDeleteflag() {
		return this.deleteflag;
	}

	public void setDeleteflag(BigDecimal deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getDeptcode() {
		return this.deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDisabled() {
		return this.disabled;
	}

	public void setDisabled(BigDecimal disabled) {
		this.disabled = disabled;
	}

	public BigDecimal getDivisor() {
		return this.divisor;
	}

	public void setDivisor(BigDecimal divisor) {
		this.divisor = divisor;
	}

	public BigDecimal getDowntime() {
		return this.downtime;
	}

	public void setDowntime(BigDecimal downtime) {
		this.downtime = downtime;
	}

	public String getEngdesc() {
		return this.engdesc;
	}

	public void setEngdesc(String engdesc) {
		this.engdesc = engdesc;
	}

	public String getEngnum() {
		return this.engnum;
	}

	public void setEngnum(String engnum) {
		this.engnum = engnum;
	}

	public String getEnsupervisor() {
		return this.ensupervisor;
	}

	public void setEnsupervisor(String ensupervisor) {
		this.ensupervisor = ensupervisor;
	}

	public String getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public BigDecimal getEstatapprlabcost() {
		return this.estatapprlabcost;
	}

	public void setEstatapprlabcost(BigDecimal estatapprlabcost) {
		this.estatapprlabcost = estatapprlabcost;
	}

	public double getEstatapprlabhrs() {
		return this.estatapprlabhrs;
	}

	public void setEstatapprlabhrs(double estatapprlabhrs) {
		this.estatapprlabhrs = estatapprlabhrs;
	}

	public BigDecimal getEstatapprmatcost() {
		return this.estatapprmatcost;
	}

	public void setEstatapprmatcost(BigDecimal estatapprmatcost) {
		this.estatapprmatcost = estatapprmatcost;
	}

	public BigDecimal getEstatapprservcost() {
		return this.estatapprservcost;
	}

	public void setEstatapprservcost(BigDecimal estatapprservcost) {
		this.estatapprservcost = estatapprservcost;
	}

	public BigDecimal getEstatapprtoolcost() {
		return this.estatapprtoolcost;
	}

	public void setEstatapprtoolcost(BigDecimal estatapprtoolcost) {
		this.estatapprtoolcost = estatapprtoolcost;
	}

	public double getEstdur() {
		return this.estdur;
	}

	public void setEstdur(double estdur) {
		this.estdur = estdur;
	}

	public BigDecimal getEstlabcost() {
		return this.estlabcost;
	}

	public void setEstlabcost(BigDecimal estlabcost) {
		this.estlabcost = estlabcost;
	}

	public double getEstlabhrs() {
		return this.estlabhrs;
	}

	public void setEstlabhrs(double estlabhrs) {
		this.estlabhrs = estlabhrs;
	}

	public BigDecimal getEstmatcost() {
		return this.estmatcost;
	}

	public void setEstmatcost(BigDecimal estmatcost) {
		this.estmatcost = estmatcost;
	}

	public BigDecimal getEstservcost() {
		return this.estservcost;
	}

	public void setEstservcost(BigDecimal estservcost) {
		this.estservcost = estservcost;
	}

	public BigDecimal getEsttoolcost() {
		return this.esttoolcost;
	}

	public void setEsttoolcost(BigDecimal esttoolcost) {
		this.esttoolcost = esttoolcost;
	}

	public String getExecuter() {
		return this.executer;
	}

	public void setExecuter(String executer) {
		this.executer = executer;
	}

	public String getExternalrefid() {
		return this.externalrefid;
	}

	public void setExternalrefid(String externalrefid) {
		this.externalrefid = externalrefid;
	}

	public String getFacility() {
		return this.facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public Date getFaildate() {
		return this.faildate;
	}

	public void setFaildate(Date faildate) {
		this.faildate = faildate;
	}

	public String getFailreason() {
		return this.failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}

	public String getFailurecode() {
		return this.failurecode;
	}

	public void setFailurecode(String failurecode) {
		this.failurecode = failurecode;
	}

	public String getFincntrlid() {
		return this.fincntrlid;
	}

	public void setFincntrlid(String fincntrlid) {
		this.fincntrlid = fincntrlid;
	}

	public String getGeneratedforpo() {
		return this.generatedforpo;
	}

	public void setGeneratedforpo(String generatedforpo) {
		this.generatedforpo = generatedforpo;
	}

	public BigDecimal getGenforpolineid() {
		return this.genforpolineid;
	}

	public void setGenforpolineid(BigDecimal genforpolineid) {
		this.genforpolineid = genforpolineid;
	}

	public String getGlaccount() {
		return this.glaccount;
	}

	public void setGlaccount(String glaccount) {
		this.glaccount = glaccount;
	}

	public BigDecimal getHaschildren() {
		return this.haschildren;
	}

	public void setHaschildren(BigDecimal haschildren) {
		this.haschildren = haschildren;
	}

	public BigDecimal getHasfollowupwork() {
		return this.hasfollowupwork;
	}

	public void setHasfollowupwork(BigDecimal hasfollowupwork) {
		this.hasfollowupwork = hasfollowupwork;
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

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public BigDecimal getInterruptible() {
		return this.interruptible;
	}

	public void setInterruptible(BigDecimal interruptible) {
		this.interruptible = interruptible;
	}

	public BigDecimal getIstask() {
		return this.istask;
	}

	public void setIstask(BigDecimal istask) {
		this.istask = istask;
	}

	public String getJpnum() {
		return this.jpnum;
	}

	public void setJpnum(String jpnum) {
		this.jpnum = jpnum;
	}

	public String getJpoint01() {
		return this.jpoint01;
	}

	public void setJpoint01(String jpoint01) {
		this.jpoint01 = jpoint01;
	}

	public String getJpoint02() {
		return this.jpoint02;
	}

	public void setJpoint02(String jpoint02) {
		this.jpoint02 = jpoint02;
	}

	public String getJpoint03() {
		return this.jpoint03;
	}

	public void setJpoint03(String jpoint03) {
		this.jpoint03 = jpoint03;
	}

	public String getJpoint04() {
		return this.jpoint04;
	}

	public void setJpoint04(String jpoint04) {
		this.jpoint04 = jpoint04;
	}

	public String getJpoint05() {
		return this.jpoint05;
	}

	public void setJpoint05(String jpoint05) {
		this.jpoint05 = jpoint05;
	}

	public String getJpoint06() {
		return this.jpoint06;
	}

	public void setJpoint06(String jpoint06) {
		this.jpoint06 = jpoint06;
	}

	public String getJpoint07() {
		return this.jpoint07;
	}

	public void setJpoint07(String jpoint07) {
		this.jpoint07 = jpoint07;
	}

	public String getJpoint08() {
		return this.jpoint08;
	}

	public void setJpoint08(String jpoint08) {
		this.jpoint08 = jpoint08;
	}

	public String getJpoint09() {
		return this.jpoint09;
	}

	public void setJpoint09(String jpoint09) {
		this.jpoint09 = jpoint09;
	}

	public String getJpoint10() {
		return this.jpoint10;
	}

	public void setJpoint10(String jpoint10) {
		this.jpoint10 = jpoint10;
	}

	public String getJustifypriority() {
		return this.justifypriority;
	}

	public void setJustifypriority(String justifypriority) {
		this.justifypriority = justifypriority;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getLead() {
		return this.lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMandesc() {
		return this.mandesc;
	}

	public void setMandesc(String mandesc) {
		this.mandesc = mandesc;
	}

	public String getMannum() {
		return this.mannum;
	}

	public void setMannum(String mannum) {
		this.mannum = mannum;
	}

	public String getMasupervisor() {
		return this.masupervisor;
	}

	public void setMasupervisor(String masupervisor) {
		this.masupervisor = masupervisor;
	}

	public Date getMeasuredate() {
		return this.measuredate;
	}

	public void setMeasuredate(Date measuredate) {
		this.measuredate = measuredate;
	}

	public Date getMeasuredate01() {
		return this.measuredate01;
	}

	public void setMeasuredate01(Date measuredate01) {
		this.measuredate01 = measuredate01;
	}

	public Date getMeasuredate02() {
		return this.measuredate02;
	}

	public void setMeasuredate02(Date measuredate02) {
		this.measuredate02 = measuredate02;
	}

	public Date getMeasuredate03() {
		return this.measuredate03;
	}

	public void setMeasuredate03(Date measuredate03) {
		this.measuredate03 = measuredate03;
	}

	public Date getMeasuredate04() {
		return this.measuredate04;
	}

	public void setMeasuredate04(Date measuredate04) {
		this.measuredate04 = measuredate04;
	}

	public Date getMeasuredate05() {
		return this.measuredate05;
	}

	public void setMeasuredate05(Date measuredate05) {
		this.measuredate05 = measuredate05;
	}

	public Date getMeasuredate06() {
		return this.measuredate06;
	}

	public void setMeasuredate06(Date measuredate06) {
		this.measuredate06 = measuredate06;
	}

	public Date getMeasuredate07() {
		return this.measuredate07;
	}

	public void setMeasuredate07(Date measuredate07) {
		this.measuredate07 = measuredate07;
	}

	public Date getMeasuredate08() {
		return this.measuredate08;
	}

	public void setMeasuredate08(Date measuredate08) {
		this.measuredate08 = measuredate08;
	}

	public Date getMeasuredate09() {
		return this.measuredate09;
	}

	public void setMeasuredate09(Date measuredate09) {
		this.measuredate09 = measuredate09;
	}

	public Date getMeasuredate10() {
		return this.measuredate10;
	}

	public void setMeasuredate10(Date measuredate10) {
		this.measuredate10 = measuredate10;
	}

	public BigDecimal getMeasurementvalue() {
		return this.measurementvalue;
	}

	public void setMeasurementvalue(BigDecimal measurementvalue) {
		this.measurementvalue = measurementvalue;
	}

	public BigDecimal getMeasurementvalue01() {
		return this.measurementvalue01;
	}

	public void setMeasurementvalue01(BigDecimal measurementvalue01) {
		this.measurementvalue01 = measurementvalue01;
	}

	public BigDecimal getMeasurementvalue02() {
		return this.measurementvalue02;
	}

	public void setMeasurementvalue02(BigDecimal measurementvalue02) {
		this.measurementvalue02 = measurementvalue02;
	}

	public BigDecimal getMeasurementvalue03() {
		return this.measurementvalue03;
	}

	public void setMeasurementvalue03(BigDecimal measurementvalue03) {
		this.measurementvalue03 = measurementvalue03;
	}

	public BigDecimal getMeasurementvalue04() {
		return this.measurementvalue04;
	}

	public void setMeasurementvalue04(BigDecimal measurementvalue04) {
		this.measurementvalue04 = measurementvalue04;
	}

	public BigDecimal getMeasurementvalue05() {
		return this.measurementvalue05;
	}

	public void setMeasurementvalue05(BigDecimal measurementvalue05) {
		this.measurementvalue05 = measurementvalue05;
	}

	public BigDecimal getMeasurementvalue06() {
		return this.measurementvalue06;
	}

	public void setMeasurementvalue06(BigDecimal measurementvalue06) {
		this.measurementvalue06 = measurementvalue06;
	}

	public BigDecimal getMeasurementvalue07() {
		return this.measurementvalue07;
	}

	public void setMeasurementvalue07(BigDecimal measurementvalue07) {
		this.measurementvalue07 = measurementvalue07;
	}

	public BigDecimal getMeasurementvalue08() {
		return this.measurementvalue08;
	}

	public void setMeasurementvalue08(BigDecimal measurementvalue08) {
		this.measurementvalue08 = measurementvalue08;
	}

	public BigDecimal getMeasurementvalue09() {
		return this.measurementvalue09;
	}

	public void setMeasurementvalue09(BigDecimal measurementvalue09) {
		this.measurementvalue09 = measurementvalue09;
	}

	public BigDecimal getMeasurementvalue10() {
		return this.measurementvalue10;
	}

	public void setMeasurementvalue10(BigDecimal measurementvalue10) {
		this.measurementvalue10 = measurementvalue10;
	}

	public BigDecimal getMeasuremin01() {
		return this.measuremin01;
	}

	public void setMeasuremin01(BigDecimal measuremin01) {
		this.measuremin01 = measuremin01;
	}

	public BigDecimal getMeasuremin02() {
		return this.measuremin02;
	}

	public void setMeasuremin02(BigDecimal measuremin02) {
		this.measuremin02 = measuremin02;
	}

	public BigDecimal getMeasuremin03() {
		return this.measuremin03;
	}

	public void setMeasuremin03(BigDecimal measuremin03) {
		this.measuremin03 = measuremin03;
	}

	public BigDecimal getMeasuremin04() {
		return this.measuremin04;
	}

	public void setMeasuremin04(BigDecimal measuremin04) {
		this.measuremin04 = measuremin04;
	}

	public BigDecimal getMeasuremin05() {
		return this.measuremin05;
	}

	public void setMeasuremin05(BigDecimal measuremin05) {
		this.measuremin05 = measuremin05;
	}

	public BigDecimal getMeasuremin06() {
		return this.measuremin06;
	}

	public void setMeasuremin06(BigDecimal measuremin06) {
		this.measuremin06 = measuremin06;
	}

	public BigDecimal getMeasuremin07() {
		return this.measuremin07;
	}

	public void setMeasuremin07(BigDecimal measuremin07) {
		this.measuremin07 = measuremin07;
	}

	public BigDecimal getMeasuremin08() {
		return this.measuremin08;
	}

	public void setMeasuremin08(BigDecimal measuremin08) {
		this.measuremin08 = measuremin08;
	}

	public BigDecimal getMeasuremin09() {
		return this.measuremin09;
	}

	public void setMeasuremin09(BigDecimal measuremin09) {
		this.measuremin09 = measuremin09;
	}

	public BigDecimal getMeasuremin10() {
		return this.measuremin10;
	}

	public void setMeasuremin10(BigDecimal measuremin10) {
		this.measuremin10 = measuremin10;
	}

	public BigDecimal getMeasuresub01() {
		return this.measuresub01;
	}

	public void setMeasuresub01(BigDecimal measuresub01) {
		this.measuresub01 = measuresub01;
	}

	public BigDecimal getMeasuresub02() {
		return this.measuresub02;
	}

	public void setMeasuresub02(BigDecimal measuresub02) {
		this.measuresub02 = measuresub02;
	}

	public BigDecimal getMeasuresub03() {
		return this.measuresub03;
	}

	public void setMeasuresub03(BigDecimal measuresub03) {
		this.measuresub03 = measuresub03;
	}

	public BigDecimal getMeasuresub04() {
		return this.measuresub04;
	}

	public void setMeasuresub04(BigDecimal measuresub04) {
		this.measuresub04 = measuresub04;
	}

	public BigDecimal getMeasuresub05() {
		return this.measuresub05;
	}

	public void setMeasuresub05(BigDecimal measuresub05) {
		this.measuresub05 = measuresub05;
	}

	public BigDecimal getMeasuresub06() {
		return this.measuresub06;
	}

	public void setMeasuresub06(BigDecimal measuresub06) {
		this.measuresub06 = measuresub06;
	}

	public BigDecimal getMeasuresub07() {
		return this.measuresub07;
	}

	public void setMeasuresub07(BigDecimal measuresub07) {
		this.measuresub07 = measuresub07;
	}

	public BigDecimal getMeasuresub08() {
		return this.measuresub08;
	}

	public void setMeasuresub08(BigDecimal measuresub08) {
		this.measuresub08 = measuresub08;
	}

	public BigDecimal getMeasuresub09() {
		return this.measuresub09;
	}

	public void setMeasuresub09(BigDecimal measuresub09) {
		this.measuresub09 = measuresub09;
	}

	public BigDecimal getMeasuresub10() {
		return this.measuresub10;
	}

	public void setMeasuresub10(BigDecimal measuresub10) {
		this.measuresub10 = measuresub10;
	}

	public String getMovetobinnum() {
		return this.movetobinnum;
	}

	public void setMovetobinnum(String movetobinnum) {
		this.movetobinnum = movetobinnum;
	}

	public String getMovetoloc() {
		return this.movetoloc;
	}

	public void setMovetoloc(String movetoloc) {
		this.movetoloc = movetoloc;
	}

	public String getMovetoparent() {
		return this.movetoparent;
	}

	public void setMovetoparent(String movetoparent) {
		this.movetoparent = movetoparent;
	}

	public Date getNewschcomp() {
		return this.newschcomp;
	}

	public void setNewschcomp(Date newschcomp) {
		this.newschcomp = newschcomp;
	}

	public Date getNewschstart() {
		return this.newschstart;
	}

	public void setNewschstart(Date newschstart) {
		this.newschstart = newschstart;
	}

	public String getNotachreason() {
		return this.notachreason;
	}

	public void setNotachreason(String notachreason) {
		this.notachreason = notachreason;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOnbehalfof() {
		return this.onbehalfof;
	}

	public void setOnbehalfof(String onbehalfof) {
		this.onbehalfof = onbehalfof;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrigrecordclass() {
		return this.origrecordclass;
	}

	public void setOrigrecordclass(String origrecordclass) {
		this.origrecordclass = origrecordclass;
	}

	public String getOrigrecordid() {
		return this.origrecordid;
	}

	public void setOrigrecordid(String origrecordid) {
		this.origrecordid = origrecordid;
	}

	public String getOriwonum() {
		return this.oriwonum;
	}

	public void setOriwonum(String oriwonum) {
		this.oriwonum = oriwonum;
	}

	public BigDecimal getOutlabcost() {
		return this.outlabcost;
	}

	public void setOutlabcost(BigDecimal outlabcost) {
		this.outlabcost = outlabcost;
	}

	public BigDecimal getOutmatcost() {
		return this.outmatcost;
	}

	public void setOutmatcost(BigDecimal outmatcost) {
		this.outmatcost = outmatcost;
	}

	public BigDecimal getOuttoolcost() {
		return this.outtoolcost;
	}

	public void setOuttoolcost(BigDecimal outtoolcost) {
		this.outtoolcost = outtoolcost;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnergroup() {
		return this.ownergroup;
	}

	public void setOwnergroup(String ownergroup) {
		this.ownergroup = ownergroup;
	}

	public String getOwnersysid() {
		return this.ownersysid;
	}

	public void setOwnersysid(String ownersysid) {
		this.ownersysid = ownersysid;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent2() {
		return this.parent2;
	}

	public void setParent2(String parent2) {
		this.parent2 = parent2;
	}

	public BigDecimal getParentchgsstatus() {
		return this.parentchgsstatus;
	}

	public void setParentchgsstatus(BigDecimal parentchgsstatus) {
		this.parentchgsstatus = parentchgsstatus;
	}

	public BigDecimal getPerformmoveto() {
		return this.performmoveto;
	}

	public void setPerformmoveto(BigDecimal performmoveto) {
		this.performmoveto = performmoveto;
	}

	public String getPersongroup() {
		return this.persongroup;
	}

	public void setPersongroup(String persongroup) {
		this.persongroup = persongroup;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getPmcounter() {
		return this.pmcounter;
	}

	public void setPmcounter(BigDecimal pmcounter) {
		this.pmcounter = pmcounter;
	}

	public String getPmdelayreason() {
		return this.pmdelayreason;
	}

	public void setPmdelayreason(String pmdelayreason) {
		this.pmdelayreason = pmdelayreason;
	}

	public Date getPmduedate() {
		return this.pmduedate;
	}

	public void setPmduedate(Date pmduedate) {
		this.pmduedate = pmduedate;
	}

	public Date getPmextdate() {
		return this.pmextdate;
	}

	public void setPmextdate(Date pmextdate) {
		this.pmextdate = pmextdate;
	}

	public String getPmfailreason() {
		return this.pmfailreason;
	}

	public void setPmfailreason(String pmfailreason) {
		this.pmfailreason = pmfailreason;
	}

	public String getPmimproveplan() {
		return this.pmimproveplan;
	}

	public void setPmimproveplan(String pmimproveplan) {
		this.pmimproveplan = pmimproveplan;
	}

	public Date getPmnextduedate() {
		return this.pmnextduedate;
	}

	public void setPmnextduedate(Date pmnextduedate) {
		this.pmnextduedate = pmnextduedate;
	}

	public String getPmnum() {
		return this.pmnum;
	}

	public void setPmnum(String pmnum) {
		this.pmnum = pmnum;
	}

	public String getPmnumOrigin() {
		return this.pmnumOrigin;
	}

	public void setPmnumOrigin(String pmnumOrigin) {
		this.pmnumOrigin = pmnumOrigin;
	}

	public double getPmschedule() {
		return this.pmschedule;
	}

	public void setPmschedule(double pmschedule) {
		this.pmschedule = pmschedule;
	}

	public BigDecimal getPmschupdate() {
		return this.pmschupdate;
	}

	public void setPmschupdate(BigDecimal pmschupdate) {
		this.pmschupdate = pmschupdate;
	}

	public BigDecimal getPmschupdate2() {
		return this.pmschupdate2;
	}

	public void setPmschupdate2(BigDecimal pmschupdate2) {
		this.pmschupdate2 = pmschupdate2;
	}

	public String getPmsupervisor() {
		return this.pmsupervisor;
	}

	public void setPmsupervisor(String pmsupervisor) {
		this.pmsupervisor = pmsupervisor;
	}

	public String getPoint01() {
		return this.point01;
	}

	public void setPoint01(String point01) {
		this.point01 = point01;
	}

	public String getPoint02() {
		return this.point02;
	}

	public void setPoint02(String point02) {
		this.point02 = point02;
	}

	public String getPoint03() {
		return this.point03;
	}

	public void setPoint03(String point03) {
		this.point03 = point03;
	}

	public String getPoint04() {
		return this.point04;
	}

	public void setPoint04(String point04) {
		this.point04 = point04;
	}

	public String getPoint05() {
		return this.point05;
	}

	public void setPoint05(String point05) {
		this.point05 = point05;
	}

	public String getPoint06() {
		return this.point06;
	}

	public void setPoint06(String point06) {
		this.point06 = point06;
	}

	public String getPoint07() {
		return this.point07;
	}

	public void setPoint07(String point07) {
		this.point07 = point07;
	}

	public String getPoint08() {
		return this.point08;
	}

	public void setPoint08(String point08) {
		this.point08 = point08;
	}

	public String getPoint09() {
		return this.point09;
	}

	public void setPoint09(String point09) {
		this.point09 = point09;
	}

	public String getPoint10() {
		return this.point10;
	}

	public void setPoint10(String point10) {
		this.point10 = point10;
	}

	public String getPointnum() {
		return this.pointnum;
	}

	public void setPointnum(String pointnum) {
		this.pointnum = pointnum;
	}

	public String getProblemcode() {
		return this.problemcode;
	}

	public void setProblemcode(String problemcode) {
		this.problemcode = problemcode;
	}

	public String getReasonforchange() {
		return this.reasonforchange;
	}

	public void setReasonforchange(String reasonforchange) {
		this.reasonforchange = reasonforchange;
	}

	public double getRemdur() {
		return this.remdur;
	}

	public void setRemdur(double remdur) {
		this.remdur = remdur;
	}

	public Date getReportdate() {
		return this.reportdate;
	}

	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}

	public String getReportedby() {
		return this.reportedby;
	}

	public void setReportedby(String reportedby) {
		this.reportedby = reportedby;
	}

	public Date getRespondby() {
		return this.respondby;
	}

	public void setRespondby(Date respondby) {
		this.respondby = respondby;
	}

	public String getRisk() {
		return this.risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getRowstamp() {
		return this.rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	public Date getSchedfinish() {
		return this.schedfinish;
	}

	public void setSchedfinish(Date schedfinish) {
		this.schedfinish = schedfinish;
	}

	public Date getSchedstart() {
		return this.schedstart;
	}

	public void setSchedstart(Date schedstart) {
		this.schedstart = schedstart;
	}

	public BigDecimal getSchimport() {
		return this.schimport;
	}

	public void setSchimport(BigDecimal schimport) {
		this.schimport = schimport;
	}

	public String getSchmonth() {
		return this.schmonth;
	}

	public void setSchmonth(String schmonth) {
		this.schmonth = schmonth;
	}

	public String getSchyear() {
		return this.schyear;
	}

	public void setSchyear(String schyear) {
		this.schyear = schyear;
	}

	public String getSendersysid() {
		return this.sendersysid;
	}

	public void setSendersysid(String sendersysid) {
		this.sendersysid = sendersysid;
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

	public BigDecimal getSuccess() {
		return this.success;
	}

	public void setSuccess(BigDecimal success) {
		this.success = success;
	}

	public Date getSucduedate() {
		return this.sucduedate;
	}

	public void setSucduedate(Date sucduedate) {
		this.sucduedate = sucduedate;
	}

	public String getSucimprove() {
		return this.sucimprove;
	}

	public void setSucimprove(String sucimprove) {
		this.sucimprove = sucimprove;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public Date getTargcompdate() {
		return this.targcompdate;
	}

	public void setTargcompdate(Date targcompdate) {
		this.targcompdate = targcompdate;
	}

	public Date getTargstartdate() {
		return this.targstartdate;
	}

	public void setTargstartdate(Date targstartdate) {
		this.targstartdate = targstartdate;
	}

	public BigDecimal getTaskid() {
		return this.taskid;
	}

	public void setTaskid(BigDecimal taskid) {
		this.taskid = taskid;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVerification() {
		return this.verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getWhomischangefor() {
		return this.whomischangefor;
	}

	public void setWhomischangefor(String whomischangefor) {
		this.whomischangefor = whomischangefor;
	}

	public BigDecimal getWoacceptscharges() {
		return this.woacceptscharges;
	}

	public void setWoacceptscharges(BigDecimal woacceptscharges) {
		this.woacceptscharges = woacceptscharges;
	}

	public String getWoclass() {
		return this.woclass;
	}

	public void setWoclass(String woclass) {
		this.woclass = woclass;
	}

	public String getWoeq1() {
		return this.woeq1;
	}

	public void setWoeq1(String woeq1) {
		this.woeq1 = woeq1;
	}

	public String getWoeq10() {
		return this.woeq10;
	}

	public void setWoeq10(String woeq10) {
		this.woeq10 = woeq10;
	}

	public String getWoeq11() {
		return this.woeq11;
	}

	public void setWoeq11(String woeq11) {
		this.woeq11 = woeq11;
	}

	public BigDecimal getWoeq12() {
		return this.woeq12;
	}

	public void setWoeq12(BigDecimal woeq12) {
		this.woeq12 = woeq12;
	}

	public Date getWoeq13() {
		return this.woeq13;
	}

	public void setWoeq13(Date woeq13) {
		this.woeq13 = woeq13;
	}

	public BigDecimal getWoeq14() {
		return this.woeq14;
	}

	public void setWoeq14(BigDecimal woeq14) {
		this.woeq14 = woeq14;
	}

	public String getWoeq2() {
		return this.woeq2;
	}

	public void setWoeq2(String woeq2) {
		this.woeq2 = woeq2;
	}

	public String getWoeq3() {
		return this.woeq3;
	}

	public void setWoeq3(String woeq3) {
		this.woeq3 = woeq3;
	}

	public String getWoeq4() {
		return this.woeq4;
	}

	public void setWoeq4(String woeq4) {
		this.woeq4 = woeq4;
	}

	public BigDecimal getWoeq5() {
		return this.woeq5;
	}

	public void setWoeq5(BigDecimal woeq5) {
		this.woeq5 = woeq5;
	}

	public Date getWoeq6() {
		return this.woeq6;
	}

	public void setWoeq6(Date woeq6) {
		this.woeq6 = woeq6;
	}

	public BigDecimal getWoeq7() {
		return this.woeq7;
	}

	public void setWoeq7(BigDecimal woeq7) {
		this.woeq7 = woeq7;
	}

	public String getWoeq8() {
		return this.woeq8;
	}

	public void setWoeq8(String woeq8) {
		this.woeq8 = woeq8;
	}

	public String getWoeq9() {
		return this.woeq9;
	}

	public void setWoeq9(String woeq9) {
		this.woeq9 = woeq9;
	}

	public String getWogroup() {
		return this.wogroup;
	}

	public void setWogroup(String wogroup) {
		this.wogroup = wogroup;
	}

	public String getWojo1() {
		return this.wojo1;
	}

	public void setWojo1(String wojo1) {
		this.wojo1 = wojo1;
	}

	public String getWojo2() {
		return this.wojo2;
	}

	public void setWojo2(String wojo2) {
		this.wojo2 = wojo2;
	}

	public String getWojo3() {
		return this.wojo3;
	}

	public void setWojo3(String wojo3) {
		this.wojo3 = wojo3;
	}

	public BigDecimal getWojo4() {
		return this.wojo4;
	}

	public void setWojo4(BigDecimal wojo4) {
		this.wojo4 = wojo4;
	}

	public String getWojo5() {
		return this.wojo5;
	}

	public void setWojo5(String wojo5) {
		this.wojo5 = wojo5;
	}

	public String getWojo6() {
		return this.wojo6;
	}

	public void setWojo6(String wojo6) {
		this.wojo6 = wojo6;
	}

	public String getWojo7() {
		return this.wojo7;
	}

	public void setWojo7(String wojo7) {
		this.wojo7 = wojo7;
	}

	public String getWojo8() {
		return this.wojo8;
	}

	public void setWojo8(String wojo8) {
		this.wojo8 = wojo8;
	}

	public String getWojp1() {
		return this.wojp1;
	}

	public void setWojp1(String wojp1) {
		this.wojp1 = wojp1;
	}

	public String getWojp2() {
		return this.wojp2;
	}

	public void setWojp2(String wojp2) {
		this.wojp2 = wojp2;
	}

	public String getWojp3() {
		return this.wojp3;
	}

	public void setWojp3(String wojp3) {
		this.wojp3 = wojp3;
	}

	public BigDecimal getWojp4() {
		return this.wojp4;
	}

	public void setWojp4(BigDecimal wojp4) {
		this.wojp4 = wojp4;
	}

	public Date getWojp5() {
		return this.wojp5;
	}

	public void setWojp5(Date wojp5) {
		this.wojp5 = wojp5;
	}

	public String getWol1() {
		return this.wol1;
	}

	public void setWol1(String wol1) {
		this.wol1 = wol1;
	}

	public String getWol2() {
		return this.wol2;
	}

	public void setWol2(String wol2) {
		this.wol2 = wol2;
	}

	public BigDecimal getWol3() {
		return this.wol3;
	}

	public void setWol3(BigDecimal wol3) {
		this.wol3 = wol3;
	}

	public Date getWol4() {
		return this.wol4;
	}

	public void setWol4(Date wol4) {
		this.wol4 = wol4;
	}

	public String getWolablnk() {
		return this.wolablnk;
	}

	public void setWolablnk(String wolablnk) {
		this.wolablnk = wolablnk;
	}

	public String getWolo1() {
		return this.wolo1;
	}

	public void setWolo1(String wolo1) {
		this.wolo1 = wolo1;
	}

	public BigDecimal getWolo10() {
		return this.wolo10;
	}

	public void setWolo10(BigDecimal wolo10) {
		this.wolo10 = wolo10;
	}

	public String getWolo2() {
		return this.wolo2;
	}

	public void setWolo2(String wolo2) {
		this.wolo2 = wolo2;
	}

	public String getWolo3() {
		return this.wolo3;
	}

	public void setWolo3(String wolo3) {
		this.wolo3 = wolo3;
	}

	public String getWolo4() {
		return this.wolo4;
	}

	public void setWolo4(String wolo4) {
		this.wolo4 = wolo4;
	}

	public String getWolo5() {
		return this.wolo5;
	}

	public void setWolo5(String wolo5) {
		this.wolo5 = wolo5;
	}

	public BigDecimal getWolo6() {
		return this.wolo6;
	}

	public void setWolo6(BigDecimal wolo6) {
		this.wolo6 = wolo6;
	}

	public Date getWolo7() {
		return this.wolo7;
	}

	public void setWolo7(Date wolo7) {
		this.wolo7 = wolo7;
	}

	public BigDecimal getWolo8() {
		return this.wolo8;
	}

	public void setWolo8(BigDecimal wolo8) {
		this.wolo8 = wolo8;
	}

	public String getWolo9() {
		return this.wolo9;
	}

	public void setWolo9(String wolo9) {
		this.wolo9 = wolo9;
	}

	public String getWonum() {
		return this.wonum;
	}

	public void setWonum(String wonum) {
		this.wonum = wonum;
	}

	public BigDecimal getWopriority() {
		return this.wopriority;
	}

	public void setWopriority(BigDecimal wopriority) {
		this.wopriority = wopriority;
	}

	public String getWoprogress() {
		return this.woprogress;
	}

	public void setWoprogress(String woprogress) {
		this.woprogress = woprogress;
	}

	public String getWorklocation() {
		return this.worklocation;
	}

	public void setWorklocation(String worklocation) {
		this.worklocation = worklocation;
	}

	public String getWorktype() {
		return this.worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public String getWorts1() {
		return this.worts1;
	}

	public void setWorts1(String worts1) {
		this.worts1 = worts1;
	}

	public String getWorts2() {
		return this.worts2;
	}

	public void setWorts2(String worts2) {
		this.worts2 = worts2;
	}

	public String getWorts3() {
		return this.worts3;
	}

	public void setWorts3(String worts3) {
		this.worts3 = worts3;
	}

	public Date getWorts4() {
		return this.worts4;
	}

	public void setWorts4(Date worts4) {
		this.worts4 = worts4;
	}

	public BigDecimal getWorts5() {
		return this.worts5;
	}

	public void setWorts5(BigDecimal worts5) {
		this.worts5 = worts5;
	}

	public BigDecimal getWosequence() {
		return this.wosequence;
	}

	public void setWosequence(BigDecimal wosequence) {
		this.wosequence = wosequence;
	}

}