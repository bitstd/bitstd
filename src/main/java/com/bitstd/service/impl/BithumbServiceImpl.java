package com.bitstd.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBithumbService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.Tools;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/19/17
 */
public class BithumbServiceImpl implements IBithumbService {

	private String doRequest(String type) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.BITHUMB_API_TICKER, type);
	}

	@Override
	public ExInfoBean getBithumbIndex(String type) {
		double rate = Tools.getUSDRate("usdkrw", 1086);
		ExInfoBean bean = new ExInfoBean();
		try {
			String content = doRequest(type);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONObject data = jsonObj.getJSONObject("data");
			double price = data.getDoubleValue("sell_price");
			price = price / rate;
			double volume = data.getDoubleValue("units_traded");
			if (price > 0 && volume > 0) {
				bean.setPrice(price);
				bean.setVolume(volume);
				bean.ExBeanToPrint(type + " bithumb ");
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

}
