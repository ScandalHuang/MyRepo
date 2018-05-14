package mro.app.util;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class HrInfoSync extends HttpServlet {

	/**
	 * @param args
	 * @throws NamingException
	 * @throws SQLException
	 * @throws FinderException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataSource dataSrc = setupDataSource(
				"oracle.jdbc.OracleDriver", "BPMADM", "BPMADM",
				"jdbc:oracle:thin:@pvPBPMDB.cminl.oa:1521:pbpm");
		// 測試機
//		DataSource dataDest = setupDataSource("oracle.jdbc.OracleDriver",
//				"csp", "csp",
//				"jdbc:oracle:thin:@C4C009627.cminl.oa:1521:mro130515");
		
		// 正式機
		DataSource dataDest = setupDataSource(
				"oracle.jdbc.OracleDriver", "CSP_AP", "mro0801",
				"jdbc:oracle:thin:@pvMAXIMO.cminl.oa:1521:PMAXIMDB");

		Connection connSrc = null;
		Connection connDest = null;
		try {
			connSrc = dataSrc.getConnection();

			connDest = dataDest.getConnection();

//			 updatePerson(connDest);
			 update(connSrc, connDest,"hr_org");
			 update(connSrc, connDest,"HR_EMP");
			// update(connSrc, connDest,"HR_DEPUTY");//代理人另有job
			connDest.commit();
			sendMail();
			connSrc.close();
			connDest.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (connDest != null) {
				try {
					connDest.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		System.out.println("ok");
	}

	public static DataSource setupDataSource(String sDrvName, String sUserName,
			String sPwd, String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(sDrvName);
		ds.setUsername(sUserName);
		ds.setPassword(sPwd);
		ds.setUrl(connectURI);
		ds.setMaxActive(30);
		ds.setMaxIdle(4);
		return ds;
	}


	private static void updatePerson(Connection connDest) throws SQLException {
		CallableStatement cs;
		try {
			// 設定 CallableStatement
			System.out.println("call MRO_GET_PERSON_FROM_HR_P");
			cs = connDest.prepareCall("{call MRO_GET_PERSON_FROM_HR_P}");

			// 執行 CallableStatement
			cs.execute();
			System.out.println("end MRO_GET_PERSON_FROM_HR_P");
		} catch (SQLException e) {
		}
	}

	private static void update(Connection connSrc, Connection connDest,
			String Table) throws SQLException {

		Statement stmtSrc;
		stmtSrc = connSrc.createStatement();

		Statement stmtDestX = null;
		PreparedStatement stmtDest = null;

		stmtDestX = connDest.createStatement();
		stmtDestX.execute("DELETE " + Table);
		stmtDestX.close();

		ResultSet rs;
		rs = stmtSrc.executeQuery("SELECT * FROM " + Table);

		int columnCount = rs.getMetaData().getColumnCount();
		String comma = "";
		String sql = "INSERT INTO " + Table + " VALUES (";
		for (int i = 0; i < columnCount; i++) {
			sql = sql + comma + "?";
			comma = ",";
		}
		sql = sql + ")";
		System.out.println(sql);
		stmtDest = connDest.prepareStatement(sql);

		int recordCount = 0;
		while (rs.next()) {
			++recordCount;
			System.out.println(recordCount + ": " + rs.getString(1));
			for (int i = 1; i <= columnCount; i++) {

				int iType = rs.getMetaData().getColumnType(i);
				if (java.sql.Types.VARCHAR == iType) {
					stmtDest.setString(i, rs.getString(i));
				} else if (java.sql.Types.NUMERIC == iType) {
					stmtDest.setInt(i, rs.getInt(i));
				} else if (java.sql.Types.TIMESTAMP == iType
						|| java.sql.Types.DATE == iType) {
					stmtDest.setTimestamp(i, rs.getTimestamp(i));
				} else {
					System.out.println(iType);
					System.out.println(rs.getString(i));
				}

			}
			int updateCount = stmtDest.executeUpdate();
			if (updateCount != 1) {
				// System.out.println("Error in record count: " + recordCount);
			}
		}
	}

	public static void sendMail() {
		String mailhost = "tnsmtp.cminl.oa";
		String from = "MailSender_MRO@cminl.oa";
		String to = "hongjie.wu@innolux.com,kaiyeu.hsu@innolux.com,RICHARD.CL.CHANG@innolux.com";

		String subject = "MRO 人事資料更新成功!";
		StringBuffer body = new StringBuffer();
		body.append("MRO 人事資料更新成功!<br>");
		try {
			Properties props = System.getProperties();
			if (mailhost != null) {
				props.put("mail.smtp.host", mailhost);
			}
			Session session = Session.getInstance(props, null);
			session.setDebug(false);
			MimeMessage msg = new MimeMessage(session);
			if (from != null) {
				msg.setFrom(new InternetAddress(from));
			} else {
				msg.setFrom();
			}
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));
			/*
			 * msg.setRecipients(Message.RecipientType.CC,
			 * InternetAddress.parse(cc, false));
			 */
			/*
			 * msg.setRecipients(javax.mail.Message.RecipientType.TO,
			 * InternetAddress.parse(to, false)); log.debug("MAIL TO =====" +
			 * to); if(cc != null) {
			 * msg.setRecipients(javax.mail.Message.RecipientType.CC,
			 * InternetAddress.parse(cc, false)); } log.debug("MAIL CC =====" +
			 * cc); if(bcc != null) {
			 * msg.setRecipients(javax.mail.Message.RecipientType.BCC,
			 * InternetAddress.parse(bcc, false)); }
			 */
			msg.setSubject(subject, "big5");
			msg.setContent(body.toString(), "text/html; charset=utf-8");
			// msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			// System.out.println("exception here =" + e.getMessage());
			throw new RuntimeException("error.unexpected");
		}
	}
}
