package com.bitstd.task;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bitstd.dao.BitSTDDao;
import com.bitstd.dao.BitSTDFuturesDao;
import com.bitstd.dao.CryptocurrencyDao;
import com.bitstd.dao.DBConnection;
import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.ExInfoBean;
import com.bitstd.model.SupplyBean;
import com.bitstd.model.TradeParam;
import com.bitstd.service.impl.BitmexServiceImpl;
import com.bitstd.service.impl.OkexServiceImpl;
import com.bitstd.service.impl.SupplyServiceImpl;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 4/27/18 BitSTD Futures Index
 */
public class BitSTDFuturesTask {
	private OkexServiceImpl okexService = new OkexServiceImpl();
	private BitmexServiceImpl bitmexService = new BitmexServiceImpl();
	private SupplyServiceImpl supplyService = new SupplyServiceImpl();
	private List<SupplyBean> listSupply = new ArrayList<SupplyBean>();
	private Map<String,String> listings = new HashMap<String,String>();
	
	public BitSTDFuturesTask(){
		String[] symbols = { "BTC", "ETH", "XRP", "BCH", "LTC", "ADA", "EOS" };
		listings = supplyService.getSupplyListings(symbols);
	}

	private void getSupplyInfo() {
		listSupply = supplyService.getSupplyInfo(listings);
	}

	private AvgInfoBean getBITFuturesIndex(TradeParam trade) {
		AvgInfoBean bean = null;
		double usdprice = 0;
		ExInfoBean okex_this_week = okexService.getOkexFuturesIndex(trade.getOkexParam(), "this_week");
		ExInfoBean okex_next_week = okexService.getOkexFuturesIndex(trade.getOkexParam(), "next_week");
		ExInfoBean okex_quarter = okexService.getOkexFuturesIndex(trade.getOkexParam(), "quarter");
		ExInfoBean bitmex_m18 = bitmexService.getBitmexFuturesIndex(trade.getBitmexParam());
		double volumes = okex_this_week.getVolume() + okex_next_week.getVolume() + okex_quarter.getVolume()
				+ bitmex_m18.getVolume();
		if (volumes != 0) {
			usdprice = (okex_this_week.getVolume() * okex_this_week.getPrice()
					+ okex_next_week.getVolume() * okex_next_week.getPrice()
					+ okex_quarter.getVolume() * okex_quarter.getPrice()
					+ bitmex_m18.getVolume() * bitmex_m18.getPrice()) / volumes;
		}
		bean = new AvgInfoBean();
		bean.setBittype(trade.getBitType());
		bean.setUsdprice(usdprice);
		bean.setCurrency(trade.getCurrencyType());
		if (listSupply != null && listSupply.size() > 0) {
			for (int i = 0; i < listSupply.size(); i++) {
				SupplyBean sbean = listSupply.get(i);
				if (trade.getBitType().equalsIgnoreCase(sbean.getSymbol())) {
					bean.setTotalSupply(sbean.getTotal_supply());
					break;
				}
			}
		}
		return bean;
	}

