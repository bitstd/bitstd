package com.bitstd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bitstd.model.InfosBean;

public class InformationDao {

	private double isExistInformation(Connection conn, String source) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select id from std_news where CONTENTLINK = ?";
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, source);
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

	public boolean insertToInformation(Connection conn, InfosBean bean) throws SQLException {
		if (isExistInformation(conn, bean.getSource()) != 0)
			return false;
		String sql = "insert into std_news (ID,ISEN,TYPEID,AUTHOR,TITLE,PICTURE,CONTENT,CONTENTLINK,SYNOPSIS) values(BITSTD_SEQUENCE.Nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement insertstatement = null;
		try {
			insertstatement = conn.prepareStatement(sql);
			insertstatement.setString(1, bean.getIsEn());
			insertstatement.setInt(2, bean.getTypeid());
			insertstatement.setString(3, bean.getAuthor());
			insertstatement.setString(4, bean.getTitle());
			insertstatement.setString(5, bean.getPicture());
			insertstatement.setString(6, bean.getContent());
			insertstatement.setString(7, bean.getSource());
			insertstatement.setString(8, bean.getSynopsis());
			insertstatement.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnection.cleanUp(null, null, insertstatement, null);
		}
		return true;
	}

}
