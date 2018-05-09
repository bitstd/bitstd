package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.CryptoInfoBean;

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

	public void insertToBitCurrIndex(Connection conn, CryptoInfoBean bean) throws SQLException {
		String sql = "insert into STD_INDEX_PRICEHIS (ID,ASSETCODE,CURRPICE,CIRCULATION,MONEYTYPE,TOBTCPRICE) values(STD_INDEXHIS_SEQUENCE.nextval,?,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getAssetCode());
			insertstatement.setBigDecimal(2, bean.getUsdPrice());
			insertstatement.setBigDecimal(3, bean.getCirculatingSupply());
			insertstatement.setString(4, bean.getCurrencyType());
			insertstatement.setBigDecimal(5, bean.getToBtcPrice());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private double isExistBitCurrIndexLast(Connection conn, String type) throws SQLException {
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

	private void insertToBitCurrIndexLast(Connection conn, CryptoInfoBean bean, double preCurrprice)
			throws SQLException {
		String sql = "insert into STD_INDEX_LASTPRICE (ID,ASSETCODE,CURRPRICE,PREPRICE,MONEYTYPE,TOBTCPRICE,RANK,VOLUME_DAY,MARKETCAP,CHANGEHOUR,CHANGEDAY,CHANGEWEEK,CIRCULATION) values(STD_INDEX_SEQUENCE.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getAssetCode());
			insertstatement.setBigDecimal(2, bean.getUsdPrice());
			insertstatement.setDouble(3, preCurrprice);
			insertstatement.setString(4, bean.getCurrencyType());
			insertstatement.setBigDecimal(5, bean.getToBtcPrice());
			insertstatement.setInt(6, bean.getRank());
			insertstatement.setBigDecimal(7, bean.getVolume_24h());
			insertstatement.setBigDecimal(8, bean.getMarket_cap());
			insertstatement.setDouble(9, bean.getPercent_change_1h());
			insertstatement.setDouble(10, bean.getPercent_change_24h());
			insertstatement.setDouble(11, bean.getPercent_change_7d());
			insertstatement.setBigDecimal(12, bean.getCirculatingSupply());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
	}

	private void updateBitCurrIndexLast(Connection conn, CryptoInfoBean bean, double preCurrprice) throws SQLException {
		String sql = "update STD_INDEX_LASTPRICE set CURRPRICE=?,PREPRICE=?,TOBTCPRICE=?,RANK=?,VOLUME_DAY=?,MARKETCAP=?,CIRCULATION=?,CHANGEHOUR=?,CHANGEDAY=?,CHANGEWEEK=?,UPDATETIME=sysdate where ASSETCODE=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setBigDecimal(1, bean.getUsdPrice());
			ps.setDouble(2, preCurrprice);
			ps.setBigDecimal(3, bean.getToBtcPrice());
			ps.setInt(4, bean.getRank());
			ps.setBigDecimal(5, bean.getVolume_24h());
			ps.setBigDecimal(6, bean.getMarket_cap());
			ps.setBigDecimal(7, bean.getCirculatingSupply());
			ps.setDouble(8, bean.getPercent_change_1h());
			ps.setDouble(9, bean.getPercent_change_24h());
			ps.setDouble(10, bean.getPercent_change_7d());
			ps.setString(11, bean.getAssetCode());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, null);
		}
	}

	public void doExecuteBitCurrIndexLast(Connection conn, CryptoInfoBean bean) {
		try {
			double preCurrprice = isExistBitCurrIndexLast(conn, bean.getAssetCode());
			if (preCurrprice == 0) {
				insertToBitCurrIndexLast(conn, bean, preCurrprice);
			} else {
				updateBitCurrIndexLast(conn, bean, preCurrprice);
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
