package com.bitstd.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class DBConnection {

	private static String c3p0Properties = "c3p0.properties";

	public static Connection getConnection() {
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream infile = cl.getResourceAsStream(c3p0Properties);
		Properties p = new Properties();
		String userPool = "0";
		try {
			p.load(infile);
			userPool = p.getProperty("userPool");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if("1".equals(userPool)){
			return ConnectionManager.getInstance().getConnection();
		}else{
			Connection conn = null;
			try {
	
				Class.forName(p.getProperty("driverClass")).newInstance();
				conn = DriverManager.getConnection(p.getProperty("jdbcUrl"), p.getProperty("user"), p.getProperty("password"));
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return conn;
		}
		
	}	
	
	
	public static Connection getConnection(String ip) {
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream infile = cl.getResourceAsStream(c3p0Properties);
		Properties p = new Properties();
		String userPool = "0";
		try {
			p.load(infile);
			userPool = p.getProperty("userPool");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if("1".equals(userPool)){
			ConnectionManager m = ConnectionManager.getInstance();
			if(ip != null && ip.trim().length() > 0)
				m.setIp(ip);
			return m.getConnection();
		}else{
			Connection conn = null;
			try {
	
				Class.forName(p.getProperty("driverClass")).newInstance();
				conn = DriverManager.getConnection(p.getProperty("jdbcUrl"+ip), p.getProperty("user"+ip), p.getProperty("password"+ip));
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return conn;
		}
		
	}	
	
	public static void cleanUp(Connection conn, CallableStatement cs, Statement ps,
			ResultSet rs) {
		try {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} finally {
				try {
					if (ps != null) {
						ps.close();
						ps = null;
					}
				} finally {
					try {
						if (cs != null) {
							cs.close();
							cs = null;
						}
					} finally {
						if (conn != null) {
							conn.close();
							conn = null;
						}
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static long generateIdFromSequence(Connection conn){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT XTS_ORDER_SEQUENCE.NEXTVAL AS SEQNO FROM DUAL",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				return rs.getLong(1);
			}else
				return 0;

		}catch(SQLException ex){
			 ex.printStackTrace();
			 return 0;
		} finally {
			DBConnection.cleanUp(null, null, ps, rs);
		}
		
	}
	
}

