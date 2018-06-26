package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitstd.model.AttrBean;
import com.bitstd.model.CoinDetailBean;
import com.bitstd.model.MarketBean;
import com.bitstd.model.QuotationBean;

public class QuotationDao {
	private long isExistQuotation(Connection conn, String coinid) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select id from STD_AICOIN_PRICE where COINID = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, coinid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			} else
				return 0;

		} catch (SQLException ex) {
			throw ex;
		} finally {

			DBConnection.cleanUp(null, null, ps, rs);
		}
	}

	private boolean isExistCoinDetail(Connection conn, long qid) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select COINID from STD_AICOIN_DESC where ID = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setLong(1, qid);
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

	private boolean isExistGlobalMaket(Connection conn, long qid, MarketBean bean) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from STD_AICOIN_GLOBAL where PARENTID = ? and COINID=? and CURRENCYPAIR = ? and MARKET = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setLong(1, qid);
			ps.setString(2, bean.getCoinID());
			ps.setString(3, bean.getCurrencyPair());
			ps.setString(4, bean.getMarket());
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

	public List<AttrBean> getListKeys(Connection conn) throws SQLException {
		List<AttrBean> beans = new ArrayList<AttrBean>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ID,COINID from STD_AICOIN_PRICE order by id asc";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next()) {
				AttrBean bean = new AttrBean();
				bean.id = rs.getLong(1);
				bean.key = rs.getString(2);
				beans.add(bean);
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, ps, rs);
		}
		return beans;
	}

	public boolean insertToQuotation(Connection conn, QuotationBean bean) throws SQLException {
		long qid = isExistQuotation(conn, bean.getCoinID());
		if (qid != 0) {
			String sql = "update STD_AICOIN_PRICE set MARKETCAPUSD=?,MARKETCAPRMB=?,TURNOVERUSD=?,TURNOVERRMB=?,VOLUME=?,LIQUIDITY=?,PRICEUSD=?,PRICERMB=?,CHANGEPERCENT=?,UPDATETIME=sysdate where COINID=? and id =?";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(sql);
				ps.setBigDecimal(1, bean.getMarketCapUSD());
				ps.setBigDecimal(2, bean.getMarketCapRMB());
				ps.setBigDecimal(3, bean.getTurnover24hUSD());
				ps.setBigDecimal(4, bean.getTurnover24hRMB());
				ps.setBigDecimal(5, bean.getVolume24());
				ps.setBigDecimal(6, bean.getLiquidity());
				ps.setBigDecimal(7, bean.getPriceUSD());
				ps.setBigDecimal(8, bean.getPriceRMB());
				ps.setBigDecimal(9, bean.getIncreasePercent());
				ps.setString(10, bean.getCoinID());
				ps.setLong(11, qid);
				ps.executeUpdate();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				DBConnection.cleanUp(null, null, ps, null);
			}
		} else {
			String sql = "insert into STD_AICOIN_PRICE (ID,COINID,SHORTNAME,ENNAME,CNNAME,MARKETCAPUSD,MARKETCAPRMB,TURNOVERUSD,TURNOVERRMB,VOLUME,LIQUIDITY,PRICEUSD,PRICERMB,CHANGEPERCENT) values(STD_AICOIN_SEQUENCE.Nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insertstatement = null;
			try {
				insertstatement = conn.prepareStatement(sql);
				insertstatement.setString(1, bean.getCoinID());
				insertstatement.setString(2, bean.getShortName());
				insertstatement.setString(3, bean.getEnglishName());
				insertstatement.setString(4, bean.getChineseName());
				insertstatement.setBigDecimal(5, bean.getMarketCapUSD());
				insertstatement.setBigDecimal(6, bean.getMarketCapRMB());
				insertstatement.setBigDecimal(7, bean.getTurnover24hUSD());
				insertstatement.setBigDecimal(8, bean.getTurnover24hRMB());
				insertstatement.setBigDecimal(9, bean.getVolume24());
				insertstatement.setBigDecimal(10, bean.getLiquidity());
				insertstatement.setBigDecimal(11, bean.getPriceUSD());
				insertstatement.setBigDecimal(12, bean.getPriceRMB());
				insertstatement.setBigDecimal(13, bean.getIncreasePercent());
				insertstatement.execute();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				DBConnection.cleanUp(null, null, insertstatement, null);
			}
		}
		return true;
	}

	public boolean insertToCionDetail(Connection conn, long qid, CoinDetailBean bean) throws SQLException {
		if (qid != 0) {
			if (isExistCoinDetail(conn, qid)) {
				String sql = "update STD_AICOIN_DESC set PUBLISHTIME=?,PUBLISHPRICE=?,PUBLISHAMOUNT=?,ALGORITHM=?,DESCRIPTE=?,LINKMAP=?,TEAMMAP=?,DEVELOPMAP=? where id =?";
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(sql);
					ps.setLong(1, bean.getPublishTime());
					ps.setString(2, bean.getPublish_price());
					ps.setBigDecimal(3, bean.getPublish_amount());
					ps.setString(4, bean.getAlgorithm());
					ps.setString(5, bean.getDescribe());
					ps.setString(6, bean.getLinkMap());
					ps.setString(7, bean.getTeamMap());
					ps.setString(8, bean.getDevelopMap());
					ps.setLong(9, qid);
					ps.executeUpdate();
				} catch (SQLException ex) {
					throw ex;
				} finally {
					DBConnection.cleanUp(null, null, ps, null);
				}
			} else {
				String sql = "insert into STD_AICOIN_DESC (ID,COINID,PUBLISHTIME,PUBLISHPRICE,PUBLISHAMOUNT,ALGORITHM,DESCRIPTE,LINKMAP,TEAMMAP,DEVELOPMAP) values(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement insertstatement = null;
				try {
					insertstatement = conn.prepareStatement(sql);
					insertstatement.setLong(1, qid);
					insertstatement.setString(2, bean.getCoinID());
					insertstatement.setLong(3, bean.getPublishTime());
					insertstatement.setString(4, bean.getPublish_price());
					insertstatement.setBigDecimal(5, bean.getPublish_amount());
					insertstatement.setString(6, bean.getAlgorithm());
					insertstatement.setString(7, bean.getDescribe());
					insertstatement.setString(8, bean.getLinkMap());
					insertstatement.setString(9, bean.getTeamMap());
					insertstatement.setString(10, bean.getDevelopMap());
					insertstatement.execute();
				} catch (SQLException ex) {
					throw ex;
				} finally {
					DBConnection.cleanUp(null, null, insertstatement, null);
				}
			}
		}

		return true;
	}

	public boolean insertToCoinGlobal(Connection conn, long qid, MarketBean bean) throws SQLException {
		if (isExistGlobalMaket(conn, qid, bean)) {
			String sql = "update STD_AICOIN_GLOBAL set PRICEUSD=?,PRICERMB=?,CHANGEPERCENT=?,VOLUME=?,TURNOVERRMB=?,INCOME=?,OUTCOME=?,UPDATETIME=sysdate,price=?,turnoverusd=?,incomeusd=?,outcomeusd=? where PARENTID = ? and COINID=? and CURRENCYPAIR = ? and MARKET = ?";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(sql);
				ps.setBigDecimal(1, bean.getPriceUSD());
				ps.setBigDecimal(2, bean.getPriceRMB());
				ps.setBigDecimal(3, bean.getDegree24h());
				ps.setBigDecimal(4, bean.getVol24h());
				ps.setBigDecimal(5, bean.getTrade24h());
				ps.setBigDecimal(6, bean.getNetInflow());
				ps.setBigDecimal(7, bean.getMFNInflow());
				ps.setBigDecimal(8, bean.getPrice());
				ps.setBigDecimal(9, bean.getTrade24hUSD());
				ps.setBigDecimal(10, bean.getNetInflowUSD());
				ps.setBigDecimal(11, bean.getMFNInflowUSD());
				ps.setLong(12, qid);
				ps.setString(13, bean.getCoinID());
				ps.setString(14, bean.getCurrencyPair());
				ps.setString(15, bean.getMarket());
				ps.executeUpdate();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				DBConnection.cleanUp(null, null, ps, null);
			}
		} else {
			String sql = "insert into STD_AICOIN_GLOBAL (PARENTID,COINID,SHORTNAME,MARKET,MARKETNAME,SYMBOL,CURRENCYPAIR,PRICEUSD,PRICERMB,CHANGEPERCENT,VOLUME,TURNOVERRMB,INCOME,OUTCOME,price,turnoverusd,incomeusd,outcomeusd) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insertstatement = null;
			try {
				insertstatement = conn.prepareStatement(sql);
				insertstatement.setLong(1, qid);
				insertstatement.setString(2, bean.getCoinID());
				insertstatement.setString(3, bean.getShortName());
				insertstatement.setString(4, bean.getMarket());
				insertstatement.setString(5, bean.getMarketName());
				insertstatement.setString(6, bean.getSymbol());
				insertstatement.setString(7, bean.getCurrencyPair());
				insertstatement.setBigDecimal(8, bean.getPriceUSD());
				insertstatement.setBigDecimal(9, bean.getPriceRMB());
				insertstatement.setBigDecimal(10, bean.getDegree24h());
				insertstatement.setBigDecimal(11, bean.getVol24h());
				insertstatement.setBigDecimal(12, bean.getTrade24h());
				insertstatement.setBigDecimal(13, bean.getNetInflow());
				insertstatement.setBigDecimal(14, bean.getMFNInflow());
				insertstatement.setBigDecimal(15, bean.getPrice());
				insertstatement.setBigDecimal(16, bean.getTrade24hUSD());
				insertstatement.setBigDecimal(17, bean.getNetInflowUSD());
				insertstatement.setBigDecimal(18, bean.getMFNInflowUSD());
				insertstatement.execute();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				DBConnection.cleanUp(null, null, insertstatement, null);
			}
		}

		return true;
	}

}
