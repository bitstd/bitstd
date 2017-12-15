package com.bitstd.service.impl;

import java.io.IOException;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBitfinexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/12/17
 */
public class BitfinexServiceImpl implements IBitfinexService {

	private String doRequest(String type) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.BITFINEX_API_TICKER, type);
	}

	@Override
	public ExInfoBean getBitfinexPrice(String type) {
		ExInfoBean eb = new ExInfoBean();
		try {
			String content = doRequest(type);
			JSONArray jarray = JSON.parseArray(content);
			if (jarray.size() == 10) {
				double price = jarray.getDouble(6);
				double volume = jarray.getDouble(7);
				if (price > 0 && volume > 0) {
					eb.setPrice(price);
					eb.setVolume(volume);
					eb.ExBeanToPrint("bitfinex");
				}
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eb;
	}
}
