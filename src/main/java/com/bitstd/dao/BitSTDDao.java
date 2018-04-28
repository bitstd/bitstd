package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class BitSTDDao {

	private boolean isExistBitSTDIndex(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select INDEXVAL from STD_CURR_INDEX where TYPE = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else
				return false;

		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, rs);
		}
	}

	private void insertToBitSTDIndex(Connection conn, double bitstdindex, String type) throws SQLException {
		String sql = "insert into STD_CURR_INDEX (ID,INDEXVAL,TYPE) values(STD_INDEX_SEQUENCE.nextval,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setDouble(1, bitstdindex);
			insertstatement.setString(2, type);
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitSTDIndex(Connection conn, double bitstdindex, String type) throws SQLException {
		String sql = "update STD_CURR_INDEX set INDEXVAL=?,UPDATETIME=sysdate where type=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, bitstdindex);
			ps.setString(2, type);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitSTDIndex(Connection conn, double bitstdindex, String type) {
		try {
			if (isExistBitSTDIndex(conn, type)) {
				updateBitSTDIndex(conn, bitstdindex, type);
			} else {
				insertToBitSTDIndex(conn, bitstdindex, type);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void insertToBitSTDIndexHis(Connection conn, double bitstdindex, String type) throws SQLException {
		String sql = "insert into std_indexhis (ID,INDEXVAL,TYPE) values(STD_INDEXHIS_SEQUENCE.nextval,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setDouble(1, bitstdindex);
			insertstatement.setString(2, type);
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

}
