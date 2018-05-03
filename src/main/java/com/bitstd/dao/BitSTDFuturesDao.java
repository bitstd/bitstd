package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitSTDFuturesDao {

	private double isExistBitSTDFuturesIndex(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select INDEXVAL from STD_CURR_FUTURES where TYPE = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			} else
				return 0;

		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, rs);
		}
	}

	private void insertToBitSTDFuturesIndex(Connection conn, double futuresindex, double prefuturesindex, String type)
			throws SQLException {
		String sql = "insert into STD_CURR_FUTURES (ID,INDEXVAL,PREINDEXVAL,TYPE) values(STD_INDEX_SEQUENCE.nextval,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setDouble(1, futuresindex);
			insertstatement.setDouble(2, prefuturesindex);
			insertstatement.setString(3, type);
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitSTDFuturesIndex(Connection conn, double futuresindex, double prefuturesindex, String type)
			throws SQLException {
		String sql = "update STD_CURR_FUTURES set INDEXVAL=?,PREINDEXVAL=?,UPDATETIME=sysdate where type=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, futuresindex);
			ps.setDouble(2, prefuturesindex);
			ps.setString(3, type);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitSTDFuturesIndex(Connection conn, double futuresindex, String type) {
		try {
			double preindexval = isExistBitSTDFuturesIndex(conn, type);
			System.out.println("preindexval : " + preindexval);
			if (preindexval == 0) {
				insertToBitSTDFuturesIndex(conn, futuresindex, preindexval, type);
			} else {
				updateBitSTDFuturesIndex(conn, futuresindex, preindexval, type);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void insertToBitSTDFuturesIndexHis(Connection conn, double futuresindex, String type) throws SQLException {
		String sql = "insert into STD_FUTURESHIS (ID,INDEXVAL,TYPE) values(STD_INDEXHIS_SEQUENCE.nextval,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setDouble(1, futuresindex);
			insertstatement.setString(2, type);
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

}
