package com.bitstd.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBitstampService;
import com.bitstd.utils.BitSTDException;
import com.bitstd.utils.Constants;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/12/17
 */
public class BitstampServiceImpl implements IBitstampService {

	@Override
	public ExInfoBean getBitstampIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		String content = "";
		try {
			content = getTicker(type);
			JSONObject jsonObj = JSON.parseObject(content);
			double price = jsonObj.getDouble("last");
			double volume = jsonObj.getDouble("volume");
			if (price > 0 && volume > 0) {
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint("bitstamp");
			}
		} catch (BitSTDException e) {
			e.printStackTrace();
		}

		return eb;
	}

	private String getTicker(String type) throws BitSTDException {
		return request("ticker", type);
	}

	private String request(String method, String type) throws BitSTDException {
		URLConnection conn = null;
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL(Constants.BITSTAMP_API + method + "/" + type);
			conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setRequestProperty("User-Agent", Constants.BITSTAMP_USER_AGENT);

			// read response
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)
				response.append(line);
			in.close();
		} catch (MalformedURLException e) {
			throw new BitSTDException("Internal error.", e);
		} catch (IOException e) {
			throw new BitSTDException("Error connecting to BitStamp.", e);
		}
		return response.toString();
	}

}
