package com.bitstd.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.CryptoInfoBean;
import com.bitstd.service.ICryptoService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/9/18
 */

public class CryptoServiceImpl implements ICryptoService {
	private String doRequest(String url) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(url, "");
	}

	@Override
	public List<CryptoInfoBean> getCryptoInfo(List<String> listings) {
		List<CryptoInfoBean> listbean = new ArrayList<CryptoInfoBean>();
		try {
			String content = doRequest(Constants.COINMARKET_API_TICKER);
			JSONObject jobject = JSON.parseObject(content);
			String data = jobject.getString("data");
			jobject = JSON.parseObject(data);
			if (listings != null && listings.size() > 0) {
				for (int i = 0; i < listings.size(); i++) {
					String id = listings.get(i);
					try {
						String objstr = jobject.getString(id);
						if (objstr != null && !"".equals(objstr)) {
							CryptoInfoBean bean = new CryptoInfoBean();
							JSONObject jsonobj = JSON.parseObject(objstr);
							String name = jsonobj.getString("symbol");
							int rank = jsonobj.getIntValue("rank");
							double circulating_supply = jsonobj.getDouble("circulating_supply");
							BigDecimal supplyBigDecimal = new BigDecimal(circulating_supply).setScale(1, BigDecimal.ROUND_HALF_UP);
							String quotes = jsonobj.getString("quotes");
							jsonobj = JSON.parseObject(quotes);
							
							String usdcontent = jsonobj.getString("USD");
							double usdprice  = JSON.parseObject(usdcontent).getDouble("price");
							BigDecimal usdBigDecimal = new BigDecimal(usdprice).setScale(4, BigDecimal.ROUND_HALF_UP);
							double volume_24h = JSON.parseObject(usdcontent).getDouble("volume_24h");
							BigDecimal volumeBigDecimal = new BigDecimal(volume_24h).setScale(1, BigDecimal.ROUND_HALF_UP);
							double market_cap = JSON.parseObject(usdcontent).getDouble("market_cap");
							BigDecimal marketBigDecimal = new BigDecimal(market_cap).setScale(1, BigDecimal.ROUND_HALF_UP);
							double percent_change_1h = JSON.parseObject(usdcontent).getDouble("percent_change_1h");
							double percent_change_24h = JSON.parseObject(usdcontent).getDouble("percent_change_24h");
							double percent_change_7d = JSON.parseObject(usdcontent).getDouble("percent_change_7d");
							
							String btccontent = jsonobj.getString("BTC");
							double btcprice = JSON.parseObject(btccontent).getDouble("price");
							BigDecimal btcBigDecimal = new BigDecimal(btcprice).setScale(12, BigDecimal.ROUND_HALF_UP);
							
							bean.setAssetCode(name);
							bean.setCirculatingSupply(supplyBigDecimal);
							bean.setRank(rank);
							bean.setCurrencyType("USD");
							bean.setUsdPrice(usdBigDecimal);
							bean.setToBtcPrice(btcBigDecimal);
							bean.setVolume_24h(volumeBigDecimal);
							bean.setMarket_cap(marketBigDecimal);
							bean.setPercent_change_1h(percent_change_1h);
							bean.setPercent_change_24h(percent_change_24h);
							bean.setPercent_change_7d(percent_change_7d);
							
							listbean.add(bean);
						}
					} catch (Exception ex) {
						continue;
					}
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listbean;
	}
	
	public static void main(String[] args) {
		CryptoServiceImpl crypto = new CryptoServiceImpl();
		SupplyServiceImpl supply = new SupplyServiceImpl();
		List<String> listings = supply.getSupplyListings();
		crypto.getCryptoInfo(listings);
	}

}
