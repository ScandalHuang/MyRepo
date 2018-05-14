package mro.quartz.job.sapJob.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import mro.quartz.job.sapJob.ProductionInterface;
import oracle.jdbc.driver.OracleDriver;

public class Ora implements ProductionInterface {

	private final static ResourceBundle myResources = ResourceBundle.getBundle("mro.quartz.job.sapJob.properties.oracle");
	private final static String url = myResources.getString(production?"p.ora.url":"s.ora.url");
	
	private static Connection conn = null;
	
	private Ora(){}
	
	static {
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static Connection getConn() throws SQLException {
		if (conn == null){
            synchronized(Ora.class){
                if(conn == null) { 
                	conn = DriverManager.getConnection(url);
                }
            }
        }
        return conn;
	}

	public static void disconnectOraDatabase() throws SQLException {
		if(conn!=null){
			conn.close();
			conn = null;
		}
	}

}
