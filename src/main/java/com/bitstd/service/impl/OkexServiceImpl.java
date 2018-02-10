package com.bitstd.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IOkexService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class OkexServiceImpl implements IOkexService {

	private String doRequest(String type) throws HttpException, IOException {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("symbol", type);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.OKEX_API, "", paramMap, "");
	}

	@Override
	public ExInfoBean getOkexIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			String content = doRequest(type);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONObject ticker = jsonObj.getJSONObject("ticker");
			double price = ticker.getDoubleValue("last");
			double volume = ticker.getDoubleValue("vol");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint("Okex");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return eb;
	}

}