	private AvgInfoBean getBTCFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("btc_usd");
		trade.setBitmexParam("XBTM18");
		trade.setBitType("BTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getLTCFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("ltc_usd");
		trade.setBitmexParam("LTCM18");
		trade.setBitType("LTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getBCHFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("bch_usd");
		trade.setBitmexParam("BCHM18");
		trade.setBitType("BCH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getETHFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("eth_usd");
		trade.setBitmexParam("ETHM18");
		trade.setBitType("ETH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getXRPFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("xrp_usd");
		trade.setBitmexParam("XRPM18");
		trade.setBitType("XRP");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getEOSFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("eos_usd");
		trade.setBitType("EOS");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getADAFuturesIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitmexParam("ADAM18");
		trade.setBitType("ADA");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITFuturesIndex(trade);
		return bean;
	}

	private AvgInfoBean getAvgInfoBean(Connection conn, AvgInfoBean bean) {
		AvgInfoBean infobean = bean;
		if (infobean.getUsdprice() == 0) {
			return infobean;
		}
		CryptocurrencyDao cryptdao = new CryptocurrencyDao();
		try {
			cryptdao.doExecuteBitSTDFuturesLast(conn, infobean);
			cryptdao.insertToBitCurrFuturesHis(conn, infobean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		double total_supply = 100000000;
		if (infobean.getTotalSupply() > total_supply) {
			BigDecimal defBigDecimal = new BigDecimal(total_supply);
			BigDecimal supplyBigDecimal = new BigDecimal(infobean.getTotalSupply());
			BigDecimal multiple = supplyBigDecimal.divide(defBigDecimal);
			BigDecimal priceBigDecimal = new BigDecimal(infobean.getUsdprice());
			BigDecimal npriceBigDecimal = priceBigDecimal.multiply(multiple);
			double newprice = npriceBigDecimal.doubleValue();
			infobean.setUsdprice(newprice);
			infobean.setTotalSupply(total_supply);
		}

		return infobean;
	}

	public void getBitSTDFuturesIndex() {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		while (true) {
			try {
				double bp = 0.4;
				getSupplyInfo();

				AvgInfoBean btcBean = null;
				AvgInfoBean ethBean = null;
				AvgInfoBean xrpBean = null;
				AvgInfoBean bchBean = null;
				AvgInfoBean ltcBean = null;
				AvgInfoBean adaBean = null;
				AvgInfoBean eosBean = null;

				int taskSize = 9;
				ExecutorService pool = Executors.newFixedThreadPool(taskSize);
				List<Future<AvgInfoBean>> list = new ArrayList<Future<AvgInfoBean>>();
				for (int i = 0; i < taskSize; i++) {
					Callable<AvgInfoBean> c = new BitSTDFuturesCallable(i, conn);
					Future<AvgInfoBean> f = pool.submit(c);
					list.add(f);
				}

				pool.shutdown();

				for (int i = 0; i < list.size(); i++) {
					switch (i) {
					case 0:
						btcBean = (AvgInfoBean) list.get(i).get();
						break;
					case 1:
						ethBean = (AvgInfoBean) list.get(i).get();
						break;
					case 2:
						xrpBean = (AvgInfoBean) list.get(i).get();
						break;
					case 3:
						bchBean = (AvgInfoBean) list.get(i).get();
						break;
					case 4:
						ltcBean = (AvgInfoBean) list.get(i).get();
						break;
					case 5:
						adaBean = (AvgInfoBean) list.get(i).get();
						break;
					case 6:
						eosBean = (AvgInfoBean) list.get(i).get();
						break;
					}
				}

				if (btcBean.getUsdprice() == 0 || ethBean.getUsdprice() == 0 || xrpBean.getUsdprice() == 0
						|| bchBean.getUsdprice() == 0 || ltcBean.getUsdprice() == 0 || adaBean.getUsdprice() == 0
						|| eosBean.getUsdprice() == 0) {
					continue;
				}

				BigDecimal btcBigDecimal = new BigDecimal(btcBean.getUsdprice())
						.multiply(new BigDecimal(btcBean.getTotalSupply()));
				BigDecimal ethBigDecimal = new BigDecimal(ethBean.getUsdprice())
						.multiply(new BigDecimal(ethBean.getTotalSupply()));
				BigDecimal xrpBigDecimal = new BigDecimal(xrpBean.getUsdprice())
						.multiply(new BigDecimal(xrpBean.getTotalSupply()));
				BigDecimal bchBigDecimal = new BigDecimal(bchBean.getUsdprice())
						.multiply(new BigDecimal(bchBean.getTotalSupply()));
				BigDecimal ltcBigDecimal = new BigDecimal(ltcBean.getUsdprice())
						.multiply(new BigDecimal(ltcBean.getTotalSupply()));
				BigDecimal adaBigDecimal = new BigDecimal(adaBean.getUsdprice())
						.multiply(new BigDecimal(adaBean.getTotalSupply()));
				BigDecimal eosBigDecimal = new BigDecimal(eosBean.getUsdprice())
						.multiply(new BigDecimal(eosBean.getTotalSupply()));

				BigDecimal numeratorBigDecimal = btcBigDecimal.add(ethBigDecimal).add(xrpBigDecimal).add(bchBigDecimal)
						.add(ltcBigDecimal).add(adaBigDecimal).add(eosBigDecimal);
				BigDecimal denominatorBigDecimal = new BigDecimal(btcBean.getTotalSupply())
						.add(new BigDecimal(ethBean.getTotalSupply())).add(new BigDecimal(xrpBean.getTotalSupply()))
						.add(new BigDecimal(bchBean.getTotalSupply()))
						.add(new BigDecimal(ltcBean.getTotalSupply()).add(new BigDecimal(adaBean.getTotalSupply()))
								.add(new BigDecimal(eosBean.getTotalSupply())))
						.multiply(new BigDecimal(bp));

				BigDecimal bitStdFuturesIndex = numeratorBigDecimal.divide(denominatorBigDecimal, 2,
						BigDecimal.ROUND_HALF_DOWN);
				System.out.println("bitStdFuturesIndex : " + bitStdFuturesIndex.doubleValue());

				if (bitStdFuturesIndex.doubleValue() != 0) {
					BitSTDFuturesDao bitstdfuturesdao = new BitSTDFuturesDao();
					bitstdfuturesdao.doExecuteBitSTDFuturesIndex(conn, bitStdFuturesIndex.doubleValue(), "7");
					bitstdfuturesdao.insertToBitSTDFuturesIndexHis(conn, bitStdFuturesIndex.doubleValue(), "7");
					bitstdfuturesdao.insertToBitSTDFuturesIndexHisAggregation(conn, bitStdFuturesIndex.doubleValue(), "7");
				}

				Thread.sleep(1000 * 12);
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}
	}

	public static void main(String[] args) {
		BitSTDFuturesTask futures = new BitSTDFuturesTask();
		futures.getBitSTDFuturesIndex();
	}

	class BitSTDFuturesCallable implements Callable<AvgInfoBean> {
		private AvgInfoBean bean;
		private int index;
		private Connection conn;

		BitSTDFuturesCallable(int index, Connection conn) {
			this.index = index;
			this.conn = conn;
		}

		@Override
		public AvgInfoBean call() throws Exception {
			switch (index) {
			case 0: // BTC
				bean = getBTCFuturesIndex();
				break;
			case 1: // ETH
				bean = getETHFuturesIndex();
				break;
			case 2: // XRP
				bean = getXRPFuturesIndex();
				break;
			case 3: // BCH
				bean = getBCHFuturesIndex();
				break;
			case 4: // LTC
				bean = getLTCFuturesIndex();
				break;
			case 5: // ADA
				bean = getADAFuturesIndex();
				break;
			case 6: // EOS
				bean = getEOSFuturesIndex();
				break;
			}
			bean = getAvgInfoBean(conn, bean);
			return bean;
		}

	}
}
