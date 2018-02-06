package com.bitstd.service.impl;

import java.io.IOException;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.ICoinbaseService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/13/17
 */
public class CoinbaseServiceImpl implements ICoinbaseService {

	private String doRequest(String type) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.COINBASE_API, type);
	}

	@Override
	public ExInfoBean getCoinbaseIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		try {
			String content = doRequest(type + "/ticker");
			JSONObject jsonObj = JSON.parseObject(content);
			double price = jsonObj.getDouble("price");
			double volume = jsonObj.getDouble("volume");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint(type + " coinbase ");
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
