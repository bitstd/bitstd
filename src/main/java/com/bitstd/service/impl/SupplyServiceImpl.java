package com.bitstd.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.SupplyBean;
import com.bitstd.service.ISupplyService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/6/18
 */

public class SupplyServiceImpl implements ISupplyService {

	private String doRequest(String url) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(url, "");
	}
	
	@Override
	public Map<String,String> getSupplyListings(String[] symbols){
		Map<String,String> listings = new HashMap<String,String>();
		try {
			String content = doRequest(Constants.SUPPLYLISTINGS_API);
			JSONObject jobject = JSON.parseObject(content);
			String data = jobject.getString("data");
			JSONArray jarray = JSON.parseArray(data);
			if(jarray!=null && symbols!=null){
				for (int i = 0; i < jarray.size(); i++) {
					JSONObject jsonObj = JSON.parseObject(jarray.getString(i));
					String name = jsonObj.getString("symbol");
					for(int j=0;j<symbols.length;j++){
						if (name.equalsIgnoreCase(symbols[j])) {
							String id = jsonObj.getString("id");
							listings.put(id, name);
						}
					}
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listings;
	}

	@Override
	public List<SupplyBean> getSupplyInfo(Map<String,String> listings) {
		List<SupplyBean> list = new ArrayList<SupplyBean>();
		try {
			SupplyBean bean = null;
			String content = doRequest(Constants.SUPPLY_API);
			JSONObject jobject = JSON.parseObject(content);
			String data = jobject.getString("data");
			jobject = JSON.parseObject(data);
			if(listings!=null && listings.size()>0){
				for (Map.Entry<String, String> entry : listings.entrySet()) {
					String id = entry.getKey();
					String name = entry.getValue();
					String objstr  = jobject.getString(id);
					JSONObject jsonobj = JSON.parseObject(objstr);
					bean = new SupplyBean();
					double total_supply = jsonobj.getDouble("circulating_supply");
					bean.setTotal_supply(total_supply);
					bean.setSymbol(name);
					list.add(bean);
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		SupplyServiceImpl supply = new SupplyServiceImpl();
		String[] symbols = { "BTC", "ETH", "XRP", "BCH", "LTC", "ADA", "EOS" };
		Map<String,String> map = supply.getSupplyListings(symbols);
		supply.getSupplyInfo(map);
	}

}
