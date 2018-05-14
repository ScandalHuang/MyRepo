package mro.quartz.job.sapJob.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mro.quartz.job.sapJob.vo.MroToSapMespn;

public class OraMroToMesPnIo{
	public List<MroToSapMespn> getExperimentData() throws SQLException {
		List<MroToSapMespn> list = new ArrayList<MroToSapMespn>();

		PreparedStatement pstmt = null;
		Connection conn = Ora.getConn();
		ResultSet rs = null;

		String strSQL = "SELECT i.itemnum, i.classstructureid AS material_group "
				+ "FROM item i, item_site_transfer_log c "
				+ "WHERE i.itemnum = c.itemnum "
				+ "AND i.in21 IS NULL "
				+ "AND i.status IN ('SYNC', 'ACTIVE') "
				+ "AND i.classstructureid IN ('R1107', 'R1508', 'R1510', 'R1603', 'R1606') "
				+ "and c.STATUS='Y' group by i.itemnum, i.classstructureid";
		try {
			System.out.println(strSQL);
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery(strSQL);

			while (rs.next()) {
				MroToSapMespn data = new MroToSapMespn();

				data.setItemNum(rs.getString("ITEMNUM"));
				data.setClassStructureid(rs.getString("Material_Group"));

				list.add(data);
			}
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

		return list;
	}

}
