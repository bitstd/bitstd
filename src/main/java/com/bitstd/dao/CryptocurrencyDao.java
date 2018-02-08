package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bitstd.model.AvgInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class CryptocurrencyDao {

	public void insertToBitCurrIndex(Connection conn, AvgInfoBean bean) throws SQLException {
		String sql = "insert into STD_INDEX_PRICEHIS (ID,ASSETCODE,CURRPICE,CIRCULATION,MONEYTYPE) values(STD_SEQUENCE.nextval,?,?,?,?)";
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
}
