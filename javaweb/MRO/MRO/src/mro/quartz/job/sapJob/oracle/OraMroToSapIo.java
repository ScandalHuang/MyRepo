package mro.quartz.job.sapJob.oracle;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import mro.quartz.job.sapJob.vo.MroToSapIo;

public class OraMroToSapIo {
	public int getDataCount(String applyCategory,int itemSiteTransferId) throws SQLException {
		// applyCategory: 1:料號新增,2:規格異動,3:BY_SITE申請
		PreparedStatement pstmt = null;
		Connection conn =Ora.getConn();
		ResultSet rs = null;
		String strSQL = "SELECT count(1) total FROM ITEM_SITE_TRANSFER_LOG_V "
				+ "WHERE apply_category = '"+applyCategory+"' "
				+ "and ITEM_SITE_TRANSFER_ID > "+itemSiteTransferId+" ";
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery(strSQL);
			rs.next();
			return rs.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	public List<MroToSapIo> getExperimentData(String applyCategory,int itemSiteTransferId)
			throws SQLException {
		// applyCategory: 1:料號新增,2:規格異動,3:BY_SITE申請
		List<MroToSapIo> list = new ArrayList<MroToSapIo>();
		PreparedStatement pstmt = null;
		Connection conn =Ora.getConn();
		ResultSet rs = null;
		String strSQL = "select * from ("
				+ "SELECT * FROM ITEM_SITE_TRANSFER_LOG_V "
				+ "WHERE apply_category = '"+applyCategory+"' "
				+ "and ITEM_SITE_TRANSFER_ID > "+itemSiteTransferId+"  "
				+ "order by ITEM_SITE_TRANSFER_ID) "
				+ "where rownum<=100000 ";
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery(strSQL);

			while (rs.next()) {
				// HashMap hmData = new HashMap();
				// hmData.put("ITEMNUM",rs.getString("ITEMNUM"));
				// hmData.put("PLANT_CODE",rs.getString("PLANT_CODE"));
				MroToSapIo data = new MroToSapIo();

				data.setItemNum(rs.getString("ITEMNUM"));
				data.setClassStructureid(rs.getString("CLASSSTRUCTUREID"));
				data.setDescription(rs.getString("DESCRIPTION"));
				data.setOridescription(rs.getString("DESCRIPTION"));
				data.setOrderUnit(rs.getString("ORDERUNIT"));
				data.setPackageUnit(rs.getString("PACKAGE_UNIT"));
				data.setTransferQuantity(rs.getString("TRANSFER_QUANTITY"));
				data.setUnitCost(rs.getString("UNITCOST"));
				data.setDeliverytime(rs.getString("DELIVERYTIME"));
				data.setStorageCondition(rs.getString("STORAGE_CONDITION"));
				data.setTotalShelfLife(rs.getString("TOTAL_SHELF_LIFE"));
				data.setMinShelfLife(rs.getString("MIN_SHELF_LIFE"));
				
				if(StringUtils.isBlank(data.getTotalShelfLife())){
					data.setTotalShelfLife("0");
				}
				if(StringUtils.isBlank(data.getMinShelfLife())){
					data.setMinShelfLife("0");
				}
				
//				data.setEauditType(rs.getString(15));
				data.setVendor(rs.getString("VENDOR"));
				data.setRemark(rs.getString("REMARK"));
				data.setSecondItemnum(rs.getString("SECOND_ITEMNUM"));
				data.setMcContinuityUse(rs.getString("MC_CONTINUITY_USE"));
				data.setMcUseFrequencyLevel(rs.getString("MC_USE_FREQUENCY_LEVEL"));
				data.setMcTotalMinLevel(rs.getString("MC_TOTAL_MIN_LEVEL"));
				data.setMcEveryUseLevel(rs.getString("MC_EVERY_USE_LEVEL"));
				data.setMcSuggestingSafeLevel(rs.getString("MC_SUGGESTING_SAFE_LEVEL"));
				data.setPlantCode(rs.getString("PLANT_CODE"));
				data.setMaterialGroup(rs.getString("MATERIAL_GROUP"));
				data.setMaterialGroupDesc(rs.getString("MATERIAL_GROUP_DESC"));
				data.setMaterialType(rs.getString("MATERIAL_TYPE"));
				data.setSdSalesOrganization(rs.getString("SD_SALES_ORGANIZATION"));
				data.setSdDistributionChannel(rs.getString("SD_DISTRIBUTION_CHANNEL"));
				data.setSdDdivision(rs.getString("SD_DIVISION"));
				data.setSdMaterialFreightGrp(rs.getString("SD_MATERIAL_FREIGHT_GRP"));
				data.setSdAccountAssignmentGroup(rs.getString("SD_ACCOUNT_ASSIGNMENT_GROUP"));
				data.setSdMatlGrpPackMatls(rs.getString("SD_MATL_GRP_PACK_MATLS"));
				data.setPurchasingInspectionType(rs.getString("PURCHASING_INSPECTION_TYPE"));
				data.setPurchasingGroup(rs.getString("PURCHASING_GROUP"));
				data.setMrpGroup(rs.getString("MRP_GROUP"));
				data.setMrpType(rs.getString("MRP_TYPE"));
				data.setMrpController(rs.getString("MRP_CONTROLLER"));
				data.setMrpSelectionMethod(rs.getString("MRP_SELECTION_METHOD"));
				data.setMrpLotSize(rs.getString("MRP_LOT_SIZE"));
				data.setMrpProcurementType(rs.getString("MRP_PROCUREMENT_TYPE"));
				data.setMrpReorderPoint(rs.getString("MRP_REORDER_POINT"));
				data.setMrpGrprocessingDay(rs.getString("MRP_GRPROCESSING_DAY"));
				data.setMrpProdStorageLoc(rs.getString("MRP_PROD_STORAGE_LOC"));
				data.setMrpStorageLocForEp(rs.getString("MRP_STORAGE_LOC_FOR_EP"));
				data.setMrpSpecialProcurementType(rs.getString("MRP_SPECIAL_PROCUREMENT_TYPE"));
				data.setMrpProdSchedProfile(rs.getString("MRP_PROD_SCHED_PROFILE"));
				data.setMrpRepetitiveMfg(rs.getString("MRP_REPETITIVE_MFG"));
				data.setMrpRemProfile(rs.getString("MRP_REM_PROFILE"));
				data.setMrpIndividualColl(rs.getString("MRP_INDIVIDUAL_COLL"));
				data.setMrpStrategyGroup(rs.getString("MRP_STRATEGY_GROUP"));
				data.setMrpMixedMrp(rs.getString("MRP_MIXED_MRP"));
				data.setMrpBackflush(rs.getString("MRP_BACKFLUSH"));
				data.setMrpInhouseProduction(rs.getString("MRP_INHOUSE_PRODUCTION"));
				data.setMrpProductionUnit(rs.getString("MRP_PRODUCTION_UNIT"));
				data.setIssueUnit(rs.getString("ISSUE_UNIT"));
				data.setStorageLocation(rs.getString("STORAGE_LOCATION"));
				data.setStorageBatchManagement(rs.getString("STORAGE_BATCH_MANAGEMENT"));
				data.setAccountingValuationClass(rs.getString("ACCOUNTING_VALUATION_CLASS"));
				data.setAccountingPriceControl(rs.getString("ACCOUNTING_PRICE_CONTROL"));
				data.setAccountionPriceUnit(rs.getString("ACCOUNTION_PRICE_UNIT"));
				data.setSpecprocuremCosting(rs.getString("SPECPROCUREM_COSTING"));
				data.setProfitCtr(rs.getString("PROFIT_CENTER"));
				data.setActionType(rs.getString("ACTION_TYPE"));
				data.setKeyId(rs.getString("APPLY_KEY_ID"));
				data.setName(rs.getString("NAME"));
				data.setEmpNo(rs.getString("EMP_NO"));
				data.setExtNo(rs.getString("EXT_NO"));
				data.setGpSapCode(rs.getString("GP_SAP_CODE"));
				data.setItemSiteTransferId(rs.getString("ITEM_SITE_TRANSFER_ID"));
				data.setStrategyMgmtFlag(rs.getString("STRATEGY_MGMT_FLAG"));
				//201703 add
				data.setItemCat(rs.getString("ITEM_CAT"));
				data.setTransGrp(rs.getString("TRANS_GRP"));
				data.setLoadinggrp(rs.getString("LOADINGGRP"));
				data.setMatlStats(rs.getString("MATL_STATS"));
				data.setTaxType1(rs.getString("TAX_TYPE_1"));
				data.setTaxclass1(rs.getString("TAXCLASS_1"));
				
				//40碼截斷
				if(data.getDescription().getBytes("BIG5").length>40){
					int l=0;
					char[] descriptions=data.getDescription().toCharArray();
					StringBuffer d1 = new StringBuffer();
					StringBuffer d2 = new StringBuffer();
					for(char c:descriptions){
						l+=String.valueOf(c).getBytes("BIG5").length;
						if(l<=40){
							d1.append(c);
						}else{
							d2.append(c);
						}
					}
					data.setDescription(d1.toString());
					data.setDescription2(d2.toString());
				}
				list.add(data);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return list;
	}

}
