package com.bitstd.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IKrakenService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/13/17
 */
public class KrakenServiceImpl implements IKrakenService {

	private String doRequest(String type) throws HttpException, IOException {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pair", type);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.KRAKEN_API_TICKER, "", paramMap, "");
	}

	@Override
	public ExInfoBean getKrakenIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		try {
			String content = doRequest(type);
			JSONObject jsonObj = JSON.parseObject(content);
			JSONObject result = jsonObj.getJSONObject("result");
			JSONObject contents = result.getJSONObject(type);
			JSONArray jprice = contents.getJSONArray("c");
			JSONArray jvolume = contents.getJSONArray("v");
			if (jprice != null && jprice.size() == 2 && jvolume != null && jvolume.size() == 2) {
				double price = jprice.getDoubleValue(0);
				double volume = jvolume.getDoubleValue(1);
				if (price > 0 && volume > 0) {
					eb.setPrice(price);
					eb.setVolume(volume);
					eb.ExBeanToPrint(type+" kraken ");
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
