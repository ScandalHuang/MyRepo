package mro.quartz.job.sapJob.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.quartz.job.sapJob.vo.MroToSapIo;

public class AccessLog {
	public AccessLog() {

	}

	public List<MroToSapIo> getUpdateAItemList(String applyCategory)
			throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;
		List<MroToSapIo> list = new ArrayList<MroToSapIo>();

		String sql = "";
		if (applyCategory.equals("1") || applyCategory.equals("2")) {// 料號新增、規格異動
			String changeCondition = applyCategory.equals("2") ? " and ORIITEMNUM is not null "
					: "";
			sql = " SELECT a.*,B.CLASSSTRUCTUREID,b.itemnum MATERIAL,IA.FACILITY_NUM "
					+ "FROM (select a.itemnum,a.itemid,a.organization_code PLANT_CODE, "
					+ "(select count(1) from ITEM_SITE_TRANSFER_LOG "
					+ "where APPLY_CATEGORY='"
					+ applyCategory
					+ "' and APPLY_KEY_ID=A.ITEMNUM ) TOTAL_TRANSFER,"
					+ "(select count(1) from ITEM_SITE_TRANSFER_LOG "
					+ "where APPLY_CATEGORY='"
					+ applyCategory
					+ "' and APPLY_KEY_ID=A.ITEMNUM "
					+ "AND STATUS='Y' AND TRANSFER_DATE IS NOT NULL ) TOTAL_SUCCESS_Transfer "
					+ "from A_item a where a.status='SYNC' and a.in20 is null "
					+ changeCondition
					+ " ) a "
					+ "left join item b on a.itemid=b.itemid "
					+ "left join item_attribute  ia on b.itemid=ia.itemid "
					+ "WHERE  TOTAL_Transfer=TOTAL_SUCCESS_Transfer and TOTAL_Transfer > 0 "
					+ "and B.status IN ('SYNC', 'ACTIVE') ";
		} else if (applyCategory.equals("3")) {// 料號by site拋轉
			sql = "SELECT * FROM (SELECT a.APPLY_HEADER_NUM ITEMNUM ,(SELECT COUNT (1) "
					+ "FROM item_site_transfer_log WHERE apply_category = '"
					+ applyCategory
					+ "' "
					+ "AND  APPLY_KEY_ID= a.APPLY_HEADER_NUM) TOTAL_TRANSFER, "
					+ "(SELECT COUNT (1) FROM item_site_transfer_log "
					+ "WHERE apply_category = '"
					+ applyCategory
					+ "' AND APPLY_KEY_ID = a.APPLY_HEADER_NUM "
					+ "AND status = 'Y' AND transfer_date IS NOT NULL) total_success_transfer "
					+ "FROM ITEM_TRANSFER_header_APPLY a  "
					+ "WHERE a.status = 'SYNC' and in20 is null ) "
					+ "where total_transfer = total_success_transfer ";
		}
		try {
			System.out.println(sql);
			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				MroToSapIo mroToSapIo = new MroToSapIo();
				mroToSapIo.setItemNum(rs.getString("ITEMNUM"));
				mroToSapIo.setTotalTransfer(rs.getLong("TOTAL_TRANSFER"));
				if (applyCategory.equals("1") || applyCategory.equals("2")) {// 料號新增、規格異動
					mroToSapIo.setMaterial(rs.getString("MATERIAL"));
					mroToSapIo.setClassStructureid(rs
							.getString("CLASSSTRUCTUREID"));
					mroToSapIo.setFacilityNum(rs.getString("FACILITY_NUM"));
				}
				list.add(mroToSapIo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}

	// 料號by site拋轉 line
	public List getItemTransferLineApplyList(String applyCategory)
			throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;
		List<Map> list = new ArrayList<Map>();

		String sql = "SELECT B.APPLY_LINE_ID,B.ITEMNUM,"
				+ "(SELECT CLASSSTRUCTUREID FROM ITEM WHERE ITEMID=B.ITEMID) CLASSSTRUCTUREID,"
				+ "(SELECT FACILITY_NUM FROM ITEM_ATTRIBUTE WHERE ITEMID=B.ITEMID) FACILITY_NUM,  "
				+ "(SELECT COUNT (1) "
				+ "FROM item_site_transfer_log "
				+ "WHERE apply_category = '"
				+ applyCategory
				+ "' "
				+ "AND apply_key_id = a.apply_header_num "
				+ "AND ITEMNUM=B.ITEMNUM) total_transfer, "
				+ "(SELECT COUNT (1) "
				+ "FROM item_site_transfer_log "
				+ "WHERE apply_category = '"
				+ applyCategory
				+ "' "
				+ "AND apply_key_id = a.apply_header_num "
				+ "AND ITEMNUM=B.ITEMNUM "
				+ "AND status = 'Y' "
				+ "AND transfer_date IS NOT NULL) total_success_transfer "
				+ "FROM item_transfer_header_apply a "
				+ "left join item_transfer_line_apply b on a.APPLY_HEADER_ID=b.APPLY_HEADER_ID "
				+ "WHERE a.status = 'SYNC' AND in20 IS NULL and nvl(B.STATUS,'N') in ('N','E')";
		try {
			System.out.println(sql);
			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Map map = new LinkedHashMap<>();
				map.put("APPLY_LINE_ID", rs.getString("APPLY_LINE_ID"));
				map.put("TOTAL_TRANSFER", rs.getString("TOTAL_TRANSFER"));
				map.put("TOTAL_SUCCESS_TRANSFER",
						rs.getString("TOTAL_SUCCESS_TRANSFER"));
				map.put("ITEMNUM", rs.getString("ITEMNUM"));
				map.put("CLASSSTRUCTUREID", rs.getString("CLASSSTRUCTUREID"));
				map.put("FACILITY_NUM", rs.getString("FACILITY_NUM"));
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}

	public void createLog(MroToSapIo obj, String type, String logInfo)
			throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;

		String sql = " INSERT INTO sap_access_log ( " + " MATERIAL , "
				+ " PLANT, " + " TYPE," + " LOGINFO, " + " ACCESS_TIME , "
				+ " AI_ID,STORAGE_LOCATION,MATERIAL_GROUP ) "
				+ "VALUES (?,?,?,?,SYSDATE,AI_SEQ.nextval,?,?)";
		try {

			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}

			ps = conn.prepareStatement(sql);
			ps.setString(1, obj.getItemNum());
			ps.setString(2, obj.getPlantCode());

			ps.setString(3, type);
			ps.setString(4, logInfo);
			ps.setString(5, obj.getStorageLocation());
			ps.setString(6, obj.getMaterialGroup());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error.create.log");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}

	public void updateItemSiteTranferLog(MroToSapIo obj, String status)
			throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		String strSQL = "UPDATE ITEM_SITE_TRANSFER_LOG SET STATUS=?,TRANSFER_DATE=SYSDATE "
				+ "WHERE ITEM_SITE_TRANSFER_ID=? ";
		try {

			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, status.equals("S") ? "Y" : "N");
			pstmt.setString(2, obj.getItemSiteTransferId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void updtateStatusToMro(String applyCategory, String keyId)
			throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		String strSQL = "";
		if (applyCategory.equals("1") || applyCategory.equals("2")) {// 料號新增、規格異動
			strSQL = "UPDATE a_item SET IN20='Y' WHERE ITEMNUM=? ";
		} else if (applyCategory.equals("3")) {// 料號by site拋轉
			strSQL = "UPDATE ITEM_TRANSFER_HEADER_APPLY SET IN20='Y' WHERE APPLY_HEADER_NUM=? ";
		}
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, keyId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updtateItemStatusToMro(String applyCategory, String keyId)
			throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		String strSQL = "";
		if (applyCategory.equals("1") || applyCategory.equals("2")) {// 料號新增、規格異動
			strSQL = "UPDATE item SET STATUS='ACTIVE' WHERE ITEMNUM=? and STATUS='SYNC'";
		}else 
			return;
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, keyId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updtateItemSiteStatusToMro(String applyCategory, String itemnum)
			throws SQLException {
		if(!applyCategory.equals("1")) return;
		PreparedStatement pstmt = null;
		Connection conn = Ora.getConn();
		String strSQL = "";
			strSQL = "UPDATE ITEM_SITE SET ENABLE_FLAG='Y' "
					+ "WHERE ITEMNUM=? AND ENABLE_FLAG IS NULL";
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, itemnum);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updtateItemTransferLineApply(String applyLineId, String type)
			throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		String strSQL = "UPDATE ITEM_TRANSFER_LINE_APPLY SET STATUS='" + type
				+ "' " + "WHERE APPLY_LINE_ID='" + applyLineId + "' ";
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 料號by site拋轉header
	public List getItemTransferheaderApplyList() throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;
		List<Map> list = new ArrayList<Map>();

		String sql = "SELECT *  FROM ITEM_TRANSFER_HEADER_APPLY "
				+ "WHERE STATUS='SYNC' AND IN20='Y'";
		try {
			System.out.println(sql);
			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Map map = new LinkedHashMap<>();
				map.put("APPLY_HEADER_ID", rs.getString("APPLY_HEADER_ID"));
				map.put("APPLY_HEADER_NUM", rs.getString("APPLY_HEADER_NUM"));
				map.put("ACTION", rs.getString("ACTION"));
				map.put("LOCATION_SITE", rs.getString("LOCATION_SITE"));
				map.put("LOCATION_SITE", rs.getString("LOCATION_SITE"));
				map.put("CREATE_BY", rs.getString("CREATE_BY"));
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}

	public List getItemTransferEpMallCompanyIdList(String applyHeaderId)
			throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;
		List<Map> list = new ArrayList<Map>();

		String sql = "with ep_company as ( "
				+ "select itemnum,to_char(wm_concat(B.EP_MALL_COMPANY_ID)) EP_MALL_COMPANY_ID "
				+ "from ITEM_SITE A "
				+ "LEFT JOIN LOCATION_SITE_MAP B ON A.LOCATION_SITE=B.LOCATION_SITE "
				+ "WHERE B.EP_MALL_COMPANY_ID is not null "
				+ "and nvl(ENABLE_FLAG,'N')='Y' "
				+ " GROUP BY A.ITEMNUM) "
				+ "SELECT ITL.itemnum,("
				+ "select EP_MALL_COMPANY_ID  from ep_company where ITEMNUM=ITL.ITEMNUM) EP_MALL_COMPANY_ID "
				+ "FROM ITEM_TRANSFER_line_APPLY ITL "
				+ "where ITL.APPLY_HEADER_id='" + applyHeaderId + "'";
		try {
			System.out.println(sql);
			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Map map = new LinkedHashMap<>();
				map.put("ITEMNUM", rs.getString("ITEMNUM"));
				map.put("EP_MALL_COMPANY_ID",
						rs.getString("EP_MALL_COMPANY_ID"));
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}

	public void updtateEpMallCompanyId(Map<String, String> m)
			throws SQLException {

		PreparedStatement pstmt = null;
		Connection conn = Ora.getConn();
		String company = m.get("EP_MALL_COMPANY_ID") == null ? "" : m
				.get("EP_MALL_COMPANY_ID");
		String strSQL = "update apps.z_zpo_ep_item_control@dbl_erp_mrouser "
				+ "SET EP_MALL_COMPANY_ID='" + company + "' "
				+ "WHERE itemnum='" + m.get("ITEMNUM") + "' ";

		try {
			pstmt = conn.prepareStatement(strSQL);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updtateAItemApply() throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		conn.setAutoCommit(false);
		String strSQL = "UPDATE A_ITEM SET STATUS='APPR' WHERE STATUS='SYNC' AND IN20='Y' ";

		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updtateItemTransferheaderApply(Map<String, String> m,
			String status, String enablFlag) throws SQLException {

		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		Connection conn = Ora.getConn();
		conn.setAutoCommit(false);
		String strSQL = "UPDATE ITEM_TRANSFER_HEADER_APPLY SET STATUS='"
				+ status + "' " + "WHERE APPLY_HEADER_ID='"
				+ m.get("APPLY_HEADER_ID") + "' ";

		String strSQL2 = "UPDATE ITEM_SITE "
				+ "SET LAST_UPDATE_DATE=sysdate,LAST_UPDATED_BY='"
				+ m.get("CREATE_BY")
				+ "',"
				+ "ENABLE_FLAG='"
				+ enablFlag
				+ "' "
				+ "where ITEMNUM in ( select ITEMNUM from ITEM_TRANSFER_line_APPLY "
				+ "where APPLY_HEADER_id=" + m.get("APPLY_HEADER_ID")
				+ ") and location_site='" + m.get("LOCATION_SITE") + "' ";

		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.executeUpdate();

			pstmt2 = conn.prepareStatement(strSQL2);
			pstmt2.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void createEQLog(String material, String classstructureId,
			String facilityNum, String type, String logInfo) throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;

		String sql = " INSERT INTO SAP_ACCESS_LOG_EQ ( "
				+ " MATERIAL , "
				+ " CLASSSTRUCTUREID, "
				+ " TYPE,"
				+ " LOGINFO, "
				+ " ACCESS_TIME , "
				+ " AI_ID,FACILITY_NUM ) VALUES (?,?,?,?,SYSDATE,AI_SEQ.nextval,?)";
		try {

			if (conn.isClosed()) {
				throw new IllegalStateException("error.unexpected");
			}
			conn.setAutoCommit(true);
			ps = conn.prepareStatement(sql);
			ps.setString(1, material);
			ps.setString(2, classstructureId);

			ps.setString(3, type);
			ps.setString(4, logInfo);
			ps.setString(5, facilityNum);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error.create.log");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("error.unexpected");
			}
		}
	}
}
