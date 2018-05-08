package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import com.bitstd.dao.aggregation.AggregationImpl;

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
	
	private double isExistBitSTDFuturesIndexHisTimeID(Connection conn, String tablename, long timeid, String type)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select INDEXVAL from " + tablename + " where TYPE = ? AND TIMEID =?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, type);
			ps.setLong(2, timeid);
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

	public void insertToBitSTDFuturesIndexHisAggregation(Connection conn, double bitstdfuturesindex, String type)
			throws SQLException {
		Map<String, Long> Aggregations = AggregationImpl.aggregationImpl(1);
		for (Map.Entry<String, Long> entry : Aggregations.entrySet()) {
			String tablename = entry.getKey();
			long times = entry.getValue();
			if (isExistBitSTDFuturesIndexHisTimeID(conn, tablename, times, type) == 0) {
				String sql = "insert into " + tablename + " (TIMEID,TYPE,THISTIME,INDEXVAL) values(?,?,?,?)";
				PreparedStatement insertstatement = null;
				try {
					insertstatement = conn.prepareStatement(sql);
					insertstatement.setLong(1, times);
					insertstatement.setString(2, type);
					insertstatement.setTimestamp(3, new Timestamp(times));
					insertstatement.setDouble(4, bitstdfuturesindex);
					insertstatement.execute();
				} catch (SQLException ex) {
					throw ex;
				} finally {
					DBConnection.cleanUp(null, null, insertstatement, null);
				}
			} else {
				String sql = "update " + tablename + " set INDEXVAL=? where type=? and TIMEID=?";
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(sql);
					ps.setDouble(1, bitstdfuturesindex);
					ps.setString(2, type);
					ps.setDouble(3, times);
					ps.executeUpdate();
				} catch (SQLException ex) {
					throw ex;
				} finally {
					DBConnection.cleanUp(null, null, ps, null);
				}
			}
		}

	}


}
