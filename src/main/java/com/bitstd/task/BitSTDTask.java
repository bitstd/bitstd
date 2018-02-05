package com.bitstd.task;

import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.impl.BitfinexServiceImpl;
import com.bitstd.service.impl.BithumbServiceImpl;
import com.bitstd.service.impl.BitstampServiceImpl;
import com.bitstd.service.impl.CoinbaseServiceImpl;
import com.bitstd.service.impl.KrakenServiceImpl;

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
	
	public static void main(String[] args) {
		BitSTDTask task = new BitSTDTask();
		task.getETHIndex();
	}
	
	public AvgInfoBean getBTCIndex(){
		AvgInfoBean bean = null;
		ExInfoBean bitfinex = bitfinexService.getBitfinexIndex("tBTCUSD");
		ExInfoBean bithumb = bithumbService.getBithumbIndex("BTC");
		ExInfoBean bitstamp = bitstampService.getBitstampIndex("BTCUSD");
		ExInfoBean coinbase = coinbaseService.getCoinbaseIndex("BTC-USD");
		ExInfoBean kraken = krakenService.getKrakenIndex("XXBTZUSD");
		
		if (bithumb != null && bitfinex != null && bitstamp != null && coinbase != null&& kraken != null) {
			double usdprice = (bithumb.getVolume() * bithumb.getPrice() + bitfinex.getVolume() * bitfinex.getPrice()
					+ bitstamp.getVolume() * bitstamp.getPrice() + coinbase.getVolume() * coinbase.getPrice()+ kraken.getVolume() * kraken.getPrice())
					/ (bithumb.getVolume() + bitfinex.getVolume() + bitstamp.getVolume() + coinbase.getVolume()+ kraken.getVolume());
			bean = new AvgInfoBean();
			bean.setBittype("BTC");
			bean.setUsdprice(usdprice);
			bean.setCurrency("USD");
		}
		return bean;
	}
	
	public AvgInfoBean getETHIndex(){
		AvgInfoBean bean = null;
		ExInfoBean bitfinex = bitfinexService.getBitfinexIndex("tETHUSD");
		ExInfoBean bithumb = bithumbService.getBithumbIndex("ETH");
		ExInfoBean bitstamp = bitstampService.getBitstampIndex("ETHUSD");
		ExInfoBean coinbase = coinbaseService.getCoinbaseIndex("ETH-USD");
		ExInfoBean kraken = krakenService.getKrakenIndex("XETHZUSD");
		
		if (bithumb != null && bitfinex != null && bitstamp != null && coinbase != null&& kraken != null) {
			double usdprice = (bithumb.getVolume() * bithumb.getPrice() + bitfinex.getVolume() * bitfinex.getPrice()
					+ bitstamp.getVolume() * bitstamp.getPrice() + coinbase.getVolume() * coinbase.getPrice()+ kraken.getVolume() * kraken.getPrice())
					/ (bithumb.getVolume() + bitfinex.getVolume() + bitstamp.getVolume() + coinbase.getVolume()+ kraken.getVolume());
			System.out.println(usdprice);
			bean = new AvgInfoBean();
			bean.setBittype("BTC");
			bean.setUsdprice(usdprice);
			bean.setCurrency("USD");
		}
		return bean;
	}
	
}
