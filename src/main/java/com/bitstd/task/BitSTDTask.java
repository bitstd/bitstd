package com.bitstd.task;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bitstd.dao.BitSTDDao;
import com.bitstd.dao.CryptocurrencyDao;
import com.bitstd.dao.DBConnection;
import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.ExInfoBean;
import com.bitstd.model.SupplyBean;
import com.bitstd.model.TradeParam;
import com.bitstd.service.impl.BinanceServiceImpl;
import com.bitstd.service.impl.BitfinexServiceImpl;
import com.bitstd.service.impl.BithumbServiceImpl;
import com.bitstd.service.impl.BitstampServiceImpl;
import com.bitstd.service.impl.BittrexServiceImpl;
import com.bitstd.service.impl.CoinbaseServiceImpl;
import com.bitstd.service.impl.KrakenServiceImpl;
import com.bitstd.service.impl.OkexServiceImpl;
import com.bitstd.service.impl.SupplyServiceImpl;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/14/17 
 * The rules are as follows: 
 * 1. Choose top 7 highest market value digital currencies as the measurement standard of the index.
 * 2. For the coins whose circulation amount exceeds 100 million, the overall circulation amount would be 100 million, and the price is raised by calculation. Market value is ensured not the change. (Price calculation function: price=original price*(original circulation amount/100 million)) 
 * 3. Coins traded in fewer than 3 mainstream exchanges are not considered. 
 * 4. Dynamic elimination mechanism is adopted that when the 8th has market value exceeding the 7th for 30 consecutive days, and when condition 3 is met, it will replace the 7th to be counted into the index.
 */

public class BitSTDTask {
	private BitfinexServiceImpl bitfinexService = new BitfinexServiceImpl();
	private BithumbServiceImpl bithumbService = new BithumbServiceImpl();
	private BitstampServiceImpl bitstampService = new BitstampServiceImpl();
	private CoinbaseServiceImpl coinbaseService = new CoinbaseServiceImpl();
	private KrakenServiceImpl krakenService = new KrakenServiceImpl();
	private OkexServiceImpl okexService = new OkexServiceImpl();
	private BittrexServiceImpl bittrexService = new BittrexServiceImpl();
	private BinanceServiceImpl binanceService = new BinanceServiceImpl();
	private SupplyServiceImpl supplyService = new SupplyServiceImpl();
	private List<SupplyBean> listSupply = new ArrayList<SupplyBean>();

	public static void main(String[] args) {
		BitSTDTask task = new BitSTDTask();
		task.getBitSTDIndex();
	}

