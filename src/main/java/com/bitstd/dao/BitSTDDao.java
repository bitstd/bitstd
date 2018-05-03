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

	private double isExistBitSTDIndex(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select INDEXVAL from STD_CURR_INDEX where TYPE = ?";
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

	private void insertToBitSTDIndex(Connection conn, double bitstdindex, double prebitstdindex, String type)
			throws SQLException {
		String sql = "insert into STD_CURR_INDEX (ID,INDEXVAL,PREINDEXVAL,TYPE) values(STD_INDEX_SEQUENCE.nextval,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setDouble(1, bitstdindex);
			insertstatement.setDouble(2, prebitstdindex);
			insertstatement.setString(3, type);
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitSTDIndex(Connection conn, double bitstdindex, double prebitstdindex, String type)
			throws SQLException {
		String sql = "update STD_CURR_INDEX set INDEXVAL=?,PREINDEXVAL=?,UPDATETIME=sysdate where type=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, bitstdindex);
			ps.setDouble(2, prebitstdindex);
			ps.setString(3, type);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitSTDIndex(Connection conn, double bitstdindex, String type) {
		try {
			double preindexval = isExistBitSTDIndex(conn, type);
			System.out.println("preindexval : " +preindexval);
			if (preindexval == 0) {
				insertToBitSTDIndex(conn, bitstdindex, preindexval, type);
			} else {
				updateBitSTDIndex(conn, bitstdindex, preindexval, type);
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
