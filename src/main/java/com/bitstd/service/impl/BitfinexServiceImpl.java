package com.bitstd.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBitfinexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.Tools;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/12/17
 */
public class BitfinexServiceImpl implements IBitfinexService {

	private String doRequest(String type) throws HttpException, IOException {
		URL url = new URL(Constants.BITFINEX_API_TICKER + type);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.retrieveResponseFromServer(url);
	}

	@Override
	public synchronized ExInfoBean getBitfinexIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			String content = doRequest(type);
			JSONArray jarray = JSON.parseArray(content);
			if (jarray.size() == 10) {
				double price = jarray.getDouble(6);
				double volume = jarray.getDouble(7);

				if (price > 0 && volume > 0) {
					eb.setPrice(price);
					eb.setVolume(volume);
					eb.ExBeanToPrint(type + " bitfinex ");
				}
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eb;
	}
}
