package com.bitstd.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class ConnectionManager {
	private static ConnectionManager instance;

	public ComboPooledDataSource dataSource;

	private static String c3p0Properties = "c3p0.properties";
	
	private String ip="";

	private ConnectionManager() throws Exception {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream infile = cl.getResourceAsStream(c3p0Properties);
		Properties p = new Properties();
		p.load(infile);
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(p.getProperty("user"+ip));
		dataSource.setPassword(p.getProperty("password"+ip));
		dataSource.setJdbcUrl(p.getProperty("jdbcUrl"+ip));
		dataSource.setDriverClass(p.getProperty("driverClass"));
		dataSource.setInitialPoolSize(Integer.parseInt(p.getProperty("initialPoolSize")));
		dataSource.setMinPoolSize(Integer.parseInt(p.getProperty("minPoolSize")));
		dataSource.setMaxPoolSize(Integer.parseInt(p.getProperty("maxPoolSize")));
		dataSource.setMaxStatements(Integer.parseInt(p.getProperty("maxStatements")));
		dataSource.setCheckoutTimeout(Integer.parseInt(p.getProperty("checkoutTimeout")));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(p.getProperty("idleConnectionTestPeriod")));
		dataSource.setAcquireIncrement(Integer.parseInt(p.getProperty("acquireIncrement")));
		dataSource.setMaxIdleTime(Integer.parseInt(p.getProperty("maxIdleTime")));
	}

	public static final ConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectionManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public synchronized final Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void finalize() throws Throwable {
		DataSources.destroy(dataSource);
		super.finalize();
	}

	public static void main(String[] args) {
		Connection conn = ConnectionManager.getInstance().getConnection();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
}
