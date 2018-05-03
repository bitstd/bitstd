package com.bitstd.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBitmexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.StringUtil;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 4/26/18
 */

public class BitmexServiceImpl implements IBitmexService {

	private String doRequest(String type) throws HttpException, IOException {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("symbol", type);
		paramMap.put("count", "1");
		paramMap.put("reverse", "false");
		String params = StringUtil.createLinkString(paramMap);
		URL url = new URL(Constants.BITMEX_API + "?" + params);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.retrieveResponseFromServer(url);
	}

	private double getBitmexXBTFutures(String type) {
		double price = 0;
		try {
			String content = doRequest(type);
			JSONArray jarray = JSON.parseArray(content);
			if (jarray != null && jarray.size() > 0) {
				price = jarray.getJSONObject(0).getDouble("lastPrice");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return price;
	}

	/*
	 *  XBTM18 ADAM18 BCHM18 ETHM18 LTCM18 XRPM18
	 */
	@Override
	public synchronized ExInfoBean getBitmexFuturesIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		try {
			String content = doRequest(type);
			JSONArray jarray = JSON.parseArray(content);
			if (jarray != null && jarray.size() > 0) {
				double price = jarray.getJSONObject(0).getDouble("lastPrice");
				double volume = jarray.getJSONObject(0).getDouble("volume24h");
				if (price > 0 && volume > 0) {
					if (type.contains("XBT")) {
						volume = volume / price;
					}else{
						double xbtprice = getBitmexXBTFutures("XBTM18");
						price = price * xbtprice;
					}
					eb.setPrice(price);
					eb.setVolume(volume);
					eb.ExBeanToPrint(type + " bitmex ");
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return eb;
	}

}
