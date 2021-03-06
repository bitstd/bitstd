package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import com.bitstd.dao.aggregation.AggregationImpl;
import com.bitstd.model.IndexBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/3/18
 */

public class IndexMarketDao {

	private void insertToIndexMarket(Connection conn, IndexBean indexbean, double preindexval) throws SQLException {
		String sql = "insert into STD_CURR_SPOT (ID,TYPE,INDEXVAL,PREINDEXVAL,CHANGEVAL,CHANGEINDEXVAL,OPENVAL,HIGHVAL,LOWVAL,HIGHVALHIS,LOWVALHIS,CLOSEVAL,TIMES) values(STD_INDEX_SEQUENCE.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, indexbean.getType());
			insertstatement.setDouble(2, Double.parseDouble(indexbean.getCurrentIndex()));
			insertstatement.setDouble(3, preindexval);
			insertstatement.setDouble(4, Double.parseDouble(indexbean.getRiseAndfall()));
			insertstatement.setDouble(5, Double.parseDouble(indexbean.getRisefallIndex()));
			insertstatement.setDouble(6, Double.parseDouble(indexbean.getOpeningIndex()));
			insertstatement.setDouble(7, Double.parseDouble(indexbean.getHighIndex()));
			insertstatement.setDouble(8, Double.parseDouble(indexbean.getLowIndex()));
			insertstatement.setDouble(9, Double.parseDouble(indexbean.getWeekHighIndex()));
			insertstatement.setDouble(10, Double.parseDouble(indexbean.getWeeklowIndex()));
			insertstatement.setDouble(11, Double.parseDouble(indexbean.getClosingIndex()));
			insertstatement.setString(12, indexbean.getTime());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateIndexMarket(Connection conn, IndexBean indexbean, double preindexval) throws SQLException {
		String sql = "update STD_CURR_SPOT set INDEXVAL=?,PREINDEXVAL=?,CHANGEVAL=?,CHANGEINDEXVAL=?,OPENVAL=?,HIGHVAL=?,LOWVAL=?,HIGHVALHIS=?,LOWVALHIS=?,CLOSEVAL=?,TIMES=?,UPDATETIME=sysdate where type=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, Double.parseDouble(indexbean.getCurrentIndex()));
			ps.setDouble(2, preindexval);
			ps.setDouble(3, Double.parseDouble(indexbean.getRiseAndfall()));
			ps.setDouble(4, Double.parseDouble(indexbean.getRisefallIndex()));
			ps.setDouble(5, Double.parseDouble(indexbean.getOpeningIndex()));
			ps.setDouble(6, Double.parseDouble(indexbean.getHighIndex()));
			ps.setDouble(7, Double.parseDouble(indexbean.getLowIndex()));
			ps.setDouble(8, Double.parseDouble(indexbean.getWeekHighIndex()));
			ps.setDouble(9, Double.parseDouble(indexbean.getWeeklowIndex()));
			ps.setDouble(10, Double.parseDouble(indexbean.getClosingIndex()));
			ps.setString(11, indexbean.getTime());
			ps.setString(12, indexbean.getType());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	private double isExistIndexMarket(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select INDEXVAL from STD_CURR_SPOT where TYPE = ?";
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
	
	/*
	 * query index market time
	 */
	
	public String queryIndexMarketTime(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select TIMES from STD_CURR_SPOT where TYPE = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else
				return null;

		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, rs);
		}
	}

	/*
	 * insert index market data
	 */
	public void doExecuteIndexMarket(Connection conn, IndexBean indexbean) {
		try {
			double preindexval = isExistIndexMarket(conn, indexbean.getType());
			if (preindexval == 0) {
				insertToIndexMarket(conn, indexbean, preindexval);
			} else {
				updateIndexMarket(conn, indexbean, preindexval);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * insert index market history data
	 */
	public void insertToIndexMarketHis(Connection conn, IndexBean indexbean) throws SQLException {
		String sql = "insert into STD_SPOT_INDEXHIS (ID,TYPE,INDEXVAL,CHANGEVAL,CHANGEINDEXVAL,OPENVAL,HIGHVAL,LOWVAL,HIGHVALHIS,LOWVALHIS,CLOSEVAL,TIMES) values(STD_INDEXHIS_SEQUENCE.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, indexbean.getType());
			insertstatement.setDouble(2, Double.parseDouble(indexbean.getCurrentIndex()));
			insertstatement.setDouble(3, Double.parseDouble(indexbean.getRiseAndfall()));
			insertstatement.setDouble(4, Double.parseDouble(indexbean.getRisefallIndex()));
			insertstatement.setDouble(5, Double.parseDouble(indexbean.getOpeningIndex()));
			insertstatement.setDouble(6, Double.parseDouble(indexbean.getHighIndex()));
			insertstatement.setDouble(7, Double.parseDouble(indexbean.getLowIndex()));
			insertstatement.setDouble(8, Double.parseDouble(indexbean.getWeekHighIndex()));
			insertstatement.setDouble(9, Double.parseDouble(indexbean.getWeeklowIndex()));
			insertstatement.setDouble(10, Double.parseDouble(indexbean.getClosingIndex()));
			insertstatement.setString(11, indexbean.getTime());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}
	
	private double isExistIndexMarkeHisTimeID(Connection conn, String tablename, long timeid, String type)
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

	public void insertToIndexMarkeAggregation(Connection conn, IndexBean indexbean)
			throws SQLException {
		Map<String, Long> Aggregations = AggregationImpl.aggregationImpl(2);
		for (Map.Entry<String, Long> entry : Aggregations.entrySet()) {
			String tablename = entry.getKey();
			long times = entry.getValue();
			if (isExistIndexMarkeHisTimeID(conn, tablename, times, indexbean.getType()) == 0) {
				String sql = "insert into " + tablename + " (TIMEID,TYPE,THISTIME,INDEXVAL) values(?,?,?,?)";
				PreparedStatement insertstatement = null;
				try {
					insertstatement = conn.prepareStatement(sql);
					insertstatement.setLong(1, times);
					insertstatement.setString(2, indexbean.getType());
					insertstatement.setTimestamp(3, new Timestamp(times));
					insertstatement.setDouble(4, Double.parseDouble(indexbean.getCurrentIndex()));
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
					ps.setDouble(1, Double.parseDouble(indexbean.getCurrentIndex()));
					ps.setString(2, indexbean.getType());
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