	private AvgInfoBean getBITIndex(TradeParam trade) {
		AvgInfoBean bean = null;
		double usdprice = 0;
		ExInfoBean bitfinex = bitfinexService.getBitfinexIndex(trade.getBitfinexParam());
		ExInfoBean bithumb = bithumbService.getBithumbIndex(trade.getBithumbParam());
		ExInfoBean binance = binanceService.getBinanceIndex(trade.getBinanceParam());
		ExInfoBean coinbase = coinbaseService.getCoinbaseIndex(trade.getCoinbaseParam());
		ExInfoBean kraken = krakenService.getKrakenIndex(trade.getKrakenParam());
		double volumes = bithumb.getVolume() + bitfinex.getVolume() + coinbase.getVolume() + kraken.getVolume() + binance.getVolume();
		if(volumes != 0){
			usdprice = (bithumb.getVolume() * bithumb.getPrice() + bitfinex.getVolume() * bitfinex.getPrice()
						+ coinbase.getVolume() * coinbase.getPrice() + kraken.getVolume() * kraken.getPrice()
						+ binance.getVolume() * binance.getPrice()) / volumes;
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

	private AvgInfoBean getNonmainBITIndex(TradeParam trade) {
		AvgInfoBean bean = null;
		double usdprice = 0;
		ExInfoBean okex = okexService.getOkexIndex(trade.getOkexParam());
		ExInfoBean bittrex = bittrexService.getBittrexIndex(trade.getBittrexParam());
		ExInfoBean binance = binanceService.getBinanceIndex(trade.getBinanceParam());
		ExInfoBean bitfinex = bitfinexService.getBitfinexIndex(trade.getBitfinexParam());
		double volumes = okex.getVolume() + bittrex.getVolume() + binance.getVolume() + bitfinex.getVolume();
		if(volumes != 0){
			usdprice = (okex.getPrice() * okex.getVolume() + bittrex.getPrice() * bittrex.getVolume()
						+ binance.getPrice() * binance.getVolume() + bitfinex.getPrice() * bitfinex.getVolume())
						/ volumes;
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

	private void getSupplyInfo() {
		String[] symbols = { "BTC", "ETH", "XRP", "BCH", "LTC", "ADA", "NEO", "EOS", "XLM" };
		listSupply = supplyService.getSupplyInfo(symbols);
	}

	private AvgInfoBean getBTCIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tBTCUSD");
		trade.setBithumbParam("BTC");
		trade.setBinanceParam("BTCUSDT");
		trade.setCoinbaseParam("BTC-USD");
		trade.setKrakenParam("XXBTZUSD");
		trade.setBitType("BTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getETHIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tETHUSD");
		trade.setBithumbParam("ETH");
		trade.setBinanceParam("ETHUSDT");
		trade.setCoinbaseParam("ETH-USD");
		trade.setKrakenParam("XETHZUSD");
		trade.setBitType("ETH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getXRPIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tXRPUSD");
		trade.setBithumbParam("XRP");
		trade.setBinanceParam("XRPBTC");
		trade.setKrakenParam("XXRPZUSD");
		trade.setBitType("XRP");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getBCHIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tBCHUSD");
		trade.setBithumbParam("BCH");
		trade.setBinanceParam("BCCUSDT");
		trade.setCoinbaseParam("BCH-USD");
		trade.setKrakenParam("BCHUSD");
		trade.setBitType("BCH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getLTCIndex() {
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tLTCUSD");
		trade.setBithumbParam("LTC");
		trade.setBinanceParam("LTCUSDT");
		trade.setCoinbaseParam("LTC-USD");
		trade.setKrakenParam("XLTCZUSD");
		trade.setBitType("LTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getADAIndex() {
		TradeParam trade = new TradeParam();
		trade.setBittrexParam("USDT-ADA");
		trade.setBinanceParam("ADABTC");
		trade.setBitType("ADA");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getNonmainBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getNEOIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("NEO_USDT");
		trade.setBittrexParam("USDT-NEO");
		trade.setBinanceParam("NEOUSDT");
		trade.setBitType("NEO");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getNonmainBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getEOSIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("EOS_USDT");
		trade.setBitfinexParam("tEOSUSD");
		trade.setBinanceParam("EOSBTC");
		trade.setBitType("EOS");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getNonmainBITIndex(trade);
		return bean;
	}

	private AvgInfoBean getXLMIndex() {
		TradeParam trade = new TradeParam();
		trade.setOkexParam("XLM_USDT");
		trade.setBittrexParam("BTC-XLM");
		trade.setBinanceParam("XLMBTC");
		trade.setBitType("XLM");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getNonmainBITIndex(trade);
		return bean;
	}

	/**
	 * Price calculation function: price=original price*(original circulation
	 * amount/100 million)
	 * 
	 * @param bean
	 * @return
	 */
	private AvgInfoBean getAvgInfoBean(Connection conn, AvgInfoBean bean) {
		AvgInfoBean infobean = bean;
		if(infobean.getUsdprice()==0){
			return infobean;
		}
		CryptocurrencyDao cryptdao = new CryptocurrencyDao();
		try {
			cryptdao.doExecuteBitSTDIndexLast(conn, infobean);
			cryptdao.insertToBitCurrIndex(conn, infobean);
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

	/**
	 * Choose top 7 highest market value digital coins(BTC, ETH, XRP, BCH, LTC,
	 * ADA, NEO)
	 */
	public void getBitSTDIndex() {
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
				/*
				 * AvgInfoBean btcBean = getBTCIndex(); btcBean =
				 * getAvgInfoBean(conn,btcBean); AvgInfoBean ethBean =
				 * getETHIndex(); ethBean = getAvgInfoBean(conn,ethBean);
				 * AvgInfoBean xrpBean = getXRPIndex(); xrpBean =
				 * getAvgInfoBean(conn,xrpBean); AvgInfoBean bchBean =
				 * getBCHIndex(); bchBean = getAvgInfoBean(conn,bchBean);
				 * AvgInfoBean ltcBean = getLTCIndex(); ltcBean =
				 * getAvgInfoBean(conn,ltcBean); AvgInfoBean adaBean =
				 * getADAIndex(); adaBean = getAvgInfoBean(conn,adaBean);
				 * AvgInfoBean neoBean = getNEOIndex(); neoBean =
				 * getAvgInfoBean(conn,neoBean);
				 */
				AvgInfoBean btcBean = null;
				AvgInfoBean ethBean = null;
				AvgInfoBean xrpBean = null;
				AvgInfoBean bchBean = null;
				AvgInfoBean ltcBean = null;
				AvgInfoBean adaBean = null;
				AvgInfoBean neoBean = null;
				AvgInfoBean eosBean = null;
				AvgInfoBean xlmBean = null;
				int taskSize = 9;
				ExecutorService pool = Executors.newFixedThreadPool(taskSize);
				List<Future<AvgInfoBean>> list = new ArrayList<Future<AvgInfoBean>>();
				for (int i = 0; i < taskSize; i++) {
					Callable<AvgInfoBean> c = new BitSTDCallable(i, conn);
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
						neoBean = (AvgInfoBean) list.get(i).get();
						break;
					case 7:
						eosBean = (AvgInfoBean) list.get(i).get();
						break;
					case 8:
						xlmBean = (AvgInfoBean) list.get(i).get();
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
				// BigDecimal neoBigDecimal = new
				// BigDecimal(neoBean.getUsdprice()).multiply(new
				// BigDecimal(neoBean.getTotalSupply()));
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

				BigDecimal bitStdIndex = numeratorBigDecimal.divide(denominatorBigDecimal, 2,
						BigDecimal.ROUND_HALF_DOWN);
				System.out.println("bitStdIndex : " + bitStdIndex.doubleValue());

				if(bitStdIndex.doubleValue()!=0){
					BitSTDDao bitstddao = new BitSTDDao();
					bitstddao.doExecuteBitSTDIndex(conn, bitStdIndex.doubleValue(), "7");
					bitstddao.insertToBitSTDIndexHis(conn, bitStdIndex.doubleValue(), "7");
				}

				Thread.sleep(1000 * 12);
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}

	}

	/**
	 * multithreading to get the return result
	 * 
	 * @author BitSTD
	 * @created 02/27/18
	 */
	class BitSTDCallable implements Callable<AvgInfoBean> {
		private AvgInfoBean bean;
		private int index;
		private Connection conn;

		BitSTDCallable(int index, Connection conn) {
			this.index = index;
			this.conn = conn;
		}

		@Override
		public AvgInfoBean call() throws Exception {
			switch (index) {
			case 0: // BTC
				bean = getBTCIndex();
				break;
			case 1: // ETH
				bean = getETHIndex();
				break;
			case 2: // XRP
				bean = getXRPIndex();
				break;
			case 3: // BCH
				bean = getBCHIndex();
				break;
			case 4: // LTC
				bean = getLTCIndex();
				break;
			case 5: // ADA
				bean = getADAIndex();
				break;
			case 6: // NEO
				bean = getNEOIndex();
				break;
			case 7: // EOS
				bean = getEOSIndex();
				break;
			case 8: // XLM
				bean = getXLMIndex();
				break;
			}
			bean = getAvgInfoBean(conn, bean);
			return bean;
		}

	}

}
