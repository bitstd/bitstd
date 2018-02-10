package com.bitstd.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBittrexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class BittrexServiceImpl implements IBittrexService {

	private String doRequest(String type) throws HttpException, IOException {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("market", type);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.BITTREX_API, "", paramMap, "");
	}

	@Override
	public ExInfoBean getBittrexIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			String content = doRequest(type);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONArray result = jsonObj.getJSONArray("result");
			JSONObject jresult = result.getJSONObject(0);
			double price = jresult.getDoubleValue("Last");
			double volume = jresult.getDoubleValue("Volume");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint("Bittrex");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return eb;
	}
}
