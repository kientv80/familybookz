package com.vnsoftware.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.vnsoftware.util.ConfigurationHelper;

public class JDBCConnection {
//	private static ComboPooledDataSource connectionPool = new ComboPooledDataSource();
	private static JDBCConnection instance;
	Logger log = Logger.getLogger(JDBCConnection.class);
	public static JDBCConnection getInstance(){
		if(instance == null)
			instance = new JDBCConnection();
		return instance;
	}
	private JDBCConnection() {
//		try {
//			connectionPool.setDriverClass("com.mysql.jdbc.Driver");
//			connectionPool.setJdbcUrl(ConfigurationHelper.getInstance().getValue("mysqlDB"));
//			connectionPool.setUser(ConfigurationHelper.getInstance().getValue("dbuser"));
//			connectionPool.setPassword(ConfigurationHelper.getInstance().getValue("dbpassword"));
//			Properties p = new Properties();
//			p.setProperty("useUnicode", "true");
//			p.setProperty("characterEncoding", "utf-8");
//			p.setProperty("user", ConfigurationHelper.getInstance().getValue("dbuser"));
//			p.setProperty("password", ConfigurationHelper.getInstance().getValue("dbpassword"));
//			p.setProperty("validationQuery","/* ping */ SELECT 1");
//			p.setProperty("testWhileIdle","true");
//			connectionPool.setProperties(p);
//			connectionPool.setMaxPoolSize(50);
//			connectionPool.setInitialPoolSize(10);
//			connectionPool.setMaxIdleTime(50);
//			connectionPool.setMaxStatementsPerConnection(10);
//		} catch (PropertyVetoException e) {
//			log.error("", e);
//		} 
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties p = new Properties();
			p.setProperty("useUnicode", "true");
			p.setProperty("autoReconnect", "true");
			p.setProperty("characterEncoding", "utf-8");
			p.setProperty("user", ConfigurationHelper.getInstance().getValue("dbuser"));
			p.setProperty("password", ConfigurationHelper.getInstance().getValue("dbpassword"));
			conn = DriverManager.getConnection(ConfigurationHelper.getInstance().getValue("mysqlDB"), p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
/*		try {
			Connection conn =  connectionPool.getConnection();
			return conn;
		} catch (SQLException e) {
			log.error("", e);
		}
		return null;*/
	}
}
