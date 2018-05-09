package com.bitstd.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bitstd.dao.DBConnection;
import com.bitstd.dao.IndexMarketDao;
import com.bitstd.model.IndexBean;
import com.bitstd.service.impl.IndexMarketServiceImpl;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/3/18
 */

public class IndexMarketTask {
	private IndexMarketServiceImpl indexMarketService = new IndexMarketServiceImpl();

	public void doIndexMarketTask() {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		while (true) {
			List<IndexBean> indexbeans = indexMarketService.getIndexMarket();
			if (indexbeans != null && indexbeans.size() > 0) {
				for (int i = 0; i < indexbeans.size(); i++) {
					IndexBean indexbean = indexbeans.get(i);
					IndexMarketDao indexMarketDao = new IndexMarketDao();
					String times = null;
					try {
						times = indexMarketDao.queryIndexMarketTime(conn, indexbean.getType());
						if (!indexbean.getTime().equalsIgnoreCase(times)) {
							indexMarketDao.doExecuteIndexMarket(conn, indexbean);
							indexMarketDao.insertToIndexMarketHis(conn, indexbean);
							indexMarketDao.insertToIndexMarkeAggregation(conn, indexbean);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		IndexMarketTask indexMarketTask = new IndexMarketTask();
		indexMarketTask.doIndexMarketTask();
	}
}
