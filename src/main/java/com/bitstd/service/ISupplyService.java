package com.bitstd.service;

import java.util.List;
import java.util.Map;

import com.bitstd.model.SupplyBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/6/18
 */

public interface ISupplyService {
	
	List<String> getSupplyListings();

	Map<String, String> getSupplyListings(String[] symbols);

	List<SupplyBean> getSupplyInfo(Map<String, String> listings);
	
	
}
