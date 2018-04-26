package com.bitstd.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IOkexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.StringUtil;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class OkexServiceImpl implements IOkexService {

	private String doRequest(String urlstr, HashMap<String, String> paramMap) throws HttpException, IOException {
		String params = StringUtil.createLinkString(paramMap);
		URL url = new URL(urlstr + "?" + params);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.retrieveResponseFromServer(url);
	}

	@Override
	public ExInfoBean getOkexIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("symbol", type);
			String content = doRequest(Constants.OKEX_API, paramMap);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONObject ticker = jsonObj.getJSONObject("ticker");
			double price = ticker.getDoubleValue("last");
			double volume = ticker.getDoubleValue("vol");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint(type + " Okex");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return eb;
	}

	/*
	 * BTC_USD LTC_USD ETH_USD ETC_USD BCH_USD XRP_USD EOS_USD BTG_USD
	 */
	@Override
	public ExInfoBean getOkexFuturesIndex(String type, String contract) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("symbol", type);
			paramMap.put("contract_type", contract);
			String content = doRequest(Constants.OKEXFUTURE_API, paramMap);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONObject ticker = jsonObj.getJSONObject("ticker");
			double price = ticker.getDoubleValue("last");
			double volume = ticker.getDoubleValue("vol");
			double unit_amount = ticker.getDoubleValue("unit_amount");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				volume = volume * unit_amount / price;
				eb.setVolume(volume);
				eb.ExBeanToPrint(type + " Okex");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
