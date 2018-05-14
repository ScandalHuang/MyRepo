package mro.quartz.job.sapJob.vo;

import java.util.HashMap;

public class MroToSapIo {
	private String eaudittranSid;  
	private String itemNum;
	private String plantCode;
	private String  classStructureid;
	private String description;
	private String description2;
	private String oridescription;
	private String orderUnit;
	private String packageUnit;
	private String transferQuantity;
	private String unitCost;
	private String deliverytime;
	private String storageCondition;
	private String totalShelfLife;
	private String minShelfLife;
	private String eauditType;
	private String vendor;
//	private String status;
//	private String name;
//	private String empNo;
//	private String extNo;
//	private String deptNo;
	private String remark; //用途說明
//	private String secondItemFlag; //是否有替代料號
	private String secondItemnum;  //替代料號
	private String mcContinuityUse;  //連續性用料
	//private String mcOriMinLevel;  //月需求量
	private String mcUseFrequencyLevel;  //耗用頻率(月)
	private String mcTotalMinLevel;  //總數量 
	private String mcEveryUseLevel;  //每次領用量
	private String mcSuggestingSafeLevel; // User建議安全存量
	 
	private String  materialGroup;
	private String   materialGroupDesc;
	private String   materialType;
	private String   sdSalesOrganization;
	private String  sdDistributionChannel;
	private String  sdDdivision;
	private String   sdMaterialFreightGrp;
	private String   sdAccountAssignmentGroup;
	private String   sdMatlGrpPackMatls;
	private String  purchasingInspectionType;
	private String  purchasingGroup;
	private String  mrpGroup;
	private String  mrpType;
	private String   mrpController;
	private String   mrpSelectionMethod;
	private String  mrpLotSize;
	private String   mrpProcurementType;
	private String   mrpReorderPoint;
	private String   mrpGrprocessingDay;
	private String   mrpProdStorageLoc;
	private String   mrpStorageLocForEp;
	private String   mrpSpecialProcurementType;
	private String   mrpProdSchedProfile;
	private String   mrpRepetitiveMfg;
	private String  mrpRemProfile;
	private String   mrpIndividualColl;
	private String  mrpStrategyGroup;
	private String  mrpMixedMrp;
	private String   mrpBackflush;
	private String   mrpInhouseProduction;
	private String   mrpProductionUnit;
	private String  issueUnit;
	private String   storageLocation;
	private String  storageBatchManagement;
	private String   accountingValuationClass;
	private String  accountingPriceControl;
	private String  accountionPriceUnit;
	private String  specprocuremCosting;
    private String  profitCtr;
    private String  alnvalue;
    private String  actionType;
    private String keyId;
    private String strategyMgmtFlag;
    private String facilityNum;
    private long totalTransfer;
    private String material;
    private String itemSiteTransferId;
    

	private String  name;
	private String  empNo;
	private String  extNo;
	private String  gpSapCode;
    
	protected HashMap hmPart;
     
	private String itemCat;
	private String transGrp;
	private String loadinggrp;
	//private String availcheck;
	private String matlStats;
	private String taxType1;
	private String taxclass1;
 
