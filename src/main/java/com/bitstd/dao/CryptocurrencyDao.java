package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bitstd.model.AvgInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class CryptocurrencyDao {

	/*
	 * BitSTD Index History Data
	 */
	
	public void insertToBitCurrIndex(Connection conn, AvgInfoBean bean) throws SQLException {
		String sql = "insert into STD_INDEX_PRICEHIS (ID,ASSETCODE,CURRPICE,CIRCULATION,MONEYTYPE) values(STD_INDEXHIS_SEQUENCE.nextval,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getBittype());
			insertstatement.setDouble(2, bean.getUsdprice());
			insertstatement.setDouble(3, bean.getTotalSupply());
			insertstatement.setString(4, bean.getCurrency());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private double isExistBitSTDIndexLast(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select CURRPRICE from STD_INDEX_LASTPRICE where ASSETCODE = ?";
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

	private void insertToBitCurrIndexLast(Connection conn, AvgInfoBean bean, double prefutures) throws SQLException {
		String sql = "insert into STD_INDEX_LASTPRICE (ID,ASSETCODE,CURRPRICE,PREPRICE,MONEYTYPE) values(STD_INDEX_SEQUENCE.nextval,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getBittype());
			insertstatement.setDouble(2, bean.getUsdprice());
			insertstatement.setDouble(3, prefutures);
			insertstatement.setString(4, bean.getCurrency());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitCurrIndexLast(Connection conn, AvgInfoBean bean, double prefutures) throws SQLException {
		String sql = "update STD_INDEX_LASTPRICE set CURRPRICE=?,PREPRICE=?,UPDATETIME=sysdate where ASSETCODE=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, bean.getUsdprice());
			ps.setDouble(2, prefutures);
			ps.setString(3, bean.getBittype());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitSTDIndexLast(Connection conn, AvgInfoBean bean) {
		try {
			double preindexval = isExistBitSTDIndexLast(conn, bean.getBittype());
			if (preindexval == 0) {
				insertToBitCurrIndexLast(conn, bean, preindexval);
			} else {
				updateBitCurrIndexLast(conn, bean, preindexval);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * BitSTD Futures Index History Data
	 */

	public void insertToBitCurrFuturesHis(Connection conn, AvgInfoBean bean) throws SQLException {
		String sql = "insert into STD_FUTURES_PRICEHIS (ID,ASSETCODE,CURRPICE,CIRCULATION,MONEYTYPE) values(STD_INDEXHIS_SEQUENCE.nextval,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getBittype());
			insertstatement.setDouble(2, bean.getUsdprice());
			insertstatement.setDouble(3, bean.getTotalSupply());
			insertstatement.setString(4, bean.getCurrency());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private double isExistBitSTDFuturesLast(Connection conn, String type) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select CURRPRICE from STD_FUTURES_LASTPRICE where ASSETCODE = ?";
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

	private void insertToBitCurrFuturesLast(Connection conn, AvgInfoBean bean, double prefutures) throws SQLException {
		String sql = "insert into STD_FUTURES_LASTPRICE (ID,ASSETCODE,CURRPRICE,PREPRICE,MONEYTYPE) values(STD_INDEX_SEQUENCE.nextval,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getBittype());
			insertstatement.setDouble(2, bean.getUsdprice());
			insertstatement.setDouble(3, prefutures);
			insertstatement.setString(4, bean.getCurrency());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitCurrFuturesLast(Connection conn, AvgInfoBean bean, double prefutures) throws SQLException {
		String sql = "update STD_FUTURES_LASTPRICE set CURRPRICE=?,PREPRICE=?,UPDATETIME=sysdate where ASSETCODE=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, bean.getUsdprice());
			ps.setDouble(2, prefutures);
			ps.setString(3, bean.getBittype());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitSTDFuturesLast(Connection conn, AvgInfoBean bean) {
		try {
			double preindexval = isExistBitSTDFuturesLast(conn, bean.getBittype());
			if (preindexval == 0) {
				insertToBitCurrFuturesLast(conn, bean, preindexval);
			} else {
				updateBitCurrFuturesLast(conn, bean, preindexval);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
