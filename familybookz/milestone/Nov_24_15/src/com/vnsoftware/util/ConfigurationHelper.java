package com.vnsoftware.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.vnsoftware.db.JDBCConnection;

public class ConfigurationHelper {
	private static ConfigurationHelper instance;
	Logger log = Logger.getLogger(ConfigurationHelper.class);
	private Properties config = null;

	public static ConfigurationHelper getInstance() {
		if (instance == null)
			instance = new ConfigurationHelper();

		return instance;
	}

	private ConfigurationHelper() {
		config = new Properties();
		try {
			config.load(new FileInputStream("production.properties"));
		} catch (FileNotFoundException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	public String getValue(String key) {
		return config.getProperty(key);
	}

	public static void setDBValue(String key, String value) {
		try {
			Connection conn = JDBCConnection.getInstance().getConnection();
			Statement stm = conn.createStatement();
			int num = stm.executeUpdate("update keyvalue set value_='" + value + "' where key_='" + key + "'");
			if (num == 0) {
				stm.execute("insert into keyvalue(key_,value_) values ('" + key + "','" + value + "')");
			}
			stm.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDBValue(String key) {
		String value = "";
		try {
			
			Connection conn = JDBCConnection.getInstance().getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from keyvalue where key_='"+key+"'");
			while(rs.next()){
				value = rs.getString("value_");
			}
			stm.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