	public String getClassStructureid() {
		return classStructureid;
	}
	public void setClassStructureid(String classStructureid) {
		this.classStructureid = classStructureid;
	}
	public String getEaudittranSid() {
		return eaudittranSid;
	}
	public void setEaudittranSid(String eaudittranSid) {
		this.eaudittranSid = eaudittranSid;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderUnit() {
		return orderUnit;
	}
	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}
	public String getPackageUnit() {
		return packageUnit;
	}
	public void setPackageUnit(String packageUnit) {
		this.packageUnit = packageUnit;
	}
	public String getTransferQuantity() {
		return transferQuantity;
	}
	public void setTransferQuantity(String transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	public String getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public String getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	public String getTotalShelfLife() {
		return totalShelfLife;
	}
	public void setTotalShelfLife(String totalShelfLife) {
		this.totalShelfLife = totalShelfLife;
	}
	public String getMinShelfLife() {
		return minShelfLife;
	}
	public void setMinShelfLife(String minShelfLife) {
		this.minShelfLife = minShelfLife;
	}
	public String getEauditType() {
		return eauditType;
	}
	public void setEauditType(String eauditType) {
		this.eauditType = eauditType;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	 
	 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
 
	public String getSecondItemnum() {
		return secondItemnum;
	}
	public void setSecondItemnum(String secondItemnum) {
		this.secondItemnum = secondItemnum;
	}
	public String getMcContinuityUse() {
		return mcContinuityUse;
	}
	public void setMcContinuityUse(String mcContinuityUse) {
		this.mcContinuityUse = mcContinuityUse;
	}
 
	public String getMcUseFrequencyLevel() {
		return mcUseFrequencyLevel;
	}
	public void setMcUseFrequencyLevel(String mcUseFrequencyLevel) {
		this.mcUseFrequencyLevel = mcUseFrequencyLevel;
	}
	public String getMcTotalMinLevel() {
		return mcTotalMinLevel;
	}
	public void setMcTotalMinLevel(String mcTotalMinLevel) {
		this.mcTotalMinLevel = mcTotalMinLevel;
	}
	public String getMcEveryUseLevel() {
		return mcEveryUseLevel;
	}
	public void setMcEveryUseLevel(String mcEveryUseLevel) {
		this.mcEveryUseLevel = mcEveryUseLevel;
	}
	public String getMcSuggestingSafeLevel() {
		return mcSuggestingSafeLevel;
	}
	public void setMcSuggestingSafeLevel(String mcSuggestingSafeLevel) {
		this.mcSuggestingSafeLevel = mcSuggestingSafeLevel;
	}
 
	 
	 
	 
	 
	
	public String getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	public String getMaterialGroup() {
		return materialGroup;
	}
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	public String getMaterialGroupDesc() {
		return materialGroupDesc;
	}
	public void setMaterialGroupDesc(String materialGroupDesc) {
		this.materialGroupDesc = materialGroupDesc;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getSdSalesOrganization() {
		return sdSalesOrganization;
	}
	public void setSdSalesOrganization(String sdSalesOrganization) {
		this.sdSalesOrganization = sdSalesOrganization;
	}
	public String getSdDistributionChannel() {
		return sdDistributionChannel;
	}
	public void setSdDistributionChannel(String sdDistributionChannel) {
		this.sdDistributionChannel = sdDistributionChannel;
	}
	public String getSdDdivision() {
		return sdDdivision;
	}
	public void setSdDdivision(String sdDdivision) {
		this.sdDdivision = sdDdivision;
	}
	public String getSdMaterialFreightGrp() {
		return sdMaterialFreightGrp;
	}
	public void setSdMaterialFreightGrp(String sdMaterialFreightGrp) {
		this.sdMaterialFreightGrp = sdMaterialFreightGrp;
	}
	public String getSdAccountAssignmentGroup() {
		return sdAccountAssignmentGroup;
	}
	public void setSdAccountAssignmentGroup(String sdAccountAssignmentGroup) {
		this.sdAccountAssignmentGroup = sdAccountAssignmentGroup;
	}
	public String getSdMatlGrpPackMatls() {
		return sdMatlGrpPackMatls;
	}
	public void setSdMatlGrpPackMatls(String sdMatlGrpPackMatls) {
		this.sdMatlGrpPackMatls = sdMatlGrpPackMatls;
	}
	public String getPurchasingInspectionType() {
		return purchasingInspectionType;
	}
	public void setPurchasingInspectionType(String purchasingInspectionType) {
		this.purchasingInspectionType = purchasingInspectionType;
	}
	public String getPurchasingGroup() {
		return purchasingGroup;
	}
	public void setPurchasingGroup(String purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
	}
	public String getMrpGroup() {
		return mrpGroup;
	}
	public void setMrpGroup(String mrpGroup) {
		this.mrpGroup = mrpGroup;
	}
	public String getMrpType() {
		return mrpType;
	}
	public void setMrpType(String mrpType) {
		this.mrpType = mrpType;
	}
	public String getMrpController() {
		return mrpController;
	}
	public void setMrpController(String mrpController) {
		this.mrpController = mrpController;
	}
	public String getMrpSelectionMethod() {
		return mrpSelectionMethod;
	}
	public void setMrpSelectionMethod(String mrpSelectionMethod) {
		this.mrpSelectionMethod = mrpSelectionMethod;
	}
	public String getMrpLotSize() {
		return mrpLotSize;
	}
	public void setMrpLotSize(String mrpLotSize) {
		this.mrpLotSize = mrpLotSize;
	}
	public String getMrpProcurementType() {
		return mrpProcurementType;
	}
	public void setMrpProcurementType(String mrpProcurementType) {
		this.mrpProcurementType = mrpProcurementType;
	}
	public String getMrpReorderPoint() {
		return mrpReorderPoint;
	}
	public void setMrpReorderPoint(String mrpReorderPoint) {
		this.mrpReorderPoint = mrpReorderPoint;
	}
	public String getMrpGrprocessingDay() {
		return mrpGrprocessingDay;
	}
	public void setMrpGrprocessingDay(String mrpGrprocessingDay) {
		this.mrpGrprocessingDay = mrpGrprocessingDay;
	}
	public String getMrpProdStorageLoc() {
		return mrpProdStorageLoc;
	}
	public void setMrpProdStorageLoc(String mrpProdStorageLoc) {
		this.mrpProdStorageLoc = mrpProdStorageLoc;
	}
	public String getMrpStorageLocForEp() {
		return mrpStorageLocForEp;
	}
	public void setMrpStorageLocForEp(String mrpStorageLocForEp) {
		this.mrpStorageLocForEp = mrpStorageLocForEp;
	}
	public String getMrpSpecialProcurementType() {
		return mrpSpecialProcurementType;
	}
	public void setMrpSpecialProcurementType(String mrpSpecialProcurementType) {
		this.mrpSpecialProcurementType = mrpSpecialProcurementType;
	}
	public String getMrpProdSchedProfile() {
		return mrpProdSchedProfile;
	}
	public void setMrpProdSchedProfile(String mrpProdSchedProfile) {
		this.mrpProdSchedProfile = mrpProdSchedProfile;
	}
	public String getMrpRepetitiveMfg() {
		return mrpRepetitiveMfg;
	}
	public void setMrpRepetitiveMfg(String mrpRepetitiveMfg) {
		this.mrpRepetitiveMfg = mrpRepetitiveMfg;
	}
	public String getMrpRemProfile() {
		return mrpRemProfile;
	}
	public void setMrpRemProfile(String mrpRemProfile) {
		this.mrpRemProfile = mrpRemProfile;
	}
	public String getMrpIndividualColl() {
		return mrpIndividualColl;
	}
	public void setMrpIndividualColl(String mrpIndividualColl) {
		this.mrpIndividualColl = mrpIndividualColl;
	}
	public String getMrpStrategyGroup() {
		return mrpStrategyGroup;
	}
	public void setMrpStrategyGroup(String mrpStrategyGroup) {
		this.mrpStrategyGroup = mrpStrategyGroup;
	}
	public String getMrpMixedMrp() {
		return mrpMixedMrp;
	}
	public void setMrpMixedMrp(String mrpMixedMrp) {
		this.mrpMixedMrp = mrpMixedMrp;
	}
	public String getMrpBackflush() {
		return mrpBackflush;
	}
	public void setMrpBackflush(String mrpBackflush) {
		this.mrpBackflush = mrpBackflush;
	}
	public String getMrpInhouseProduction() {
		return mrpInhouseProduction;
	}
	public void setMrpInhouseProduction(String mrpInhouseProduction) {
		this.mrpInhouseProduction = mrpInhouseProduction;
	}
	public String getMrpProductionUnit() {
		return mrpProductionUnit;
	}
	public void setMrpProductionUnit(String mrpProductionUnit) {
		this.mrpProductionUnit = mrpProductionUnit;
	}
	public String getIssueUnit() {
		return issueUnit;
	}
	public void setIssueUnit(String issueUnit) {
		this.issueUnit = issueUnit;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public String getStorageBatchManagement() {
		return storageBatchManagement;
	}
	public void setStorageBatchManagement(String storageBatchManagement) {
		this.storageBatchManagement = storageBatchManagement;
	}
	public String getAccountingValuationClass() {
		return accountingValuationClass;
	}
	public void setAccountingValuationClass(String accountingValuationClass) {
		this.accountingValuationClass = accountingValuationClass;
	}
	public String getAccountingPriceControl() {
		return accountingPriceControl;
	}
	public void setAccountingPriceControl(String accountingPriceControl) {
		this.accountingPriceControl = accountingPriceControl;
	}
	public String getAccountionPriceUnit() {
		return accountionPriceUnit;
	}
	public void setAccountionPriceUnit(String accountionPriceUnit) {
		this.accountionPriceUnit = accountionPriceUnit;
	}
	public String getSpecprocuremCosting() {
		return specprocuremCosting;
	}
	public void setSpecprocuremCosting(String specprocuremCosting) {
		this.specprocuremCosting = specprocuremCosting;
	}
	
	public String getProfitCtr() {
		return profitCtr;
	}
	public void setProfitCtr(String profitCtr) {
		this.profitCtr = profitCtr;
	}
	/*
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getItemNum());
		sb.append(", ");
		 
		return sb.toString();
	}*/
	protected void setApplicationPart(HashMap hm) {
		this.hmPart = hm;
	}
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	public String getAlnvalue() {
		return alnvalue;
	}
	public void setAlnvalue(String alnvalue) {
		this.alnvalue = alnvalue;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getExtNo() {
		return extNo;
	}
	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}
	public String getStrategyMgmtFlag() {
		return strategyMgmtFlag;
	}
	public void setStrategyMgmtFlag(String strategyMgmtFlag) {
		this.strategyMgmtFlag = strategyMgmtFlag;
	}
	public String getFacilityNum() {
		return facilityNum;
	}
	public void setFacilityNum(String facilityNum) {
		this.facilityNum = facilityNum;
	}
	public long getTotalTransfer() {
		return totalTransfer;
	}
	public void setTotalTransfer(long totalTransfer) {
		this.totalTransfer = totalTransfer;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	public String getOridescription() {
		return oridescription;
	}
	public void setOridescription(String oridescription) {
		this.oridescription = oridescription;
	}
	public String getItemSiteTransferId() {
		return itemSiteTransferId;
	}
	public void setItemSiteTransferId(String itemSiteTransferId) {
		this.itemSiteTransferId = itemSiteTransferId;
	}
	public String getGpSapCode() {
		return gpSapCode;
	}
	public void setGpSapCode(String gpSapCode) {
		this.gpSapCode = gpSapCode;
	}
	public String getItemCat() {
		return itemCat;
	}
	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}
	public String getTransGrp() {
		return transGrp;
	}
	public void setTransGrp(String transGrp) {
		this.transGrp = transGrp;
	}
	public String getLoadinggrp() {
		return loadinggrp;
	}
	public void setLoadinggrp(String loadinggrp) {
		this.loadinggrp = loadinggrp;
	}
	public String getMatlStats() {
		return matlStats;
	}
	public void setMatlStats(String matlStats) {
		this.matlStats = matlStats;
	}
	public String getTaxType1() {
		return taxType1;
	}
	public void setTaxType1(String taxType1) {
		this.taxType1 = taxType1;
	}
	public String getTaxclass1() {
		return taxclass1;
	}
	public void setTaxclass1(String taxclass1) {
		this.taxclass1 = taxclass1;
	}
	
	
}
