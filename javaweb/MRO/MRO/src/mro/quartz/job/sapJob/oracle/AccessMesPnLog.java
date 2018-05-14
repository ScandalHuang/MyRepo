package mro.quartz.job.sapJob.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccessMesPnLog {

	public AccessMesPnLog() {

	}

	public void createLog(String material, String classstructureId,
			String type, String logInfo) throws Exception {
		Connection conn = Ora.getConn();
		PreparedStatement ps = null;

		String sql = " INSERT INTO SAP_ACCESS_LOG_PN ( " + " MATERIAL , "
				+ " CLASSSTRUCTUREID, " + " TYPE," + " LOGINFO, "
				+ " ACCESS_TIME , "
				+ " AI_ID ) VALUES (?,?,?,?,SYSDATE,AI_SEQ.nextval)";
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

	public void updtateStatusToMro(String material) throws SQLException {

		PreparedStatement pstmt = null;

		Connection conn = Ora.getConn();
		String strSQL = "UPDATE item SET IN21='Y' WHERE ITEMNUM=? ";
		try {
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, material);
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

}
