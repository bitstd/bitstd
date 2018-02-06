package com.bitstd.task;

import java.util.ArrayList;
import java.util.List;

import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.ExInfoBean;
import com.bitstd.model.SupplyBean;
import com.bitstd.model.TradeParam;
import com.bitstd.service.impl.BitfinexServiceImpl;
import com.bitstd.service.impl.BithumbServiceImpl;
import com.bitstd.service.impl.BitstampServiceImpl;
import com.bitstd.service.impl.CoinbaseServiceImpl;
import com.bitstd.service.impl.KrakenServiceImpl;
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
	private SupplyServiceImpl supplyService = new SupplyServiceImpl();
	private List<SupplyBean> listSupply = new ArrayList<SupplyBean>();
	
	public static void main(String[] args) {
		BitSTDTask task = new BitSTDTask();
		task.getBTCIndex();
	}
	
	public BitSTDTask(){
		getSupplyInfo();
	}
	
	private AvgInfoBean getBITIndex(TradeParam trade){
		AvgInfoBean bean = null;
		ExInfoBean bitfinex = bitfinexService.getBitfinexIndex(trade.getBitfinexParam());
		ExInfoBean bithumb = bithumbService.getBithumbIndex(trade.getBithumbParam());
		ExInfoBean bitstamp = bitstampService.getBitstampIndex(trade.getBitstampParam());
		ExInfoBean coinbase = coinbaseService.getCoinbaseIndex(trade.getCoinbaseParam());
		ExInfoBean kraken = krakenService.getKrakenIndex(trade.getKrakenParam());
		double usdprice = (bithumb.getVolume() * bithumb.getPrice() + bitfinex.getVolume() * bitfinex.getPrice()
				+ bitstamp.getVolume() * bitstamp.getPrice() + coinbase.getVolume() * coinbase.getPrice()+ kraken.getVolume() * kraken.getPrice())
				/ (bithumb.getVolume() + bitfinex.getVolume() + bitstamp.getVolume() + coinbase.getVolume()+ kraken.getVolume());
		bean = new AvgInfoBean();
		bean.setBittype(trade.getBitType());
		bean.setUsdprice(usdprice);
		bean.setCurrency(trade.getCurrencyType());
		if(listSupply!=null && listSupply.size()>0){
			for(int i =0;i<listSupply.size();i++){
				SupplyBean sbean = listSupply.get(i);
				if(trade.getBitType().equalsIgnoreCase(sbean.getSymbol())){
					bean.setTotalSupply(sbean.getTotal_supply());
					break;
				}
			}
		}
		System.out.println(bean.getBittype()+" "+bean.getUsdprice()+" "+bean.getTotalSupply());
		return bean;
	}
	
	public void getSupplyInfo(){
		String[] symbols = {"BTC","ETH","XRP","BCH","LTC"};
		listSupply = supplyService.getSupplyInfo(symbols);
	}
	
	public AvgInfoBean getBTCIndex(){
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tBTCUSD");
		trade.setBithumbParam("BTC");
		trade.setBitstampParam("BTCUSD");
		trade.setCoinbaseParam("BTC-USD");
		trade.setKrakenParam("XXBTZUSD");
		trade.setBitType("BTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}
	
	public AvgInfoBean getETHIndex(){
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tETHUSD");
		trade.setBithumbParam("ETH");
		trade.setBitstampParam("ETHUSD");
		trade.setCoinbaseParam("ETH-USD");
		trade.setKrakenParam("XETHZUSD");
		trade.setBitType("ETH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}
	
	public AvgInfoBean getXRPIndex(){
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tXRPUSD");
		trade.setBithumbParam("XRP");
		trade.setBitstampParam("XRPUSD");
		trade.setCoinbaseParam("XRP-USD");
		trade.setKrakenParam("XXRPZUSD");
		trade.setBitType("XRP");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}
	
	public AvgInfoBean getBCHIndex(){
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tBCHUSD");
		trade.setBithumbParam("BCH");
		trade.setBitstampParam("BCHUSD");
		trade.setCoinbaseParam("BCH-USD");
		trade.setKrakenParam("BCHUSD");
		trade.setBitType("BCH");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}
	
	public AvgInfoBean getLTCIndex(){
		TradeParam trade = new TradeParam();
		trade.setBitfinexParam("tLTCUSD");
		trade.setBithumbParam("LTC");
		trade.setBitstampParam("LTCUSD");
		trade.setCoinbaseParam("LTC-USD");
		trade.setKrakenParam("XLTCZUSD");
		trade.setBitType("LTC");
		trade.setCurrencyType("USD");
		AvgInfoBean bean = getBITIndex(trade);
		return bean;
	}
	
}
