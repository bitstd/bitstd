package com.bitstd.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private String doRequest() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.SUPPLY_API, "");
	}

	@Override
	public List<SupplyBean> getSupplyInfo(String[] symbols) {
		List<SupplyBean> list = new ArrayList<SupplyBean>();
		try {
			SupplyBean bean = null;
			String content = doRequest();
			JSONArray jarray = JSON.parseArray(content);
			if (jarray != null && symbols!=null) {
				for (int i = 0; i < jarray.size(); i++) {
					JSONObject jsonObj = JSON.parseObject(jarray.getString(i));
					String name = jsonObj.getString("symbol");
					for(int j=0;j<symbols.length;j++){
						if (name.equalsIgnoreCase(symbols[j])) {
							bean = new SupplyBean();
							double total_supply = jsonObj.getDouble("total_supply");
							String symbol = jsonObj.getString("symbol");
							bean.setTotal_supply(total_supply);
							bean.setSymbol(symbol);
							list.add(bean);
							break;
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
		return list;
	}

}
