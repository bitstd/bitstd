package com.bitstd.task;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.bitstd.dao.CryptocurrencyDao;
import com.bitstd.dao.DBConnection;
import com.bitstd.model.CryptoInfoBean;
import com.bitstd.service.impl.CryptoServiceImpl;
import com.bitstd.service.impl.SupplyServiceImpl;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/9/18
 */

public class CryptoCurrencyTask {
	private CryptoServiceImpl crypto = new CryptoServiceImpl();
	private SupplyServiceImpl supply = new SupplyServiceImpl();
	private List<String> listings = new ArrayList<String>();

	public CryptoCurrencyTask() {
		listings = supply.getSupplyListings();
	}

	public void doCryptoCurrencyTask() {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		CryptocurrencyDao cryptdao = new CryptocurrencyDao();

		while (true) {
			List<CryptoInfoBean> beans = crypto.getCryptoInfo(listings);
			if (beans != null && beans.size() > 0) {
				for (CryptoInfoBean bean : beans) {
					try {
						bean.CryptoInfoBeanToString();
						cryptdao.insertToBitCurrIndex(conn, bean);
						cryptdao.doExecuteBitCurrIndexLast(conn, bean);
					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}
			}
			try {
				Thread.sleep(1000 * 50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		CryptoCurrencyTask crypto = new CryptoCurrencyTask();
		crypto.doCryptoCurrencyTask();
	}
}
